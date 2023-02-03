package com.example.notesappwithroom.fragment.add

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult

import com.example.demoviewmodel.viewmodel.NoteViewModel
import com.example.notesappwithroom.R
import com.example.notesappwithroom.databinding.FragmentFirstBinding
import com.example.notesappwithroom.model.Note
import kotlinx.coroutines.launch


class FirstFragment : Fragment() {

    lateinit var binding: FragmentFirstBinding

    //USED WHEN SAHRING VIEW MODEL b/w FRAGMENTS of SINGLE ACTIVITY
   // private  val sharedViewModel:SharedViewModel by activityViewModels()

    // USED WHEN VIEWMODEL IS UNIQUE TO SINGLE FRAGMENT
   // private lateinit var sharedViewModel:SharedViewModel

    //VIEW MODEL FOR NOTES
   // private   val mNoteViewModel : NoteViewModel by activityViewModels()
    //private  lateinit var  mNoteViewModel : NoteViewModel
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

        binding = FragmentFirstBinding.inflate(inflater, container, false)



        // USED WHEN VIEWMODEL IS UNIQUE TO SINGLE FRAGMENT
       // sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

      /*  sharedViewModel.country.observe(viewLifecycleOwner, {country ->
            binding.etText.setText(country)
        })

        binding.btnSave.setOnClickListener {
          sharedViewModel.saveCountry(binding.etText.text.toString())
           findNavController().navigate(R.id.action_firstFragment_to_secondFragment)
        }

        binding.btnClear.setOnClickListener {
            sharedViewModel.deleteCountry()
            findNavController().navigate(R.id.action_firstFragment_to_secondFragment)
        }*/


        binding.btnSave.setOnClickListener {
            lifecycleScope.launch {
                insertDataToDatabase()
            }
        }


        return binding.root
    }

    private suspend fun insertDataToDatabase() {
        val title = binding.etText.text.toString()
        val description = binding.etDescription.text.toString()
        val priority = binding.etPriority.text

        if(inputCheck(title, description ,priority)){
            //create note object
             val note = Note(0, title, description, Integer.parseInt(priority.toString()), getBitmap(), 1, "Indore")
            //adding data to database
            mNoteViewModel.addNotes(note)
            Toast.makeText(requireContext(), "Successfully added", Toast.LENGTH_SHORT).show()
            //navigate back
            findNavController().navigate(R.id.action_firstFragment_to_listFragment)
        }else {
            Toast.makeText(requireContext(), "Please fill out all the fields.", Toast.LENGTH_SHORT).show()
        }

    }

    private fun inputCheck(title : String , description : String , priority : Editable) : Boolean{
        return !(TextUtils.isEmpty(title) && TextUtils.isEmpty(description) && priority.isEmpty() )
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