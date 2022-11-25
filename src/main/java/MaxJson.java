public class MaxJson {
    private String time;
    private String category;
    private Integer sum;

    public MaxJson(String time, String category, Integer sum) {
        this.time = time;
        this.category = category;
        this.sum = sum;
    }

    public String getMaxCategory() {
        return time;
    }

    public void setMaxCategory(String maxCategory) {
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "MaxJson{" +
                "maxCategory='" + time + '\'' +
                ", category='" + category + '\'' +
                ", sum=" + sum +
                '}';
    }
}
