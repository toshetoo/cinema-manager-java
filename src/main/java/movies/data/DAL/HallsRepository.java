package movies.data.DAL;

import movies.data.db.DataProvider;
import movies.models.Hall;
import movies.models.Seat;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Repository class that provide extended methods for searching of {@link Hall} in the DB collection
 */
public class HallsRepository {

    /**
     * @return A collection of all {@link Hall}
     */
    public List<Hall> getAllHalls() {
        return DataProvider.getHalls();
    }

    /**
     * @param hallId UUID of the hall
     * @return A single {@link Hall} with the corresponding ID or NULL if the hall does not exist
     */
    public Hall getHallById(UUID hallId) {
        return DataProvider.getHalls().stream().filter(h -> h.getHallId() == hallId).findFirst().orElse(null);
    }

    /**
     * @return A list of {@link Hall} that have any free seats
     */
    public List<Hall> getFreeHalls() {
        return DataProvider.getHalls().stream().filter(h -> h.getSeats().stream().anyMatch(seat -> !seat.getTaken())).collect(Collectors.toList());
    }

    /**
     * @param hallID UUID of the hall
     * @return A List of {@link Seat} for the current hall that are currently not taken
     */
    public List<Seat> getFreeHallSeats(UUID hallID) {
        return this.getHallById(hallID).getSeats().stream().filter(seat -> !seat.getTaken()).collect(Collectors.toList());
    }

    /**
     * @param hallId The Id of the hall we're going to mark seats of
     * @param seatCount The amount of seats that are going to be taken
     */
    public void markSeatsAsTaken(UUID hallId, Integer seatCount) {
        Hall currentHall = this.getHallById(hallId);
        currentHall.getSeats().stream().filter(s -> !s.getTaken()).limit(seatCount).forEach(seat -> {
            seat.setTaken(true);
        });
    }
}
