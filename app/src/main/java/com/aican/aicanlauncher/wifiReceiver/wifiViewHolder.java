package com.aican.aicanlauncher.wifiReceiver;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aican.aicanlauncher.R;

public class wifiViewHolder extends RecyclerView.ViewHolder {
    public TextView ssid;

    public wifiViewHolder(@NonNull View itemView) {
        super(itemView);
        ssid = itemView.findViewById(R.id.ssid);
    }
}
