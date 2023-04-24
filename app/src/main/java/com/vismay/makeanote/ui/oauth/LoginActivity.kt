package com.vismay.makeanote.ui.oauth

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.vismay.makeanote.data.local.db.entity.NoteEntity
import com.vismay.makeanote.databinding.ActivityLoginBinding
import com.vismay.makeanote.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            val user = FirebaseAuth.getInstance().currentUser
            Log.d("TAG", "User: ${user?.displayName} || ${user?.email}")
            viewModel.getAllNotes()
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            Log.d("TAG", "Sign in failed!!! >> $response")
        }
    }

    private fun uploadNotesToFirebase(notes: List<NoteEntity>) {
        // Get a reference to the database
        val database = Firebase.database.reference
        val notesRef = database.child("notes")
        Log.d("TAG", "uploadNotesToFirebase: ${notes.size}")
        notes.forEach {
            val key = notesRef.push().key
            Log.d("TAG", "key: $key")
            if (key != null) {
                notesRef.child(key).setValue(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val providers = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())

        val signInIntent = AuthUI
            .getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()

        viewModel.getNotes.observe(this) {
            uploadNotesToFirebase(it)
        }
        mViewBinding.buttonGoogleSignIn.setOnClickListener {
            signInLauncher.launch(signInIntent)
        }
    }

    override val viewModel: LoginViewModel by viewModels()

    override fun getBinding(): ActivityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
}