package com.example.nt118project.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118project.response.UserRoles;
import com.example.nt118project.R;

import java.util.List;

public class UserRolesAdapter extends RecyclerView.Adapter<UserRolesAdapter.UserRolesViewHolder> {
    private final List<UserRoles> listUserRoles;
    public UserRolesAdapter(List<UserRoles> listUserRoles) {
        this.listUserRoles = listUserRoles;
    }

    @NonNull
    @Override
    public UserRolesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_roles, parent, false);

        return new UserRolesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserRolesViewHolder holder, int position) {
        UserRoles  userRoles = listUserRoles.get(position);
                if(userRoles == null){
                    return;
                }

        // int: String.valueOf()
//                holder.id.setText(userRoles.getId());
                holder.name.setText(userRoles.getName());
                holder.description.setText(userRoles.getDescription());
//                holder.composite.setText(userRoles.getComposite());
//                holder.assigned.setText(userRoles.getAssigned());
    }

    @Override
    public int getItemCount() {
        if(listUserRoles != null)
            return listUserRoles.size();
        return 0;
    }

    public static class UserRolesViewHolder extends RecyclerView.ViewHolder{
        TextView name, description, assigned;

        public UserRolesViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.usroles_name);
            description = itemView.findViewById(R.id.usroles_description);
            assigned = itemView.findViewById(R.id.usroles_assigned);

        }
    }
}
