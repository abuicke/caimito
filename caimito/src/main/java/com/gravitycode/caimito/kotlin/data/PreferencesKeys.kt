package com.gravitycode.caimito.kotlin.data

fun intPreferencesKey(name: Any, vararg names: Any) =
    androidx.datastore.preferences.core.intPreferencesKey(toString(name, *names))

fun doublePreferencesKey(name: Any, vararg names: Any) =
    androidx.datastore.preferences.core.doublePreferencesKey(toString(name, *names))

fun stringPreferencesKey(name: Any, vararg names: Any) =
    androidx.datastore.preferences.core.stringPreferencesKey(toString(name, *names))

fun booleanPreferencesKey(name: Any, vararg names: Any) =
    androidx.datastore.preferences.core.booleanPreferencesKey(toString(name, *names))

fun floatPreferencesKey(name: Any, vararg names: Any) =
    androidx.datastore.preferences.core.floatPreferencesKey(toString(name, *names))

fun longPreferencesKey(name: Any, vararg names: Any) =
    androidx.datastore.preferences.core.longPreferencesKey(toString(name, *names))

fun stringSetPreferencesKey(name: Any, vararg names: Any) =
    androidx.datastore.preferences.core.stringSetPreferencesKey(toString(name, *names))

private fun toString(any: Any, vararg anys: Any): String {
    val strBldr = StringBuilder(any.toString())
    for (a in anys) {
        strBldr.append(a.toString())
    }

    return strBldr.toString()
}