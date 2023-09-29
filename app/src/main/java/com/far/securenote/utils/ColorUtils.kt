package com.far.securenote.utils

import androidx.annotation.ColorRes
import com.far.securenote.R
import com.far.securenote.contants.Colors

object ColorUtils {

    data class ColorCombination(@ColorRes val color1:Int,@ColorRes val color2:Int, @ColorRes val color3:Int)

    fun getColors(color:Colors):ColorCombination = when(color){
        Colors.AMBER-> ColorCombination(R.color.amber_title,R.color.amber_body,R.color.dark_gray)
        Colors.BLUE->ColorCombination(R.color.blue_title,R.color.blue_body,R.color.dark_gray)
        Colors.GREEN->ColorCombination(R.color.green_title,R.color.green_body,R.color.dark_gray)
        Colors.PINK->ColorCombination(R.color.pink_title,R.color.pink_body,R.color.dark_gray)
        Colors.PURPLE->ColorCombination(R.color.purple_title,R.color.purple_body,R.color.dark_gray)
        Colors.GRAY->ColorCombination(R.color.gray_title,R.color.gray_body,R.color.dark_gray)
        else->{
            ColorCombination(R.color.black_title,R.color.black_body,R.color.white)
        }
    }

    fun colorByName(colorName:String):Colors{
        return Colors.valueOf(colorName)
    }
}