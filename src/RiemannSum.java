import java.util.Scanner;
import java.util.function.Function;

public class RiemannSum {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Solicita a função polinomial ao usuário
        System.out.println("Insira a função polinomial (use 'x' como variável):");
        System.out.print("F(x) = ");
        String functionString = scanner.nextLine();

        // Define a função polinomial com base na entrada do usuário
        Function<Double, Double> f = x -> evaluateFunction(functionString, x);

        // Solicita os limites da integral definida ao usuário
        System.out.print("Insira o limite inferior da integral (a): ");
        double a = scanner.nextDouble();
        System.out.print("Insira o limite superior da integral (b): ");
        double b = scanner.nextDouble();

        // Solicita o número de subintervalos ao usuário
        System.out.print("Insira o número de subintervalos (n): ");
        int n = scanner.nextInt();

        // Calcula a largura de cada subintervalo
        double dx = (b - a) / n;

        // Calcula a soma de Riemann
        double riemannSum = 0;
        for (int i = 0; i < n; i++) {
            double x1 = a + i * dx;
            double x2 = x1 + dx;
            double midpoint = (x1 + x2) / 2;
            double height = f.apply(midpoint);
            riemannSum += height * dx;
        }

        System.out.println("A soma de Riemann é: " + riemannSum);

        scanner.close();
    }

    // Função para avaliar a string de função para um determinado valor de x
    private static double evaluateFunction(String function, double x) {
        // Substitui todas as instâncias de 'x' na string da função pelo valor de x
        String expression = function.replaceAll("x", String.valueOf(x));
        // Avalia a expressão usando a função MathEvaluator
        return new MathEvaluator().evaluate(expression);
    }
}