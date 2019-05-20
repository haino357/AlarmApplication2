package com.websarva.wings.android.alarmapplication2

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Alarm : RealmObject(){
    @PrimaryKey
    var id : Long = 0
    var time : String = ""
}