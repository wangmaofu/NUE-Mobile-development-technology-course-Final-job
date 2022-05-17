package com.example.homework

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.PictureInPictureUiState
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.lang.Exception
import java.lang.NullPointerException

class SigninActivity: AppCompatActivity() {
    @SuppressLint("Range")
    override fun onCreate(saveInstanceState: Bundle?) {
        super.onCreate(saveInstanceState)
        setContentView(R.layout.activity_signin)
        var login = findViewById<Button>(R.id.login)
        var name=findViewById<EditText>(R.id.name)
        var password=findViewById<EditText>(R.id.password)
        var invite=findViewById<EditText>(R.id.invite)
        var back=findViewById<Button>(R.id.backToLogin)

        back.setOnClickListener {
            onBackPressed()
        }
        login.setOnClickListener {
            val dbHelper = MyDatabaseHelper(this,"User.db",1)
            val user_name = name.text.toString()
            val user_password = password.text.toString()

            if(invite.text.toString()=="123456")
            {
                dbHelper.writableDatabase
                val db = dbHelper.writableDatabase
                val value=ContentValues().apply {
                    put("name",user_name)
                    put("password",user_password)
                }
                if(user_name.isNotEmpty() and user_password.isNotEmpty()) {
                    var exist=0
                    var cursor=db.query("User",null,null,null,null,null,null)
                    if(cursor.moveToFirst()){
                        do {
                            val name1 = cursor.getString(cursor.getColumnIndex("name"))
                            if(name1.equals(user_name) )exist=1
                        }while(cursor.moveToNext())
                    }
                    cursor.close()
                    if(exist.equals(1)){
                        Toast.makeText(this, "注册失败，已存在该用户！！", Toast.LENGTH_LONG).show();
                        db.close()
                        finish()
                        val intent3 = Intent(this, SigninActivity::class.java)
                        startActivity(intent3)
                    }
                    else{
                        db.insert("User", null, value)
                        Toast.makeText(this, "注册成功，请返回登录！", Toast.LENGTH_LONG).show();
                        finish()
                        val intent3 = Intent(this, SigninActivity::class.java)
                        startActivity(intent3)
                    }
                    db.close()
                }
                else {
                    Toast.makeText(this, "注册失败，用户名冲突或输入不合法！", Toast.LENGTH_LONG).show();
                }

            }
            else {
                Toast.makeText(this, "注册失败，邀请码输入错误！", Toast.LENGTH_LONG).show();
            }
        }
    }
}