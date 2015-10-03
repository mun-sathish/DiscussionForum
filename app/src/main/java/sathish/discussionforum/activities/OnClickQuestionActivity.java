package sathish.discussionforum.activities;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import sathish.discussionforum.R;
import sathish.discussionforum.dto_util.DiscussionConst;

public class OnClickQuestionActivity extends AppCompatActivity {

    String profileLink, questionLink;
    private Dialog dialog = null;
    WebView webView;
    private android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_click_question);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        profileLink = bundle.getString("profileLink");
        questionLink = bundle.getString("questionLink");

        webView = (WebView) findViewById(R.id.webView);
           optionLayout();
           dialog.hide();

        webView.loadUrl(questionLink);

    }

    public void options(View v)
    {
        dialog.show();
    }

    public void optionLayout()
    {
        if (dialog == null) {
            dialog = new Dialog(this, R.style.DialogSlideAnim);
        }
        dialog.setContentView(R.layout.options);
        initDialogView();


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialog.show();
        lp.dimAmount = 0.7f;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

    }


    Button qa, askQuestion, profile;

    private void initDialogView() {

        dialog.setCanceledOnTouchOutside(true);

        qa = (Button) dialog.findViewById(R.id.qa);
        askQuestion = (Button) dialog.findViewById(R.id.askQuestion);
        profile = (Button) dialog.findViewById(R.id.profile);


        qa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl(questionLink);
                dialog.hide();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl(profileLink);
                dialog.hide();
            }
        });

        askQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl(DiscussionConst.ASK_A_QUESTION);
                dialog.hide();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_on_click_question, menu);
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
}
