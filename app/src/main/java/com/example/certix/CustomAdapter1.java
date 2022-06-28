package com.example.certix;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter1 extends BaseAdapter {

    Context a;
    LayoutInflater inflater;
    ArrayList acara, slot, harga;

    public CustomAdapter1(Context a, ArrayList acara, ArrayList slot, ArrayList harga){
        this.a = a;
        this.acara = acara;
        this.slot = slot;
        this.harga = harga;
        inflater = LayoutInflater.from(a);
    }

    @Override
    public int getCount() {
        return acara.size();
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
        view = inflater.inflate(R.layout.element, null);

        TextView event = (TextView) view.findViewById(R.id.tvacara);
        TextView price = (TextView) view.findViewById(R.id.tvharga);
        TextView slottiket = (TextView) view.findViewById(R.id.tvslot);

        event.setText(String.valueOf(acara.get(i)));
        price.setText(String.valueOf("Rp."+harga.get(i)));
        slottiket.setText(String.valueOf("Slot: "+slot.get(i)));
        return view;
    }
}
