@file:Suppress("SpellCheckingInspection", "UNUSED_PARAMETER", "CallToThreadRun")

package com.example.feedbackpepper

import android.annotation.SuppressLint
import android.os.Bundle
//import android.content.ContentValues.TAG
//import android.os.Bundle
//import android.util.Log
import android.view.View
import android.widget.RatingBar
//import com.aldebaran.qi.Future
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.`object`.actuation.Animate
import com.aldebaran.qi.sdk.`object`.actuation.Animation
import com.aldebaran.qi.sdk.`object`.conversation.BodyLanguageOption
import com.aldebaran.qi.sdk.`object`.conversation.Say
import com.aldebaran.qi.sdk.`object`.human.*
import com.aldebaran.qi.sdk.`object`.humanawareness.HumanAwareness
import com.aldebaran.qi.sdk.builder.AnimateBuilder
import com.aldebaran.qi.sdk.builder.AnimationBuilder
import com.aldebaran.qi.sdk.builder.SayBuilder
import com.aldebaran.qi.sdk.core.QiThreadPool.*
import com.aldebaran.qi.sdk.design.activity.RobotActivity
import com.aldebaran.qi.sdk.design.activity.conversationstatus.SpeechBarDisplayStrategy
import com.aldebaran.qi.sdk.`object`.touch.Touch
import com.aldebaran.qi.sdk.`object`.touch.TouchSensor

class MainActivity : RobotActivity(), RobotLifecycleCallbacks {

    // region Variables
    //private var qiContext: QiContext? = null
    //private var touchedHead:Boolean= false
    private var askForFeedbackBoolean: Boolean = false
    private var startedFeedback: Boolean = false
    //private var firstStart: Boolean = true
    private var isLookingForHumans = false
    private var feedbackStates: FeedbackStates = FeedbackStates.STAYINHOLDCOIN /*private var feedback = mutableListOf<Feedback>()*/
    //private var liked: Boolean = false
    //private var checkCount: Int = 0
    //private var xCount: Int = 0
    private var humanAwareness: HumanAwareness? = null
    private lateinit var touch: Touch
    private lateinit var touchSensor: TouchSensor
    //private lateinit var stayInGetCoinThread: Thread
    //private lateinit var stayInGiveCoinThread: Thread
    //private lateinit var stayInHoldCoinThread: Thread
    //endregion

    //region Animation_Variables
    private lateinit var giveCoinAnimation: Animation
    private lateinit var waveAnimation: Animation
    private lateinit var wave: Animate
    private lateinit var getCoinAnimation: Animation
    private lateinit var getAndGiveCoinAnimation: Animation
    private lateinit var getAndGiveCoin: Animate
    private lateinit var giveCoin: Animate
    private lateinit var getCoin: Animate
    private lateinit var getIntoGetCoinPositionAnimation: Animation
    private lateinit var getIntoGetCoinPosition: Animate
    private lateinit var holdCoinAnimation: Animation
    private lateinit var holdCoin: Animate
    private lateinit var getIntoGiveCoinPositionAnimation: Animation
    private lateinit var getIntoGiveCoinPosition: Animate
    private lateinit var stayInGiveCoinPositionAnimation: Animation
    private lateinit var stayInGiveCoinPosition: Animate
    private lateinit var getIntoTakeCoinPositionAnimation: Animation
    private lateinit var getIntoTakeCoinPosition: Animate
    private lateinit var stayInTakeCoinPositionAnimation: Animation
    private lateinit var stayInTakeCoinPosition: Animate
    private lateinit var takeToHoldAnimation: Animation
    private lateinit var takeToHold: Animate
    private lateinit var giveToHoldAnimation: Animation
    private lateinit var giveToHold: Animate

    //endregion

    //region Say_Variables
    private lateinit var askForFeedback: Say
    private lateinit var askbranch : Say
    private lateinit var thanksForFeedback: Say
    private lateinit var what: Say
    private lateinit var what2: Say
    private lateinit var sayState: Say
    private lateinit var sayTest : Say
    //endregion


    enum class FeedbackStates {
        /*GETCOIN,*/ GIVECOIN, ASKFEEDBACK, /*WAITFEEDBACK, STAYINTAKECOIN,*/ STAYINGIVECOIN, STAYINHOLDCOIN, /*FINDHUMAN,*/ GOTOHOLDCOIN, GOFROMGIVETOHOLDCOIN
    }

