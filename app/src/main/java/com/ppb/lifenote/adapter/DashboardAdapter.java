package com.ppb.lifenote.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ppb.lifenote.dataclass.Barang;
import com.ppb.lifenote.R;

import java.util.ArrayList;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.MyViewHolder> {

    Context context;
    ArrayList<Barang> list;
    int saldo;

    public DashboardAdapter(Context context, ArrayList<Barang> list) {
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

        holder.keterangan.setText(user.getKeterangan());
        holder.namabarang.setText(user.getNamabarang());
        holder.pemasukan.setText("Pemasukan \n"+user.getPemasukan());
        holder.pengeluaran.setText("Pengeluaran \n"+user.getPengeluaran());
        holder.tanggal.setText(user.getTanggal());

        int temp_saldo = Integer.parseInt(user.getPemasukan()) - Integer.parseInt(user.getPengeluaran());
        saldo += temp_saldo;
        holder.tvSaldoAkhir.setText("Saldo: \n"+saldo);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

//        saldo = (saldo)

        TextView tanggal, namabarang, pengeluaran, pemasukan, keterangan, tvSaldoAkhir;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            tvSaldoAkhir = itemView.findViewById(R.id.tv_saldo_akhir);
            tanggal = itemView.findViewById(R.id.tv_tanggal);
            namabarang = itemView.findViewById(R.id.tv_namabarang);
            pengeluaran = itemView.findViewById(R.id.tv_pengeluaran);
            pemasukan = itemView.findViewById(R.id.tv_pemasukan);
            keterangan = itemView.findViewById(R.id.tv_keterangan);
        };
    }
}
