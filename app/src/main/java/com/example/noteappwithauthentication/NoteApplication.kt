package com.example.noteappwithauthentication

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.noteappwithauthentication.data.AppContainer
import com.example.noteappwithauthentication.data.DefaultAppContainer
import com.example.noteappwithauthentication.preferences.AuthTokenManager

private const val AUTH_PREF_NAME = "auth_pref"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = AUTH_PREF_NAME
)

class NoteApplication : Application() {
    lateinit var container: AppContainer
    lateinit var authTokenManager: AuthTokenManager

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
        authTokenManager = AuthTokenManager(dataStore)

    }
}