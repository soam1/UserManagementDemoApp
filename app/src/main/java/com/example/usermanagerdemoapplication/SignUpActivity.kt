package com.example.usermanagerdemoapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.usermanagerdemoapplication.helpers.StringHelper

class SignUpActivity : AppCompatActivity() {
    lateinit var first_name: EditText
    lateinit var last_name: EditText
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var confirm: EditText
    lateinit var sign_up_btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Hook Edit Text Fields:
        first_name = findViewById(R.id.first_name)
        last_name = findViewById(R.id.last_name)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        confirm = findViewById(R.id.confirm)

        // Hook Sign Up Button:
        sign_up_btn = findViewById(R.id.sign_up_btn)

        sign_up_btn.setOnClickListener { processFormFields() }
    }

    // End Of onCreate Method.
    fun goToHome(view: View?) {
        val intent = Intent(this@SignUpActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun goToSigInAct(view: View?) {
        val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun processFormFields() {
        // Check For Errors:
        if (!validateFirstName() || !validateLastName() || !validateEmail() || !validatePasswordAndConfirm()) {
            return
        }

        // End Of Check For Errors.

        // Instantiate The Request Queue:
        val queue = Volley.newRequestQueue(this@SignUpActivity)
        // The URL Posting TO:
        val url = "http://192.168.0.104:9080/api/v1/user/register"


        // String Request Object:
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                if (response.equals("success", ignoreCase = true)) {
                    first_name.text = null
                    last_name.text = null
                    email.text = null
                    password.text = null
                    confirm.text = null
                    Toast.makeText(
                        this@SignUpActivity,
                        "Registration Successful",
                        Toast.LENGTH_LONG
                    ).show()
                }
                // End Of Response If Block.
            },
            Response.ErrorListener { error ->
                error.printStackTrace()
                println(error.message)
                Toast.makeText(
                    this@SignUpActivity,
                    "Registration Un-Successful BECAUSE ${error.message}",
                    Toast.LENGTH_LONG
                )
                    .show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["first_name"] = first_name.text.toString()
                params["last_name"] = last_name.text.toString()
                params["email"] = email.text.toString()
                params["password"] = password.text.toString()
                return params
            }
        } // End Of String Request Object.

        queue.add(stringRequest)
    }


    // End Of Process Form Fields Method.
    fun validateFirstName(): Boolean {
        val firstName = first_name.text.toString()
        // Check If First Name Is Empty:
        if (firstName.isEmpty()) {
            first_name.error = "First name cannot be empty!"
            return false
        } else {
            first_name.error = null
            return true
        } // Check If First Name Is Empty.
    }


    // End Of Validate First Name Field.
    fun validateLastName(): Boolean {
        val lastName = last_name.text.toString()
        // Check If Last Name Is Empty:
        if (lastName.isEmpty()) {
            last_name.error = "Last name cannot be empty!"
            return false
        } else {
            last_name.error = null
            return true
        } // Check If Last Name Is Empty.
    }

    // End Of Validate Last Name Field.
    fun validateEmail(): Boolean {
        val email_e = email.text.toString()
        // Check If Email Is Empty:
        if (email_e.isEmpty()) {
            email.error = "Email cannot be empty!"
            return false
        } else if (!StringHelper.regexEmailValidationPattern(email_e)) {
            email.error = "Please enter a valid email"
            return false
        } else {
            email.error = null
            return true
        } // Check If Email Is Empty.
    }

    // End Of Validate Email Field.
    fun validatePasswordAndConfirm(): Boolean {
        val password_p = password.text.toString()
        val confirm_p = confirm.text.toString()

        // Check If Password and Confirm Field Is Empty:
        if (password_p.isEmpty()) {
            password.error = "Password cannot be empty!"
            return false
        } else if (password_p != confirm_p) {
            password.error = "Passwords do not match!"
            return false
        } else if (confirm_p.isEmpty()) {
            confirm.error = "Confirm field cannot be empty!"
            return false
        } else {
            password.error = null
            confirm.error = null
            return true
        } // Check Password and Confirm Field Is Empty.
    } // End Of Validate Password and Confirm Field.
} // End Of Sign UP Activity Class.
