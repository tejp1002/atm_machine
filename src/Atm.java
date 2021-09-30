import atm.AtmMachine;
import atm.AtmMachineImpl;
import atmservice.AtmService;
import atmservice.AtmServiceImpl;
import numtowords.NumbersToWords;
import reader.ScannerReader;

import static utils.StringUtils.INCORRECT_INPUT;

/**
 * Main class to start the ATM.
 * Assumptions
 * 1. In real world, Exit option will not be shown to the user, but to terminate we are showing it as an option as of now.
 * 2. We will provide only numeric keyboard, so char input option will not be taken at all. So did not handle the case here
 *
 */
public class Atm {
    public static void main(String[] args) {
        ScannerReader scanner = new ScannerReader();
        AtmMachine atmMachine = new AtmMachineImpl();
        NumbersToWords numbersToWords = new NumbersToWords();
        AtmService atmService = new AtmServiceImpl(scanner, atmMachine, numbersToWords);
        atmService.showOptions();
        int input = scanner.getNextInt();
        while (input != 3) {
            switch(input) {
                case 1:
                    atmService.deposit();
                    input = atmService.getInput();
                    break;
                case 2:
                    atmService.withdraw();
                    input = atmService.getInput();
                    break;
                default:
                    System.out.println(INCORRECT_INPUT);
                    input = atmService.getInput();
                    break;
            }
        }
    }
}
