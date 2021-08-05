package com.example.todoapp.view.ui.fragment.list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.databinding.ListFragmentBinding
import com.example.todoapp.view.adapter.ListAdapter
import com.example.todoapp.viewmodel.ListViewModel

class ListFragment : Fragment() {

    private lateinit var adapter: ListAdapter
    private lateinit var listViewModel: ListViewModel

    private var _binding: ListFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ListFragmentBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listViewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        initRecyclerView()
        setListener()

        listViewModel.currentTodoData?.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }
    }

    private fun setListener() {
        binding.addFabButton.setOnClickListener {
            findNavController().navigate(ListFragmentDirections.actionListFragmentToAddFragment())
        }

    }

    private fun initRecyclerView() {
        adapter = ListAdapter{position ->
            val data = adapter.dataList[position]
            findNavController().navigate(ListFragmentDirections.actionListFragmentToUpdateFragment(data))
        }
        binding.recyclerview.adapter = adapter
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_menu, menu)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}