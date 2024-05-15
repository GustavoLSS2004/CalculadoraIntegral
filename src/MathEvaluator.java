import java.util.Stack;

public class MathEvaluator {

    public double evaluate(String expression) {
        Stack<Double> numbers = new Stack<>();
        Stack<String> operators = new Stack<>();

        expression = expression.replaceAll("\\s+", ""); // Remove white spaces

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (Character.isDigit(c) || (c == '-' && (i == 0 || expression.charAt(i - 1) == '('))) {
                StringBuilder sb = new StringBuilder();
                if (c == '-') {
                    sb.append(c);
                    i++;
                }
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    sb.append(expression.charAt(i++));
                }
                i--; // Step back because the next character is not a digit
                numbers.push(Double.parseDouble(sb.toString()));
            } else if (c == '(') {
                operators.push(String.valueOf(c));
            } else if (c == ')') {
                while (!operators.peek().equals("(")) {
                    numbers.push(applyOperator(operators.pop(), numbers.pop(), numbers.pop()));
                }
                operators.pop();
                // Check if the operator at the top of the stack is a function and apply it
                if (!operators.isEmpty() && isFunction(operators.peek())) {
                    numbers.push(applyFunction(operators.pop(), numbers.pop()));
                }
            } else if (isOperator(c) || isFunction(expression.substring(i))) {
                if (isFunction(expression.substring(i))) {
                    String function = extractFunction(expression.substring(i));
                    operators.push(function);
                    i += function.length() - 1;
                } else {
                    while (!operators.isEmpty() && precedence(c) <= precedence(operators.peek().charAt(0))) {
                        numbers.push(applyOperator(operators.pop(), numbers.pop(), numbers.pop()));
                    }
                    operators.push(String.valueOf(c));
                }
            }
        }

        while (!operators.isEmpty()) {
            numbers.push(applyOperator(operators.pop(), numbers.pop(), numbers.pop()));
        }

        return numbers.pop();
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    private boolean isFunction(String s) {
        return s.startsWith("sin") || s.startsWith("cos") || s.startsWith("tan") || s.startsWith("sec") || s.startsWith("ln");
    }

    private String extractFunction(String s) {
        if (s.startsWith("sin")) return "sin";
        if (s.startsWith("cos")) return "cos";
        if (s.startsWith("tan")) return "tan";
        if (s.startsWith("sec")) return "sec";
        if (s.startsWith("ln")) return "ln";
        return "";
    }

    private double applyFunction(String function, double value) {
        switch (function) {
            case "sin":
                return Math.sin(value);
            case "cos":
                return Math.cos(value);
            case "tan":
                return Math.tan(value);
            case "sec":
                return 1 / Math.cos(value);
            case "ln":
                return Math.log(value);
            default:
                throw new IllegalArgumentException("Unknown function: " + function);
        }
    }

    private int precedence(char operator) {
        if (operator == '+' || operator == '-') return 1;
        if (operator == '*' || operator == '/') return 2;
        if (operator == '^') return 3;
        return 0;
    }

    private double applyOperator(String operator, double b, double a) {
        switch (operator.charAt(0)) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) throw new ArithmeticException("Division by zero!");
                return a / b;
            case '^':
                return Math.pow(a, b);
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }
}