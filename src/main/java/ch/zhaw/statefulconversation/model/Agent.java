package ch.zhaw.statefulconversation.model;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.gson.JsonElement;

import ch.zhaw.statefulconversation.spi.ContenFilterException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Agent {

    @Id
    @GeneratedValue
    private UUID id;

    public UUID getId() {
        return this.id;
    }

    protected Agent() {

    }

    private String name;
    private String description;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private State initialState;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private State currentState;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Storage storage;

    public Agent(String name, String description, State initialState) {
        this(name, description, initialState, null);
    }

    public Agent(String name, String description, State initialState, Storage storage) {
        this.name = name;
        this.description = description;
        this.initialState = initialState;
        this.storage = storage;
        this.currentState = this.initialState;
    }

    public String name() {
        return this.name;
    }

    public String description() {
        return this.description;
    }

    public Map<String, JsonElement> storage() {
        return this.storage.toMap();
    }

    public boolean isActive() {
        return this.currentState.isActive();
    }

    public List<Utterance> conversation() {
        if (this.isActive()) {
            return this.currentState.getUtterances().toList();
        }
        return this.initialState.getUtterances().toList();
    }

    public String summarise() {
        // @TODO: if this.currentState is an OuterState which is inactive due to an
        // internal final state, then this consequence does not make sense
        if (this.isActive()) {
            return this.currentState.summarise();
        }
        return this.initialState.summarise();
    }

    public String start() {
        try {
            return this.currentState.start();
        } catch (ContenFilterException e) {
            throw e;
        }
    }

    public String respond(String userSays) {
        try {
            return this.currentState.respond(userSays);
        } catch (ContenFilterException e) {
            throw e;
        } catch (TransitionException e) {
            this.currentState = e.getSubsequentState();
            if (this.currentState.isStarting()) {
                return this.start();
            }
            return this.respond(userSays);
        }
    }

    public String reRespond() {

        if (!this.isActive()) {
            throw new RuntimeException("cannot rerespond if agent is inactive.");
        }

        String lastUserSays = this.currentState.getUtterances().removeLastTwoUtterances();
        return this.respond(lastUserSays);
    }

    public void reset() {
        // @TODO: if is starting = True, consider sending starting message again.
        this.currentState = this.initialState;
        this.currentState.reset();
    }

    public void resetCurrentState() {
        this.currentState.reset();
    }

    @Override
    public String toString() {
        return "Agent with current state " + this.currentState;
    }
}
