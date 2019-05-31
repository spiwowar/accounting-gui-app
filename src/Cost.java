/**
 * Created by Szymon on 2017-04-17.
 */
public class Cost {

    private Integer id;
    private String name;
    private Double value;
    private Double qty;

    public Cost(Integer id, String name, Double value, Double qty) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.qty = qty;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Double getValue() {
        return this.value;
    }

    public Double getQty() {
        return this.qty;
    }
}
