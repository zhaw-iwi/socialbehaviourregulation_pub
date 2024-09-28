package ch.zhaw.statefulconversation.socialbehaviourregulation.coaching;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.zhaw.statefulconversation.model.Utterances;
import ch.zhaw.statefulconversation.socialbehaviourregulation.Actuator;
import ch.zhaw.statefulconversation.socialbehaviourregulation.Behaviour;
import ch.zhaw.statefulconversation.socialbehaviourregulation.Detector;
import ch.zhaw.statefulconversation.socialbehaviourregulation.RegulationSystem;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;

@Entity
public class CoachingImpl extends RegulationSystem {

        private static final Logger LOGGER = LoggerFactory.getLogger(CoachingImpl.class);

        protected CoachingImpl() {

        }

        @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        private Detector focus;
        @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        private Detector emotionalStability;
        @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        private Detector restlessness;
        @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        private Detector timeManagement;
        @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        private Detector socialInteraction;

        @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        private Actuator encourager;
        @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        private Actuator grounder;
        @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        private Actuator reinforcer;
        @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        private Actuator reflector;
        @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        private Actuator listener;

        public CoachingImpl(boolean marker) {

                this.focus = new Detector(PromptsProvider.instance().detectFocus());
                this.emotionalStability = new Detector(PromptsProvider.instance().detectEmotionalStability());
                this.restlessness = new Detector(PromptsProvider.instance().detectRestlessness());
                this.timeManagement = new Detector(PromptsProvider.instance().detectTimeManagement());
                this.socialInteraction = new Detector(PromptsProvider.instance().detectSocialInteraction());

                this.encourager = new Actuator(
                                DefaultPromptsProvider.instance().behaveEncourage(),
                                DefaultPromptsProvider.instance().behaveEncourageNot(),
                                DefaultPromptsProvider.instance().behaveEncourageNeutral());
                this.grounder = new Actuator(
                                DefaultPromptsProvider.instance().behaveGround(),
                                DefaultPromptsProvider.instance().behaveGroundNot(),
                                DefaultPromptsProvider.instance().behaveEncourageNeutral());
                this.reinforcer = new Actuator(
                                DefaultPromptsProvider.instance().behaveReinforce(),
                                DefaultPromptsProvider.instance().behaveReinforceNot(),
                                DefaultPromptsProvider.instance().behaveReinforceNeutral());
                this.reflector = new Actuator(
                                DefaultPromptsProvider.instance().behaveReflect(),
                                DefaultPromptsProvider.instance().behaveReflectNot(),
                                DefaultPromptsProvider.instance().behaveReflectNeutral());
                this.listener = new Actuator(
                                DefaultPromptsProvider.instance().behaveListen(),
                                DefaultPromptsProvider.instance().behaveListenNot(),
                                DefaultPromptsProvider.instance().behaveListenNeutral());
        }

        @Override
        public String process(Utterances utterances) {
                // 1 Detect
                int focus = this.focus.detect(utterances);
                int emotionalStability = this.emotionalStability.detect(utterances);
                int restlessness = this.restlessness.detect(utterances);
                int timeManagement = this.timeManagement.detect(utterances);
                int socialInteraction = this.socialInteraction.detect(utterances);

                CoachingImpl.LOGGER
                                .info("Detection (Foc, EmS, Res, TiM, SoI): " + "("
                                                + focus + ", "
                                                + emotionalStability + ", "
                                                + restlessness + ", "
                                                + timeManagement + ", "
                                                + socialInteraction + ")");

                // 2 Cope (@TODO)

                // 3 Behave
                Behaviour behaviour = new BehaviourImpl()
                                .encourage(this.encourager.act(-emotionalStability))
                                .ground(this.grounder
                                                .act((focus < 0 && restlessness < 0 ? 1
                                                                : (focus > 0 && restlessness > 0 ? -1 : 0))))
                                .reinforce(this.reinforcer
                                                .act((focus > 0 && timeManagement > 0 ? 1 : -(focus * timeManagement))))
                                .reflect(this.reflector
                                                .act((emotionalStability > 0 && timeManagement > 0 ? -1
                                                                : (emotionalStability < 0 && timeManagement < 0 ? 1
                                                                                : 0))))
                                .listen(this.listener.act(-socialInteraction));

                String result = behaviour.getPrompt();
                CoachingImpl.LOGGER
                                .info("Social Behaviour Regulation: " + result);
                return result;
        }
}
