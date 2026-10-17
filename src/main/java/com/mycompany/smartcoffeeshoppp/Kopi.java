package com.mycompany.smartcoffeeshoppp;

// =====================================================
// SUBCLASS 1: Kopi
// extends 1 abstract class + implements 2 interface sekaligus
// (MULTIPLE IMPLEMENTS)
// =====================================================
public class Kopi extends Minuman implements DapatDikustomisasi, DapatDinilai {
    private String jenisBiji = "Arabika";
    private int jumlahShot = 1;

    private int totalRating = 0;
    private int jumlahPenilai = 0;

    public Kopi(String nama, double harga, String ukuran, String jenisBiji, int jumlahShot) {
        super(nama, harga, ukuran);
        setJenisBiji(jenisBiji);
        setJumlahShot(jumlahShot);
    }

    public String getJenisBiji() {
        return jenisBiji;
    }

    public int getJumlahShot() {
        return jumlahShot;
    }

    public void setJenisBiji(String jenisBiji) {
        if (jenisBiji != null && (jenisBiji.equalsIgnoreCase("Arabika") || jenisBiji.equalsIgnoreCase("Robusta"))) {
            this.jenisBiji = jenisBiji.substring(0, 1).toUpperCase() + jenisBiji.substring(1).toLowerCase();
        } else {
            System.out.println("[Validasi] Jenis biji harus Arabika atau Robusta!");
        }
    }

    public void setJumlahShot(int jumlahShot) {
        if (jumlahShot >= 1 && jumlahShot <= 3) {
            this.jumlahShot = jumlahShot;
        } else {
            System.out.println("[Validasi] Jumlah shot harus 1 sampai 3!");
        }
    }

    // ---------- Override method superclass ----------
    @Override
    public double hitungHarga(int jumlah) {
        double biayaShotEkstra = (jumlahShot - 1) * 3000 * jumlah;
        return super.hitungHarga(jumlah) + biayaShotEkstra;
    }

    @Override
    public String getJenis() {
        return "Kopi";
    }

    @Override
    public String getDetail() {
        return "Biji " + jenisBiji + ", " + jumlahShot + " shot";
    }

    // ---------- Override ABSTRACT METHOD dari Minuman ----------
    @Override
    public void siapkan() {
        System.out.println("[Kopi] Menggiling biji " + jenisBiji + " dan mengekstrak "
                + jumlahShot + " shot espresso untuk " + getNama()
                + " ukuran " + getUkuran() + ".");
    }

    // ---------- Implementasi interface DapatDikustomisasi ----------
    @Override
    public void kustomisasi() {
        System.out.println("[Kustomisasi Kopi] Memproses permintaan tambah shot untuk " + getNama() + "...");
        if (jumlahShot < 3) {
            setJumlahShot(jumlahShot + 1);
            System.out.println("Shot ditambah menjadi " + jumlahShot + " shot. Harga jual per gelas sekarang "
                    + String.format("Rp%,.0f", hitungHarga(1)) + ".");
        } else {
            System.out.println("Shot sudah maksimal (3 shot), tidak bisa ditambah lagi.");
        }
    }

    // ---------- Implementasi interface DapatDinilai ----------
    @Override
    public void beriRating(int bintang) {
        if (bintang < RATING_MIN || bintang > RATING_MAKS) {
            System.out.println("[Rating Kopi] Rating harus " + RATING_MIN + " sampai " + RATING_MAKS + "!");
            return;
        }
        totalRating += bintang;
        jumlahPenilai++;
        System.out.printf("[Rating Kopi] %s menerima %d bintang. Rata-rata: %.1f dari %d penilai.%n",
                getNama(), bintang, (double) totalRating / jumlahPenilai, jumlahPenilai);
    }
}
