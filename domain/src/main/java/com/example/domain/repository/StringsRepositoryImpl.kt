package com.example.domain.repository

import android.content.Context
import com.example.domain.R
import javax.inject.Inject

class StringsRepositoryImpl  @Inject constructor(
    context: Context
) : StringsRepository {

    override val blankAmountMessage = context.getString(R.string.blank_amount_message)

    override val inNumericAmountMessage = context.getString(R.string.in_numeric_amount_message)

    override val zeroAmountMessage = context.getString(R.string.zero_amount_message)

    override val highAmountMessage = context.getString(R.string.high_amount_message)

    override val blankEmailMessage = context.getString(R.string.blank_email_message)

    override val invalidEmailMessage = context.getString(R.string.invalid_email_message)

    override val blankFullNameMessage = context.getString(R.string.blank_name_message)

    override val shortFullNameMessage = context.getString(R.string.short_name_message)

    override val blankPasswordMessage = context.getString(R.string.blank_password_message)

    override val shortPasswordMessage = context.getString(R.string.short_password_message)

    override val incorrectPasswordFormatExc = context.getString(R.string.exclamation_point)

    override val incorrectPasswordFormat =
        context.getString(R.string.incorrect_password_format_message)

    override val notMatchingPassword = context.getString(R.string.not_matching_password_message)

    override val blankPhoneMessage = context.getString(R.string.blank_phone_message)

    override val invalidPhone = context.getString(R.string.invalid_phone_message)

}