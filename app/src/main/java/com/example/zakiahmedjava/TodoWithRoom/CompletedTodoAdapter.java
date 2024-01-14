package com.example.zakiahmedjava.TodoWithRoom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zakiahmedjava.R;
import com.example.zakiahmedjava.RecyclerViewInterface;
import java.util.List;

public class CompletedTodoAdapter extends RecyclerView.Adapter<CompletedTodoAdapter.MyViewHolder> {
    protected Context context;
    protected List<TodoEntity> completedList;
    protected RecyclerViewInterface recyclerViewInterface;
    public CompletedTodoAdapter(Context context, List<TodoEntity> completedList, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.completedList = completedList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.todo_completed_item_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.title.setText(completedList.get(position).getTodoHeadline());
        holder.description.setText(completedList.get(position).getTodoNote());
    }

    @Override
    public int getItemCount() {
        return completedList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        protected TextView title, description;
        protected ImageView deleteIcon;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvCompletedTodoTitle);
            description = itemView.findViewById(R.id.tvCompletedTodoDescription);
            deleteIcon = itemView.findViewById(R.id.ivTodoDeleteButton);
        }
    }
}
