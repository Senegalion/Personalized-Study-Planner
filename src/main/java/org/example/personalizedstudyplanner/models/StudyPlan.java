package org.example.personalizedstudyplanner.models;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

public class StudyPlan {
    private int studyPlanId;
    private int studentId;
    private OffsetDateTime creationDate;
    private final Map<String, StudyPlanTranslation> translations;

    public StudyPlan(int studyPlanId, int studentId, OffsetDateTime creationDate) {
        this.studyPlanId = studyPlanId;
        this.studentId = studentId;
        this.creationDate = creationDate;
        this.translations = new HashMap<>();
    }

    public int getStudyPlanId() {
        return studyPlanId;
    }

    public void setStudyPlanId(int studyPlanId) {
        this.studyPlanId = studyPlanId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public OffsetDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(OffsetDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void addTranslation(String languageCode, String title, String description) {
        StudyPlanTranslation translation = new StudyPlanTranslation(languageCode, title, description);
        translation.setStudyPlanId(this.studyPlanId);
        translations.put(languageCode, translation);
    }

    public Map<String, StudyPlanTranslation> getTranslations() {
        return translations;
    }

    public void setTranslations(Map<String, StudyPlanTranslation> translations) {
        this.translations.clear();
        this.translations.putAll(translations);
    }
}
