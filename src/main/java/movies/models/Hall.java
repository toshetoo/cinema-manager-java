package movies.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Hall class describing a movie hall that has a certain amount of seats and a current movie
 * Seats are generated automatically during initialization depending on the hall capacity.
 */
public class Hall {
    private UUID hallId;
    private Integer hallNumber;
    private Integer capacity;
    private Movie currentPlayingMovie;
    private List<Seat> seats;

    public Hall(Integer hallNumber, Integer capacity, Movie currentPlayingMovie) {
        this.hallId = UUID.randomUUID();
        this.hallNumber = hallNumber;
        this.capacity = capacity;
        this.currentPlayingMovie = currentPlayingMovie;
        this.seats = new ArrayList<>();

        for (int i = 0; i < this.capacity; i++) {
            this.seats.add(new Seat(this.getHallId(), (i + 1), false));
        }
    }

    public UUID getHallId() {
        return this.hallId;
    }

    public Integer getHallNumber() {
        return hallNumber;
    }

    public void setHallNumber(Integer hallNumber) {
        this.hallNumber = hallNumber;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Movie getCurrentPlayingMovie() {
        return currentPlayingMovie;
    }

    public void setCurrentPlayingMovie(Movie currentPlayingMovie) {
        this.currentPlayingMovie = currentPlayingMovie;
    }

    public List<Seat> getSeats() {
        return seats;
    }
}
