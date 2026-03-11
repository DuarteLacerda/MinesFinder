import java.util.Random;

public class CampoMinado {
    private boolean[][] minas;
    private int[][] estado;

    private int nrLinhas;
    private int nrColunas;
    private int nrMinas;

    public static final int VAZIO = 0;
    /* de 1 a 8 são o número de minas à volta */
    public static final int TAPADO = 9;
    public static final int DUVIDA = 10;
    public static final int MARCADO = 11;
    public static final int REBENTADO = 12;

    private boolean primeiraJogada;
    private boolean jogoTerminado;
    private boolean jogadorDerrotado;

    private long instanteInicioJogo;
    private long duracaoJogo;

    private int casasSegurasRestantes;

    private Random random = new Random();

    public CampoMinado(int nrLinhas, int nrColunas, int nrMinas) {
        this.nrLinhas = nrLinhas;
        this.nrColunas = nrColunas;
        this.nrMinas = nrMinas;
        this.minas = new boolean[nrLinhas][nrColunas];
        this.estado = new int[nrLinhas][nrColunas];

        for (var x = 0; x < nrLinhas; ++x) {
            for (var y = 0; y < nrColunas; ++y) {
                estado[x][y] = TAPADO;
            }
        }

        this.primeiraJogada = true;
        this.jogoTerminado = false;
        this.jogadorDerrotado = false;

        this.casasSegurasRestantes = nrLinhas * nrColunas - nrMinas;
    }

    public int getNrLinhas() {
        return nrLinhas;
    }

    public int getNrColunas() {
        return nrColunas;
    }

    public int getEstadoQuadricula(int x, int y) {
        return estado[x][y];
    }

    public boolean hasMina(int x, int y) {
        return minas[x][y];
    }

    public void revelarQuadricula(int x, int y) {
        if (x < 0 || x >= nrLinhas || y < 0 || y >= nrColunas) return;
        if (jogoTerminado || estado[x][y] != TAPADO) return;

        if (primeiraJogada) {
            primeiraJogada = false;
            colocarMinas(x, y);
            instanteInicioJogo = System.currentTimeMillis();
        }

        if (hasMina(x, y)) {
            estado[x][y] = REBENTADO;
            revelarTabuleiro();
            jogoTerminado = true;
            jogadorDerrotado = true;
            duracaoJogo = System.currentTimeMillis() - instanteInicioJogo;
        } else {
            int minasVizinhas = contarMinasVizinhas(x, y);

            if (minasVizinhas == 0) {
                estado[x][y] = VAZIO;
                casasSegurasRestantes--;
                revelarQuadriculasVizinhas(x, y);
            } else {
                estado[x][y] = minasVizinhas;
                casasSegurasRestantes--;
            }
        }

        if (!jogadorDerrotado && isVitoria()) {
            jogoTerminado = true;
            revelarTabuleiro();
            duracaoJogo = System.currentTimeMillis() - instanteInicioJogo;
        }
    }

    private void revelarTabuleiro() {
        for (int i = 0; i < nrLinhas; i++) {
            for (int j = 0; j < nrColunas; j++) {
                if (hasMina(i, j)) {
                    estado[i][j] = REBENTADO;
                } else {
                    int minas = contarMinasVizinhas(i, j);
                    estado[i][j] = (minas == 0) ? VAZIO : minas;
                }
            }
        }
    }

    private void colocarMinas(int exceptoX, int exceptoY) {
        for (int i = 0; i < nrMinas; i++) {
            int x, y;
            do {
                x = random.nextInt(nrLinhas);
                y = random.nextInt(nrColunas);
            } while (hasMina(x, y) || (x == exceptoX && y == exceptoY));
            minas[x][y] = true;
        }
    }

    public boolean isJogoTerminado() {
        return jogoTerminado;
    }

    public boolean isJogadorDerrotado() {
        return jogadorDerrotado;
    }

    private int contarMinasVizinhas(int x, int y) {
        int numMinas = 0;
        for (int i = Math.max(0, x - 1); i < Math.min(nrLinhas, x + 2); ++i) {
            for (int j = Math.max(0, y - 1); j < Math.min(nrColunas, y + 2); ++j) {
                if (!(i == x && j == y) && hasMina(i, j)) {
                    numMinas++;
                }
            }
        }
        return numMinas;
    }

    private void revelarQuadriculasVizinhas(int x, int y) {
        for (int i = Math.max(0, x - 1); i < Math.min(nrLinhas, x + 2); ++i) {
            for (int j = Math.max(0, y - 1); j < Math.min(nrColunas, y + 2); ++j) {
                if (estado[i][j] == TAPADO) {
                    revelarQuadricula(i, j);
                }
            }
        }
    }

    public boolean isVitoria() {
        return casasSegurasRestantes == 0;
    }

    public void marcarComoTendoMina(int x, int y) {
        if (estado[x][y] == DUVIDA || estado[x][y] == TAPADO) {
            estado[x][y] = MARCADO;
        }
    }

    public void marcarComoSuspeita(int x, int y) {
        if (estado[x][y] == MARCADO || estado[x][y] == TAPADO) {
            estado[x][y] = DUVIDA;
        }
    }

    public void desmarcarQuadricula(int x, int y) {
        if (estado[x][y] == MARCADO || estado[x][y] == DUVIDA) {
            estado[x][y] = TAPADO;
        }
    }

    public long getDuracaoJogo() {
        if (primeiraJogada) return 0;
        return jogoTerminado ? duracaoJogo : System.currentTimeMillis() - instanteInicioJogo;
    }
}