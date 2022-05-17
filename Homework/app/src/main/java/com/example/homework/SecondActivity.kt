package com.example.homework

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter

import android.net.Uri
import android.os.Build
import android.os.Bundle

import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class SecondActivity: AppCompatActivity(){

    private var name=""
    private var password= ""
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        var date1:TextView = findViewById<TextView>(R.id.time)
        initThread(date1)
        name= intent.getStringExtra("name_data").toString()
        password = intent.getStringExtra("password_data").toString()

        var name1=findViewById<TextView>(R.id.name1)
        name1.text="我是"+name
        var password1=findViewById<TextView>(R.id.password1)
        password1.text="我的密码是"+password

        var button =findViewById<Button>(R.id.back)
        button.setOnClickListener {
            finish()
        }

        var change_user=findViewById<Button>(R.id.change_user)
        change_user.setOnClickListener {
            finish()
            val intent1=Intent(this,MainActivity::class.java)
            startActivity(intent1)
        }

        var input=findViewById<Button>(R.id.input)
        input.setOnClickListener {
            val intent1=Intent(this,InputActivity::class.java)
            startActivity(intent1)
        }

        var query_local=findViewById<Button>(R.id.query_local)
        query_local.setOnClickListener {
            val intent2=Intent(this,LocalActivity::class.java)
            startActivity(intent2)
        }

        var query_company_XML=findViewById<Button>(R.id.query_company_XML)
        query_company_XML.setOnClickListener {
            //李哥，你从这里开启你XML的activity
            val intent3=Intent(this,XMLActivity::class.java)
            startActivity(intent3)
        }

        var query_company_JSon=findViewById<Button>(R.id.query_company_JSon)
        query_company_JSon.setOnClickListener {
            //李哥，你从这里开启你JSon的activity
            val intent4=Intent(this,JSonActivity::class.java)
            startActivity(intent4)
        }
    }

    /**
     * 启动线程，每一秒更改一次时间
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun initThread(date: TextView) {
        Thread(Runnable {
            try {
                while (true) {

                    runOnUiThread { getCurrentTime(date )
                    }
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }).start()
    }
    companion object {
        /**
         * 获取当前时间
         */
        @RequiresApi(Build.VERSION_CODES.O)
        fun getCurrentTime(date:TextView) :String{
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("当前的时间yyyy年MM月dd日\nHH时mm分ss秒")
            val formatted = current.format(formatter)

            date.setText(formatted.toString())
            return formatted.toString()
        }
    }


}