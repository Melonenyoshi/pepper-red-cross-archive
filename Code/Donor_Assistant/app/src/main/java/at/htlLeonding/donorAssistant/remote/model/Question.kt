package at.htlLeonding.donorAssistant.remote.model

data class Question(
    val id: Int = 0,
    val germanQuestion: String,
    val englishQuestion: String,
    val answers: MutableList<Answer>
)