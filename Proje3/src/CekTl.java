import javax.swing.*;
import java.awt.*;

public class CekTl extends JDialog {
    private JPanel Cekpanel;
    private JButton cektlbutton;
    private JButton geributton2;
    private JLabel cektllabel;
    private JTextField tutarcektxtfield;
    private Kullanicilar currentUser;
    private Sql sql;


    public CekTl(JFrame parent, Kullanicilar user) {
        super(parent);
        this.currentUser = user;
        this.sql = new Sql();
        setTitle("OdrinBank-Para Cekme");
        setContentPane(Cekpanel);
        setMinimumSize(new Dimension(1280, 720));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ImageIcon image = new ImageIcon("emir.png");
        setIconImage(image.getImage());

        cektlbutton.addActionListener(e -> {
            try {
                double cekilecekTutar = Double.parseDouble(tutarcektxtfield.getText());
                if (currentUser.getTlhesap() >= cekilecekTutar) {
                    sql.cekTL(currentUser.getTckimlik(), cekilecekTutar);
                    currentUser.setTlhesap(currentUser.getTlhesap() - cekilecekTutar);
                    JOptionPane.showMessageDialog(this, cekilecekTutar + " TL başarıyla çekildi!");
                } else {
                    JOptionPane.showMessageDialog(this, "Hesabınızda yeterli bakiye yok!", "Hata", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Lütfen geçerli bir tutar girin.", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

        geributton2.addActionListener(e -> {
            dispose();
            new KullaniciPanel(null, currentUser);
        });

        setVisible(true);
    }
}