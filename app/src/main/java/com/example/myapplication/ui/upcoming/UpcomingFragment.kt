package com.example.myapplication.ui.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.data.response.ListEventsItem
import com.example.myapplication.databinding.FragmentUpcomingBinding

class UpcomingFragment : Fragment() {

    private var _binding: FragmentUpcomingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {


        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //inisialisasi atau memanggil viewmodel
        val upcomingViewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory()
            ).get(UpcomingViewModel::class.java)

        // observe viewmodel untuk menampilkannya di ui
        upcomingViewModel.listEvent.observe(viewLifecycleOwner) { events ->
            setUpcomingList(events)
        }

        // observe viewmodel untuk ui loading atau progresbar
        upcomingViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        // observe eror message jika tidak ada internet
        upcomingViewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

    }


    // function untuk visibility dari progresbar
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    // function untuk menampilkan recycler view, set layoutmanager, dan set adapter
    private fun setUpcomingList(events: List<ListEventsItem>) {
        val adapter = UpcomingAdapter()

        // untuk set layout manager pada recycler view
        binding.rvUpcoming.layoutManager = LinearLayoutManager(requireContext())

        adapter.submitList(events)
        binding.rvUpcoming.adapter = adapter

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}