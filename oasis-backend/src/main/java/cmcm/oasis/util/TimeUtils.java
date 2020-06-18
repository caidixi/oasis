package cmcm.oasis.util;

import java.util.Calendar;

public class TimeUtils {

    public static int getRecentYear(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }
}
