//
//  HttpMockingTests.swift
//  SampleiOSUITests
//
//  Created by Laskowski, Michal on 28/02/2020.
//  Copyright © 2020 Michal Laskowski. All rights reserved.
//

import Foundation
import Embassy
import Ambassador

final class HttpMockingTests: XCTestCase {

    private let port = 8000
    private(set) var router: Router!
    let app = XCUIApplication()

    private var eventLoop: EventLoop!
    private var server: HTTPServer!
    private var eventLoopThreadCondition: NSCondition!
    private var eventLoopThread: Thread!

    override func setUp() {
        super.setUp()
        setupWebApp()
    }

    override func tearDown() {
        super.tearDown()
        eventLoop.stop()
        server.stop()
    }

    @objc private func runEventLoop() {
        eventLoop.runForever()
        eventLoopThreadCondition.lock()
        eventLoopThreadCondition.signal()
        eventLoopThreadCondition.unlock()
    }

    private func setupWebApp() {
        do {
            eventLoop = try SelectorEventLoop(selector: try KqueueSelector())
            router = Router()
            server = DefaultHTTPServer(eventLoop: eventLoop, port: port, app: router.app)

            try server.start()
        } catch {
            fatalError("Unable to set-up because of error: \(error)")
        }

        eventLoopThreadCondition = NSCondition()
        eventLoopThread = Thread(target: self, selector: #selector(runEventLoop), object: nil)
        eventLoopThread.start()
    }

    func testWithUnmockedResponse() {

        let app = XCUIApplication()
        app.launchArguments = ["-contributors_url", "http://localhost:\(port)"]

        app.launch()

        app.buttons["show_contributors"].tap()
        let errorLabel = app.staticTexts.matching(identifier: "label").matching(NSPredicate(format: "label CONTAINS[c] 'error'")).element.label
        XCTAssertEqual(errorLabel, "Error!")
    }

    func testWithMockedResponse() {
        let app = XCUIApplication()
        app.launchArguments = ["-contributors_url", "http://localhost:\(port)/"]
        router["/repos/michallaskowski/kuiks/contributors"] = JSONResponse { _ in
            [
                ["login": "michal", "contributions": 1],
                ["login": "bartek", "contributions": 1]
            ]
        }

        app.launch()

        app.buttons["show_contributors"].tap()

        XCTAssertEqual(app.staticTexts["label"].label, "Loading...")

        // wait for updated label
        let updatedLabel = app.staticTexts.matching(identifier: "label").matching(NSPredicate(format: "label CONTAINS[c] 'michal'")).element.label
        XCTAssertEqual(updatedLabel, "michal, bartek")
    }

    func testWithMockedResponseWiremock() {
        let app = XCUIApplication()
        app.launchArguments = ["-contributors_url", "http://localhost:8080"]

        app.launch()

        app.buttons["show_contributors"].tap()

        // wait for updated label
        let updatedLabel = app.staticTexts.matching(identifier: "label").matching(NSPredicate(format: "label CONTAINS[c] 'michal'")).element.label
        XCTAssertEqual(updatedLabel, "michal, oskar")
    }

}
