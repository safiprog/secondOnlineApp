package com.example.onlineapp.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.onlineapp.R
import com.example.onlineapp.RoomDB.AppDatabase
import com.example.onlineapp.RoomDB.ProductModel
import com.example.onlineapp.activity.AddressActivity
import com.example.onlineapp.activity.CategoryActivity
import com.example.onlineapp.adapter.CardAdaptor
import com.example.onlineapp.databinding.FragmentCardBinding

class CardFragment : Fragment() {
    private lateinit var binding: FragmentCardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentCardBinding.inflate(layoutInflater)

        val preference=requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)
        val editor=preference.edit()
        editor.putBoolean("isCart",false )
        editor.apply()

        val dao=AppDatabase.getDatabase(requireContext()).productDao()
        dao.getAllProducts().observe(requireActivity()){
            binding.cardRecycler.adapter=CardAdaptor(requireContext(),it)
            totalCost(it)
        }
        return binding.root
    }

    private fun totalCost(data: List<ProductModel>?) {

        var total=0
        for (item in data!!){
            total+=item.productSp!!.toInt()
            binding.TotalItem.text="Total item in cart is ${data.size}"
            binding.TotalCost.text="Total Cost :$total"
            binding.checkbox.setOnClickListener {
                val intent= Intent(context, AddressActivity::class.java)
                intent.putExtra("totalCost",total)
                startActivity(intent)
            }
        }
    }

}