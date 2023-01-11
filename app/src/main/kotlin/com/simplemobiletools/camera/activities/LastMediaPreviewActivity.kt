package com.simplemobiletools.camera.activities

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.FileProvider
import com.simplemobiletools.camera.BuildConfig
import com.simplemobiletools.camera.R
import kotlinx.android.synthetic.main.activity_last_media_preview.*
import timber.log.Timber
import java.io.File

class LastMediaPreviewActivity : SimpleActivity() {
    private var imageUriString:String? = null
    private var lastHash:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_last_media_preview)
        imageUriString = intent.getStringExtra("last_taken")
        setImageUri(imageUriString)
        setShareProofOnClick()
    }
    private fun setShareProofOnClick() {
        fab_share_proof.setOnClickListener{
            imageUriString
        }
    }

    private fun setImageUri(uriString:String?) {
        try {
            val uri:Uri? = Uri.parse(uriString)
            imageUriString = uriString
            image_last_taken.setImageURI(uri)
        }catch (_:Exception) {

        }
    }

    private fun shareProofZip() {
        try {
            val fileUri = FileProvider.getUriForFile(this,"${BuildConfig.APPLICATION_ID}.provider",File(imageUriString))
            //val proofZip = ProofModeUtils.makeProofZip(File(fileUri.path).absoluteFile,applicationContext)
            //ProofModeUtils.shareFile(proofZip.absoluteFile,this)
            Timber.e("The scheme is ${fileUri.scheme}")
        }catch (ex:Exception){
            Toast.makeText(this, "The error ${ex.message}, ${ex.cause}", Toast.LENGTH_SHORT).show()
            Timber.e("There was an error ${ex.message}, Cause:${ex.cause}")
            textView.text = "There was an error ${ex.message}, Cause:${ex.cause}"
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("last_taken",imageUriString)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        imageUriString = savedInstanceState.getString("last_taken","")
        setImageUri(imageUriString)
    }
}
