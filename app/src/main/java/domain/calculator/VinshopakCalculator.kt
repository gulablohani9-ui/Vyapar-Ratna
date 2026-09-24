fun calculateVinshopak(
    vedhaPaad: Int,      // 1-4 (एक, दो, तीन, पूर्ण)
    drishtiPaad: Int,    // 1-4
    isShubhGraha: Boolean
): Double {
    val base = if (isShubhGraha) 5.0 else 4.0  // 20/4 = 5, 20/5 = 4
    return (vedhaPaad / 4.0) * (drishtiPaad / 4.0) * base
}

fun calculateFinalPrediction(
    totalShubhVinshopak: Double,
    totalAshubhVinshopak: Double,
    currentPrice: Double
): PricePrediction {
    val diff = totalShubhVinshopak - totalAshubhVinshopak
    val priceChange = (currentPrice / 20.0) * diff
    
    return PricePrediction(
        newPrice = currentPrice + priceChange,
        change = priceChange,
        isTeji = priceChange > 0,
        isMandi = priceChange < 0
    )
}
