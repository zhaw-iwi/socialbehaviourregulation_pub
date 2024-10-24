package ch.zhaw.statefulconversation.socialbehaviourregulation.rest;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.zhaw.statefulconversation.socialbehaviourregulation.RegulationSystem;

public interface RegulationSystemRepository extends JpaRepository<RegulationSystem, UUID> {

}
