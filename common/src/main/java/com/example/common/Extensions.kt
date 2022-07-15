package com.example.common

import android.content.Context
import androidx.annotation.AttrRes
import androidx.core.content.res.getColorOrThrow
import androidx.core.content.withStyledAttributes
import androidx.core.util.PatternsCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LifecycleCoroutineScope
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

fun <T> Flow<T>.launchWhenStarted(lifeCycleScope: LifecycleCoroutineScope) = lifeCycleScope
    .launchWhenStarted {
        this@launchWhenStarted.collect()
    }

fun TextInputEditText.updateText(newText: String) {
    if (newText != text.toString()) {
        setText(newText)
        setSelection(length())
    }
}
fun Context.getColorByAttribute(@AttrRes attr: Int): Int {
    withStyledAttributes(0, intArrayOf(attr)) {
        return getColorOrThrow(0)
    }
    throw IllegalArgumentException()
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)

