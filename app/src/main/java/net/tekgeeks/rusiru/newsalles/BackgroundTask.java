package net.tekgeeks.rusiru.newsalles;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.EditText;
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
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Rusiru on 5/18/2017.
 */

public class BackgroundTask extends AsyncTask<String,Void,String> {
    String register_url="http://169.254.80.80/loginapp/register.php";

    String login_url="http://205.134.251.68/~ceylontrafficapp/schedule/login.php";


    String appointment_url="http://169.254.80.80/loginapp/appointment.php";
    String insertAppointment="http://205.134.251.68/~ceylontrafficapp/schedule/insertAppointment.php";

    String json_usrl= "http://169.254.80.80/loginapp/appointment.php";
    String DateClick= "http://169.254.80.80/loginapp/appointment_by_date.php";
    String UpdateAppointment="http://205.134.251.68/~ceylontrafficapp/schedule/updateAppointment.php";






    Context ctx;
    ProgressDialog progressDialog;
    Activity activity;
    AlertDialog.Builder builder;
    private String JSON_String;

    public BackgroundTask (Context  ctx){
         this.ctx=ctx;
         activity=(Activity)ctx;
    }

    @Override
    protected void onPreExecute() {
       builder=new AlertDialog.Builder(activity);
        progressDialog=new ProgressDialog(ctx);
        progressDialog.setTitle("please wait");
        progressDialog.setMessage("connecting to server");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();




    }


    @Override
    protected String doInBackground(String... params) {
        String method= params[0];
        if(method.equals("register")){
            try {
                URL url=new URL(register_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String name=params[1];
                String email=params[2];
                String password=params[3];
                String data= URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                             URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+
                             URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder=new StringBuilder();
                String line="";
                while((line=bufferedReader.readLine())!=null){
                    stringBuilder.append(line+"\n");


                }
                httpURLConnection.disconnect();
                Thread.sleep(3000);
                return  stringBuilder.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else if(method.equals("login")){

            try {
                URL url=new URL(login_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String email,password;
                email=params[1];
                password=params[2];

                String data= URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+
                            URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder=new StringBuilder();
                String line="";
                while((line=bufferedReader.readLine())!=null){
                    stringBuilder.append(line+"\n");


                }
                httpURLConnection.disconnect();
                Thread.sleep(3000);
                return  stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

      }else if(method.equals("appointmentInsert")) {
            try {
                URL url = new URL(insertAppointment);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String User_ID = params[1];
                String date_time = params[2];
                String Title = params[3];
                String desc = params[4];
                String CurrentDate = params[5];


                String data = URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(Title, "UTF-8") + "&" +
                        URLEncoder.encode("date_time", "UTF-8") + "=" + URLEncoder.encode(date_time, "UTF-8") + "&" +
                        URLEncoder.encode("des", "UTF-8") + "=" + URLEncoder.encode(desc, "UTF-8") + "&" +
                        URLEncoder.encode("userID", "UTF-8") + "=" + URLEncoder.encode(User_ID, "UTF-8") + "&" +
                        URLEncoder.encode("Currentdate", "UTF-8") + "=" + URLEncoder.encode(CurrentDate, "UTF-8");
                bufferedWriter.write(data);


                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();


                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");


                }
                httpURLConnection.disconnect();
                Thread.sleep(3000);
                return stringBuilder.toString().trim();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
            else if(method.equals("appointmentUpdate")){

                try {
                    URL url=new URL(UpdateAppointment);
                    HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream=httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                    String User_ID=params[1];
                    String date_time=params[2];
                    String Title=params[3];
                    String desc=params[4];
                    String CurrentDate=params[5];
                    String AppointmentID=params[6];


                    String data= URLEncoder.encode("title","UTF-8")+"="+URLEncoder.encode(Title,"UTF-8")+"&"+
                            URLEncoder.encode("date_time","UTF-8")+"="+URLEncoder.encode(date_time,"UTF-8")+"&"+
                            URLEncoder.encode("des","UTF-8")+"="+URLEncoder.encode(desc,"UTF-8")+"&"+
                            URLEncoder.encode("userID","UTF-8")+"="+URLEncoder.encode(User_ID,"UTF-8")+"&"+
                            URLEncoder.encode("Currentdate","UTF-8")+"="+URLEncoder.encode(CurrentDate,"UTF-8")+"&"+
                            URLEncoder.encode("appointment_id","UTF-8")+"="+URLEncoder.encode(AppointmentID,"UTF-8");
                    bufferedWriter.write(data);


                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();


                    InputStream inputStream=httpURLConnection.getInputStream();
                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder=new StringBuilder();
                    String line="";
                    while((line=bufferedReader.readLine())!=null){
                        stringBuilder.append(line+"\n");


                    }
                    httpURLConnection.disconnect();
                    Thread.sleep(3000);
                    return  stringBuilder.toString().trim();





                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        else if (method.equals("appo_read")){

            try {
                String json_String;

                URL url =new URL(json_usrl);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder=new StringBuilder();
                while((JSON_String=bufferedReader.readLine())!=null){
                    stringBuilder.append(JSON_String+"/n");

                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String json) {
        try {
            progressDialog.dismiss();
            JSONObject jsonObject=new JSONObject(json);
            JSONArray jsonArray=jsonObject.getJSONArray("server_response");
            JSONObject JO= jsonArray.getJSONObject(0);
            String code=JO.getString("code");

            String message=JO.getString("message");

            if(code.equals("login_true")){

                String USER_ID= JO.getString("userID");

                Intent intent=new Intent(activity,MainActivity.class);
                intent.putExtra("userid",USER_ID);
                activity.startActivity(intent);

            }else if(code.equals("login_false")){
                showDialog("Login Error",message,code);




            }else if(code.equals("appo_false")){
                showDialog("Adding Fialed",message,code);


            }else if(code.equals("appo_true")){
                showDialog(" Success",message,code);
            }else if(code.equals("update_false")){
                showDialog("Failed",message,code);
            }
            else if(code.equals("update_true")){
                showDialog("Success",message,code);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void showDialog (String title,String message,String code){
        builder.setTitle(title);
        if(code.equals("reg_true")||code.equals("reg_false")){
            builder.setMessage(message);
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    activity.finish();
                }
            });

        }
        else if(code.equals("login_false")){
            builder.setMessage(message);
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    EditText email,password;
                    email=(EditText) activity.findViewById(R.id.email);
                    password=(EditText) activity.findViewById(R.id.password);
                    email.setText("");
                    password.setText("");
                    dialogInterface.dismiss();
                }
            });



        }else if(code.equals("appo_false")){
            builder.setMessage(message);
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    TextView mDate=(TextView)  activity.findViewById(R.id.apo_date);
                    TextView mTime=(TextView)  activity.findViewById(R.id.apo_time);
                    EditText mTitle=(EditText) activity.findViewById(R.id.apo_title);
                    EditText mDes=(EditText)   activity.findViewById(R.id.apo_des);

                    mDate.setHint("Tap to Enter Date");
                    mTime.setHint("Tap to Enter Time");
                    mTitle.setHint("Enter Title ");
                    mDes.setHint("Enter Discription");
                }
            });
        }else if(code.equals("appo_true")){
            builder.setMessage(message);
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        }else if(code.equals("update_true")){
            builder.setMessage(message);
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                }
            });

        }else if(equals("update_false")){
            builder.setMessage(message);
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        }
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
}
