package ch.zhaw.statefulconversation.socialbehaviourregulation.bigfive;

public abstract class PromptsProvider {

    private static PromptsProvider INSTANCE = new DefaultPromptsProvider();

    public static void instanciate(Class<? extends PromptsProvider> cla55) {
        try {
            PromptsProvider.INSTANCE = (PromptsProvider) cla55.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static PromptsProvider instance() {
        return PromptsProvider.INSTANCE;
    }

    public abstract String detectOpenness();

    public abstract String detectConscientiousness();

    public abstract String detectExtraversion();

    public abstract String detectAgreeableness();

    public abstract String detectNeuroticism();

    public String behaviourApply() {
        return "Adjust your conversational tone by combining the following behaviors to influence the user's experience: ";
    }

    public abstract String behaveAggression();

    public abstract String behaveAggressionNot();

    public abstract String behaveAggressionNeutral();

    public abstract String behaveSupplication();

    public abstract String behaveSupplicationNot();

    public abstract String behaveSupplicationNeutral();

    public abstract String behaveExploration();

    public abstract String behaveExplorationNot();

    public abstract String behaveExplorationNeutral();

    public abstract String behaveAvoidance();

    public abstract String behaveAvoidanceNot();

    public abstract String behaveAvoidanceNeutral();

    public abstract String behaveAffiliation();

    public abstract String behaveAffiliationNot();

    public abstract String behaveAffiliationNeutral();

}
