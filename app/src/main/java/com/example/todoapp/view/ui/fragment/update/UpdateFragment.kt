package com.example.todoapp.view.ui.fragment.update

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentUpdateBinding
import com.example.todoapp.model.entities.TodoData
import com.example.todoapp.util.listener
import com.example.todoapp.util.parsePriority
import com.example.todoapp.util.verifyDataCheck
import com.example.todoapp.viewmodel.UpdateViewModel
import kotlinx.coroutines.launch


class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var updateViewModel: UpdateViewModel

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateViewModel = ViewModelProvider(this).get(UpdateViewModel::class.java)

        binding.currentTitleEdit.setText(args.currentItem.title)
        binding.descriptionEdit.setText(args.currentItem.description)
        binding.currentSpinnerPriorities.setSelection(args.currentItem.priority.ordinal)
        binding.currentSpinnerPriorities.onItemSelectedListener = listener(requireContext())
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_save -> {
                updateItem()
            }
            R.id.action_delete -> {
                deleteItem()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteItem() {
        val builder = AlertDialog.Builder(context)
        builder.setPositiveButton("Yes") { _, _ ->
            lifecycleScope.launch {
                updateViewModel.deleteData(args.currentItem)
                Toast.makeText(context, "Successfully Removed!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(UpdateFragmentDirections.actionUpdateFragmentToListFragment())
            }
        }
        builder.setNegativeButton("No"){_,_->}
        builder.setIcon(R.drawable.ic_danger)
        builder.setTitle("Delete '${args.currentItem.title}'")
        builder.setMessage("Are you sure you want to remove '${args.currentItem.title}'")
        builder.create().show()
    }

    private fun updateItem() {
        val title = binding.currentTitleEdit.text.toString()
        val description = binding.descriptionEdit.text.toString()
        val getPriority = binding.currentSpinnerPriorities.selectedItem.toString()

        val validation = verifyDataCheck(title, description)
        if (validation) {
            val updateItem =
                TodoData(args.currentItem.id, title, parsePriority(getPriority), description)
            lifecycleScope.launch {
                updateViewModel.updateData(updateItem)
                closeKeyBoard()
                Toast.makeText(context, "Successfully updated!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(UpdateFragmentDirections.actionUpdateFragmentToListFragment())
            }
        } else {
            Toast.makeText(context, "Please fill out all fields.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun closeKeyBoard() {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}