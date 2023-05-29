package com.eneserkocak.ilac.view2

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController

import com.eneserkocak.ilac.R
import com.eneserkocak.ilac.databinding.FragmentQrCodeBinding
import com.eneserkocak.ilac.model.ILACLAR
import com.eneserkocak.ilac.model.ILAC_ADI
import com.eneserkocak.ilac.model.ILAC_BARKOD
import com.eneserkocak.ilac.model.Ilac
import com.eneserkocak.ilac.view.BaseFragment
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.google.firebase.firestore.FirebaseFirestore
import com.kozan.mylibrary.utils.apputils.ToastUtil
import java.io.IOException
import java.sql.Timestamp
import java.text.SimpleDateFormat

private var scannedValue = ""
class qrCodeFragment : BaseFragment<FragmentQrCodeBinding>(R.layout.fragment_qr_code) {

    //private lateinit var codeScanner: CodeScanner

    private val requestCodeCameraPermission = 1001
    private lateinit var cameraSource: CameraSource
    private lateinit var barcodeDetector: BarcodeDetector



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            askForCameraPermission()
            //requestCodeCameraPermission
        } else {
            setupControls()


        }

        val aniSlide: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.scanner_animation)
        binding.barcodeLine.startAnimation(aniSlide)


    }

    fun setupControls() {barcodeDetector =BarcodeDetector.Builder(requireContext()).setBarcodeFormats(Barcode.ALL_FORMATS).build()

        cameraSource = CameraSource.Builder(requireContext(), barcodeDetector)
            .setRequestedPreviewSize(1920, 1080)
            .setAutoFocusEnabled(true) //you should add this feature
            .build()


        binding.cameraSurfaceView.getHolder().addCallback(object : SurfaceHolder.Callback { @SuppressLint("MissingPermission")
        override fun surfaceCreated(holder: SurfaceHolder) {
            try {
                //Start preview after 1s delay
                cameraSource.start(holder)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

            @SuppressLint("MissingPermission")
            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
                try {
                    cameraSource.start(holder)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource.stop()
            }
        })

        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {
                Toast.makeText(requireContext(), "Scanner has been closed", Toast.LENGTH_SHORT).show()
            }

            override fun receiveDetections(detections: Detector.Detections<Barcode>) {val barcodes = detections.detectedItems
                if (barcodes.size() == 1) {
                    scannedValue = barcodes.valueAt(0).rawValue




                    activity?.runOnUiThread {
                        cameraSource.stop()

                        println("Scanned Value : $scannedValue")

                        val barcode =  if(scannedValue.get(0)=='0') scannedValue.subSequence(3,16).toString()
                            else scannedValue.subSequence(4,17).toString()//"8699540070080"

                        println("barkod : $barcode")

                      // 17270129
                        val skt = scannedValue.substringAfter("\u001D17").subSequence(0,6).toString()
                        println("skt : $skt")

                        val sdf1 = SimpleDateFormat("yyMMdd")
                           val date =  sdf1.parse(skt)

                       val bulunanIlac =  viewModel.ilaclar.value?.find { it.ilacBarkod==barcode }
                        bulunanIlac?.let {
                            it.skt = date
                            viewModel.secilenIlac.value = it
                        } ?:  ToastUtil.longToast(requireContext(),"İlaç veritabanında bulunmamaktadır.")


                        /*FirebaseFirestore.getInstance().collection(ILACLAR)
                            .whereEqualTo(ILAC_BARKOD,barcode).limit(1).get()
                            .addOnSuccessListener {
                                it?.let {
                                    if (it.isEmpty){
                                       ToastUtil.longToast(requireContext(),"İlaç veritabanında bulunmamaktadır.")
                                        return@addOnSuccessListener
                                    }
                                    it.documents.forEach {
                                     //   val ilac = it.toObject(Ilac::class.java)
                                        val ilacAdi = it.get("ilacAdi").toString()
                                        val ilacEndikasyon = it.get("ilacEndikasyon").toString()
                                        val ilacId = it.get("ilacId").toString()

                                       val ilac = Ilac(ilacId
                                           ,ilacAdi,barcode,"","","","",
                                           ilacEndikasyon,date
                                       )
                                        println(ilac)
                                        viewModel.secilenIlac.value = ilac

                                    }
                                }
                            }*/

                        //Toast.makeText(requireActivity(), "value- $scannedValue", Toast.LENGTH_LONG).show()



                       // findNavController().popBackStack()
                        findNavController().navigate(R.id.yeniIlacEkleFragment)
                    }
                }else
                {
                    Toast.makeText(requireActivity(), "value- else", Toast.LENGTH_SHORT).show()

                }
            }
        })
    }


    fun askForCameraPermission() {
        ActivityCompat.requestPermissions(requireActivity(),arrayOf(android.Manifest.permission.CAMERA), requestCodeCameraPermission)
    }

    //override fun
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == requestCodeCameraPermission && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupControls()
            } else {
                Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //override fun
    override fun onDestroy() {
        super.onDestroy()
        cameraSource.stop()
    }

       }



