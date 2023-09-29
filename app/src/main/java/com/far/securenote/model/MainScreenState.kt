package com.far.securenote.model

data class MainScreenState(var loading:Boolean,var search:String,var notes:List<Note>,var operationResult:OperationResult?)