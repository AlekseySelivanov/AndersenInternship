package com.example.andersenhw.ui.home.cardView

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.example.andersenhw.R

class CustomCreditCard : LinearLayout {

    private lateinit var mainView: View

    var mTYPE = TYPE.RUBLE
    var backgroundSrc = ContextCompat.getDrawable(context, R.drawable.cardback)
    var mPercent = 5

    private lateinit var logo: AppCompatImageView
    private lateinit var gradient: AppCompatImageView
    private lateinit var cardAmount: AppCompatTextView
    private lateinit var percent: AppCompatTextView
    private lateinit var backgroundImage: AppCompatImageView

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        getAttrs(attrs)
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        getAttrs(attrs)
        init(context)
    }

    private fun init(context: Context) {

        mainView = View.inflate(context, R.layout.view_card, this)

        logo = findViewById(R.id.creditcard_logo)
        gradient = findViewById(R.id.gradient)
        cardAmount = findViewById(R.id.creditcard_card_number_label)
        backgroundImage = findViewById(R.id.background)
        percent = findViewById(R.id.creditcard_expiration_y_label)

        setType(mTYPE)
        backgroundSrc?.let { setSrc(it) }
        setPercentage(mPercent)
    }


    private fun getAttrs(attrs: AttributeSet?) {
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomCreditCard,
            0, 0
        )
        try {
            mTYPE = TYPE.fromId(a.getInt(R.styleable.CustomCreditCard_cardType, 0))
            backgroundSrc = a.getDrawable(R.styleable.CustomCreditCard_scrBackground)
            val tmpYear = a.getInteger(R.styleable.CustomCreditCard_percent, 5)
            mPercent = if (tmpYear in 0..100) tmpYear else 5
        } finally {
            a.recycle()
        }
    }

    fun setAmount(value: Int): CustomCreditCard =
        apply {
            if (value in 0..10000000) {
                cardAmount.text = value.toString()
            } else throw Exception("Слишком большая сумма")
        }

    fun setPercentage(value: Int): CustomCreditCard =
        apply {
            if (value in 0..100) {
                percent.text = value.toString()
            } else throw java.lang.Exception("Процент должен быть в диапазоне 0..100")
        }

    fun setSrc(drawable: Drawable): CustomCreditCard =
        apply {
            try {
                backgroundSrc = drawable
                backgroundImage.setImageDrawable(drawable)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    fun setType(type: TYPE): CustomCreditCard =
        apply {
            mTYPE = type
            when (type) {
                TYPE.RUBLE -> {
                    logo.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ruble))
                    gradient.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.cardback
                        )
                    )
                }
                TYPE.DOLLAR -> {
                    logo.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.dollar))
                    gradient.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.ruble_gradient
                        )
                    )
                }
                TYPE.EURO -> {
                    logo.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.euro))
                    gradient.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.euro_gradient
                        )
                    )
                }
            }
        }


    enum class TYPE(var type: Int) {
        RUBLE(0),
        DOLLAR(1),
        EURO(2);

        companion object {
            fun fromId(type: Int): TYPE {
                for (dec in values())
                    if (dec.type == type) return dec
                throw IllegalArgumentException()
            }
        }
    }
}