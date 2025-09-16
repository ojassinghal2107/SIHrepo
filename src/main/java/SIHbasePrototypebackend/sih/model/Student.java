package SIHbasePrototypebackend.sih.model;


import java.util.Arrays;

import jakarta.persistence.*;


@Entity
@Table(name = "students")
public class Student {

    @Id
    private String studentId;

    private int attendance;
    private String phoneNumber;

    private boolean feePaid;

    private int score1;
    private int score2;
    private int score3;

    private int attempts;

    public Student() {}

    public Student(String studentId, int attendance, boolean feePaid,
               int score1, int score2, int score3, int attempts, String guardianPhone) {
    this.studentId = studentId; 
    this.attendance = attendance;
    this.feePaid = feePaid;
    this.score1 = score1;
    this.score2 = score2;
    this.score3 = score3;
    this.attempts = attempts;
    this.phoneNumber = guardianPhone;
}


    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    public boolean isFeePaid() {
        return feePaid;
    }

    public void setFeePaid(boolean feePaid) {
        this.feePaid = feePaid;
    }

    public int getScore1() {
        return score1;
    }
    public void setGuardianPhone(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }

    public int getScore2() {
        return score2;
    }

    public void setScore2(int score2) {
        this.score2 = score2;
    }

    public int getScore3() {
        return score3;
    }

    public void setScore3(int score3) {
        this.score3 = score3;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    
    public int[] getScoreArray() {
        return new int[] { score1, score2, score3 };
    }
    public String getGuardianPhone() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", attendance=" + attendance +
                ", feePaid=" + feePaid +
                ", scores=" + Arrays.toString(getScoreArray()) +
                ", attempts=" + attempts +
                '}';
    }
}