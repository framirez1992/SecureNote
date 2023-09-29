package com.far.securenote.view.common

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.core.view.setPadding
import com.far.securenote.R
import com.far.securenote.databinding.RoundedImageButtonBinding

class CustomButton: RelativeLayout {

    private var icon:Drawable? = null
    private var iconColor=0
    private var iconBackground=0
    private var loading:Boolean = false
    private var buttonSize = ButtonSizes.MEDIUM
    private var buttonShape = ButtonShape.STANDARD
    private var customWith:Float = 0f
    private var customHeight:Float = 0f
    private var customCardRadius:Float = 0f
    private var customPadding:Float = 0f
    private  lateinit var _binding: RoundedImageButtonBinding

    constructor(context: Context):super(context){
        initView(context)
    }

    constructor(context: Context,attributeSet: AttributeSet):super(context,attributeSet){
        getXmlAttributes(context,attributeSet)
        initView(context)
    }
    constructor(context: Context,attributeSet: AttributeSet,defStyleAttr:Int):super(context,attributeSet,defStyleAttr){
        getXmlAttributes(context,attributeSet)
        initView(context)
    }

    private fun getXmlAttributes(context: Context,attributeSet: AttributeSet){

        var data = context?.obtainStyledAttributes(attributeSet,R.styleable.CustomButton)
        if(data != null){
            icon = data.getDrawable(R.styleable.CustomButton_icon)
            iconColor = data.getColor(R.styleable.CustomButton_iconColor,resources.getColor(R.color.black))
            iconBackground = data.getColor(R.styleable.CustomButton_iconBackground,resources.getColor(R.color.purple_500))
            loading = data.getBoolean(R.styleable.CustomButton_loading,false)

            buttonSize = when (data.getInt(R.styleable.CustomButton_size,0)){
                0-> ButtonSizes.MEDIUM
                else ->ButtonSizes.LARGE
            }
            buttonShape = when (data.getInt(R.styleable.CustomButton_shape,0)){
                0-> ButtonShape.STANDARD
                else -> ButtonShape.ROUNDED
            }


            if(buttonShape == ButtonShape.ROUNDED){
                //circular (same width and height)
                 customWith = resources.getDimension(if(buttonSize == ButtonSizes.MEDIUM) R.dimen.custom_button_shape_medium_rounded else R.dimen.custom_button_shape_large_rounded)
                 customHeight = customWith
                 customCardRadius = resources.getDimension(if(buttonSize == ButtonSizes.MEDIUM) R.dimen.custom_button_shape_medium_rounded_radius else R.dimen.custom_button_shape_large_rounded_radius)
            }else{
                customWith = resources.getDimension(if(buttonSize == ButtonSizes.MEDIUM) R.dimen.custom_button_size_medium_width else R.dimen.custom_button_size_large_width)
                customHeight = resources.getDimension(if(buttonSize == ButtonSizes.MEDIUM) R.dimen.custom_button_size_medium_height else R.dimen.custom_button_size_large_height)
                customCardRadius = resources.getDimension(if(buttonSize == ButtonSizes.MEDIUM) R.dimen.custom_button_size_medium_radius else R.dimen.custom_button_size_large_radius)
            }
            customPadding = resources.getDimension(if(buttonSize == ButtonSizes.MEDIUM) R.dimen.custom_button_size_medium_padding else R.dimen.custom_button_size_large_padding)

            data.recycle()
        }
    }


    private fun initView(context: Context){
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT)
        _binding = RoundedImageButtonBinding.inflate(LayoutInflater.from(context),this,true)
        _binding.root.setBackgroundColor(iconBackground)
        _binding.image.setImageDrawable(icon)
        _binding.image.setColorFilter(iconColor, android.graphics.PorterDuff.Mode.SRC_IN)
        _binding.pb.indeterminateTintList= ColorStateList.valueOf(iconColor)

        var layoutParams = LayoutParams(customWith.toInt(),customHeight.toInt())
        _binding.pb.layoutParams = layoutParams
        _binding.pb.setPadding(customPadding.toInt())

        _binding.image.layoutParams = layoutParams
        _binding.image.setPadding(customPadding.toInt())

        _binding.card.layoutParams = layoutParams
        _binding.card.radius = customCardRadius

        changeLoadingState()

        refreshDrawableState()

    }

    private fun changeLoadingState(){
        _binding.image.visibility = if(loading) GONE else VISIBLE
        _binding.pb.visibility = if(loading) VISIBLE else GONE
    }
    fun setLoading(loading:Boolean){
        this.loading = loading
        changeLoadingState()
        invalidate()
    }

    enum class ButtonSizes{
        MEDIUM,
        LARGE
    }
    enum class ButtonShape{
        STANDARD,
        ROUNDED
    }



}