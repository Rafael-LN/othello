/**
 * Objetivo do Jogo:
 * O objetivo do Othello é ter mais peças da sua cor (pretas ou brancas) no tabuleiro do que o seu oponente ao final do jogo.
 *
 * Regras Básicas:
 * 1 - O jogo começa com quatro peças colocadas no centro do tabuleiro, com pretas e brancas alternando.
 * 2 - Cada jogador faz sua jogada colocando uma nova peça em uma posição vazia do tabuleiro.
 * 3 - Para que uma jogada seja válida, o jogador deve colocar sua nova peça de forma que haja pelo menos uma ou
 * mais peças do adversário adjacentes na linha (horizontal, vertical ou diagonal) formada entre a nova peça e uma
 * peça sua já existente no tabuleiro.
 * 4 - Após colocar a nova peça, o jogador captura todas as peças do adversário existentes nas linhas retas entre essa
 * peça e quaisquer outras peças suas existentes no tabuleiro. Todas as peças capturadas assumem a cor do jogador atual.
 * 5 - Os jogadores alternam suas jogadas. Se um jogador não tiver nenhuma jogada válida, ele passa a vez para o adversário.
 * 6 - O jogo continua até que nenhum dos jogadores tenha movimentos válidos ou até que o tabuleiro esteja completamente
 * preenchido.
 * 7 - Quando o jogo termina, o jogador com mais peças da sua cor no tabuleiro é declarado o vencedor. Se ambos os
 * jogadores tiverem o mesmo número de peças, o jogo termina com um empate.
 * */
public class Game {
    //tamanho do tabuleiro (8 linhas por 8 colunas)
    public static final int SIZE = 8;

    //constante que representa uma celula vazia
    public static final int EMPTY = 0;

    //constante que representa uma peça preta
    public static final int BLACK = 1;

    //constante que representa uma peça branca
    public static final int WHITE = 2;


    private int[][] board;
    private int currentPlayer;

    /**
     * Construtor da classe Game.
     * Inicializa o tabuleiro com as peças iniciais.
     */
    public Game() {
        board = new int[SIZE][SIZE];
        currentPlayer = BLACK;

        // inicia o tabuleiro com peças iniciais
        board[3][3] = WHITE;
        board[3][4] = BLACK;
        board[4][3] = BLACK;
        board[4][4] = WHITE;
    }


