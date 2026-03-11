import java.util.ArrayList;

public class SolverCampoMinado {
    private CampoMinado campo;

    public SolverCampoMinado(CampoMinado campo) {
        this.campo = campo;
    }

    /**
     * Executa um passo lógico do resolver.
     * @return true se fez alguma jogada, false se não encontrou jogadas lógicas.
     */
    public boolean passo() {
        boolean fezAlgo = false;

        int linhas = campo.getNrLinhas();
        int colunas = campo.getNrColunas();

        for (int x = 0; x < linhas; x++) {
            for (int y = 0; y < colunas; y++) {
                int estadoAtual = campo.getEstadoQuadricula(x, y);

                // Só interessa se é um número entre 1 e 8
                if (estadoAtual >= 1 && estadoAtual <= 8) {
                    ArrayList<int[]> tapadas = new ArrayList<>();
                    int marcadas = 0;

                    // Verifica células adjacentes
                    for (int i = Math.max(0, x - 1); i < Math.min(linhas, x + 2); i++) {
                        for (int j = Math.max(0, y - 1); j < Math.min(colunas, y + 2); j++) {
                            if (i == x && j == y) continue;
                            int estadoAdj = campo.getEstadoQuadricula(i, j);
                            if (estadoAdj == CampoMinado.TAPADO || estadoAdj == CampoMinado.DUVIDA) {
                                tapadas.add(new int[]{i, j});
                            }
                            if (estadoAdj == CampoMinado.MARCADO) {
                                marcadas++;
                            }
                        }
                    }

                    // Todas as restantes adjacentes são seguras
                    if (marcadas == estadoAtual) {
                        for (int[] pos : tapadas) {
                            campo.revelarQuadricula(pos[0], pos[1]);
                            fezAlgo = true;
                        }
                    }

                    // Todas as tapadas restantes são minas
                    if (tapadas.size() + marcadas == estadoAtual) {
                        for (int[] pos : tapadas) {
                            campo.marcarComoTendoMina(pos[0], pos[1]);
                            fezAlgo = true;
                        }
                    }
                }
            }
        }

        return fezAlgo;
    }

    /**
     * Executa todos os passos lógicos possíveis até não restar mais jogadas óbvias.
     */
    public void resolverTudo() {
        while (passo()) {
            // Continua até não haver jogadas lógicas
        }
    }
}