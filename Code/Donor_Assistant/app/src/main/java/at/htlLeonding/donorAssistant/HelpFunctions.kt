package at.htlLeonding.donorAssistant

import at.htlLeonding.donorAssistant.remote.apiContact.DataAccess
import at.htlLeonding.donorAssistant.remote.model.Answer
import at.htlLeonding.donorAssistant.remote.model.Question
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.`object`.conversation.Say
import com.aldebaran.qi.sdk.`object`.locale.Language
import com.aldebaran.qi.sdk.`object`.locale.Locale
import com.aldebaran.qi.sdk.`object`.locale.Region
import com.aldebaran.qi.sdk.builder.SayBuilder
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Suppress(
    "DEPRECATION", "UNUSED_PARAMETER", "SpellCheckingInspection"
) // This is needed because we get warnings because Pepper is using old versions
class HelpFunctions {
    companion object {

        //region Variables

        /**
         * Set to true if you want to run the app on Pepper
         * This is important because Pepper can't say anything on the emulator and the app will crash
         * This is because the qIContext is null and never gets initialized
         */
        const val isOnPepper = true

        /**
         * The QiContext is needed to do something on Pepper
         */
        var qIContext: QiContext? = null

        /**
         * The DataAccess is needed to get the data from the API
         */
        var dataAccess: DataAccess? = null

        /**
         * Set to true if the app is in german at the moment
         */
        var isGerman = false

        /**
         * The quiz is a list of questions
         */
        lateinit var quiz: List<Question>

        /**
         * The SayQuestions are needed to say the questions of the quiz on Pepper
         */
        private lateinit var sayRandomQuestions: List<SayQuestion>

        /**
         * The questionCounter is needed to know which question is currently asked
         */
        var questionCounter: Int = 0

        /**
         * The quizIsRunning is needed to know if the quiz is currently running
         */
        var quizIsRunning = false

        /**
         * The amount of questions requested from the API
         */
        var amountOfQuestionsRequested: Int = 5

        /**
         * Hardcoded question added to the random quiz because we dont know how to force reload for a page
         */
        private val question = Question(
            1,
            "Hast du schon einmal Blut gespendet?",
            "Have you donated blood before?",
            mutableListOf(
                Answer(0, "Ja", "Yes", true),
                Answer(1, "Nein", "No", true),
                Answer(2, "Ich will nicht", "I don\'t want to", true),
                Answer(3, "Ich darf nicht", "I am not allowed to", true)
            )
        )

        //endregion

        //region Functions
        /**
         * This function is used to get a random quote from the API
         * @return the quote as a string
         */
        fun getQuotefromDataAccess(): String {
            return runBlocking {
                suspendCoroutine { continuation ->
                    launch {
                        val value: String =
                            if (isGerman) dataAccess!!.getRandomQuote().germanQuote else dataAccess!!.getRandomQuote().englishQuote//dataAccess.logout().toString()//
                        continuation.resume(value)
                    }
                }
            }
        }

        /**
         * This function is used to create a random quiz with questions from the API
         */
        fun createRandomQuiz() {
            val rquiz = runBlocking {
                suspendCoroutine<List<Question>> { continuation ->
                    launch {
                        //API provides 6 questions when entering 5 -> do 5-1 to ensure 5 questions
                        val value = dataAccess!!.getRandomQuestions(amountOfQuestionsRequested - 1)
                        continuation.resume(value)
                    }
                }
            }

            // Add the hardcoded question to the random quiz
            quiz = (listOf(question) + rquiz)

            // Create the Says for each question
            if (isOnPepper) {
                sayRandomQuestions = mutableListOf()
                for (i in quiz.indices) {
                    val sayRandomQuestion = SayQuestion(qIContext!!)
                    sayRandomQuestion.createSays(
                        quiz[i].germanQuestion, quiz[i].englishQuestion
                    )
                    (sayRandomQuestions as MutableList<SayQuestion>).add(sayRandomQuestion)
                }
            }
        }

        /**
         * Is used to start the quiz and say the first question
         */
        fun changeToQuizQuestion() {
            quizIsRunning = true
            if (isOnPepper) {
                pepperSayQuestion()
                //Say the question either in German or English
            }
        }

        /**
         * Is used say the current question
         * @return the question
         */
        fun pepperSayQuestion() {
            if (isGerman) sayRandomQuestions[questionCounter].sayQuestionGerman?.async()?.run()
            else sayRandomQuestions[questionCounter].sayQuestionEnglish?.async()?.run()

        }
        //endregion
    }

    //region SayQuestion
    internal class SayQuestion(private val qiContext: QiContext) {
        /**
         * The Say for the question in german
         */
        var sayQuestionGerman: Say? = null

        /**
         * The Say for the question in english
         */
        var sayQuestionEnglish: Say? = null

        /**
         * Is used to create the Says for the question
         * @param german the german question
         * @param english the english question
         */
        fun createSays(german: String, english: String) {
            if (isOnPepper) {
                sayQuestionGerman = SayBuilder.with(qiContext).withText(german)
                    .withLocale(Locale(Language.GERMAN, Region.GERMANY)).build()

                sayQuestionEnglish = SayBuilder.with(qiContext).withText(english)
                    .withLocale(Locale(Language.ENGLISH, Region.UNITED_STATES)).build()
            }

        }


    }
    //endregion

}