package at.htlLeonding.donorAssistant

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.toColorInt
import at.htlLeonding.donorAssistant.HelpFunctions.Companion.amountOfQuestionsRequested
import at.htlLeonding.donorAssistant.HelpFunctions.Companion.dataAccess
import at.htlLeonding.donorAssistant.HelpFunctions.Companion.isGerman
import at.htlLeonding.donorAssistant.HelpFunctions.Companion.isOnPepper
import at.htlLeonding.donorAssistant.HelpFunctions.Companion.qIContext
import at.htlLeonding.donorAssistant.HelpFunctions.Companion.questionCounter
import at.htlLeonding.donorAssistant.HelpFunctions.Companion.quiz
import at.htlLeonding.donorAssistant.HelpFunctions.Companion.quizIsRunning
import at.htlLeonding.donorAssistant.remote.apiContact.DataAccess
import at.htlLeonding.donorAssistant.remote.followMe.MainPresenter
import at.htlLeonding.donorAssistant.remote.followMe.MainView
import com.aldebaran.qi.Future
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.`object`.actuation.Animate
import com.aldebaran.qi.sdk.`object`.actuation.Animation
import com.aldebaran.qi.sdk.`object`.human.Human
import com.aldebaran.qi.sdk.`object`.humanawareness.HumanAwareness
import com.aldebaran.qi.sdk.builder.AnimateBuilder
import com.aldebaran.qi.sdk.builder.AnimationBuilder
import com.aldebaran.qi.sdk.builder.SayBuilder
import com.aldebaran.qi.sdk.design.activity.RobotActivity
import com.aldebaran.qi.sdk.design.activity.conversationstatus.SpeechBarDisplayStrategy
import com.softbankrobotics.facerecognition.*
import kotlinx.coroutines.*
import java.util.*
import kotlin.concurrent.thread
import mu.KotlinLogging
import com.softbankrobotics.facerecognition.*
import de.hdodenhof.circleimageview.CircleImageView
import java.sql.Timestamp
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import androidx.lifecycle.lifecycleScope

@Suppress(
    "DEPRECATION", "UNUSED_PARAMETER", "SpellCheckingInspection"
) // This is needed because we get warnings because Pepper is using old versions
class MainActivity : RobotActivity(), RobotLifecycleCallbacks, MainView {
    companion object {
        private const val TAG = "MainActivity"
    }

    //region Variables
    //private lateinit var remoteLogger: RemoteLogger
    //private var logger = LogManager.getLogger(MainActivity::class.java);
    //private val logger = KotlinLogging.logger {}
    /*val httpManager = HttpAppender.HttpManager(
        URL("https://student.cloud.htl-leonding.ac.at/l.federsel/logs"),
        "admin",
        "yuyhslEvcFXHB07xlUbB",
        layout,// Specify your layout here
        true
    )*/

    //val httpAppender = HttpAppender("http", layout, null, false, httpManager)

    private var mainPresenter: MainPresenter? = null

    private var currentEngagedHuman: Human? = null
    private var qiContext: QiContext? = null
    private var currentUserId: String? = null
    private val humanUnknownImage by lazy {
        BitmapFactory.decodeResource(
            resources, R.drawable.humanwho
        )
    }

    private val userNumbers = hashMapOf<String, Int>()

    //private val userNames = hashMapOf<Int, String>()
    private var numberCounter = 0
    private var currentSay = Future.of<Void>(null)
    private var newHuman = false

    private val humanFaceReco by lazy {
        HumanFaceRecognition()
    }

    /**
     * Amount of answers the user answered correctly
     */
    private var correctAnswers: Int = 0

    /**
     * Is the current question answered and take corresponding actions
     */
    private var answered: Boolean = false

    /**
     *  Animate object to play animations on pepper
     */
    private var animate: Animate? = null

    private var correctAnswerAnimation: Animate? = null
    private var wrongAnswerAnimation: Animate? = null

    /**
     * Array of the buttons available in a question
     */
    private var quizAnswerButtons: List<Int> =
        listOf(R.id.buttonanswer0, R.id.buttonanswer1, R.id.buttonanswer2, R.id.buttonanswer3)

    /**
     * Id of a picture which is a thumb up if answered correctly or a thumb down if answered wrong
     */
    private var quizImageThumbs: Int = R.id.imageThumbs

    /**
     * Sound which gets played when a question is answered correct
     **/
    private lateinit var mpcorrect: MediaPlayer

    /**
     * Sound which gets played when a question is answered wrong
     **/
    private lateinit var mpwrong: MediaPlayer

