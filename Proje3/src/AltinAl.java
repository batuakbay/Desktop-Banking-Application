import javax.swing.*;
import java.awt.*;

public class AltinAl extends JDialog {
    private JPanel Altinpanel1;
    private JButton albutton;
    private JLabel Dolarallabel;
    private JTextField tutarcektxtfield;
    private JButton geributton2;
    private JPanel AltinPanel;
    private JTextField tutaraltxtfield;
    private JButton bozdurButton;
    private JLabel dolarsatlabel;
    private Kullanicilar currentUser;
    private Sql sql;
    private static final double BOZDURMA_KURU = 2408.26;
    private static final double ALTIN_KURU = 2596.26;
    public AltinAl(JFrame parent, Kullanicilar user) {
        super(parent);
        this.currentUser = user;
        this.sql = new Sql();
        setTitle("OdrinBank-Altin Al/Sat");
        setContentPane(AltinPanel);
        setContentPane(Altinpanel1);
        setMinimumSize(new Dimension(1280, 720));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ImageIcon image = new ImageIcon("emir.png");
        setIconImage(image.getImage());
        albutton.addActionListener(e -> {
            double almakIstenenAltin = Double.parseDouble(tutarcektxtfield.getText());
            double gerekliTlMiktari = almakIstenenAltin * ALTIN_KURU;

            if (currentUser.getTlhesap() >= gerekliTlMiktari) {

                sql.cekTL(currentUser.getTckimlik(), gerekliTlMiktari);
                sql.ekleAltin(currentUser.getTckimlik(), almakIstenenAltin);
                currentUser = sql.kullaniciGetir(currentUser.getTckimlik());

                JOptionPane.showMessageDialog(this, "Basarili! " + almakIstenenAltin + " Gram altin aldiniz.");

                dispose();
                new KullaniciPanel(parent, currentUser).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Yetersiz bakiye! İşlem gerceklestirilemedi.");
            }
        });

        geributton2.addActionListener(e -> {
            dispose();
            Kullanicilar updatedUser = sql.kullaniciGetir(currentUser.getTckimlik());
            new DovizIslem(null, updatedUser).setVisible(true);

        });

        bozdurButton.addActionListener(e -> {
            double bozdurulacakAltin = Double.parseDouble(tutaraltxtfield.getText());

            if (currentUser.getAltinhesap() >= bozdurulacakAltin) {

                sql.altinBozdur(currentUser.getTckimlik(), bozdurulacakAltin);
                currentUser = sql.kullaniciGetir(currentUser.getTckimlik());

                double eldeEdilenTl = bozdurulacakAltin * BOZDURMA_KURU;
                JOptionPane.showMessageDialog(this, "Başarılı! " + bozdurulacakAltin + " Gram altin bozdurdunuz ve " + eldeEdilenTl + " TL kazandınız.");

                dispose();
                new KullaniciPanel(parent, currentUser).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Bu kadar altina sahip degilsiniz!");
            }

        });

    }
}