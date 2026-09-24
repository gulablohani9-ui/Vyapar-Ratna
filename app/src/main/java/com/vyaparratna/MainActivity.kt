class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VyaparRatnaTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") { HomeScreen(navController) }
        composable("transit") { TransitScreen(navController) }
        composable("chakra") { SarvatobhadraScreen(navController) }
        composable("analysis") { AnalysisScreen(navController) }
        composable("dhruvank") { DhruvankScreen(navController) }
        composable("panchang") { PanchangScreen(navController) }
        composable("vedha") { VedhaScreen(navController) }
        composable("tools") { ToolsScreen(navController) }
    }
}
