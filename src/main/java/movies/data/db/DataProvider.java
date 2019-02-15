package movies.data.db;

import movies.models.Hall;
import movies.models.Movie;
import movies.models.Reservation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * DataProvider class is used to generate in-memory data that should be used for the application.
 * The class generates a list of movies and halls when an instance is created and then assigns
 * a movie to each hall.
 */
public final class DataProvider {
    private static List<Movie> movies = DataProvider.generateMovies();
    private static List<Hall> halls = DataProvider.generateHalls();
    private static List<Reservation> reservations = new ArrayList<Reservation>();

    public static List<Hall> getHalls() {
        return DataProvider.halls;
    }

    public static List<Movie> getMovies() {
        return DataProvider.movies;
    }

    public static List<Reservation> getReservations() {
        return DataProvider.reservations;
    }

    /**
     * @param r {@link Reservation} that should be added to the DB
     */
    public static void addReservation(Reservation r) {
        DataProvider.reservations.add(r);
    }

    /**
     * @param r {@link Reservation} to be deleted
     */
    public static void removeReservation(Reservation r) {
        DataProvider.reservations.remove(r);
    }

    /**
     * Generates a hall for each created movie and assigns the movie to the specific hall
     * @return A list of {@link Hall} with an assigned movie
     */
    private static List<Hall> generateHalls() {
        List<Hall> halls = new ArrayList<>();
        int count = 1;
        for (Movie movie : DataProvider.movies) {
            halls.add(new Hall(count, 10, movie));
            count++;
        }

        return halls;
    }

    /**
     * @return A list of {@link Movie}
     */
    private static List<Movie> generateMovies() {
        return Arrays.asList(
                new Movie("Lord of The Rings", 120, 5.3f),
                new Movie("Harry Potter", 75, 4.1f),
                new Movie("Aquaman", 85, 3.3f),
                new Movie("House MD", 45, 4.9f),
                new Movie("Avatar", 168, 8.9f),
                new Movie("Interstellar", 180, 8.3f),
                new Movie("Avengers: Infinity War", 124, 7.5f),
                new Movie("First Man", 85, 4.3f),
                new Movie("In Time", 100, 6.3f)
        );
    }


}
