package DataStructureAndAlgorithms.models;

public class ProblemInfo {

    private String name;

    private String category;

    private String className;

    private String returnType;

    private String filePath;

    public ProblemInfo(String name, String category, String className, String returnType, String filePath) {
        this.name = name;
        this.category = category;
        this.className = className;
        this.returnType = returnType;
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
