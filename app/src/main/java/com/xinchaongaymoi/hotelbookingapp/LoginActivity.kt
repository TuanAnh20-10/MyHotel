package com.xinchaongaymoi.hotelbookingapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.tasks.Task
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.xinchaongaymoi.hotelbookingapp.databinding.ActivityLogin2Binding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLogin2Binding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient:GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLogin2Binding.inflate(layoutInflater)
        firebaseAuth=FirebaseAuth.getInstance()
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this,gso)
        binding.loginGoogleBtn.setOnClickListener{
            signInWithGoogle()
        }
        val loginBtn=binding.loginBtn
        loginBtn.setOnClickListener{
            val email= binding.emailLoginET.text.toString()
            val password =binding.passwordLoginET.text.toString()
            if(email.isNotEmpty()&&password.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener{
                    if(it.isSuccessful){
                        startActivity(Intent(this,MainActivity::class.java))
                    }
                    else{
                       Log.i("Test looiiiiiii",it.exception.toString())
                    }
                }
            }
            else{
                Toast.makeText(this,"Fields cannot be empty",Toast.LENGTH_SHORT).show()

            }
        }
        binding.linkSignUp.setOnClickListener{
            startActivity(Intent(this,AuthenActivity::class.java))
        }
    }
    private fun signInWithGoogle(){
        val signInIntent=googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {
        result ->
        if(result.resultCode==Activity.RESULT_OK){
            val task =GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResult(task)
        }
        else{
            Log.i("faidddddddd","fddddddddd")
        }
    }
    private fun handleResult(task:Task<GoogleSignInAccount>)
    {
        if(task.isSuccessful){
            Log.i("thanhcong","thanhcong")
            val account :GoogleSignInAccount?=task.result
            if(account!=null){
                updateUI(account)
            }
        }
        else{
            Log.i("thatbai","thatbai")

            Toast.makeText(this,task.exception.toString(),Toast.LENGTH_SHORT).show()

        }
    }
    private fun updateUI(account: GoogleSignInAccount){
        val credential = GoogleAuthProvider.getCredential(account.idToken,null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener{
            if(it.isSuccessful)
            {
                val intent =Intent(this,MainActivity::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()

            }
        }
    }

}