package com.mycompany.quiz_online1.observer;

import com.mycompany.quiz_online1.models.Question;
import com.mycompany.quiz_online1.models.QuestionBuilder;
import com.mycompany.quiz_online1.singleton.DatabaseConnection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QuizFrame extends JFrame implements Observer {
    private JLabel questionLabel;
    private JRadioButton option1, option2, option3, option4, optionTrue, optionFalse;
    private ButtonGroup buttonGroup;
    private JButton nextButton;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private String username;

    private List<Question> questions; // تغيير النوع إلى List<Question>

    public QuizFrame(String username) {
        this.username = username;

        setTitle("Quiz Page - Welcome " + username);
        setSize(500, 400);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        questionLabel = new JLabel("Question");
        questionLabel.setBounds(50, 30, 400, 25);
        add(questionLabel);

        option1 = new JRadioButton();
        option2 = new JRadioButton();
        option3 = new JRadioButton();
        option4 = new JRadioButton();
        optionTrue = new JRadioButton("True");
        optionFalse = new JRadioButton("False");

        buttonGroup = new ButtonGroup();
        buttonGroup.add(option1);
        buttonGroup.add(option2);
        buttonGroup.add(option3);
        buttonGroup.add(option4);
        buttonGroup.add(optionTrue);
        buttonGroup.add(optionFalse);

        option1.setBounds(50, 80, 300, 25);
        option2.setBounds(50, 120, 300, 25);
        option3.setBounds(50, 160, 300, 25);
        option4.setBounds(50, 200, 300, 25);
        optionTrue.setBounds(50, 120, 100, 25);
        optionFalse.setBounds(200, 120, 100, 25);

        add(option1);
        add(option2);
        add(option3);
        add(option4);
        add(optionTrue);
        add(optionFalse);

        nextButton = new JButton("Next");
        nextButton.setBounds(200, 300, 100, 30);
        add(nextButton);

        loadQuestions();
        displayQuestion();

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
                currentQuestionIndex++;
                if (currentQuestionIndex < questions.size()) {
                    displayQuestion();
                } else {
                    JOptionPane.showMessageDialog(null, "Quiz Finished! Your Score: " + score);
                    updateScore();
                    dispose();
                }
            }
        });

        setVisible(true);
    }

    private void loadQuestions() {
        questions = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM questions LIMIT 10");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Question question = new QuestionBuilder()
                        .setQuestionText(rs.getString("question_text"))
                        .setOption1(rs.getString("option1"))
                        .setOption2(rs.getString("option2"))
                        .setOption3(rs.getString("option3"))
                        .setOption4(rs.getString("option4"))
                        .setCorrectAnswer(rs.getString("correct_answer"))
                        .setQuestionType(rs.getString("question_type"))
                        .build();

                questions.add(question); // إضافة السؤال إلى القائمة
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayQuestion() {
        Question question = questions.get(currentQuestionIndex);

        questionLabel.setText("Q" + (currentQuestionIndex + 1) + ": " + question.getQuestionText());

        if (question.getQuestionType().equals("TrueFalse")) {
            optionTrue.setVisible(true);
            optionFalse.setVisible(true);

            option1.setVisible(false);
            option2.setVisible(false);
            option3.setVisible(false);
            option4.setVisible(false);
        } else {
            optionTrue.setVisible(false);
            optionFalse.setVisible(false);

            option1.setVisible(true);
            option2.setVisible(true);
            option3.setVisible(true);
            option4.setVisible(true);

            option1.setText(question.getOption1());
            option2.setText(question.getOption2());
            option3.setText(question.getOption3());
            option4.setText(question.getOption4());
        }

        buttonGroup.clearSelection();
    }

    private void checkAnswer() {
        Question question = questions.get(currentQuestionIndex);
        String correctAnswer = question.getCorrectAnswer();

        if (question.getQuestionType().equals("TrueFalse")) {
            if ((optionTrue.isSelected() && correctAnswer.equalsIgnoreCase("True")) ||
                    (optionFalse.isSelected() && correctAnswer.equalsIgnoreCase("False"))) {
                score += 10;
            }
        } else {
            if ((option1.isSelected() && option1.getText().equals(correctAnswer)) ||
                    (option2.isSelected() && option2.getText().equals(correctAnswer)) ||
                    (option3.isSelected() && option3.getText().equals(correctAnswer)) ||
                    (option4.isSelected() && option4.getText().equals(correctAnswer))) {
                score += 10;
            }
        }
    }

    private void updateScore() {
        String query = "UPDATE students SET score_rate = ? WHERE username = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, score);
            stmt.setString(2, username);
            stmt.executeUpdate();
            System.out.println("Score updated successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to update the score!");
        }
    }

    @Override
    public void update() {
        loadQuestions();
    }
}
