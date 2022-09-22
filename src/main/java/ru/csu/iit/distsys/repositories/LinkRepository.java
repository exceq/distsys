package ru.csu.iit.distsys.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.csu.iit.distsys.domain.Link;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {
}
