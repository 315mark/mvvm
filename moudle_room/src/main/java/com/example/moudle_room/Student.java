package com.example.moudle_room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "student_table")
public class Student {

    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "student_number")
    public int studentNumber;

}
