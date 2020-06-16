package com.android.cagadroid.dummyia;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Anonymized
 * @version 0.1
 * @since 0.1
 */

// Adapter for the recycler view that lists the authenticatorIDs and confidences
public class Adapter extends RecyclerView.Adapter {

    public static final String TAG = "CAGA.DUMMYIA";

    private Context mContext;
    private LayoutInflater mInflater;

    public Adapter(Context context) {
        this.mContext = context;
        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = mInflater.inflate(R.layout.user_item_info, parent, false);
        return new Adapter.ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        Adapter.ViewHolder holder = (Adapter.ViewHolder) viewHolder;

        AuthItem item = Authentication.getInstance().getPosition(position);
        holder.userID.setText(item.getUserID().toString());
        holder.conf.setText(item.getConf().toString());

        holder.conf.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    double new_value;
                    try {
                        new_value = Double.parseDouble(((EditText) v).getText().toString());
                    } catch (Exception ex) {
                        new_value = 0.0;
                        ((EditText) v).setText(0.0 + "");
                    }
                    if(new_value > 1.0) {
                        new_value = 1.0;
                        ((EditText) v).setText(1.0 + "");
                    }
                    if(new_value < 0.0) {
                        new_value = 0.0;
                        ((EditText) v).setText(0.0 + "");
                    }
                    int uID = Authentication.getInstance().getPosition(position).getUserID();
                    Authentication.getInstance().setUserConfidence(uID, new_value);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return Authentication.getInstance().getNumberOfItems();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView userID;
        EditText conf;

        ViewHolder(View itemView) {
            super(itemView);
            userID = itemView.findViewById(R.id.user_id);
            conf = itemView.findViewById(R.id.confidence);
        }

    }
}
