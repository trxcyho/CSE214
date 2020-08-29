/**
 * Equation class computes the mathematics of the calculator
 *
 * @author Tracy Ho
 * 112646529
 * tracy.ho@stonybrook.edu
 * Homework #3
 * CSE214 R02
 * Reema Mittal and Gaurang Khanwalkar
 */
public class Equation {

    private String equation, prefix, postfix, binary, hex;
    private double answer;
    private boolean balanced;
    private EquationStack equationStack = new EquationStack();

    public Equation() {
    }

    /**
     * Argument constructor for the Equation class
     * checks if the equation is balanced and initializes everything else
     * if it is not balanced, then the rest of the variables are initialized to
     * N//A
     *
     * @param newEquation the equation for the class
     */
    public Equation(String newEquation) {
        this.equation = newEquation;
        if (isBalanced()) {
            this.balanced = true;
            this.prefix = inFixToPreFix(equation);
            this.postfix = inFixToPostFix(equation);
            this.answer = evaluate(equation);
            this.binary = decToBin((int) Math.round(answer));
            this.hex = decToHex((int) Math.round(answer));
        } else {
            this.balanced = false;
            this.prefix = "N/A";
            this.postfix = "N/A";
            this.binary = "N/A";
            this.hex = "N/A";
            this.answer = 0;
        }
    }

    public String getEquation() {
        return equation;
    }

