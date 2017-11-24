package net.tekgeeks.rusiru.newsalles;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by User on 2/28/2017.
 */

public class Tab3Fragment extends Fragment {
    private static final String TAG = "Tab2Fragment";

    static final int DIALOG_ID=0;
    int year_X;
    int month_X;
    int day_X;


Button apooo,SelectDate;
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private RecyclerView mRVFishPrice;
    private AdapterFish mAdapter;


    private Button mAdd_appo;
    public String DateOn;
    UserData ds=new UserData();
    private Button mevent;
    public  String User__ID;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.tab3_fragment,container,false);

        mevent=(Button) view.findViewById(R.id.fab);

        Intent myIntent = getActivity().getIntent();
         User__ID=myIntent.getStringExtra("userid");

        mevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),events.class);


                intent.putExtra("userid", User__ID);
                startActivity(intent);
            }
        });




        CalendarView mcv=(CalendarView) view.findViewById(R.id.calendarView);


        mcv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int i1, int day) {

                int month=i1+1;

                DateOn= year+"-"+month+"-"+day;


                new AsyncFetch().execute("ClickDate",DateOn.toString(),User__ID.toString());

            }
        });





        return view;
    }
    public void ShowDiolog(){

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
                //url = new URL("http://169.254.80.80/schedule/userAppointmentByDate.php");
                url = new URL("http://205.134.251.68/~ceylontrafficapp/schedule/userAppointmentByDate.php");
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
          }
            try {

                String method= params[0];
                if(method.equals("ClickDate")) {
                    // Setup HttpURLConnection class to send and receive data from php and mysql
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);

                    conn.setReadTimeout(READ_TIMEOUT);
                    conn.setConnectTimeout(CONNECTION_TIMEOUT);
                    // conn.setRequestMethod("GET");

                    // setDoOutput to true as we recieve data from json file
                    conn.setDoOutput(true);
                    try {
                        OutputStream outputStream = conn.getOutputStream();
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                        String SelectDate, User_ID;
                        SelectDate = params[1];
                        User_ID = params[2];

                        String data = URLEncoder.encode("Select_Date", "UTF-8") + "=" + URLEncoder.encode(SelectDate, "UTF-8") + "&" +
                                URLEncoder.encode("User_ID", "UTF-8") + "=" + URLEncoder.encode(User_ID, "UTF-8");

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

                    }catch (IOException e){
                        Toast.makeText(getActivity(),"No connect to database ",Toast.LENGTH_SHORT).show();

                    }





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
            List<DataFish> data=new ArrayList<>();

            pdLoading.dismiss();
            try {

                JSONArray jArray = new JSONArray(result);

                // Extract data from json and store into ArrayList as class objects
                for(int i=0;i<jArray.length();i++){
                    JSONObject json_data = jArray.getJSONObject(i);
                    DataFish fishData = new DataFish();
                    fishData.title= json_data.getString("vTitle");
                    fishData.date= json_data.getString("dAppointmentDateTime");
                    //fishData.time= json_data.getString("apo_time");
                    fishData.description= json_data.getString("vSummary");
                    fishData.appo_ID= json_data.getString("id");
                    fishData.USERID= json_data.getString("iMEID");
                    data.add(fishData);
                }

                // Setup and Handover data to recyclerview
                mRVFishPrice = (RecyclerView) getView().findViewById(R.id.fishPriceList);
                mAdapter = new AdapterFish(getActivity(), data,getActivity());
                mRVFishPrice.setAdapter(mAdapter);
                mRVFishPrice.setLayoutManager(new LinearLayoutManager(getActivity()));

            } catch (JSONException e) {
                Toast.makeText(getActivity(), "No events for Show" , Toast.LENGTH_LONG).show();
            }

        }

    }


}