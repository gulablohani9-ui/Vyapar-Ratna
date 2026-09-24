data class CommodityDhruvank(
    val name: String,        // गेहूँ, सोना, चाँदी...
    val dhruvank: Int,       // 14, 86, 81...
    val category: String     // धातु/मूल/जीव
)

// Sample data from pages 40-41
val commodityList = listOf(
    CommodityDhruvank("गेहूँ", 14, "मूल"),
    CommodityDhruvank("जौ", 57, "मूल"),
    CommodityDhruvank("चना", 56, "मूल"),
    CommodityDhruvank("ज्वार", 100, "मूल"),
    CommodityDhruvank("बाजरा", 34, "मूल"),
    CommodityDhruvank("चावल", 77, "मूल"),
    CommodityDhruvank("धान", 165, "मूल"),
    CommodityDhruvank("उड़द", 80, "मूल"),
    CommodityDhruvank("मूँग", 51, "मूल"),
    CommodityDhruvank("अरहर", 72, "मूल"),
    CommodityDhruvank("तिल", 53, "मूल"),
    CommodityDhruvank("तेल", 10, "मूल"),
    CommodityDhruvank("घृत", 50, "मूल"),
    CommodityDhruvank("सरसों", 88, "मूल"),
    CommodityDhruvank("गुड़", 40, "मूल"),
    CommodityDhruvank("खाँड", 102, "मूल"),
    CommodityDhruvank("मिश्री", 103, "मूल"),
    // ... continue for all 100+ items
)
