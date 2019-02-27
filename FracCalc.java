import java.util.*;
public class FractionCalculator {
    private static String numerator;
    private static String denominator;
    private static boolean negativeNum;
    private static boolean negativeDen;

    public static boolean validFrac(String fraction) {
            negativeDen = false;
            negativeNum = false;
        if (fraction.contains("/")) {
            if (fraction.substring(0, 1).equals("-")) {
                numerator = fraction.substring(1, fraction.indexOf("/"));
                negativeNum = true;
            } else {
                numerator = fraction.substring(0, fraction.indexOf("/"));
            }

            if (fraction.substring(fraction.indexOf("/") + 1, fraction.indexOf("/") + 2).equals("-")) {
                denominator = fraction.substring(fraction.indexOf("/") + 2);
                negativeDen = true;
            } else {
                denominator = fraction.substring(fraction.indexOf("/") + 1);
            }

        } else {
            numerator = fraction;
            denominator = "1";
            if (fraction.substring(0, 1).equals("-")) {
                numerator = fraction.substring(1);
                negativeNum = true;
            }
        }

        return isNumber(numerator) && isNumber(denominator) && !denominator.equals("0");

    }

    public static boolean isNumber(String num) {
        boolean valid = true;
        for (int i = 0; i < num.length(); i++) {
            if (!Character.isDigit(num.charAt(i))) {
                valid = false;
            }
        }
        return valid;
    }

    public static Fraction getFraction(Scanner scan) {
        System.out.print("Enter a fraction (a/b) or integer (a): ");
        String fraction = scan.nextLine();
        while (!validFrac(fraction)) {
            System.out.print("Invalid fraction. Please enter (a/b) or (a): ");
            fraction = scan.nextLine();
        }

        int intNumerator = Integer.valueOf(numerator);
        if (negativeNum) {
            intNumerator *= -1;
        }
        int intDenominator = Integer.valueOf(denominator);
        if (negativeDen) {
            intDenominator *= -1;
        }
        return new Fraction(intNumerator, intDenominator);
    }

    public static char getOperation(Scanner scan){
        System.out.print("Enter an operation (+, -, *, /, =, or q to quit): ");
        String operation = scan.nextLine();
        while (!(operation.equals("+") || operation.equals("-") || operation.equals("*") || operation.equals("/") || operation.equals("=") || operation.equalsIgnoreCase("q"))) {
            System.out.print("Invalid operation (+, -, *, /, =, or q to quit): ");
            operation = scan.nextLine();
        }
        return  operation.charAt(0);
    }


    public static void main(String[] args) {
        Scanner scanStr = new Scanner(System.in);
        char operation;

        do{
            operation = getOperation(scanStr);
            System.out.println();

            if (operation != 'q' && operation != 'Q'){
                Fraction frac1 = getFraction(scanStr);
                System.out.println();

                Fraction frac2 = getFraction(scanStr);
                System.out.println();

                String answer = "";
                switch (operation){
                    case '+':
                        answer = " = " + frac1.add(frac2);
                        break;
                    case '-':
                        answer = " = " + frac1.subtract(frac2);
                        break;
                    case '*':
                        answer = " = " + frac1.multiply(frac2);
                        break;
                    case '/':
                        if (frac2.toDouble() == 0){
                            answer = " = undefined";
                        } else {
                            answer = " = " + frac1.divide(frac2);
                        }
                        break;
                    case '=':
                        answer = " is " + frac1.equals(frac2);
                        break;
                }

                System.out.println(frac1.toString() + " " + operation + " " + frac2.toString() + answer);

            }
        }while(operation != 'q' && operation != 'Q' );
   }
}

class Fraction {
    private int numerator;
    private int denominator;

    public Fraction (int numerator, int denominator){
        if (denominator == 0){
            throw new IllegalArgumentException("denominator can't be zero");
        }
        else{
            if (denominator > 0){
                this.numerator = numerator;
                this.denominator = denominator;
            }
            else {
                this.numerator = -(numerator);
                this.denominator = -(denominator);
            }
        }
    }

    public Fraction (int numerator){
        this.numerator = numerator;
        this.denominator = 1;
    }

    public Fraction () {
        this.numerator = 0;
        this.denominator = 1;
    }

    public int getNumerator(){
        return numerator;
    }

    public int getDenominator(){
        return denominator;
    }

    public String toString(){
        if (numerator == 0){
            return "0";
        } else if (denominator == 1){
            return "" + numerator;
        }else {
            return numerator + "/" + denominator;
        }
    }

    public double toDouble(){
        return (double)numerator/denominator;
    }

    public Fraction add(Fraction other){
        int newDenominator = this.denominator*other.denominator;
        int newNumerator = (this.numerator*other.denominator) + (other.numerator*this.denominator);

        Fraction sum = new Fraction(newNumerator, newDenominator);

        return sum.toLowestTerms();
    }

    public Fraction subtract(Fraction other){
        int newDenominator = this.denominator*other.denominator;
        int newNumerator = (this.numerator*other.denominator) - (other.numerator*this.denominator);

        Fraction difference = new Fraction(newNumerator, newDenominator);

        return difference.toLowestTerms();
    }

    public Fraction multiply(Fraction other){
        int newNumerator = this.numerator * other.numerator;
        int newDenominator = this.denominator * other.denominator;

        Fraction product = new Fraction(newNumerator, newDenominator);

        return product.toLowestTerms();
    }

    public Fraction divide(Fraction other){
        int newNumerator = this.numerator * other.denominator;
        int newDenominator = this.denominator * other.numerator;

        Fraction quotient = new Fraction(newNumerator, newDenominator);

        return quotient.toLowestTerms();
    }

    public boolean equals(Fraction other){
        return this.toDouble() == other.toDouble();
    }

    public Fraction toLowestTerms(){
        int gcd = Fraction.gcd(this.numerator, this.denominator);
        int newNumerator = this.numerator / gcd;
        int newDenominator = this.denominator / gcd;

        return new Fraction (newNumerator, newDenominator);
    }

    public static int gcd(int a, int b){
        int c;
        do{
            c = a%b;
            a = b;
            b = c;
        }while((a != 0) && (b != 0));

        return a;
    }

}

