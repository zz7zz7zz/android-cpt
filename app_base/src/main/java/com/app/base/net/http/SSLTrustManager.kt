package cn.douyuu.base.biz.network

import android.annotation.SuppressLint
import java.security.cert.X509Certificate
import javax.net.ssl.X509TrustManager

class SSLTrustManager: X509TrustManager {

    @SuppressLint("TrustAllX509TrustManager")
    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        //do nothing
    }

    @SuppressLint("TrustAllX509TrustManager")
    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        //do nothing
    }

    override fun getAcceptedIssuers(): Array<X509Certificate> {
        //do nothing
        return arrayOf()
    }

}