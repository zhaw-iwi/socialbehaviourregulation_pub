package ch.zhaw.statefulconversation.socialbehaviourregulation.rest;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.statefulconversation.socialbehaviourregulation.Tank;

@RestController
public class TankController {

    @Autowired
    private TankRepository repository;

    @PutMapping("socialbehaviourregulation/tank/{id}/increase")
    public ResponseEntity<Tank> increase(@PathVariable("id") UUID id) {
        Optional<Tank> result = this.repository.findById(id);

        if (result.isEmpty()) {
            return new ResponseEntity<Tank>(HttpStatus.NOT_FOUND);
        }

        result.get().increase();
        this.repository.save(result.get());
        return new ResponseEntity<Tank>(HttpStatus.OK);
    }

    @PutMapping("socialbehaviourregulation/tank/{id}/decrease")
    public ResponseEntity<Tank> decrease(@PathVariable("id") UUID id) {
        Optional<Tank> result = this.repository.findById(id);

        if (result.isEmpty()) {
            return new ResponseEntity<Tank>(HttpStatus.NOT_FOUND);
        }

        result.get().decrease();
        this.repository.save(result.get());
        return new ResponseEntity<Tank>(HttpStatus.OK);
    }

}
