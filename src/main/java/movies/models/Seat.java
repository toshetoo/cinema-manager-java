package movies.models;

import java.util.UUID;

/**
 * Seat class describing a single seat in a movie hall
 */
public class Seat {
    private UUID hallId;
    private Integer seatNumber;
    private Boolean isTaken;

    public Seat(UUID hallId, Integer seatNumber, Boolean isTaken) {
        this.hallId = hallId;
        this.seatNumber = seatNumber;
        this.isTaken = isTaken;
    }

    public UUID getHallId() {
        return hallId;
    }

    public void setHallId(UUID hallId) {
        this.hallId = hallId;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Boolean getTaken() {
        return isTaken;
    }

    public void setTaken(Boolean taken) {
        isTaken = taken;
    }
}
