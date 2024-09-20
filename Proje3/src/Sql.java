import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class Sql {
    public String randomString() {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public Kullanicilar kullaniciKontrol(String sifre, String tckimlik) {
        Kullanicilar user = null;
        final String DB_URL = "jdbc:mysql://localhost/banka?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM kullanicilar WHERE tckimlik=? and sifre=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, tckimlik);
            preparedStatement.setString(2, sifre);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new Kullanicilar();
                user.setTckimlik(resultSet.getString("tckimlik"));
                user.setSifre(resultSet.getString("sifre"));
                user.setIsim(resultSet.getString("isim"));
                user.setSoyisim(resultSet.getString("soyisim"));
                user.setHesapno(resultSet.getString("hesapno"));
                user.setTlhesap(resultSet.getDouble("tlhesap"));
                user.setEurohesap(resultSet.getDouble("eurohesap"));
                user.setDolarhesap(resultSet.getDouble("dolarhesap"));
                user.setAltinhesap(resultSet.getInt("altinhesap"));
            }

            preparedStatement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    public Kullanicilar kullaniciEkle(String isim, String soyisim, String tckimlik, String sifre) {
        Kullanicilar user = null;
        String hesapno = randomString();
        final String DB_URL = "jdbc:mysql://localhost/banka?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "INSERT INTO kullanicilar (isim, soyisim, tckimlik, sifre, hesapno, tlhesap ,dolarhesap,eurohesap,altinhesap) VALUES (?, ?, ?, ?, ?, ?, ?, ? ,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, isim);
            preparedStatement.setString(2, soyisim);
            preparedStatement.setString(3, tckimlik);
            preparedStatement.setString(4, sifre);
            preparedStatement.setString(5, hesapno);
            preparedStatement.setDouble(6, 0.0);
            preparedStatement.setDouble(7, 0.0);
            preparedStatement.setDouble(8, 0.0);
            preparedStatement.setInt(9, 0);
            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                user = new Kullanicilar();
                user.setIsim(isim);
                user.setSoyisim(soyisim);
                user.setTckimlik(tckimlik);
                user.setSifre(sifre);
                user.setHesapno(hesapno);
                user.setTlhesap(0.0);
                user.setEurohesap(0.0);
                user.setDolarhesap(0.0);
                user.setAltinhesap(0);
            }

            preparedStatement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    public ArrayList<Kullanicilar> kullanicilariCek() {
        ArrayList<Kullanicilar> liste = new ArrayList<>();
        final String DB_URL = "jdbc:mysql://localhost/banka?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM kullanicilar";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Kullanicilar user = new Kullanicilar();
                user.setSifre(resultSet.getString("sifre"));
                user.setTckimlik(resultSet.getString("tckimlik"));
                user.setIsim(resultSet.getString("isim"));
                user.setSoyisim(resultSet.getString("soyisim"));
                user.setHesapno(resultSet.getString("hesapno"));
                user.setTlhesap(resultSet.getDouble("tlhesap"));
                user.setDolarhesap(resultSet.getDouble("dolarhesap"));
                user.setEurohesap(resultSet.getDouble("eurohesap"));
                user.setAltinhesap(resultSet.getInt("altinhesap"));
                liste.add(user);
            }

            preparedStatement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return liste;
    }

    public void ekleAltin(String tckimlik, double miktar) {
        final String DB_URL = "jdbc:mysql://localhost/banka?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "UPDATE kullanicilar SET altinhesap = altinhesap + ? WHERE tckimlik = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setDouble(1, miktar);
            preparedStatement.setString(2, tckimlik);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ekleEuro(String tckimlik, double miktar) {
        final String DB_URL = "jdbc:mysql://localhost/banka?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "UPDATE kullanicilar SET eurohesap = eurohesap + ? WHERE tckimlik = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setDouble(1, miktar);
            preparedStatement.setString(2, tckimlik);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ekleDolar(String tckimlik, double miktar) {
        final String DB_URL = "jdbc:mysql://localhost/banka?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "UPDATE kullanicilar SET dolarhesap = dolarhesap + ? WHERE tckimlik = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setDouble(1, miktar);
            preparedStatement.setString(2, tckimlik);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void ekleTL(String tckimlik, double miktar) {
        final String DB_URL = "jdbc:mysql://localhost/banka?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "UPDATE kullanicilar SET tlhesap = tlhesap + ? WHERE tckimlik = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setDouble(1, miktar);
            preparedStatement.setString(2, tckimlik);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cekTL(String tckimlik, double miktar) {
        final String DB_URL = "jdbc:mysql://localhost/banka?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "UPDATE kullanicilar SET tlhesap = tlhesap - ? WHERE tckimlik = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setDouble(1, miktar);
            preparedStatement.setString(2, tckimlik);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Kullanicilar kullaniciGetir(String tckimlik) {
        Kullanicilar user = null;
        final String DB_URL = "jdbc:mysql://localhost/banka?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM kullanicilar WHERE tckimlik = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, tckimlik);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new Kullanicilar();
                user.setTckimlik(resultSet.getString("tckimlik"));
                user.setSifre(resultSet.getString("sifre"));
                user.setIsim(resultSet.getString("isim"));
                user.setSoyisim(resultSet.getString("soyisim"));
                user.setHesapno(resultSet.getString("hesapno"));
                user.setTlhesap(resultSet.getDouble("tlhesap"));
                user.setAltinhesap(resultSet.getInt("altinhesap"));
                user.setDolarhesap(resultSet.getDouble("dolarhesap"));
                user.setEurohesap(resultSet.getDouble("eurohesap"));
            }

            preparedStatement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }
     public boolean hesapNoGecerliMi(String hesapNo) {
        final String DB_URL = "jdbc:mysql://localhost/banka?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "SELECT COUNT(*) FROM kullanicilar WHERE hesapNo = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, hesapNo);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }


    public boolean paraTransfer(String gonderenTcKimlik, String aliciHesapNo, double miktar) {
        final String DB_URL = "jdbc:mysql://localhost/banka?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        Connection conn = null;
        PreparedStatement cekStatement = null;
        PreparedStatement ekleStatement = null;

        try {
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            conn.setAutoCommit(false);

            Kullanicilar gonderen = kullaniciGetir(gonderenTcKimlik);
            if (gonderen == null || gonderen.getTlhesap() < miktar) {
                return false;
            }

            String cekSql = "UPDATE kullanicilar SET tlhesap = tlhesap - ? WHERE tckimlik = ?";
            cekStatement = conn.prepareStatement(cekSql);
            cekStatement.setDouble(1, miktar);
            cekStatement.setString(2, gonderenTcKimlik);
            cekStatement.executeUpdate();

            String ekleSql = "UPDATE kullanicilar SET tlhesap = tlhesap + ? WHERE hesapno = ?";
            ekleStatement = conn.prepareStatement(ekleSql);
            ekleStatement.setDouble(1, miktar);
            ekleStatement.setString(2, aliciHesapNo);
            ekleStatement.executeUpdate();

            conn.commit();

            return true;
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (cekStatement != null) cekStatement.close();
                if (ekleStatement != null) ekleStatement.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public void dolarBozdur(String tckimlik, double miktar) {
        final String DB_URL = "jdbc:mysql://localhost/banka?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";
        double dolarKuru = 32.17;

        Connection conn = null;
        PreparedStatement cekStatement = null;
        PreparedStatement ekleStatement = null;

        try {
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            conn.setAutoCommit(false);


            Kullanicilar kullanici = kullaniciGetir(tckimlik);
            if (kullanici == null || kullanici.getDolarhesap() < miktar) {
                return;
            }

            String sql = "UPDATE kullanicilar SET dolarhesap = dolarhesap - ? WHERE tckimlik = ?";
            cekStatement = conn.prepareStatement(sql);
            cekStatement.setDouble(1, miktar);
            cekStatement.setString(2, tckimlik);
            cekStatement.executeUpdate();

            double tlMiktar = miktar * dolarKuru;
            sql = "UPDATE kullanicilar SET tlhesap = tlhesap + ? WHERE tckimlik = ?";
            ekleStatement = conn.prepareStatement(sql);
            ekleStatement.setDouble(1, tlMiktar);
            ekleStatement.setString(2, tckimlik);
            ekleStatement.executeUpdate();

            conn.commit();
        } catch (Exception e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (cekStatement != null) cekStatement.close();
                if (ekleStatement != null) ekleStatement.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public void altinBozdur(String tckimlik, double miktar) {
        final String DB_URL = "jdbc:mysql://localhost/banka?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";
        double altinKuru = 2408.26;

        Connection conn = null;
        PreparedStatement cekStatement = null;
        PreparedStatement ekleStatement = null;

        try {
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            conn.setAutoCommit(false);


            Kullanicilar kullanici = kullaniciGetir(tckimlik);
            if (kullanici == null || kullanici.getAltinhesap() < miktar) {
                return;
            }


            String sql = "UPDATE kullanicilar SET altinhesap = altinhesap - ? WHERE tckimlik = ?";
            cekStatement = conn.prepareStatement(sql);
            cekStatement.setDouble(1, miktar);
            cekStatement.setString(2, tckimlik);
            cekStatement.executeUpdate();


            double tlMiktar = miktar * altinKuru;
            sql = "UPDATE kullanicilar SET tlhesap = tlhesap + ? WHERE tckimlik = ?";
            ekleStatement = conn.prepareStatement(sql);
            ekleStatement.setDouble(1, tlMiktar);
            ekleStatement.setString(2, tckimlik);
            ekleStatement.executeUpdate();

            conn.commit();
        } catch (Exception e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (cekStatement != null) cekStatement.close();
                if (ekleStatement != null) ekleStatement.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public void euroBozdur(String tckimlik, double miktar) {
        final String DB_URL = "jdbc:mysql://localhost/banka?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";
        double euroKuru = 34.93;

        Connection conn = null;
        PreparedStatement cekStatement = null;
        PreparedStatement ekleStatement = null;

        try {
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            conn.setAutoCommit(false);

            Kullanicilar kullanici = kullaniciGetir(tckimlik);
            if (kullanici == null || kullanici.getEurohesap() < miktar) {
                return;
            }

            String sql = "UPDATE kullanicilar SET eurohesap = eurohesap - ? WHERE tckimlik = ?";
            cekStatement = conn.prepareStatement(sql);
            cekStatement.setDouble(1, miktar);
            cekStatement.setString(2, tckimlik);
            cekStatement.executeUpdate();

            double tlMiktar = miktar * euroKuru;
            sql = "UPDATE kullanicilar SET tlhesap = tlhesap + ? WHERE tckimlik = ?";
            ekleStatement = conn.prepareStatement(sql);
            ekleStatement.setDouble(1, tlMiktar);
            ekleStatement.setString(2, tckimlik);
            ekleStatement.executeUpdate();

            conn.commit();
        } catch (Exception e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (cekStatement != null) cekStatement.close();
                if (ekleStatement != null) ekleStatement.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}









