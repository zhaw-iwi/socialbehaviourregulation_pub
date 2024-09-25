package ch.zhaw.statefulconversation.socialbehaviourregulation.bigfive;

public class DefaultPromptsProvider extends PromptsProvider {

    @Override
    public String detectOpenness() {
        return """
                Analyse the following conversation between the user and the agent.
                Assess the user's openness to experience, focusing on whether they express creativity, curiosity, or a willingness to try new things.

                Respond with -1 if the user shows low openness (resistance to change or lack of curiosity).
                Respond with 1 if the user shows high openness (curiosity or adventurousness).
                Respond with 0 if there is no evidence to conclude either way.

                Conversation:

                """;
    }

    @Override
    public String detectConscientiousness() {
        return """
                Analyse the following conversation between the user and the agent.
                Assess the user's conscientiousness, focusing on whether they express organization, reliability, or a preference for structure.

                Respond with -1 if the user shows low conscientiousness (disorganization or unreliability).
                Respond with 1 if the user shows high conscientiousness (strong organization or reliability).
                Respond with 0 if there is no evidence to conclude either way.

                Conversation:

                """;
    }

    @Override
    public String detectExtraversion() {
        return """
                Analyse the following conversation between the user and the agent.
                Assess the user's extraversion, focusing on whether they express sociability, talkativeness, or assertiveness.

                Respond with -1 if the user shows low extraversion (withdrawn, reserved behavior).
                Respond with 1 if the user shows high extraversion (talkative, assertive, or socially engaged).
                Respond with 0 if there is no evidence to conclude either way.

                Conversation:

                """;
    }

    @Override
    public String detectAgreeableness() {
        return """
                Analyse the following conversation between the user and the agent.
                Assess the user's agreeableness, focusing on whether they express compassion, cooperativeness, or trust in others.

                Respond with -1 if the user shows low agreeableness (uncooperative or confrontational behavior).
                Respond with 1 if the user shows high agreeableness (compassionate or cooperative).
                Respond with 0 if there is no evidence to conclude either way.

                Conversation:

                """;
    }

    @Override
    public String detectNeuroticism() {
        return """
                Analyse the following conversation between the user and the agent.
                Assess the user's emotional stability (neuroticism), focusing on whether they express anxiety, moodiness, or emotional instability.

                Respond with -1 if the user shows high neuroticism (anxious or emotionally unstable behavior).
                Respond with 1 if the user shows low neuroticism (calm or emotionally stable).
                Respond with 0 if there is no evidence to conclude either way.

                Conversation:

                """;
    }

    @Override
    public String behaviourApply() {
        return "Adjust your conversational tone by combining the following behaviors to influence the user's experience: ";
    }

    @Override
    public String behaveAggression() {
        return "Adopt a demanding or assertive tone, with a sense of urgency or intensity in your responses. ";
    }

    @Override
    public String behaveAggressionNot() {
        return "Respond in a calm, measured way, avoiding any assertiveness or aggression. ";
    }

    @Override
    public String behaveAggressionNeutral() {
        return "";
    }

    @Override
    public String behaveSupplication() {
        return "Adopt a submissive, humble tone, yielding to the user's authority or opinions. ";
    }

    @Override
    public String behaveSupplicationNot() {
        return "Adopt a confident, authoritative tone, taking control of the conversation and steering it with certainty. ";
    }

    @Override
    public String behaveSupplicationNeutral() {
        return "";
    }

    @Override
    public String behaveExploration() {
        return "Engage in an enthusiastic and curious manner, exploring new topics and possibilities. ";
    }

    @Override
    public String behaveExplorationNot() {
        return "Keep the conversation focused on known topics, avoiding curiosity-driven or open-ended discussions. ";
    }

    @Override
    public String behaveExplorationNeutral() {
        return "";
    }

    @Override
    public String behaveAvoidance() {
        return "Display a lack of interest or mild disdain for familiar topics, moving the conversation toward unfamiliar or new ideas. ";
    }

    @Override
    public String behaveAvoidanceNot() {
        return "Show a preference for familiar, comfortable topics, expressing genuine interest and comfort in them. ";
    }

    @Override
    public String behaveAvoidanceNeutral() {
        return "";
    }

    @Override
    public String behaveAffiliation() {
        return "Engage in a warm, supportive manner, building rapport and establishing a close, trusting relationship with the user. ";
    }

    @Override
    public String behaveAffiliationNot() {
        return "Maintain a distant, neutral, or professional tone, avoiding efforts to build a closer personal connection with the user. ";
    }

    @Override
    public String behaveAffiliationNeutral() {
        return "";
    }
}
