package com.example.zakiahmedjava.TodoWithRoom;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zakiahmedjava.R;
import com.example.zakiahmedjava.RecyclerViewInterface;
import com.example.zakiahmedjava.SideBarActivityHost;
import com.example.zakiahmedjava.databinding.FragmentMainTodoBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
1. Add the dependency
2. Making a data class or an entity class.
    @PrimaryKey (autogenerate = true) for id
    @Entity for at the top of the class.
3. Create interface TodoDao to implement methods Dao
    1. Insert or update the TodoItem by @Upsert above the function.
    2. suspend keyword for coroutines and asynchronous programming for the function how does not return an value.
    3. To delete a TodoItem by @Delete above the function
    4. getting the live updates that is a keyword called Flow which takes the list of model class. @Query ("SELECT * FROM table_name ORDER BY column_name ASC");

4. Creating an Abstract class which has to be inherit from the RoomDatabase()
    - @Database ( entities = [table-name.class] version = 1 )
    - initialize the Dao interface in the abstract class.


*/
public class MainTodoFragment extends Fragment implements RecyclerViewInterface {
    //    protected FragmentMainTodoBinding binding;
    protected RecyclerView recyclerView;
    private TodoDatabase todoDatabase;
    protected FloatingActionButton fab;
    protected Toolbar toolbar;
    protected List<TodoEntity> todoList, filteredList;
    protected TodoListRecyclerViewAdapter customAdapter;
    protected ImageView ivCurrent, ivCompleted;
    protected TextView tvCurrent, tvCompleted;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        todoList = new ArrayList<TodoEntity>();
        filteredList = new ArrayList<TodoEntity>();

        RoomDatabase.Callback dbCallBack = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
            }

            @Override
            public void onDestructiveMigration(@NonNull SupportSQLiteDatabase db) {
                super.onDestructiveMigration(db);
            }
        };

        todoDatabase = Room.databaseBuilder(getContext(), TodoDatabase.class, "Todos")
                .addCallback(dbCallBack)
                .build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_todo, container, false);

        initViews(view);


        getTodoListInBackground();

        ivCurrent.setOnClickListener(v -> {
            replaceFragment(new MainTodoFragment());
        });

        ivCompleted.setOnClickListener(v -> {
            replaceFragment(new CompletedTodoListFragment());
        });

        return view;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.todoRecyclerView);
        toolbar = view.findViewById(R.id.todoToolbar);
        ivCurrent = view.findViewById(R.id.ivCurrentTask);
        ivCompleted = view.findViewById(R.id.ivCompletedTask);
    }
    private void createAlertDialogToGetDetails() {


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add new Todo");
        final View view = getLayoutInflater().inflate(R.layout.todo_input_dialog_box_layout, null);
        builder.setView(view);

        builder.setPositiveButton("Add", (dialog, which) -> {
            EditText etAddTitle = view.findViewById(R.id.etTodoHeadline);
            EditText etAddDescription = view.findViewById(R.id.etTodoDescription);
            CheckBox cbTaskCompeted = view.findViewById(R.id.cbTaskCompleted);
            boolean isCompleted = cbTaskCompeted.isChecked();

            if (!etAddTitle.getText().toString().isEmpty() && !etAddDescription.getText().toString().isEmpty()) {
                addDataToDataBase(new TodoEntity(etAddTitle.getText().toString(), etAddDescription.getText().toString(), isCompleted));
            } else if (etAddTitle.getText().toString().isEmpty())
                etAddTitle.setError("Add Field");
            else if (etAddDescription.getText().toString().isEmpty())
                etAddDescription.setError("Add Field");

        });

        builder.setNegativeButton("Cancel", ((dialog, which) -> {
            dialog.cancel();
        }));

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void addDataToDataBase(TodoEntity todoEntity) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        service.execute(new Runnable() {
            @Override
            public void run() {
                todoDatabase.getTodoDAO().upsertTodo(todoEntity);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "Add to list", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
    public void getTodoListInBackground() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // Background task
                todoList = todoDatabase.getTodoDAO().getCurrentTodos();
                filterData();
                //on finishing task
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        setAdapter();
                        Toast.makeText(getContext(), "Loading data", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void setAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        customAdapter = new TodoListRecyclerViewAdapter(getContext(), todoList, this::onItemClick);
        customAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(customAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.todo_list_menu, menu);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.inflateMenu(R.menu.todo_list_menu);

        toolbar.setNavigationIcon(R.drawable.icon_burger);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code to implement
                Toast.makeText(getContext(), "GET DETAILS", Toast.LENGTH_SHORT).show();
                AppCompatActivity activityCompat = (AppCompatActivity) getActivity();
                activityCompat.setSupportActionBar(toolbar);

                toolbar.setBackInvokedCallbackEnabled(true);
            }
        });

        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.actionAddNewTodo) {
                createAlertDialogToGetDetails();
                return true;
            } else if (item.getItemId() == R.id.actionCompletedTodo) {
                Toast.makeText(getContext(), "completed list showing", Toast.LENGTH_SHORT).show();
                return true;
            } else
                return false;
        });
    }

    @Override
    public void onItemClick(int position) {

    }

    protected void replaceFragment(Fragment fragment) {
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.sideFrameLayout, fragment, null)
                .commit();
    }

    protected void filterData() {
        for (TodoEntity todo: todoList) {
            if (!todo.isCompleted())
                filteredList.add(todo);
        }
    }
}