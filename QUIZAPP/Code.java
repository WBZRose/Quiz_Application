import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Code extends JFrame {
    private List<Question> questions;
    private int currentQuestionIndex;
    private int score;
    private Timer timer;
    private int secondsLeft;

    private JLabel questionLabel;
    private JRadioButton[] optionRadioButtons;
    private JButton nextButton;
    private JButton finishButton;
    private JLabel timerLabel;
    private ButtonGroup optionsGroup;

    public Code() {
        setTitle("Quiz");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        questions = new ArrayList<>();
        initializeQuestions();

        JPanel mainPanel = new JPanel(new BorderLayout());

        questionLabel = new JLabel();
        mainPanel.add(questionLabel, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel(new GridLayout(0, 1));
        optionRadioButtons = new JRadioButton[3];
        optionsGroup = new ButtonGroup();

        for (int i = 0; i < optionRadioButtons.length; i++) {
            optionRadioButtons[i] = new JRadioButton();
            optionsGroup.add(optionRadioButtons[i]);
            optionsPanel.add(optionRadioButtons[i]);
        }

        mainPanel.add(optionsPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkAnswerAndNext();
            }
        });
        buttonPanel.add(nextButton);

        finishButton = new JButton("Finish");
        finishButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayScore();
            }
        });
        buttonPanel.add(finishButton);

        timerLabel = new JLabel();
        buttonPanel.add(timerLabel);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        startQuiz();
    }

    private void initializeQuestions() {
        questions.add(new Question("What is the capital of Bangladesh?", "Dhaka", "Berlin", "Chittagong", "A"));
        questions.add(new Question("Which planet is known as the Red Planet?", "Mars", "Venus", "Jupiter", "A"));
        questions.add(new Question("What is the largest mammal?", "Elephant", "Blue Whale", "Giraffe", "B"));
        questions.add(new Question("Which element has the chemical symbol 'H'?", "Hydrogen", "Helium", "Oxygen", "A"));
        questions.add(new Question("Who painted the Mona Lisa?", "Pablo Picasso", "Leonardo da Vinci", "Vincent van Gogh", "B"));

        /* Shuffle the order of questions */
        Collections.shuffle(questions);
    }

    private void startQuiz() {
        score = 0;
        currentQuestionIndex = 0;
        secondsLeft = 10; /* Set the timer duration in seconds */
        timerLabel.setText("Time left: " + secondsLeft + " seconds");

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                secondsLeft--;
                timerLabel.setText("Time left: " + secondsLeft + " seconds");

                if (secondsLeft <= 0) {
                    timer.cancel();
                    checkAnswerAndNext();
                }
            }
        }, 500, 500); /* Update timer every second */

        showQuestion(currentQuestionIndex);
    }

    private void showQuestion(int index) {
        Question currentQuestion = questions.get(index);
        questionLabel.setText(currentQuestion.getQuestion());

        for (int i = 0; i < optionRadioButtons.length; i++) {
            optionRadioButtons[i].setText(currentQuestion.getOptions()[i]);
            optionRadioButtons[i].setSelected(false);
        }

        nextButton.setEnabled(false);
        finishButton.setEnabled(false);
    }

    private void checkAnswerAndNext() {
        Question currentQuestion = questions.get(currentQuestionIndex);

        for (int i = 0; i < optionRadioButtons.length; i++) {
            if (optionRadioButtons[i].isSelected() && currentQuestion.getCorrectAnswerIndex() == i) {
                score++;
                break;
            }
        }

        if (currentQuestionIndex < questions.size() - 1) {
            currentQuestionIndex++;
            showQuestion(currentQuestionIndex);
            secondsLeft = 30; /* Reset timer for the next question */
        } else {
            timer.cancel();
            displayScore();
        }
    }

    private void displayScore() {
        String result = "Quiz Finished!\nYour score: " + score + " out of " + questions.size();
        JOptionPane.showMessageDialog(this, result, "Quiz Results", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }
}