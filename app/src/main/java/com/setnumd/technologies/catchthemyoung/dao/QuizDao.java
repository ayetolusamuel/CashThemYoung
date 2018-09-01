package com.setnumd.technologies.catchthemyoung.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.setnumd.technologies.catchthemyoung.constants.Quiz;

import java.util.List;

@Dao
public interface QuizDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertToDatabase(Quiz quiz);

    @Query("SELECT * FROM  quiz_database " )
    List<Quiz> getQuiz();

    @Query("SELECT count(*) FROM quiz_database")
    Long getDatabaseCount();



//    @Delete()
//    void deleteQuiz(Quiz... quiz);

}
