package com.websarva.wings.android.alarmapplication2

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.View
import android.view.WindowManager.LayoutParams.*
import android.widget.TextView
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_alarm_edit.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton
import java.util.*

class AlarmEditActivity : AppCompatActivity()
        , SimpleAlertDialog.OnClickListener
        , TimePickerFragment.OnTimeSelectedListener {
    // realmの宣言(null許容型で後から初期化: lateinit)
    private lateinit var realm: Realm

    override fun onNegativeClick() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPositiveClick() {
        TODO(reason = "not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSelected(hourOfDay: Int, minute: Int) {
        timeText.text = "%1$02d:%2$02d".format(hourOfDay, minute)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(intent?.getBooleanExtra("onReceive", false) == true) {
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ->
                    window.addFlags(FLAG_TURN_SCREEN_ON or FLAG_SHOW_WHEN_LOCKED)
                else ->
                    window.addFlags(FLAG_TURN_SCREEN_ON or FLAG_SHOW_WHEN_LOCKED or FLAG_DISMISS_KEYGUARD)
            }
            val dialog = SimpleAlertDialog()
            dialog.show(supportFragmentManager, "alert_dialog")
        }

        setContentView(R.layout.activity_alarm_edit)
//        setSupportActionBar(toolbar)
        val realmConfig = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        realm = Realm.getInstance(realmConfig)

        //現在時刻を表示
        val calendar = Calendar.getInstance()
        // 時・分・秒を設定する
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)
        // dateViewのテキストを変更
        val timeText = findViewById<TextView>(R.id.timeText)
        timeText.text = "${hour}:${minute}"


        val alarmId = intent?.getLongExtra("alarm_id", -1L)
        if (alarmId != -1L) {
            val alarm = realm.where<Alarm>().equalTo("id", alarmId).findFirst()
            timeText.setText(alarm?.time)
            snoozeText.setText(alarm?.snooze)
            alarmText.setText(alarm?.alarm)
            delete.visibility = View.VISIBLE
        } else {
            delete.visibility = View.INVISIBLE
        }

        // 保存ボタン押下時の処理
        save.setOnClickListener {
            when (alarmId) {
                -1L -> {
                    realm.executeTransaction {
                        val maxId = realm.where<Alarm>().max("id")
                        val nextId = (maxId?.toLong() ?: 0L) + 1L
                        val alarm = realm.createObject<Alarm>(nextId)
                        alarm.time = timeText.text.toString()
                        alarm.snooze = snoozeText.text.toString()
                        alarm.alarm = alarmText.text.toString()
                    }
                    alert("追加しました") {
                        yesButton { finish() }
                    }.show()
                }
                else -> {
                    realm.executeTransaction {
                        val alarm = realm.where<Alarm>().equalTo("id", alarmId).findFirst()
                        alarm?.time = timeText.text.toString()
                        alarm?.snooze = snoozeText.text.toString()
                        alarm?.alarm = alarmText.text.toString()
                    }
                    alert("修正しました") {
                        yesButton { finish() }
                    }.show()
                }
            }
        }

        // 削除ボタン押下時の処理
        delete.setOnClickListener {
            realm.executeTransaction {
                realm.where<Alarm>().equalTo("id", alarmId)?.findFirst()?.deleteFromRealm()
            }
            alert("削除しました") {
                yesButton { finish() }
            }.show()
        }

        // ハンドラのインスタンス作成
//        val hander = Handler()

        // nameとpreiodを指定、{}内が全部action指定
//        timer(name = "testTimer", period = 1000) {
//            val calendar = Calendar.getInstance()
//            // 時・分・秒を設定する
//            val hour = calendar.get(Calendar.HOUR_OF_DAY)
//            val minute = calendar.get(Calendar.MINUTE)
//            val second = calendar.get(Calendar.SECOND)

            // ハンドラで処理したい内容をメインメソッドに送信
//            hander.post {
                // dateViewのテキストを変更
//                val timeText = findViewById<TextView>(R.id.timeText)
//                timeText.text = "${hour}:${minute}"
///           }

//        /*
//        * spinnerの設定
//        */
//        val spinner = findViewById<Spinner>(R.id.spinner)
//
//        //ArrayAdapter
//        val adapter = ArrayAdapter(applicationContext,
//            android.R.layout.simple_spinner_item, spinnerItems)
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//
//        //spinnerにadapterをセット
//        spinner.adapter = adapter
//
//
//        //リスナーを登録
//        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                val spinnerParent = parent as Spinner
//                val item = spinnerParent.selectedItem
//                textView4.text = item.toString()
//            }

//            override fun on(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                val spinnerParent = parent as Spinner
//                val item = spinnerParent.selectedItem as Spinner
//                textView4.text = item.toString()
//            }

//            //アイテムが選択されなかった
//            override fun onNothingSelected(parent: AdapterView<*>?){

//            }


//        }


        // 時刻決定ダイアログ表示
        timeText.setOnClickListener {
            val dialog = TimePickerFragment()
            println("test-------------$dialog")
            dialog.show(supportFragmentManager, "time_dialog")
            println("test-------------$dialog")
            val text1 = timeText.text.toString()
            println("test-------------$text1")
        }

        //スヌーズ決定ダイアログ表示
        snoozeText.setOnClickListener {
            /*
            * ※時間があれば実装※
            * 5分おきぐらいにずらっと表示させたい
             */
            val array = arrayOf("スヌーズなし", "1分後", "5分後", "10分後")

            val builder = AlertDialog.Builder(this)
            builder.setTitle("スヌーズ設定")
            builder.setItems(array) { _, which ->
                val selected = array[which]
                try {
                    println("test-------------$selected")
                    snoozeText.text = selected
                } catch (e : IllegalAccessException) {
                    println("test-------------エラー")
                }
            }
            val dialog = builder.create()

            dialog.show()
        }

        //アラーム決定ダイアログ
        alarmText.setOnClickListener {
            val array = arrayOf("アラーム1", "アラーム2", "アラーム3", "アラーム4")

            val builder = AlertDialog.Builder(this)
            builder.setTitle("アラーム設定")
            builder.setItems(array) { _, which ->
                val selected = array[which]
                try {
                    println("test-------------$selected")
                    alarmText.text = selected
                } catch (e : IllegalAccessException) {
                    println("test-------------エラー")
                }
            }
            val dialog = builder.create()

            dialog.show()
        }
    }

}