    private fun initializeVariables(qiContext: QiContext?) {


        giveCoinAnimation =
            AnimationBuilder.with(qiContext).withResources(R.raw.give_gift_complete).build()

        giveCoin = AnimateBuilder.with(qiContext).withAnimation(giveCoinAnimation).build()

        getCoinAnimation = AnimationBuilder.with(qiContext).withResources(R.raw.hold_coin).build()

        getCoin = AnimateBuilder.with(qiContext).withAnimation(getCoinAnimation).build()

        getAndGiveCoinAnimation =
            AnimationBuilder.with(qiContext).withResources(R.raw.give_gift_complete).build()

        getAndGiveCoin = AnimateBuilder.with(qiContext).withAnimation(getAndGiveCoinAnimation).build()

        askForFeedback = SayBuilder.with(qiContext).withText("Hi. Möchtest du die HTL Leonding in Zukunft besuchen?").withBodyLanguageOption(BodyLanguageOption.DISABLED).build()

        thanksForFeedback = SayBuilder.with(qiContext).withText("Danke für dein Feedback!").withBodyLanguageOption(BodyLanguageOption.DISABLED).build()

        askbranch = SayBuilder.with(qiContext).withText("Welcher Zeig interessiert dich am meisten?").withBodyLanguageOption(BodyLanguageOption.DISABLED).build()

        what = SayBuilder.with(qiContext).withText("Links").build()

        what2 = SayBuilder.with(qiContext).withText("Rechts").build()

        getIntoGetCoinPositionAnimation = AnimationBuilder.with(qiContext).withResources(R.raw.start_to_take_coin).build()

        getIntoGetCoinPosition = AnimateBuilder.with(qiContext).withAnimation(getIntoGetCoinPositionAnimation).build()

        holdCoinAnimation = AnimationBuilder.with(qiContext).withResources(R.raw.hold_coin).build()

        holdCoin = AnimateBuilder.with(qiContext).withAnimation(holdCoinAnimation).build()

        getIntoGiveCoinPositionAnimation = AnimationBuilder.with(qiContext).withResources(R.raw.hold_to_give).build()

        getIntoGiveCoinPosition = AnimateBuilder.with(qiContext).withAnimation(getIntoGiveCoinPositionAnimation).build()

        stayInGiveCoinPositionAnimation = AnimationBuilder.with(qiContext).withResources(R.raw.stay_in_give_coin_pos).build()

        stayInGiveCoinPosition = AnimateBuilder.with(qiContext).withAnimation(stayInGiveCoinPositionAnimation).build()

        getIntoTakeCoinPositionAnimation = AnimationBuilder.with(qiContext).withResources(R.raw.give_to_take_coin).build()

        //getIntoTakeCoinPosition
        getIntoTakeCoinPosition = AnimateBuilder.with(qiContext).withAnimation(getIntoTakeCoinPositionAnimation).build()

        stayInTakeCoinPositionAnimation = AnimationBuilder.with(qiContext).withResources(R.raw.stay_in_take_coin_pos).build()

        stayInTakeCoinPosition = AnimateBuilder.with(qiContext).withAnimation(stayInTakeCoinPositionAnimation).build()

        takeToHoldAnimation = AnimationBuilder.with(qiContext).withResources(R.raw.take_to_hold_coin).build()

        takeToHold = AnimateBuilder.with(qiContext).withAnimation(takeToHoldAnimation).build()

        giveToHoldAnimation = AnimationBuilder.with(qiContext).withResources(R.raw.give_to_hold).build()

        giveToHold = AnimateBuilder.with(qiContext).withAnimation(giveToHoldAnimation).build()

        waveAnimation = AnimationBuilder.with(qiContext).withResources(R.raw.wave).build()

        wave = AnimateBuilder.with(qiContext).withAnimation(waveAnimation).build()

        humanAwareness = qiContext?.humanAwareness

        sayState = SayBuilder.with(qiContext).withText(feedbackStates.toString()).withBodyLanguageOption(BodyLanguageOption.DISABLED).build()

        sayTest = SayBuilder.with(qiContext).withText("test").withBodyLanguageOption(BodyLanguageOption.DISABLED).build()

        touch = qiContext!!.touch
        touchSensor = touch.getSensor("Head/Touch")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSpeechBarDisplayStrategy(SpeechBarDisplayStrategy.IMMERSIVE)
        setContentView(R.layout.feedback_start)

        QiSDK.register(this, this)
    }

