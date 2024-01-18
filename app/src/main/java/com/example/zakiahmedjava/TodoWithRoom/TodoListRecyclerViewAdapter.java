package com.example.zakiahmedjava.TodoWithRoom;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.zakiahmedjava.R;
import com.example.zakiahmedjava.RecyclerViewInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Handler;


public class TodoListRecyclerViewAdapter extends RecyclerView.Adapter<TodoListRecyclerViewAdapter.MyViewHolder> {
    protected Context context;
    protected List<TodoEntity> todoList;
    protected RecyclerViewInterface recyclerViewInterface;
    protected TodoDatabase todoDatabase;

    public TodoListRecyclerViewAdapter(Context context, List<TodoEntity> todoList, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.todoList = todoList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        todoDatabase = Room.databaseBuilder(context, TodoDatabase.class, "Todos")
                .build();
        View view = LayoutInflater.from(context).inflate(R.layout.todo_item_layout, parent, false);
        return new MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvTitle.setText(todoList.get(position).getTodoHeadline());
        holder.tvDescription.setText(todoList.get(position).getTodoNote());
        // first removing from list if the check box is selected.
        // Check for th check box and then update as it is.


        holder.cbIsCompleted.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Log.d("position", "ID: " +todoList.get(position).getTodoId());
                updateListInDatabase(todoList.get(position).getTodoId() , isChecked);
            } else
                Toast.makeText(context, "Not checked", Toast.LENGTH_SHORT).show();
        });


    }


    private void updateListInDatabase(int itemID, boolean isChecked) {
        Log.d("position", " " + itemID);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // Background task
                todoDatabase.getTodoDAO().updateList(itemID, isChecked);
            }
        });
        Toast.makeText(context, "update Done", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDescription;
        CheckBox cbIsCompleted;

        public MyViewHolder(View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTodoTitle);
            tvDescription = itemView.findViewById(R.id.tvTodoDescription);
            cbIsCompleted = itemView.findViewById(R.id.cbIsCompleted);

        }
    }

}
