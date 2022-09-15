package ru.csu.iit.distsys.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.csu.iit.distsys.controllers.commands.StudentCommand;
import ru.csu.iit.distsys.domain.Student;
import ru.csu.iit.distsys.repositories.StudentRepository;

import java.util.Optional;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudentController {
    // Вообще лучше из контроллера обращаться к Service,
    // а тот, в свою очередь, обращается к Repository
    private final StudentRepository studentRepository;


    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent())
            return ResponseEntity.ok(student.get());
        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody StudentCommand studentCommand) {
        Student student = new Student(null,
                studentCommand.getName(),
                studentCommand.getCourse(),
                studentCommand.getGroupName());
        studentRepository.save(student);

        return ResponseEntity.ok(student);
    }
}
