package org.example.personalizedstudyplanner.models;

public class StudyPlanTranslation {
    private int studyPlanTranslationId;
    private int studyPlanId;
    private String languageCode;
    private String title;
    private String description;

    public StudyPlanTranslation(int studyPlanTranslationId, int studyPlanId, String languageCode, String title, String description) {
        this.studyPlanTranslationId = studyPlanTranslationId;
        this.studyPlanId = studyPlanId;
        this.languageCode = languageCode;
        this.title = title;
        this.description = description;
    }

    public StudyPlanTranslation(String languageCode, String title, String description) {
        this.languageCode = languageCode;
        this.title = title;
        this.description = description;
    }

    public StudyPlanTranslation(int studyPlanId, String languageCode, String title, String description) {
        this.studyPlanId = studyPlanId;
        this.languageCode = languageCode;
        this.title = title;
        this.description = description;
    }

    public int getStudyPlanTranslationId() {
        return studyPlanTranslationId;
    }

    public void setStudyPlanTranslationId(int studyPlanTranslationId) {
        this.studyPlanTranslationId = studyPlanTranslationId;
    }

    public int getStudyPlanId() {
        return studyPlanId;
    }

    public void setStudyPlanId(int studyPlanId) {
        this.studyPlanId = studyPlanId;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
