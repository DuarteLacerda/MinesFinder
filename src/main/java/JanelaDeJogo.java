import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JanelaDeJogo extends JFrame {
    private BotaoCampoMinado[][] botoes;
    private CampoMinado campoMinado;

    private TabelaRecordes recordes;

    private JPanel painelJogo; // painel do jogo. O nome é definido no modo Design, em "field name"

    public JanelaDeJogo(CampoMinado campoMinado, TabelaRecordes recordes) {
        this.campoMinado = campoMinado;
        this.recordes = recordes;
        var nrLinhas = campoMinado.getNrLinhas();
        var nrColunas = campoMinado.getNrColunas();
        this.botoes = new BotaoCampoMinado[nrLinhas][nrColunas];
        painelJogo.setLayout(new GridLayout(nrLinhas, nrColunas));
        // Criar e adicionar os botões à janela
        for (int linha = 0; linha < nrLinhas; ++linha) {
            for (int coluna = 0; coluna < nrColunas; ++coluna) {
                botoes[linha][coluna] = new BotaoCampoMinado(linha, coluna);
                botoes[linha][coluna].addActionListener(
                        this::btnCampoMinadoActionPerformed);
                botoes[linha][coluna].addMouseListener(mouseListener);
                botoes[linha][coluna].addKeyListener(keyListener);
                painelJogo.add(botoes[linha][coluna]);
            }
        }
        setContentPane(painelJogo);
        // Destrói esta janela, removendo-a completamente da memória.
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        // Causes this Window to be sized to fit the preferred size and layouts of its subcomponents.
        pack();
        setVisible(true);
    }

    public void marcarQuadricula(int x, int y) {
        switch (campoMinado.getEstadoQuadricula(x, y)) {
            case CampoMinado.TAPADO -> campoMinado.marcarComoTendoMina(x, y);
            case CampoMinado.MARCADO -> campoMinado.marcarComoSuspeita(x, y);
            case CampoMinado.DUVIDA -> campoMinado.desmarcarQuadricula(x, y);
        }
        actualizarEstadoBotoes();
    }

    public void btnCampoMinadoActionPerformed(ActionEvent e) {
        var botao = (BotaoCampoMinado) e.getSource();
        int x = botao.getLinha();
        int y = botao.getColuna();
        campoMinado.revelarQuadricula(x, y);
        actualizarEstadoBotoes();

        if (campoMinado.isJogoTerminado()) {
            if (campoMinado.isJogadorDerrotado()) {
                JOptionPane.showMessageDialog(this, "Oh 😭, rebentou uma mina. Custou-te " +
                                (campoMinado.getDuracaoJogo() / 1000) + " segundos de vida!",
                        "Perdeu😵‍💫!", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Parabéns 👏. Conseguis-te descobrir todas as minas em " +
                                (campoMinado.getDuracaoJogo() / 1000) + " segundos!",
                        "Vitória🏆", JOptionPane.INFORMATION_MESSAGE);

                boolean novoRecorde=campoMinado.getDuracaoJogo()<recordes.getTempo();
                if (novoRecorde) {
                    String nome=JOptionPane.showInputDialog("Introduza o seu nome");
                    recordes.setRecorde(nome, campoMinado.getDuracaoJogo());
                }
                dispose();
            }
        }
    }

    private void actualizarEstadoBotoes() {
        for (int x = 0; x < campoMinado.getNrLinhas(); x++) {
            for (int y = 0; y < campoMinado.getNrColunas(); y++) {
                botoes[x][y].setEstado(campoMinado.getEstadoQuadricula(x, y));
            }
        }
    }

    MouseListener mouseListener = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getButton() != MouseEvent.BUTTON3) {
                return;
            }
            var botao = (BotaoCampoMinado) e.getSource();
            var x = botao.getLinha();
            var y = botao.getColuna();

            marcarQuadricula(x, y);
        }
    };

    KeyListener keyListener = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            var botao = (BotaoCampoMinado) e.getSource();
            var x = botao.getLinha();
            var y = botao.getColuna();
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP -> botoes[--x < 0 ? campoMinado.getNrLinhas() - 1 : x][y].requestFocus();
                case KeyEvent.VK_DOWN -> botoes[(x + 1) % campoMinado.getNrLinhas()][y].requestFocus();
                case KeyEvent.VK_LEFT -> botoes[x][--y < 0 ? campoMinado.getNrColunas() - 1 : y].requestFocus();
                case KeyEvent.VK_RIGHT -> botoes[x][(y + 1) % campoMinado.getNrColunas()].requestFocus();
                case KeyEvent.VK_M -> {
                    marcarQuadricula(x, y);
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {}
    };

}
