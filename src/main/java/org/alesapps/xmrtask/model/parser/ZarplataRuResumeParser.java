package org.alesapps.xmrtask.model.parser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.alesapps.xmrtask.model.Resume;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Anatoliy Kozhayev on 03.09.2017.
 */
public class ZarplataRuResumeParser implements ResumeParser {

    private static final Logger LOG = getLogger(ZarplataRuResumeParser.class);

    private static final String RESUME_API_URL = "http://ekb.zarplata.ru/api/v1/resumes/?geo_id=994&limit=100&offset=0";
    private static final String RESUME_JSON_ARRAY_NAME = "resumes";

    public List<Resume> parse() {
        Map<String, Object> jsonDocument;
        try {
            jsonDocument = new ObjectMapper().readValue(
                    new URL(RESUME_API_URL),
                    new TypeReference<Map<String, Object>>() {
                    });
        } catch (IOException e) {
            LOG.debug("Get Resumes Error: {}", e.getMessage());
            return Collections.emptyList();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return objectMapper.convertValue(jsonDocument.get(RESUME_JSON_ARRAY_NAME), new TypeReference<List<Resume>>() {
        });
    }
}
