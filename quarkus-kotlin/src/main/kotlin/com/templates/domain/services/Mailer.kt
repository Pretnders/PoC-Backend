package com.templates.domain.services

import com.azure.communication.email.EmailClientBuilder
import com.azure.communication.email.models.EmailAddress
import com.azure.communication.email.models.EmailMessage
import com.azure.communication.email.models.EmailSendResult
import com.azure.core.util.polling.PollResponse
import com.azure.core.util.polling.SyncPoller
import com.templates.domain.errors.ApplicationException
import com.templates.domain.errors.ApplicationExceptionsEnum
import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.config.inject.ConfigProperty


@ApplicationScoped
class Mailer {
    @field:ConfigProperty(name = "azure.comm-service.endpoint")
    private lateinit var azureCommunicationEndpoint: String
    @field:ConfigProperty(name = "azure.comm-service.mailer.sender.do.not.reply")
    private lateinit var azureDoNotReplySender: String

    fun sendHtmlEmail(recipient: String, subject:String, content: String) {
        val emailClient = EmailClientBuilder().connectionString(azureCommunicationEndpoint).buildClient()
        val toAddress = EmailAddress(recipient)

        val emailMessage: EmailMessage = EmailMessage()
            .setSenderAddress(azureDoNotReplySender)
            .setToRecipients(toAddress)
            .setSubject(subject)
            .setBodyPlainText("Hello World par e-mail.")
            .setBodyHtml(
                content.trimIndent()
            )

        val poller: SyncPoller<EmailSendResult, EmailSendResult> = emailClient.beginSend(emailMessage, null)
        val result: PollResponse<EmailSendResult> = poller.waitForCompletion()
        if(result.value.status.value != "Succeeded"){
            throw ApplicationException(ApplicationExceptionsEnum.EMAIL_DELIVERY_FAILED)
        }
    }

    fun generateOtpEmail(userName: String, verificationCode: String): String {
        return "<div style='font-family: Helvetica,Arial,sans-serif;min-width:1000px;overflow:auto;line-height:2'>\n" +
                "  <div style='margin:50px auto;width:70%;padding:20px 0'>\n" +
                "    <p style='font-size:1.1em'>Hi " + userName + ",</p>\n" +
                "    <p>Thank you for joining Us. Use the following OTP to complete your Sign Up procedures. OTP " +
                "is valid for 20 minutes</p>\n" +
                "    <h2 style='background: #00466a;margin: 0 auto;width: max-content;padding: 0 10px;color: " +
                "#fff;border-radius: 4px;'>" + verificationCode +"</h2>\n" +
                "    <p style='font-size:0.9em'>Regards,<br />Us</p>\n" +
                "    <hr style='border:none;border-top:1px solid #eee' />\n" +
                "    <div style='float:right;padding:8px 0;color:#aaa;font-size:0.8em;line-height:1;font-weight:300'>\n" +
                "      <p>Us</p>\n" +
                "      <p>Nation</p>\n" +
                "      <p>Paris, France</p>\n" +
                "    </div>\n" +
                "  </div>\n" +
                "</div>"
    }
    fun generatePasswordRecoveryEmail(token:String): String {
        val url = String.format("http://localhost:5173/reset-password/%s",token)
        return "<div style='font-family: Helvetica,Arial,sans-serif;min-width:1000px;overflow:auto;line-height:2'>\n" +
                "  <div style='margin:50px auto;width:70%;padding:20px 0'>\n" +
                "    <p>A request has been sent to recover your password. Use the following link to reset your " +
                "password</p>\n" +
                "    <p><a href='$url' target='_blank'>Réinitialiser mon mot de passe</a></p>\n" +
                "    </div>\n" +
                "  </div>\n" +
                "</div>"
    }

    fun newOtpEmail(verificationCode: String): String {
        return "<div style='font-family: Helvetica,Arial,sans-serif;min-width:1000px;overflow:auto;line-height:2'>\n" +
                "  <div style='margin:50px auto;width:70%;padding:20px 0'>\n" +
                "    <p style='font-size:1.1em'>Hi,</p>\n" +
                "    <p>Here is the updated OTP to complete your Sign Up procedures. OTP " +
                "is valid for 20 minutes</p>\n" +
                "    <h2 style='background: #00466a;margin: 0 auto;width: max-content;padding: 0 10px;color: " +
                "#fff;border-radius: 4px;'>" + verificationCode +"</h2>\n" +
                "    <p style='font-size:0.9em'>Regards,<br />Us</p>\n" +
                "    <hr style='border:none;border-top:1px solid #eee' />\n" +
                "    <div style='float:right;padding:8px 0;color:#aaa;font-size:0.8em;line-height:1;font-weight:300'>\n" +
                "      <p>Us</p>\n" +
                "      <p>Nation</p>\n" +
                "      <p>Paris, France</p>\n" +
                "    </div>\n" +
                "  </div>\n" +
                "</div>"
    }
}