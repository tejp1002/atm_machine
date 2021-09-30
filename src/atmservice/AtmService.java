package atmservice;

public interface AtmService {
    /**
     *  Asks for denominations and it's count to deposit.
     *
     *  1. Initialize failure, totalCurrencyCount and index i to zero
     *  2. boolean flag to true.
     *  3. Empty map of <Integer, Integer>
     *  4. StringBuilder with initial String
     *  5. Asks for count of each denomination to deposit
     *      if count < 0
     *          increment failure
     *          if failure <= 3
     *              a. ask again for count of denomination
     *          else
     *              terminate the deposit process and ask again
     *      else
     *          put denomination and count in map.
     *          append the denomination and count info in stringBuilder to show if count value is less than zero
     *          Add count to totalCurrencyCount.
     *  6. if totalCurrencyCount is zero than terminate the deposit operation
     *  7. show the available balance and denominations, and it's count
     *
     */
    void deposit();

    /**
     *  Asks amount to withdraw and show the info of available denominations, and it's count.
     *
     *  1. Fetches the available balance in atm
     *  2. if amount <= 0 or amount > availableBalance
     *      terminate the withdrawal operation
     *  3. initialize map to empty map of <Integer, Integer>
     *  4. for each denomination
     *      if (amount % denomination) = amount or denomination == 1
     *          get the count for denomination
     *          if count <=  amount / denomination
     *              put denomination and (amount / denomination) in map
     *              amount = amount % denomination
     *          else
     *              set amount to (amount - (count * denomination))
     *              put denomination and count in map
     *  5. If amount != 0 i.e. withdrawn amount can be dispensed in the available denominations
     *      Get the info of available denominations
     *      convert into words
     *  6. else
     *      get the info of available denominations after withdraw
     *  8.show the info fetched in step 5 or 6
     *
     */
    void withdraw();

    /**
     * Show options to choose i.e. deposit, withdraw
     */
    void showOptions();

    /**
     * To show the info  denominations, and it's count after deposit and withdraw
     *
     * @param stringBuilder the stringBuilder.
     */
    void showTransactionOutput(StringBuilder stringBuilder);

    /**
     * @return the next input on ATM screen.
     */
    int getInput();
}
