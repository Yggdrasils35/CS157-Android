package info.androidhive.recyclersearch;

public class Search {
    private String text;
    private int id;

    public Search(String text, int id) {
        this.text = text;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}
