package com.far.securenote.viewmodel

import androidx.lifecycle.*
import com.far.securenote.model.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

class MainScreenViewModel(private var noteService:NoteService):ViewModel() {

    private var _state = MutableLiveData<MainScreenState>()
    val state:LiveData<MainScreenState> = _state

    init {
        _state.value = MainScreenState(false,"", emptyList(),null)
    }

    fun search(s:String){
        _state.value = _state.value?.copy(loading = true, search = s)
        viewModelScope.launch {
            try {
                withTimeout(10000){
                    var notes = noteService.getNotes(s.lowercase())
                    operationResult("00",notes)
                }
            }catch (timeout: TimeoutCancellationException){
                operationResult("01")
            }
            catch (ce:CancellationException){
                operationResult("90")
            }
            catch (ex:Exception){
                operationResult("99",ex.message!!)
            }
        }
    }


    private fun operationResult(code:String){
        operationResult(code,"", emptyList())
    }
    private fun operationResult(code:String, notes:List<Note>){
        operationResult(code,"",notes)
    }
    private fun operationResult(code:String,message:String){
        operationResult(code,message, emptyList())
    }
    private fun operationResult(code:String,message:String, notes:List<Note>){
        val operationResult= when(code){
            "00" -> OperationResult("00","success")
            "01" -> OperationResult("01","timeout")
            "90" -> OperationResult("90","cancelled")
            else -> OperationResult("99",message)
        }

        val mainScreenState = _state.value?.copy(loading = false,notes = notes, operationResult = operationResult)
        _state.postValue(mainScreenState!!)

    }

    class MainScreenViewModelFactory(ns: NoteService) : ViewModelProvider.Factory {
        private val noteService: NoteService

        init {
            noteService = ns
        }

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainScreenViewModel(noteService) as T
        }
    }

}