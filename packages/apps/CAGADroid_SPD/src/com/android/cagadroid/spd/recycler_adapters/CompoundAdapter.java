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
import com.android.cagadroid.spd.db.basetables.CompoundTable;
import com.android.cagadroid.spd.policy.Manager;

/**
 * @author Anonymized
 * @version 0.1
 * @since 0.1
 */
public class CompoundAdapter extends RecyclerView.Adapter {

    public static final String TAG = "CAGA.SPD";

    private Context mContext;
    private LayoutInflater mInflater;
    private String mWhatToList;
    private List<CompoundTable.Assignment> mItems;

    public CompoundAdapter(Context aContext, String aWhatToList) {
        this.mContext = aContext;
        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mWhatToList = aWhatToList;
        if(Manager.getInstance().isInitialized) {
            if (mWhatToList.equals("Sub_Dom"))
                mItems = Contract.Subject_Domain.INSTANCE.getAll(Manager.getInstance().mDB);
            else if (mWhatToList.equals("Obj_Typ"))
                mItems = Contract.Object_Type.INSTANCE.getAll(Manager.getInstance().mDB);
            else if (mWhatToList.equals("Usr_Rol"))
                mItems = Contract.User_Role.INSTANCE.getAll(Manager.getInstance().mDB);
            else if (mWhatToList.equals("Auth_Mod"))
                mItems = Contract.Authenticator_Modality.INSTANCE.getAll(Manager.getInstance().mDB);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = mInflater.inflate(R.layout.compound_item_info, parent, false);
        return new CompoundAdapter.ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        CompoundAdapter.ViewHolder holder = (CompoundAdapter.ViewHolder) viewHolder;

        String individual = "";
        String group = "";
        if (mWhatToList.equals("Sub_Dom")) {
            individual = Contract.Subject.INSTANCE.getNameById(Manager.getInstance().mDB, mItems.get(position).individualId);
            group = Contract.Domain.INSTANCE.getNameById(Manager.getInstance().mDB, mItems.get(position).groupId);
        }
        else if (mWhatToList.equals("Obj_Typ")) {
            individual = Contract.Object.INSTANCE.getNameById(Manager.getInstance().mDB, mItems.get(position).individualId);
            group = Contract.Type.INSTANCE.getNameById(Manager.getInstance().mDB, mItems.get(position).groupId);
        }
        else if (mWhatToList.equals("Usr_Rol")) {
            individual = Contract.User.INSTANCE.getNameById(Manager.getInstance().mDB, mItems.get(position).individualId);
            group = Contract.Role.INSTANCE.getNameById(Manager.getInstance().mDB, mItems.get(position).groupId);
        }
        else if (mWhatToList.equals("Auth_Mod")) {
            individual = Contract.Authenticator.INSTANCE.getNameById(Manager.getInstance().mDB, mItems.get(position).individualId);
            group = Contract.Modality.INSTANCE.getNameById(Manager.getInstance().mDB, mItems.get(position).groupId);
        }

        holder.individual.setText(individual);
        holder.group.setText(group);
    }

    @Override
    public int getItemCount() {
        if(mItems != null)
            return mItems.size();
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView individual;
        TextView group;

        ViewHolder(View itemView) {
            super(itemView);
            individual = itemView.findViewById(R.id.compound_individual);
            group = itemView.findViewById(R.id.compound_group);
        }

    }
}
