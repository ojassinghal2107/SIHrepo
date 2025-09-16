package SIHbasePrototypebackend.sih.controllers;

import SIHbasePrototypebackend.sih.model.Student;
import SIHbasePrototypebackend.sih.model.RiskRecord;
import SIHbasePrototypebackend.sih.services.StudentService;
import SIHbasePrototypebackend.sih.services.RiskRecordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminDashboardController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private RiskRecordService riskRecordService;

    // 1. Get all students
    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    // 2. Get student by ID
    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable String id) {
        return studentService.getStudentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. Get students with low attendance
    @GetMapping("/students/low-attendance")
    public ResponseEntity<List<Student>> getLowAttendance(@RequestParam int threshold) {
        return ResponseEntity.ok(studentService.getLowAttendanceStudents(threshold));
    }

    // 4. Get fee defaulters
    @GetMapping("/students/fee-defaulters")
    public ResponseEntity<List<Student>> getFeeDefaulters() {
        return ResponseEntity.ok(studentService.getFeeDefaulters());
    }

    // 5. Get latest risk score for a student
    @GetMapping("/students/{id}/risk")
    public ResponseEntity<RiskRecord> getLatestRisk(@PathVariable String id) {
        RiskRecord record = riskRecordService.getLatestRisk(id);
        return record != null ? ResponseEntity.ok(record) : ResponseEntity.notFound().build();
    }

    // 6. Get full risk history for a student
    @GetMapping("/students/{id}/risk-history")
    public ResponseEntity<List<RiskRecord>> getRiskHistory(@PathVariable String id) {
        return ResponseEntity.ok(riskRecordService.getRiskHistory(id));
    }

    // 7. Get students above a risk threshold
    @GetMapping("/students/high-risk")
    public ResponseEntity<List<RiskRecord>> getHighRiskStudents(@RequestParam double threshold) {
        return ResponseEntity.ok(riskRecordService.getHighRiskStudents(threshold));
    }

    // 8. Get risk records evaluated in a time window
    @GetMapping("/risk-records")
    public ResponseEntity<List<RiskRecord>> getRiskRecordsBetween(
            @RequestParam String start,
            @RequestParam String end
    ) {
        LocalDateTime startTime = LocalDateTime.parse(start);
        LocalDateTime endTime = LocalDateTime.parse(end);
        return ResponseEntity.ok(riskRecordService.getRecordsBetween(startTime, endTime));
    }}
