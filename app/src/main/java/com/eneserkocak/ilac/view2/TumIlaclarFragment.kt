package com.eneserkocak.ilac.view2

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.eneserkocak.ilac.R
import com.eneserkocak.ilac.adapter.TumIlaclarAdapter
import com.eneserkocak.ilac.databinding.DialogIlacFaydaBinding
import com.eneserkocak.ilac.databinding.FragmentTumIlaclarBinding
import com.eneserkocak.ilac.db.AppDatabase
import com.eneserkocak.ilac.db.IlacDao
import com.eneserkocak.ilac.model.Ilac
import com.eneserkocak.ilac.model.MIAD_UYARI_SURESI
import com.eneserkocak.ilac.util.Util
import com.eneserkocak.ilac.view.BaseFragment


class TumIlaclarFragment : BaseFragment<FragmentTumIlaclarBinding>(R.layout.fragment_tum_ilaclar) {

    lateinit var dao: IlacDao

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dao = AppDatabase.getInstance(requireContext())!!.ilacDao()
        val ilaclar = dao.getAll()

        val adapter = TumIlaclarAdapter(){

            ilacFaydasiGoster(it)



        }
        binding.tumListeRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.tumListeRecycler.adapter = adapter

        ilaclar?.let {  adapter.setItems(ilaclar)}




        binding.miadiYaklasanButton.setOnClickListener {
           // navigate(R.id.miadiYaklasanlarFragment
            //val tarih = SimpleDateFormat("ddMMyy").parse("280927").time
            val sharedPreferences =  Util.getSharedPreferences(requireContext())
            val miadUyariSuresi = sharedPreferences.getInt(MIAD_UYARI_SURESI,0)
            val miadUyariSureLong = miadUyariSuresi*30*24*60*60*1000L

           val miadiYaklasanlar =  dao.miadiYaklasanlariGetir(System.currentTimeMillis(),miadUyariSureLong)
            adapter.setItems(miadiYaklasanlar)


        }

        binding.miadiGecenlerButton.setOnClickListener {

            val miadiGecenler =  dao.miadiGecenleriGetir(System.currentTimeMillis())
            adapter.setItems(miadiGecenler)

        }
        binding.tumIlaclarButton.setOnClickListener {
            navigate(R.id.tumIlaclarFragment)
        }
        binding.yeniIlacEkleButton.setOnClickListener {
            navigate(R.id.yeniIlacEkleFragment)
        }



    }

    private fun ilacFaydasiGoster(it: Ilac) {

        val binding = DialogIlacFaydaBinding.inflate(LayoutInflater.from(requireContext()))
        binding.ilac = it
        val dialog = AlertDialog.Builder(requireContext()).setView(binding.root).show()

        //dialog.setButton("Kapat"){dialog,which->}
            //.setTitle("Bu ilaç aşağıdaki semptomların tedavisinde kullanılır:")



        //binding.ilac= it

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.ayarlar_menu,menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        findNavController().navigate(R.id.ayarlarFragment)
        return super.onOptionsItemSelected(item)
    }


}