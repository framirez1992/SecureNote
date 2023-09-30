package com.far.securenote.common

import android.net.Uri
import com.far.securenote.view.common.BaseActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

class FileManager(var activity:BaseActivity) {


     suspend fun readFileContent(fileUri:Uri):String{
       return withContext(Dispatchers.IO){
           // Read the file's contents.
           val inputStream =  activity.application.contentResolver.openInputStream(fileUri)
           val reader = BufferedReader(InputStreamReader(inputStream))
           return@withContext reader.readText()
        }

    }
}