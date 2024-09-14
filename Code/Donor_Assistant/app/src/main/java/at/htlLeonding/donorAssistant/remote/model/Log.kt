package at.htlLeonding.donorAssistant.remote.model

data class LogInfo(
    val message: String,
    val timestamp: String,
    val category: String,
    val origin: String
)