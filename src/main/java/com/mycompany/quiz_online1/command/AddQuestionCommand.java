package com.mycompany.quiz_online1.command;

import com.mycompany.quiz_online1.observer.TeacherFrame;

public class AddQuestionCommand implements Command {
    private TeacherFrame teacherFrame;
    private String questionText;
    private String correctAnswer;

    public AddQuestionCommand(TeacherFrame teacherFrame, String questionText, String correctAnswer) {
        this.teacherFrame = teacherFrame;
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
    }

    @Override
    public void execute() {
        teacherFrame.addQuestionToDatabase(questionText, correctAnswer);
        System.out.println("Question added: " + questionText);
    }

    @Override
    public void undo() {
        teacherFrame.removeLastQuestion();
        System.out.println("Undo question added: " + questionText);
    }
}
