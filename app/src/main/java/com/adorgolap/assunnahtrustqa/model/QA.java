package com.adorgolap.assunnahtrustqa.model;

import com.parse.ParseObject;

/**
 * Created by ifta on 12/3/16.
 */

public class QA {
    private int id;
    private String question,answer,category;

    public QA(ParseObject object)
    {
        int id = object.getInt("qaId");
        String question = object.getString("question");
        String answer = object.getString("answer");
        String category = object.getString("category");
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.category = category;

    }
    public QA(int id, String question, String answer, String category) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.category = category;
    }

    @Override
    public String toString() {
        return "ID : " +id+"\n"
                +"Q : " + question+"\n"
                +"A : " + answer+"\n"
                +"C : " + category+"\n";
    }

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public String getCategory() {
        return category;
    }
}
