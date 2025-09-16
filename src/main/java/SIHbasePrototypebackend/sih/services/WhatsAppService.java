package SIHbasePrototypebackend.sih.services;
import SIHbasePrototypebackend.sih.model.*;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WhatsAppService {

    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    @Value("${twilio.whatsappFrom}")
    private String fromNumber; // e.g. "whatsapp:+14155238886"

    public void sendAlertToGuardian(Student student, RiskRecord riskRecord) {
        Twilio.init(accountSid, authToken);

        String guardianNumber = "whatsapp:" + student.getGuardianPhone(); // e.g. "+91XXXXXXXXXX"
        String message = String.format(
            "⚠️ Alert: Your ward %s has a dropout risk level of %s (%.2f).\nPlease consult the mentor immediately.",
            student.getStudentId(), riskRecord.getRiskLevel(), riskRecord.getRiskScore()
        );

        Message.creator(
            new PhoneNumber(guardianNumber),
            new PhoneNumber(fromNumber),
            message
        ).create();

        System.out.println("📤 WhatsApp alert sent to guardian of " + student.getStudentId());
    }
}