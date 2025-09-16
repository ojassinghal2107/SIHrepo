package SIHbasePrototypebackend.sih.repo;




import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import SIHbasePrototypebackend.sih.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

    // Optional: Custom query methods

    // Find students with attendance below a threshold
    List<Student> findByAttendanceLessThan(int threshold);

    // Find students who haven't paid fees
    List<Student> findByFeePaidFalse();

    // Find students by attempts
    List<Student> findByAttemptsGreaterThan(int count);
}