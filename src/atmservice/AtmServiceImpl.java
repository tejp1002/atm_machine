package atmservice;

import atm.AtmMachine;
import numtowords.NumbersToWords;
import reader.ScannerReader;

import java.util.HashMap;
import java.util.List;

import static utils.StringUtils.AND;
import static utils.StringUtils.COLON;
import static utils.StringUtils.COMMA;
import static utils.StringUtils.DEPOSIT;
import static utils.StringUtils.DISPENSED;
import static utils.StringUtils.DOLLAR;
import static utils.StringUtils.EMPTY;
import static utils.StringUtils.EQUAL_SIGN;
import static utils.StringUtils.NEW_LINE;
import static utils.StringUtils.POSTFIX;
import static utils.StringUtils.SELECT_OPTION;
import static utils.StringUtils.SPACE;
import static utils.StringUtils.ZERO;

/**
 *  Service class which do all teh calculation for withdraw and deposit and pass the calculation
 *  to ATM object just to update the calculation. So that if in future we persist the ATM info we can
 *  save the calculation without doing much code change.
 */
public class AtmServiceImpl implements AtmService {
    private final ScannerReader scannerReader;

    private final AtmMachine atmMachine;

    private final NumbersToWords numbersToWords;

    /**
     * @param scannerReader the scanner reader.
     * @param atmMachine the atm machine.
     * @param numbersToWords helper to convert number to words.
     *
     * In future if build ATM using spring boot framework we can just autowire instead of passing the all the params.
     */
    public AtmServiceImpl(ScannerReader scannerReader, AtmMachine atmMachine, NumbersToWords numbersToWords) {
        this.scannerReader = scannerReader;
        this.atmMachine = atmMachine;
        this.numbersToWords = numbersToWords;
    }

    @Override
    public void deposit() {
        atmMachine.incrementDepositOpCnt();
        System.out.println("Please mention the count of all denomination.");
        int i = 0, failure = 0, totalCurrencyCount = 0;
        boolean flag = true;
        HashMap<Integer, Integer> map = new HashMap<>();
        StringBuilder stringBuilder = new StringBuilder(DEPOSIT).append(SPACE)
                .append(atmMachine.getDepositOpCnt()).append(COLON).append(SPACE);
        List<Integer> denominations = atmMachine.getAllDenominations();
        while (i < denominations.size()) {
            if (flag) {
                System.out.print(stringBuilder);
                flag = false;
            }
            int denominationUnitValue = denominations.get(i);
            System.out.printf("%s%s%s%s", denominationUnitValue, POSTFIX, COLON, SPACE);
            int currDenominationsCount = scannerReader.getNextInt();
            if (currDenominationsCount < 0) {
                failure++;
                flag = true;
                if (failure <= 3) {
                    System.out.printf("%sIncorrect deposit amount, please try again...%n", NEW_LINE);
                } else {
                    System.out.printf("%sYou have reached maximum failure attempts(3).%s%n", NEW_LINE, NEW_LINE);
                    return;
                }
            } else {
                map.put(denominationUnitValue, currDenominationsCount);
                stringBuilder.append(denominationUnitValue).append(POSTFIX).append(SPACE).append(currDenominationsCount).append(SPACE);
                totalCurrencyCount += currDenominationsCount;
                i++;
            }
        }

        if (totalCurrencyCount == 0) {
            System.out.printf("%sDeposit amount cannot be zero.%s%n", NEW_LINE, NEW_LINE);
            return;
        }

        stringBuilder = atmMachine.deposit(map);
        this.showTransactionOutput(stringBuilder);
    }

    @Override
    public void withdraw() {
        atmMachine.incrementWithdrawOpCnt();;
        System.out.printf("Withdraw %s: ", atmMachine.getWithdrawOpCnt());
        int amount = scannerReader.getNextInt();
        int availableBalance = atmMachine.getAvailableBalance();

        if (amount <= 0 || amount > availableBalance) {
            System.out.println("Incorrect or insufficient funds." + NEW_LINE);
            return;
        }

        StringBuilder stringBuilder = new StringBuilder(DISPENSED);
        stringBuilder.append(COLON).append(SPACE);
        HashMap<Integer, Integer> map = new HashMap<>();
        List<Integer> denominations = atmMachine.getAllDenominations();
        String prefix = EMPTY;
        for (int denomination : denominations) {
            int mod = amount % denomination;
            if (mod != amount || denomination == 1) {
                int count = atmMachine.getDenominationCount(denomination);
                int div = amount / denomination;
                if (div <= count) {
                    amount = amount % denomination;
                    map.put(denomination, div);
                    stringBuilder.append(prefix).append(denomination).append(POSTFIX).append(EQUAL_SIGN).append(div);
                } else {
                    amount -= (count * denomination);
                    map.put(denomination, count);
                    stringBuilder.append(prefix).append(denomination).append(POSTFIX).append(EQUAL_SIGN).append(count);
                }
                prefix = COMMA + SPACE;
            }
        }

        if (amount != 0) {
            stringBuilder = getAvailableDenominationInfo(denominations);
        } else {
            StringBuilder denominationInfo = atmMachine.withdraw(map);
            stringBuilder.append(NEW_LINE).append(denominationInfo);
        }
        this.showTransactionOutput(stringBuilder);
    }

    private StringBuilder getAvailableDenominationInfo(List<Integer> denominations) {
        StringBuilder stringBuilder = new StringBuilder("Requested withdraw amount is not dispensable").append(NEW_LINE);
        stringBuilder.append("Note: At this stage, the ATM has only ");
        String prefix = EMPTY;
        int i = 0;
        for (int denomination : denominations) {
            i++;
            int count = atmMachine.getDenominationCount(denomination);
            String getWords = numbersToWords.getWordsFromNumber(count);
            stringBuilder.append(prefix).append(getWords).append(SPACE).append(denomination).append(SPACE).append(DOLLAR);
            if (!getWords.equals(ZERO)) {
                stringBuilder.append(POSTFIX);
            }
            if (i == denominations.size() - 1) {
                prefix = SPACE + AND + SPACE;
            } else {
                prefix = COMMA + SPACE;
            }
        }
        stringBuilder.append(SPACE).append("bills. So, the withdrawal amount cannot be dispensed.");
        return stringBuilder;
    }

    @Override
    public void showOptions() {
        System.out.println("Choose the option to continue");
        System.out.println(SELECT_OPTION);
    }

    @Override
    public void showTransactionOutput(StringBuilder stringBuilder) {
        System.out.println("-------------------------------\n");
        System.out.println(stringBuilder);
        System.out.println();
    }

    @Override
    public int getInput() {
        this.showOptions();
        return scannerReader.getNextInt();
    }
}
