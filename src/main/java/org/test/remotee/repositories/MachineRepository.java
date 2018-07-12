package org.test.remotee.repositories;


import org.springframework.data.repository.CrudRepository;
import org.test.remotee.entities.Machine;

public interface MachineRepository extends CrudRepository<Machine, Long> {
}
