package com.android.cagadroid.spd.recycler_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.cagadroid.spd.R;
import com.android.cagadroid.spd.db.Contract;
import com.android.cagadroid.spd.db.basetables.PrimitiveTable;
import com.android.cagadroid.spd.policy.Manager;

/**
 * @author Anonymized
 * @version 0.1
 * @since 0.1
 */
public class PrimitiveAdapter extends RecyclerView.Adapter {

    public static final String TAG = "CAGA.SPD";

    private Context mContext;
    private LayoutInflater mInflater;
    private String mWhatToList;
    private List<PrimitiveTable.Tuple> mItems;

    public PrimitiveAdapter(Context aContext, String aWhatToList) {
        this.mContext = aContext;
        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mWhatToList = aWhatToList;
        if(Manager.getInstance().isInitialized) {
            if (mWhatToList.equals("Actions"))
                mItems = Contract.Action.INSTANCE.getAll(Manager.getInstance().mDB);
            else if (mWhatToList.equals("Operations"))
                mItems = Contract.Operation.INSTANCE.getAll(Manager.getInstance().mDB);
            else if (mWhatToList.equals("Domains"))
                mItems = Contract.Domain.INSTANCE.getAll(Manager.getInstance().mDB);
            else if (mWhatToList.equals("Types"))
                mItems = Contract.Type.INSTANCE.getAll(Manager.getInstance().mDB);
            else if (mWhatToList.equals("Roles"))
                mItems = Contract.Role.INSTANCE.getAll(Manager.getInstance().mDB);
            else if (mWhatToList.equals("Modalities"))
                mItems = Contract.Modality.INSTANCE.getAll(Manager.getInstance().mDB);
            else if (mWhatToList.equals("Subjects"))
                mItems = Contract.Subject.INSTANCE.getAll(Manager.getInstance().mDB);
            else if (mWhatToList.equals("Objects"))
                mItems = Contract.Object.INSTANCE.getAll(Manager.getInstance().mDB);
            else if (mWhatToList.equals("Users"))
                mItems = Contract.User.INSTANCE.getAll(Manager.getInstance().mDB);
            else if (mWhatToList.equals("Authenticators"))
                mItems = Contract.Authenticator.INSTANCE.getAll(Manager.getInstance().mDB);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = mInflater.inflate(R.layout.primitive_item_info, parent, false);
        return new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.id.setText(mItems.get(position).id + "");
        holder.name.setText(mItems.get(position).name);
    }

    @Override
    public int getItemCount() {
        if(mItems != null)
            return mItems.size();
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView id;
        TextView name;

        ViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.primitive_item_id);
            name = itemView.findViewById(R.id.primitive_item_name);
        }

    }
}
