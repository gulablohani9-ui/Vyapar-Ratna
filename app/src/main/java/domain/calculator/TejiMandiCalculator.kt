class TejiMandiCalculator {
    
    fun calculate(
        commodity: String,      // e.g., "रुई"
        date: LocalDate,        // e.g., 2024-05-15
        city: String,           // e.g., "बम्बई"
        currentPrice: Double,   // e.g., 204.00
        panchang: PanchangData  // From ephemeris
    ): PredictionResult {
        
        // Step 1: Get commodity dhruvank
        val commodityDhruvank = DhruvankRepository.getCommodity(commodity) // 77
        
        // Step 2: Get panchang elements
        val nakshatra = panchang.nakshatra      // "विशाखा" = 320
        val tithi = panchang.tithi              // "त्रयोदशी" = 11
        val vaar = panchang.vaar                // "गुरुवार" = 65
        val sankranti = panchang.sankranti      // "मेष" = 37
        val maas = panchang.maas                // "वैशाख" = 63
        val nagar = DhruvankRepository.getCity(city) // "बम्बई" = 188
        
        // Step 3: Sum all dhruvanks
        val total = commodityDhruvank + nakshatra + tithi + vaar + 
                    sankranti + maas + nagar
        // 77 + 320 + 11 + 65 + 37 + 63 + 188 = 771
        
        // Step 4: Divide by 8, get remainder
        val remainder = total % 8  // 771 % 8 = 3 (as per book: 771÷8 = 96, remainder 3)
        
        // Step 5: Map remainder to planet (starting from Vaar's lord)
        // If Vaar is Thursday (Guru), start counting from Guru
        val planetSequence = listOf("गुरु", "शनि", "सूर्य", "चन्द्र", "मंगल", 
                                     "राहु", "बुध", "केतु", "शुक्र")
        val startingIndex = planetSequence.indexOf(vaarLord) // गुरु = 0
        val resultPlanet = planetSequence[(startingIndex + remainder - 1) % 9]
        
        // Step 6: Determine Teji/Mandi
        val isTeji = resultPlanet in listOf("सूर्य", "मंगल", "राहु", "शनि", "केतु")
        val isMandi = resultPlanet in listOf("चन्द्र", "गुरु", "शुक्र")
        
        return PredictionResult(
            isTeji = isTeji,
            isMandi = isMandi,
            planet = resultPlanet,
            dhruvank = total,
            remainder = remainder
        )
    }
}
