package src;

import src.tabuleiroGUI.CelulaEstado;
import src.tabuleiroGUI.ClickHandler;
import src.tabuleiroGUI.JPanelTabuleiro;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        testarTabuleiro4EmLinha();
    }

    private static void testarTabuleiro4EmLinha() {
        System.out.println("Teste...");
        JFrame j = new JFrame();
        j.setSize(500,500);
        j.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanelTabuleiro tab = new JPanelTabuleiro(8,8);
        j.setContentPane(tab);

        // para colocar o nosso tabuleiro em readOnly apos inicio do jogo
        //tab.setReadOnly(true);

        j.setVisible(true);

        tab.setCelula(7, 0, CelulaEstado.PRETA);
        tab.setCelula(7, 1, CelulaEstado.BRANCA);


        // TESTES PARA JOGO DO 4 EM LINHA
        // Fase de Jogo
        System.out.println();
        System.out.println("################################");
        System.out.println("       ...Fase de JOGO...");
        System.out.println("################################");
        ClickHandler chJogo = new ClickHandler(){
            @Override
            public void clicked(int linha,int coluna,int estado) {
                System.out.println("...CLICK...");
                System.out.println("linha: " + linha);
                System.out.println("coluna: " + coluna);
                System.out.println("estado anterior: " + CelulaEstado.decodeEstadoString(estado));
                if(estado == CelulaEstado.LIVRE)
                    tab.setCelula(linha, coluna, CelulaEstado.PRETA);
                if(estado == CelulaEstado.PRETA)
                    tab.setCelula(linha, coluna, CelulaEstado.BRANCA);
                if(estado == CelulaEstado.BRANCA)
                    tab.setCelula(linha, coluna, CelulaEstado.LIVRE);
                System.out.println("estado novo: " + tab.getCelulaEstadoString(linha, coluna));

            }
        };

        tab.setClickHandler(chJogo);

    }
}
