package com.t2008m.android10;


import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.t2008m.android10.entity.User;
import com.t2008m.android10.util.ItemClickListener;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter {
    Activity activity;
    List<User> users;

    public UserAdapter(Activity activity, List<User> users) {
        this.activity = activity;
        this.users = users;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = activity.getLayoutInflater().inflate(R.layout.item_user, parent, false);

        return new UserHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        UserHolder vh = (UserHolder) holder;
        User model = users.get(position);

        vh.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
            Intent intent = new Intent(activity,EditActivity.class);
            intent.putExtra("data",model.getId());
                activity.startActivity(intent);
                Toast.makeText(activity, "Long Click: "+position, Toast.LENGTH_SHORT).show();
            }
        });

        vh.tvId.setText(model.getId() + " ");
        vh.tvUser.setText(model.getUsername());
        vh.tvGender.setText(model.getGender());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UserHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        private ItemClickListener itemClickListener;
        TextView tvUser, tvId, tvGender;

        public UserHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            tvUser = itemView.findViewById(R.id.tvUser);
            tvId = itemView.findViewById(R.id.tvId);
            tvGender = itemView.findViewById(R.id.tvGender);
        }
        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),false);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),true);
            return true;
        }
    }
}
