package ch.zhaw.statefulconversation.socialbehaviourregulation;

import java.util.UUID;

import ch.zhaw.statefulconversation.model.Utterances;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public abstract class RegulationSystem {

        @Id
        @GeneratedValue
        private UUID id;

        public UUID getID() {
                return this.id;
        }

        protected RegulationSystem() {

        }

        public abstract String process(Utterances utterances);
}
