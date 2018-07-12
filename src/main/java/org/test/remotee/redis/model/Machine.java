package org.test.remotee.redis.model;

import java.io.Serializable;

public class Machine implements Serializable {
    private Long id;
    private boolean available;

    public Machine()
    {}

    public Machine(final Long id, final boolean available)
    {
        this.id = id;
        this.available = available;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Machine{" +
                "id=" + id +
                ", available=" + available +
                '}';
    }
}
