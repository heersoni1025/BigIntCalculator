import java.util.ArrayList;
import java.util.List;

class BigInt implements Comparable<BigInt> {

    List<Integer> ls;
    Boolean isNeg = false;


    BigInt(String a){
        ls = new ArrayList<>();
        if (a == null || a.isEmpty()) {
            ls.add(0);
            isNeg = false;
            return;
        }


        int startIndex = 0;
        if (a.charAt(0) == '-') {
            isNeg = true;
            startIndex = 1;
        } else if (a.charAt(0) == '+') {
            startIndex = 1;
        }
        while (startIndex < a.length() && a.charAt(startIndex) == '0') {
            startIndex++;
        }

        for (int i = a.length() - 1; i >= startIndex; i--) {
            ls.add(a.charAt(i) - '0');
        }

        if (ls.isEmpty()) {
            ls.add(0);
            isNeg = false;
        }


    }


    /**
     * Checks whether this BigInt is equal to another object
     *
     * @param o The object to compare with this BigInt.
     * @return True if the object is a BigInt and represents the same integer, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BigInt)) {
            return false;
        }

        BigInt other = (BigInt) o;
        return this.isNeg == other.isNeg && this.ls.equals(other.ls);
    }


    /**
     * compares this BigInt with another BigInt
     *
     * @param o The BigInt to compare with
     * @return -1 if this < o, 1 if this > o, and 0 if they are equal.
     */
    @Override
    public int compareTo(BigInt o) {
        if (this.isNeg && !o.isNeg) {
            return -1;
        } else if (!this.isNeg && o.isNeg) {
            return 1;
        }

        if (this.ls.size() < o.ls.size()) {
            return -1;
        } else if (this.ls.size() > o.ls.size()) {
            return 1;
        }
        for (int i = this.ls.size() - 1; i >= 0; i--) {

            int thisDigit = this.ls.get(i);
            int otherDigit = o.ls.get(i);

            if (thisDigit < otherDigit) {
                if (this.isNeg) {
                    return 1;
                } else {
                    return -1;
                }
            }

            else if (thisDigit > otherDigit) {
                if (this.isNeg) {
                    return -1;
                } else {
                    return 1;
                }
            }}

        return 0;

    }

    /**
     * Converts this BigInt to its string representation
     * @return A string representation of this BigInt
     */
    @Override
    public String toString() {
        if (ls.size() == 1 && ls.get(0) == 0) {
            return "0";
        }

        StringBuilder sb = new StringBuilder();
        if (isNeg) {
            sb.append("-");
        }

        for (int i = ls.size() - 1; i >= 0; i--) {
            sb.append(ls.get(i));
        }

        return sb.toString();
    }

    /**
     * Adds this BigInt with another BigInt and returns the result as a new BigInt.
     *
     * @param b2 The BigInt to add.
     * @return A new BigInt representing the sum of this and b2.
     */
    BigInt add(BigInt b2) {
        // notes:    cases: if this => b2
        //if this is negative and b2 is not
        // if this is positive and b2 is negative
        //both negative

        if (this.isNeg == b2.isNeg) {
            if (this.isNeg == true) {
                BigInt result = addPositive(b2);
                result.isNeg = true;
                return result;
            }
            else{
                BigInt result = addPositive(b2);
                result.isNeg = false;
                return result;}
        } else {
            if (this.compareMagnitude(b2) >= 0) {
                BigInt result = this.subPositive(b2);
                result.isNeg = this.isNeg;

                return result;}


            else {
                BigInt result = b2.subPositive(this);
                result.isNeg = b2.isNeg;
                return result;
            }
        }


    }

    /**
     * Compares the magnitude (absolute value) of this BigInt with another
     * @param o The BigInt to compare with.
     * @return -1 if the magnitude of this < o, 1 if the magnitude of this > o, and 0 if they are equal.
     */
    private int compareMagnitude(BigInt o) {
        if (this.ls.size() < o.ls.size()) {
            return -1;
        } else if (this.ls.size() > o.ls.size()) {
            return 1;
        }

        for (int i = this.ls.size() - 1; i >= 0; i--) {
            int thisDigit = this.ls.get(i);
            int otherDigit = o.ls.get(i);

            if (thisDigit < otherDigit) {
                return -1;
            } else if (thisDigit > otherDigit) {
                return 1;
            }
        }
        return 0;
    }

    /**
     * Subtracts another BigInt from this BigInt and returns the result as a new BigInt.
     *
     * @param b2 The BigInt to subtract.
     * @return A new BigInt representing the difference between this and b2.
     */
    BigInt sub(BigInt b2) {
        if (this.isNeg != b2.isNeg) {// Numbers have opposite signs
            BigInt result = addPositive(b2);
            result.isNeg = this.isNeg;
            return result;
        } else {
            // Both numbers have the same sign
            if (this.compareMagnitude(b2) >= 0) {
                BigInt result = subPositive(b2);
                result.isNeg = this.isNeg;
                return result;
            } else {
                BigInt result = b2.subPositive(this);
                result.isNeg = !this.isNeg;
                return result;
            }
        }
    }

    /**
     * Multiplies this BigInt with another BigInt and returns the result as a new BigInt.
     *
     * @param b2 The BigInt to multiply with.
     * @return A new BigInt representing the product of this and b2.
     */
    BigInt mul(BigInt b2) {
        BigInt result = mulPositive(b2);

        if (this.isNeg == b2.isNeg) {
            result.isNeg = false;
        } else {
            result.isNeg = true;
        }

        return result;
    }


