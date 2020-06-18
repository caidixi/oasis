package cmcm.oasis.response.body.trend;

import java.util.LinkedList;
import java.util.List;

public class Trend {
    private List<SingleTrend> trend;

    public Trend() {
        trend = new LinkedList<>();
    }

    public void addTrend(String name,String id,double activity){
        trend.add(new SingleTrend(name,id,activity));
    }

    public void addTrend(String name,Long id,double activity){
        trend.add(new SingleTrend(name,id+"",activity));
    }

    public List<SingleTrend> getTrend() {
        return trend;
    }
}
