import java.io.Serializable;
import java.util.ArrayList;

public class TabelaRecordes implements Serializable {
    //Serializable saves the information of this class in a binary file for persistance

    private String nomeJogador;
    private long tempoDeJogo;

    //transient means that even though this class is serializable, this attribute will not be saved
    private transient ArrayList<TabelaRecordesListener> listeners;


    public TabelaRecordes(){
        tempoDeJogo = 9999999;
        nomeJogador ="n/a";
        listeners=new ArrayList<>();
    }

    public void addTabelaRecordesListener(TabelaRecordesListener list) {
        if (listeners == null) listeners = new ArrayList<>();
        listeners.add(list);
    }
    public void removeTabelaRecordesListener(TabelaRecordesListener list) {
        if (listeners != null){
            listeners.remove(list);
        }
    }

    private void notifyRecordesActualizados() {
        if (listeners != null) {
            for (TabelaRecordesListener list : listeners)
                list.recordesActualizados(this);
        }
    }

    public String getNomeJogador() {
        return nomeJogador;
    }

    public long getTempoDeJogo() {
        return tempoDeJogo;
    }

    public void setRecorde(String jogador, long tempo){
        if (tempo<this.tempoDeJogo) {
            this.nomeJogador = jogador;
            this.tempoDeJogo = tempo;
            notifyRecordesActualizados();
        }
    }
}
