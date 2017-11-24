package net.tekgeeks.rusiru.newsalles;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.Collections;
import java.util.List;

public class AdapterSaller extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<DataSaleller> data= Collections.emptyList();
    DataSaleller current;
    int currentPos=0;

    // create constructor to innitilize context and data sent from MainActivity
    public AdapterSaller(Context context, List<DataSaleller> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }



    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.sales_profiles, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder= (MyHolder) holder;
        current=data.get(position);

        myHolder.saller_Name.setText(current.S_R_name);
        myHolder.saller_Rank.setText(current.ID);
       // myHolder.Saller_Image.setText(current.S_R_Image);
  // load image into imageview using glide


        Glide.with(context).load("http://169.254.80.80/schedule/userspic/"+current.S_R_Image)
                   .placeholder(R.drawable.ic_arrow_up_gray)
                .error(R.drawable.ic_arrow_up_blue_soft)
                .into(myHolder. Saller_Image);

    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder{


        TextView saller_Rank;
        TextView saller_Name;
        ImageView Saller_Image;
        //TextView textPrice;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            saller_Name= (TextView) itemView.findViewById(R.id.saller_name);

            saller_Rank = (TextView) itemView.findViewById(R.id.saller_rank);

            Saller_Image=(ImageView) itemView.findViewById(R.id.saller_image);
            }

    }

}