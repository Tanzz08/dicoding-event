package com.example.myapplication.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.myapplication.R
import com.example.myapplication.data.local.entity.EventsEntity
import com.example.myapplication.data.response.ListEventsItem
import com.example.myapplication.databinding.FragmentFavoriteBinding
import com.example.myapplication.ui.finished.FinishedAdapter
import com.example.myapplication.utils.ViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FavoriteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FinishedAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity().application)
        val viewModel: FavoriteViewModel by viewModels {
            factory
        }

        adapter = FinishedAdapter()
        binding.rvFavorite.adapter = adapter

        viewModel.getAllEvents().observe(viewLifecycleOwner) { users ->
            val items = arrayListOf<EventsEntity>()
            users.map {
                val item = ListEventsItem(id = it.id.toInt(), name = it.name, mediaCover = it.mediaCover)
                items.add(item)
            }
            adapter.submitList(items)
        }
    }

}