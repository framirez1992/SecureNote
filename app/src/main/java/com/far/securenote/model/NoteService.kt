package com.far.securenote.model

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class NoteService(private val db:FirebaseFirestore) {

    fun insertNote(note:Note){//Save local until the device has an internet connection
        val notesRef = db.collection("notes")
        notesRef.document().set(note)
    }

    fun updateNote(note:Note){//Save local until the device has an internet connection
        val documentReference = db.document("notes/${note.docRef}")
        documentReference.update(
            mapOf(
                Note::title.name to note.title,
                Note::body.name to note.body,
                Note::color.name to note.color
        ))
    }

    fun deleteNote(note:Note){//Save local until the device has an internet connection
        val documentReference = db.document("notes/${note.docRef}")
        documentReference.delete()
    }


    suspend fun getNotes(search: String): List<Note> {//Try to search for transactions online, after a few seconds if you don't have internet, search locally
        var noteList = mutableListOf<Note>()
        val notesRef = db.collection("notes")

        //there is no 'like' fiter
        notesRef.get().await().documents.forEach{
            var n =it.toObject(Note::class.java)
            n?.docRef=it.id
            noteList.add(n!!)
        }
        //we filter titles here
        return noteList.filter { it.title.lowercase().contains(search) or it.body.lowercase().contains(search) }
    }
}