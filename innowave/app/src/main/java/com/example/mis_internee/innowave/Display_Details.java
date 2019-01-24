package com.example.mis_internee.innowave;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mis_internee.innowave.Adapters.Details_Adapter;
import com.example.mis_internee.innowave.Model.Details_Model;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class Display_Details extends AppCompatActivity {

    public String GET_SEARCH_STR;
    private RequestQueue requestQueue;
    private TextView result;
    Button btn;
    private ProgressDialog pg;
    private ArrayList<Details_Model> arrayList = new ArrayList<Details_Model>();
    Details_Adapter adapter;
    TextView mNameTv, mEmailTv;
    ImageView mAvatar;
    ListView lv;
    String a[] ={"A","b"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display__details);
        result=(TextView)findViewById(R.id.text);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mNameTv=(TextView)findViewById(R.id.name);
        mEmailTv=(TextView)findViewById(R.id.email);
        mAvatar=(ImageView) findViewById(R.id.avatar);
        lv=(ListView)findViewById(R.id.lv);
        GET_SEARCH_STR=getIntent().getExtras().getString("SEARCH_STR");
        getJSON("https://api.github.com/users/"+GET_SEARCH_STR);
        getJSON2("https://api.github.com/users/"+GET_SEARCH_STR+"/followers");
      }
    private void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pg = ProgressDialog.show(Display_Details.this, "Collecting Information", "Please Wait", false, false);


            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pg.dismiss();
//                getJSON2("https://api.github.com/users/"+GET_SEARCH_STR+"/followers");
//                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

                try {
                    if (s.equals("")) {

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.
                                Builder(Display_Details.this);
                        alertDialogBuilder.setTitle("Data Not Found");
                        alertDialogBuilder
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Display_Details.this.finish();
                                    }
                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                } catch (NullPointerException e) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.
                            Builder(Display_Details.this);
                    alertDialogBuilder.setTitle("Error...!!!");
                    alertDialogBuilder
                            .setMessage("No Data Found...!!!")
                            .setCancelable(false)
                            .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                    Display_Details.this.finish();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }


                try {

                    loadIntoListView(s);

                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    Log.e("ERROR", "Error pasting data " + e.toString());
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();


    }

    private void loadIntoListView(String json) throws JSONException {
        try {
            JSONObject object = new JSONObject(json);
                for (int i = 0; i < object.length(); i++) {
                try {

                    String name = object.getString("login");
                    String email = object.getString("email");
                    String avatar = object.getString("avatar_url");
                    if ( email.trim().equals("null")) {
                        mEmailTv.setText("No Email Found");

                    }else{
                        mEmailTv.setText(email);
                    }
                     if(name.trim().equals("null")){
                        mNameTv.setText("No Name Found");
                    }
                    else {
                         mNameTv.setText(name);
                     }
                        try {
                            if (avatar != null) {
                                Picasso.with(getApplicationContext())
                                        .load(avatar)
                                        .resize(100, 100).noFade().into(mAvatar);
                            } else {
                                mAvatar.setImageDrawable(getResources().getDrawable(R.drawable.noimg));
                            }
                        } catch (NullPointerException e) {
                            e.fillInStackTrace();
                        }


                } catch (JSONException e) {
                    Toast.makeText(this, "Data Fetching Error...!!!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }


        } catch (NullPointerException e) {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.
                    Builder(Display_Details.this);
            alertDialogBuilder.setTitle("Error");
            alertDialogBuilder
                    .setMessage("No Data Found..!!!")
                    .setCancelable(false)
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();


        }
    }
    private void getJSON2(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                pg = ProgressDialog.show(Display_Details.this, "Collecting Information", "Please Wait", false, false);


            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                pg.dismiss();
//                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    if (s.equals("")) {

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.
                                Builder(Display_Details.this);
                        alertDialogBuilder.setTitle("Data Not Found");
                        alertDialogBuilder
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Display_Details.this.finish();
                                    }
                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                } catch (NullPointerException e) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.
                            Builder(Display_Details.this);
                    alertDialogBuilder.setTitle(" Error...!!!");
                    alertDialogBuilder
                            .setMessage("No Data Found....!!!")
                            .setCancelable(false)
                            .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                    Display_Details.this.finish();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }


                try {

                    loadIntoListView2(s);

                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    Log.e("ERROR", "Error pasting data " + e.toString());
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();


    }

    private void loadIntoListView2(String json) throws JSONException {
        try {


            JSONArray jsonArray=new JSONArray(json);
            final String[] F_Username = new String[jsonArray.length()];
            final String[] F_Avatar = new String[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                final JSONObject obj;

                try {

                    obj = jsonArray.getJSONObject(i);
                    F_Username[i] =obj.getString("login");
                  F_Avatar[i] =obj.getString("avatar_url");
                    Details_Model model = new Details_Model( F_Username[i],F_Avatar[i]);
                    arrayList.add(model);

                } catch (JSONException e) {
                    Toast.makeText(this, "Data Fetching Error...!!!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }


            }

            adapter = new Details_Adapter(this, arrayList);
//
//            //bind the adapter to the listview
            lv.setAdapter(adapter);
//            Collections.sort(arrayList, new All_Transit_Assets_List.ListComparator());



//            lv.setListAdapter(adapter);
//        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, a);


        } catch (NullPointerException e) {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.
                    Builder(Display_Details.this);
            alertDialogBuilder.setTitle("Network Error");
            alertDialogBuilder
                    .setMessage("Please Check Network..!!!")
                    .setCancelable(false)
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();


        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}

