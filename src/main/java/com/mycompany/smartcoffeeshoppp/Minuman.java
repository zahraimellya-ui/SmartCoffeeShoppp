package com.mycompany.smartcoffeeshoppp;

// =====================================================
// ABSTRACT SUPERCLASS: Minuman
// Tidak bisa di-instansiasi (new Minuman(...) -> error).
// Hanya menjadi kerangka untuk Kopi, NonKopi, dan Teh.
// =====================================================
public abstract class Minuman {
    private String nama = "Tanpa Nama";
    private double harga = 0;
    private String ukuran = "Medium";

    private static int jumlahMinuman = 0;

    public Minuman(String nama, double harga, String ukuran) {
        setNama(nama);
        setHarga(harga);
        setUkuran(ukuran);
        jumlahMinuman++;
    }

    // ---------- ABSTRACT METHOD ----------
    // Tanpa isi. WAJIB di-override oleh semua subclass.
    public abstract void siapkan();

    // ---------- Getter ----------
    public String getNama() {
        return nama;
    }

    public double getHarga() {
        return harga;
    }

    public String getUkuran() {
        return ukuran;
    }

    // ---------- Setter + validasi ----------
    public void setNama(String nama) {
        if (nama != null && !nama.trim().isEmpty()) {
            this.nama = nama.trim();
        } else {
            System.out.println("[Validasi] Nama tidak boleh kosong!");
        }
    }

    public void setHarga(double harga) {
        if (harga > 0) {
            this.harga = harga;
        } else {
            System.out.println("[Validasi] Harga harus lebih dari 0!");
        }
    }

    public void setUkuran(String ukuran) {
        if (ukuran != null) {
            String u = ukuran.trim();
            if (u.equalsIgnoreCase("Small") || u.equalsIgnoreCase("Medium") || u.equalsIgnoreCase("Large")) {
                this.ukuran = u.substring(0, 1).toUpperCase() + u.substring(1).toLowerCase();
                return;
            }
        }
        System.out.println("[Validasi] Ukuran harus Small, Medium, atau Large!");
    }

    public static int getJumlahMinuman() {
        return jumlahMinuman;
    }

    // ---------- COMPILE-TIME POLYMORPHISM (Method Overloading) ----------
    public double hitungHarga(int jumlah) {
        return harga * jumlah;
    }

    public double hitungHarga(int jumlah, double diskonPersen) {
        // hitungHarga(jumlah) di sini akan di-dynamic-binding ke versi subclass
        double total = hitungHarga(jumlah);
        return total - (total * diskonPersen / 100);
    }

    // ---------- Method yang di-override subclass ----------
    public String getJenis() {
        return "Minuman";
    }

    public String getDetail() {
        return "-";
    }

    public void tampilInfo(int no) {
        System.out.printf("| %-3d | %-16s | %-9s | %-7s | %11s | %11s | %-26s |%n",
                no,
                nama,
                getJenis(),
                ukuran,
                String.format("Rp%,.0f", harga),
                String.format("Rp%,.0f", hitungHarga(1)),
                getDetail());
    }
}
