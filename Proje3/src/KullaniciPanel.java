import javax.swing.*;
import java.awt.*;

public class KullaniciPanel extends JDialog {
    private JPanel kpanel;
    private JLabel isimlabel;
    private JLabel hesapLabel;
    private JLabel hesapnoLABEL;
    private JLabel tlbakiyelabel;
    private JLabel tlbakiyegosterlabel;
    private JButton tlyuklebutton;
    private JButton tltransferbutton;
    private JButton TLCekButton;
    private JButton DovizIslemleriButton;
    private JLabel dolarbakiyegosterlabel;
    private JLabel eurobakiyegosterlabel;
    private JLabel altinbakiyegosterlabel;

    private Kullanicilar currentUser;

    public KullaniciPanel(JFrame parent, Kullanicilar user) {
        super(parent, "Kullanıcı Paneli", true);
        this.currentUser = user;


        setContentPane(kpanel);
        setMinimumSize(new Dimension(1280, 720));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ImageIcon image = new ImageIcon("emir.png");
        setIconImage(image.getImage());

        isimlabel.setText(user.getIsim() + " " + user.getSoyisim());
        hesapLabel.setText(user.getHesapno());

        tlbakiyegosterlabel.setText(String.format("%.2f", user.getTlhesap()));
        dolarbakiyegosterlabel.setText(String.format("%.2f", user.getDolarhesap()));
        eurobakiyegosterlabel.setText(String.format("%.2f", user.getEurohesap()));
        altinbakiyegosterlabel.setText(String.format("%d",user.getAltinhesap()));
        tlyuklebutton.addActionListener(e -> {
            dispose();
            new EkleTl(parent, user, this);
        });

        TLCekButton.addActionListener(e -> {
            dispose();
            new CekTl(parent, user);
        });

        DovizIslemleriButton.addActionListener(e -> {
            dispose();
            new DovizIslem(parent, user).setVisible(true);
        });

        tltransferbutton.addActionListener(e -> {
            dispose();
            new TlTransfer(parent, user).setVisible(true);
        });

        setVisible(true);
    }

    public void bakiyeGuncelle() {
        tlbakiyegosterlabel.setText(String.format("%.2f", currentUser.getTlhesap()));
    }


}