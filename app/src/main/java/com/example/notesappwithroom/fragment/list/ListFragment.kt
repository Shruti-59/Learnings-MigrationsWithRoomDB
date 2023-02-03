package com.example.notesappwithroom.fragment.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.demoviewmodel.viewmodel.NoteViewModel
import com.example.notesappwithroom.R
import com.example.notesappwithroom.databinding.FragmentListBinding


class ListFragment : Fragment() , SearchView.OnQueryTextListener{

    lateinit var binding: FragmentListBinding
    val adapter = ListAdapter()

    private val mNoteViewModel: NoteViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onViewCreated()"
        }
        //The ViewModelProviders (plural) is deprecated.
        //ViewModelProviders.of(this, DevByteViewModel.Factory(activity.application)).get(DevByteViewModel::class.java)
        ViewModelProvider(this, NoteViewModel.Factory(activity.application)).get(NoteViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mNoteViewModel.getAllNotes.observe(viewLifecycleOwner, Observer { note ->
            adapter.setNotes(note)

        })

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_firstFragment)
        }

        //Add Menu
        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu , menu)

        val search = menu?.findItem(R.id.search_menu)
        val searchView = search?.actionView as SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.delete_menu){
            deleteNote()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun deleteNote() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){ _,_ ->
            mNoteViewModel.deleteAllNotes()
            Toast.makeText(requireContext(), "Successfully removed everything.", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No"){ _,_ ->

        }
        builder.setTitle("Delete everything?")
        builder.setMessage("Are you sure you want to delete everything?")
        builder.create().show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
       if(query != null){
           searchDatabase(query)
       }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if(query != null){
            searchDatabase(query)
        }
        return true
    }

    private fun searchDatabase(query: String?){
        val searchQuery = "%$query%"
        mNoteViewModel.searchDatabase(searchQuery).observe(this) { list ->
            list.let {
                adapter.setNotes(it)
            }

        }
    }
}