package grails.tado.training

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

        redirect controller: 'mainTado', action: 'index'
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
