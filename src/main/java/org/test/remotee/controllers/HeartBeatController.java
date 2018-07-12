package org.test.remotee.controllers;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.test.remotee.entities.Machine;
import org.test.remotee.messaging.MessagePublisher;
import org.test.remotee.redis.messaging.MachinePublisher;
import org.test.remotee.repositories.MachineRepository;
import org.slf4j.Logger;

import java.util.Optional;


@RestController(value="/heartbeat")
public class HeartBeatController {

    @Autowired
    MachinePublisher messagePublisher;

    @Autowired
    MachineRepository machineRepository;

    private static Logger logger = LoggerFactory.getLogger(HeartBeatController.class);


    @PostMapping
    public ResponseEntity<String> index(@RequestParam Long id)
    {
        ResponseEntity<String> response = null;
        try {
            logger.info("find machine against: " + id);
            Optional<Machine> result = machineRepository.findById(id);
            if (result.isPresent()) {
                Machine machine = result.get();
                machine.setCurrentTime();

                if (machine.isOffline())
                {
                    logger.trace("machine is back online");

                    //turn it back online
                    machine.setOffline(false);

                    //here we should notify availability of the instance.
                    messagePublisher.publish(machine.toString());
                }

                machineRepository.save(machine);
                response = ResponseEntity.ok("Success");
            }
            else
            {
                logger.warn("no machine found against id " + id);
                response = ResponseEntity.badRequest().body("Failure to process request");
            }

        }
        catch(Exception ex)
        {
            logger.info(ex.getMessage());
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Failure while saving data");
        }

        return response;
    }

}
