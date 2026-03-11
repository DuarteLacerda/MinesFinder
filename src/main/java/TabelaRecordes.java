import java.io.Serializable;
import java.util.ArrayList;

public class TabelaRecordes implements Serializable {
    private String nome;
    private int tempo;

    private TabelaRecordes recordes;

    private CampoMinado campoMinado;

    private transient ArrayList<TabelaRecordesListener> listeners;

    public TabelaRecordes() {
        this.nome = "Anónimo";
        this.tempo = 9999999;
        listeners = new ArrayList<>();
    }

    public void JanelaDeJogo(CampoMinado campo) {
        this.campoMinado = campo;
    }

    public String getNome() {
        return nome;
    }

    public int getTempo() {
        return tempo;
    }

    public void setRecorde(String nome, long tempo) {
        this.nome = nome;
        this.tempo = (int) tempo;
        notifyRecordesActualizados();
    }

    public void addTabelaRecordesListener(TabelaRecordesListener list) {
        if (listeners == null) listeners = new ArrayList<>();
        listeners.add(list);
    }
    public void removeTabelaRecordesListener(TabelaRecordesListener list) {
        if (listeners != null) listeners.remove(list);
    }

    private void notifyRecordesActualizados() {
        if (listeners != null) {
            for (TabelaRecordesListener list : listeners)
                list.recordesActualizados(this);
        }
    }
}
