import javax.swing.*;
import java.awt.*;

public class EkleTl extends JDialog {
    private JPanel Tlpanel;
    private JTextField yukletltxtfield;
    private JButton YukleButton;
    private JButton p20button;
    private JButton a50Button;
    private JButton a100Button;
    private JButton a200Button;
    private JButton Geributton1;
    private Kullanicilar currentUser;
    private Sql sql;
    private KullaniciPanel kullaniciPanel;

    public EkleTl(JFrame parent, Kullanicilar user, KullaniciPanel kullaniciPanel) {
        super(parent);
        this.currentUser = user;
        this.sql = new Sql();
        this.kullaniciPanel = kullaniciPanel;
        setTitle("OdrinBank-TL Ekle");
        setContentPane(Tlpanel);
        setMinimumSize(new Dimension(1280, 720));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ImageIcon image = new ImageIcon("emir.png");
        setIconImage(image.getImage());

        p20button.addActionListener(e -> {
            sql.ekleTL(currentUser.getTckimlik(), 20.0);
            currentUser.setTlhesap(currentUser.getTlhesap() + 20.0);
            kullaniciPanel.bakiyeGuncelle();
            JOptionPane.showMessageDialog(this, "20 TL başarıyla eklendi!");
        });

        a50Button.addActionListener(e -> {
            sql.ekleTL(currentUser.getTckimlik(), 50.0);
            currentUser.setTlhesap(currentUser.getTlhesap() + 50.0);
            kullaniciPanel.bakiyeGuncelle();
            JOptionPane.showMessageDialog(this, "50 TL başarıyla eklendi!");
        });

        a100Button.addActionListener(e -> {
            sql.ekleTL(currentUser.getTckimlik(), 100.0);
            currentUser.setTlhesap(currentUser.getTlhesap() + 100.0);
            kullaniciPanel.bakiyeGuncelle();
            JOptionPane.showMessageDialog(this, "100 TL başarıyla eklendi!");
        });

        a200Button.addActionListener(e -> {
            sql.ekleTL(currentUser.getTckimlik(), 200.0);
            currentUser.setTlhesap(currentUser.getTlhesap() + 200.0);
            kullaniciPanel.bakiyeGuncelle();
            JOptionPane.showMessageDialog(this, "200 TL başarıyla eklendi!");
        });

        YukleButton.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(yukletltxtfield.getText());
                sql.ekleTL(currentUser.getTckimlik(), amount);
                currentUser.setTlhesap(currentUser.getTlhesap() + amount);
                kullaniciPanel.bakiyeGuncelle();
                JOptionPane.showMessageDialog(this, amount + " TL başarıyla eklendi!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Lütfen geçerli bir tutar girin.", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

        Geributton1.addActionListener(e -> {
            dispose();
            Kullanicilar updatedUser = sql.kullaniciGetir(currentUser.getTckimlik());
            new KullaniciPanel(null, updatedUser);
        });

        setVisible(true);
    }
}