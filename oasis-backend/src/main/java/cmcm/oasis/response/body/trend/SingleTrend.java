package cmcm.oasis.response.body.trend;

public class SingleTrend {
    private String name;
    private String id;
    private double activity;

    public SingleTrend(String name, String id, double activity) {
        this.name = name;
        this.id = id;
        this.activity = activity;
    }

    public SingleTrend() {
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public double getActivity() {
        return activity;
    }
}
