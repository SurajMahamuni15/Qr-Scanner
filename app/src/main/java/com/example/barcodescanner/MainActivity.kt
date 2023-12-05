package com.example.barcodescanner

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.barcodescanner.databinding.ActivityMainBinding
import com.example.commonutils.util.toastMsg
import dagger.hilt.android.AndroidEntryPoint
import io.github.g00fy2.quickie.QRResult
import io.github.g00fy2.quickie.ScanQRCode
import io.github.g00fy2.quickie.content.QRContent


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null

    private val binding get() = _binding!!

    private val qrScannerLauncher = registerForActivityResult(ScanQRCode(), ::qrResult)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.scan.setOnClickListener {
            qrScannerLauncher.launch(null)
        }
    }

    private fun qrResult(qrResult: QRResult?) {
        when (qrResult) {
            is QRResult.QRError -> toastMsg("something went wrong!")
            QRResult.QRMissingPermission -> toastMsg("please allow permission")
            is QRResult.QRSuccess -> {
                Log.e("result", qrResult.content.rawValue ?: "")
                successResult(qrResult)
            }

            QRResult.QRUserCanceled -> {}
            else -> {}
        }
    }

    private fun successResult(qrResult: QRResult.QRSuccess) {
        if (qrResult.content is QRContent.Url) {
            binding.url.visibility = View.VISIBLE
            binding.url.text = (qrResult.content as QRContent.Url).url
            Intent(Intent.ACTION_VIEW, Uri.parse((qrResult.content as QRContent.Url).url)).let {
                startActivity(it)
            }

        }
    }
}

