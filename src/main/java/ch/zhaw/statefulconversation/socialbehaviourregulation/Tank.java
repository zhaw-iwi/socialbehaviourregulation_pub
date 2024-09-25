package ch.zhaw.statefulconversation.socialbehaviourregulation;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Tank {

    @Id
    @GeneratedValue
    private UUID id;

    public UUID getID() {
        return this.id;
    }

    protected Tank() {

    }

    private int claim;
    private int current;

    public Tank(int claim) {
        this(claim, claim);
    }

    public Tank(int claim, int initial) {
        this.claim = claim;
        this.current = initial;
    }

    public void process(int incOrDec) {
        if (incOrDec < 0) {
            this.decrease();
        } else if (incOrDec > 0) {
            this.increase();
        }
    }

    public void increase() {
        this.current++;
    }

    public void decrease() {
        this.current--;
    }

    public int getBalance() {
        if (this.current > this.claim) {
            return 1;
        }
        if (this.current < this.claim) {
            return -1;
        }
        return 0;
    }
}
