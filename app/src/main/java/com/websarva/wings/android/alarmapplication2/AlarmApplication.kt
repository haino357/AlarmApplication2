package com.websarva.wings.android.alarmapplication2

import android.app.Application
import io.realm.Realm

class AlarmApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}