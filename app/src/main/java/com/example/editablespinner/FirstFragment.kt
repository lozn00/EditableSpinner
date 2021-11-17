package com.example.editablespinner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.editablespinner.databinding.FragmentFirstBinding
import lozn.spinner.OnItemLongClickListener

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        binding.buttonFetchValue.setOnClickListener {
//            binding.editspinner

            binding.editspinner.appCompatSpinner.translationY = 50f;//修改弹出的偏移
            Toast.makeText(
                activity,
                "spinner  value:" + binding.editspinner.text+", dropdown pos off modify 50",
                Toast.LENGTH_SHORT
            ).show()
        }
//        binding.editspinner.editText.setText("xxx")
        binding.editspinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {

                Toast.makeText(
                    activity,
                    "change:" + binding.editspinner.text+"",
                    Toast.LENGTH_SHORT
                ).show()

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}