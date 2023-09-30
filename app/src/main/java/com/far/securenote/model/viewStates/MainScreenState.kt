package com.far.securenote.model.viewStates

import com.far.securenote.model.Note
import com.far.securenote.model.OperationResult

data class MainScreenState(var loading:Boolean, var search:String, var notes:List<Note>, var operationResult: OperationResult?)