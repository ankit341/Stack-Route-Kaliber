package com.kaliber.quizplay.model;

public class ConfidenceWager {
    private String label;
    private float bonusScore;
    private float penaltyScore;

    public ConfidenceWager() {
    }

    public ConfidenceWager(String label, float bonusScore, float penaltyScore) {
        this.label = label;
        this.bonusScore = bonusScore;
        this.penaltyScore = penaltyScore;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public float getBonusScore() {
        return bonusScore;
    }

    public void setBonusScore(float bonusScore) {
        this.bonusScore = bonusScore;
    }

    public float getPenaltyScore() {
        return penaltyScore;
    }

    public void setPenaltyScore(float penaltyScore) {
        this.penaltyScore = penaltyScore;
    }
}
