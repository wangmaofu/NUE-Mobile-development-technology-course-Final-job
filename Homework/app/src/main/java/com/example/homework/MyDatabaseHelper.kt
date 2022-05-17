package com.example.homework

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import kotlin.math.log

class MyDatabaseHelper(val context: Context, name: String, version: Int):
    SQLiteOpenHelper(context,name,null,version) {
    private val createUser="create table User ( name text  primary key not null, password text not null )"
    private val createOrder="create table Orders ( id text primary key not null, aim text not null , send text ,"+
            " sendTel text ,receive text , receiveTel text , name text not null, number text not null ,"+
            "money1 text, money2 text)"

    override fun onCreate(p0: SQLiteDatabase) {
        p0.execSQL(createUser)
        p0.execSQL(createOrder)

    //    Toast.makeText(context,"create database",Toast.LENGTH_SHORT).show()
    }

    override fun onUpgrade(p0: SQLiteDatabase, p1: Int, p2: Int) {

    }
}