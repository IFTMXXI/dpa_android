package com.example.dpa_android.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import android.content.Context
import android.view.ViewGroup
import androidx.constraintlayout.helper.widget.Carousel
import android.widget.*
import androidx.fragment.app.Fragment
import android.widget.AdapterView
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.dpa_android.MainAdapter
import com.example.dpa_android.R
import com.example.dpa_android.databinding.FragmentHomeBinding
import java.io.Serializable
import android.widget.*
import androidx.navigation.findNavController
import com.example.dpa_android.data.model.Produto
import com.example.dpa_android.databinding.FragmentDashboardBinding
import android.widget.GridView
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.row_item.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val imgs  = intArrayOf(
        R.drawable.ic_launcher_background,
        R.mipmap.ic_launcher,
        R.mipmap.ic_launcher,
        R.mipmap.ic_launcher,
        R.mipmap.ic_launcher
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val btnViewProduct: Button = binding.buttonViewProduct
        val btnViewNews: Button = binding.buttonViewNews
        btnViewProduct.setOnClickListener { view ->
            view.findNavController().navigate(R.id.navigation_dashboard)
        }
        btnViewNews.setOnClickListener { view ->
            view.findNavController().navigate(R.id.navigation_notifications)
        }


        return root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}