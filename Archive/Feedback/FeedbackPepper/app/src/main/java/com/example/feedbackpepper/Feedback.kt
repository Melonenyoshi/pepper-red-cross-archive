@file:Suppress("SpellCheckingInspection")

package com.example.feedbackpepper

class Feedback(private val wants_toJoin: Boolean, private val branch: Branch = Branch.NOJOIN) {

    fun writeToFile() {

        /*val t = Thread{
            val dir = Environment.DIRECTORY_DOCUMENTS
            val fileName = "feedback.csv"
            val file = File(dir, fileName)
            if(!file.exists())
            {
                file.createNewFile()
                file.writeText("wants_toJoin; branch \n")
            }
            file.appendText("${wants_toJoin}; ${branch}\n")
        }*/

    }


}

enum class Branch {
    NOJOIN, INFORMATIK, MEDIENTECHNIK, ELEKTRONIK, BIOMEDIZIN, FACHSCHULE
}


