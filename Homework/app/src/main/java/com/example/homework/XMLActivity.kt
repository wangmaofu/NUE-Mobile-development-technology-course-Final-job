package com.example.homework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.OkHttpClient
import okhttp3.Request
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader
import java.lang.Exception
import kotlin.concurrent.thread


/*
    Pull解析方式
 */

class XMLActivity : AppCompatActivity() {

    private val order_list=ArrayList<Order>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_xmlactivity)
        sendRequestWithOkHttp()
        //val s=order_list.size;
        //Log.d("orderList",s.toString())
        Thread.sleep(1000);
        val layoutManager = LinearLayoutManager(this)
        var recyclerView=findViewById<RecyclerView>(R.id.recyclerView1)
        recyclerView.layoutManager = layoutManager
        val adapter = Adapter(order_list)
        recyclerView.adapter=adapter

        var back=findViewById<Button>(R.id.back)
        back.setOnClickListener {
            finish()
        }
    }


    private fun sendRequestWithOkHttp() {
        thread {
            try {
                val client = OkHttpClient()
              val request=Request.Builder()
                    .url("http://60.12.122.142:6080/simulated-Waybills-db.xml")
                    .build()
                  val response = client.newCall(request).execute()
                val responseData = response.body?.string()
                if(responseData !=null)
                {
                    getXML(responseData)
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }

    }

    private fun getXML(xmlData:String) {
        try {
            val factory = XmlPullParserFactory.newInstance()
            val xmlPullParser=factory.newPullParser()
            xmlPullParser.setInput(StringReader(xmlData))
            var eventType = xmlPullParser.eventType

            var waybillNo = ""
            var consignor = ""
            var consignorPhoneNumber = ""
            var consignee = ""
            var consigneePhoneNumber = ""
            var transportationDepartureStation = ""
            var transportationArrivalStation = ""
            var goodsDistributionAddress = ""
            var goodsName = ""
            var numberOfPackages = ""
            var freightPaidByTheReceivingParty = ""
            var freightPaidByConsignor = ""
            while(eventType!= XmlPullParser.END_DOCUMENT){
                val nodeName =xmlPullParser.name
                when (eventType){
                    XmlPullParser.START_TAG ->{
                        when (nodeName){
                            "waybillNo"-> waybillNo = xmlPullParser.nextText()
                            "consignor"-> consignor = xmlPullParser.nextText()
                            "consignorPhoneNumber"-> consignorPhoneNumber = xmlPullParser.nextText()
                            "consignee"-> consignee = xmlPullParser.nextText()
                            "consigneePhoneNumber"-> consigneePhoneNumber = xmlPullParser.nextText()
                            "transportationDepartureStation"-> transportationDepartureStation = xmlPullParser.nextText()
                            "transportationArrivalStation"-> transportationArrivalStation = xmlPullParser.nextText()
                            "goodsDistributionAddress"-> goodsDistributionAddress = xmlPullParser.nextText()
                            "goodsName"-> goodsName = xmlPullParser.nextText()
                            "numberOfPackages"-> numberOfPackages = xmlPullParser.nextText()
                            "freightPaidByTheReceivingParty"-> freightPaidByTheReceivingParty = xmlPullParser.nextText()
                            "freightPaidByConsignor"-> freightPaidByConsignor = xmlPullParser.nextText()
                        }
                    }
                    XmlPullParser.END_TAG ->{
                        if("waybillRecord" == nodeName){
                            //更新 Order
                            var value: String =
                                transportationDepartureStation + " --> " + transportationArrivalStation + "        " + goodsName+"   "+numberOfPackages + "\nNo: " + waybillNo + "\n发货人: " +
                                        consignor + "  ( TEL:" + consignorPhoneNumber + " )" + "\n收货人: " + consignee + "  ( TEL:" + consigneePhoneNumber + " )"+"\n详细地址:"+goodsDistributionAddress
                            if (freightPaidByTheReceivingParty == "0")
                            {
                                value = value + "\n已付: " + freightPaidByConsignor + " 元"
                            }
                            else{
                                value = value + "\n到付: " + freightPaidByTheReceivingParty + " 元"
                            }
                            order_list.add(Order(value))

                            //Log.d("value",value)
                        }
                    }
                }
                eventType = xmlPullParser.next()
            }
        }
        catch (e:Exception){
            e.printStackTrace()
        }
    }
}