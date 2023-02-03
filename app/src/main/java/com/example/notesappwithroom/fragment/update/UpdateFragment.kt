package com.example.notesappwithroom.fragment.update

import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.demoviewmodel.viewmodel.NoteViewModel
import com.example.notesappwithroom.R
import com.example.notesappwithroom.databinding.FragmentSecondBinding
import com.example.notesappwithroom.model.Note
import kotlinx.coroutines.launch

class UpdateFragment : Fragment() {

    lateinit var binding: FragmentSecondBinding
    //USED WHEN SAHRING VIEW MODEL b/w FRAGMENTS of SINGLE ACTIVITY
    //private  val sharedViewModel:SharedViewModel by activityViewModels()

    // USED WHEN VIEWMODEL IS UNIQUE TO SINGLE FRAGMENT
    //private lateinit var sharedViewModel:SharedViewModel

            private  val args by navArgs<UpdateFragmentArgs>()

    private lateinit var mNoteViewModel :NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSecondBinding.inflate(inflater, container, false)

        mNoteViewModel = ViewModelProvider(requireActivity()).get(NoteViewModel::class.java)

        binding.updateText.setText(args.currentUser.title)
        binding.updateDescription.setText(args.currentUser.description)
        binding.updatePriority.setText(args.currentUser.priority.toString())

        binding.btnUpdate.setOnClickListener {
            lifecycleScope.launch {
                updateData()
            }
        }

        //Add Menu
        setHasOptionsMenu(true)

        // USED WHEN VIEWMODEL IS UNIQUE TO SINGLE FRAGMENT
       // sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

      /*  sharedViewModel.country.observe(viewLifecycleOwner, {country ->
            binding.etText2.setText(country)
        })

        binding.btnSend2.setOnClickListener {
            sharedViewModel.saveCountry(binding.etText2.text.toString())
            findNavController().navigate(R.id.action_secondFragment_to_firstFragment)
        }

        binding.btnClear2.setOnClickListener {
            sharedViewModel.deleteCountry()
            findNavController().navigate(R.id.action_firstFragment_to_secondFragment)
        }*/

        return binding.root

    }

    private suspend fun updateData() {
        val title = binding.updateText.text.toString()
        val description = binding.updateDescription.text.toString()
        val priority = binding.updatePriority.text

        if(inputCheck(title, description ,priority)){
            //create note object
            val updatedNote = Note(
                args.currentUser.id,
                title,
                description,
                Integer.parseInt(priority.toString()),
                getBitmap(), 1, "Indore"
            )
            //adding data to database
            mNoteViewModel.updateNotes(updatedNote)
            Toast.makeText(requireContext(), "Successfully updated", Toast.LENGTH_SHORT).show()
            //navigate back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }else {
            Toast.makeText(requireContext(), "Please fill out all the fields.", Toast.LENGTH_SHORT).show()
        }

    }

    private fun inputCheck(title : String , description : String , priority : Editable) : Boolean{
        return !(TextUtils.isEmpty(title) && TextUtils.isEmpty(description) && priority.isEmpty() )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu , menu)
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
            mNoteViewModel.deleteNotes(args.currentUser)
            Toast.makeText(requireContext(), "Successfully removed ${args.currentUser.title}.", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No"){ _,_ ->

        }
        builder.setTitle("Delete ${args.currentUser.title}?")
        builder.setMessage("Are you sure you want to delete ${args.currentUser.title}?")
        builder.create().show()
    }

    private suspend fun getBitmap(): Bitmap {
        val loading = ImageLoader(requireContext())
        val request = ImageRequest.Builder(requireContext())
            .data("https://avatars3.githubusercontent.com/u/14994036?s=400&u=2832879700f03d4b37ae1c09645352a352b9d2d0&v=4")
            .build()

        val result = (loading.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }
}