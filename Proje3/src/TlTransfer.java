import javax.swing.*;
import java.awt.*;

public class TlTransfer extends JDialog {
    private JPanel TransferPanel;
    private  JPanel Transferpanel1;
    private JLabel Dolarallabel;
    private JButton geriButton;
    private JLabel HesapNolabel;
    private JTextField HesapNotxtfield;
    private JTextField tutarTxtField;
    private JButton transferbutton;
    private Kullanicilar currentUser;
    private Sql sql;

    public TlTransfer(JFrame parent, Kullanicilar user) {
        super(parent, "OdrinBank-Para Transferi", true);
        this.currentUser = user;
        this.sql = new Sql();


        setContentPane(TransferPanel);
        setContentPane(Transferpanel1);
        setMinimumSize(new Dimension(1280, 720));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ImageIcon image = new ImageIcon("emir.png");
        setIconImage(image.getImage());

        geriButton.addActionListener(e -> {
            dispose();
            new KullaniciPanel(parent, currentUser);
        });

        transferbutton.addActionListener(e -> {
            String aliciHesapNo = HesapNotxtfield.getText();
            double miktar;
            try {
                miktar = Double.parseDouble(tutarTxtField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Lütfen geçerli bir tutar giriniz.", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (miktar <= 0) {
                JOptionPane.showMessageDialog(this, "Lütfen pozitif bir tutar giriniz.", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!sql.hesapNoGecerliMi(aliciHesapNo)) {
                JOptionPane.showMessageDialog(this, "Geçersiz hesap numarası.", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean success = sql.paraTransfer(currentUser.getTckimlik(), aliciHesapNo, miktar);
            if (success) {
                JOptionPane.showMessageDialog(this, "Para transferi başarılı.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                currentUser.setTlhesap(currentUser.getTlhesap() - miktar);
            } else {
                JOptionPane.showMessageDialog(this, "Yetersiz bakiye!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }
}