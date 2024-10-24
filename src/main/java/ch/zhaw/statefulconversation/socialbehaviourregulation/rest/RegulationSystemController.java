package ch.zhaw.statefulconversation.socialbehaviourregulation.rest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.statefulconversation.socialbehaviourregulation.RegulationSystem;

@RestController
public class RegulationSystemController {

    @Autowired
    private RegulationSystemRepository repository;

    @GetMapping("socialbehaviourregulation/regulationsystem/all")
    public ResponseEntity<List<RegulationSystem>> findAll() {
        List<RegulationSystem> result = this.repository.findAll();
        return new ResponseEntity<List<RegulationSystem>>(result, HttpStatus.OK);
    }

    @PutMapping("socialbehaviourregulation/regulationsystem/{id}/observe")
    public ResponseEntity<RegulationSystem> observe(@PathVariable("id") UUID id, @RequestBody Observation observation) {
        Optional<RegulationSystem> result = this.repository.findById(id);

        if (result.isEmpty()) {
            return new ResponseEntity<RegulationSystem>(HttpStatus.NOT_FOUND);
        }

        // result.get().observer(observation);
        // this.repository.save(result.get());
        return new ResponseEntity<RegulationSystem>(HttpStatus.OK);
    }

}
