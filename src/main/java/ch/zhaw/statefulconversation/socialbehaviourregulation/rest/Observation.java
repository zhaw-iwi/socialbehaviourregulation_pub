package ch.zhaw.statefulconversation.socialbehaviourregulation.rest;

public class Observation {
    private int arousal;
    private int valence;

    public Observation(int arousal, int valence) {
        this.arousal = arousal;
        this.valence = valence;
    }

    public int getArousal() {
        return this.arousal;
    }

    public int getValence() {
        return this.valence;
    }
}