    public void setEquation(String equation) {
        this.equation = equation;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPostfix() {
        return postfix;
    }

    public void setPostfix(String postfix) {
        this.postfix = postfix;
    }

    public String getBinary() {
        return binary;
    }

    public void setBinary(String binary) {
        this.binary = binary;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public double getAnswer() {
        return answer;
    }

    public void setAnswer(double answer) {
        this.answer = answer;
    }

    public void setBalanced(boolean balanced) {
        this.balanced = balanced;
    }

    /**
     * Checks if the parenthesis in the equation is balanced
     *
     * @return true if the parenthesis is balanced, false otherwise
     */
    public boolean isBalanced() {
        int openParenthesis = 0;
        int closedParenthesis = 0;
        String temp = equation.replaceAll(" ", "");
        boolean isDigit = true;
        if (temp.equals(""))
            throw new IllegalArgumentException("Please do not enter an " +
              "empty string or spaces");
        for (int i = 0; i < temp.length(); i++) {
            if (temp.charAt(i) == '(')
                openParenthesis++;
            else if (temp.charAt(i) == ')')
                closedParenthesis++;
            if (Character.isLetter(temp.charAt(i)))
                return false;
            if (closedParenthesis > openParenthesis)
                return false;
            if(!Character.isDigit(temp.charAt(i)) && precedence(temp.
              charAt(i)) == 0)
                return false;
            if(!Character.isDigit(temp.charAt(i)))
                isDigit = false;
        }
        if(isDigit)
            return false;
        if(precedence(temp.charAt(temp.length()-1)) >= 2)
            return false;
        if (openParenthesis == closedParenthesis)
            return true;
        else
            return false;
    }

    /**
     * Converts an integer to a binary number
     *
     * @param number integer that needs to be converted to binary
     * @return string representation of the number in binary form
     */
    public String decToBin(int number) {
        String binary = "";
        if (number == 0)
            binary = String.valueOf(number);
        else {
            while (number > 0) {
                binary = number % 2 + binary;
                number /= 2;
            }
        }
        return binary;
    }

    /**
     * converts an integer to a hexadecimal
     *
     * @param number integer that is to be converted to hexadeciaml
     * @return string representation of the number as a hexadecimal
     */
    public String decToHex(int number) {
        String hexadecimal = "";
        int tempValue = 0;
        if (number == 0)
            hexadecimal = "0";
        else {
            while (number > 0) {
                tempValue = number % 16;
                if (tempValue < 10)
                    hexadecimal = tempValue + hexadecimal;
                else {
                    tempValue += 55;
                    hexadecimal = (char) tempValue + hexadecimal;
                }
                number /= 16;
            }
        }
        return hexadecimal;
    }

    /**
     * a table representation of the contents in the class
     *
     * @return a string with the contents of the class
     */
    public String toString() {
        String string = "";
        string = String.format("%-35s%-35s%-35s%.3f%-8s%-15s%-13s", equation,
          prefix, postfix, answer, "", binary, hex) + "\n";
        return string;
    }

    /**
     * converts the infix equation to postfix
     *
     * @param equation the infix equation
     * @return the postfix equation
     */
    public String inFixToPostFix(String equation) {
        String postfix = "";
        char temp;
        equationStack.push("$");
        for (int i = 0; i < equation.length(); i++) {
            char token = equation.charAt(i);
            if (token == ' ')
                continue;
            if (Character.isDigit(token))
                postfix += token;
            else if (token == '(')
                equationStack.push(equation.charAt(i) + "");
            else if (token == ')') {
                temp = equationStack.pop().charAt(0);
                while (temp != '(') {
                    postfix += " ";
                    postfix += temp;
                    temp = equationStack.pop().charAt(0);
                }
            } else {
                temp = equationStack.peek().charAt(0);
                while (precedence(temp) >= precedence(token)) {
                    postfix += " ";
                    postfix += equationStack.pop();
                    temp = equationStack.peek().charAt(0);
                }
                postfix += " ";
                equationStack.push(token + "");
            }
        }
        temp = equationStack.pop().charAt(0);
        while (temp != '$') {
            postfix += " ";
            postfix += temp;
            temp = equationStack.pop().charAt(0);
        }
        return postfix;
    }

    /**
     * method that tells the precedence of the operators
     *
     * @param s the operator
     * @return an int; the higher the int the higher precedence it has
     */
    public int precedence(char s) {
        if (s == '(' || s == ')')
            return 1;
        else if (s == '+' || s == '-')
            return 2;
        else if (s == '*' || s == '/' || s == '%')
            return 3;
        else if (s == '^')
            return 4;
        else
            return 0;
    }

    /**
     * reverses the equation
     *
     * @param inFix the equation that is to be reversed
     * @return the reversed equation
     */
    public String reverseEquation(String inFix) {
        String reverse = "";
        String temp = "";
        for (int i = inFix.length() - 1; i >= 0; i--) {
            if (!Character.isDigit(inFix.charAt(i))) {
                for (int j = temp.length() - 1; j >= 0; j--)
                    reverse += temp.charAt(j);
                temp = "";
            }
            if (Character.isDigit(inFix.charAt(i)))
                temp += inFix.charAt(i) + "";
            else if (inFix.charAt(i) == ')')
                reverse += "(";
            else if (inFix.charAt(i) == '(')
                reverse += ")";
            else
                reverse += inFix.charAt(i);
        }
        if (temp != "") {
            for (int j = temp.length() - 1; j >= 0; j--)
                reverse += temp.charAt(j);
        }
        return reverse;
    }

    /**
     * converts the equation to prefix notation
     *
     * @param equation the equation to be converted
     * @return the prefix notation of the equation
     */
    public String inFixToPreFix(String equation) {
        String temp = reverseEquation(equation);
        String reversePreFix = inFixToPostFix(temp);
        String prefix = reverseEquation(reversePreFix);
        return prefix;
    }

    /**
     * evaluates the equation (solves the equation)
     *
     * @param equation the postfix notation of the equation
     * @return the value of the answer to the equation
     */
    public double evaluate(String equation) {
        String postfix = inFixToPostFix(equation);
        String temp = "";
        for (int i = 0; i < postfix.length(); i++) {
            char token = postfix.charAt(i);
            if (!Character.isDigit(token)) {
                if (!temp.equals("")) {
                    equationStack.push(temp);
                }
                temp = "";
            }
            if (Character.isDigit(token)) {
                temp += postfix.charAt(i) + "";
            } else if (token == ' ') {
                continue;
            } else {
                double int2 = Double.parseDouble(equationStack.pop());
                double int1 = Double.parseDouble(equationStack.pop());
                double answer = 0;
                switch (token) {
                    case '+':
                        answer = int1 + int2;
                        break;
                    case '-':
                        answer = int1 - int2;
                        break;
                    case '*':
                        answer = int1 * int2;
                        break;
                    case '/':
                        answer = int1 / int2;
                        break;
                    case '%':
                        answer = int1 % int2;
                        break;
                    case '^':
                        answer = Math.pow(int1, int2);
                        break;
                }
                equationStack.push(answer + "");
            }
        }
        double solution = Double.parseDouble(equationStack.pop());
        return Math.round(solution * 1000.0) / 1000.0;
    }


}
