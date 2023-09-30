package com.far.securenote.viewmodel

import androidx.lifecycle.*
import com.far.securenote.contants.Colors
import com.far.securenote.model.*
import com.far.securenote.model.services.NoteService
import com.far.securenote.model.viewStates.NoteAddState
import kotlinx.coroutines.*
import java.util.UUID


class NotesViewModel(private val noteService: NoteService, note:Note?): ViewModel() {

    private var _state = MutableLiveData<NoteAddState>()
    val state:LiveData<NoteAddState> = _state

    private var _currentNote = MutableLiveData<Note?>()
    val currentNote:LiveData<Note?> = _currentNote

    init {
        if(note != null){
            _currentNote.value = note
        }else{
            _currentNote.value = Note(docRef = null,id= UUID.randomUUID().toString(), title = "",body="",Colors.AMBER.name)
        }
        _state.value = NoteAddState(loading = false, operationResult = null)
    }

    fun changeColor(color:Colors){
        _currentNote.value = _currentNote.value?.copy(color = color.name)
    }

    fun updateNoteText(title: String,body: String){
        val cn = _currentNote.value?.copy(title=title, body = body)
        _currentNote.value = cn
    }



    fun saveNote(){
            _state.value= _state.value?.copy(loading = true, operationResult = null)
           viewModelScope.launch {
               try {
                   //10 seconds
                   withTimeout(10000){
                       noteService.insertNote(currentNote.value!!)
                       operationResult("00")
                   }
               } catch (timeout:TimeoutCancellationException){
                   operationResult("01")
               }
               catch (ce:CancellationException){
                   operationResult("01")
               }
               catch (ex:Exception){
                   operationResult("99",ex.message!!)
               }

           }
    }

    fun updateNote(){
        _state.value= _state.value?.copy(loading = true, operationResult = null)
        viewModelScope.launch {
            try {
                //10 seconds
                withTimeout(10000) {
                    noteService.updateNote(_currentNote.value!!)
                    operationResult("00")
                }
            }catch (timeout:TimeoutCancellationException){
                operationResult("01")
            }
            catch (ce:CancellationException){
                operationResult("01")
            }
            catch (ex:Exception){
                operationResult("99",ex.message!!)
            }

        }
    }

    fun deleteNote(){
        _state.value= _state.value?.copy(loading = true, operationResult = null)
        viewModelScope.launch {
            try {
                //10 seconds
                withTimeout(10000) {
                    noteService.deleteNote(_currentNote.value!!)
                    operationResult("00")
                }
            }catch (timeout:TimeoutCancellationException){
                operationResult("01")
            }
            catch (ce:CancellationException){
                operationResult("01")
            }
            catch (ex:Exception){
                operationResult("99",ex.message!!)
            }

        }
    }


    private fun operationResult(code:String){
        operationResult(code,"")
    }
    private fun operationResult(code:String,message:String){
        val operationResult= when(code){
            "00" -> OperationResult("00","success")
            "01" -> OperationResult("01","success (LOCAL)")
            "90" -> OperationResult("90","cancelled")
            else -> OperationResult("99",message)
        }
        val newState: NoteAddState = _state.value!!.copy(loading = false,operationResult = operationResult)
        _state.postValue(newState)
    }




    class NoteAddViewModelFactory(ns: NoteService, note:Note?) : ViewModelProvider.Factory {
        private val noteService: NoteService
        private val currentNote:Note?

        init {
            noteService = ns
            currentNote = note
        }

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NotesViewModel(noteService,currentNote) as T
        }
    }

}