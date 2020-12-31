package com.example.coffee

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_main.*
import java.text.NumberFormat
import java.util.*

const val ORDER_SUMMARY="OrderSummary"
class MainActivity : AppCompatActivity() {
    private var quantity:Int=1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var locale= Locale(getString(R.string.en),getString(R.string.IN))
        var  ind= NumberFormat.getCurrencyInstance(locale)
        whippedCreamId.setOnClickListener{
            var price=50*quantity
            price= checkToppings(price)
            val text=getString(R.string.TotalPrice2,ind.format(price))
            price_text_view.text = text
        }
        chocolateId.setOnClickListener{
            var price=50*quantity
            price= checkToppings(price)
            val text=getString(R.string.TotalPrice2,ind.format(price))
            price_text_view.text=text
        }
    }

    fun increment(view: View)
    {
        if(quantity<100) {
            quantity = quantity + 1
        }
        else{
            Toast.makeText(this,getString(R.string.max_limit), Toast.LENGTH_SHORT).show()
        }
        displayQuantity()
        displayPrice()
    }
    fun decrement(view: View)
    {
        if(quantity>1)
        {
            quantity = quantity - 1
        }
        else
        {
            Toast.makeText(this,getString(R.string.min_limit), Toast.LENGTH_SHORT).show()
        }
        displayQuantity()
        displayPrice()
    }

    fun submitOrder(view:View)
    {
        val text:String=displayPrice()

        //*****************************THIS IS A PART OF EMAIL INTENTS***************************
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse(getString(R.string.mailto)) // only email apps should handle this
            putExtra(Intent.EXTRA_EMAIL, getString(R.string.rohitmail))
            putExtra(Intent.EXTRA_SUBJECT, getString(R.string.sending_order_summary))
            putExtra(Intent.EXTRA_TEXT, text)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
        else{
            Toast.makeText(this,getString(R.string.no_app_found),Toast.LENGTH_SHORT).show()
        }
    }

    fun displayQuantity()
    {
        quantity_text_view.setText(quantity.toString())
    }

    fun displayPrice():String
    {
        val locale=Locale(getString(R.string.en),getString(R.string.IN))
        val ind=NumberFormat.getCurrencyInstance(locale)
        var price:Int=50*quantity
        price=checkToppings(price)
        var str=getString(R.string.OrderSummaryName,nameId.text)
        str+="\n"+getString(R.string.addedwhippedcream,whippedCreamId.isChecked.toString())
        str+="\n"+getString(R.string.addedchocolate,chocolateId.isChecked.toString())
        str+="\n"+getString(R.string.quantity_number,quantity)
        str+="\n"+getString(R.string.TotalPrice2,ind.format(price))
        str+="\n"+getString(R.string.thank_you_name,nameId.text)

        val priceshow:String=getString(R.string.TotalPrice2,ind.format(price))
        price_text_view.setText(priceshow)
        return(str)
    }

    fun checkToppings(value:Int):Int{
        var price=value
        if(whippedCreamId.isChecked)//With whipped cream
        {
            price=price+quantity*10
        }
        if(chocolateId.isChecked)
        {
            price=price+quantity*20
        }
        return(price)
    }
    //********************************Second Acitivity*************************************
    fun secondActivity(view: View)
    {
        val intent2=Intent(this,MainActivity2::class.java)
        intent2.putExtra(ORDER_SUMMARY,displayPrice())
        startActivity(intent2)
    }

}