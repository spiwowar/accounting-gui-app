import java.util.ArrayList;

/**
 * Created by Szymon on 2017-04-17.
 */
public class Person {

    String name;

    ArrayList<Cost> costs = new ArrayList<>();

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Cost> getAllCosts() {
        return this.costs;
    }

    public void addCost(Cost cost) {
        costs.add(cost);
    }

    public Cost getCost(Integer id) {
        for (Cost cost : costs) {
            if (cost.getId() == id) {
                return cost;
            }
        }
        return null;
    }

    public Cost getCost(String costName) {
        for (Cost cost : costs) {
            if (cost.getName() == costName) {
                return cost;
            }
        }
        return null;
    }

    public void modifyCost(String costName, Double newValue) {
        for (Cost cost : costs) {
            if (cost.getName().equals(costName)) {
                cost.setValue(newValue);
                break;
            }
        }
    }

    public void modifyQty(String costName, Double newValue) {
        for (Cost cost : costs) {
            if (cost.getName().equals(costName)) {
                cost.setQty(newValue);
                break;
            }
        }
    }

    Integer id;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }
}
