import javax.net.ssl.X509TrustManager
import javax.net.ssl.TrustManager
import javax.net.ssl.SSLContext
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection

class BootStrap {

    def init = { servletContext ->

        // MAYBE TO BootStrap Disable certificate verification
        def trustManagerMethods = [
                getAcceptedIssuers: { null },
                checkClientTrusted: {a, b ->  },
                checkServerTrusted: {a, b ->  }
        ]

        def hostnameVerifierMethods = [
                verify: {a, b -> true }
        ]

        def trustManager = ProxyGenerator.instantiateAggregate(trustManagerMethods, [X509TrustManager])
        TrustManager[] trustAllCerts = (TrustManager[]) [trustManager]

        // Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("SSL")

        def hostnameVerifier = ProxyGenerator.instantiateAggregate(hostnameVerifierMethods, [HostnameVerifier])
        HostnameVerifier hv = (HostnameVerifier) hostnameVerifier

        sc.init(null, trustAllCerts, new java.security.SecureRandom())

        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory())
        HttpsURLConnection.setDefaultHostnameVerifier(hv)

        // Make a request

    }
    def destroy = {
    }
}
