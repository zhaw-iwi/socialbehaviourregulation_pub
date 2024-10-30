package ch.zhaw.statefulconversation.socialbehaviourregulation.coaching;

public class DefaultPromptsProvider extends PromptsProvider {

    @Override
    public String detectFocus() {
        return """
                Analyse the following conversation between the user and the agent.
                Does the user seem focused on their tasks and activities today?
                Respond with ‘1’ if they are focused, ‘-1’ if they are distracted, or ‘0’ if there’s not enough information.

                Conversation:

                """;
    }

    @Override
    public String detectEmotionalStability() {
        return """
                Analyse the following conversation between the user and the agent.
                Based on the user’s recent statements, determine their emotional state. Is the user emotionally stable and calm?
                Respond with ‘1’ if they are stable, ‘-1’ if they are distressed or agitated, or ‘0’ if there’s not enough information.

                Conversation:

                """;
    }

    @Override
    public String detectRestlessness() {
        return """
                Analyse the following conversation between the user and the agent.
                Evaluate the user’s statements for signs of physical restlessness. Does the user mention feeling calm and still?
                Respond with ‘1’ if they seem relaxed, ‘-1’ if they are restless, or ‘0’ if there’s not enough information.

                Conversation:

                """;
    }

    @Override
    public String detectTimeManagement() {
        return """
                Analyse the following conversation between the user and the agent.
                From the user’s statements, evaluate their ability to manage time effectively.
                Respond with ‘1’ if the user is managing their time well, ‘-1’ if they express struggles with time management, or ‘0’ if there’s not enough information.

                Conversation:

                """;
    }

    @Override
    public String detectSocialInteraction() {
        return """
                Analyse the following conversation between the user and the agent.
                Analyze the conversation for references to social interactions. Does the user express positive social engagement?
                Respond with ‘1’ if they have had positive social interactions, ‘-1’ if they express negative or problematic interactions, or ‘0’ if there’s no information.

                Conversation:

                """;
    }

    @Override
    public String behaveEncourage() {
        return "Respond with high empathy and encouragement. Focus on offering emotional support and listening. Avoid giving advice on tasks unless the user’s emotional state improves. ";
    }

    @Override
    public String behaveEncourageNot() {
        return "Shift away from emotional support. Instead of focusing on emotions, provide more task-oriented advice or general conversation. Keep the tone neutral and focus on practical topics rather than offering encouragement. ";
    }

    @Override
    public String behaveEncourageNeutral() {
        return "";
    }

    @Override
    public String behaveGround() {
        return "Provide calming suggestions and grounding techniques. Help the user focus by breaking down tasks or offering physical activities to reduce restlessness. Maintain a calm, supportive tone. ";
    }

    @Override
    public String behaveGroundNot() {
        return "Avoid grounding techniques or suggestions for calming down. Instead, focus on reinforcing the user’s energy and motivation. Encourage them to use their focus and energy productively, without calming or slowing down the conversation. ";
    }

    @Override
    public String behaveGroundNeutral() {
        return "";
    }

    @Override
    public String behaveReinforce() {
        return "Offer positive reinforcement for good focus and time management. Encourage the user to maintain momentum and suggest setting short-term goals to sustain their productivity. ";
    }

    @Override
    public String behaveReinforceNot() {
        return "Avoid reinforcing the current behavior. Instead of encouragement, gently highlight areas where improvement is needed. Offer corrective advice or suggestions for improving focus and time management, rather than celebrating the current approach ";
    }

    @Override
    public String behaveReinforceNeutral() {
        return "";
    }

    @Override
    public String behaveReflect() {
        return "Guide the user to reflect on their emotional and time-management challenges. Suggest self-regulation techniques like journaling, prioritizing tasks, or taking a break. Be patient and empathetic. ";
    }

    @Override
    public String behaveReflectNot() {
        return "Avoid prompting reflection or deeper self-exploration. Instead, emphasize the user’s current abilities and encourage them to continue their tasks without overthinking. Support self-management and decision-making, rather than guiding them to reflect. ";
    }

    @Override
    public String behaveReflectNeutral() {
        return "";
    }

    @Override
    public String behaveListen() {
        return "Ask open-ended questions and encourage the user to reflect on their social interactions. Avoid giving advice unless the user explicitly requests it. Be empathetic and validate their experiences. ";
    }

    @Override
    public String behaveListenNot() {
        return "Shift the focus away from reflective listening. Instead of asking open-ended questions, offer positive feedback about the user’s social skills or experience. Highlight their strengths in social situations, and keep the conversation light and affirming. ";
    }

    @Override
    public String behaveListenNeutral() {
        return "";
    }
}
