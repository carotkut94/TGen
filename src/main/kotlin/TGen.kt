import dev.turingcomplete.kotlinonetimepassword.HmacAlgorithm
import dev.turingcomplete.kotlinonetimepassword.TimeBasedOneTimePasswordConfig
import dev.turingcomplete.kotlinonetimepassword.TimeBasedOneTimePasswordGenerator
import java.util.*
import java.util.concurrent.TimeUnit

fun main() {
    val token = "QDWSM3OYBPGTEVSPB5FKVDM3CSNCWHVK"
    val token2 = "K6IPBHCQTVLCZDM2"
    val config = TimeBasedOneTimePasswordConfig(codeDigits = 6,
        hmacAlgorithm = HmacAlgorithm.SHA1,
        timeStep = 30,
        timeStepUnit = TimeUnit.SECONDS)

    val config2 = TimeBasedOneTimePasswordConfig(codeDigits = 6,
        hmacAlgorithm = HmacAlgorithm.SHA1,
        timeStep = 30,
        timeStepUnit = TimeUnit.SECONDS)

    val timeBasedOneTimePasswordGenerator = TimeBasedOneTimePasswordGenerator(token.toByteArray(), config)
    val timeBasedOneTimePasswordGenerator2 = TimeBasedOneTimePasswordGenerator(token2.toByteArray(), config2)
    Timer().schedule(object:TimerTask(){
        override fun run() {
            val time = System.currentTimeMillis()
            val otp = timeBasedOneTimePasswordGenerator.generate(time)
            val counter = timeBasedOneTimePasswordGenerator.counter(time)


            val endEpochMillis = timeBasedOneTimePasswordGenerator.timeslotStart(counter+1)-1
            val millisValid = endEpochMillis - time

            println("Valid for : ${millisValid/1000} and OTP : $otp")

            val time2 = System.currentTimeMillis()
            val otp2 = timeBasedOneTimePasswordGenerator2.generate(time2)
            val counter2 = timeBasedOneTimePasswordGenerator2.counter(time2)


            val endEpochMillis2 = timeBasedOneTimePasswordGenerator2.timeslotStart(counter2+1)-1
            val millisValid2 = endEpochMillis2 - time2

            println("OTP 2 Valid for : ${millisValid2/1000} and OTP : $otp2")

        }
    }, 0,1000)
}
