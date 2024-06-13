package com.example.usermanagerdemoapplication;

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {
    lateinit var tv_first_name: TextView
    lateinit var tv_last_name: TextView
    lateinit var tv_email: TextView
    lateinit var sign_out_btn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Hook Text View Objects:
        tv_first_name = findViewById<TextView>(R.id.first_name)
        tv_last_name = findViewById<TextView>(R.id.last_name)
        tv_email = findViewById<TextView>(R.id.email)

        // Get Intent Extra Values:
        val first_name = intent.getStringExtra("first_name")
        val last_name = intent.getStringExtra("last_name")
        val email = intent.getStringExtra("email")

        // Set Text View Profile Values:
        tv_first_name.text = first_name
        tv_last_name.text = last_name
        tv_email.text = email

        // Hook Sign Out Button:
        sign_out_btn = findViewById<Button>(R.id.sign_out_btn)

        // Set On Click Listener:
        sign_out_btn.setOnClickListener { signUserOut() }
    }

    fun signUserOut() {
        // Set Text View Profile Values:

        tv_first_name.text = null
        tv_last_name.text = null
        tv_email.text = null


        // Return User Back To Home:
        val goToHome = Intent(this@ProfileActivity, MainActivity::class.java)
        startActivity(goToHome)
        finish()
    }
}