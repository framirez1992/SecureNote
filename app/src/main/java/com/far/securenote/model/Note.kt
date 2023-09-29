package com.far.securenote.model

import com.far.securenote.contants.Colors
import com.google.firebase.firestore.Exclude

data class Note(@Exclude var docRef:String? = null,val id:String="",var title:String="",var body:String="",var color:String= Colors.AMBER.name)