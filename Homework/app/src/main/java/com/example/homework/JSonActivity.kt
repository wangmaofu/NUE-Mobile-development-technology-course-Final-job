package com.example.homework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import kotlin.concurrent.thread

class JSonActivity : AppCompatActivity() {

    private val order_list=ArrayList<Order>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_json)
        sendRequestWithOkhttp()

        Thread.sleep(1000);
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

    private fun sendRequestWithOkhttp(){
        thread {
            try{
                val client = OkHttpClient()
                val request = Request.Builder()
                    .url("http://60.12.122.142:6080/simulated-Waybills-db.json")
                    .build()
                val response=client.newCall(request).execute()
                val responseData=response.body?.string()
                if (responseData != null){
                    getJSon(responseData)
                }
            }catch (e:Exception)
            {
                e.printStackTrace()
            }
        }
    }

    private fun getJSon(jsonData:String) {
        try {
            val jsonRecordArray = JSONObject(jsonData)

            val jsonArray = jsonRecordArray.getJSONArray("waybillRecord")

            for(i in 0 until jsonArray.length()){
                val jsonObject = jsonArray.getJSONObject(i)

                var waybillNo = jsonObject.getString("waybillNo")
                var consignor = jsonObject.getString("consignor")
                var consignorPhoneNumber = jsonObject.getString("consignorPhoneNumber")
                var consignee = jsonObject.getString("consignee")
                var consigneePhoneNumber = jsonObject.getString("consigneePhoneNumber")
                var transportationDepartureStation = jsonObject.getString("transportationDepartureStation")
                var transportationArrivalStation = jsonObject.getString("transportationArrivalStation")
                var goodsDistributionAddress = jsonObject.getString("goodsDistributionAddress")
                var goodsName = jsonObject.getString("goodsName")
                var numberOfPackages = jsonObject.getInt("numberOfPackages").toString()
                var freightPaidByTheReceivingParty = jsonObject.getInt("freightPaidByTheReceivingParty").toString()
                var freightPaidByConsignor = jsonObject.getInt("freightPaidByConsignor").toString()



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
                //Log.d("为啥没存进去呢？？？？？？？？？？？",value)
            }
        }catch (e:Exception)
        {
            e.printStackTrace()
            //Log.d("报错啦","喵了个咪的")
        }

    }
}