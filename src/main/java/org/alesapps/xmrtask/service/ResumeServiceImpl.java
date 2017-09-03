package org.alesapps.xmrtask.service;

import org.alesapps.xmrtask.model.Resume;
import org.alesapps.xmrtask.model.parser.ResumeParser;
import org.alesapps.xmrtask.repository.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Anatoliy Kozhayev on 03.09.2017.
 */
@Service
public class ResumeServiceImpl implements ResumeService {

    private ResumeParser resumeParser;
    private ResumeRepository repository;

    @Autowired
    public ResumeServiceImpl(ResumeParser resumeParser, ResumeRepository repository) {
        this.resumeParser = resumeParser;
        this.repository = repository;
    }

    @Override
    public void parse() {
        repository.deleteAll();
        List<Resume> resumes = resumeParser.parse();
        if (resumes != null) repository.save(resumes);
    }

    @Override
    public List<Resume> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Resume> findByHeaderContainingIgnoreCase(String header) {
        return repository.findByHeaderContainingIgnoreCase(header);
    }
}
