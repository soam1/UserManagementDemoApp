package com.example.usermanagerdemoapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.usermanagerdemoapplication.helpers.StringHelper
import org.json.JSONException
import org.json.JSONObject

class SignInActivity : AppCompatActivity() {

    lateinit var sign_in_btn: Button
    lateinit var et_email: EditText
    lateinit var et_password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        // Hook Edit Text Fields:
        et_email = findViewById(R.id.email)
        et_password = findViewById<EditText>(R.id.password)

        // Hook Button:
        sign_in_btn = findViewById<Button>(R.id.sign_in_btn)

        // Set Sign In Button On Click Listener:
        sign_in_btn.setOnClickListener { authenticateUser() }
    }


    // End Of On Create Activity.
    fun authenticateUser() {
        // Check For Errors:
        if (!validateEmail() || !validatePassword()) {
            return
        }

        // End Of Check For Errors.

        // Instantiate The Request Queue:
        val queue: RequestQueue = Volley.newRequestQueue(this@SignInActivity)
        // The URL Posting TO:
        val url = "http://192.168.0.104:9080/api/v1/user/login"

        // Set Parameters:
        val params = HashMap<String?, String?>()
        params["email"] = et_email.text.toString()
        params["password"] = et_password.getText().toString()

        // Set Request Object:
        val jsonObjectRequest =
            JsonObjectRequest(
                Request.Method.POST, url, (params as Map<*, *>?)?.let { JSONObject(it) },
                { response ->
                    try {
                        // Get Values From Response Object:
                        val first_name = response["first_name"] as String
                        val last_name = response["last_name"] as String
                        val email = response["email"] as String

                        // Set Intent Actions:
                        val goToProfile = Intent(
                            this@SignInActivity,
                            ProfileActivity::class.java
                        )
                        // Pass Values To Profile Activity:
                        goToProfile.putExtra("first_name", first_name)
                        goToProfile.putExtra("last_name", last_name)
                        goToProfile.putExtra("email", email)
                        // Start Activity:
                        startActivity(goToProfile)
                        finish()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        println(e.message)
                    }
                    // End Of Try Block.
                }) { error ->
                error.printStackTrace()
                println(error.message)
                Toast.makeText(this@SignInActivity, "Login Failed", Toast.LENGTH_LONG)
                    .show()
            } // End Of Set Request Object.

        queue.add(jsonObjectRequest)
    }

    fun goToHome(view: View?) {
        val intent = Intent(this@SignInActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


    // End Of Go To Home Intent Method.
    fun goToSigUpAct(view: View?) {
        val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
        startActivity(intent)
        finish()
    }


    // End Of Go To Sign Up Intent Method.
    fun validateEmail(): Boolean {
        val email = et_email.text.toString()
        // Check If Email Is Empty:
        if (email.isEmpty()) {
            et_email.error = "Email cannot be empty!"
            return false
        } else if (!StringHelper.regexEmailValidationPattern(email)) {
            et_email.error = "Please enter a valid email"
            return false
        } else {
            et_email.error = null
            return true
        } // Check If Email Is Empty.
    }


    // End Of Validate Email Field.
    fun validatePassword(): Boolean {
        val password: String = et_password.getText().toString()

        // Check If Password and Confirm Field Is Empty:
        if (password.isEmpty()) {
            et_password.error = "Password cannot be empty!"
            return false
        } else {
            et_password.error = null
            return true
        } // Check Password and Confirm Field Is Empty.
    } // End Of Validate Password;

}
