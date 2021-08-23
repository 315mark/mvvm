package com.example.moudle_room;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Student.class},version = 1, exportSchema = false)
public abstract class StudentDataBase extends RoomDatabase {

    private static volatile StudentDataBase dataBase;

    public static StudentDataBase getInstance(Context context) {
        if (dataBase == null) {
            dataBase = Room.databaseBuilder(context,StudentDataBase.class ,"student_database")
                    .allowMainThreadQueries()  //放在主线程执行
                    .build();
        }
        return dataBase;
    }

    public abstract  StudentDao getStudentDao();
}
