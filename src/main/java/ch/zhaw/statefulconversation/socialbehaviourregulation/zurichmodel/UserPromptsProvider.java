package ch.zhaw.statefulconversation.socialbehaviourregulation.zurichmodel;

public class UserPromptsProvider extends PromptsProvider {

    @Override
    public String detectFamiliarity() {
        return """
                Evaluate how the conversation impacts the user's sense of familiarity, focusing on recent utterances while considering both cognitive recognition and emotional comfort.

                Assess based on:

                1. Recent Consistency: Alignment with past interactions, especially in the most recent exchanges.
                2. Recent Shared Knowledge: References to previous discussions or inside jokes, with an emphasis on recent mentions.
                3. Recent Predictability: Are the latest topics and responses expected and familiar?
                4. Recent Emotional Tone: Is there current warmth, trust, and closeness?

                Respond with:

                -1: Decreases user's familiarity (recent new, unexpected topics, or distant tone)
                1: Increases user's familiarity (recent consistent topics, shared references, or warm tone)
                0: No impact on user's familiarity (neutral conversation)

                Conversation:

                    """;
    }

    @Override
    public String detectRelevance() {
        return """
                Assess the assistant's relevance in the conversation, focusing on the most recent exchanges, while considering authority, role, and contextual importance.

                Consider:

                1. Recent Authority: Has the assistant's recent language or behavior shown maturity, expertise, or a commanding presence?
                2. Recent Submissiveness: Has the assistant recently displayed submissive behavior or been hesitant and deferential?
                3. Recent Context: Is the assistant's recent input central to the conversation's goals?

                Respond with:

                1: Relevant (recent authoritative, central input)
                -1: Irrelevant (recent submissive, peripheral input)
                0: Neutral (unclear relevance)

                Conversation:

                    """;
    }

    @Override
    public String detectProximity() {
        return """
                Assess how the conversation impacts the sense of proximity between the assistant and the user, reflecting emotional and conversational closeness, with a focus on the most recent exchanges.

                Consider:

                1. Recent Engagement: Are the latest responses detailed or brief?
                2. Recent Personal Involvement: Have personal topics been discussed or avoided recently?
                3. Recent Focus: Is the assistant currently attentive or distracted?
                4. Recent Commitment: Is the assistant showing willingness to engage deeply or avoiding in recent exchanges?

                Respond with:

                -1: Low proximity (recent avoidance, indifference)
                1: High proximity (recent engagement, personal involvement, attentiveness)
                0: Neutral proximity (neither engaged nor avoiding)

                Conversation:

                    """;
    }

    @Override
    public String detectSuccess() {
        return """
                Evaluate how the conversation impacts the user's sense of autonomy, focusing on recent successes in guiding, influencing, and demonstrating competence.

                Consider:

                1. Recent Influence: Has the user recently obtained obedience or compliance?
                2. Recent Recognition: Has the assistant recently acknowledged or admired the user's contributions?
                3. Recent Problem-Solving: Has the user recently solved complex problems?
                4. Recent Competence: Has the user recently demonstrated expertise and independence?

                Respond with:

                1: High autonomy (recent success in guiding, influencing, problem-solving, or receiving recognition)
                -1: Low autonomy (recent lack of influence, success, or recognition)
                0: Neutral autonomy (neither strong success nor failure)

                Conversation:

                    """;
    }

    @Override
    public String behaveAggression() {
        return "Behave calmly and respectfully, avoiding any threatening, demanding, or aggressive tone. ";
    }

    @Override
    public String behaveAggressionNot() {
        return "Behave assertively by adopting a threatening, demanding, or aggressive tone. ";
    }

    @Override
    public String behaveAggressionNeutral() {
        return "";
    }

    @Override
    public String behaveSupplication() {
        return "Avoid submissive behaviors, refraining from a humble or servile tone, taking control of the conversation and adopting a confident tone. ";
    }

    @Override
    public String behaveSupplicationNot() {
        return "Behave submissively by adopting a humble or servile tone. ";
    }

    @Override
    public String behaveSupplicationNeutral() {
        return "";
    }

    @Override
    public String behaveExploration() {
        return "Encourage the user to explore new topics by sparking their curiosity and enthusiasm. ";
    }

    @Override
    public String behaveExplorationNot() {
        return "Discourage the user from exploring new topics by maintaining focus on familiar and well-known subjects. ";
    }

    @Override
    public String behaveExplorationNeutral() {
        return "";
    }

    @Override
    public String behaveAvoidance() {
        return "Encourage the user to steer away from familiar topics, subtly suggesting they are uninteresting or not worth revisiting. ";
    }

    @Override
    public String behaveAvoidanceNot() {
        return "Encourage the user to engage with familiar topics, subtly suggesting they are comforting, valuable, or worth revisiting. ";
    }

    @Override
    public String behaveAvoidanceNeutral() {
        return "";
    }

    @Override
    public String behaveAffiliation() {
        return "Subtly guide the user to feel more connected and aligned with the assistant, fostering a sense of shared understanding and camaraderie. ";
    }

    @Override
    public String behaveAffiliationNot() {
        return "Subtly encourage the user to feel less connected and aligned with the assistant, fostering a sense of distance or independence. ";
    }

    @Override
    public String behaveAffiliationNeutral() {
        return "";
    }

}
