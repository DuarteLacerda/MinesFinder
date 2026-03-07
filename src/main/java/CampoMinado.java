import java.util.Random;

public class CampoMinado {
    private boolean[][] minas;
    private int[][] estado;

    private int nrLinhas; // ou largura
    private int nrColunas; // ou altura
    private int nrMinas;

    public static final int VAZIO = 0;
    /* de 1 a 8 são o número de minas à volta */
    public static final int TAPADO = 9;
    public static final int DUVIDA = 10;
    public static final int MARCADO = 11;
    public static final int REBENTADO = 12;

    private boolean primeiraJogada;
    private boolean jogoterminado;
    private boolean jogadorDerrotado;

    private long instanteInicioJogo;
    private long duracaoJogo;

    public CampoMinado(int nrLinhas, int nrColunas, int nrMinas) {
        this.nrLinhas = nrLinhas;
        this.nrColunas = nrColunas;
        this.nrMinas = nrMinas;
        this.minas = new boolean[nrLinhas][nrColunas]; // Valores começam a false
        this.estado = new int[nrLinhas][nrColunas]; // Valores começam a 0 (VAZIO)

        // Regra 1
        for (var x = 0; x < nrLinhas; ++x) {
            for (var y = 0; y < nrColunas; ++y) {
                estado[x][y] = TAPADO;
            }
        }

        // Regra 3
        primeiraJogada = true;
        jogoterminado = false;
        jogadorDerrotado = false;
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

    // Regra 2
    public void revelarQuadricula(int x, int y) {
        if (x < 0 || x >= nrLinhas || y < 0 || y >= nrColunas) {
            return;
        }

        if (jogoterminado || estado[x][y] != TAPADO) {
            return; // Já foi revelada
        }

        if (primeiraJogada) {
            primeiraJogada = false;
            colocarMinas(x, y);

            instanteInicioJogo = System.currentTimeMillis();
        }

        if (hasMina(x, y)) {
            estado[x][y] = REBENTADO;
            for (var i = 0; i < nrLinhas; ++i) {
                for (var j = 0; j < nrColunas; ++j) {
                    if (hasMina(i, j)) {
                        estado[i][j] = REBENTADO;
                    } else if (estado[i][j] == TAPADO || estado[i][j] == DUVIDA || estado[i][j] == MARCADO) {
                        estado[i][j] = VAZIO;
                    } else {
                        revelarQuadriculasVizinhas(i, j);
                    }
                }
            }

            jogoterminado = true;
            jogadorDerrotado = true;
            duracaoJogo = System.currentTimeMillis() - instanteInicioJogo;
        } else {
            int minasVizinhas = contarMinasVizinhas(x, y);

            if (minasVizinhas == 0) {
                estado[x][y] = VAZIO;
                revelarQuadriculasVizinhas(x, y);
            } else {
                estado[x][y] = minasVizinhas;
            }
        }
        if (!jogadorDerrotado && isVitoria()) {
            jogoterminado = true;

            for (var i = 0; i < nrLinhas; ++i) {
                for (var j = 0; j < nrColunas; ++j) {
                    if (hasMina(i, j)) {
                        estado[i][j] = REBENTADO;
                    } else if (estado[i][j] == TAPADO || estado[i][j] == DUVIDA || estado[i][j] == MARCADO) {
                        estado[i][j] = VAZIO;
                    }
                }
            }

            duracaoJogo = System.currentTimeMillis() - instanteInicioJogo;
        }
    }

    // Regra 3
    private void colocarMinas(int exceptoX, int exceptoY) {
        var aleatorio = new Random();
        var x = 0;
        var y = 0;

        for (var i = 0; i < nrMinas; ++i) {
            do {
                x = aleatorio.nextInt(nrLinhas);
                y = aleatorio.nextInt(nrColunas);
            } while (hasMina(x, y) || (x == exceptoX && y == exceptoY));
            minas[x][y] = true;
        }
    }

    // Regra 4
    public boolean isJogoTerminado() {
        return jogoterminado;
    }

    public boolean isJogadorDerrotado() {
        return jogadorDerrotado;
    }

    // Regra 5
    private int contarMinasVizinhas(int x, int y) {
        var numMinasVizinhas = 0;
        for (var i = Math.max(0, x - 1); i < Math.min(nrLinhas, x + 2); ++i) {
            for (var j = Math.max(0, y - 1); j < Math.min(nrColunas, y + 2); ++j) {
                if (!(i == x && j == y) && hasMina(i, j)) {
                    ++numMinasVizinhas;
                }
            }
        }
        return numMinasVizinhas;
    }

    // Regra 6
    public void revelarQuadriculasVizinhas(int x, int y) {
        for (var i = Math.max(0, x - 1); i < Math.min(nrLinhas, x + 2); ++i) {
            for (var j = Math.max(0, y - 1); j < Math.min(nrColunas, y + 2); ++j) {
                if (estado[i][j] == TAPADO) {
                    revelarQuadricula(i, j);
                }
            }
        }
    }

    // Regra 7
    public boolean isVitoria() {
        for (var x = 0; x < nrLinhas; ++x) {
            for (var y = 0; y < nrColunas; ++y) {
                if (!hasMina(x, y) && estado[x][y] >= TAPADO) {
                    return false;
                }
            }
        }
        return true;
    }

    // Regra 8
    public void marcarComoTendoMina(int x, int y) {
        if (estado[x][y] == DUVIDA || estado[x][y] == TAPADO) {
            estado[x][y] = MARCADO;
        }
    }

    // Regra 9
    public void marcarComoSuspeita(int x, int y) {
        if (estado[x][y] == MARCADO || estado[x][y] == TAPADO) {
            estado[x][y] = DUVIDA;
        }
    }

    // Regra 10
    public void desmarcarQuadricula(int x, int y) {
        if (estado[x][y] == MARCADO || estado[x][y] == DUVIDA) {
            estado[x][y] = TAPADO;
        }
    }

    // Regra 11
    public long getDuracaoJogo() {
        if (primeiraJogada) {
            return 0;
        }
        if (!jogoterminado) {
            return System.currentTimeMillis() - instanteInicioJogo;
        }

        return duracaoJogo;
    }
}