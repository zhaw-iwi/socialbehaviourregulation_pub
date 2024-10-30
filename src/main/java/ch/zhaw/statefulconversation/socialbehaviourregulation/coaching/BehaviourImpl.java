package ch.zhaw.statefulconversation.socialbehaviourregulation.coaching;

import ch.zhaw.statefulconversation.socialbehaviourregulation.Behaviour;

public class BehaviourImpl implements Behaviour {

    private String behaviourApply;

    private String encourage;
    private String ground;
    private String reinforce;
    private String reflect;
    private String listen;

    public BehaviourImpl() {
        this.behaviourApply = PromptsProvider.instance().behaviourApply();
        this.encourage = PromptsProvider.instance().behaveEncourageNeutral();
        this.ground = PromptsProvider.instance().behaveGroundNeutral();
        this.reinforce = PromptsProvider.instance().behaveReinforceNeutral();
        this.reflect = PromptsProvider.instance().behaveReflectNeutral();
        this.listen = DefaultPromptsProvider.instance().behaveListenNeutral();
    }

    public BehaviourImpl encourage(String encourage) {
        this.encourage = encourage;
        return this;
    }

    public BehaviourImpl ground(String ground) {
        this.ground = ground;
        return this;
    }

    public BehaviourImpl reinforce(String reinforce) {
        this.reinforce = reinforce;
        return this;
    }

    public BehaviourImpl reflect(String reflect) {
        this.reflect = reflect;
        return this;
    }

    public BehaviourImpl listen(String listen) {
        this.listen = listen;
        return this;
    }

    @Override
    public String getPrompt() {
        StringBuffer result = new StringBuffer();
        if (!(this.encourage.isEmpty() && this.ground.isEmpty() && this.reinforce.isEmpty()
                && this.reflect.isEmpty() && this.listen.isEmpty())) {
            result.append(this.behaviourApply + "\n");
        }
        result.append(this.encourage.isEmpty() ? "" : this.encourage + "\n");
        result.append(this.ground.isEmpty() ? "" : this.ground + "\n");
        result.append(this.reinforce.isEmpty() ? "" : this.reinforce + "\n");
        result.append(this.reflect.isEmpty() ? "" : this.reflect + "\n");
        result.append(this.listen.isEmpty() ? "" : this.listen + "\n");
        return result.toString();
    }
}
