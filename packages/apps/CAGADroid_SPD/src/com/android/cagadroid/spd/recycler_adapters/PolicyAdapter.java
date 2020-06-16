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
import com.android.cagadroid.spd.db.basetables.PolicyTable;
import com.android.cagadroid.spd.policy.Manager;

/**
 * @author Anonymized
 * @version 0.1
 * @since 0.1
 */
public class PolicyAdapter extends RecyclerView.Adapter {
    public static final String TAG = "CAGA.SPD";

    private Context mContext;
    private LayoutInflater mInflater;
    private List<PolicyTable.PolicyRep> mItems;

    public PolicyAdapter(Context aContext) {
        this.mContext = aContext;
        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(Manager.getInstance().isInitialized) {
            mItems = Contract.Policy.INSTANCE.getAll(Manager.getInstance().mDB);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = mInflater.inflate(R.layout.policy_item_info, parent, false);
        return new PolicyAdapter.ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        PolicyAdapter.ViewHolder holder = (PolicyAdapter.ViewHolder) viewHolder;
        holder.action.setText(Contract.Action.INSTANCE.getNameById(Manager.getInstance().mDB, mItems.get(position).ActionID));
        holder.operation.setText(Contract.Operation.INSTANCE.getNameById(Manager.getInstance().mDB, mItems.get(position).OperationID));
        holder.domain.setText(Contract.Domain.INSTANCE.getNameById(Manager.getInstance().mDB, mItems.get(position).DomainID));
        holder.type.setText(Contract.Type.INSTANCE.getNameById(Manager.getInstance().mDB, mItems.get(position).TypeID));
        holder.role.setText(Contract.Role.INSTANCE.getNameById(Manager.getInstance().mDB, mItems.get(position).RoleID));
        holder.modality.setText(Contract.Modality.INSTANCE.getNameById(Manager.getInstance().mDB, mItems.get(position).ModalityID));
        holder.confidence.setText(mItems.get(position).Confidence + "");
    }

    @Override
    public int getItemCount() {
        if(mItems != null)
            return mItems.size();
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView action;
        TextView operation;
        TextView domain;
        TextView type;
        TextView role;
        TextView modality;
        TextView confidence;

        ViewHolder(View itemView) {
            super(itemView);
            action = itemView.findViewById(R.id.policy_action_value);
            operation = itemView.findViewById(R.id.policy_operation_value);
            domain = itemView.findViewById(R.id.policy_domain_value);
            type = itemView.findViewById(R.id.policy_type_value);
            role = itemView.findViewById(R.id.policy_role_value);
            modality = itemView.findViewById(R.id.policy_modality_value);
            confidence = itemView.findViewById(R.id.policy_confidence_value);
        }
    }
}
