package org.alesapps.xmrtask.service;

import org.alesapps.xmrtask.model.Resume;

import java.util.List;

/**
 * Created by Anatoliy Kozhayev on 03.09.2017.
 */
public interface ResumeService {

    void parse();

    List<Resume> findAll();

    List<Resume> findByHeaderContainingIgnoreCase(String header);
}
