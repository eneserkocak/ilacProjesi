package com.eneserkocak.ilac.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.eneserkocak.ilac.R
import com.eneserkocak.ilac.databinding.FragmentSecimBinding


class SecimFragment : BaseFragment<FragmentSecimBinding>(R.layout.fragment_secim) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ilacRehberiButton.setOnClickListener {
            navigate(R.id.ilacListesiFragment)
        }

        binding.eczaDolabimButton.setOnClickListener {
            navigate(R.id.tumIlaclarFragment)
        }


    }

  }
