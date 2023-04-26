package com.vismay.makeanote.ui.oauth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.vismay.makeanote.R
import com.vismay.makeanote.data.local.db.entity.NoteEntity
import com.vismay.makeanote.databinding.ActivityLoginBinding
import com.vismay.makeanote.ui.base.BaseActivity
import com.vismay.makeanote.utils.extensions.ViewExtensions.visibleGone
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {

    private val user by lazy {
        FirebaseAuth.getInstance().currentUser
    }
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            toggleView(true)
            Log.d("TAG", "Email: ${user?.email} Name: ${user?.displayName}")
            setUserDetails()
            viewModel.getAllNotes()
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            Log.d("TAG", "Sign in failed!!! >> $response")
            toggleView()
        }
    }

    private fun uploadNotesToFirebase(notes: List<NoteEntity>) {
        val database = Firebase.database.reference
        val notesRef = database.child("notes")
        notes.forEach {
            val key = notesRef.push().key
            if (key != null) {
                notesRef.child(key).setValue(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addClickListeners()
        mViewBinding.includeToolbarMain.toolbar.title = getString(R.string.title_cloud_backup)
        viewModel.getNotes.observe(this) {
            uploadNotesToFirebase(it)
        }

        if (user != null && isLastAccountLoggedIn()) {
            toggleView(true)
            setUserDetails()
        }
    }

    private fun setUserDetails() {
        user?.run {
            mViewBinding.textViewUserEmail.text = email
            mViewBinding.textViewUserName.text = displayName
        }
    }

    private fun toggleView(isLoggedIn: Boolean = false) {
        mViewBinding.buttonGoogleSignIn.visibleGone(isGone = isLoggedIn)
        mViewBinding.groupUserLogin.visibleGone(isGone = !isLoggedIn)
    }

    private fun isLastAccountLoggedIn(): Boolean =
        GoogleSignIn.getLastSignedInAccount(this) != null

    private fun getSignInIntent(): Intent {
        val providers = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())

        return AuthUI
            .getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
    }

    private fun addClickListeners() {
        mViewBinding.buttonGoogleSignIn.setOnClickListener {
            signInLauncher.launch(getSignInIntent())
        }

        mViewBinding.buttonSyncNotes.setOnClickListener {

        }

        mViewBinding.buttonLogout.setOnClickListener {
            AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener {
                    Log.d("TAG", "it.isSuccessful: ${it.isSuccessful}")
                    toggleView()
                }
        }
    }

    override val viewModel: LoginViewModel by viewModels()

    override fun getBinding(): ActivityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
}