package com.eneserkocak.ilac.view2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.eneserkocak.ilac.databinding.IlacsilbottomsheetFragmentBinding
import com.eneserkocak.ilac.db.AppDatabase
import com.eneserkocak.ilac.model.Ilac
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class IlacSil_BS_Fragment:BottomSheetDialogFragment(){

    lateinit var binding : IlacsilbottomsheetFragmentBinding
    lateinit var silinecekIlac : Ilac

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = IlacsilbottomsheetFragmentBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Tüm İlaçlar Recyler Adapter den Silinecek ilacı Safe Args ile buraya gönderdik. Aşağıda Sil
        arguments?.let {
           silinecekIlac = IlacSil_BS_FragmentArgs.fromBundle(it).silinecekIlac

        }
        binding.okBtn.setOnClickListener {
            AppDatabase.getInstance(requireContext())!!.ilacDao().delete(silinecekIlac)
            findNavController().popBackStack()
        }



        binding.cancelBtn.setOnClickListener {
            findNavController().popBackStack()
        }




    }


}



