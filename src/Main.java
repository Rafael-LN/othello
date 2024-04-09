package src;

import src.tabuleiroGUI.CelulaEstado;
import src.tabuleiroGUI.JPanelTabuleiro;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        Game game = new Game(); // cria instância do jogo

        // cria a interface gráfica do tabuleiro
        SwingUtilities.invokeLater(() -> createAndShowGUI(game));
    }

    /**
     * Cria e exibe a interface gráfica do jogo
     *
     * @param game O objeto Game que contém a lógica do jogo.
     */
    private static void createAndShowGUI(Game game) {
        JFrame frame = new JFrame("Othello"); // cria um novo JFrame com o título "Othello"
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // define o comportamento padrão de exit

        JPanelTabuleiro tabuleiro = new JPanelTabuleiro(Game.SIZE, Game.SIZE); // cria um novo JPanelTabuleiro com as dimensões especificadas
        frame.add(tabuleiro); // adiciona o painel do tabuleiro ao frame

        frame.setSize(500,500); // define o tamanho inicial do frame

        //frame.pack();
        frame.setVisible(true); // torna o frame visível na tela

        // configura o handler de cliques para realizar as jogadas no tabuleiro
        tabuleiro.setClickHandler((linha, coluna, estado) -> {
            // faz uma jogada no jogo
            game.makeMove(linha, coluna);

            // atualiza o estado do tabuleiro gráfico com base no estado do jogo
            updateTabuleiro(tabuleiro, game);
        });
    }


    private static void updateTabuleiro(JPanelTabuleiro tabuleiro, Game game) {
        int[][] board = game.getBoard(); // obtém o estado atual do tabuleiro do jogo

        // percorre todas as células do tabuleiro
        for (int i = 0; i < Game.SIZE; i++) {
            for (int j = 0; j < Game.SIZE; j++) {
                // verifica o estado da célula no tabuleiro do jogo e atualiza o estado gráfico correspondente no JPanelTabuleiro
                if (board[i][j] == Game.BLACK) {
                    tabuleiro.setCelula(i, j, CelulaEstado.PRETA); // define a célula como preta no JPanelTabuleiro
                } else if (board[i][j] == Game.WHITE) {
                    tabuleiro.setCelula(i, j, CelulaEstado.BRANCA); // define a célula como branca no JPanelTabuleiro
                }
            }
        }

        // Verifica se o jogo acabou
        if (game.isGameOver()) {
            // Obtém o vencedor do jogo
            int winner = game.getWinner();

            // Exibe a mensagem adequada com base no vencedor
            if (winner == Game.BLACK) {
                JOptionPane.showMessageDialog(null, "O jogador preto venceu!", "Resultado", JOptionPane.INFORMATION_MESSAGE);
            } else if (winner == Game.WHITE) {
                JOptionPane.showMessageDialog(null, "O jogador branco venceu!", "Resultado", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "O jogo terminou em empate!", "Resultado", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
