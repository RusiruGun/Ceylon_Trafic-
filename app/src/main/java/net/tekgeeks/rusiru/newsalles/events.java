package net.tekgeeks.rusiru.newsalles;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class events extends AppCompatActivity {

    static final int DIALOG_ID=0;
    static final int dDialog_ID=1;
    int hour_X;
    int minute_X;
    int year_X;
    int month_X;
    int day_X;



    TextView mDate,mTime;
    EditText mDes;
    EditText mTitle;
    Button mAdd;
    AlertDialog.Builder builder;
    private String userid;
    private String CurrentDate;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        Intent intent = getIntent();

        userid =intent.getStringExtra("userid");


        setContentView(R.layout.activity_events);
        mDate=(TextView) findViewById(R.id.app_date);
        mTime=(TextView) findViewById(R.id.time);
        mTitle=(EditText) findViewById(R.id.app_title);
        mDes=(EditText) findViewById(R.id.description);
        mAdd=(Button) findViewById(R.id.add);
        showTimePickerDialog();
        showDateDialogButtonClick();
        final Calendar cal=Calendar.getInstance();
        year_X=cal.get(Calendar.YEAR);
        month_X=cal.get(Calendar.MONTH);
        day_X=cal.get(Calendar.DATE);
        CurrentDate=year_X+"-"+month_X+"-"+day_X;



        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mTitle.getText().toString().equals("")||mDes.getText().toString().equals("")||mTime.getText().toString().equals("")){

                    Toast.makeText(events.this, "Please fill all the fields",Toast.LENGTH_SHORT).show();
                }else {

                    Button cl= (Button) findViewById(R.id.btnGoCalendar);
                    String date_time= mDate.getText().toString()+" "+mTime.getText().toString();
                    cl.setText(date_time);
                    BackgroundTask backgroundTask=new BackgroundTask(events.this);
                    backgroundTask.execute("appointmentInsert",userid.toString(),date_time.toString(),mTitle.getText().toString(),mDes.getText().toString(),CurrentDate.toString());


                }
            }
        });




    }
    @Override
    protected Dialog onCreateDialog(int id){
        if(id==DIALOG_ID) {
            return new TimePickerDialog(events.this, kTimePickerListner, hour_X, minute_X, false);
        }
        else if(id==dDialog_ID) {
            return new DatePickerDialog(this, dpickerListner, year_X, month_X, day_X);
        }
        return null;
    }



    private DatePickerDialog.OnDateSetListener dpickerListner
            =new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            year_X=year;
            month_X=month+1;
            day_X=day;
            mDate.setText(year_X+"-"+month_X+"-"+day_X);
        }
    };
    protected TimePickerDialog.OnTimeSetListener kTimePickerListner=
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hourOfday, int minuths) {
                         hour_X= hourOfday;
                         minute_X=minuths;
                    mTime.setText(hour_X+":"+minute_X);



                }
            };
    public void showTimePickerDialog(){

        mTime.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialog(DIALOG_ID);
                    }
                }
        );
    }
 public void showDateDialogButtonClick(){
     mDate.setOnClickListener(
             new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                      showDialog(dDialog_ID);
                 }
             }
     );
 }



}
