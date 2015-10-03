package sathish.discussionforum.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;

import sathish.discussionforum.AppController;
import sathish.discussionforum.R;
import sathish.discussionforum.dto_util.DiscussionConst;
import sathish.discussionforum.dto_util.ProgressDialogUtil;
import sathish.discussionforum.supportingfiles.AuthenticatingWebView;
import sathish.discussionforum.supportingfiles.AuthenticatingWebViewCallbackMethods;

public class AuthenticateActivity extends AppCompatActivity implements AuthenticatingWebViewCallbackMethods {

    private AuthenticatingWebView authenticatingWebView;
    private Context myContext;
    private CookieManager myCookieManager;
    private WebView webView;
    private String requestUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate);

        webView = (WebView) findViewById(R.id.authWebview);

        myCookieManager = CookieManager.getInstance();
        myCookieManager.setAcceptCookie(true);


        requestUrl = (new StringBuffer()).append(DiscussionConst.AUTH_URL)
                .append("?client_id=")
                .append(DiscussionConst.CLIENT_ID)
                .append("&redirect_uri=")
                .append(DiscussionConst.REDIRECT_URI)
                .append("&scope=")
                .append(DiscussionConst.SCOPES)
                .toString();


            authenticatingWebView = new AuthenticatingWebView(webView, this);
            authenticatingWebView.makeRequest(requestUrl);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_authenticate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void startProgressDialog() {
        ProgressDialogUtil.showDialog(myContext,"Authenticating... Please wait");
    }

    @Override
    public void stopProgressDialog() {
        ProgressDialogUtil.hidePDialog();
    }

    @Override
    public void getToken(HashMap<String, String> authorizationReturnParameters) {
        AppController.getInstance().setAccessToken(authorizationReturnParameters.get("access_token"));
        Intent intent = new Intent(this, BrowseQuestionsActivity.class);
        startActivity(intent);
        finish();
    }
}
