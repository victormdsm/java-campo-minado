package br.com.campominado.visao;

import br.com.campominado.excecao.ExplosaoException;
import br.com.campominado.excecao.SairException;
import br.com.campominado.modelo.Tabuleiro;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class TabuleiroConsole {

    private Tabuleiro tabuleiro;
    private Scanner entrada = new Scanner(System.in);

    public TabuleiroConsole(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;

        executarJogo();
    }

    private void executarJogo() {
        try {
            boolean continuar = true;

            while (continuar) {
                cicloDoJogo();

                System.out.println("Outra pardida? (S/n)");
                String resposta = entrada.nextLine();

                if("n".equalsIgnoreCase(resposta)){
                    continuar = false;
                } else {
                    tabuleiro.reiniciar();
                }
            }
        } catch (SairException e) {
            System.out.println("Tchau!!!");
        } finally {
            entrada.close();
        }
    }

    private void cicloDoJogo() {
        try {

            while (!tabuleiro.objetivoAlcancado()) {
                System.out.println(tabuleiro.toString());

                String digitado = capturarValorDigitado("Digite (x, y): ");

                Iterator<Integer> xy = Arrays.stream(digitado.split(","))
                        .map(e -> Integer.parseInt(e.trim())).iterator();

                digitado = capturarValorDigitado("1 - abrir ou 2 - (Des)Marcar ");

                if("1".equals(digitado)) {
                    tabuleiro.abrir(xy.next(), xy.next());
                } else if ("2".equals(digitado)) {
                    tabuleiro.alternarMarcacao(xy.next(), xy.next());
                    
                }
            }
            System.out.println(tabuleiro);
            System.out.println("voce ganhou");
        } catch (ExplosaoException e) {
            System.out.println(tabuleiro);
            System.out.println("voce perdeu!");
        }
    }

    private String capturarValorDigitado(String texto) {
        System.out.println(texto);
        String digitado = entrada.nextLine();

        if("sair".equalsIgnoreCase(digitado)) {
            throw new SairException();
        }
        return digitado;
    }
}
