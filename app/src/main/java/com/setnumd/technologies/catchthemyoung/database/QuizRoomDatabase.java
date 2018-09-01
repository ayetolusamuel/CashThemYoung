package com.setnumd.technologies.catchthemyoung.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import com.setnumd.technologies.catchthemyoung.constants.Quiz;
import com.setnumd.technologies.catchthemyoung.dao.QuizDao;

@Database(entities = {Quiz.class}, version = 1, exportSchema = false)
public abstract class QuizRoomDatabase extends RoomDatabase {
private static QuizRoomDatabase INSTANCE;
private static final String DATABASE_NAME = "quiz_database";
private static Object lock = new Object();
        public abstract QuizDao quizDao();

        public static QuizRoomDatabase getInstance(final Context context) {
                if (INSTANCE == null){
                        synchronized (lock){
                                if (INSTANCE == null){
                                        //create database Here......
                                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                                QuizRoomDatabase.class, DATABASE_NAME)
                                                .fallbackToDestructiveMigration()
                                                //.addMigrations(MIGRATION_1_2)
                                                .build();

                                }
                        }
                }
                return INSTANCE;
        }
        static final Migration MIGRATION_1_2 = new Migration(4, 5) {
                @Override
                public void migrate(SupportSQLiteDatabase database) {

                }
        };

}
