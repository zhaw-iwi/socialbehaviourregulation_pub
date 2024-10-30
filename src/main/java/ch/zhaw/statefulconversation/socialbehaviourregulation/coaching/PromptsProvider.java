package ch.zhaw.statefulconversation.socialbehaviourregulation.coaching;

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

    public abstract String detectFocus();

    public abstract String detectEmotionalStability();

    public abstract String detectRestlessness();

    public abstract String detectTimeManagement();

    public abstract String detectSocialInteraction();

    public String behaviourApply() {
        return "Adjust your conversational behaviour by combining the following behaviors to influence the user's experience: ";
    }

    public abstract String behaveEncourage();

    public abstract String behaveEncourageNot();

    public abstract String behaveEncourageNeutral();

    public abstract String behaveGround();

    public abstract String behaveGroundNot();

    public abstract String behaveGroundNeutral();

    public abstract String behaveReinforce();

    public abstract String behaveReinforceNot();

    public abstract String behaveReinforceNeutral();

    public abstract String behaveReflect();

    public abstract String behaveReflectNot();

    public abstract String behaveReflectNeutral();

    public abstract String behaveListen();

    public abstract String behaveListenNot();

    public abstract String behaveListenNeutral();

}
