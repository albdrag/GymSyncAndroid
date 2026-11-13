package com.example.gymsync.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gymsync.R
import com.example.gymsync.util.ValidationUtils

/**
 * Activity handling user sign-in and authentication flow.
 */
class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvRegisterLink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        tvRegisterLink = findViewById(R.id.tvRegisterLink)

        btnLogin.setOnClickListener {
            performLogin()
        }

        tvRegisterLink.setOnClickListener {
            Toast.makeText(this, getString(R.string.auth_no_account), Toast.LENGTH_SHORT).show()
        }
    }

    private fun performLogin() {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()

        if (!ValidationUtils.isNotBlank(email)) {
            etEmail.error = getString(R.string.val_field_required)
            Toast.makeText(this, getString(R.string.val_field_required), Toast.LENGTH_SHORT).show()
            return
        }

        if (!ValidationUtils.isNotBlank(password)) {
            etPassword.error = getString(R.string.val_field_required)
            Toast.makeText(this, getString(R.string.val_field_required), Toast.LENGTH_SHORT).show()
            return
        }

        Toast.makeText(this, getString(R.string.auth_title_login) + " Successful!", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        finish()
    }
}
