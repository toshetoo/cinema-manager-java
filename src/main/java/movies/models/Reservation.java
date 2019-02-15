package movies.models;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Reservation class responsible to describe a single reservation in a hall, for a movie, on a specific date
 */
public class Reservation {
    private UUID id;
    private User reservedFor;
    private UUID reservedForMovie;
    private UUID hallId;
    private LocalDateTime reservedOn;
    private Integer count;

    public Reservation(User reservedFor, UUID reservedForMovie, LocalDateTime reservedOn, Integer count, UUID hallId) {
        this.id = UUID.randomUUID();
        this.reservedFor = reservedFor;
        this.reservedForMovie = reservedForMovie;
        this.reservedOn = reservedOn;
        this.count = count;
        this.hallId = hallId;
    }

    public UUID getId() {
        return id;
    }

    public User getReservedFor() {
        return reservedFor;
    }

    public void setReservedFor(User reservedFor) {
        this.reservedFor = reservedFor;
    }

    public UUID getReservedForMovie() {
        return reservedForMovie;
    }

    public void setReservedForMovie(UUID reservedForMovie) {
        this.reservedForMovie = reservedForMovie;
    }

    public LocalDateTime getReservedOn() {
        return reservedOn;
    }

    public void setReservedOn(LocalDateTime reservedOn) {
        this.reservedOn = reservedOn;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public UUID getHallId() {
        return hallId;
    }

    public void setHallId(UUID hallId) {
        this.hallId = hallId;
    }
}