    override fun onRobotFocusGained(qiContext: QiContext?)
    {
        //this.qiContext = qiContext

        initializeVariables(qiContext)

        //getCoin()



        while (true) {
            //sayState.run()
            when (feedbackStates) {
                FeedbackStates.GOFROMGIVETOHOLDCOIN -> fromGiveToHoldCoin()
                //FeedbackStates.FINDHUMAN -> checkForHumans(qiContext)
                FeedbackStates.STAYINHOLDCOIN -> stayInHoldCoin(qiContext)
                //FeedbackStates.STAYINTAKECOIN -> stayInTakeCoin()
                FeedbackStates.STAYINGIVECOIN -> stayInGiveCoin()
                FeedbackStates.GOTOHOLDCOIN -> goToHoldCoin()
                FeedbackStates.ASKFEEDBACK -> askForFeedback()
                //FeedbackStates.GETCOIN -> getCoin()
                FeedbackStates.GIVECOIN -> giveCoin()
                else -> {}
            }
        }
            /*val touch: Touch = qiContext!!.touch
            //val sensorNames: List<String> = touch.sensorNames
            //Log.i(TAG, "sensors "+sensorNames.toString())
            val touch: Touch = qiContext!!.touch
            val touchSensor: TouchSensor = touch.getSensor("Head/Touch")
            touchSensor.addOnStateChangedListener { touchState ->
                timer.onFinish()
                if(touchState.touched && !onCoolDown){
                    what.run()
                    Log.i(TAG, "Kopf")
                    timer.start()
                    onCoolDown = true
                }
                //Log.i(TAG, "Sensor " + (if (touchState.touched ) "touched" else "released") + " at ${touchState.time}")
            }
            touchSensor2.addOnStateChangedListener { touchState ->
                if(touchState.touched){
                    what2.run()
                    Log.i(TAG, "Rechts")
                }
                //Log.i(TAG, "Sensor " + (if (touchState.touched ) "touched" else "released") + " at ${touchState.time}")
            }
            // sensorNames (on Pepper): ["Head/Touch", "LHand/Touch", "RHand/Touch", "Bumper/FrontLeft", "Bumper/FrontRight", "Bumper/Back"].*/
    }




    override fun onRobotFocusLost() {
        // Remove on started listeners from the Chat action.
    }

    override fun onRobotFocusRefused(reason: String?) {

    }

    override fun onDestroy() {
        QiSDK.unregister(this, this)
        super.onDestroy()
    }

    //region Functions

    //region UI_Functions


    @SuppressLint("MissingInflatedId")
    fun pressedcheck( view: View) {
        // buttons invis machen und danke für dein feedback ausgeben
        if (feedbackStates == FeedbackStates.STAYINHOLDCOIN) {
            //liked = true
            //checkCount++
            //findViewById<TextView>(R.id.textView6).text = getString(R.string.check_count)
            setContentView(R.layout.branch)
            askbranch.async().run()
        }
    }

    @SuppressLint("MissingInflatedId")
    fun pressedx( view: View) {
        // buttons invis machen und danke für dein feedback ausgeben
        if (feedbackStates == FeedbackStates.STAYINHOLDCOIN) {
            //liked = false

            //xCount++
            //findViewById<TextView>(R.id.textView7).text = getString(R.string.x_count)
            feedbackStates = FeedbackStates.GIVECOIN
            setContentView(R.layout.feedbackend)
            feedbackNo()
        }
    }

    @SuppressLint("MissingInflatedId")
    fun pressedStart( view: View) {
        setContentView(R.layout.signup)
        askForFeedbackBoolean = true
        startedFeedback = true
        isLookingForHumans = false
        feedbackStates = FeedbackStates.ASKFEEDBACK
    }

