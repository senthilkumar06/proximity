package com.geeks4share.materix

import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.android.synthetic.main.activity_my_circle.*
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.android.gms.common.api.ApiException
import com.geeks4share.materix.network.NetworkInterface
import com.geeks4share.materix.network.model.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyCircleActivity : BaseActivity() {

    private val RC_SIGN_IN = 999
    private val TAG = "Login"
    override fun onCreate(savedInstanceState: Bundle?) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_circle)
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if(account == null) {
            my_chat.setOnClickListener {
                val signInIntent = mGoogleSignInClient.signInIntent
                startActivityForResult(signInIntent, RC_SIGN_IN)
            }
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            println(account.idToken)
            NetworkInterface.Factory.create().loginTokenValidation(hashMapOf("token" to account.idToken!!)).enqueue(object : Callback<LoginResponse>{
                override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
                    Toast.makeText(this@MyCircleActivity, "Login Failed", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<LoginResponse>?, response: Response<LoginResponse>?) {
                    Toast.makeText(this@MyCircleActivity, "Hi "+response?.body()?.name, Toast.LENGTH_LONG).show()
                }
            })
            // Signed in successfully, show authenticated UI.

        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
        }

    }
}
