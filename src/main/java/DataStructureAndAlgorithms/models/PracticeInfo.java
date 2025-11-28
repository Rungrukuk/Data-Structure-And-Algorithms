package DataStructureAndAlgorithms.models;

import DataStructureAndAlgorithms.utils.Constants;

public class PracticeInfo {

    private ProblemInfo problemInfo;
    private String practiceClassName;
    private String filePath;

    public PracticeInfo(ProblemInfo problemInfo, String practiceClassName, String filePath) {
        this.problemInfo = problemInfo;
        this.practiceClassName = practiceClassName;
        this.filePath = filePath;
    }

    public ProblemInfo getProblemInfo() {
        return problemInfo;
    }

    public void setProblemInfo(ProblemInfo problemInfo) {
        this.problemInfo = problemInfo;
    }

    public String getPracticeClassName() {
        return practiceClassName;
    }

    public void setPracticeClassName(String practiceClassName) {
        this.practiceClassName = practiceClassName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getUniqueId() {
        return problemInfo.getCategory() + Constants.SEPERATOR + problemInfo.getName();
    }
}
