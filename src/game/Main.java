package game;

import tabuleiroGUI.CelulaEstado;
import tabuleiroGUI.JPanelTabuleiro;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        Game game = new Game(); // cria instância do jogo

        // cria a interface gráfica do tabuleiro
        SwingUtilities.invokeLater(() -> createAndShowGUI(game));
    }

    /**
     * Cria e exibe a interface gráfica do jogo
     *
     * @param game O objeto game.Game que contém a lógica do jogo.
     */
    /*private static void createAndShowGUI(game.Game game) {
        JFrame frame = new JFrame("Othello"); // cria um novo JFrame com o título "Othello"
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // define o comportamento padrão de exit

        JPanelTabuleiro tabuleiro = new JPanelTabuleiro(game.Game.SIZE, game.Game.SIZE); // cria um novo JPanelTabuleiro com as dimensões especificadas
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
    }*/

    public static void createAndShowGUI(Game game) {
        JFrame frame = new JFrame("Othello");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int boardWidth = 500;
        int boardHeight = 500;
        int cellSize = 10;
        int margin = 20;

        int windowWidth = boardWidth + 2 * margin;
        int windowHeight = boardHeight + 2 * margin + 50; // Added 50 pixels for timer and top margin

        frame.setSize(windowWidth, windowHeight);

        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        frame.add(container);

        JPanel westMargin = new JPanel();
        JPanel eastMargin = new JPanel();
        JPanel northMargin = new JPanel();
        JPanel southMargin = new JPanel();

        westMargin.setBackground(new Color(62, 76, 62));
        eastMargin.setBackground(new Color(62, 76, 62));
        northMargin.setBackground(new Color(62, 76, 62));
        southMargin.setBackground(new Color(62, 76, 62));

        westMargin.setPreferredSize(new Dimension(margin, windowHeight));
        eastMargin.setPreferredSize(new Dimension(margin, windowHeight));
        northMargin.setPreferredSize(new Dimension(windowWidth, margin));
        southMargin.setPreferredSize(new Dimension(windowWidth, margin));

        container.add(westMargin, BorderLayout.WEST);
        container.add(eastMargin, BorderLayout.EAST);
        container.add(northMargin, BorderLayout.NORTH);
        container.add(southMargin, BorderLayout.SOUTH);

        JPanelTabuleiro tabuleiro = new JPanelTabuleiro(Game.SIZE, Game.SIZE, cellSize);
        tabuleiro.setPreferredSize(new Dimension(boardWidth, boardHeight));
        container.add(tabuleiro, BorderLayout.CENTER);

        JPanel playersPanel = new JPanel(new GridLayout(1, 0));
        playersPanel.setBackground(new Color(62, 76, 62));
        Border marginplayer = BorderFactory.createEmptyBorder(20, 20, 20, 20);
        playersPanel.setBorder(marginplayer);

        JPanel player1Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        player1Panel.setBackground(new Color(62, 76, 62));
        JLabel jogador1 = new JLabel("Player 1: ");
        jogador1.setForeground(Color.WHITE);
        player1Panel.add(jogador1);
        playersPanel.add(player1Panel);

        JPanel player2Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        player2Panel.setBackground(new Color(62, 76, 62));
        JLabel jogador2 = new JLabel("Player 2: ");
        jogador2.setForeground(Color.WHITE);
        player2Panel.add(jogador2);
        playersPanel.add(player2Panel);

        JPanel timerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        timerPanel.setBackground(new Color(62, 76, 62));
        JLabel timer = new JLabel("00:00:00");
        timer.setForeground(Color.WHITE);
        timerPanel.add(timer);
        playersPanel.add(timerPanel);

        // Create a timer that updates the timer label every second
        long startTime = System.currentTimeMillis();
        Timer timerThread = new Timer(1000, e -> {
            // Update the timer label
            long time = (System.currentTimeMillis() - startTime) / 1000;
            timer.setText(String.format("%02d:%02d:%02d", time / 3600, (time % 3600) / 60, time % 60));
        });
        timerThread.start();

        container.add(playersPanel, BorderLayout.NORTH);

        frame.setVisible(true);

        tabuleiro.setClickHandler((linha, coluna, estado) -> {
            game.makeMove(linha, coluna);
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
        if (game.isGameOver()) {
            endGame(game);
        }
    }

    private static void endGame(Game game) {
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
