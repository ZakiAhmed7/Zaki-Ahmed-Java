package com.example.zakiahmedjava.TodoWithRoom;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;
import androidx.room.Upsert;

import java.util.List;
@Dao
// Performing curd operation in this one
public interface TodoDAO {
    @Upsert
    public void upsertTodo (TodoEntity todoEntity);
    @Delete
    public void deleteTodo (TodoEntity todoEntity);

    @Query("SELECT * FROM Todos WHERE completed = 1")
    List<TodoEntity> getCompletedTodo();

    @Query("SELECT * FROM Todos WHERE completed = 0")
    List<TodoEntity> getCurrentTodos();

    @Query("SELECT * FROM Todos WHERE id==:todoID")
    TodoEntity getTodoDetails(int todoID);
    @Query("SELECT * FROM Todos")
    List<TodoEntity> getAllTodos();
    @Query("UPDATE Todos SET completed = :changedValue WHERE id = :position")
    void updateList(int position, boolean changedValue);

}
