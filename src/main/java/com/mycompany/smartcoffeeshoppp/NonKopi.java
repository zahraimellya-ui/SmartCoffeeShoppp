package com.mycompany.smartcoffeeshoppp;

// =====================================================
// SUBCLASS 2: NonKopi
// extends Minuman + implements DapatDinilai
// =====================================================
public class NonKopi extends Minuman implements DapatDinilai {
    private String rasa = "Original";
    private boolean pakaiEs = false;

    private int totalRating = 0;
    private int jumlahPenilai = 0;

    public NonKopi(String nama, double harga, String ukuran, String rasa, boolean pakaiEs) {
        super(nama, harga, ukuran);
        setRasa(rasa);
        setPakaiEs(pakaiEs);
    }

    public String getRasa() {
        return rasa;
    }

    public boolean isPakaiEs() {
        return pakaiEs;
    }

    public void setRasa(String rasa) {
        if (rasa != null && !rasa.trim().isEmpty()) {
            this.rasa = rasa.trim();
        } else {
            System.out.println("[Validasi] Rasa tidak boleh kosong!");
        }
    }

    public void setPakaiEs(boolean pakaiEs) {
        this.pakaiEs = pakaiEs;
    }

    @Override
    public double hitungHarga(int jumlah) {
        double biayaEs = pakaiEs ? 2000 * jumlah : 0;
        return super.hitungHarga(jumlah) + biayaEs;
    }

    @Override
    public String getJenis() {
        return "Non Kopi";
    }

    @Override
    public String getDetail() {
        return "Rasa " + rasa + ", " + (pakaiEs ? "Dingin (es)" : "Panas");
    }

    // ---------- Override ABSTRACT METHOD dari Minuman ----------
    @Override
    public void siapkan() {
        System.out.println("[Non Kopi] Mencampur bahan rasa " + rasa + " untuk " + getNama()
                + " ukuran " + getUkuran() + ", disajikan "
                + (pakaiEs ? "dingin dengan es batu." : "panas."));
    }

    // ---------- Implementasi interface DapatDinilai ----------
    @Override
    public void beriRating(int bintang) {
        if (bintang < RATING_MIN || bintang > RATING_MAKS) {
            System.out.println("[Rating Non Kopi] Rating harus " + RATING_MIN + " sampai " + RATING_MAKS + "!");
            return;
        }
        totalRating += bintang;
        jumlahPenilai++;
        System.out.printf("[Rating Non Kopi] %s menerima %d bintang. Rata-rata: %.1f dari %d penilai.%n",
                getNama(), bintang, (double) totalRating / jumlahPenilai, jumlahPenilai);
    }
}
