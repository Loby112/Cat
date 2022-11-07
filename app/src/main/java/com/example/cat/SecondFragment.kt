package com.example.cat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.cat.databinding.FragmentSecondBinding
import com.example.cat.models.CatViewModel
import com.example.cat.models.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {


    private var _binding: FragmentSecondBinding? = null
    private val userViewModel: UserViewModel by activityViewModels()

    private val args: SecondFragmentArgs by navArgs()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //auth = Firebase.auth

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentUser = userViewModel.userLiveData.value

        /*if (currentUser != null) {
            //binding.emailInputField.setText(currentUser.email) // half automatic login
            // current user exists: No need to login again
            SecondFragmentDirections.actionSecondFragmentToThirdFragment(args.position)
            findNavController().popBackStack()
        }
         */

        binding.messageView.text = "Current user ${currentUser?.email}"
        binding.signIn.setOnClickListener {
            val email = binding.emailInputField.text.toString().trim()
            val password = binding.passwordInputField.text.toString().trim()
            if (email.isEmpty()) {
                binding.emailInputField.error = "No email"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.passwordInputField.error = "No password"
                return@setOnClickListener
            }
            // https://firebase.google.com/docs/auth/android/password-auth
            userViewModel.login(email, password)
            //SecondFragmentDirections.actionSecondFragmentToThirdFragment(args.position)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}