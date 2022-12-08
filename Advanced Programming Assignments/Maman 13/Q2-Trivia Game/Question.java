import java.util.ArrayList;

public class Question {
    private String usrQuestion;
    private String rightAnswer;
    private ArrayList<String> options;

    public Question(String usrQuestion, String rightAnswer, String option1, String option2, String option3) {
        options = new ArrayList<String>();
        this.usrQuestion = usrQuestion;
        this.rightAnswer = rightAnswer;
        options.add(rightAnswer);
        options.add(option1);
        options.add(option2);
        options.add(option3);
    }

    public String getUsrQuestion() {
        return usrQuestion;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public ArrayList<String> getOptions() {
        return options;
    }
}
