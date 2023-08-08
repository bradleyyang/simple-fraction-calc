// Simple calculator program
// Input: Prompt for an equation containing a series of simple fractions
// Processing: Calculate the result of the equation containing a series of simple fractions
// Output: Display the equation and result

import java.util.*;
import java.lang.Math;

class Calculator {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        boolean rerun = true;
        do {
            Fraction num = new Fraction();
            int[] fracAns = new int[2];
            num.promptNumFrac();
            Fraction[] frac = new Fraction[num.numFrac];
            Fraction[] opers = new Fraction[num.numFrac - 1];
            String[] fracDisplayNumer = new String[num.numFrac];
            String[] fracDisplayDenom = new String[num.numFrac];
            String[] opersDisplay = new String[num.numFrac - 1];
            for (int count = 0; count < num.numFrac; count++) {
                frac[count] = new Fraction();
                frac[count].promptFraction();
                while (true) {
                    if (count != 0 && opers[count - 1].oper == 4 && frac[count].numer == 0) {
                        System.out.println("Cannot divide by 0. Please re-enter the fraction.");
                        frac[count].promptFraction();
                    } else 
                        break;
                }
                if (count != num.numFrac - 1) {
                    opers[count] = new Fraction();
                    opers[count].promptOperator();
                }
            }
            for (int element = 0; element < num.numFrac; element++) {
                fracDisplayNumer[element] = String.valueOf(frac[element].numer);
                fracDisplayDenom[element] = String.valueOf(frac[element].denom);
            }
            for (int item = 0; item < num.numFrac - 1; item++) {
                switch (opers[item].oper) {
                    case (1):
                        opersDisplay[item] = "+";
                        break;
                    case (2):
                        opersDisplay[item] = "-";
                        break;
                    case (3):
                        opersDisplay[item] = "x";
                        break;
                    case (4):
                        opersDisplay[item] = "/";
                        break;
                }
            }
            fracAns = Fraction.calcEquation(frac, opers);
            num.reduceFraction(fracAns);
            num.displayResults(fracDisplayNumer, fracDisplayDenom, opersDisplay, num.reducedAns);
            num.rerunProgram();
            rerun = num.boolRerun;
        } while (rerun);
    }
}

