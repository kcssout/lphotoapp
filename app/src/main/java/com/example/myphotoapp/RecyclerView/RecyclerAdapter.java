package com.example.myphotoapp.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myphotoapp.DB.DbOpenHelper;
import com.example.myphotoapp.R;
import com.example.myphotoapp.User;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RcViewHolder> {

    RcViewHolder viewHolder;
    DbOpenHelper dbopen;
    public Context mContext;
    public ArrayList<User> users;

    public RecyclerAdapter(Context mCon){
        this.mContext = mCon;
    }

    public void setItem(ArrayList<User> aa){
        this.users = aa;
    }

    @NonNull
    @Override
    public RcViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_layout, parent, false);
        viewHolder = new RcViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RcViewHolder holder, int position) {


        holder.textArtist.setText(users.get(position).getArtist());
        holder.textTitle.setText(users.get(position).getTitle());
//        holder.img.setImageBitmap();
    }

    @Override
    public int getItemCount() {
        if(users == null){
            return 0;
        }
        return users.size();
    }

    public ArrayList<User> UserDb(){
        ArrayList<User> userlist = null;
        dbopen = new DbOpenHelper(mContext);
        dbopen.open();
        dbopen.read();
        dbopen.close();

        return userlist;
    }



    public class RcViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        public TextView textTitle, textArtist;

        public RcViewHolder(View view) {
            super(view);
            this.img = view.findViewById(R.id.img);
            this.textTitle = view.findViewById(R.id.textTitle);
            this.textArtist = view.findViewById(R.id.textArtist);


        }
    }
}
