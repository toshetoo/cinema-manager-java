package movies.models;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

/**
 * Movie class describing a movie that can be watched in a cinema
 */
public class Movie {
    private UUID movieId;
    private String name;
    private Integer length;
    private Float grade;
    private LocalDateTime playOn;

    public Movie(String name, Integer length, Float grade) {
        this.movieId = UUID.randomUUID();
        this.name = name;
        this.length = length;
        this.grade = grade;
        this.playOn = this.generatePlayTime();
    }

    public UUID getMovieId() {
        return this.movieId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Float getGrade() {
        return grade;
    }

    public void setGrade(Float grade) {
        this.grade = grade;
    }

    public LocalDateTime getPlayOn() {
        return playOn;
    }

    public void setPlayOn(LocalDateTime playOn) {
        this.playOn = playOn;
    }

    /**
     * Generates a random play time for a movie. Takes the current day of the month and a random hour and minutes
     * @return An instance of {@link LocalDateTime} that should be assigned to the movie
     */
    public LocalDateTime generatePlayTime() {
        Random rnd = new Random();
        int dayNumber = LocalDateTime.now().getDayOfMonth();

        String day = Integer.toString(dayNumber);
        if(dayNumber < 10) {
            day = "0" + dayNumber;
        }

        int hourNumber = rnd.nextInt(23);
        String hour = Integer.toString(hourNumber);
        if(hourNumber < 10) {
            hour = "0" + hour;
        }

        int minuteNumber = rnd.nextInt(60);
        String minute = Integer.toString(minuteNumber);
        if(minuteNumber < 10) {
            minute = "0" + minuteNumber;
        }

        String date = MessageFormat.format("2019-02-{0} {1}:{2}", day, hour, minute);
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
