import javax.net.ssl.X509TrustManager
import javax.net.ssl.TrustManager
import javax.net.ssl.SSLContext
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection

/*
 * The MIT License
 *
 * Copyright 2013 Miroslav Kopecky.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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
