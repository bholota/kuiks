//  Copyright © 2020 Michal Laskowski. All rights reserved.

import sharedTests
import Embassy
import Ambassador

final class MockServerImpl: MockServer {
    private let router: Router

    private let eventLoop: EventLoop
    private var server: HTTPServer!
    private let eventLoopThreadCondition: NSCondition
    private var eventLoopThread: Thread!

    init() {
        do {
            eventLoop = try SelectorEventLoop(selector: try KqueueSelector())
            router = Router()
        } catch {
            fatalError("Unable to set-up because of error: \(error)")
        }

        eventLoopThreadCondition = NSCondition()
    }

    @objc private func runEventLoop() {
        eventLoop.runForever()
        eventLoopThreadCondition.lock()
        eventLoopThreadCondition.signal()
        eventLoopThreadCondition.unlock()
    }

    func route(route: [String : String]) {
        route.forEach { (key, value) in
            router[key] = DataResponse { _ in
                value.data(using: .utf8)!
            }
        }
    }

    func routeRemote(remotes: [String: String]) {
        remotes.forEach { (key, url) in
            router[key] = RewriteResponse(url: URL(string: url)!)
        }
    }

    func shutdown() {
        eventLoop.stop()
        server.stop()
    }

    func start(port: Int32) {
        eventLoopThread = Thread(target: self, selector: #selector(runEventLoop), object: nil)
        server = DefaultHTTPServer(eventLoop: eventLoop, port: Int(port), app: router.app)
        try! server.start()
        eventLoopThread.start()
    }
}

private final class RewriteResponse: WebApp {
    let url: URL
    init(url: URL) {
        self.url = url
    }

    func app(_ environ: [String : Any], startResponse: @escaping ((String, [(String, String)]) -> Void), sendBody: @escaping ((Data) -> Void)) {
        let remoteCall = URLSession.shared.dataTask(with: url) { (data, _, error) in
            if let _ = error {
                XCTFail()
                return
            }

            startResponse("200 OK", [("Content-Type", "application/json")])
            sendBody(data!)
            sendBody(Data())
        }
        remoteCall.resume()
    }
}

final class SampleiOSUITests: DelegatingTestBase {
    override func setUp() {
        continueAfterFailure = false
    }

    override class func testObject() -> NSObject {
        let testExample = TestExample()
        testExample.mockServer = MockServerImpl()
        return testExample
    }

//    override class func testClass() -> AnyClass { TestExample.self }
}
