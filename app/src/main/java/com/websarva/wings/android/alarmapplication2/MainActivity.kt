package com.websarva.wings.android.alarmapplication2

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setSupportActionBar(toolbar)
//        realm = Realm.getDefaultInstance()

//        val alarms = realm.where(Alarm::class.java).findAll()
//        listView.adapter = AlarmAdapter(alarms)

        fab.setOnClickListener { view ->
            startActivity<AlarmEditActivity>()
        }
//        listView.setOnClickListener { parent, view, position, id ->
//            val alarm = parent.getItemAtPosition(position) as Alarm
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        realm.close()
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

