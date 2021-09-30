package atm;

import java.util.HashMap;
import java.util.List;

import static utils.StringUtils.BALANCE;
import static utils.StringUtils.COLON;
import static utils.StringUtils.COMMA;
import static utils.StringUtils.EQUAL_SIGN;
import static utils.StringUtils.POSTFIX;
import static utils.StringUtils.SPACE;
import static utils.StringUtils.TOTAL;

/**
 * ATM class maintains the available balance, denominations, and it's count.
 */
public class AtmMachineImpl implements AtmMachine {
    private final HashMap<Integer, Integer> currencyVsCount = new HashMap<>();

    private final List<Integer> denominations;

    private int totalAmount;

    private int depositOpCnt;

    private int withdrawOpCnt;

    public AtmMachineImpl() {
        this.depositOpCnt = 0;
        this.withdrawOpCnt = 0;
        this.totalAmount = 0;
        this.denominations = Denominations.getDenominations();
        this.denominations.forEach(currency -> currencyVsCount.put(currency, 0));
    }

    @Override
    public StringBuilder deposit(HashMap<Integer, Integer> map) {
        StringBuilder stringBuilder = new StringBuilder(BALANCE).append(COLON).append(SPACE);
        String postFix =COMMA + SPACE;
        int sum = 0;
        for (int currency : denominations) {
            int currencyCount = map.get(currency);
            currencyCount += currencyVsCount.get(currency);
            currencyVsCount.put(currency, currencyCount);
            sum += currency * currencyCount;
            stringBuilder.append(currency).append(POSTFIX).append(EQUAL_SIGN).append(currencyCount).append(postFix);
        }
        totalAmount = sum;
        stringBuilder.append(TOTAL).append(EQUAL_SIGN).append(totalAmount);
        return stringBuilder;
    }

    @Override
    public StringBuilder withdraw(HashMap<Integer, Integer> map) {
        StringBuilder stringBuilder = new StringBuilder(BALANCE).append(COLON).append(SPACE);
        int sum = 0;
        for (int currency : denominations) {
            int curr = map.get(currency);
            int prev = currencyVsCount.get(currency);
            int now = prev - curr;
            currencyVsCount.put(currency, now);
            stringBuilder.append(currency).append(POSTFIX).append(EQUAL_SIGN).append(now).append(COMMA).append(SPACE);
            sum += currency * now;
        }
        totalAmount = sum;
        stringBuilder.append(TOTAL).append(EQUAL_SIGN).append(totalAmount);
        return stringBuilder;
    }

    @Override
    public int getAvailableBalance() {
        return totalAmount;
    }

    public List<Integer> getAllDenominations() {
        return denominations;
    }

    @Override
    public int getDenominationCount(int currency) {
        return currencyVsCount.get(currency);
    }

    @Override
    public void incrementDepositOpCnt() {
        depositOpCnt++;
    }

    @Override
    public void incrementWithdrawOpCnt() {
        withdrawOpCnt++;
    }

    @Override
    public int getDepositOpCnt() {
        return depositOpCnt;
    }

    @Override
    public int getWithdrawOpCnt() {
        return withdrawOpCnt;
    }
}
