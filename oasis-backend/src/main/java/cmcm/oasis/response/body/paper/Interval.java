package cmcm.oasis.response.body.paper;

public class Interval {
    private int startYear;
    private int endYear;

    public Interval() {
    }

    public Interval(int startYear, int endYear) {
        this.startYear = startYear;
        this.endYear = endYear;
    }

    public int getStartYear() {
        return startYear;
    }

    public int getEndYear() {
        return endYear;
    }
}
