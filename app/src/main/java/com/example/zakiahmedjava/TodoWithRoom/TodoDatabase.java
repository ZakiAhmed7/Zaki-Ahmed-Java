package com.example.zakiahmedjava.TodoWithRoom;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(
        entities = {TodoEntity.class},
        version = 1
)
public abstract class TodoDatabase extends RoomDatabase {
    public abstract TodoDAO getTodoDAO();
}
