package org.alesapps.xmrtask.repository;

import org.alesapps.xmrtask.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Anatoliy Kozhayev on 03.09.2017.
 */
@Transactional
public interface ResumeRepository extends JpaRepository<Resume, Long> {

    List<Resume> findByHeaderContainingIgnoreCase(String header);
}
