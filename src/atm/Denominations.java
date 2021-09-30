package atm;

import java.util.ArrayList;
import java.util.List;

/**
 * Enums of all the denominations available for performing deposit and withdraw transactions in ATM.
 */
public enum Denominations {
    Twenty(20),
    Ten(10),
    Five(5),
    One(1);

    private final Integer value;

    //For getByValue().
    private static final Denominations[] denominations = Denominations.values();

    Denominations(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }

    // Getter by value:
    public static Denominations getByValue(Integer value) {
        for(Denominations e: denominations) {
            if(e.getValue().equals(value)) {
                return e;
            }
        }
        return null;
    }

    // Get all the denominations in list
    public static List<Integer> getDenominations() {
        List<Integer> list = new ArrayList<>();
        for(Denominations e: denominations) {
            list.add(e.getValue());
        }
        return list;
    }
}
