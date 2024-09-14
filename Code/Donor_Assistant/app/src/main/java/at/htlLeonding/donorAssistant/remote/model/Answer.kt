package at.htlLeonding.donorAssistant.remote.model

data class Answer(
    val id: Int = 0, val germanAnswer: String, val englishAnswer: String, val correct: Boolean
)