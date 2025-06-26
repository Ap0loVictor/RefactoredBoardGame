package tabuleiro;

import java.util.Scanner;

public class InstanciarTabuleiro {
    private Scanner scanner = new Scanner(System.in);


    public Tabuleiro configurarTabuleiro() {
        int totalCasas = perguntarNumeroDeCasas();
        return new Tabuleiro(totalCasas);
    }

    public int perguntarNumeroDeCasas() {
        System.out.print("Digite o número de casas do tabuleiro: ");

        while (!scanner.hasNextInt()) {
            System.out.println("Entrada inválida. Digite um número inteiro:");
            scanner.next(); // não libera entrada inválida
        }

        int casas = scanner.nextInt();
        scanner.nextLine(); // limpa a quebra de linha

        while (casas <= 0) {
            System.out.print("Número deve ser maior que zero. Tente novamente: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Entrada inválida. Digite um número inteiro:");
                scanner.next();
            }
            casas = scanner.nextInt();
            scanner.nextLine(); // limpa a quebra de linha
        }
        return casas;
    }

}
