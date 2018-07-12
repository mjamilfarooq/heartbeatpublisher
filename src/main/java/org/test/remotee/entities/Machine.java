package org.test.remotee.entities;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
public class Machine {

    @Id
    private Long id;

    private Timestamp lastHeartBeat;

    private boolean offline;

    public Machine()
    {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getLastHeartBeat() {
        return lastHeartBeat;
    }

    public void setLastHeartBeat(Timestamp lastHeartBeat) {
        this.lastHeartBeat = lastHeartBeat;
    }

    public void setCurrentTime()
    {
        this.lastHeartBeat = Timestamp.valueOf(LocalDateTime.now());
    }


    public boolean isOffline() {
        return offline;
    }

    public void setOffline(boolean offline) {
        this.offline = offline;
    }

    @Override
    public String toString() {
        return "Machine{" +
                "id=" + id +
                ", lastHeartBeat=" + lastHeartBeat +
                ", offline=" + offline +
                '}';
    }
}
