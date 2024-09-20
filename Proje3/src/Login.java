import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Login extends JDialog{
    private JLabel Tc;
    private JLabel Sifre;
    private JPasswordField sifreGiris;
    private JTextField tcGiris;
    private JCheckBox sifreGoster;
    private JButton girisYapButton;
    private JButton kayitOlButton;
    private JPanel LoginPanel;

    private JLabel loginLabel;


    public Login(JFrame parent) {
        super(parent);
        setTitle("OdrinBank-Login");
        setContentPane(LoginPanel);
        setMinimumSize(new Dimension(1280, 720));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ImageIcon image = new ImageIcon("emir.png");
        setIconImage(image.getImage());
        Sql sql = new Sql();
        String tckimlik = "";
        String sifre = "";
        sifreGoster.addActionListener(e -> {
            if(sifreGoster.isSelected()){
               sifreGiris.setVisible(true);
               sifreGiris.setEchoChar((char)0);
            }
            else{
                sifreGiris.setEchoChar('*');
            }
        });
        girisYapButton.addActionListener(e -> {
            Kullanicilar user = sql.kullaniciKontrol(sifreGiris.getText(),tcGiris.getText());
            if(user != null){
                JOptionPane.showMessageDialog(this.LoginPanel,"Hosgeldin "+user.getIsim()+" "+user.getSoyisim(),"Basarili", 1);

                dispose();
                new KullaniciPanel(null,user);
            }
            else{
                JOptionPane.showMessageDialog(this.LoginPanel,"Tekrar Deneyiniz","Hata", 2);
            }
        });
        kayitOlButton.addActionListener(e -> {
            dispose();
            new Kayit(null);
        });

        tcGiris.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(tcGiris.getText().length() > 10) e.consume();
            }
        });
        setVisible(true);
    }
}


