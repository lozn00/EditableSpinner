package com.example.editablespinner

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.editablespinner.databinding.FragmentFirstBinding
import lozn.spinner.EditSpinner
import lozn.spinner.impl.AutoCompleteSpinnerAdapter

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private val TAG: String?="EditSpinner"
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

        val adapter = AutoCompleteSpinnerAdapter<CharSequence>()
        val data = ArrayList<CharSequence>()
        val mentriesx = arrayOf("a2", "a", "f", "bbc", "12345","ffcc")
        mentriesx.set(0,mentriesx::class.java.simpleName+"_test");
        for (sequence in mentriesx) {
            data.add(sequence)
        }
        adapter.setData(data)
        binding.editspinnerAutotip.setAdapter(adapter)

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


/*
        binding.editspinner.setOnValueChangeListener(object :
            EditSpinner.SimpleOnValueChangeListener() {
            override fun onLossFocusAndTextChange() {

            }
        })

 */
        binding.editspinner.setOnValueChangeListener(object :
            EditSpinner.OnValueChangeListener {
            override  fun onLossFocus() {

                Log.w(TAG,"onLossFocus");
            }
            override fun onLossFocusAndTextChange() {}
            override  fun onTextAutoCompleteChoose(position: Int, id: Long) {}
            override   fun onTextChanged(s: CharSequence?) {}
            override fun onGainFocus(focusText: String?) {}
            override   fun onItemSelectPostionChanged(position: Int, selectedItem: String?): Boolean {
                return false
            }

            override   fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                return false
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}