package SIHbasePrototypebackend.sih.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "risk_records")
public class RiskRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentId;

    private String riskLevel; // e.g., "Low", "Medium", "High"

    private double riskScore; // e.g., 0.82

    private LocalDateTime timestamp;

    public RiskRecord() {}

    public RiskRecord(String studentId, String riskLevel, double riskScore, LocalDateTime timestamp) {
        this.studentId = studentId;
        this.riskLevel = riskLevel;
        this.riskScore = riskScore;
        this.timestamp = timestamp;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public double getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(double riskScore) {
        this.riskScore = riskScore;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "RiskRecord{" +
                "studentId='" + studentId + '\'' +
                ", riskLevel='" + riskLevel + '\'' +
                ", riskScore=" + riskScore +
                ", timestamp=" + timestamp +
                '}';
    }
}
