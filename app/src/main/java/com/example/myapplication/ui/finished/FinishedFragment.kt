package com.example.myapplication.ui.finished

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
import com.example.myapplication.databinding.FragmentFinishedBinding

class FinishedFragment : Fragment() {

    private var _binding: FragmentFinishedBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(FinishedViewModel::class.java)

        _binding = FragmentFinishedBinding.inflate(inflater, container, false)
        val root: View = binding.root



        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val finishedViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FinishedViewModel::class.java)
        finishedViewModel.listevent.observe(viewLifecycleOwner){events ->
            setFinishedList(events)
        }

        finishedViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        // observe eror message jika tidak ada internet
        finishedViewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setFinishedList(events: List<ListEventsItem>) {
        val adapter = FinishedAdapter()

        binding.rvFinished.layoutManager = LinearLayoutManager(requireContext())

        adapter.submitList(events)
        binding.rvFinished.adapter = adapter

    }

    private fun showLoading(isLoading : Boolean){
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}