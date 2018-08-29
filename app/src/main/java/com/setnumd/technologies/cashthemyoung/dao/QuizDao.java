package com.setnumd.technologies.cashthemyoung.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.setnumd.technologies.cashthemyoung.constants.Quiz;

import java.util.List;

@Dao
public interface QuizDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertToDatabase(List<Quiz> quizList);

    @Query("SELECT * FROM  quiz_database " )
    List<Quiz> getQuiz();

}
