package com.natiqhaciyef.android_safe_mode_util.base.store

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class SecuredSharedPref {
    private var encryptedSharedPref: SharedPreferences? = null

    fun provideSharedPref(
        databaseName: String? = null,
        context: Context
    ): SharedPreferences {
        val masterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        if (encryptedSharedPref == null) {
            encryptedSharedPref = EncryptedSharedPreferences
                .create(
                    databaseName ?: DATABASE_NAME,
                    masterKey,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
                )
        }

        return encryptedSharedPref!!
    }

    fun getDb() = encryptedSharedPref

    companion object {
        private const val DATABASE_NAME = "secured_shared_pref"
    }
}