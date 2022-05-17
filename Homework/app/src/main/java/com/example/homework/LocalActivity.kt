package com.example.homework

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LocalActivity : AppCompatActivity() {

    private val order_list=ArrayList<Order>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_local)

        initOrders()
        val layoutManager = LinearLayoutManager(this)

        var recyclerView=findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        val adapter = Adapter(order_list)
        recyclerView.adapter=adapter
        var back=findViewById<Button>(R.id.back)
        back.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("Range")
    private fun initOrders() {
        //从数据库中读取数据
        val dbHelper = MyDatabaseHelper(this,"Orders.db",1)
        val db = dbHelper.writableDatabase
        val cursor = db.query("Orders",null,null,null,null,null,null)
        if(cursor.moveToFirst())
        {
            do {
                var id = cursor.getString(cursor.getColumnIndex("id")).toString()
                var aim = cursor.getString(cursor.getColumnIndex("aim")).toString()
                var send = cursor.getString(cursor.getColumnIndex("send")).toString()
                var sendTel = cursor.getString(cursor.getColumnIndex("sendTel")).toString()
                var receive = cursor.getString(cursor.getColumnIndex("receive")).toString()
                var receiveTel = cursor.getString(cursor.getColumnIndex("receiveTel")).toString()
                var name = cursor.getString(cursor.getColumnIndex("name")).toString()
                var number = cursor.getString(cursor.getColumnIndex("number")).toString()
                var money1 = cursor.getString(cursor.getColumnIndex("money1")).toString()
                var money2 = cursor.getString(cursor.getColumnIndex("money2")).toString()

                var value: String =
                    aim + "-沈阳     " + name + " " + number + "\nNo: " + id + "\n发货人: " + send + "  ( TEL:" + sendTel + " )" + "\n收货人: " + receive + "  ( TEL:" + receiveTel + " )"
                if (money1.isNotEmpty())
                {
                    value = value + "\n已付 " + money1 + " 元"
                }
                else{
                    value = value + "\n到付 " + money2 + " 元"
                }
                order_list.add(Order(value))
            }while(cursor.moveToNext())
        }
        cursor.close()
    }
}