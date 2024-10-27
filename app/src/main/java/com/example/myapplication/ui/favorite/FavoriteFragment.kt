package com.example.myapplication.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.data.response.ListEventsItem
import com.example.myapplication.databinding.FragmentFavoriteBinding
import com.example.myapplication.ui.finished.FinishedAdapter
import com.example.myapplication.utils.ViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FinishedAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity().application)
        val viewModel: FavoriteViewModel by viewModels {
            factory
        }

        adapter = FinishedAdapter()
        binding.rvFavorite.adapter = adapter

        // Set LayoutManager untuk RV
        binding.rvFavorite.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getAllEvents().observe(viewLifecycleOwner) { users ->
            val items = arrayListOf<ListEventsItem>()
            users.map {
                val item = ListEventsItem(
                    id = it.id.toInt(),
                    name = it.name,
                    mediaCover = it.mediaCover.toString(),
                    description = it.description,
                    beginTime = it.beginTime,
                    endTime = it.endTime,
                    ownerName = it.ownerName,
                    summary = it.summary,
                    category = it.category,
                    cityName = it.cityName,
                    quota = it.quota,
                    registrants = it.registrants,
                    link = it.link,
                    imageLogo = it.imageLogo
                )
                items.add(item)
            }
            adapter.submitList(items)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}