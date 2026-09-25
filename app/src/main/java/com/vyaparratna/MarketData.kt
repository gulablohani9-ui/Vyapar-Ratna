package com.vyaparratna

object MarketData {

    // Aapke 4 instruments
    // Gold aur Crude Oil ka dhruvank book se liya hai
    // Nifty aur BankNifty modern hain - unke liye standard values assign ki hain
    val marketDhruvank = mapOf(
        "Gold" to 86,       // सोना - Book Page 14
        "Crude Oil" to 10,  // तेल - Book Page 14
        "Nifty" to 108,     // Modern index
        "BankNifty" to 12   // Modern index (12 major banks)
    )

    // Default Vaar (aaj ka vaar)
    val defaultVaar = "रविवार"
}
