package com.example.dpa_android

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.example.dpa_android.data.model.Produto
import com.example.dpa_android.databinding.FragmentProdutosBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_produtos.*
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Produtos.newInstance] factory method to
 * create an instance of this fragment.
 */
class Produtos : Fragment(R.layout.fragment_produtos) {


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentProdutosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        setHasOptionsMenu(true);
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProdutosBinding.bind(view)
        val args = arguments
        val produto = args!!.getSerializable("produto") as Produto
        binding.textView4.text = "Título: "+produto.nome
        Picasso.get().load("https://denislima.com.br/xyz/uploads/Produtos/"+produto.imagem).into(binding.imageView3);
        binding.textView3.text = "Valor: "+produto.valor
        binding.textView5.text = "Descrição: "+produto.descricao
        binding.textView7.text = "Quantidade: "+produto.quantidade
        binding.textView8.text = "Categoria: "+produto.categoria

        val callback = object : OnBackPressedCallback(true /** true means that the callback is enabled */) {
            override fun handleOnBackPressed() {
                view.findNavController().navigate(R.id.action_produtos_to_navigation_dashboard)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_produtos, container, false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                requireActivity().onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Produtos.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Produtos().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}