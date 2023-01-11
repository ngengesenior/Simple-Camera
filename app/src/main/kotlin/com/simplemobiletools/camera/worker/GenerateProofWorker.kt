package com.simplemobiletools.camera.worker

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.simplemobiletools.camera.helpers.ProofModeUtils
import org.witness.proofmode.ProofMode

/**
 * A background worker for generating proof of images and video taken with Simple Camera
 */
class GenerateProofWorker(private val ctx:Context, workParams:WorkerParameters): Worker(ctx.applicationContext,workParams) {
    override fun doWork(): Result {
        val imageUriString = inputData.getString(ProofModeUtils.MEDIA_KEY)
        val hash = ProofMode.generateProof(ctx, Uri.parse(imageUriString))
        if (hash.isNotEmpty()) {
            return Result.success(ProofModeUtils.createData(ProofModeUtils.MEDIA_KEY,imageUriString))
        }
        return Result.failure()
    }
}
