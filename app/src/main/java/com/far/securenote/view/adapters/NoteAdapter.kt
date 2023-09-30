package com.far.securenote.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.recyclerview.widget.RecyclerView
import com.far.securenote.contants.Colors
import com.far.securenote.databinding.NoteItemBinding
import com.far.securenote.model.Note
import com.far.securenote.utils.ColorUtils

class NoteAdapter( private val notes:List<Note>,val clickListener:(Note)->Unit): RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NoteViewHolder(binding)
    }

    override fun getItemCount() = notes.count()

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position])
        holder.binding.root.setOnClickListener{
            clickListener(notes[position])
        }
    }


    inner class NoteViewHolder(val binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(note:Note){
            binding.tvTitle.text = note.title
            binding.tvBody.text = note.body
            var color = ColorUtils.colorByName(note.color)
            setupColors(color)

        }

        private fun setupColors(color:Colors){
            var colorCombination = ColorUtils.getColors(color)

            binding.tvTitle.setTextColor(getColor(colorCombination.color3))
            binding.tvTitle.setBackgroundColor(getColor(colorCombination.color1))

            binding.tvBody.setTextColor(getColor(colorCombination.color3))
            binding.card.setCardBackgroundColor(getColor(colorCombination.color2))

        }

        private fun getColor(@ColorRes color:Int) :Int {
           return binding.root.context.getColor(color)
        }
    }
}