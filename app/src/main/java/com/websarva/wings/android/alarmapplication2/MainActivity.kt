package com.websarva.wings.android.alarmapplication2

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setSupportActionBar(toolbar)

        // Migration処理(Modelクラスに変更が生じた場合に発生するエラーの回避する)
        Realm.init(this)
        val realmConfig = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        realm = Realm.getInstance(realmConfig)

        val alarms = realm.where<Alarm>().findAll()
        listView.adapter = AlarmAdapter(alarms)

        // アラーム新規作成ボタンを押下したときに動く処理
        fab.setOnClickListener { view ->
            startActivity<AlarmEditActivity>()
        }

        listView.setOnItemClickListener { parent, view, position, id ->
            val alarm = parent.getItemAtPosition(position) as Alarm
            startActivity<AlarmEditActivity>("alarm_id" to alarm.id)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        return when (item.itemId) {
//            R.id.action_settings -> true
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
}

