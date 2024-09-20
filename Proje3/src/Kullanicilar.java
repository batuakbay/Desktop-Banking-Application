public class Kullanicilar {
    private String isim;
    private String soyisim;
    private String tckimlik;
    private String sifre;
    private String hesapno;
    private double tlhesap ;

    private double dolarhesap;
    private double eurohesap;
    private int altinhesap;

    public double getDolarhesap() {
        return dolarhesap;
    }

    public double getEurohesap() {
        return eurohesap;
    }

    public int getAltinhesap() {
        return altinhesap;
    }

    public void setAltinhesap(int altinhesap) {
        this.altinhesap = altinhesap;
    }

    public void setDolarhesap(double dolarhesap) {
        this.dolarhesap = dolarhesap;
    }

    public void setEurohesap(double eurohesap) {this.eurohesap = eurohesap;}

    public double getTlhesap() {return tlhesap;}

    public void setTlhesap(double tlhesap) {this.tlhesap = tlhesap;}

    public void setHesapno(String hesapno) {this.hesapno = hesapno;}

    public String getHesapno() {
        return hesapno;
    }

    public String getIsim() {
        return isim;
    }

    public String getTckimlik() {
        return tckimlik;
    }

    public String getSifre() {
        return sifre;
    }

    public String getSoyisim() {
        return soyisim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public void setSoyisim(String soyisim) {
        this.soyisim = soyisim;
    }

    public void setTckimlik(String tckimlik) {
        this.tckimlik = tckimlik;
    }
}
