package ch.zhaw.statefulconversation.socialbehaviourregulation.bigfive;

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
public class BigFiveImpl extends RegulationSystem {

        private static final Logger LOGGER = LoggerFactory.getLogger(BigFiveImpl.class);

        protected BigFiveImpl() {

        }

        @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        private Detector openness;
        @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        private Detector conscientiousness;
        @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        private Detector extraversion;
        @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        private Detector agreeableness;
        @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        private Detector neuroticism;

        @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        private Actuator aggression;
        @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        private Actuator supplication;
        @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        private Actuator exploration;
        @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        private Actuator avoidance;
        @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        private Actuator affiliation;

        public BigFiveImpl(boolean marker) {

                this.openness = new Detector(PromptsProvider.instance().detectOpenness());
                this.conscientiousness = new Detector(PromptsProvider.instance().detectConscientiousness());
                this.extraversion = new Detector(PromptsProvider.instance().detectExtraversion());
                this.agreeableness = new Detector(PromptsProvider.instance().detectAgreeableness());
                this.neuroticism = new Detector(PromptsProvider.instance().detectNeuroticism());

                this.aggression = new Actuator(
                                PromptsProvider.instance().behaveAggression(),
                                PromptsProvider.instance().behaveAggressionNot(),
                                PromptsProvider.instance().behaveAggressionNeutral());
                this.supplication = new Actuator(
                                PromptsProvider.instance().behaveSupplication(),
                                PromptsProvider.instance().behaveSupplicationNot(),
                                PromptsProvider.instance().behaveSupplicationNeutral());
                this.exploration = new Actuator(
                                PromptsProvider.instance().behaveExploration(),
                                PromptsProvider.instance().behaveExplorationNot(),
                                PromptsProvider.instance().behaveExplorationNeutral());
                this.avoidance = new Actuator(
                                PromptsProvider.instance().behaveAvoidance(),
                                PromptsProvider.instance().behaveAvoidanceNot(),
                                PromptsProvider.instance().behaveAvoidanceNeutral());
                this.affiliation = new Actuator(
                                PromptsProvider.instance().behaveAffiliation(),
                                PromptsProvider.instance().behaveAffiliationNot(),
                                PromptsProvider.instance().behaveAffiliationNeutral());
        }

        @Override
        public String process(Utterances utterances) {
                // 1 Detect
                int openness = this.openness.detect(utterances);
                int conscientiousness = this.conscientiousness.detect(utterances);
                int extraversion = this.extraversion.detect(utterances);
                int agreeableness = this.agreeableness.detect(utterances);
                int neuroticism = this.neuroticism.detect(utterances);

                BigFiveImpl.LOGGER
                                .info("Detection (Ope, Con, Ext, Agr, Neu): " + "("
                                                + openness + ", "
                                                + conscientiousness + ", "
                                                + extraversion + ", "
                                                + conscientiousness + ", "
                                                + neuroticism + ")");

                // 2 Cope (@TODO)

                // 3 Behave
                Behaviour behaviour = new BehaviourImpl()
                                .aggression(this.aggression.act(extraversion * (-agreeableness)))
                                .supplication(this.supplication.act(agreeableness * (-conscientiousness)))
                                .exploration(this.exploration.act(openness * extraversion))
                                .avoidance(this.avoidance.act((-openness) * neuroticism))
                                .affiliation(this.affiliation.act(agreeableness * neuroticism));

                String result = behaviour.getPrompt();
                BigFiveImpl.LOGGER
                                .info("Social Behaviour Regulation: " + result);
                return result;
        }
}
