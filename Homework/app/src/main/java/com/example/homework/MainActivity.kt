package com.example.homework

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity(){
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var back=findViewById<Button>(R.id.back)
        back.setOnClickListener {
            finish()
        }
        val dbHelper = MyDatabaseHelper(this,"User.db",1)

        var login=findViewById<Button>(R.id.login)
        login.setOnClickListener {
            var name=findViewById<EditText>(R.id.name)
            var password=findViewById<EditText>(R.id.password)
            val nameString = name.text.toString()
            val passwordString = password.text.toString()
            var pass=0
            val db=dbHelper.writableDatabase
            if(nameString.isEmpty() or passwordString.isEmpty()) {
                Toast.makeText(this, "请输入账户名和密码！", Toast.LENGTH_SHORT).show()
            }
            else {
                var cursor=db.query("User",null,null,null,null,null,null)
                if(cursor.moveToFirst()){
                    do {
                        val name1 = cursor.getString(cursor.getColumnIndex("name"))
                        val password1 = cursor.getString(cursor.getColumnIndex("password"))
                        if(name1.equals(nameString) and password1.equals(passwordString))pass=1
                    }while(cursor.moveToNext())
                }
                cursor.close()
                if (pass.equals(1)){
                    Toast.makeText(this ,"登录成功！",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,SecondActivity::class.java)
                        intent.putExtra("name_data",nameString)
                    intent.putExtra("password_data",passwordString)
                    startActivity(intent)
                }
                else {
                    Toast.makeText(this ,"账户或密码输入有误，请重新登录！",Toast.LENGTH_LONG).show()
                    finish()
                    val intent3=Intent(this,MainActivity::class.java)
                    startActivity(intent3)
                }
            }
        }

        var signIn=findViewById<Button>(R.id.register)
        signIn.setOnClickListener {
            val intent1=Intent(this,SigninActivity::class.java)
            startActivity(intent1)
        }
    }
}