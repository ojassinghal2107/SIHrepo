package SIHbasePrototypebackend.sih.services;



import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import SIHbasePrototypebackend.sih.model.RiskRecord;
import SIHbasePrototypebackend.sih.model.Student;

import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

@Service
public class MLService {

    private final RestTemplate restTemplate;

    public MLService() {
        this.restTemplate = new RestTemplate();
    }

    // Evaluate a single student
    public RiskRecord evaluate(Student student) {
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Student> request = new HttpEntity<>(student, headers);
        ResponseEntity<RiskRecord> response = restTemplate.postForEntity(
            "http://localhost:8000/predict", request, RiskRecord.class);

        return response.getBody();
    }

    // Evaluate a batch of students
    public List<RiskRecord> evaluateBulk(List<Student> students) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<Student>> request = new HttpEntity<>(students, headers);
        ResponseEntity<RiskRecord[]> response = restTemplate.postForEntity(
            "http://localhost:8000/predict/bulk", request, RiskRecord[].class);

        return Arrays.asList(response.getBody());
    }
}