package formula;

/**
 * @author lucifer
 */
public class Fraction {
    int numberator;
    int denominator;

    public Fraction() {
    }

    public Fraction(int numberator, int denominator) {
        this.numberator = numberator;
        this.denominator = denominator;
    }

    @Override
    public String toString() {
        return numberator+"/"+denominator;
    }
}
