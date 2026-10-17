package com.mycompany.smartcoffeeshoppp;

// =====================================================
// SUBCLASS 3 (BARU): Teh
// extends Minuman + implements DapatDikustomisasi
// =====================================================
public class Teh extends Minuman implements DapatDikustomisasi {
    private String jenisTeh = "Hitam";
    private boolean pakaiBoba = false;

    public Teh(String nama, double harga, String ukuran, String jenisTeh, boolean pakaiBoba) {
        super(nama, harga, ukuran);
        setJenisTeh(jenisTeh);
        setPakaiBoba(pakaiBoba);
    }

    public String getJenisTeh() {
        return jenisTeh;
    }

    public boolean isPakaiBoba() {
        return pakaiBoba;
    }

    public void setJenisTeh(String jenisTeh) {
        if (jenisTeh != null && (jenisTeh.equalsIgnoreCase("Hitam")
                || jenisTeh.equalsIgnoreCase("Hijau")
                || jenisTeh.equalsIgnoreCase("Melati"))) {
            this.jenisTeh = jenisTeh.substring(0, 1).toUpperCase() + jenisTeh.substring(1).toLowerCase();
        } else {
            System.out.println("[Validasi] Jenis teh harus Hitam, Hijau, atau Melati!");
        }
    }

    public void setPakaiBoba(boolean pakaiBoba) {
        this.pakaiBoba = pakaiBoba;
    }

    @Override
    public double hitungHarga(int jumlah) {
        double biayaBoba = pakaiBoba ? 4000 * jumlah : 0;
        return super.hitungHarga(jumlah) + biayaBoba;
    }

    @Override
    public String getJenis() {
        return "Teh";
    }

    @Override
    public String getDetail() {
        return "Teh " + jenisTeh + (pakaiBoba ? ", + Boba" : ", tanpa topping");
    }

    // ---------- Override ABSTRACT METHOD dari Minuman ----------
    @Override
    public void siapkan() {
        System.out.println("[Teh] Menyeduh teh " + jenisTeh + " untuk " + getNama()
                + " ukuran " + getUkuran()
                + (pakaiBoba ? ", lalu menambahkan boba." : ", tanpa topping."));
    }

    // ---------- Implementasi interface DapatDikustomisasi ----------
    @Override
    public void kustomisasi() {
        System.out.println("[Kustomisasi Teh] Memproses permintaan topping boba untuk " + getNama() + "...");
        setPakaiBoba(!pakaiBoba);
        System.out.println(pakaiBoba ? "Boba ditambahkan." : "Boba dihapus.");
        System.out.println("Harga jual per gelas sekarang " + String.format("Rp%,.0f", hitungHarga(1)) + ".");
    }
}
