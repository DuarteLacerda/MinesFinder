import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MinesFinder extends JFrame {

    private JPanel painelPrincipal;
    private JButton jogoFacilButton;
    private JButton jogoMedioButton;
    private JButton sairButton;
    private JButton jogoDificilButton;
    private JLabel lblNomeFacil;
    private JLabel lblTempoFacil;
    private JLabel lblNomeMedio;
    private JLabel lblTempoMedio;
    private JLabel lblNomeDificil;
    private JLabel lblTempoDificil;

    private TabelaRecordes recordesFacil;
    private TabelaRecordes recordesMedio;
    private TabelaRecordes recordesDificil;

    public MinesFinder(String title) {
        super(title);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(painelPrincipal);

        // Causes this Window to be sized to fit the preferred size and layouts of its subcomponents.
        pack();

        sairButton.addActionListener(this::sairButtonActionPerformed);
        jogoDificilButton.addActionListener(this::jogoDificilActionPerformed);
        jogoMedioButton.addActionListener(this::jogoMedioActionPerformed);
        jogoFacilButton.addActionListener(this::jogoFacilActionPerformed);

        recordesFacil = new TabelaRecordes();
        recordesMedio = new TabelaRecordes();
        recordesDificil = new TabelaRecordes();

        lerRecordesDoDisco();
        lblNomeFacil.setText(recordesFacil.getNome());
        lblTempoFacil.setText(Long.toString(recordesFacil.getTempo()/1000));
        lblNomeMedio.setText(recordesMedio.getNome());
        lblTempoMedio.setText(Long.toString(recordesMedio.getTempo()/1000));
        lblNomeDificil.setText(recordesDificil.getNome());
        lblTempoDificil.setText(Long.toString(recordesDificil.getTempo()/1000));

        recordesFacil.addTabelaRecordesListener(this::recordesFacilActualizado);
        recordesMedio.addTabelaRecordesListener(this::recordesMedioActualizado);
        recordesDificil.addTabelaRecordesListener(this::recordesDificilActualizado);
    }

    public void sairButtonActionPerformed(ActionEvent e) {
        System.exit(0);
    }

    private void jogoFacilActionPerformed(ActionEvent evt) {
        var janela = new JanelaDeJogo(new CampoMinado(9, 9, 10), recordesFacil);
        janela.setSize(800, 600);
        janela.setVisible(true);
    }

    private void jogoMedioActionPerformed(ActionEvent e) {
        var janela = new JanelaDeJogo(new CampoMinado(16, 16, 40), recordesMedio);
        janela.setSize(1200, 800);
        janela.setVisible(true);
    }

    private void jogoDificilActionPerformed(ActionEvent e) {
        var janela = new JanelaDeJogo(new CampoMinado(16, 30, 90), recordesDificil);
        janela.setSize(1800, 1000);
        janela.setVisible(true);
    }

    private void recordesFacilActualizado(TabelaRecordes recordes) {
        lblNomeFacil.setText(recordes.getNome());
        lblTempoFacil.setText(String.valueOf(recordes.getTempo() / 1000));
        guardarRecordesDisco();
    }

    private void recordesMedioActualizado(TabelaRecordes recordes) {
        lblNomeMedio.setText(recordes.getNome());
        lblTempoMedio.setText(String.valueOf(recordes.getTempo() / 1000));
        guardarRecordesDisco();
    }

    private void recordesDificilActualizado(TabelaRecordes recordes) {
        lblNomeDificil.setText(recordes.getNome());
        lblTempoDificil.setText(String.valueOf(recordes.getTempo() / 1000));
        guardarRecordesDisco();
    }

    private void guardarRecordesDisco() {
        File f = new File(System.getProperty("user.home") + File.separator + "minesfinder.recordes");

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f))) {
            oos.writeObject(recordesFacil);
            oos.writeObject(recordesMedio);
            oos.writeObject(recordesDificil);
        } catch (IOException ex) {
            Logger.getLogger(MinesFinder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void lerRecordesDoDisco() {
        File f = new File(System.getProperty("user.home") + File.separator + "minesfinder.recordes");

        if (f.canRead()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
                recordesFacil = (TabelaRecordes) ois.readObject();
                recordesMedio = (TabelaRecordes) ois.readObject();
                recordesDificil = (TabelaRecordes) ois.readObject();
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(MinesFinder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MinesFinder("Mines Finder").setVisible(true));
    }
}