package SIHbasePrototypebackend.sih.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "risk_records")
public class RiskRecord {


@Id
private String studentId;

@Column(nullable = false)
private String riskLevel;

@Column(nullable = false)
private double riskScore;


    @Column(nullable = false, updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    private LocalDateTime timestamp;


    public RiskRecord() {}

    public RiskRecord(String studentId, String riskLevel, double riskScore, LocalDateTime timestamp) {
        this.studentId = studentId;
        this.riskLevel = riskLevel;
        this.riskScore = riskScore;
        this.timestamp = timestamp;
    }

    // Getters and setters
    

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
