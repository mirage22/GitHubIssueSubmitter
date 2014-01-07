package grails.miko.training

import javax.net.ssl.X509TrustManager
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection
import static groovyx.net.http.ContentType.JSON

import groovyx.net.http.RESTClient
import org.apache.http.protocol.HttpContext
import org.apache.http.HttpResponse
import org.apache.http.HttpResponseInterceptor
import org.apache.http.HttpRequest
import org.apache.http.HttpRequestInterceptor

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

class IssueController {

    private static String GIT_HUB_API_URL = "https://api.github.com/repos/mirage22/GitHubIssueSubmitter/issues"
    private static String GIT_HUB_API_USER = "mirage22"
    private static String GIT_HUB_API_PASSWORD = ""  // Your Password

    def index() {

    }

    def restClient

    //Controller which create Issue on GitHub over API
    def createIssue(){
        def title = params?.hiddenTitle
        def body = params?.hiddenBody

        // POST ISSUE With Description
        restClient =  getRestClient()
        createNewIssue(title, body)

        redirect controller: 'mainMiko', action: 'index'
    }

    def createNewIssue(title, description){
        doPostRequest(
                GIT_HUB_API_URL,
                [title: title, body: description]
        )
    }

    private doPostRequest(path, data=[:]){
       restClient.post(path: path, body: data).data
    }

    private getRestClient(){
        new RESTClient(GIT_HUB_API_URL).with{
            contentType = JSON
            handler.failure = { resp ->
                throw new RuntimeException("Got an error while connection to GitHub with the URL $path: ${resp.statusLine}")
            }
            client.addResponseInterceptor(
                    [process: { HttpResponse response, HttpContext context ->
                        response.removeHeaders('Set-Cookie') // httpclient can't parse this, so remove it
                    }] as HttpResponseInterceptor,0
            )
            client.addRequestInterceptor(
                    [process: { HttpRequest request, HttpContext context ->
                        request.setHeader("Authorization", "Basic $basicAuth")
                    }] as HttpRequestInterceptor

            )
            delegate
        }
    }

    private getBasicAuth(username = grailsApplication.config.github.user,
        password = grailsApplication.config.github.password)
        {
            "${GIT_HUB_API_USER}:${GIT_HUB_API_PASSWORD}".toString().bytes.encodeBase64()
        }


}
