import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MinesFinder extends JFrame {

    private JPanel painelPrincipal;
    private JButton jogoFacilButton;
    private JButton jogoMedioButton;
    private JButton jogoDificilButton;
    private JButton sairButton;
    private JLabel title;
    private JLabel lblRecordeFacil;
    private JLabel lblRecordeMedio;
    private JLabel lblRecordeDificil;

    private TabelaRecordes recordesFacil, recordesMedio, recordesDificil;

    public MinesFinder(String title){
        super(title);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(painelPrincipal);
        // Causes this Window to be sized to fit the preferred size and layouts of its subcomponents.
        pack();
        sairButton.addActionListener(this::sairButtonActionPerformed);
        jogoFacilButton.addActionListener(this::jogoFacilButtonActionPerformed);
        jogoMedioButton.addActionListener(this::jogoMedioButtonActionPerformed);
        jogoDificilButton.addActionListener(this::jogoDificilButtonActionPerformed);

        recordesFacil = new TabelaRecordes();
        recordesMedio = new TabelaRecordes();
        recordesDificil = new TabelaRecordes();
        lerRecordesDoDisco();
        recordesFacilActualizado(recordesFacil);
        recordesMedioActualizado(recordesMedio);
        recordesDificilActualizado(recordesDificil);

        recordesFacil.addTabelaRecordesListener(new TabelaRecordesListener() {
            @Override
            public void recordesActualizados(TabelaRecordes recordes) {
                recordesFacilActualizado(recordes);
            }
        });
        recordesMedio.addTabelaRecordesListener(new TabelaRecordesListener() {
            @Override
            public void recordesActualizados(TabelaRecordes recordes) {
                recordesMedioActualizado(recordes);
            }
        });
        recordesDificil.addTabelaRecordesListener(new TabelaRecordesListener() {
            @Override
            public void recordesActualizados(TabelaRecordes recordes) {
                recordesDificilActualizado(recordes);
            }
        });
    }

    private void recordesFacilActualizado(TabelaRecordes recordes) {
        guardarRecordesDisco();
        lblRecordeFacil.setText(" player= "+ recordes.getNomeJogador()+" | record= "+(int)(recordes.getTempoDeJogo()/1000)+"s");
    }
    private void recordesMedioActualizado(TabelaRecordes recordes) {
        guardarRecordesDisco();
        lblRecordeMedio.setText(" player= "+ recordes.getNomeJogador()+" | record= "+(int)(recordes.getTempoDeJogo()/1000)+"s");
    }
    private void recordesDificilActualizado(TabelaRecordes recordes) {
        guardarRecordesDisco();
        lblRecordeDificil.setText(" player= "+ recordes.getNomeJogador()+" | record= "+(int)(recordes.getTempoDeJogo()/1000)+"s");
    }

    private void sairButtonActionPerformed(ActionEvent e){
        System.exit(0);
    }

    private void jogoFacilButtonActionPerformed(ActionEvent e){
        var janela = new JanelaDeJogo(new CampoMinado(9,9, 10),recordesFacil);
    }
    private void jogoMedioButtonActionPerformed(ActionEvent e){
        var janela = new JanelaDeJogo(new CampoMinado(16, 16, 40),recordesMedio);
    }
    private void jogoDificilButtonActionPerformed(ActionEvent e){
        var janela = new JanelaDeJogo(new CampoMinado(16, 30, 90),recordesDificil);
    }

    //save the info to a binary file
    private void guardarRecordesDisco() {
        ObjectOutputStream oos = null;
        try {
            File f =new
                    File(System.getProperty("user.home")+ File.separator+"minesfinder.recordes");
            oos = new ObjectOutputStream(new FileOutputStream(f));
            oos.writeObject(recordesFacil);
            oos.writeObject(recordesMedio);
            oos.writeObject(recordesDificil);
            oos.close();
        } catch (IOException ex) {
            Logger.getLogger(MinesFinder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //read the info from the binary file
    private void lerRecordesDoDisco() {
        ObjectInputStream ois = null;
        File f = new
                File(System.getProperty("user.home")+File.separator+"minesfinder.records");
        if (f.canRead()) {
            try {
                ois = new ObjectInputStream(new FileInputStream(f));
                recordesFacil=(TabelaRecordes) ois.readObject();
                recordesMedio=(TabelaRecordes) ois.readObject();
                recordesDificil=(TabelaRecordes) ois.readObject();
                ois.close();
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(MinesFinder.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
        }
    }

    public static void main(String[] args){
        new MinesFinder("Mines Finder").setVisible(true);
    }
}
