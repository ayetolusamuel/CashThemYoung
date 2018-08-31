package com.setnumd.technologies.catchthemyoung.constants;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "quiz_database",indices = {@Index(value = {"question","hints"},unique = true)})
public class Quiz {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String answer;
    private String stage;
    private String hints;



    @ColumnInfo(name = "question")
    @NonNull
    @PrimaryKey(autoGenerate = false)
    public static final String QUESTION = "question";

    @ColumnInfo(name = "optionA")
    public static final String OPTION_A = "opta";

    @ColumnInfo(name = "optionB")
    public static final String OPTION_B = "optb";

    @ColumnInfo(name = "optionC")
    public static final String OPTION_C = "optc";

    @ColumnInfo(name = "optionD")
    public static final String OPTION_D = "optd";

    @ColumnInfo(name = "answer")
    public static final String ANSWER = "answer";

    @ColumnInfo(name = "stage")
    public static final String STAGE = "stage";

    @ColumnInfo(name = "hints")
    public static final String HINTS = "hints";


    public Quiz(int id, String question, String optionA, String optionB, String optionC, String optionD, String answer, String stage, String hints) {
        this.id = id;
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.answer = answer;
        this.stage = stage;
        this.hints = hints;
    }

@Ignore
    public Quiz(String question, String optionA, String optionB, String optionC, String optionD, String answer, String stage, String hints) {
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.answer = answer;
        this.stage = stage;
        this.hints = hints;
    }

    public String getQuestion() {
        return question;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public String getAnswer() {
        return answer;
    }

    public String getStage() {
        return stage;
    }

    public String getHints() {
        return hints;
    }

    public int getId() {
        return id;
    }
}
