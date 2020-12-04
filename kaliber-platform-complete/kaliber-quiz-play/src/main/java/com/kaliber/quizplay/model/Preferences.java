package com.kaliber.quizplay.model;

public class Preferences {

    private boolean showHints;
    private boolean explainFeedback;
    private boolean showAnswers;
    public Preferences(boolean showHints, boolean explainFeedback, boolean showAnswers) {
        super();
        this.showHints = showHints;
        this.explainFeedback = explainFeedback;
        this.showAnswers = showAnswers;
    }
    public Preferences() {
    }
    public boolean isShowHints() {
        return showHints;
    }
    public void setShowHints(boolean showHints) {
        this.showHints = showHints;
    }
    public boolean isExplainFeedback() {
        return explainFeedback;
    }
    public void setExplainFeedback(boolean explainFeedback) {
        this.explainFeedback = explainFeedback;
    }
    public boolean isShowAnswers() {
        return showAnswers;
    }
    public void setShowAnswers(boolean showAnswers) {
        this.showAnswers = showAnswers;
    }



}
