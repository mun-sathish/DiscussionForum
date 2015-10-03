package sathish.discussionforum.supportingfiles;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.HashMap;

/**
 * Extends a generic webView to execute an Oauth2 authentication. Returns a token and its associated parameters
 *
 * @see WebView
 */
public class AuthenticatingWebView {

    private final HashMap<String, String> authorizationReturnParameters = new HashMap<>();

    private final WebView webView;

    private final AuthenticatingWebViewCallbackMethods listener;


    public AuthenticatingWebView(final WebView webView, final AuthenticatingWebViewCallbackMethods listener) {
        this.webView = webView;
        this.listener = listener;
    }

    @SuppressLint("SetJavaScriptEnabled")
    public final void makeRequest(final String requestUrl) {


        webView.loadUrl("about:blank");
        webView.setVisibility(View.VISIBLE);


        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);


        webView.setWebViewClient(new MyCustomWebViewClient());


        webView.loadUrl(requestUrl);
    }


    private class MyCustomWebViewClient extends WebViewClient {


        @Override
        public boolean shouldOverrideUrlLoading(final WebView view, final String url) {

            /*
             * Is this the "redirect URI" that we are about to load? If so, parse it and don't load it. Parsing is based
             * on the # and the & characters, so make sure they are present before accepting this as a valid redirect
             * URI.
             */
            if (url.indexOf("https://foo.example.com/#") == 0 ) {

                parseRedirectURI(url);

                /*
                 * Clear the webView and hide it
                 */
                view.loadUrl("about:blank");
                webView.setVisibility(View.INVISIBLE);

                /*
                 * Display all the parameters returned with the token
                 */
                listener.getToken(authorizationReturnParameters);

                return true;

            } else {

                view.loadUrl(url);
                return true;
            }
        }

        /**
         * Callback fires when page starts to load. Used to start the Progress Dialog.
         *
         * @param view the webView referred to by this callback
         * @param url the URL that the webView started to load
         * @param favicon the favicon of the page being loaded
         */
        @Override
        public void onPageStarted(final WebView view, final String url, final Bitmap favicon) {
       //     listener.startProgressDialog();
        }

        /**
         * Callback fires when page finishes loading. We use it to turn off the Progress Dialog.
         *
         * @param view the webView referred to by this callback
         * @param url the URL that this page is loading
         */
        @Override
        public void onPageFinished(final WebView view, final String url) {
         //   listener.stopProgressDialog();
        }
    }

    /**
     * Parse a redirect url into its parameters. The string has the form
     * [redirectURI]#[param1]=[val1]&[param2]=[val2]...
     *
     * @param redirectUrl the redirect url to be parsed
     */
    private void parseRedirectURI(final String redirectUrl) {

        String[] params = redirectUrl.split("#");

        for (String parameter : params) {
            if (parameter.contains("=")) {
                authorizationReturnParameters.put(parameter.split("=")[0], parameter.split("=")[1]);
            } else {
                authorizationReturnParameters.put(parameter, "");
            }
        }
    }
}
