package com.ppb.lifenote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Barang> list;

    public MyAdapter(Context context, ArrayList<Barang> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.view_design, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Barang user = list.get(position);

        holder.keterangan.setText("Keterangan :"+user.getKeterangan());
        holder.namabarang.setText("Nama Barang :"+user.getNamabarang());
        holder.pemasukan.setText("Pemasukan :Rp. "+user.getPemasukan());
        holder.pengeluaran.setText("Pengeluaran :Rp. "+user.getPengeluaran());
        holder.tanggal.setText("Tanggal :"+user.getTanggal());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tanggal, namabarang, pengeluaran, pemasukan, keterangan;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            tanggal = itemView.findViewById(R.id.tv_tanggal);
            namabarang = itemView.findViewById(R.id.tv_namabarang);
            pengeluaran = itemView.findViewById(R.id.tv_pengeluaran);
            pemasukan = itemView.findViewById(R.id.tv_pemasukan);
            keterangan = itemView.findViewById(R.id.tv_keterangan);
        };
    }
}
