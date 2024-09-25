package ch.zhaw.statefulconversation.socialbehaviourregulation.zurichmodel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.zhaw.statefulconversation.model.Utterances;
import ch.zhaw.statefulconversation.socialbehaviourregulation.Actuator;
import ch.zhaw.statefulconversation.socialbehaviourregulation.Behaviour;
import ch.zhaw.statefulconversation.socialbehaviourregulation.Detector;
import ch.zhaw.statefulconversation.socialbehaviourregulation.RegulationSystem;
import ch.zhaw.statefulconversation.socialbehaviourregulation.Tank;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;

@Entity
public class ZurichModelImpl extends RegulationSystem {

        private static final Logger LOGGER = LoggerFactory.getLogger(ZurichModelImpl.class);

        protected ZurichModelImpl() {

        }

        @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        private Tank dependency;
        @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        private Tank enterprise;
        @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        private Tank autonomy;

        @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        private Detector familiarity;
        @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        private Detector relevance;
        @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        private Detector proximity;
        @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        private Detector success;

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

        public ZurichModelImpl(int dependency, int enterprise, int autonomy) {
                this.dependency = new Tank(dependency);
                this.enterprise = new Tank(enterprise);
                this.autonomy = new Tank(autonomy);

                this.familiarity = new Detector(PromptsProvider.instance().detectFamiliarity());
                this.relevance = new Detector(PromptsProvider.instance().detectRelevance());
                this.proximity = new Detector(PromptsProvider.instance().detectProximity());
                this.success = new Detector(PromptsProvider.instance().detectSuccess());

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
                int familiarity = this.familiarity.detect(utterances);
                int potency = this.relevance.detect(utterances) * this.proximity.detect(utterances);
                this.dependency.process(familiarity * potency);
                this.enterprise.process(-1 * familiarity * potency);
                this.autonomy.process(this.success.detect(utterances));
                ZurichModelImpl.LOGGER
                                .info("Internal Social Needs (Dep, Ent, Aut): " + "(" + this.dependency.getBalance()
                                                + ", " + this.enterprise.getBalance() + ", "
                                                + this.autonomy.getBalance() + ")");

                // 2 Cope (@TODO)

                // 3 Behave
                Behaviour behaviour = new BehaviourImpl()
                                .aggression(this.aggression.act(-1 * this.autonomy.getBalance()))
                                .supplication(this.supplication.act(this.autonomy.getBalance()))
                                .exploration(this.exploration.act(-1 * this.enterprise.getBalance()))
                                .avoidance(this.avoidance.act(enterprise.getBalance() * this.dependency.getBalance()))
                                .affiliation(this.affiliation.act(-1 * this.dependency.getBalance()));

                String result = behaviour.getPrompt();
                ZurichModelImpl.LOGGER
                                .info("Social Behaviour Regulation: " + result);
                return result;
        }
}
