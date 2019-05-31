import java.util.ArrayList;

/**
 * Created by Szymon on 2017-04-17.
 */
public class CostManager {

    static ArrayList<Cost> costs = new ArrayList<>();

    public static void addCost(Cost cost) {
        costs.add(cost);
    }

    public static ArrayList<Cost> getCosts() {
        return costs;
    }

    public static Cost getCostById(Integer id) {
        for (Cost cost : costs) {
            if (cost.getId() == id) {
                return cost;
            }
        }
        return null;
    }

    public static void clear() {
        costs.clear();
    }

}
