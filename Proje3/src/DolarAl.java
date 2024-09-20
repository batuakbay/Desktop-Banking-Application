import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class DolarAl extends JDialog {
    private JPanel DolarPanel;
    private JPanel Dolarpanel1;
    private JButton albutton;
    private JLabel Dolarallabel;
    private JTextField tutarcektxtfield;
    private JButton geributton2;
    private JButton bozdurButton;
    private JTextField tutaraltxtfield;
    private Kullanicilar currentUser;
    private Sql sql;
    private static final double BOZDURMA_KURU = 32.17;
    private static final double DOLAR_KURU = 32.36;
    public DolarAl(JFrame parent, Kullanicilar user ) {
        super(parent);
        this.currentUser = user;
        this.sql = new Sql();
        setTitle("OdrinBank-Dolar Al/Sat");
        setContentPane(DolarPanel);
        setContentPane(Dolarpanel1);
        setMinimumSize(new Dimension(1280, 720));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ImageIcon image = new ImageIcon("emir.png");
        setIconImage(image.getImage());
        albutton.addActionListener(e -> {
            double almakIstenenDolar = Double.parseDouble(tutarcektxtfield.getText());
            double gerekliTlMiktari = almakIstenenDolar * DOLAR_KURU;

            if (currentUser.getTlhesap() >= gerekliTlMiktari) {
                sql.cekTL(currentUser.getTckimlik(), gerekliTlMiktari);
                sql.ekleDolar(currentUser.getTckimlik(), almakIstenenDolar);

                currentUser = sql.kullaniciGetir(currentUser.getTckimlik());

                JOptionPane.showMessageDialog(this, "Başarılı! " + almakIstenenDolar + " Dolar aldınız.");

                dispose();
                new KullaniciPanel(parent, currentUser).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Yetersiz bakiye! İşlem gerçekleştirilemedi.");
            }
        });
        bozdurButton.addActionListener(e -> {
            double bozdurulacakDolar = Double.parseDouble(tutaraltxtfield.getText());

            if (currentUser.getDolarhesap() >= bozdurulacakDolar) {

                sql.dolarBozdur(currentUser.getTckimlik(), bozdurulacakDolar);

                currentUser = sql.kullaniciGetir(currentUser.getTckimlik());

                double eldeEdilenTl = bozdurulacakDolar * BOZDURMA_KURU;
                JOptionPane.showMessageDialog(this, "Başarılı! " + bozdurulacakDolar + " Dolar bozdurdunuz ve " + eldeEdilenTl + " TL kazandınız.");

                dispose();
                new KullaniciPanel(parent, currentUser).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Yetersiz dolar bakiyesi! İşlem gerçekleştirilemedi.");
            }

        });
        geributton2.addActionListener(e -> {
            dispose();
            Kullanicilar updatedUser = sql.kullaniciGetir(currentUser.getTckimlik());
            new DovizIslem(null, updatedUser).setVisible(true);

        });

        }
    }



