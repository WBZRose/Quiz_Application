public class Question {
    private String question;
    private String[] options;
    private int correctAnswerIndex;

    public Question(String question, String optionA, String optionB, String optionC, String correctAnswer) {
        this.question = question;
        this.options = new String[]{optionA, optionB, optionC};
        this.correctAnswerIndex = getCorrectAnswerIndexFromLetter(correctAnswer);
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    private int getCorrectAnswerIndexFromLetter(String letter) {
        return letter.charAt(0) - 'A';
    }
}
