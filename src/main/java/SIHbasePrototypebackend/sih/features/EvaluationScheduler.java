package SIHbasePrototypebackend.sih.features;
import SIHbasePrototypebackend.sih.model.RiskRecord;
import SIHbasePrototypebackend.sih.model.Student;           
import SIHbasePrototypebackend.sih.services.MLService;
import SIHbasePrototypebackend.sih.services.RiskRecordService;  
import SIHbasePrototypebackend.sih.services.StudentService;
import SIHbasePrototypebackend.sih.services.WhatsAppService;    
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EvaluationScheduler {

    @Autowired
    private StudentService studentService;

    @Autowired
    private MLService mlService;


    @Autowired
    private RiskRecordService riskRecordService;

    @Autowired
    private WhatsAppService whatsappService;

    // Runs every day at 2 AM IST
    @Scheduled(cron = "0 0 2 * * *", zone = "Asia/Kolkata")
    public void evaluateAllStudents() {
        List<Student> students = studentService.getAllStudents();
        List<RiskRecord> risks = mlService.evaluateBulk(students);

        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            RiskRecord risk = risks.get(i);

            if (risk != null) {
                risk.setStudentId(student.getStudentId());
                riskRecordService.save(risk);

                // Trigger WhatsApp alert if risk exceeds threshold
                if (risk.getRiskScore() > 0.8) {
                String message = "⚠️ High dropout risk detected for student " + student.getStudentId();
                whatsappService.sendMessage(student.getGuardianPhone(), message);
            }

            }
        }

        System.out.println("✅ Daily risk evaluation completed: " + risks.size() + " students processed.");
    }
}