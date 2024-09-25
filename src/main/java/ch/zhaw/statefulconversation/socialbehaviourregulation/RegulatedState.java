package ch.zhaw.statefulconversation.socialbehaviourregulation;

import java.util.List;

import ch.zhaw.statefulconversation.model.State;
import ch.zhaw.statefulconversation.model.Storage;
import ch.zhaw.statefulconversation.model.Transition;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;

@Entity
public class RegulatedState extends State {

    protected RegulatedState() {

    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private RegulationSystem regulationSystem;

    public RegulatedState(RegulationSystem regulationSystem, String prompt, String name, String starterPrompt,
            List<Transition> transitions) {
        super(prompt, name, starterPrompt, transitions);
        this.regulationSystem = regulationSystem;
    }

    public RegulatedState(RegulationSystem regulationSystem, String prompt, String name, String starterPrompt,
            List<Transition> transitions,
            String summarisePrompt,
            boolean isStarting,
            boolean isOblivious) {
        super(prompt, name, starterPrompt, transitions, summarisePrompt, isStarting, isOblivious);
        this.regulationSystem = regulationSystem;
    }

    public RegulatedState(RegulationSystem regulationSystem, String prompt, String name, String starterPrompt,
            List<Transition> transitions,
            Storage storage,
            List<String> storageKeysFrom) {
        super(prompt, name, starterPrompt, transitions, storage, storageKeysFrom);
        this.regulationSystem = regulationSystem;
    }

    public RegulatedState(RegulationSystem regulationSystem, String prompt, String name, String starterPrompt,
            List<Transition> transitions,
            String summarisePrompt,
            boolean isStarting,
            boolean isOblivious,
            Storage storage, List<String> storageKeysFrom) {
        super(prompt, name, starterPrompt, transitions, summarisePrompt, isStarting, isOblivious, storage,
                storageKeysFrom);
        this.regulationSystem = regulationSystem;
    }

    @Override
    protected String composeTotalPrompt(String outerPrompt) {
        String superTotalPrompt = super.composeTotalPrompt(outerPrompt);
        String promptExtension = this.regulationSystem.process(this.getUtterances());
        String result = superTotalPrompt + "\n" + promptExtension;
        return result.trim();
    }
}
