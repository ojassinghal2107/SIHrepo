package SIHbasePrototypebackend.sih.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import SIHbasePrototypebackend.sih.model.RiskRecord;

import java.util.List;

@Repository
public interface RiskRecordRepository extends JpaRepository<RiskRecord, Long> {

    // Get all risk records for a specific student
    List<RiskRecord> findByStudentId(String studentId);

    // Get latest risk record for a student (assuming timestamp is present)
    RiskRecord findTopByStudentIdOrderByTimestampDesc(String studentId);

    // Get all students above a risk threshold
    List<RiskRecord> findByRiskScoreGreaterThan(double threshold);

    // Get all records evaluated on a specific date
    List<RiskRecord> findByTimestampBetween(java.time.LocalDateTime start, java.time.LocalDateTime end);
}