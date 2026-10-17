package com.mycompany.smartcoffeeshoppp;

import java.util.Scanner;

// =====================================================
// MAIN PROGRAM
// =====================================================
public class SmartCoffeeShoppp {

    static final Scanner input = new Scanner(System.in);

    // ARRAY BERTIPE SUPERCLASS (Upcasting): bisa menampung Kopi, NonKopi, dan Teh
    static Minuman[] daftarMenu = new Minuman[50];
    static int jumlahMenu = 0;

    // ---------- Helper input ----------
    static int bacaInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int nilai = Integer.parseInt(input.nextLine().trim());
                if (nilai >= min && nilai <= max) {
                    return nilai;
                }
                System.out.println("Masukkan angka antara " + min + " sampai " + max + "!");
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka bulat!");
            }
        }
    }

    static double bacaDouble(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            try {
                double nilai = Double.parseDouble(input.nextLine().trim());
                if (nilai >= min && nilai <= max) {
                    return nilai;
                }
                System.out.println("Masukkan angka antara " + min + " sampai " + max + "!");
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka!");
            }
        }
    }

    static String bacaTeks(String prompt) {
        while (true) {
            System.out.print(prompt);
            String teks = input.nextLine().trim();
            if (!teks.isEmpty()) {
                return teks;
            }
            System.out.println("Input tidak boleh kosong!");
        }
    }

    // ---------- Tampilan tabel ----------
    static void cetakHeader() {
        String garis = "+-----+------------------+-----------+---------+-------------+-------------+----------------------------+";
        System.out.println(garis);
        System.out.printf("| %-3s | %-16s | %-9s | %-7s | %11s | %11s | %-26s |%n",
                "No", "Nama Menu", "Jenis", "Ukuran", "Harga Dasar", "Harga Jual", "Keterangan");
        System.out.println(garis);
    }

    static void cetakGaris() {
        System.out.println("+-----+------------------+-----------+---------+-------------+-------------+----------------------------+");
    }

    // ---------- Data awal: mencakup SEMUA variasi subclass ----------
    static void isiDataAwal() {
        daftarMenu[jumlahMenu++] = new Kopi("Espresso", 15000, "Small", "Arabika", 2);
        daftarMenu[jumlahMenu++] = new Kopi("Cappuccino", 22000, "Medium", "Robusta", 1);
        daftarMenu[jumlahMenu++] = new NonKopi("Matcha Latte", 24000, "Medium", "Matcha", true);
        daftarMenu[jumlahMenu++] = new NonKopi("Coklat Panas", 20000, "Medium", "Coklat", false);
        daftarMenu[jumlahMenu++] = new Teh("Teh Tarik", 18000, "Large", "Hitam", false);
        daftarMenu[jumlahMenu++] = new Teh("Boba Green Tea", 21000, "Medium", "Hijau", true);
    }

    // ---------- Fitur 1: Tambah data (3 variasi subclass) ----------
    static void tambahData() {
        if (jumlahMenu >= daftarMenu.length) {
            System.out.println("Kapasitas menu penuh!");
            return;
        }

        System.out.println("\n--- TAMBAH MENU BARU ---");
        System.out.println("1. Kopi");
        System.out.println("2. Non Kopi");
        System.out.println("3. Teh");
        int jenis = bacaInt("Pilih tipe minuman (1-3): ", 1, 3);

        String nama = bacaTeks("Nama menu          : ");
        double harga = bacaDouble("Harga dasar (Rp)    : ", 1, 1000000);
        int pilihUkuran = bacaInt("Ukuran (1=Small, 2=Medium, 3=Large): ", 1, 3);

        String ukuran;
        switch (pilihUkuran) {
            case 1:
                ukuran = "Small";
                break;
            case 3:
                ukuran = "Large";
                break;
            default:
                ukuran = "Medium";
        }

        // UPCASTING: objek Kopi/NonKopi/Teh disimpan di referensi Minuman
        if (jenis == 1) {
            int pilihBiji = bacaInt("Jenis biji (1=Arabika, 2=Robusta): ", 1, 2);
            String biji = (pilihBiji == 1) ? "Arabika" : "Robusta";
            int shot = bacaInt("Jumlah shot (1-3): ", 1, 3);
            daftarMenu[jumlahMenu++] = new Kopi(nama, harga, ukuran, biji, shot);
        } else if (jenis == 2) {
            String rasa = bacaTeks("Rasa               : ");
            int pilihEs = bacaInt("Penyajian (1=Dingin, 2=Panas): ", 1, 2);
            daftarMenu[jumlahMenu++] = new NonKopi(nama, harga, ukuran, rasa, pilihEs == 1);
        } else {
            int pilihTeh = bacaInt("Jenis teh (1=Hitam, 2=Hijau, 3=Melati): ", 1, 3);
            String jenisTeh = (pilihTeh == 1) ? "Hitam" : (pilihTeh == 2) ? "Hijau" : "Melati";
            int pilihBoba = bacaInt("Topping boba (1=Ya, 2=Tidak): ", 1, 2);
            daftarMenu[jumlahMenu++] = new Teh(nama, harga, ukuran, jenisTeh, pilihBoba == 1);
        }
        System.out.println("Menu berhasil ditambahkan!");
    }

    // ---------- Fitur 2: Tampilkan seluruh data ----------
    static void tampilkanSemua() {
        System.out.println("\n=========================== DAFTAR MENU SMART COFFEE SHOP ===========================");
        if (jumlahMenu == 0) {
            System.out.println("Belum ada data menu.");
            return;
        }
        cetakHeader();
        for (int i = 0; i < jumlahMenu; i++) {
            // DYNAMIC BINDING: getJenis(), getDetail(), hitungHarga() mengikuti wujud asli objek
            daftarMenu[i].tampilInfo(i + 1);
        }
        cetakGaris();
        System.out.println("Total menu tampil          : " + jumlahMenu);
        System.out.println("Total objek dibuat (static): " + Minuman.getJumlahMinuman());
    }

    // ---------- Fitur 3: Pencarian (COMPILE-TIME POLYMORPHISM: overloading) ----------
    static int cariMenu(String keyword) {
        int ditemukan = 0;
        for (int i = 0; i < jumlahMenu; i++) {
            if (daftarMenu[i].getNama().toLowerCase().contains(keyword.toLowerCase())) {
                if (ditemukan == 0) {
                    cetakHeader();
                }
                daftarMenu[i].tampilInfo(i + 1);
                ditemukan++;
            }
        }
        return ditemukan;
    }

    static int cariMenu(double hargaMaks) {
        int ditemukan = 0;
        for (int i = 0; i < jumlahMenu; i++) {
            if (daftarMenu[i].hitungHarga(1) <= hargaMaks) {
                if (ditemukan == 0) {
                    cetakHeader();
                }
                daftarMenu[i].tampilInfo(i + 1);
                ditemukan++;
            }
        }
        return ditemukan;
    }

    static void menuCari() {
        System.out.println("\n--- CARI MENU ---");
        System.out.println("1. Berdasarkan nama");
        System.out.println("2. Berdasarkan harga maksimal");
        int pilih = bacaInt("Pilih (1-2): ", 1, 2);

        int hasil;
        if (pilih == 1) {
            String keyword = bacaTeks("Masukkan kata kunci nama: ");
            hasil = cariMenu(keyword);               // overload versi String
        } else {
            double maks = bacaDouble("Masukkan harga maksimal (Rp): ", 1, 1000000);
            hasil = cariMenu(maks);                  // overload versi double
        }

        if (hasil == 0) {
            System.out.println("Data tidak ditemukan.");
        } else {
            cetakGaris();
            System.out.println("Ditemukan " + hasil + " menu.");
        }
    }

    // =====================================================
    // METHOD BERPARAMETER SUPERCLASS (Upcasting pada parameter)
    // Menerima objek Kopi, NonKopi, Teh, atau subclass lain.
    // =====================================================
    static void simulasiPenyiapan(Minuman m) {
        System.out.println("\n>> Simulasi penyiapan: " + m.getNama());
        m.siapkan();   // DYNAMIC BINDING -> abstract method milik subclass yang sebenarnya
    }

    // Kemampuan tambahan berdasarkan interface, dicek dengan instanceof
    static void cetakKemampuan(Minuman m) {
        StringBuilder sb = new StringBuilder();
        if (m instanceof DapatDikustomisasi) {
            sb.append("Kustomisasi");
        }
        if (m instanceof DapatDinilai) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append("Rating");
        }
        if (sb.length() == 0) {
            sb.append("(tidak ada)");
        }
        System.out.println("   Kemampuan tambahan: " + sb);
    }

    // ---------- Fitur 4: Pesan minuman ----------
    // COMPILE-TIME POLYMORPHISM: dua versi prosesPesanan (beda jumlah parameter)
    static void prosesPesanan(Minuman m, int jumlah) {
        cetakStruk(m, jumlah, 0, m.hitungHarga(jumlah));
        simulasiPenyiapan(m);
    }

    static void prosesPesanan(Minuman m, int jumlah, double diskonPersen) {
        cetakStruk(m, jumlah, diskonPersen, m.hitungHarga(jumlah, diskonPersen));
        simulasiPenyiapan(m);
    }

    static void cetakStruk(Minuman m, int jumlah, double diskon, double total) {
        System.out.println("\n============= STRUK PEMESANAN =============");
        System.out.printf("Menu     : %s (%s, %s)%n", m.getNama(), m.getJenis(), m.getUkuran());
        System.out.printf("Jumlah   : %d gelas%n", jumlah);
        System.out.printf("Diskon   : %.0f%%%n", diskon);
        System.out.printf("Total    : Rp%,.0f%n", total);
        System.out.println("===========================================");
    }

    static void pesanMinuman() {
        if (jumlahMenu == 0) {
            System.out.println("Belum ada menu untuk dipesan.");
            return;
        }
        tampilkanSemua();
        int nomor = bacaInt("\nPilih nomor menu (1-" + jumlahMenu + "): ", 1, jumlahMenu);
        int jumlah = bacaInt("Jumlah pesanan: ", 1, 100);
        int pakaiDiskon = bacaInt("Punya kode diskon? (1=Ya, 2=Tidak): ", 1, 2);

        Minuman m = daftarMenu[nomor - 1];

        if (pakaiDiskon == 1) {
            double diskon = bacaDouble("Besar diskon (0-50 %): ", 0, 50);
            prosesPesanan(m, jumlah, diskon);   // overload 3 parameter
        } else {
            prosesPesanan(m, jumlah);           // overload 2 parameter
        }
    }

    // ---------- Fitur 5: Simulasi penyiapan SEMUA menu (Dynamic Binding) ----------
    static void simulasiSemuaMenu() {
        if (jumlahMenu == 0) {
            System.out.println("Belum ada menu.");
            return;
        }
        System.out.println("\n===== SIMULASI PENYIAPAN SEMUA MENU (DYNAMIC BINDING) =====");
        for (int i = 0; i < jumlahMenu; i++) {
            // Referensi bertipe Minuman, tetapi yang berjalan siapkan() milik wujud aslinya
            simulasiPenyiapan(daftarMenu[i]);
            cetakKemampuan(daftarMenu[i]);
        }
    }

    // ---------- Fitur 6: Aksi khusus (Interface + instanceof) ----------
    static void menuAksiKhusus() {
        if (jumlahMenu == 0) {
            System.out.println("Belum ada menu.");
            return;
        }
        tampilkanSemua();
        int nomor = bacaInt("\nPilih nomor menu (1-" + jumlahMenu + "): ", 1, jumlahMenu);
        Minuman m = daftarMenu[nomor - 1];

        System.out.println("\nMenu terpilih: " + m.getNama() + " (" + m.getJenis() + ")");
        cetakKemampuan(m);
        System.out.println("1. Kustomisasi menu   (interface DapatDikustomisasi)");
        System.out.println("2. Beri rating        (interface DapatDinilai)");
        System.out.println("3. Siapkan minuman    (abstract method)");
        int aksi = bacaInt("Pilih aksi (1-3): ", 1, 3);

        switch (aksi) {
            case 1:
                // instanceof dulu agar pemanggilan aman, baru downcast
                if (m instanceof DapatDikustomisasi) {
                    DapatDikustomisasi k = (DapatDikustomisasi) m;
                    k.kustomisasi();
                } else {
                    System.out.println(m.getNama() + " tidak bisa dikustomisasi "
                            + "(" + m.getJenis() + " tidak mengimplementasikan DapatDikustomisasi).");
                }
                break;
            case 2:
                if (m instanceof DapatDinilai) {
                    int bintang = bacaInt("Masukkan rating ("
                            + DapatDinilai.RATING_MIN + "-" + DapatDinilai.RATING_MAKS + "): ",
                            DapatDinilai.RATING_MIN, DapatDinilai.RATING_MAKS);
                    ((DapatDinilai) m).beriRating(bintang);
                } else {
                    System.out.println(m.getNama() + " tidak bisa diberi rating "
                            + "(" + m.getJenis() + " tidak mengimplementasikan DapatDinilai).");
                }
                break;
            default:
                simulasiPenyiapan(m);
        }
    }

    // ---------- Program utama ----------
    public static void main(String[] args) {
        isiDataAwal();

        int pilihan;
        do {
            System.out.println("\n=================================");
            System.out.println("      SMART COFFEE SHOP");
            System.out.println("=================================");
            System.out.println("1. Tambah Data Baru");
            System.out.println("2. Tampilkan Seluruh Data");
            System.out.println("3. Cari Menu");
            System.out.println("4. Pesan Minuman");
            System.out.println("5. Simulasi Penyiapan Semua Menu");
            System.out.println("6. Aksi Khusus (Kustomisasi / Rating)");
            System.out.println("0. Keluar");
            System.out.println("---------------------------------");
            pilihan = bacaInt("Pilih menu (0-6): ", 0, 6);

            switch (pilihan) {
                case 1:
                    tambahData();
                    break;
                case 2:
                    tampilkanSemua();
                    break;
                case 3:
                    menuCari();
                    break;
                case 4:
                    pesanMinuman();
                    break;
                case 5:
                    simulasiSemuaMenu();
                    break;
                case 6:
                    menuAksiKhusus();
                    break;
                case 0:
                    System.out.println("Terima kasih sudah berkunjung ke Smart Coffee Shop!");
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        } while (pilihan != 0);

        input.close();
    }
}
