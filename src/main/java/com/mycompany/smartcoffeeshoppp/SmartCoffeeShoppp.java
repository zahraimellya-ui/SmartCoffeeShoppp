package com.mycompany.smartcoffeeshoppp;

import java.util.Scanner;

// =====================================================
// SUPERCLASS: Minuman
// =====================================================
class Minuman {
    // Encapsulation: semua field private
    private String nama = "Tanpa Nama";
    private double harga = 0;
    private String ukuran = "Medium";

    // Static: pencatat total objek yang berhasil dibuat
    private static int jumlahMinuman = 0;

    // Constructor (memakai setter agar data tervalidasi)
    public Minuman(String nama, double harga, String ukuran) {
        setNama(nama);
        setHarga(harga);
        setUkuran(ukuran);
        jumlahMinuman++;
    }

    // Getter 
    public String getNama() {
        return nama;
    }

    public double getHarga() {
        return harga;
    }

    public String getUkuran() {
        return ukuran;
    }

    // Setter dengan validasi 
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

    // Static method 
    public static int getJumlahMinuman() {
        return jumlahMinuman;
    }

    // Method Overloading
    public double hitungHarga(int jumlah) {
        return harga * jumlah;
    }

    public double hitungHarga(int jumlah, double diskonPersen) {
        double total = hitungHarga(jumlah);   // memakai versi subclass (dynamic binding)
        return total - (total * diskonPersen / 100);
    }

    // Method yang di-override subclass 
    public String getJenis() {
        return "Minuman";
    }

    public String getDetail() {
        return "-";
    }

    // Menampilkan satu baris tabel (memanggil method hasil overriding)
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

// =====================================================
// SUBCLASS 1: Kopi
// =====================================================
class Kopi extends Minuman {
    private String jenisBiji = "Arabika";
    private int jumlahShot = 1;

    public Kopi(String nama, double harga, String ukuran, String jenisBiji, int jumlahShot) {
        super(nama, harga, ukuran);   // memanggil constructor superclass
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

    // Overriding: tiap shot ekstra +Rp3.000 per gelas
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
}

// =====================================================
// SUBCLASS 2: NonKopi
// =====================================================
class NonKopi extends Minuman {
    private String rasa = "Original";
    private boolean pakaiEs = false;

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

    // Overriding: minuman dingin +Rp2.000 per gelas
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
}

// =====================================================
// MAIN PROGRAM
// =====================================================
public class SmartCoffeeShoppp {

    static final Scanner input = new Scanner(System.in);

    // Array penyimpan objek (bertipe superclass, berisi objek subclass)
    static Minuman[] daftarMenu = new Minuman[50];
    static int jumlahMenu = 0;

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

    // Tampilan tabel 
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

    // Data awal (5 objek) 
    static void isiDataAwal() {
        daftarMenu[jumlahMenu++] = new Kopi("Espresso", 15000, "Small", "Arabika", 2);
        daftarMenu[jumlahMenu++] = new Kopi("Cappuccino", 22000, "Medium", "Robusta", 1);
        daftarMenu[jumlahMenu++] = new Kopi("Latte", 25000, "Large", "Arabika", 2);
        daftarMenu[jumlahMenu++] = new NonKopi("Matcha Latte", 24000, "Medium", "Matcha", true);
        daftarMenu[jumlahMenu++] = new NonKopi("Coklat Panas", 20000, "Medium", "Coklat", false);
    }

    // Fitur 1: Tambah data 
    static void tambahData() {
        if (jumlahMenu >= daftarMenu.length) {
            System.out.println("Kapasitas menu penuh!");
            return;
        }

        System.out.println("\n--- TAMBAH MENU BARU ---");
        System.out.println("1. Kopi");
        System.out.println("2. Non Kopi");
        int jenis = bacaInt("Pilih tipe minuman (1-2): ", 1, 2);

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

        if (jenis == 1) {
            int pilihBiji = bacaInt("Jenis biji (1=Arabika, 2=Robusta): ", 1, 2);
            String biji = (pilihBiji == 1) ? "Arabika" : "Robusta";
            int shot = bacaInt("Jumlah shot (1-3): ", 1, 3);
            daftarMenu[jumlahMenu++] = new Kopi(nama, harga, ukuran, biji, shot);
        } else {
            String rasa = bacaTeks("Rasa               : ");
            int pilihEs = bacaInt("Penyajian (1=Dingin, 2=Panas): ", 1, 2);
            daftarMenu[jumlahMenu++] = new NonKopi(nama, harga, ukuran, rasa, pilihEs == 1);
        }
        System.out.println("Menu berhasil ditambahkan!");
    }

    // Fitur 2: Tampilkan seluruh data
    static void tampilkanSemua() {
        System.out.println("\n=========================== DAFTAR MENU SMART COFFEE SHOP ===========================");
        if (jumlahMenu == 0) {
            System.out.println("Belum ada data menu.");
            return;
        }
        cetakHeader();
        for (int i = 0; i < jumlahMenu; i++) {
            daftarMenu[i].tampilInfo(i + 1);   
        }
        cetakGaris();
        System.out.println("Total menu tampil          : " + jumlahMenu);
        System.out.println("Total objek dibuat (static): " + Minuman.getJumlahMinuman());
    }

    // Fitur 3: Pencarian (Method Overloading) 
    // Cari berdasarkan nama
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

    // Cari berdasarkan harga jual maksimal
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
            hasil = cariMenu(keyword);               
        } else {
            double maks = bacaDouble("Masukkan harga maksimal (Rp): ", 1, 1000000);
            hasil = cariMenu(maks);                  
        }

        if (hasil == 0) {
            System.out.println("Data tidak ditemukan.");
        } else {
            cetakGaris();
            System.out.println("Ditemukan " + hasil + " menu.");
        }
    }

    // Fitur 4: Pesan minuman (overloading hitungHarga) 
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
        double total;
        double diskon = 0;

        if (pakaiDiskon == 1) {
            diskon = bacaDouble("Besar diskon (0-50 %): ", 0, 50);
            total = m.hitungHarga(jumlah, diskon);   
        } else {
            total = m.hitungHarga(jumlah);          
        }

        System.out.println("\n============= STRUK PEMESANAN =============");
        System.out.printf("Menu     : %s (%s, %s)%n", m.getNama(), m.getJenis(), m.getUkuran());
        System.out.printf("Jumlah   : %d gelas%n", jumlah);
        System.out.printf("Diskon   : %.0f%%%n", diskon);
        System.out.printf("Total    : Rp%,.0f%n", total);
        System.out.println("===========================================");
    }

    // Program utama 
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
            System.out.println("0. Keluar");
            System.out.println("---------------------------------");
            pilihan = bacaInt("Pilih menu (0-4): ", 0, 4);

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
