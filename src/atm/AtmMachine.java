package atm;

import java.util.HashMap;
import java.util.List;

public interface AtmMachine {
    /**
     * Deposit the amount requested by user.
     *
     * @param map, the map, contains denomination, and it's count to add.
     *
     * @return the StringBuilder to show on screen about total balance and denominations.
     */
    StringBuilder deposit(HashMap<Integer, Integer> map);

    /**
     * Deposit the amount requested by user.
     *
     * @param map, the map, contains denomination, and it's count to update after withdraw.
     *
     * @return the StringBuilder to show on screen about total balance and denominations.
     */
    StringBuilder withdraw(HashMap<Integer, Integer> map);

    /**
     * @return the total balance available in ATM.
     */
    int getAvailableBalance();

    /**
     * @returns the all denominations available to deposit and withdraw values in list.
     */
    List<Integer> getAllDenominations();

    /**
     * @returns the denominations count available in ATM.
     */
    int getDenominationCount(int currency);

    /**
     * @returns the total numbers of deposit operations have been completed.
     */
    int getDepositOpCnt();

    /**
     * @returns the total numbers of withdraw operations have been completed.
     */
    int getWithdrawOpCnt();

    /**
     * Increment the withdraw operation count.
     */
    void incrementWithdrawOpCnt();

    /**
     * Increment the deposit operation count.
     */
    void incrementDepositOpCnt();
}