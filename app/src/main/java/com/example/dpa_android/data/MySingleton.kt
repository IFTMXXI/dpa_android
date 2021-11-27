package com.example.dpa_android.data

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class MySingleton private constructor(private var ctx: Context) {
    private var requestQueue: RequestQueue?

    fun getRequestQueue(): RequestQueue? {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext())
        }
        return requestQueue
    }

    fun <T> addToRequestQueue(req: Request<T>?) {
        getRequestQueue()!!.add(req)
    }

    companion object {
        private var instancia: MySingleton? = null
        @Synchronized
        fun getInstance(context: Context): MySingleton? {
            if (instancia == null) {
                instancia = MySingleton(context)
            }
            return instancia
        }
    }

    init {
        requestQueue = getRequestQueue()
    }
}