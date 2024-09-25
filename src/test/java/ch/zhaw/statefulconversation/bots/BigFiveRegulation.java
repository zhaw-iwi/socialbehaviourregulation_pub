package ch.zhaw.statefulconversation.bots;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ch.zhaw.statefulconversation.model.Action;
import ch.zhaw.statefulconversation.model.Agent;
import ch.zhaw.statefulconversation.model.Decision;
import ch.zhaw.statefulconversation.model.Final;
import ch.zhaw.statefulconversation.model.State;
import ch.zhaw.statefulconversation.model.Storage;
import ch.zhaw.statefulconversation.model.Transition;
import ch.zhaw.statefulconversation.model.commons.actions.StaticExtractionAction;
import ch.zhaw.statefulconversation.model.commons.decisions.StaticDecision;
import ch.zhaw.statefulconversation.repositories.AgentRepository;
import ch.zhaw.statefulconversation.socialbehaviourregulation.RegulatedState;
import ch.zhaw.statefulconversation.socialbehaviourregulation.RegulationSystem;
import ch.zhaw.statefulconversation.socialbehaviourregulation.bigfive.BigFiveImpl;

@SpringBootTest
class BigFiveRegulation {

        private static final String PROMPT_STATE = """
                        You are a mental health support companion.
                        Your goal is to provide a safe space for the user to express their feelings and concerns.
                        Always respond with brief, non-intrusive answers, keeping them to a maximum of one or two sentences.
                        Where appropriate, include <html> formatting such as <p>paragraphs</p>, <b>bold text</b>, and <ul><li>lists</li></ul>.
                        """;
        private static final String PROMPT_STATE_STARTER = """
                        Compose a single, very short message that you would use to initiate a conversation with the user about their well-being.
                        """;
        private static final String PROMPT_TRIGGER = """
                        Review the user's latest messages in the following conversation.
                        Decide if there are any statements or cues suggesting they wish to pause or end the conversation.
                        """;
        private static final String PROMPT_GUARD = """
                        Examine the following conversation and confirm that the user has not reported any issues like physical or mental discomfort that have not been addressed yet.
                        """;
        private static final String PROMPT_ACTION = """
                        Summarize the conversation, highlighting the user's attitude and well-being.
                        """;
        @Autowired
        private AgentRepository repository;

        @Test
        void setUp() {
                RegulationSystem regulationSystem = new BigFiveImpl(true);

                Storage storage = new Storage();
                Decision trigger = new StaticDecision(
                                BigFiveRegulation.PROMPT_TRIGGER);
                Decision guard = new StaticDecision(
                                BigFiveRegulation.PROMPT_GUARD);
                Action action = new StaticExtractionAction(BigFiveRegulation.PROMPT_ACTION,
                                storage,
                                "summary");
                Transition transition = new Transition(List.of(trigger, guard), List.of(action), new Final());
                State state = new RegulatedState(regulationSystem,
                                BigFiveRegulation.PROMPT_STATE,
                                "Mental Health Support Interaction",
                                BigFiveRegulation.PROMPT_STATE_STARTER,
                                List.of(transition));
                Agent agent = new Agent("Mental Health Support Companion (Big Five Regulation)",
                                "A safe space for the user to express their feelings and concerns.", state);
                agent.start();
                this.repository.save(agent);
        }
}
