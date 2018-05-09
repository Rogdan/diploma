package ua.rogdan.trag.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ua.rogdan.trag.R;
import ua.rogdan.trag.data.user.User;

public class EmployeesAdapter extends RecyclerView.Adapter<EmployeesAdapter.EmployeesViewHolder>{
    private ArrayList<User> itemsList;
    private Context context;

    public EmployeesAdapter(Context context) {
        this.context = context;
        this.itemsList = new ArrayList<>();
    }

    public void setItemsList(ArrayList<User> itemsList) {
        this.itemsList = itemsList;
        notifyDataSetChanged();
    }

    @Override
    public EmployeesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EmployeesViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_preview, parent, false));
    }

    @Override
    public void onBindViewHolder(EmployeesViewHolder holder, int position) {
        User user = itemsList.get(position);

        holder.nameTV.setText(user.getName());
        holder.positionTV.setText(user.getPosition());
        Glide.with(context)
                .load(user.getPhotoURI())
                .apply(new RequestOptions()
                .placeholder(R.drawable.user)
                .transform(new CenterCrop()))
                .into(holder.photoIV);
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class EmployeesViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.photo_iv)
        protected ImageView photoIV;
        @BindView(R.id.name_tv)
        protected TextView nameTV;
        @BindView(R.id.position_tv)
        protected TextView positionTV;

        public EmployeesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