    /**
     * Sound which gets played when a quiz is finished
     **/
    private lateinit var mpfinish: MediaPlayer

    private var isReco = false

    private var humanStateId: Int = R.id.humanstate
    private var profileImageId: Int = R.id.profile_image
    //private var token: String = ""

    //endregion
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSpeechBarDisplayStrategy(SpeechBarDisplayStrategy.IMMERSIVE)
        setContentView(R.layout.activity_main)
        QiSDK.register(this, this)
        // Initialize DataAccess asynchronously


        dataAccess = DataAccess()
        lifecycleScope.launch {
            dataAccess!!.login { tk ->
                Log.i(javaClass.simpleName, "\n YAHHOOOOO TOKEN IS $tk\n")
                // Use the token if it's not null
            }
        }
        //remoteLogger = RemoteLogger()
        //remoteLogger.log("onCreate called", "INFO", MainActivity::class.java.simpleName)
        // Configurator.initialize(null, "log4j2.xml")
        //logger.error("onCreate has been called")
        //Log.i("LOGGER", "$logger")
    }

    //region PepperRobotFocusFunctions
    override fun onRobotFocusGained(qiContext: QiContext?) {
        //Log.i(TAG, "onRobotFocusGained")

        //Sets the qIContext so we can use it later for pepper animations etc.
        qIContext = qiContext

        changeLanguageToGermanNew()

        //Create the media players for the sound effects
        mpcorrect = MediaPlayer.create(this, R.raw.correct)
        mpwrong = MediaPlayer.create(this, R.raw.wrong)
        mpfinish = MediaPlayer.create(this, R.raw.finish)

        //If the app is running on pepper every time pepper sees a human it will wave
        if (isOnPepper) {
            val animation: Animation? =
                AnimationBuilder.with(qiContext).withResources(R.raw.wave).build()

            val humanAwareness: HumanAwareness = qiContext!!.humanAwareness

            animate = AnimateBuilder.with(qiContext).withAnimation(animation).build()

            animate?.async()?.run()

            //qiContext?.humanAwareness?.addOnEngagedHumanChangedListener(::onEngagedHumanChanged)
            //onEngagedHumanChanged(qiContext?.humanAwareness?.engagedHuman)
            /*qiContext.humanAwareness?.addOnEngagedHumanChangedListener(::onEngagedHumanChanged)
            onEngagedHumanChanged(qiContext.humanAwareness?.engagedHuman)*/

            /*qIContext?.humanAwareness?.addOnEngagedHumanChangedListener{
                    human -> human?.let {
                if (!quizIsRunning && !isReco) { //Only wave if the quiz isn't running in order to not disturb the user (because peppers arms are moving)
                    animate = AnimateBuilder.with(qiContext).withAnimation(animation).build()
                    animate?.async()?.run()
                }
            }
            }*/

            // Wave everytime pepper sees a human
            //remoteLogger.log("Pepper is waving", "INFO", MainActivity::class.java.simpleName)
            humanAwareness.addOnEngagedHumanChangedListener { human ->
                human?.let {
                    if (!quizIsRunning && !isReco) { //Only wave if the quiz isn't running in order to not disturb the user (because peppers arms are moving)
                        animate = AnimateBuilder.with(qiContext).withAnimation(animation).build()
                        animate?.async()?.run()
                    }
                }
            }

            //qIContext?.humanAwareness?.addOnEngagedHumanChangedListener(::onEngagedHumanChanged)
            //onEngagedHumanChanged(qIContext?.humanAwareness?.engagedHuman)

            val animationCorrectAnswer: Animation? =
                AnimationBuilder.with(qiContext).withResources(R.raw.question_right).build()
            correctAnswerAnimation =
                AnimateBuilder.with(qiContext).withAnimation(animationCorrectAnswer).build()

            val animationWrongAnswer: Animation? =
                AnimationBuilder.with(qiContext).withResources(R.raw.question_wrong).build()
            wrongAnswerAnimation =
                AnimateBuilder.with(qiContext).withAnimation(animationWrongAnswer).build()

            mainPresenter = MainPresenter(this, this, qiContext)
        }
    }

    override fun onRobotFocusLost() {
        Log.i(TAG, "onRobotFocusLost")
        mainPresenter?.onDestroy()
        qiContext?.humanAwareness?.removeAllOnEngagedHumanChangedListeners()
        currentEngagedHuman?.removeAllOnFacePictureChangedListeners()
    }

    override fun onRobotFocusRefused(reason: String?) {
        Log.e(TAG, "onRobotFocusRefused: $reason")
    }

    override fun onDestroy() {
        QiSDK.unregister(this, this)
        super.onDestroy()
        mainPresenter?.onDestroy()
    }
    //endregion

    //region UIFunctions
    fun changeToDownloadView(view: View) {
        setContentView(R.layout.download_app)
    }

    fun changeToMainView(view: View) {
        changeLanguageToGerman(view)
        setContentView(R.layout.activity_main)
        // Get the Quote from the API
        getQuote()
    }

    fun changeToAboutUsView(view: View) {
        setContentView(R.layout.about_me)
    }

    fun changeToQuizQuestion(view: View) {
        setContentView(R.layout.quiz_question)
        // Start the quiz
        HelpFunctions.changeToQuizQuestion()
    }

    fun changeToQuizSizeSelection(view: View) {
        setContentView(R.layout.choose_quiz_size_page)
        updateCounter()
    }

    @SuppressLint("MissingInflatedId")
    fun changeToLoadingScreen(view: View) {
        //this is a thread which creates a random quiz
        val t = thread(start = true) {
            HelpFunctions.createRandomQuiz()
        }
        t.join()
        changeToQuizQuestion(view)
    }

    /**
     * Changes the language to German and updates the UI
     */
    private fun changeLanguageToGerman(view: View) {
        val config: Configuration = resources.configuration
        config.locale = Locale("de")
        isGerman = true
        resources.updateConfiguration(config, resources.displayMetrics)
        setContentView(R.layout.activity_main)
        getQuote()
    }

    private fun changeLanguageToGermanNew() {
        val config: Configuration = resources.configuration
        config.locale = Locale("de")
        isGerman = true
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    /**
     * Changes the language to English and updates the UI
     */
    private fun changeLanguageToEnglish(view: View) {
        val config: Configuration = resources.configuration
        config.locale = Locale("en")
        isGerman = false
        resources.updateConfiguration(config, resources.displayMetrics)
        setContentView(R.layout.activity_main)
        getQuote()
    }

    /**
     * Changes the language and the flag in the UI
     */
    fun changeLanguage(view: View) {
        if (isGerman) {
            changeLanguageToEnglish(view)
            val flag: ImageView = findViewById(R.id.imageView5)
            flag.setImageResource(R.drawable.flag_of_austria)
            /*remoteLogger.log(
                "Changed Lang. to English", "INFO", MainActivity::class.java.simpleName
            )*/
        } else {
            changeLanguageToGerman(view)
            val flag: ImageView = findViewById(R.id.imageView5)
            flag.setImageResource(R.drawable.flag_of_uk)
            //remoteLogger.log("Changed Lang. to German", "INFO", MainActivity::class.java.simpleName)
        }
    }

    /**
     * Goes to quiz page
     */
    @SuppressLint("CutPasteId", "MissingInflatedId")
    fun goToQuizPage(view: View) {
        //remoteLogger.log("\nGoing to quiz page \n", "INFO", MainActivity::class.java.simpleName)
        setContentView(R.layout.quiz_question)

        //Get the button and make it invisible
        val nextQuestionButton: Button = findViewById(R.id.nextQuestion)
        nextQuestionButton.visibility = View.INVISIBLE

        if (questionCounter >= quiz.size) {
            goToQuizResults(view)
        } else {
            updateQuestionAnswers()
        }
    }

    /**
     * Checks if the given answer is correct takes corresponding actions
     */
    fun checkAnswer(view: View) {
        var correct = false
        if (!answered) {
            //Get the answer of a question when the user clicks on an answer button
            val index = view.tag.toString().toInt()
            val thisAnswer = quiz[questionCounter].answers[index]

            //Check if it's correct
            if (thisAnswer.correct) {
                correct = true
                correctAnswers++
            }

            //Set to false to ensure that the user can't click on another answer
            answered = false

            //Update the UI to show the correct answer and play a sound effect
            runOnUiThread {
                for (i in 0..3) {
                    val button: Button = findViewById(quizAnswerButtons[i])
                    button.isClickable = false
                    if (quiz[questionCounter].answers[i].correct) {
                        button.setBackgroundColor("#50C878".toColorInt())
                    } else {
                        button.setBackgroundColor("#FA5252".toColorInt())
                    }

                    val imagebutton: Button = findViewById(quizImageThumbs)
                    if (thisAnswer.correct) {
                        imagebutton.background = AppCompatResources.getDrawable(
                            applicationContext, R.drawable.new_thumbs_up
                        )
                        if (isOnPepper) {
                            mpcorrect.start()
                        }
                    } else {
                        imagebutton.background = AppCompatResources.getDrawable(
                            applicationContext, R.drawable.new_thumbs_down
                        )
                        if (isOnPepper) {
                            mpwrong.start()
                        }
                    }
                    imagebutton.visibility = View.VISIBLE
                    imagebutton.startAnimation(
                        AnimationUtils.loadAnimation(
                            applicationContext, R.anim.move_in
                        )
                    )
                }
            }

            //Increase the question counter
            questionCounter++

            //Make the next question button visible
            val nextQuestionButton: Button = findViewById(R.id.nextQuestion)
            nextQuestionButton.visibility = View.VISIBLE
        }
        if (isOnPepper && correct) {
            //(WIP) Play animation on pepper if the answer is correct
            correctAnswerAnimation?.async()?.run()?.get()
        } else if (isOnPepper) {
            //(WIP) Play animation on pepper if the answer is NOT correct
            wrongAnswerAnimation?.async()?.run()?.get()
        }
    }

    /**
     * Updates the UI to show the next question and answers in either German or English
     */
    private fun updateQuestionAnswers() {
        runOnUiThread {

            val question: TextView = findViewById(R.id.quizquestion)
            question.text =
                if (isGerman) quiz[questionCounter].germanQuestion else quiz[questionCounter].englishQuestion
            question.isClickable = true

            for (i in quizAnswerButtons.indices) {
                val answer: Button = findViewById(quizAnswerButtons[i])
                answer.text =
                    if (isGerman) quiz[questionCounter].answers[i].germanAnswer else quiz[questionCounter].answers[i].englishAnswer
                answer.isClickable = true
            }

            if (isOnPepper) {
                HelpFunctions.pepperSayQuestion()
            }
        }
    }


    fun addOneToQuestions(view: View) {
        amountOfQuestionsRequested++
        updateCounter()
    }

    fun removeOneFromQuestions(view: View) {
        amountOfQuestionsRequested--
        updateCounter()
    }

    private fun updateCounter() {
        val et: TextView = findViewById(R.id.textView4)
        et.text = amountOfQuestionsRequested.toString()
    }

    /**
     * Show the number of correct questions
     */
    @SuppressLint("SetTextI18n")
    private fun updateCorrectCounter() {
        //Update the UI to show the correct answers
        runOnUiThread {
            findViewById<TextView>(R.id.question_correct).text = "$correctAnswers/$questionCounter"
        }
    }

    /**
     * Get the quote from the API and show it in the UI
     */
    private fun getQuote() {
        val quote = HelpFunctions.getQuotefromDataAccess()
        val text: TextView = findViewById(R.id.textViewQuote)
        Log.i(TAG, "The quote gotten is"+text)
        text.text = quote
    }

    fun quitQuiz(view: View) {
        goToQuizResults(view)
    }

    private fun goToQuizResults(view: View) {
        setContentView(R.layout.quiz_results)
        updateCorrectCounter()
        if (isOnPepper) {
            mpfinish.start()
        }
        questionCounter = 0
        correctAnswers = 0
        amountOfQuestionsRequested = 5
        quizIsRunning = false
    }

    override fun showLookingForHuman() {
        return
    }

    override fun showReadyToFollow() {
        return
    }

    override fun showStopFollowing() {
        return
    }

    fun startRecognition(view: View) {
        isReco = true
        setContentView(R.layout.facereco)

        // maybe use a normal image view instead of the circle image view


    }

    fun stopRecognition(view: View) {
        isReco = false
        setContentView(R.layout.activity_main)

    }

    private fun onEngagedHumanChanged(human: Human?) {
        Log.i(TAG, "onEngagedHumanChanged")
        runOnUiThread {
            when (human) {
                null -> {
                    isReco = false
                    humanDisengaged()
                }
                currentEngagedHuman -> {
                    Log.i(TAG, "Human already engaged")
                }
                else -> {
                    isReco = true
                    newHumanEngaged(human)
                }
            }
        }
    }

    private fun humanDisengaged() {
        runOnUiThread {
            if (isReco) {
                Log.i(TAG, "Human disengaged")
                mainPresenter?.stopFollowing()
                currentEngagedHuman?.let {
                    it.async().removeAllOnFacePictureChangedListeners()
                    humanFaceReco.removeAllOnFaceIdAvailableListener(it)
                }
                currentEngagedHuman = null
                currentUserId = null
                //val humanstate = findViewById<TextView>(humanStateId)
                //humanstate?.text = "No Human"
                val profileImage = findViewById<ImageView>(profileImageId)
                profileImage?.setImageBitmap(humanUnknownImage)
                profileImage?.visibility = View.INVISIBLE
            }
        }
    }

    private fun newHumanEngaged(human: Human) {
        Log.i(TAG, "new Human engaged")

        runOnUiThread {
            currentEngagedHuman?.let {
                it.async().removeAllOnFacePictureChangedListeners()
                humanFaceReco.removeAllOnFaceIdAvailableListener(it)
            }
            currentEngagedHuman = human
            currentUserId = null
            //val humanstate = findViewById<TextView>(humanStateId)
            //humanstate?.text = "Human engaged: Unknown"
            val profileImage = findViewById<ImageView>(profileImageId)
            profileImage?.setImageBitmap(humanUnknownImage)
            profileImage?.visibility = View.INVISIBLE

            human.async().facePicture.andThenConsume {
                it.getBitmap()?.let {
                    runOnUiThread {
                        val profileImage = findViewById<ImageView>(profileImageId)
                        profileImage?.setImageBitmap(it)
                    }
                }
            }
            profileImage?.visibility = View.INVISIBLE
            human.async().addOnFacePictureChangedListener {
                it.getBitmap()?.let {
                    runOnUiThread {
                        val profileImage = findViewById<ImageView>(profileImageId)
                        profileImage?.setImageBitmap(it)

                    }
                }
            }
            profileImage?.visibility = View.INVISIBLE
            humanFaceReco.addOnFaceIdAvailableListener(human, object : OnFaceIdAvailableListener {
                override fun onFaceIdAvailable(faceId: String) {
                    onFaceIdAvailable(human, faceId)
                }
            })
        }
    }

    fun onFaceIdAvailable(human: Human, faceId: String) {
        runOnUiThread {
            /*if(newHuman)
            {
                val textBoxName = findViewById<EditText>(R.id.editTextTextPersonName)
                textBoxName?.visibility = View.VISIBLE
                val buttonName = findViewById<Button>(R.id.buttonName)
                buttonName?.visibility = View.VISIBLE
                buttonName?.setOnClickListener {
                    val name = textBoxName?.text.toString()
                    val userNumber = userNumbers[faceId]
                    if (userNumber != null) {
                        assignUserName(name, userNumber)
                    }
                    newHuman = false
                    textBoxName?.visibility = View.INVISIBLE
                    buttonName?.visibility = View.INVISIBLE
                }
            }*/
            // If we are still talking about same human:
            if (currentEngagedHuman == human) {
                Log.i(TAG, "Found face $faceId")
                val newlyAssignedNumber = !userNumbers.containsKey(faceId)
                val userNumber = userNumbers[faceId] ?: assignUserNumber(faceId)
                //val userName = userNames[userNumber] ?: "Unknown"
                //val humanstate = findViewById<TextView>(humanStateId)
                //humanstate?.text = "Human engaged: $userNumber"


                // entweder mit listen den namen holen (ist eh wie man hier sieht ausprogrammiert worden, geht aber nicht wahrscheinlich wegen dem follow me)

                // oder ein edit field einbauen, welches sich den namen holt und etc. etc.
                // (vll ein bool der auf true gesetzt wird, wenn er jemanden erkannt hat und den namen wissen will
                // -> wenn dieser bool auf true gesetzt wurde kann der pepper nur draufwarten, bis er den button gedrückt hat
                // (button setzt den bool auf false und man kann ganz normal weitermachen))

                val greet: String
                if (newlyAssignedNumber) {
                    //val textBoxName = findViewById<EditText>(R.id.editTextTextPersonName)
                    greet = "Hello! This is the first time I have seen you, what is your name?"
                    newHuman = true
                } else {
                    greet = "Hello bro ! Nice to see you again!"
                }

                if (currentSay.isDone) {
                    // Do not stacks several Say actions
                    SayBuilder.with(qIContext).withText(greet).buildAsync().andThenConsume { say ->
                        currentSay = say.async().run()
                    }
                }
            }

        }
    }

    private fun assignUserNumber(faceId: String): Int {
        numberCounter += 1
        userNumbers[faceId] = numberCounter
        saveUserNumber(faceId, numberCounter)
        return numberCounter
    }

    private fun saveUserNumber(id: String, number: Int) {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putInt(id, number)
        editor.apply()
    }

    /*fun assignUserName(name: String, userNumber: Int)
    {
        userNames[userNumber] = name
    }*/
    //endregion
}


