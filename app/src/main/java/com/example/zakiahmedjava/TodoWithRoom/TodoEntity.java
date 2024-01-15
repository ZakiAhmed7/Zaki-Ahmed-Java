package com.example.zakiahmedjava.TodoWithRoom;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity (tableName = "Todos")
public class TodoEntity {
    @ColumnInfo (name = "id")
    @PrimaryKey (autoGenerate = true)
    protected int todoId = 0;

    @ColumnInfo(name = "headline")
    protected String todoHeadline;

    @ColumnInfo(name = "note")
    protected String todoNote;

    @ColumnInfo(name = "completed")
    protected boolean isCompleted;

    @Ignore
    public TodoEntity() { }

    public TodoEntity(String todoHeadline, String todoNote, boolean isCompleted) {
        this.todoHeadline = todoHeadline;
        this.todoNote = todoNote;
        this.isCompleted = isCompleted;
    }

    public int getTodoId() {
        return todoId;
    }

    public void setTodoId(int todoId) {
        this.todoId = todoId;
    }

    public String getTodoHeadline() {
        return todoHeadline;
    }

    public void setTodoHeadline(String todoHeadline) {
        this.todoHeadline = todoHeadline;
    }

    public String getTodoNote() {
        return todoNote;
    }

    public void setTodoNote(String todoNote) {
        this.todoNote = todoNote;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
