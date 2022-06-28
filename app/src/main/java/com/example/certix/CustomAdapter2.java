package com.example.certix;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomAdapter2 extends BaseAdapter {

    Context a;
    LayoutInflater inflater;
    ArrayList namaacara, tiket, totalharga, tiketstatus;

    public CustomAdapter2(Context a, ArrayList namaacara, ArrayList tiket, ArrayList totalharga, ArrayList tiketstatus){
        this.a = a;
        this.namaacara = namaacara;
        this.tiket = tiket;
        this.totalharga = totalharga;
        this.tiketstatus = tiketstatus;
        inflater = LayoutInflater.from(a);
    }

    @Override
    public int getCount() {
        return namaacara.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Log.d("URL","mlv2");
        view = inflater.inflate(R.layout.element2, null);

        TextView eventname = (TextView) view.findViewById(R.id.historyacara);
        TextView eventticket = (TextView) view.findViewById(R.id.historytiket);
        TextView eventprice = (TextView) view.findViewById(R.id.historyharga);
        TextView eventstatus = (TextView) view.findViewById(R.id.historystatus);

        eventname.setText(String.valueOf(namaacara.get(i)));
        eventticket.setText(String.valueOf("Qty: "+tiket.get(i)));
        eventprice.setText(String.valueOf("Price: Rp."+totalharga.get(i)));


        if(String.valueOf(tiketstatus.get(i)).equals("PENDING")){
            eventstatus.setTextColor(Color.RED);
            eventstatus.setText(String.valueOf("Status: "+tiketstatus.get(i)));
        }
        else if(String.valueOf(tiketstatus.get(i)).equals("BERHASIL")){
            eventstatus.setTextColor(Color.GREEN);
            eventstatus.setText(String.valueOf("Status: "+tiketstatus.get(i)));

        }

        return view;
    }
}
