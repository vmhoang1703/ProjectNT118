package com.example.nt118project.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.nt118project.R;

import java.util.List;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {
    private final LayoutInflater inflater;
    private final Context context;
    private final List<String> values;

    public CustomSpinnerAdapter(Context context, int resource, List<String> values) {
        super(context, resource, values);
        this.context = context;
        this.values = values;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
        TextView textView = view.findViewById(android.R.id.text1);

        if (position == 0) {
            // Set the default color for the hint item
            textView.setTextColor(Color.parseColor("#ACABAB"));
        } else {
            // Set the color for the selected item
            textView.setTextColor(Color.BLACK);
        }

        textView.setText(values.get(position));

        // Adjust the padding or margin for more spacing
        int padding = 24; // Set your desired padding
        textView.setPadding(padding, padding, padding, padding);

        return view;
    }
}

