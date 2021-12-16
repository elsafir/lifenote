package com.ppb.lifenote.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ppb.lifenote.R;
import com.ppb.lifenote.dataclass.UserClass;

import java.util.ArrayList;

public class ProfileAdapter  extends RecyclerView.Adapter<ProfileAdapter.MyViewHolder> {

    Context context;
    ArrayList<UserClass> list;

    public ProfileAdapter(Context context, ArrayList<UserClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.temp_profile, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        UserClass user = list.get(position);

        holder.nama.setText(user.getEtNamaLengkap());
        holder.email.setText(user.getEtMail());
        holder.notelp.setText(user.getEtNoHp());
        holder.tglLahir.setText(user.getEtTglLahir());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nama, email, notelp, tglLahir;

        public MyViewHolder(View itemView){
            super(itemView);

            nama = itemView.findViewById(R.id.tv_temp_name);
            email = itemView.findViewById(R.id.tv_email);
            notelp = itemView.findViewById(R.id.tv_no_hp);
            tglLahir = itemView.findViewById(R.id.tv_tgl_lahir);
        }
    }

}
