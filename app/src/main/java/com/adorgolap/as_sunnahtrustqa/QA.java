package com.adorgolap.as_sunnahtrustqa;

/**
 * Created by ifta on 12/3/16.
 */

public class QA {
    private int id;
    private String question,answer,category;

    public QA(int id, String question, String answer, String category) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.category = category;
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
