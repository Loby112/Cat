package com.example.cat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.cat.databinding.FragmentSecondBinding
import com.example.cat.databinding.FragmentThirdBinding
import com.example.cat.models.CatViewModel
import com.example.cat.models.UserViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class ThirdFragment : Fragment() {
    private var _binding: FragmentThirdBinding? = null
    private val binding get() = _binding!!
    private val catViewModel: CatViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()

    private val args: ThirdFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_third, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = requireArguments()
        val thirdFragmentArgs: ThirdFragmentArgs = ThirdFragmentArgs.fromBundle(bundle)
        val position = thirdFragmentArgs.position
        val cat = catViewModel[position]
        if (cat == null) {
            binding.textviewMessage.text = "No such book!"
            return
        }


    }
}