package dev.michallaskowski.kuiks.sample.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.contribution_list.*

class ContributionList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contribution_list)
        setResponseText()
    }

    private fun setResponseText() {
        (application as BaseSampleApplication).service
            .contributors("michallaskowski", "kuiks")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                label.text= it.joinToString(separator = ", ") { it.login }
            }, {
                it.printStackTrace()
            })
    }
}

