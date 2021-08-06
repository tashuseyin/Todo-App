package com.example.todoapp.view.ui.fragment.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.databinding.ListFragmentBinding
import com.example.todoapp.model.entities.TodoData
import com.example.todoapp.view.adapter.ListAdapter
import com.example.todoapp.view.adapter.SwipeToDelete
import com.example.todoapp.viewmodel.ListViewModel
import com.google.android.material.snackbar.Snackbar
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
        binding.lifecycleOwner = this
        binding.listViewModel = listViewModel
        initRecyclerView()

        listViewModel.currentTodoData?.observe(viewLifecycleOwner) {
            listViewModel.checkDatabaseEmpty(it)
            adapter.setData(it)
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
        swipeToDelete(binding.recyclerview)
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback = object : SwipeToDelete(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemToDelete = adapter.dataList[viewHolder.adapterPosition]
                lifecycleScope.launch {
                    // Delete Item
                    listViewModel.deleteData(itemToDelete)
                    adapter.notifyItemRemoved(viewHolder.adapterPosition)
                }
                // Restore delete item
                restoreDelete(viewHolder.itemView, itemToDelete, viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }


    private fun restoreDelete(view: View, deleteItem: TodoData, position: Int) {
        val snackBar = Snackbar.make(view, "Deleted '${deleteItem.title}'", Snackbar.LENGTH_LONG)
        snackBar.setAction("Undo") {
            lifecycleScope.launch {
                listViewModel.insert(deleteItem)
                adapter.notifyItemChanged(position)
            }
        }
        snackBar.show()
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