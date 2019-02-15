package movies.views;

import movies.data.DAL.HallsRepository;
import movies.data.DAL.MoviesRepository;
import movies.data.DAL.ReservationsRepository;
import movies.models.Hall;
import movies.models.Movie;
import movies.models.Reservation;
import movies.models.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MenuRenderer {
    private MoviesRepository moviesRepository;
    private HallsRepository hallsRepository;
    private ReservationsRepository reservationsRepository;

    public MenuRenderer() {
        this.moviesRepository = new MoviesRepository();
        this.hallsRepository = new HallsRepository();
        this.reservationsRepository = new ReservationsRepository();
    }


    public void renderMainMenu() {
        //clearConsole();
        System.out.println("Select a category:");
        System.out.println("1. View movies");
        System.out.println("2. Create reservation");
        System.out.println("3. Exit");
        String input = readFromConsole();

        switch (input) {
            case "1":
                renderMoviesMenu();
                break;
            case "2":
                renderReservationsMenu();
                break;
            case "3":
                System.exit(0);
                break;
            default:
                System.out.println("Invalid selection! Try again!");
                clearConsole();
                renderMainMenu();
                break;
        }
    }

    private void renderMoviesMenu() {
        print("--- All movies ---");
        List<Movie> movies = this.moviesRepository.getAllMovies();
        for (int i = 0; i < movies.size(); i++) {
            Movie currentMovie = movies.get(i);
            print((i + 1) + ". " + currentMovie.getName());
            print("- Length: " + currentMovie.getLength());
            print("- Grade: " + currentMovie.getGrade());
            print("- PlayOn: " + currentMovie.getPlayOn());
            print("#########");
        }

        print("### Press [B] for back");
        String input = readFromConsole();
        while (!input.toLowerCase().equals("b")) {
            print("Invalid selection, try again...");
            input = readFromConsole();
        }

        clearConsole();
        renderMainMenu();
    }

    private void renderReservationsMenu() {
        print("--- Create a reservation ---");

        List<Hall> halls = this.hallsRepository.getFreeHalls();
        for (int i = 0; i < halls.size(); i++) {
            Hall currentHall = halls.get(i);
            print("No: " + (i + 1));
            print("Current movie: " + currentHall.getCurrentPlayingMovie().getName());
            print("Hall No: " + currentHall.getHallNumber());
            print("Free seats: " + this.hallsRepository.getFreeHallSeats(currentHall.getHallId()).size());
            print("Plays on: " + currentHall.getCurrentPlayingMovie().getPlayOn());
            print("----------------------");
        }

        print("### Select a movie from the list");
        int choice = Integer.parseInt(readFromConsole());
        while (choice < 1 || choice > 10) {
            print("Invalid choice. Try again");
            choice = Integer.parseInt(readFromConsole());
        }

        Hall selectedHall = halls.get(choice - 1);

        print("Insert name for reservation: ");
        User u = new User(readFromConsole());
        print("How many people?");
        int people = Integer.parseInt(readFromConsole());

//        LocalDateTime reservationTime = LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        Reservation newReservation = new Reservation(u, selectedHall.getCurrentPlayingMovie().getMovieId(), LocalDateTime.now(), people, selectedHall.getHallId());

        try {
            this.reservationsRepository.createReservation(newReservation);
            print("Reservation successful");
            sleep();
            clearConsole();
            renderMainMenu();
        } catch (Exception e) {
            print(e.getMessage());
            sleep();
            clearConsole();
            renderMainMenu();
        }
    }

    private void print(String msg) {
        System.out.println(msg);
    }

    private String readFromConsole() {
        String input;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            input = br.readLine();
            return input;
        } catch (IOException e) {
            System.out.println("Invalid string");
            e.printStackTrace();
        }
        return null;
    }

    private void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void clearConsole() {
        for (int clear = 0; clear < 1000; clear++) {
            System.out.println();
        }
    }
}
