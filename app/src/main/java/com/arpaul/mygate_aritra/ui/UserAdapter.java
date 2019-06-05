package com.arpaul.mygate_aritra.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arpaul.mygate_aritra.R;
import com.arpaul.mygate_aritra.models.User;

import java.io.FileInputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {
    private Context context;
    private List<User> users;

    public class UserHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.civProfileImage)
        CircleImageView civProfileImage;
        @BindView(R.id.tvProfileName)
        TextView tvProfileName;
        @BindView(R.id.tvPasscode)
        TextView tvPasscode;


        public UserHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }

    public void refresh(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }


    public UserAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }

    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item, parent, false);

        return new UserHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserHolder holder, int position) {
        holder.tvProfileName.setText(users.get(position).getUserName());
        holder.tvPasscode.setText(users.get(position).getPasscode() + "");

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(users.get(position).getImagePath(), options);
        holder.civProfileImage.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
