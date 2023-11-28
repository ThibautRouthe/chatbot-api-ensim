package fr.ensim.interop.introrest;

public class Joke {
    private int id;
    private String title;
    private String text;
    private int rating; // la note sur 10

    public Joke(int id, String title, String text, int rating) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
