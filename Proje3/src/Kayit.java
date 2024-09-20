import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


public class Kayit extends JDialog{
    private JTextField isimField;
    private JTextField soyisimField;
    private JTextField tcField;
    private JTextField sifreField;
    private JButton kayitOlButton;
    private JButton geriDonButton;
    private JPanel kayitPanel;

    Kayit(JFrame parent){
        super(parent);
        setTitle("OdrinBank-Kayit");
        setContentPane(kayitPanel);
        setMinimumSize(new Dimension(1280, 720));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Sql sql = new Sql();
        ImageIcon image = new ImageIcon("emir.png");
        setIconImage(image.getImage());

        kayitOlButton.addActionListener(e -> {
            if(isimField.getText().isEmpty() || soyisimField.getText().isEmpty() || sifreField.getText().isEmpty() || tcField.getText().isEmpty()){
                JOptionPane.showMessageDialog(this.kayitPanel,"Lutfen Butun Alanlari Doldurun","Hata", 2);
            }
            else if(tcField.getText().length() != 11){
                JOptionPane.showMessageDialog(this.kayitPanel,"Tc Kimlik Numarası 11 haneli olmalıdır.","Hata", 2);
            }
            else{
                if(tcField.getText().length() == 11){
                    boolean check = true;
                    try{
                        Long temp = Long.parseLong(tcField.getText());
                    }catch(NumberFormatException nfe){
                        check = false;
                    }
                    if(!check){
                        JOptionPane.showMessageDialog(this.kayitPanel,"Tc Kimlik Numarası Sadece Rakamlardan Olusmalidir","Hata", 2);
                        return;
                    }
                }
                ArrayList<Kullanicilar> liste = sql.kullanicilariCek();
                for(Kullanicilar user:liste){
                    if(tcField.getText().equals(user.getTckimlik())){
                        JOptionPane.showMessageDialog(this.kayitPanel,"Bu TC Kimlik Zaten Kayitli","Hata", 2);
                        return;
                    }
                }
                sql.kullaniciEkle(isimField.getText(),soyisimField.getText(),tcField.getText(),sifreField.getText());
                JOptionPane.showMessageDialog(this.kayitPanel,"Kayit Olundu","Basarili", 1);
                dispose();
                new Login(null);
            }
        });
        geriDonButton.addActionListener(e -> {
            dispose();
            new Login(null);
        });

        tcField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(tcField.getText().length() > 10) e.consume();
            }
        });
        setVisible(true);
    }
}