    /**
     * Verifica se uma jogada é válida.
     * Uma jogada é considerada válida se atender aos seguintes critérios:
     * 1 - A posição onde a jogada é feita está dentro dos limites do tabuleiro, ou seja, a linha row e a coluna col estão
     * entre 0 e SIZE - 1.
     * 2 - A posição onde a jogada é feita está vazia, ou seja, não contém nenhuma peça (representada pelo valor EMPTY
     * no tabuleiro).
     * 3 - Pelo menos uma das oito direções ao redor da posição onde a jogada é feita contém uma sequência de peças do
     * oponente seguida por uma peça do jogador atual.
     *
     * Se todas essas condições forem verdadeiras, a jogada é considerada válida e o método retorna true.
     * Caso contrário, a jogada é considerada inválida e o método retorna false.
     *
     * @param row A linha onde a jogada é feita.
     * @param col A coluna onde a jogada é feita.
     * @return true se a jogada for válida, false caso contrário.
     */
    public boolean isValidMove(int row, int col) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE || board[row][col] != EMPTY) {
            return false;
        }

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                if (checkDirection(row, col, i, j)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Verifica uma direção específica a partir de uma posição no tabuleiro para determinar se uma jogada é válida.
     *
     * Uma jogada é considerada válida se atender aos seguintes critérios:
     * 1 - Posição vazia: A posição onde a jogada é feita deve estar vazia, ou seja, não deve conter nenhuma peça.
     * 2 - Captura de peças do oponente: Pelo menos uma das oito direções ao redor da posição onde a jogada é feita
     * deve resultar na captura de uma ou mais peças do oponente. A captura ocorre quando uma sequência de uma ou
     * mais peças do oponente é seguida por uma peça do jogador atual na mesma direção.
     * 3 - Diferentes direções: A verificação da captura de peças do oponente deve ser realizada em todas as oito
     * direções (horizontal, vertical e diagonais) a partir da posição onde a jogada é feita.
     *
     * Se todos esses critérios forem cumpridos, a jogada é considerada válida. Caso contrário, é inválida e não pode
     * ser realizada no jogo.
     *
     * @param row A linha da posição onde a jogada é feita.
     * @param col A coluna da posição onde a jogada é feita.
     * @param dr A variação na linha para a direção a ser verificada.
     * @param dc A variação na coluna para a direção a ser verificada.
     * @return true se uma sequência de peças do oponente seguida por uma peça do jogador atual é encontrada na direção especificada, false caso contrário.
     */
    private boolean checkDirection(int row, int col, int dr, int dc) {
        int opponent = currentPlayer == BLACK ? WHITE : BLACK;
        int r = row + dr;
        int c = col + dc;

        // percorre na direção especificada enquanto ainda estiver dentro dos limites do tabuleiro e encontrar peças do oponente
        while (r >= 0 && r < SIZE && c >= 0 && c < SIZE && board[r][c] == opponent) {
            r += dr;
            c += dc;
        }

        // verifica se a posição final após percorrer a direção contém uma peça do jogador atual
        if (r >= 0 && r < SIZE && c >= 0 && c < SIZE && board[r][c] == currentPlayer) {
            return true; // uma sequência válida de peças do oponente seguida por uma peça do jogador atual foi encontrada
        }

        return false; // nenhuma sequência válida foi encontrada na direção especificada
    }

    /**
     * Realiza uma jogada
     *
     * @param row A linha onde a jogada é feita.
     * @param col A coluna onde a jogada é feita.
     */
    public void makeMove(int row, int col) {
        // verifica se a jogada é válida
        if (!isValidMove(row, col)) {
            return; // se a jogada não for válida, retorna sem fazer nenhuma alteração no estado do jogo
        }

        // define a posição da jogada com a peça do jogador atual
        board[row][col] = currentPlayer;

        // percorre todas as oito direções a partir da posição da jogada para virar as peças do oponente, se houver capturas
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) {
                    continue; // ignora a direção atual se for a própria posição da jogada
                }
                flipDirection(row, col, dr, dc); // vira as peças na direção especificada
            }
        }

        // alterna o jogador atual para o próximo turno
        currentPlayer = currentPlayer == BLACK ? WHITE : BLACK;
    }

    /**
     * Vira as peças na direção especificada após uma jogada (de branco para preto ou preto para branco)
     *
     * @param row A linha da posição onde a jogada foi feita.
     * @param col A coluna da posição onde a jogada foi feita.
     * @param dr A variação na linha para a direção a ser virada.
     * @param dc A variação na coluna para a direção a ser virada.
     */
    private void flipDirection(int row, int col, int dr, int dc) {
        // determina o jogador oponente com base no jogador atual
        int opponent = currentPlayer == BLACK ? WHITE : BLACK;

        // inicializa as novas coordenadas na direção especificada
        int r = row + dr;
        int c = col + dc;

        // percorre na direção especificada enquanto ainda estiver dentro dos limites do tabuleiro e encontrar peças do oponente
        while (r >= 0 && r < SIZE && c >= 0 && c < SIZE && board[r][c] == opponent) {
            r += dr;
            c += dc;
        }

        // se a última posição após percorrer a direção contiver uma peça do jogador atual, realiza a mudança das peças
        if (r >= 0 && r < SIZE && c >= 0 && c < SIZE && board[r][c] == currentPlayer) {
            r -= dr; // volta para a última posição onde uma peça do jogador atual foi encontrada
            c -= dc; // volta para a última posição onde uma peça do jogador atual foi encontrada

            // realiza a mudança das peças a partir da última posição até voltar à posição inicial da jogada
            while (r != row || c != col) {
                board[r][c] = currentPlayer; // vira a peça para o jogador atual
                r -= dr; // move para a próxima posição na direção oposta
                c -= dc; // move para a próxima posição na direção oposta
            }
        }
    }

    /**
     * Conta o número de peças de um jogador no tabuleiro atual
     *
     * @param player O identificador do jogador cujo número de peças será contado.
     * @return O número de peças do jogador especificado no tabuleiro.
     */
    public int countPieces(int player) {
        int count = 0; // inicializa o contador de peças do jogador
        for (int[] row : board) { // percorre todas as linhas do tabuleiro
            for (int piece : row) { // percorre todas as colunas da linha atual
                if (piece == player) { // verifica se a peça na posição atual pertence ao jogador especificado
                    count++; // incrementa o contador se a peça pertencer ao jogador
                }
            }
        }
        return count; // retorna o número total de peças do jogador no tabuleiro
    }

    /**
     * Determina o vencedor do jogo com base no número de peças de cada jogador.
     *
     * Um jogador ganha no jogo se tiver mais peças no tabuleiro do que seu oponente quando o jogo termina.
     * Determinar o vencedor:
     * 1 - Contagem de Peças: Contabiliza o número de peças de cada jogador no tabuleiro.
     * 2 - Comparação de Contagem: Compara o número de peças do jogador preto (pretas) com o número de peças do
     * jogador branco (brancas).
     * 3 - Determinação do Vencedor: O jogador com mais peças é declarado o vencedor. Se ambos os jogadores tiverem
     * o mesmo número de peças, o jogo é declarado um empate.
     *
     *
     * @return O identificador do jogador vencedor (BLACK, WHITE) ou EMPTY se houver empate.
     */
    public int getWinner() {
        // obtém o número de peças para cada jogador
        int blackCount = countPieces(BLACK);
        int whiteCount = countPieces(WHITE);

        // compara o número de peças de cada jogador para determinar o vencedor
        if (blackCount > whiteCount) {
            return BLACK; // retorna BLACK se o jogador preto tiver mais peças que o jogador branco
        } else if (whiteCount > blackCount) {
            return WHITE; // retorna WHITE se o jogador branco tiver mais peças que o jogador preto
        } else {
            return EMPTY; // retorna EMPTY se houver um empate no número de peças
        }
    }

    /**
     * Obtém o identificador do jogador atual que deve realizar a próxima jogada.
     *
     * @return O identificador do jogador atual (BLACK ou WHITE).
     */
    public int getCurrentPlayer() {
        return currentPlayer; // retorna o identificador do jogador atual
    }

    /**
     * Obtém o estado atual do tabuleiro do jogo Othello.
     *
     * @return Uma matriz bidimensional representando o estado atual do tabuleiro, onde cada elemento
     *         representa uma célula do tabuleiro e pode conter os valores EMPTY, BLACK ou WHITE.
     */
    public int[][] getBoard() {
        return board; // retorna o estado atual do tabuleiro
    }

    /**
     * Verifica se ambos os jogadores não têm mais movimentos válidos.
     * Se nenhum dos jogadores puder fazer uma jogada válida, o jogo termina.
     *
     * @return true se nenhum dos jogadores puder fazer uma jogada válida, false caso contrário.
     */
    public boolean isGameOver() {
        // Verifica se pelo menos um dos jogadores pode fazer uma jogada válida
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (isValidMove(i, j)) {
                    return false; // Se uma jogada válida for encontrada, o jogo ainda não acabou
                }
            }
        }
        return true; // Se nenhum dos jogadores puder fazer uma jogada válida, o jogo está encerrado
    }

}
