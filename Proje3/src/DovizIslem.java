import javax.swing.*;
import java.awt.*;


public class DovizIslem extends JDialog {
    private JPanel Dovizislemleripanel;
    private JLabel DolarField;
    private JLabel EuroField;
    private JLabel AltinField;
    private JLabel Dolarallabel;
    private JLabel Dolarsatlabel;
    private JLabel Euroallabel;
    private JLabel Eurosatlabel;
    private JLabel Altinallabel;
    private JLabel Altinsatlabel;
    private JButton Euroalbutton2;
    private JButton Dolaralbutton3;
    private JButton Altinalbutton6;
    private JButton GeriDonbutton1;


    private Kullanicilar currentUser;
    private Sql sql;

    public DovizIslem(JFrame parent, Kullanicilar user) {
        super(parent, "Döviz İşlemleri", true);
        this.currentUser = user;
        this.sql = new Sql();
        setTitle("OdrinBank-Döviz İşlemleri");
        setContentPane(Dovizislemleripanel);
        setMinimumSize(new Dimension(1280, 720));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ImageIcon image = new ImageIcon("emir.png");
        setIconImage(image.getImage());

        GeriDonbutton1.addActionListener(e -> {
            dispose();
            new KullaniciPanel(parent, currentUser);
        });


        Dolaralbutton3.addActionListener(e -> {
            dispose();
            new DolarAl(parent, currentUser).setVisible(true);

        });
        Euroalbutton2.addActionListener(e -> {
            dispose();
            new EuroAl(parent, currentUser).setVisible(true);
        });
        Altinalbutton6.addActionListener(e -> {
            dispose();
            new AltinAl(parent, currentUser).setVisible(true);
        });
    }


}