package com.setnumd.technologies.catchthemyoung.constants;


import android.support.annotation.NonNull;

public class Quiz {
    private int id;
    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String answer;
    private String stage;
    private String hints;


    public static final String QUESTION = "question";
    public static final String OPTION_A = "opta";
    public static final String OPTION_B = "optb";
    public static final String OPTION_C = "optc";
    public static final String OPTION_D = "optd";
    public static final String ANSWER = "answer";
    public static final String STAGE = "stage";
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
