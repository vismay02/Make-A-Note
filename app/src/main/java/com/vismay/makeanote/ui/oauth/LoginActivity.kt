package com.vismay.makeanote.ui.oauth

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.vismay.makeanote.data.local.db.entity.NoteEntity
import com.vismay.makeanote.databinding.ActivityLoginBinding
import com.vismay.makeanote.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }
    private val getLoginInfo =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val user = auth.currentUser
                Log.d("TAG", "User: ${user?.displayName} || ${user?.email}")
                viewModel.getAllNotes()
            } else {
                Log.d("TAG", "Sign in failed!!!")
            }
        }

    private fun uploadNotesToFirebase(notes: List<NoteEntity>) {
        // Get a reference to the database
        val database = Firebase.database
        FirebaseAuth.getInstance().currentUser?.uid?.let {
            val notesRef = database.reference.child("notes")
            val key = notesRef.push().key
            if (key != null) {
                notesRef.child(key).setValue(notes).addOnCompleteListener {
                    Log.d("TAG", "isSuccessful: ${it.isSuccessful}")
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        val config = AuthUI
            .getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()

        viewModel.getNotes.observe(this) {
            uploadNotesToFirebase(it)
        }
        mViewBinding.buttonGoogleSignIn.setOnClickListener {
            getLoginInfo.launch(config)
        }
    }

    override val viewModel: LoginViewModel by viewModels()

    override fun getBinding(): ActivityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
}