package com.example.myapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.data.response.ListEventsItem
import com.example.myapplication.databinding.FragmentHomeBinding

class HomeFragment : Fragment()  {

    private var _binding: FragmentHomeBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //inisialisasi viewmodel di fragment
        val homeViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[HomeViewModel::class.java]
        homeViewModel.listevent.observe(viewLifecycleOwner){events ->
            setUpcomingList(events)
        }

        // observe eror message jika tidak ada internet
        homeViewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        val finishedViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FinishedViewModel::class.java]
        finishedViewModel.listevent.observe(viewLifecycleOwner){events ->
            setFinishedLst(events)
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        finishedViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }
    }


    private fun setFinishedLst(events: List<ListEventsItem>){
        val adapter = FinishedHomeAdapter()


        binding.rvFinished.layoutManager = LinearLayoutManager(requireContext())

        adapter.submitList(events)
        binding.rvFinished.adapter = adapter

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


    private fun setUpcomingList(events: List<ListEventsItem>) {
        val adapter = UpcomingHomeAdapter()

        //inisialisasi layout manager recycler view
        binding.rvUpcoming.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        adapter.submitList(events)
        binding.rvUpcoming.adapter = adapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}