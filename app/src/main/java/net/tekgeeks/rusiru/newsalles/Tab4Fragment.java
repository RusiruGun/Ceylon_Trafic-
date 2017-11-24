package net.tekgeeks.rusiru.newsalles;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ClipDrawable;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import com.github.mikephil.charting.utils.ColorTemplate;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;


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

public class Tab4Fragment extends Fragment {
    private static final String TAG = "Tab1Fragment";

    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    public Spinner spinner;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.tab4_fragment,container,false);

        TabHost host = (TabHost) view.findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(R.id.tab1);
        spec.setIndicator("History");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Quick Order");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("Tab Three");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Detail Order");
        host.addTab(spec);

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


        }

        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your json file resides
                // Even you can make call to php file which returns json data
                url = new URL("http://169.254.80.80/schedule/CustomerListByName.php");

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


            final List<CustomerHistory> customerhistry=new ArrayList<>();

            ArrayList<String> worldlist = new ArrayList<String>();


            pdLoading.dismiss();
            try {

                JSONArray jArray = new JSONArray(result);

                // Extract data from json and store into ArrayList as class objects
                for(int i=0;i<jArray.length();i++){
                    JSONObject json_data = jArray.getJSONObject(i);
                    CustomerHistory ch=new CustomerHistory();

                    ch.CusName=json_data.optString("dOrdtotalSpot");
//                    ch.OrderNumber=json_data.optString("vOrderNumber");
                   ch.OrderStartDate=json_data.optString("dOrdPackageValue");
                    ch.OrdPackageValue=json_data.optString("dOrdRateCardDiscount");
                  ch.OrdRateCardDiscount=json_data.optString("dOrderStartDate");
                    customerhistry.add(ch);

                  worldlist.add(json_data.optString("vCusName"));

//                    TextView tx=(TextView) getView().findViewById(R.id.textView3);
//                    tx.setText(ch.CusName);

                    spinner = (Spinner) getActivity().findViewById(R.id.spCustomer);

                    ArrayAdapter<String> adapter;

                    adapter = new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_spinner_item, worldlist);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spinner.setAdapter(adapter);

                     spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                         @Override
                         public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            TextView tvOrderCode=(TextView) getActivity().findViewById(R.id.tvOrderNumber);
                            TextView tvAmount=(TextView) getActivity().findViewById(R.id.tvAmount);
                            TextView tvDiscount=(TextView) getActivity().findViewById(R.id.tvDiscount);
                            TextView tvDate=(TextView) getActivity().findViewById(R.id.tvDate);


                            //setup data....

                            tvAmount.setText("Package Value  :"+customerhistry.get(position).OrdPackageValue);
                            tvDiscount.setText("Order Rate Card Discount  :"+customerhistry.get(position).OrdRateCardDiscount);
                            tvDate.setText("Order Start Date  :"+customerhistry.get(position).OrderStartDate);

                         }

                         @Override
                         public void onNothingSelected(AdapterView<?> parent) {

                         }
                     });




                }

                // Setup and Handover data to recyclerview

            } catch (JSONException e) {
                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                //Toast.makeText(getActivity(), "Test Error", Toast.LENGTH_LONG).show();
            }

        }

    }


}
