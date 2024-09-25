package ch.zhaw.statefulconversation.socialbehaviourregulation.zurichmodel;

import ch.zhaw.statefulconversation.socialbehaviourregulation.Behaviour;

public class BehaviourImpl implements Behaviour {

    private String behaviourApply;

    private String aggression;
    private String supplication;
    private String exploration;
    private String avoidance;
    private String affiliation;

    public BehaviourImpl() {
        this.behaviourApply = PromptsProvider.instance().behaviourApply();
        this.aggression = PromptsProvider.instance().behaveAggressionNeutral();
        this.supplication = PromptsProvider.instance().behaveSupplicationNeutral();
        this.exploration = PromptsProvider.instance().behaveExplorationNeutral();
        this.avoidance = PromptsProvider.instance().behaveAvoidanceNeutral();
        this.affiliation = PromptsProvider.instance().behaveAffiliationNeutral();
    }

    public BehaviourImpl aggression(String aggression) {
        this.aggression = aggression;
        return this;
    }

    public BehaviourImpl supplication(String supplication) {
        this.supplication = supplication;
        return this;
    }

    public BehaviourImpl exploration(String exploration) {
        this.exploration = exploration;
        return this;
    }

    public BehaviourImpl avoidance(String avoidance) {
        this.avoidance = avoidance;
        return this;
    }

    public BehaviourImpl affiliation(String affiliation) {
        this.affiliation = affiliation;
        return this;
    }

    @Override
    public String getPrompt() {
        StringBuffer result = new StringBuffer();
        if (!(this.aggression.isEmpty() && this.supplication.isEmpty() && this.exploration.isEmpty()
                && this.avoidance.isEmpty() && this.affiliation.isEmpty())) {
            result.append(this.behaviourApply + "\n");
        }
        result.append(this.aggression.isEmpty() ? "" : this.aggression + "\n");
        result.append(this.supplication.isEmpty() ? "" : this.supplication + "\n");
        result.append(this.exploration.isEmpty() ? "" : this.exploration + "\n");
        result.append(this.avoidance.isEmpty() ? "" : this.avoidance + "\n");
        result.append(this.affiliation.isEmpty() ? "" : this.affiliation + "\n");
        return result.toString();
    }
}
