package sathish.discussionforum.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sathish.discussionforum.AppController;
import sathish.discussionforum.R;
import sathish.discussionforum.adapter.BrowseQuestionsAdapter;
import sathish.discussionforum.adapter.BrowseQuestionsAdapter.onSendDataListener;
import sathish.discussionforum.dto_util.DiscussionConst;
import sathish.discussionforum.dto_util.DiscussionDTO;
import sathish.discussionforum.dto_util.ProgressDialogUtil;

public class BrowseQuestionsActivity extends AppCompatActivity implements onSendDataListener {

    public String SEARCH_STRING = "programming";
    public String URL_MAIN = DiscussionConst.SEARCH_URL + DiscussionConst.QUESTION_MARK
                            + "intitle" + DiscussionConst.EQUAL_TO + SEARCH_STRING
                            + "&site" + DiscussionConst.EQUAL_TO + DiscussionConst.SITE;


    Toolbar toolbar;
    private UltimateRecyclerView recyclerView;
    EditText search;

    Context myContext;

    BrowseQuestionsAdapter adapter;
    private List<DiscussionDTO> allRowItemList = new ArrayList<>();
    DiscussionDTO details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_questions);
        initLayout();
        initRecyclerView();
        ProgressDialogUtil.showDialog(myContext, "Loading...");
        populate();
    }
    private void initLayout()
    {//set up toolbar
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.toolbar_icon);
        search = (EditText) findViewById(R.id.edittext_search);
    }


    private void initRecyclerView() {
        recyclerView = (UltimateRecyclerView) findViewById(R.id.listView_contents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);
        adapter = new BrowseQuestionsAdapter(this, allRowItemList,this);
        //set adapter
        recyclerView.setAdapter(adapter);
    }


    private void populate()
    {
        allRowItemList.clear();
        adapter.notifyDataSetChanged();
        // Creating volley request obj
        JsonObjectRequest getItemDetailsReq = new JsonObjectRequest(Request.Method.GET,
                 URL_MAIN ,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {


                            JSONArray itemArray = jsonObject.getJSONArray("items");

                            for(int i=0; i< itemArray.length(); i++)
                            {
                                JSONObject sourceObj = itemArray.getJSONObject(i);
                                details = new DiscussionDTO();

                                if(sourceObj.has("view_count"))
                                details.setViewCount(sourceObj.getString("view_count"));
                                if(sourceObj.has("answer_count"))
                                details.setAnswerCount(sourceObj.getString("answer_count"));
                                if(sourceObj.has("question_id"))
                                details.setQuestionID(sourceObj.getString("question_id"));
                                if(sourceObj.has("link"))
                                details.setQuestionLink(sourceObj.getString("link"));
                                if(sourceObj.has("title"))
                                details.setQuestionTitle(sourceObj.getString("title"));
                                if(sourceObj.getJSONObject("owner").has("user_id"))
                                details.setUserID(sourceObj.getJSONObject("owner").getString("user_id"));
                                if(sourceObj.getJSONObject("owner").has("profile_image"))
                                details.setProfileImage(sourceObj.getJSONObject("owner").getString("profile_image"));
                                if(sourceObj.getJSONObject("owner").has("display_name"))
                                details.setDisplayName(sourceObj.getJSONObject("owner").getString("display_name"));
                                if(sourceObj.getJSONObject("owner").has("link"))
                                details.setProfileLink(sourceObj.getJSONObject("owner").getString("link"));
                                if(sourceObj.has("is_answered"))
                                details.setIsAnswered(sourceObj.getBoolean("is_answered"));

                                allRowItemList.add(details);
                                adapter.notifyDataSetChanged();

                            }


                        } catch (JSONException error) {
                            error.printStackTrace();
                            Toast.makeText(getBaseContext(), "Response Suceess, catch exception", Toast.LENGTH_LONG).show();
                        }ProgressDialogUtil.hidePDialog();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("PROFILE","ERROR RESPONSE");
                Toast.makeText(getBaseContext(), "Error Response" , Toast.LENGTH_LONG).show();
                ProgressDialogUtil.hidePDialog();
            }
        }){

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Content-Encoding", "application/gzip");
                return headers;
            }

        };
        Log.e("PROFILE", "GOING TO APPCONTROLLER STATEMENT");
        AppController.getInstance().addToRequestQueue(getItemDetailsReq);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_browse_questions, menu);
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
    public void contact_info(String profileLink, String questionLink) {


        Intent intent = new Intent(myContext, OnClickQuestionActivity.class);
        intent.putExtra("profileLink", profileLink);
        intent.putExtra("questionLink",questionLink);
        startActivity(intent);

    }

    public void search(View v) {
        if (!search.getText().toString().equals("")) {

            ProgressDialogUtil.showDialog(myContext,"Loading...");
            SEARCH_STRING = search.getText().toString();
            SEARCH_STRING = SEARCH_STRING.replace(" ", "\\\\");
            URL_MAIN = DiscussionConst.SEARCH_URL + DiscussionConst.QUESTION_MARK
                    + "intitle" + DiscussionConst.EQUAL_TO + SEARCH_STRING
                    + "&site" + DiscussionConst.EQUAL_TO + DiscussionConst.SITE;
            search.setText("");

            v = this.getCurrentFocus();
            if (v != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }

            populate();

        } else {
            Toast.makeText(getBaseContext(), "Field is empty", Toast.LENGTH_SHORT).show();
        }
    }
}
