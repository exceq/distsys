package ru.csu.iit.distsys.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.csu.iit.distsys.domain.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
