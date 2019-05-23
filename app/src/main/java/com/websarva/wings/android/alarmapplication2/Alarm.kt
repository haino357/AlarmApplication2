package com.websarva.wings.android.alarmapplication2

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Alarm : RealmObject(){
    @PrimaryKey
    var id : Long = 0
    var time : String = ""
    var snooze : String = ""
    var alarm : String = ""
}

/**
 * モデルを変更すると前までのモデルの情報がすでにあるためエラーが発生する
 * 旧のモデル情報に対して新のモデル情報に更新してやる必要がある
 * これは言語は問わない
 */