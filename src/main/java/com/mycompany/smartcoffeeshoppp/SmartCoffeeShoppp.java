package com.mycompany.smartcoffeeshoppp;

import java.util.Scanner;

public class SmartCoffeeShoppp {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        String[] namaMenu = {"Espresso", "Cappuccino", "Matcha Latte", "Lemon Tea"};
        double[] hargaMenu = {15000, 22000, 24000, 18000};
        int pilihan;

        do {
            
            System.out.println("\n===== SMART COFFEE SHOP =====");
            for (int i = 0; i < namaMenu.length; i++) {
                System.out.println((i + 1) + ". " + namaMenu[i] + " - Rp" + hargaMenu[i]);
            }
            System.out.println("0. Keluar");

            System.out.print("Pilih menu: ");
            pilihan = input.nextInt();

            if (pilihan >= 1 && pilihan <= namaMenu.length) {
                System.out.print("Jumlah: ");
                int jumlah = input.nextInt();

                double total = hargaMenu[pilihan - 1] * jumlah;

                if (jumlah >= 5 || total >= 100000) {
                    double diskon = total * 0.1;
                    total = total - diskon;
                    System.out.println("Selamat, Anda dapat diskon 10%!");
                }

                System.out.println("Pesanan : " + namaMenu[pilihan - 1] + " x" + jumlah);
                System.out.println("Total bayar: Rp" + total);
            } else if (pilihan != 0) {
                System.out.println("Pilihan tidak valid!");
            }

        } while (pilihan != 0);

        
        System.out.print("Beri rating (1-3): ");
        int rating = input.nextInt();

        switch (rating) {
            case 1:
                System.out.println("Kami akan berusaha lebih baik.");
                break;
            case 2:
                System.out.println("Terima kasih!");
                break;
            case 3:
                System.out.println("Senang bisa melayani Anda!");
                break;
            default:
                System.out.println("Rating tidak valid.");
        }

        System.out.println("Terima kasih sudah berkunjung!");
        input.close();
    }
}