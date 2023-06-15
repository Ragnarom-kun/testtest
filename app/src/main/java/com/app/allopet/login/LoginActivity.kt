package com.app.allopet.login

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.view.View
import android.widget.TextView
import android.widget.ImageView
import com.app.allopet.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : Activity() {

    private lateinit var _bg__login_ek2: View
    private lateinit var rectangle_49: View
    private lateinit var login_ek3: TextView
    private lateinit var line_14: View
    private lateinit var vector: ImageView
    private lateinit var allopet: TextView
    private lateinit var rectangle_4204: View
    private lateinit var vector_8: ImageView
    private lateinit var rectangle_4205: View
    private lateinit var rectangle_4213: ImageView
    private lateinit var don_t_have_an_account_yet__sign_up: TextView
    private lateinit var line_16: View
    private lateinit var enter_your_email: TextView
    private lateinit var password: TextView

    // [START declare_auth]
    private lateinit var auth: FirebaseAuth
    // [END declare_auth]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        _bg__login_ek2 = findViewById<View>(R.id._bg__login_ek2)
        rectangle_49 = findViewById<View>(R.id.rectangle_49)
        login_ek3 = findViewById<TextView>(R.id.login_ek3)
        line_14 = findViewById<View>(R.id.line_14)
        vector = findViewById<ImageView>(R.id.vector)
        allopet = findViewById<TextView>(R.id.allopet)
        rectangle_4204 = findViewById<View>(R.id.rectangle_4204)
        vector_8 = findViewById<ImageView>(R.id.vector_8)
        rectangle_4205 = findViewById<View>(R.id.rectangle_4205)
        rectangle_4213 = findViewById<ImageView>(R.id.rectangle_4213)
        don_t_have_an_account_yet__sign_up = findViewById<TextView>(R.id.don_t_have_an_account_yet__sign_up)
        line_16 = findViewById<View>(R.id.line_16)
        enter_your_email = findViewById<TextView>(R.id.enter_your_email)
        password = findViewById<TextView>(R.id.password)

        // sign in with email
        auth = Firebase.auth
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // redirect homepage (TODO)
        }
    }

    private fun signIn(email: String, password: String) {
        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    updateUI(null)
                }
            }
        // [END sign_in_with_email]
    }

    private fun updateUI(user: FirebaseUser?) {
    }

    private fun reload() {
    }

    companion object {
        private const val TAG = "EmailPassword"
    }
}