    /**
     * Creates a deep copy of this BigInt instance
     *
     * @return a new BigInt instance representing the same value
     */
    BigInt copy() {
        BigInt copy = new BigInt("0");
        copy.ls = new ArrayList<>(this.ls);
        copy.isNeg= this.isNeg;
        return copy;
    }

    /**
     * Creates a negated copy of this BigInt instance
     *
     * @return A new BigInt instance representing the negated value
     */
    BigInt negate() {
        BigInt negated = this.copy();
        negated.isNeg = !this.isNeg;

        return negated;
    }

    /**
     * Checks if this BigInt and another have different signs.
     *
     * @param b The other BigInt to check.
     * @return True if the signs are different, false otherwise.
     */
    private boolean areDifferentSigns(BigInt b) {
        return this.isNeg != b.isNeg;
    }


    /**
     * Adds two BigInt objects (positive values only).
     *
     * @param b The BigInt to add
     * @return A new BigInt that is the sum of the two numbers
     */
    public BigInt addPositive(BigInt b) {
        List<Integer> result = new ArrayList<>();
        int carry = 0;

        int maxLength = Math.max(this.ls.size(), b.ls.size());
        for (int i = 0; i < maxLength; i++) {
            int digit1 = (i < this.ls.size()) ? this.ls.get(i) : 0;
            int digit2 = (i < b.ls.size()) ? b.ls.get(i) : 0;

            int sum = digit1 + digit2 + carry;
            result.add(sum % 10);
            carry = sum / 10;
        }

        if (carry > 0) {
            result.add(carry);
        }


        BigInt sum = new BigInt("");
        sum.ls = result;

        return sum;
    }

    /**
     * Divides this BigInt by another BigInt and returns the quotient as a new BigInt.
     *
     * @param divisor The BigInt to divide by.
     * @return A new BigInt representing the quotient.
     */
    BigInt div(BigInt b2) {
        if (b2.equals(new BigInt("0"))) {
            throw new IllegalArgumentException("Divide by zero");
        } else if (!this.isNeg && !b2.isNeg) {
            return this.divPositives(b2);
        } else if (!this.isNeg) {
            return this.divPositives(b2.negate()).negate();
        } else if (!b2.isNeg) {
            return this.negate().divPositives(b2).negate();
        } else {
            return this.negate().divPositives(b2.negate());
        }
    }

    private BigInt divPositives(BigInt divisor) {
        BigInt originalDividend = this.copy();
        BigInt dividend = divisor.copy();

        List<BigInt> previousDivisors = new ArrayList<>();
        while (dividend.compareTo(originalDividend) <= 0) {
            previousDivisors.add(dividend.copy());
            dividend = dividend.mul(new BigInt("2"));
        }

        BigInt quotient = new BigInt("0");
        while (originalDividend.compareTo(divisor) >= 0) {
            while (previousDivisors.get(previousDivisors.size() - 1).compareTo(originalDividend) > 0) {
                previousDivisors.remove(previousDivisors.size() - 1);
            }
            BigInt subtrahend = previousDivisors.get(previousDivisors.size() - 1);
            originalDividend = originalDividend.sub(subtrahend);
            BigInt power = new BigInt("1");
            for (int i = 1; i < previousDivisors.size(); i++) {
                power = power.mul(new BigInt("2"));
            }
            quotient = quotient.add(power);
            previousDivisors.remove(previousDivisors.size() - 1);
        }

        return quotient;
    }


    /**
     * Subtracts one positive BigInt from another and returns the result as a new BigInt.
     *
     * @param b The BigInt to subtract.
     * @return A new BigInt representing the difference between the two positive numbers.
     */
    BigInt subPositive(BigInt b) {
        List<Integer> result = new ArrayList<>();
        int borrow = 0;

        for (int i = 0; i < this.ls.size(); i++) {
            int digit1 = this.ls.get(i);
            int digit2 = (i < b.ls.size()) ? b.ls.get(i) : 0;


            int diff = digit1 - digit2 - borrow;
            if (diff < 0) {
                diff += 10;
                borrow = 1;
            } else {
                borrow = 0;
            }

            result.add(diff);
        }

        while (result.size() > 1 && result.get(result.size() - 1) == 0) {
            result.remove(result.size() - 1);
        }

        BigInt difference = new BigInt("");
        difference.ls = result;
        difference.isNeg = false;
        return difference;
    }

    /**
     * Multiplies two non-negative BigInt numbers and returns the result as a new BigInt
     *
     * @param b the BigInt to multiply with this BigInt
     * @return a new BigInt representing the product of this and b
     */
    private BigInt mulPositive(BigInt b) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < this.ls.size() + b.ls.size(); i++) {
            result.add(0);
        }

        for (int i = 0; i < this.ls.size(); i++) {
            int carry = 0;
            for (int j = 0; j < b.ls.size(); j++) {
                int product = this.ls.get(i) * b.ls.get(j) + result.get(i + j) + carry;
                result.set(i + j, product % 10);
                carry = product / 10;
            }
            if (carry > 0) {
                int current = result.get(i + b.ls.size());
                result.set(i + b.ls.size(), current + carry);
            }
        }

        while (result.size() > 1 && result.get(result.size() - 1) == 0) {
            result.remove(result.size() - 1);
        }

        BigInt product = new BigInt("");
        product.ls = result;
        product.isNeg = false;
        return product;
    }

  
}
