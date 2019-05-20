package com.websarva.wings.android.alarmapplication2

import android.view.View
import android.view.ViewGroup
import io.realm.OrderedRealmCollection
import io.realm.RealmBaseAdapter

class AlarmAdapter(data: OrderedRealmCollection<Alarm>?) : RealmBaseAdapter<Alarm>(data) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}