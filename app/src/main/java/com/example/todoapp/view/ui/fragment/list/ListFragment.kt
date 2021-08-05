package com.example.todoapp.view.ui.fragment.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.databinding.ListFragmentBinding
import com.example.todoapp.view.adapter.ListAdapter
import com.example.todoapp.viewmodel.ListViewModel
import kotlinx.coroutines.launch

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
            listViewModel.checkDatabaseEmpty(it)
            adapter.setData(it)
        }
        listViewModel.emptyDatabase.observe(viewLifecycleOwner) {
            checkDatabase(it)
        }
    }


    private fun checkDatabase(emptyDatabase: Boolean) {
        if (emptyDatabase) {
            binding.apply {
                imageNoData.isVisible = true
                textNoData.isVisible = true
                recyclerview.isVisible = false
            }
        } else {
            binding.apply {
                imageNoData.isVisible = false
                textNoData.isVisible = false
                recyclerview.isVisible = true
            }
        }
    }


    private fun setListener() {
        binding.addFabButton.setOnClickListener {
            findNavController().navigate(ListFragmentDirections.actionListFragmentToAddFragment())
        }

    }

    private fun initRecyclerView() {
        adapter = ListAdapter { position ->
            val data = adapter.dataList[position]
            findNavController().navigate(
                ListFragmentDirections.actionListFragmentToUpdateFragment(
                    data
                )
            )
        }
        binding.recyclerview.adapter = adapter
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_delete_all -> {
                deleteAllData()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllData() {
        val builder = AlertDialog.Builder(context)
        builder.setPositiveButton("Yes") { _, _ ->
            lifecycleScope.launch {
                listViewModel.deleteAllData()
                Toast.makeText(context, "Successfully Removed Everything!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setIcon(R.drawable.ic_danger)
        builder.setTitle("Delete everything?")
        builder.setMessage("Are you sure you want to remove everything?")
        builder.create().show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}