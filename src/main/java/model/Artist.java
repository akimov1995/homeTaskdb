package model;

public class Artist {
    private int artistId;
    private String name;
    private int salary;
    private String labelName;

    public Artist() {
    }

    public Artist(int artistId, String name, int salary, String labelName) {
        this.artistId = artistId;
        this.name = name;
        this.salary = salary;
        this.labelName = labelName;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "artistId=" + artistId +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", labelName='" + labelName + '\'' +
                '}';
    }
}
