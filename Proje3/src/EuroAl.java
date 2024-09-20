import javax.swing.*;
import java.awt.*;

public class EuroAl extends JDialog {
    private JPanel Europanel1;
    private JButton albutton;
    private JLabel Dolarallabel;
    private JTextField tutarcektxtfield;
    private JButton geributton2;
    private JPanel EuroPanel;
    private JButton bozdurButton;
    private JTextField tutaraltxtfield;
    private Kullanicilar currentUser;
    private Sql sql;
    private static final double BOZDURMA_KURU = 34.93;
    private static final double EURO_KURU = 35.02;
    public EuroAl(JFrame parent, Kullanicilar user) {
        super(parent);
        this.currentUser = user;
        this.sql = new Sql();
        setTitle("OdrinBank-Dolar Al/Sat");
        setContentPane(EuroPanel);
        setContentPane(Europanel1);
        setMinimumSize(new Dimension(1280, 720));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ImageIcon image = new ImageIcon("emir.png");
        setIconImage(image.getImage());
        albutton.addActionListener(e -> {
            double almakIstenenEuro = Double.parseDouble(tutarcektxtfield.getText());
            double gerekliTlMiktari = almakIstenenEuro * EURO_KURU;

            if (currentUser.getTlhesap() >= gerekliTlMiktari) {

                sql.cekTL(currentUser.getTckimlik(), gerekliTlMiktari);

                sql.ekleEuro(currentUser.getTckimlik(), almakIstenenEuro);


                currentUser = sql.kullaniciGetir(currentUser.getTckimlik());

                JOptionPane.showMessageDialog(this, "Başarılı! " + almakIstenenEuro + " Euro aldınız.");

                dispose();
                new KullaniciPanel(parent, currentUser).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Yetersiz bakiye! İşlem gerçekleştirilemedi.");
            }
        });

        bozdurButton.addActionListener(e -> {
            double bozdurulacakEuro = Double.parseDouble(tutaraltxtfield.getText());

            if (currentUser.getEurohesap() >= bozdurulacakEuro) {

                sql.euroBozdur(currentUser.getTckimlik(), bozdurulacakEuro);


                currentUser = sql.kullaniciGetir(currentUser.getTckimlik());

                double eldeEdilenTl = bozdurulacakEuro * BOZDURMA_KURU;
                JOptionPane.showMessageDialog(this, "Başarılı! " + bozdurulacakEuro + " Euro bozdurdunuz ve " + eldeEdilenTl + " TL kazandınız.");

                dispose();
                new KullaniciPanel(parent, currentUser).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Yetersiz Euro bakiyesi! İşlem gerçekleştirilemedi.");
            }

        });

        geributton2.addActionListener(e -> {
            dispose();
            Kullanicilar updatedUser = sql.kullaniciGetir(currentUser.getTckimlik());
            new DovizIslem(null, updatedUser).setVisible(true);

        });


    }
}