    @SuppressLint("MissingInflatedId")
    fun saveResults( view: View) {
        val result = arrayOf(0.0F, 0.0F, 0.0F, 0.0F)
        result[0] = findViewById<RatingBar>(R.id.ratingBarSound).rating
        result[1] = findViewById<RatingBar>(R.id.ratingBarPresentations).rating
        result[2] = findViewById<RatingBar>(R.id.ratingBarTour).rating
        result[3] = findViewById<RatingBar>(R.id.ratingBarIdk).rating
        //val f = Feedback(liked, result)
        //feedback.add(f)
        feedbackStates = FeedbackStates.GIVECOIN
        setContentView(R.layout.feedbackend)
    }


    /*fun receiveCoin(view: View) {
        isLookingForHumans = true
        feedbackStates = FeedbackStates.GOTOHOLDCOIN
    }*/

    /*fun giveCoin( view: View) {
        startedFeedback = false
        feedbackStates = FeedbackStates.GETCOIN
        runOnUiThread { setContentView(R.layout.feedback_start) }
    }*/

    //endregion
    //region Logic_Functions
    private fun askForFeedback() {
        // Pepper asks for feedback
        askForFeedback.run()
        feedbackStates = FeedbackStates.STAYINHOLDCOIN
    }

    /*private fun checkForHumans(qiContext: QiContext?)
    {
        val humansAroundFuture: Future<List<Human>>? = humanAwareness?.async()?.humansAround
        humansAroundFuture?.andThenConsume { humansAround ->
            Log.i(TAG, "${humansAround.size} human(s) around.")

            searchInterestedHuman(humansAround, qiContext)
        }

    }*/

    /*private fun searchInterestedHuman (humans: List<Human>,  qiContext: QiContext?) {
        var foundInterestedHuman = false
        humans.forEachIndexed { _, human ->
            val engagementIntentionState: EngagementIntentionState = human.engagementIntention
            val attentionState: AttentionState = human.attention
            if(attentionState == AttentionState.LOOKING_AT_ROBOT && engagementIntentionState == EngagementIntentionState.SEEKING_ENGAGEMENT)
            {
                foundInterestedHuman = true
            }
        }
        if(foundInterestedHuman)
        {
            askIfWantFeedback.run()
            //feedbackStates = FeedbackStates.STAYINHOLDCOIN
        }
    }*/

