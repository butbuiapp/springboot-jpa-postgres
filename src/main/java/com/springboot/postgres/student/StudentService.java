package com.springboot.postgres.student;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService {
	
	private final StudentRepository studentRepository;
	
	@Autowired
	public StudentService(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}
	
	public List<Student> getStudents() {
		return studentRepository.findAll();
	}
	
	public void addNewStudent(Student student) {
		Optional<Student> studentOptional = studentRepository.
				findStudentByEmail(student.getEmail());
		if (studentOptional.isPresent()) {
			throw new IllegalStateException("Email was registered.");
		} else {
			studentRepository.save(student);
		}
	}
	
	public void deleteStudent(Long id) {
		boolean exists = studentRepository.existsById(id.intValue());
		if (!exists) {
			throw new IllegalStateException(
					"Student with id " + id + " does not exist.");
		}
		studentRepository.deleteById(id.intValue());
	}

	@Transactional
	public void updateStudent(Long id, String name, String email) {
		Student student = studentRepository.findById(id.intValue())
							.orElseThrow(() -> new IllegalStateException(
									"Student with id " + id + " does not exist."));
		if (name != null && 
				name.length() > 0 &&
				!Objects.equals(student.getName(), name)) {
			student.setName(name);
		}
		
		if (email != null &&
				email.length()>0 &&
				!Objects.equals(student.getEmail(), email)) {
			Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
			if (studentOptional.isPresent()) {
				throw new IllegalStateException("Student with email " + email + " was registered.");
			}
			student.setEmail(email);
		}
		
	}
}
