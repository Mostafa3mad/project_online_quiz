package com.mycompany.quiz_online1.models;

public class QuestionBuilder {
    private String questionText;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String correctAnswer;
    private String questionType;

    public QuestionBuilder setQuestionText(String questionText) {
        this.questionText = questionText;
        return this;
    }

    public QuestionBuilder setOption1(String option1) {
        this.option1 = option1;
        return this;
    }

    public QuestionBuilder setOption2(String option2) {
        this.option2 = option2;
        return this;
    }

    public QuestionBuilder setOption3(String option3) {
        this.option3 = option3;
        return this;
    }

    public QuestionBuilder setOption4(String option4) {
        this.option4 = option4;
        return this;
    }

    public QuestionBuilder setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
        return this;
    }

    public QuestionBuilder setQuestionType(String questionType) {
        this.questionType = questionType;
        return this;
    }

    public Question build() {
        return new Question(questionText, option1, option2, option3, option4, correctAnswer, questionType);
    }
}
