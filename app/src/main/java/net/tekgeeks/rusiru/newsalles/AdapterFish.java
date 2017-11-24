package net.tekgeeks.rusiru.newsalles;

import android.content.Context;
import android.content.Intent;
import android.content.SyncStats;
import android.media.Image;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.Collections;
import java.util.List;

public class AdapterFish extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private LayoutInflater inflater;
    List<DataFish> data= Collections.emptyList();
    DataFish current;
    int currentPos=0;
    Context ctx;

    // create constructor to innitilize context and data sent from MainActivity
    public AdapterFish(Context context, List<DataFish> data,Context ctx){


        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
        this.ctx=ctx;



    }



    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.event_lines, parent,false);
        MyHolder holder=new MyHolder(view);

        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder= (MyHolder) holder;
        current=data.get(position);
        myHolder.textFishName.setText(current.title);
        myHolder.textSize.setText(current.date);
       // myHolder.textType.setText(current.time);
        myHolder.textPrice.setText(current.description);
        myHolder.AppointmentID= current.appo_ID.toString();
        myHolder.User_ID=current.USERID.toString();






    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textFishName;
        TextView textSize;
      //  TextView textType;
        TextView textPrice;
        String AppointmentID;
        String User_ID;
        ImageView statusIcon;


        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
         textFishName= (TextView) itemView.findViewById(R.id.apo_title);

         textSize =    (TextView) itemView.findViewById(R.id.apo_date);
         //textType =    (TextView) itemView.findViewById(R.id.apo_time);
         textPrice =   (TextView) itemView.findViewById(R.id.apo_des);
         AppointmentID=(toString());
         User_ID=(toString());
            statusIcon=(ImageView) itemView.findViewById(R.id.sync_Status);


        }

        @Override
        public void onClick(View v) {

            Intent intent=new Intent(ctx,AppointmentEdit.class);
            intent.putExtra("title",textFishName.getText().toString());
            intent.putExtra("date",textSize.getText().toString());
            intent.putExtra("des",textPrice.getText().toString());
            intent.putExtra("Appo_ID",AppointmentID);
            intent.putExtra("user_id",User_ID);
             ctx.startActivity(intent);
        }
    }


}