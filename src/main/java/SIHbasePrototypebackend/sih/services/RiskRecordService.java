package SIHbasePrototypebackend.sih.services;

   
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SIHbasePrototypebackend.sih.model.RiskRecord;
import SIHbasePrototypebackend.sih.repo.RiskRecordRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RiskRecordService {

    @Autowired
    private RiskRecordRepository riskRecordRepository;

    // Save a batch of risk records
    public void saveAll(List<RiskRecord> riskRecords) {
        riskRecordRepository.saveAll(riskRecords);
    }

    // Save a single risk record
    public void save(RiskRecord riskRecord) {
        riskRecordRepository.save(riskRecord);
    }

    // Get latest risk record for a student
    public RiskRecord getLatestRisk(String studentId) {
        return riskRecordRepository.findTopByStudentIdOrderByTimestampDesc(studentId);
    }

    // Get all risk records for a student
    public List<RiskRecord> getRiskHistory(String studentId) {
        return riskRecordRepository.findByStudentId(studentId);
    }

    // Get all students above a risk threshold
    public List<RiskRecord> getHighRiskStudents(double threshold) {
        return riskRecordRepository.findByRiskScoreGreaterThan(threshold);
    }

    // Get records evaluated within a time window
    public List<RiskRecord> getRecordsBetween(LocalDateTime start, LocalDateTime end) {
        return riskRecordRepository.findByTimestampBetween(start, end);
    }
}