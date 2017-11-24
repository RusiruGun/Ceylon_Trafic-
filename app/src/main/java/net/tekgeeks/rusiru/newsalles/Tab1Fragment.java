package net.tekgeeks.rusiru.newsalles;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ClipDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 2/28/2017.
 */

public class Tab1Fragment extends Fragment {
    private static final String TAG = "Tab1Fragment";

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    private AdapterFish mAdapter;


    private ImageView mPropic;
    private TextView  mUserName;
    private TextView  mUserPhone;
    private TextView  mUserRegisterNumber;
    private ImageView mUserPhoto;

    private Context context;
    UserData current;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1_fragment,container,false);
        mUserName=(TextView)view.findViewById(R.id.userName);
        mUserPhone= (TextView) view.findViewById(R.id.userPhone);
        mUserRegisterNumber=(TextView) view.findViewById(R.id.Reg_NO);
        mUserPhoto=  (ImageView) view.findViewById(R.id.propic);

        Intent myIntent = getActivity().getIntent();
        String User__ID=myIntent.getStringExtra("userid");



        new AsyncFetch().execute("current_user",User__ID.toString());



        return view;
    }
    class AsyncFetch extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(getActivity());
        HttpURLConnection conn;
        URL url = null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your json file resides
                // Even you can make call to php file which returns json data
                url = new URL("http://205.134.251.68/~ceylontrafficapp/schedule/currentUser.php");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }

            try {

                String method= params[0];
                if(method.equals("current_user")) {
                    // Setup HttpURLConnection class to send and receive data from php and mysql
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);

                    conn.setReadTimeout(READ_TIMEOUT);
                    conn.setConnectTimeout(CONNECTION_TIMEOUT);
                    // conn.setRequestMethod("GET");

                    // setDoOutput to true as we recieve data from json file
                    conn.setDoOutput(true);
                    OutputStream outputStream = conn.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String  User_ID;
                    User_ID = params[1];

                    String data = URLEncoder.encode("User_ID", "UTF-8") + "=" + URLEncoder.encode(User_ID, "UTF-8");

                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();


                    InputStream inputStream = conn.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line + "\n");


                    }
                    conn.disconnect();
                    Thread.sleep(3000);
                    return stringBuilder.toString().trim();
                }

            } catch (IOException e1) {
                Toast.makeText(getActivity(),"Loading Error",Toast.LENGTH_SHORT).show();
                e1.printStackTrace();
                return e1.toString();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread

            pdLoading.dismiss();
            List<UserData> data=new ArrayList<>();

            pdLoading.dismiss();
            try {

                JSONArray jArray = new JSONArray(result);

                // Extract data from json and store into ArrayList as class objects
                for(int i=0;i<jArray.length();i++){
                    JSONObject json_data = jArray.getJSONObject(i);
                    UserData User_Data = new UserData();

                    User_Data.Name= json_data.getString("Name");
                    User_Data.User_Phone= json_data.getString("phone");
                    User_Data.Reg_Number= json_data.getString("regNo");
                    User_Data.Pro_picture=json_data.getString("ProfilePicture");
                    data.add(User_Data);

//                    Glide.with(context).load("http://169.254.80.80/schedule/userspic/"+User_Data.Pro_picture)
//                            .placeholder(R.drawable.ic_arrow_up_gray)
//                            .error(R.drawable.ic_arrow_up_blue_soft)
//                            .into(mUserPhoto);

                    Glide.with(getActivity())
                            .load("http://169.254.80.80/schedule/userspic/"+User_Data.Pro_picture)
                            .placeholder(R.drawable.ic_arrow_up_blue)
                            .into(mUserPhoto);
//
                    mUserName.setText(User_Data.Name);
                    mUserRegisterNumber.setText("Register Number :"+User_Data.Reg_Number);
                    mUserPhone.setText(User_Data.User_Phone);

                    //mUserPhoto.setImageURI(Uri.parse("http://169.254.80.80/schedule/userspic/"+User_Data.Pro_picture));

                }

                // Setup and Handover data to recyclerview

            } catch (JSONException e) {
                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
            }

        }

    }

    }




