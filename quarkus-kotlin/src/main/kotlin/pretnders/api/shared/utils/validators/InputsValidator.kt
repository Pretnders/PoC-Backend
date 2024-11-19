package pretnders.api.shared.utils.validators

import pretnders.api.shared.errors.ApplicationException
import pretnders.api.shared.errors.ApplicationExceptionsEnum
import pretnders.api.shared.utils.validators.PasswordUtils.verifyPassword
import java.sql.Timestamp
import kotlin.math.abs

object InputsValidator {
    fun validatePasswordConfirmation(inputPassword: String, confirmPassword: String) {
        if(inputPassword != confirmPassword){
            throw ApplicationException(ApplicationExceptionsEnum.PASSWORDS_NO_MATCH)
        }
    }
    fun validatePasswordFormat(inputPassword: String) {
        if(!inputPassword.matches(Regex("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!?\\\$_])[A-Za-z\\d!?\\\$_]{8,}\$"))){
            throw ApplicationException(ApplicationExceptionsEnum.USER_INVALID_PASSWORD)
        }
    }

    fun validatePhoneNumberFormat(phoneNumber: String) {
        if(!phoneNumber.matches(Regex("^0[67]\\d{8}$"))){
            throw ApplicationException(ApplicationExceptionsEnum.CREATE_USER_INVALID_PHONE_NUMBER)
        }
    }

    fun validatePasswordHash(token:String, hashedToken:String) {
        if(!verifyPassword(token, hashedToken)){
            throw ApplicationException(ApplicationExceptionsEnum.INVALID_TOKEN)
        }
    }

    fun hasTimestampExceededTwentyMinutes(timestamp1: Timestamp, timestamp2: Timestamp) {
        val millisecondsDifference = abs(timestamp2.time - timestamp1.time)
        if( millisecondsDifference > 1200000){
            throw ApplicationException(ApplicationExceptionsEnum.OTP_TIMESTAMP_EXCEEDED)

        }
    }

    fun validateHeight(height: String) {
        if(!height.matches(Regex("^[1|2]{1}[0-9]{2}\$"))){
            throw ApplicationException(ApplicationExceptionsEnum.INVALID_HEIGHT_FORMAT)
        }
        val toInt = height.toInt()
        if(toInt < 120 || toInt > 250){
            throw ApplicationException(ApplicationExceptionsEnum.INVALID_HEIGHT_VALUE)
        }
    }

    fun validateScore(score: Short) {
        if(score < 0 || score > 100){
            throw ApplicationException(ApplicationExceptionsEnum.INVALID_TRAIT_PAIR_SCORE_VALUE)
        }
    }

}