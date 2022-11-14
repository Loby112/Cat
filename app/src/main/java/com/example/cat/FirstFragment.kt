package com.example.cat

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cat.databinding.FragmentFirstBinding
import com.example.cat.models.CatViewModel
import com.example.cat.models.MyAdapter
import com.example.cat.models.UserViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    private val userViewModel: UserViewModel by activityViewModels()

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val catViewModel: CatViewModel by activityViewModels()
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

        catViewModel.catLiveData.observe(viewLifecycleOwner) { cats ->
            //Log.d("APPLE", "observer $cats")
            binding.progressbar.visibility = View.GONE
            binding.recyclerView.visibility = if (cats == null) View.GONE else View.VISIBLE
            if (cats != null) {
                val adapter = MyAdapter(cats) { position ->
                    val action =
                        FirstFragmentDirections.actionFirstFragmentToThird(position)
                    findNavController().navigate(action /*R.id.action_FirstFragment_to_SecondFragment*/)
                }
                // binding.recyclerView.layoutManager = LinearLayoutManager(activity)
                var columns = 2
                val currentOrientation = this.resources.configuration.orientation
                if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                    columns = 4
                } else if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
                    columns = 2
                }
                binding.recyclerView.layoutManager = GridLayoutManager(this.context, columns)

                binding.recyclerView.adapter = adapter
            }
        }

        binding.fab.setOnClickListener {
            if (userViewModel.userLiveData.value != null) {
                findNavController().navigate(R.id.action_FirstFragment_to_addCatFragment)
            } else {
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }
        }
        catViewModel.errorMessageLiveData.observe(viewLifecycleOwner) { errorMessage ->
            binding.textviewMessage.text = errorMessage
        }

        catViewModel.reload()

        binding.swiperefresh.setOnRefreshListener {
            catViewModel.reload()
            binding.swiperefresh.isRefreshing = false
        }
        binding.buttonSort.setOnClickListener{
            when (binding.spinnerSorting.selectedItemPosition){
                0 -> catViewModel.sortByReward()
                1 -> catViewModel.sortByRewardDescending()
            }
        }

        binding.buttonFilter.setOnClickListener{
            val name = binding.editTextFilter.text.toString().trim()
            catViewModel.filterByName(name)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}