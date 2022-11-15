package com.example.cat

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.cat.databinding.FragmentSecondBinding
import com.example.cat.databinding.FragmentThirdBinding
import com.example.cat.models.CatViewModel
import com.example.cat.models.UserViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

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
        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = requireArguments()
        val thirdFragmentArgs: ThirdFragmentArgs = ThirdFragmentArgs.fromBundle(bundle)
        val position = thirdFragmentArgs.position
        val cat = catViewModel[position]
        Log.d("APPLE", "observer ${cat?.id}")
        if (cat == null) {
            binding.textviewMessage.text = "No such cat!"
            return
        }
        binding.catName.text = cat.name
        binding.catPlace.text = cat.place
        binding.catDescription.text = cat.description
        binding.catReward.text = cat.reward.toString()
        val catDate = cat.date.toLong()
        val sdf = java.text.SimpleDateFormat("yyyy-MM-dd")
        val date = java.util.Date(catDate * 1000)
        binding.catDate.text = sdf.format(date)
        binding.catUserId.text = cat.userId

        binding.DeleteButton.setOnClickListener{
            catViewModel.delete(cat.id)
            val snack = Snackbar.make(it, "Kat Slettet", Snackbar.LENGTH_LONG)
            snack.show()
            findNavController().popBackStack()
        }

        if(Firebase.auth.currentUser?.uid != cat.userId){
            binding.DeleteButton.visibility = View.GONE
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}