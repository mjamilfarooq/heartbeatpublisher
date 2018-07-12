package org.test.remotee.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.test.remotee.entities.Machine;
import org.test.remotee.messaging.MessagePublisher;
import org.test.remotee.redis.messaging.MachinePublisher;
import org.test.remotee.repositories.MachineRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class DowntimeMonitor {

    private static final Logger logger = LoggerFactory.getLogger(DowntimeMonitor.class);

    @Autowired
    MachineRepository machineRepository;

    @Autowired
    MachinePublisher messagePublisher;

    //in milliseconds timeout value to consider instance unavailable
    @Value("${downtimeMonitor.timeout}")
    Long timeout;


    @Scheduled(fixedDelayString = "${downtimeMonitor.delay}")
    public void monitor()
    {
        logger.info("monitoring downtime -- start @" + LocalTime.now() + " with downtime of " + timeout);


        for(Machine machine: machineRepository.findAll())
        {

            logger.info("checking machine with id " + machine.getId());

            LocalDateTime heartbeat = machine.getLastHeartBeat().toLocalDateTime();
            LocalDateTime now = LocalDateTime.now();

            //if machine is not offline already and timeout has passed change it to offline status
            //and notify clients
            if (Duration.between(heartbeat, now).toMillis() > timeout && !machine.isOffline())
            {
                machine.setOffline(true);
                machineRepository.save(machine);

                //notify all the subscriber for unavailibility
                logger.warn("machine is offline " + machine.getId());
                messagePublisher.publish(machine.toString());

            }
        }

        logger.info("monitoring downtime -- end");
    }
}
