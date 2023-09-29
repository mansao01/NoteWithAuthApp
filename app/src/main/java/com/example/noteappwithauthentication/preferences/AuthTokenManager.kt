package com.example.noteappwithauthentication.preferences

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

class AuthTokenManager(private val dataStore: DataStore<Preferences>) {
    private val accessTokenKey = stringPreferencesKey("access_token")
    private val refreshTokenKey = stringPreferencesKey("refresh_token")
    private val isLoginState = booleanPreferencesKey("is_login")

    val token: Flow<String> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e("AuthTokenManager", "Error reading preferences", it)
                emit(emptyPreferences())
            } else throw it
        }.map { preferences ->
            preferences[accessTokenKey] ?: "null"

        }

    suspend fun saveAccessToken(token: String) {
        dataStore.edit { preferences ->
            preferences[accessTokenKey] = token
        }
    }

    suspend fun saveRefreshToken(token: String) {
        dataStore.edit { preferences ->
            preferences[refreshTokenKey] = token
        }
    }

    suspend fun saveIsLoginState(isLogin: Boolean) {
        dataStore.edit { preferences ->
            preferences[isLoginState] = isLogin
        }
    }


    fun getIsLoginState(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val loginState = preferences[isLoginState] ?: false
                loginState
            }
    }


    suspend fun getRefreshToken(): String? {
        val preferences = dataStore.data.first()
        return preferences[refreshTokenKey]
    }

    suspend fun getAccessToken(): String? {
        val preferences = dataStore.data.first()
        return preferences[accessTokenKey]
    }

    fun getAccessTokenFlow(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[accessTokenKey] ?: "null"
        }
    }

    suspend fun clearTokens() {
        dataStore.edit { preferences ->
            preferences.remove(accessTokenKey)
            preferences.remove(refreshTokenKey)
        }
    }
}