package ua.rogdan.trag.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import ua.rogdan.trag.R;
import ua.rogdan.trag.data.task.Task;
import ua.rogdan.trag.data.user.User;
import ua.rogdan.trag.tools.SuffixFormatter;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>{
    private List<Task> taskList;
    private Context context;
    private SimpleDateFormat outputFormat;
    private ItemClickListener<Task> clickListener;

    public TaskListAdapter(Context context) {
        this.taskList = new ArrayList<>();
        this.context = context;
        this.outputFormat = new SimpleDateFormat(OUTPUT_TIME_FORMAT, Locale.getDefault());
    }

    public void setItems(List<Task> taskList) {
        this.taskList = taskList;
        notifyDataSetChanged();
    }

    public void setClickListener(ItemClickListener<Task> clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);

        String kmFormat = context.getString(R.string.km_format);
        String km = String.format(kmFormat, task.getTravelKM());
        String stations = SuffixFormatter.formatWithSuffix(task.getTaskPoints().size(), context.getResources().getStringArray(R.array.station_format));
        String result = km + ", " + stations;
        holder.distanceTV.setText(result);

        String timeString = SuffixFormatter.formatMinutes(task.getTravelTime(), context);
        String timeFormat = context.getString(R.string.approximately_format);
        holder.timeTV.setText(String.format(timeFormat, timeString));

        Date date = new Date();
        date.setTime(date.getTime() + new Random().nextInt(1000000000) + 100000);
        String finishTill = outputFormat.format(date);
        String finishTillFormat = context.getString(R.string.finish_to_format);
        holder.deadlineTV.setText(String.format(finishTillFormat, finishTill));

        int taskNumber = task.getId();
        String numberFormat = context.getString(R.string.task_number_format);
        holder.taskNumberTV.setText(String.format(numberFormat, taskNumber));

        User customer = task.getCustomer();
        String customerFormat = context.getString(R.string.customer_format);
        holder.customerTV.setText(String.format(customerFormat, customer.getName()));

        holder.itemView.setOnClickListener(view -> {
            if (clickListener != null) {
                int pos = holder.getAdapterPosition();
                Task item = taskList.get(pos);
                clickListener.onItemSelected(item, pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.distance_tv)
        public TextView distanceTV;
        @BindView(R.id.time_tv)
        protected TextView timeTV;
        @BindView(R.id.deadline_tv)
        protected TextView deadlineTV;
        @BindView(R.id.customer_tv)
        protected TextView customerTV;
        @BindView(R.id.task_number_tv)
        protected TextView taskNumberTV;

        public TaskViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static final String OUTPUT_TIME_FORMAT = "dd.MM hh:mm";
}
