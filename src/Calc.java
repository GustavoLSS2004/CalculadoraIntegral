import java.util.Scanner;
public class Calc {
    public static double EquacaoRiemann(double a, double b, int n ){

    }
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        System.out.println("Valor de a: ");
        double a = sc.nextDouble();

        System.out.println("Valor de b: ");
        double b = sc.nextDouble();

        System.out.println("Número de divisões do intervalo(n): ");
        int n = sc.nextInt();

        double vlfinal = EquacaoRiemann();
        System.out.println("Valor final da equação de Riemann: " + vlfinal);
        
        sc.close();
    }
}
