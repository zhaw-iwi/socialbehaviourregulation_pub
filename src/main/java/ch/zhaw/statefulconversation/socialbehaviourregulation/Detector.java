package ch.zhaw.statefulconversation.socialbehaviourregulation;

import java.util.UUID;

import ch.zhaw.statefulconversation.model.Utterances;
import ch.zhaw.statefulconversation.spi.LMOpenAI;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Detector {

    @Id
    @GeneratedValue
    private UUID id;

    public UUID getID() {
        return this.id;
    }

    protected Detector() {

    }

    @Column(length = 3000)
    private String prompt;

    public Detector(String prompt) {
        this.prompt = prompt;
    }

    public int detect(Utterances conversation) {
        if (conversation.isEmpty()) {
            return 0;
        }
        int result = LMOpenAI.detect(conversation, this.prompt);
        return result;
    }
}
