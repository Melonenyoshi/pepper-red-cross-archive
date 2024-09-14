## Getting started

In order to first start developing the android app, you need to get android studio and install the pepper SDK. However, newer versions of android studio dont work with PepperSDK, so you need to make sure you have the right version. The version Dolphin 2021.3.1.2021 worked for me. You can get this version via Jetbrains Toolbox by clicking on the three dots next to android studio. Alternatively you can also download it from the website.

Now we need to install PepperSDK. The Plugin cannot be found within the android studio plugin search anymore. Therefore we need to download it manually. For that you need to go to https://plugins.jetbrains.com/plugin/8354-pepper-sdk and download the zip. Then in android studio you go to File -> Settings -> Plugins and then click on the settings button next to the installed tab and press install from disk. Then you select the zip for PepperSDK.

Also make sure you install the required android SDKs. In order to do that, you go to Settings -> System Settings -> Android SDK. There you can select the desired SDKs the ones you need are:

* Android 13.0 (Tiramisu)
* Android 12L (Sv2)
* Android 12.0 (S)
* Android 11.0 (R)
* Android 7.0 (Nougat)
* Android 6.0 (Marshmallow)

Once you have installed everything you need to get the IP Address of the Pepper Robot. This can be done by either shortly pressing the on/off switch on pepper or by looking at it in the pulldown menu. In the pulldown menu you need to use the IP for the Robot Browser. Then you go to Tools -> PepperSDK -> Connect and enter The IP Address. If it is your first time connecting to the robot there will be a pop-up on pepper which you need to accept. It will then take a while and you should be able to connect to pepper. Note however, that only one person can be connected to pepper at a time.if you are connected, you can then press the run button which will deploy the app on pepper. This will however take some time.

## Emulator

Instead of deploying the app on pepper which can take a very long time, you can use the pepper emulator. The emulator only runs on linux however and you need to enter some commands in order for it to work:
```
cd "/home/$USER/.local/share/Softbank Robotics/RobotSDK/API 7/tools/lib/"
sudo mv libz.so.1 libz.so.1.old
sudo ln -s /lib/x86_64-linux-gnu/libz.so.1
```
If you enter those commands the emulator should work flawlessly. You can start it by going to Tools -> PepperSDK -> Emulator.

## Creating Animations

In order to create a new animation, you go to res -> raw, right click the folder and create a new animation timeline. In order to edit it now, right click the new file and press edit animation. A window should open where you can edit the animation. on the left you should see a timeline of all of the animation nodes. on the right you should have a model of pepper. On the model you can move all of peppers limbs individually. The way you create animations is by building a creation point in the animation and then marking it on the timeline. This can be done with the buttons resembling a human shape at the top. Pepper will now move all of its limbs to the desired position in the timeline. The speed of the movements depends on how far the point is away from the previous state. in order to use the animation you need to first create an animation object using the file we created:
```
val animation: Animation? =
        AnimationBuilder.with(qiContext).withResources(R.raw.{File Name}).build()
```
Then we also need to create an animate object:

private var animate: Animate? = null
```
animate = AnimateBuilder.with(qiContext)
        .withAnimation(animation)
        .build()
```
Finally we need to run the animation:
```
val animateFuture: Future<Void>? = animate?.async()?.run()
```
## Playing audio files

Pepper can play most audio files like .mp3 or .wav. In order to play them, you first move the audio file into the res -> raw folder. Then you can create a media player in the program and use it to play the file.
```
private lateinit var mp: MediaPlayer

mp = MediaPlayer.create(this, R.raw.{File Name})
```
Then you can simply run it using
```
mp.start()
```
## Having pepper talk

Instead of playing audio files, Pepper can also use a text to speech function which allows them to talk in different languages. In order to do that you need to create a say. 

var sayQuestionGerman: Say? = null
sayQuestionGerman = SayBuilder.with(qiContext).withText({string}).build()

If you want to use a specific language you need to specify it here, otherwise pepper will use its system language:
```	
private val eng: Language = Language.ENGLISH
        private val regeng: Region = Region.UNITED_STATES

sayQuestionEnglish =
        SayBuilder.with(qiContext).withText({string}).withLocale(Locale(eng, regeng)).build()
```
After creating it you can simply run it and Pepper will say the phrase
```
sayQuestionGerman.async().run()
```
## Creating frontend pages (xml)

Pepper uses xml for its frontend technology. It is also possible to use jetpack compose for the frontend. However this will come with some threading issues, therefore this part will focus on creating pages with xml.

New xml files should be created in the res -> layout folder. The files can be edited via text or via a scene builder. If you are using the scene builder, make sure to set the right resolution (Pepper 1.9 API 33). This can be changed in a dropdown menu above the page. Once created you need to select the page at the beginning of your kotlin program via the setContentView Method:

```
setContentView(R.layout.activity_main)
```

You can use the same method to switch between pages later in the program.

## Creating language specific strings

Language specific strings are stored in res -> values -> strings. there is a strings.xml file for each language. The names of the strings in the two files have to be equal. You can use the strings by accessing the file:
```
@string/name
```
When the language is changed the string automatically switches. Here is an example of a method that switches the language to english:

```
 private fun changeLanguageToEnglish(view: View) {
        val config: Configuration = resources.configuration
        config.locale = Locale("en")
        isGerman = false
        resources.updateConfiguration(config, resources.displayMetrics)
        setContentView(R.layout.activity_main)
        getQuote()
}
```

## Use peppers human awareness

Using peppers human awareness feature we can, for example, play an animation when pepper looks at a different person. To do that we need to add a listener in the onFocusGained method. 

```
val humanAwareness: HumanAwareness = qiContext.humanAwareness
	
humanAwareness.addOnEngagedHumanChangedListener { human ->
        human?.let {
                animate = AnimateBuilder.with(qiContext).withAnimation(animation).build()
                val animateFuture: Future<Void>? = animate?.async()?.run()
        }
}
```

This code for example will play an animation whenever pepper looks at a different person.
