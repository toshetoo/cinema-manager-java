package movies.data.DAL;

import movies.data.db.DataProvider;
import movies.models.Movie;
import movies.models.Reservation;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Repository class that provide extended methods for searching and CRUD operations of {@link Reservation} in the DB collection
 */
public class ReservationsRepository {
    private MoviesRepository moviesRepository;
    private HallsRepository hallsRepository;
    private long MIN_INTERVAL = 60;

    public ReservationsRepository() {
        this.moviesRepository = new MoviesRepository();
        this.hallsRepository = new HallsRepository();
    }
    /**
     * @return A list of all created {@link Reservation}
     */
    public List<Reservation> getAllReservations() {
        return DataProvider.getReservations();
    }

    /**
     * @param reservationID UUID of the reservation
     * @return A single {@link Reservation} with the corresponding ID or null if the reservation does not exist
     */
    public Reservation getReservationById(UUID reservationID) {
        return DataProvider.getReservations().stream().filter(r -> r.getId() == reservationID).findFirst().orElse(null);
    }


    /**
     * @param r {@link Reservation} to be created
     */
    public void createReservation(Reservation r) throws Exception {
        Movie selectedMovie = this.moviesRepository.getMovieById(r.getReservedForMovie());

        LocalDateTime date = r.getReservedOn().plusMinutes(MIN_INTERVAL);
        if (date.compareTo(selectedMovie.getPlayOn()) < 0) {
            throw new Exception("Reservation time should be more than 60 minutes before the movie play date.");
        }

        if(r.getCount() > this.hallsRepository.getFreeHallSeats(r.getHallId()).size()) {
            throw new Exception("Not enough free seats");
        }

        this.hallsRepository.markSeatsAsTaken(r.getHallId(), r.getCount());
        DataProvider.addReservation(r);
    }


    /**
     * @param reservationId UUID of the {@link Reservation} that should be deleted
     */
    public void deleteReservation(UUID reservationId) {
        Reservation r = DataProvider.getReservations().stream().filter(res -> res.getId() == reservationId).findFirst().orElse(null);
        if (r != null) {
            DataProvider.removeReservation(r);
        }
    }
}
