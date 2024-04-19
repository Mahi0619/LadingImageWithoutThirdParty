package com.mrdev.androidtestassesment.data.network

import android.util.Log
import com.mrdev.androidtestassesment.base.LogoutInterface

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(private val logoutInterface: LogoutInterface?) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        try {
            if (response.code == 404 || response.code == 401) {
                Log.e("LogoutInterface", "True")
                logoutInterface?.logoutFromApp(true) // Notify the LogoutInterface
            } else {
                Log.e("LogoutInterface", "False")
            }
        }catch (e:Exception){
            Log.e("ExceptionFount","${e.message}")
        }finally {
            Log.e("LogoutInterface", "Finally")
        }
        return null // Return null to indicate that the authentication failed
    }
}
