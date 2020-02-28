package dev.michallaskowski.kuiks.sample.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.contribution_list.*
import tech.viacom.sample_android_web.internal.server.api.createService

class ContributionList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contribution_list)
        setResponseText()
    }

    private fun setResponseText() {
        createService()
                    .contributors("michallaskowski", "kuiks")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        call_response_text_view.text = it.toString()
                    }, {
                        it.printStackTrace()
            })
    }
}