    /*private fun getCoin() {

        // Pepper gets coin

        sayTest.run()
        // Animation where Pepper reaches out for a coin (some individual has to give it to him irl)
        if(firstStart)
        {
            val t = Thread {
                sayTest.run()
                //start_to_take_coin

                getIntoGetCoinPosition.async()?.run()
                Thread.sleep(950)
                firstStart = false
                feedbackStates = FeedbackStates.STAYINTAKECOIN
            }

            t.run()

            // NO START NOT WORKING

            /*run {
                Thread {
                    sayTest.run()
                    getIntoGetCoinPosition.async()?.run()
                    Thread.sleep(2000)
                    firstStart = false
                    feedbackStates = FeedbackStates.STAYINTAKECOIN
                }
            }*/
        }
        else
        {
            val t = Thread {
                getIntoTakeCoinPosition.async()?.run()
                Thread.sleep(950)
                feedbackStates = FeedbackStates.STAYINTAKECOIN
            }

            t.run()
        }
    }*/


    /*private fun stayInTakeCoin() {
        val t = Thread{
            stayInTakeCoinPosition.async()?.run()
            Thread.sleep(950)
            //sayTest.run()
        }

        t.run()

        // das mit dem head sensor also sobald man etwas erkennt changed man state zu askfeedback

        touchSensor.addOnStateChangedListener { touchState ->
            if(touchState.touched){
                isLookingForHumans = true
                feedbackStates = FeedbackStates.GOTOHOLDCOIN
            }
        }

    }*/

    private fun giveCoin() {
        // Pepper thanks for feedback and gives the person the coin (animation wise, the person has to take it)7
        // runOnUiThread { findViewById<TextView>(R.id.textView5).visibility = View.VISIBLE }
        /*giveCoin.async()?.run()
        thanksForFeedback.run()
        feedbackStates = FeedbackStates.GETCOIN*/

        val t = Thread{
            thanksForFeedback.async()?.run()
            //getIntoGiveCoinPosition.async()?.run()
            Thread.sleep(2000)
            feedbackStates = FeedbackStates.STAYINGIVECOIN
        }

        t.run()
    }

    private fun stayInGiveCoin() {
        val t = Thread{
            //stayInGiveCoinPosition.async()?.run()
            Thread.sleep(930)
        }

        t.run()

        // das mit dem head sensor also sobald man etwas erkennt changed man state zu getcoin weil nach dem feedback
        // hat pepper keine coin mehr also muss er wieder nach einer suchen/fragen bzw. die animations zum coin getten
        // werden wieder ausgeführt und man muss pepper die coin geben
        touchSensor.addOnStateChangedListener { touchState ->
            if(touchState.touched){
                feedbackStates = FeedbackStates.GOFROMGIVETOHOLDCOIN
                runOnUiThread { setContentView(R.layout.feedback_start) }
            }
        }
    }

    private fun goToHoldCoin() {
        val t = Thread{
            // vielleicht bei der animation, dass pepper noch ungefähr 1 sek in der take coin position bleibt und erst danach seine hand schließt maybe
            // fixt das das problem

            //takeToHold.async()?.run()
            Thread.sleep(4380)
            feedbackStates = FeedbackStates.STAYINHOLDCOIN
        }

        t.run()
    }

    private fun fromGiveToHoldCoin() {
        val t = Thread{
            //giveToHold.async()?.run()
            Thread.sleep(2000)
            feedbackStates = FeedbackStates.STAYINHOLDCOIN
        }

        t.run()
    }

    private fun stayInHoldCoin(qiContext: QiContext?) {
        val t = Thread{
            //holdCoin.async()?.run()
            //if(!isLookingForHumans)
            //{
                Thread.sleep(930)
            //

        // }
            /*else
            {
                lookingForHumans(qiContext)
            }
            if(askForFeedbackBoolean)
            {
                askForFeedback.run()
                askForFeedbackBoolean = false
            }*/
        }

        t.run()
    }

    /*private fun lookingForHumans(qiContext: QiContext?) {
        // every 3 seconds, pepper checks if there are humans around looking at him
        // isLookingForHumans is false at the beginning so that stayInHoldCoin wont call this method every loop cycle, after checkForHumans and the 3 second sleep
        // isLookingForHumans is set to true so stayInHoldCoin can call this method again
        val t = Thread{
            isLookingForHumans = false
            if(!isLookingForHumans && !startedFeedback)
            {
                Thread.sleep(900)
                checkForHumans(qiContext)
                isLookingForHumans = true
            }
        }

        t.run()
    }*/

    private fun feedbackNo()
    {
        Feedback(false).writeToFile()
        //stayInHoldCoinThread.interrupt()
        //giveCoin()
        feedbackStates = FeedbackStates.GIVECOIN
        setContentView(R.layout.feedbackend)
    }
    fun feedbackInformatics(view: View)
    {
        Feedback(true, Branch.INFORMATIK).writeToFile()
        //stayInHoldCoinThread.interrupt()
        //giveCoin()
        feedbackStates = FeedbackStates.GIVECOIN
        setContentView(R.layout.feedbackend)
    }

    fun feedbackMediatechnology(view: View)
    {
        Feedback(true, Branch.MEDIENTECHNIK).writeToFile()
        //stayInHoldCoinThread.interrupt()
        //giveCoin()
        feedbackStates = FeedbackStates.GIVECOIN
        setContentView(R.layout.feedbackend)
    }

    fun feedbackElectronics(view: View)
    {
        Feedback(true, Branch.ELEKTRONIK).writeToFile()
        //stayInHoldCoinThread.interrupt()
        //giveCoin()
        feedbackStates = FeedbackStates.GIVECOIN
        setContentView(R.layout.feedbackend)
    }

    fun feedbackBiomedicine(view: View)
    {
        Feedback(true, Branch.BIOMEDIZIN).writeToFile()
        //stayInHoldCoinThread.interrupt()
        //giveCoin()
        feedbackStates = FeedbackStates.GIVECOIN
        setContentView(R.layout.feedbackend)
    }

    fun feedbackFachschule(view: View)
    {
        Feedback(true, Branch.FACHSCHULE).writeToFile()
        //stayInHoldCoinThread.interrupt()
        //giveCoin()
        feedbackStates = FeedbackStates.GIVECOIN
        setContentView(R.layout.feedbackend)
    }

    //endregion

    //endregion


}