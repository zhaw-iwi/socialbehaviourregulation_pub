package ch.zhaw.statefulconversation.socialbehaviourregulation.rest;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.zhaw.statefulconversation.socialbehaviourregulation.Tank;

public interface TankRepository extends JpaRepository<Tank, UUID> {

}
