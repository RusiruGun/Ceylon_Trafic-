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

public class adapterUser extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<UserData> data= Collections.emptyList();
    UserData current;
    int currentPos=0;

    // create constructor to innitilize context and data sent from MainActivity
    public adapterUser(Context context, List<UserData> data){
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

        myHolder.mUserName.setText(current.User_Phone);
        myHolder.mUserPhone.setText(current.User_Phone);
        myHolder.mUserRegisterNumberc.setText(current.Reg_Number);
        // myHolder.Saller_Image.setText(curre
        // load image into imageview using glident.S_R_Image);

        Glide.with(context).load("http://169.254.80.80/loginapp/images/users/"+current.Pro_picture)
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



        TextView mUserName;
        ImageView Saller_Image;
        TextView mUserPhone;
        TextView  mUserRegisterNumberc;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
           // mPropic=(ImageView) itemView.findViewById(R.id.propic);
            mUserName=(TextView) itemView.findViewById(R.id.userName);
            mUserPhone=(TextView) itemView.findViewById(R.id.userPhone);
            mUserRegisterNumberc=(TextView) itemView.findViewById(R.id.Reg_NO);
        }

    }

}