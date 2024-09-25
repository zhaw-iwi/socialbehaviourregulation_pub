package ch.zhaw.statefulconversation.socialbehaviourregulation;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Actuator {

    @Id
    @GeneratedValue
    private UUID id;

    public UUID getID() {
        return this.id;
    }

    protected Actuator() {

    }

    @Column(length = 3000)
    private String promptPositive;
    @Column(length = 3000)
    private String promptNegative;
    @Column(length = 3000)
    private String promptNeutral;

    public Actuator(String promptPositive, String promptNegative, String promptNeutral) {
        this.promptPositive = promptPositive;
        this.promptNegative = promptNegative;
        this.promptNeutral = promptNeutral;
    }

    public String act(int policy) {
        if (policy < 0) {
            return this.promptNegative;
        }
        if (policy > 0) {
            return this.promptPositive;
        }
        return this.promptNeutral;
    }
}
