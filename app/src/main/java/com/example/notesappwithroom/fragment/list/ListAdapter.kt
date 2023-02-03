package com.example.notesappwithroom.fragment.list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.notesappwithroom.R
import com.example.notesappwithroom.model.Note


class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var notesList = emptyList<Note>()

    inner class MyViewHolder(itemView : View)  : RecyclerView.ViewHolder(itemView){
        var title = itemView.findViewById<TextView>(R.id.tvTitle)
        var description = itemView.findViewById<TextView>(R.id.tvDescription)
        var priority = itemView.findViewById<TextView>(R.id.tvPriority)
        var tvImage = itemView.findViewById<ImageView>(R.id.tvImage)
        var rowLayout = itemView.findViewById<ConstraintLayout>(R.id.rowLayout)
        var checkBox = itemView.findViewById<CheckBox>(R.id.checkBox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return  MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row_item, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = notesList[position]
        holder.title.text = currentItem.title
        holder.description.text = currentItem.description
        val text = "Priority : "
        holder.priority.text = text.plus(currentItem.priority.toString())
        holder.tvImage.load(currentItem.profilePhoto)

        if(currentItem.isDone == 1){
            holder.checkBox.isChecked = true
        }else{
            holder.checkBox.isChecked = false
        }

        holder.rowLayout.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    fun setNotes(notes : List<Note>){
        this.notesList = notes
        notifyDataSetChanged()
    }
}