class Fraction extends Calculator {
    int numer, denom, oper, numFrac;
    String input;
    int[] reducedAns = new int[2];
    boolean boolRerun = true;
    public void promptNumFrac() {
        while (true) {
            try {
                System.out.print("Enter number of fractions you will input: ");
                input = sc.next();
                numFrac = Integer.parseInt(input);
                if (numFrac < 2) {
                    System.out.println("Please enter an integer greater than 1");
                } else 
                    break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer");
            }
        }
    }
    public void promptFraction() {
        while (true) {
            try {
                System.out.print("Enter numerator of fraction: ");
                input = sc.next();
                numer = Integer.parseInt(input);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer");
            }
        }
        while (true) {
            try {
                System.out.print("Enter denominator of fraction: ");
                input = sc.next();
                denom = Integer.parseInt(input);
                if (denom != 0)
                    break;
                else
                    System.out.println("Please enter a non-zero integer");
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer");
            }
        }
    }
    public void promptOperator() {
        while (true) {
            System.out.println("Enter the number for which operation you wish to use.");
            System.out.println("1. Add\n2. Subtract\n3. Multiply\n4. Divide");
            input = sc.next();
            if (input.equals("1") || input.equals("2") || input.equals("3") || input.equals("4")) {
                oper = Integer.parseInt(input);
                break;
            } else {
                System.out.println("Please enter 1, 2, 3, or 4");
            }
        }
    }
    public static int[] calcEquation(Fraction[] frac, Fraction[] opers) {
        int termStart = 0, termEnd = 1, tempNumer;
        int[] tempFrac = new int[2];
        int[] tempFrac2 = new int[2];
        tempFrac[0] = 0;
        tempFrac[1] = 0;
        tempFrac2[0] = 1;
        tempFrac2[1] = 1;
        int[] ans = new int[2];
        for (int count = 0; count < opers.length; count++) {
            if (opers[count].oper == 2) {
                frac[count + 1].numer = (frac[count + 1].numer) * (-1);
                opers[count].oper = 1;
            } else if (opers[count].oper == 4) {
                tempNumer = frac[count + 1].numer;
                frac[count + 1].numer = frac[count + 1].denom;
                frac[count + 1].denom = tempNumer;
                opers[count].oper = 3;
            }
        }
        for (int count2 = 0; count2 < opers.length; count2++) {
            if (opers[count2].oper == 1) {
                termEnd = count2;
                for (int count3 = termStart; count3 <= termEnd; count3++) {
                    tempFrac2[0] = tempFrac2[0] * frac[count3].numer;
                    tempFrac2[1] = tempFrac2[1] * frac[count3].denom;
                }
                if (tempFrac[1] != 0) {
                    tempFrac[0] = (tempFrac[0] * tempFrac2[1]) + (tempFrac2[0] * tempFrac[1]);
                    tempFrac[1] = (tempFrac[1] * tempFrac2[1]);
                } else {
                    tempFrac[0] = tempFrac2[0];
                    tempFrac[1] = tempFrac2[1];
                }
                termStart = termEnd + 1;
                tempFrac2[0] = 1;
                tempFrac2[1] = 1;
            }
        }
        termEnd = frac.length - 1;
        for (int count4 = termStart; count4 <= termEnd; count4++) {
            tempFrac2[0] = tempFrac2[0] * frac[count4].numer;
            tempFrac2[1] = tempFrac2[1] * frac[count4].denom;
        }
        if (tempFrac[1] == 0) {
            tempFrac[0] = tempFrac2[0];
            tempFrac[1] = tempFrac2[1];
        } else {
            tempFrac[0] = (tempFrac[0] * tempFrac2[1]) + (tempFrac2[0] * tempFrac[1]);
            tempFrac[1] = (tempFrac[1] * tempFrac2[1]);
        }
        ans[0] = tempFrac[0];
        ans[1] = tempFrac[1];
        return ans;
    }
    public void reduceFraction(int[] fracAns) {
        int reduceCounter = Math.abs(fracAns[0]);
        while (true) {
            if (reduceCounter != 0 && fracAns[0] % reduceCounter == 0 && fracAns[1] % reduceCounter == 0 && reduceCounter != 1) {
                fracAns[0] = fracAns[0] / reduceCounter;
                fracAns[1] = fracAns[1] / reduceCounter;
                reduceCounter = Math.abs(fracAns[0]);
            } else if (reduceCounter > 1) {
                reduceCounter--;
            } else 
                break;
        }
        reducedAns[0] = fracAns[0];
        reducedAns[1] = fracAns[1];
        if (reducedAns[0] < 0 && reducedAns[1] < 0) {
            reducedAns[0] = Math.abs(reducedAns[0]);
            reducedAns[1] = Math.abs(reducedAns[1]);
        } else if (reducedAns[0] > 0 && reducedAns[1] < 0) {
            reducedAns[0] = reducedAns[0] * (-1);
            reducedAns[1] = Math.abs(reducedAns[1]);
        }
    }
    public void displayResults(String[] fracDisplayNumer, String[] fracDisplayDenom, String[] opersDisplay, int[] reducedAns) {
        for (int displayCount = 0; displayCount < fracDisplayNumer.length; displayCount++) {
            System.out.print(fracDisplayNumer[displayCount] + "/" + fracDisplayDenom[displayCount] + " ");
            if (displayCount < fracDisplayNumer.length - 1) {
                System.out.print(opersDisplay[displayCount] + " ");
            }
        }
        System.out.print("= " + Integer.toString(reducedAns[0]) + "/" + Integer.toString(reducedAns[1]));
    }
    public void rerunProgram() {
        while (true) {
            System.out.print("\nDo you want to rerun the program (yes/no): ");
            input = sc.next();
            if (input.toLowerCase().equals("no")) {
                boolRerun = false;
                break;
            } else if (input.toLowerCase().equals("yes")) {
                boolRerun = true;
                break;
            } else {
                System.out.println("Please enter either yes or no");
            }
        }
    }
}