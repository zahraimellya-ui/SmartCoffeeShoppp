package com.mycompany.smartcoffeeshoppp;

// =====================================================
// INTERFACE 2: kontrak "bisa dinilai pelanggan"
// Menjawab pertanyaan: "Menu ini BISA diberi rating?"
// =====================================================
public interface DapatDinilai {
    // Konstanta (otomatis public static final)
    int RATING_MIN = 1;
    int RATING_MAKS = 5;

    // Otomatis public abstract
    void beriRating(int bintang);
}
