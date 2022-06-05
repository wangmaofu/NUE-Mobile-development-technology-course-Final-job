package com.example.homework

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class InputActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)

        val dbHelper=MyDatabaseHelper(this,"Orders.db",1)

        var save=findViewById<Button>(R.id.save)
        var back=findViewById<Button>(R.id.back)

        var id =findViewById<EditText>(R.id.id)
        var aim =findViewById<EditText>(R.id.aim)
        var send =findViewById<EditText>(R.id.send)
        var sendTel =findViewById<EditText>(R.id.sendTel)
        var receive =findViewById<EditText>(R.id.receive)
        var receiveTel =findViewById<EditText>(R.id.receiveTel)
        var name =findViewById<EditText>(R.id.name)
        var number =findViewById<EditText>(R.id.number)
        var money1 =findViewById<EditText>(R.id.money1)
        var money2 =findViewById<EditText>(R.id.money2)


        save.setOnClickListener {
            var id_string=id.text.toString()
            var aim_string=aim.text.toString()
            var send_string=send.text.toString()
            var sendTel_string=sendTel.text.toString()
            var receive_string=receive.text.toString()
            var receiveTel_string=receiveTel.text.toString()
            var name_string=name.text.toString()
            var number_string=number.text.toString()
            var money1_string=money1.text.toString()
            var money2_string=money2.text.toString()
            if(id_string.isNotEmpty() and aim_string.isNotEmpty() and name_string.isNotEmpty() and number_string.isNotEmpty()){
                //保存至数据库
                val db = dbHelper.writableDatabase
                var select_id:String="id="+id_string
                val cursor=db.query("Orders",null,select_id,null,null,null,null)
                if (cursor.getCount()>0){
                    Toast.makeText(this,"该订单号已有记录，请检查输入单号！",Toast.LENGTH_SHORT).show()
                }
                else{
                    val value=ContentValues().apply {
                        put("id",id_string)
                        put("aim",aim_string)
                        put("send",send_string)
                        put("sendTel",sendTel_string)
                        put("receive",receive_string)
                        put("receiveTel",receiveTel_string)
                        put("name",name_string)
                        put("number",number_string)
                        put("money1",money1_string)
                        put("money2",money2_string)
                    }
                    db.insert("Orders",null,value)
                    Toast.makeText(this,"保存本地成功！",Toast.LENGTH_SHORT).show()
                    finish()
                    val intent1= Intent(this,InputActivity::class.java)
                    startActivity(intent1)
                }
                cursor.close()
            }
            else {
                Toast.makeText(this,"红色框为必填项，请填写完整后保存！",Toast.LENGTH_SHORT).show()
            }
        }
        back.setOnClickListener {
            finish()
        }
    }
}