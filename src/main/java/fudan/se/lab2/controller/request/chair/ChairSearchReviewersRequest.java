package fudan.se.lab2.controller.request.chair;

public class ChairSearchReviewersRequest {

    private String name = "GET";

    // 真实姓名
    private String fullName;

    public ChairSearchReviewersRequest() {

    }

    public ChairSearchReviewersRequest(String fullName) {
        this.fullName = fullName;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "ChairSearchReviewersRequest{" +
                "name='" + name + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
