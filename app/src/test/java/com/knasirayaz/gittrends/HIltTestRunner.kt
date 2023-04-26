package com.knasirayaz.gittrends

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class HIltTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, MyApplication::class.java.name, context)
    }
}