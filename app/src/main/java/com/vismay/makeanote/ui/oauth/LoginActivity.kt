package com.vismay.makeanote.ui.oauth

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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
    private val database by lazy {
        Firebase.database.reference
    }

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
//            TODO: Show the progress dialog
            mViewBinding.progressIndicatorSync.visibleGone(isGone = false)
            val authUser = FirebaseAuth.getInstance().currentUser
            toggleView(true)
            authUser?.run {
                val user = User(email, displayName)
                database.child("users").child(uid).setValue(user)
                setUserDetails(this)
                viewModel.getAllNotes()
            }
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            toggleView()
        }
    }

    private fun uploadNotesToFirebase(notes: List<NoteEntity>) {
        val authUser = FirebaseAuth.getInstance().currentUser
        authUser?.run {
            val task = database.child("notes").child(uid).setValue(notes)
            task.addOnCompleteListener {
                mViewBinding.progressIndicatorSync.visibleGone(isGone = true)

            }.addOnCanceledListener {
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
        val authUser = FirebaseAuth.getInstance().currentUser

        if (authUser != null && isLastAccountLoggedIn()) {
            toggleView(true)
            setUserDetails(authUser)
        }
    }

    private fun setUserDetails(authUser: FirebaseUser) {
        authUser.run {
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
            mViewBinding.progressIndicatorSync.visibleGone(isGone = false)
            viewModel.getAllNotes()
        }

        mViewBinding.buttonLogout.setOnClickListener {
            AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener {
                    toggleView()
                }
        }
    }

    override val viewModel: LoginViewModel by viewModels()

    override fun getBinding(): ActivityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
}