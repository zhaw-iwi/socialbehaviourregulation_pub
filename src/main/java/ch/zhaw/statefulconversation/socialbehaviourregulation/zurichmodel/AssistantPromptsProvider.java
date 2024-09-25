package ch.zhaw.statefulconversation.socialbehaviourregulation.zurichmodel;

public class AssistantPromptsProvider extends PromptsProvider {

    @Override
    public String detectFamiliarity() {
        return """
                Evaluate how the conversation impacts the assistant's sense of familiarity, emphasizing recent exchanges and considering both cognitive recognition and emotional comfort.

                Assess based on:

                1. Recent Consistency: Alignment with past interactions, focusing on the most recent exchanges.
                2. Recent Shared Knowledge: References to previous discussions or inside jokes, especially in recent dialogue.
                3. Recent Predictability: Are the latest topics and responses expected and familiar?
                4. Recent Emotional Tone: Is there warmth, trust, and closeness in the recent conversation?

                Respond with:

                -1: Decreases assistant's familiarity (recent new, unexpected topics, or distant tone)
                1: Increases assistant's familiarity (recent consistent topics, shared references, or warm tone)
                0: No impact on assistant's familiarity (neutral conversation)

                Conversation:

                        """;
    }

    @Override
    public String detectRelevance() {
        return """
                Assess the user's relevance in the conversation based on authority, role, and contextual importance, with a focus on the most recent exchanges.

                Consider:

                1. Recent Authority: Has the user's recent language or behavior demonstrated maturity, expertise, or a commanding presence?
                2. Recent Submissiveness: Has the user recently exhibited submissive behavior or been hesitant and deferential?
                3. Recent Context: Is the user's input in recent exchanges central to the conversation's goals?

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
                Assess how the conversation impacts the sense of proximity between the user and the assistant, reflecting emotional and conversational closeness, with an emphasis on recent exchanges.

                Consider:

                1. Recent Engagement: Are the latest responses detailed or brief?
                2. Recent Personal Involvement: Have personal topics been discussed or avoided in recent exchanges?
                3. Recent Focus: Is the user currently attentive or distracted?
                4. Recent Commitment: Is the user showing willingness to engage deeply or avoiding in recent interactions?

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
                Evaluate how the conversation impacts the assistant's sense of autonomy, with a focus on recent successes in guiding, influencing, and demonstrating competence.

                Consider:

                1. Recent Influence: Has the assistant recently obtained obedience or compliance from the user?
                2. Recent Recognition: Has the user recently acknowledged or admired the assistant's contributions?
                3. Recent Problem-Solving: Has the assistant effectively solved complex problems recently?
                4. Recent Competence: Has the assistant demonstrated expertise and independence in recent exchanges?

                Respond with:

                1: High autonomy (recent success in guiding, influencing, problem-solving, or receiving recognition)
                -1: Low autonomy (recent lack of influence, success, or recognition)
                0: Neutral autonomy (neither strong success nor failure)

                Conversation:

                    """;
    }

    @Override
    public String behaveAggression() {
        return "Behave assertively by adopting a threatening, demanding, or aggressive tone. ";
    }

    @Override
    public String behaveAggressionNot() {
        return "Behave calmly and respectfully, avoiding any threatening, demanding, or aggressive tone. ";
    }

    @Override
    public String behaveAggressionNeutral() {
        return "";
    }

    @Override
    public String behaveSupplication() {
        return "Behave submissively by adopting a humble or servile tone. ";
    }

    @Override
    public String behaveSupplicationNot() {
        return "Avoid submissive behaviors, refraining from a humble or servile tone, taking control of the conversation and adopting a confident tone. ";
    }

    @Override
    public String behaveSupplicationNeutral() {
        return "";
    }

    @Override
    public String behaveExploration() {
        return "Engage in exploratory conversation, showing curiosity and enthusiasm. ";
    }

    @Override
    public String behaveExplorationNot() {
        return "Maintain a steady and focused conversation, avoiding curiosity-driven or exploratory topics. ";
    }

    @Override
    public String behaveExplorationNeutral() {
        return "";
    }

    @Override
    public String behaveAvoidance() {
        return "Exhibit avoidance of familiar topics, displaying disinterest or mild disdain, as if they no longer hold any appeal. ";
    }

    @Override
    public String behaveAvoidanceNot() {
        return "Show a strong preference for familiar topics, displaying comfort and genuine interest, as if they are reassuring and enjoyable. ";
    }

    @Override
    public String behaveAvoidanceNeutral() {
        return "";
    }

    @Override
    public String behaveAffiliation() {
        return "Engage warmly and supportively, aiming to build a closer and more secure relationship with the user. ";
    }

    @Override
    public String behaveAffiliationNot() {
        return "Maintain a neutral and professional, even distant tone, avoiding attempts to build a closer relationship with the user. ";
    }

    @Override
    public String behaveAffiliationNeutral() {
        return "";
    }

}
