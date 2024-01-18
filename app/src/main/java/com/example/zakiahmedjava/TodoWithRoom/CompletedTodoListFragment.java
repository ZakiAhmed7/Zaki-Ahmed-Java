package com.example.zakiahmedjava.TodoWithRoom;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.zakiahmedjava.R;
import com.example.zakiahmedjava.RecyclerViewInterface;
import com.example.zakiahmedjava.SwipeLeftToDeleteCallBack;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletedTodoListFragment extends Fragment implements RecyclerViewInterface {
    protected RecyclerView recyclerView;
    protected CompletedTodoAdapter todoAdapter;
    protected List<TodoEntity> completedList;
    protected TodoDatabase todoDatabase;
    protected ImageView ivCurrentTask, ivCompletedTask;
    protected ConstraintLayout constraintLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialization
        completedList = new ArrayList<>();
        RoomDatabase.Callback callback = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
            }

            @Override
            public void onDestructiveMigration(@NonNull SupportSQLiteDatabase db) {
                super.onDestructiveMigration(db);
            }
        };

        todoDatabase = Room.databaseBuilder(getContext(),
                TodoDatabase.class,
                "Todos")
                .addCallback(callback)
                .build();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.completed_todo_list_layout, container, false);

        recyclerView = view.findViewById(R.id.rvCompletedTodo);
        ivCurrentTask = view.findViewById(R.id.ivCCurrentTask);
        ivCompletedTask = view.findViewById(R.id.ivCCompletedTask);
        constraintLayout = view.findViewById(R.id.completedConstraintLayout);

        ivCurrentTask.setOnClickListener(v -> {
            replaceFragment(new MainTodoFragment());
        });

        ivCompletedTask.setOnClickListener(v -> {
            replaceFragment(new CompletedTodoListFragment());
        });

        getDataFromDB();
        enableSwipeToDeleteAndUndo();

        return view;
    }

    private void getDataFromDB() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                //Background task
                completedList = todoDatabase.getTodoDAO().getCompletedTodo();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // on finishing task
                        setAdapter();
                        Toast.makeText(getContext(), "Data updated", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void setAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        todoAdapter = new CompletedTodoAdapter(getContext(), completedList, this);
        todoAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(todoAdapter);
    }
    protected void replaceFragment(Fragment fragment) {
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.sideFrameLayout, fragment, null)
                .commit();
    }

    @Override
    public void onItemClick(int position) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                todoDatabase.getTodoDAO().deleteTodo(completedList.get(position));

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "Delete Successful", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeLeftToDeleteCallBack swipeLeftToDeleteCallBack = new SwipeLeftToDeleteCallBack(getContext()) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                final TodoEntity item = completedList.get(position);

                todoAdapter.notifyItemRemoved(position);

                Snackbar snackbar = Snackbar.make(constraintLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeLeftToDeleteCallBack);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

}
