package com.websarva.wings.android.alarmapplication2

import android.app.AlertDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.TimePicker
import android.widget.Toast
import java.util.*

class SimpleAlertDialog : DialogFragment() {

    interface OnClickListener {
        fun onPositiveClick()
        fun onNegativeClick()
    }

    private lateinit var listener: OnClickListener

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is SimpleAlertDialog.OnClickListener) {
            listener = context
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = context
        if (context == null)
            return super.onCreateDialog(savedInstanceState)
        val builder = AlertDialog.Builder(context).apply {
            setMessage("時間になりました！")
            setPositiveButton("起きる") { dialog, which ->
                Toast.makeText(context, "起きるがクリックされました", Toast.LENGTH_SHORT).show()
                listener.onPositiveClick()
            }
            setNegativeButton("あと5分") {dialog, which ->
                Toast.makeText(context, "あと5分がクリックされました", Toast.LENGTH_SHORT).show()
                listener.onNegativeClick()
            }
        }
        return builder.create()
    }
}

class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    interface OnTimeSelectedListener {
        fun onSelected(hourOfDay: Int, minute: Int)
    }

    private lateinit var  listener: OnTimeSelectedListener

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is OnTimeSelectedListener) {
            listener = context
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        return TimePickerDialog(context, this, hour, minute, true)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        listener.onSelected(hourOfDay, minute)
    }

}