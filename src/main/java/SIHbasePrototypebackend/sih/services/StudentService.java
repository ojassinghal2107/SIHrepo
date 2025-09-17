package SIHbasePrototypebackend.sih.services;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SIHbasePrototypebackend.sih.model.Student;
import SIHbasePrototypebackend.sih.repo.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    // Save a single student
    public void saveStudent(Student student) {
    studentRepository.save(student);
}

    

    // Save a batch of students
    public void saveAll(List<Student> students) {
        studentRepository.saveAll(students);
    }

    // Retrieve all students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Retrieve a student by ID
    public Optional<Student> getStudentById(String studentId) {
        return studentRepository.findById(studentId);
    }

    // Delete a student (optional)
    public void deleteStudent(String studentId) {
        studentRepository.deleteById(studentId);
    }

    // Filter students by attendance
    public List<Student> getLowAttendanceStudents(int threshold) {
        return studentRepository.findByAttendanceLessThan(threshold);
    }

    // Filter students with unpaid fees
    public List<Student> getFeeDefaulters() {
        return studentRepository.findByFeePaidFalse();
    }
}