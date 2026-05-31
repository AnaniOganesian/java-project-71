package hexlet.code;

public class DiffNode {
    public enum Status {
        ADDED,      // + ключ добавили
        REMOVED,    // - ключ удалили
        UNCHANGED,  // ключ не изменили
        CHANGED     // ± ключ изменили
    }

    private String key;
    private Object value1;  // значение в первом файле
    private Object value2;  // значение во втором файле
    private Status status;

    public DiffNode(String key, Object value1, Object value2, Status status) {
        this.key = key;
        this.value1 = value1;
        this.value2 = value2;
        this.status = status;
    }

    public String getKey() {
        return key;
    }

    public Object getValue1() {
        return value1;
    }

    public Object getValue2() {
        return value2;
    }

    public Status getStatus() {
        return status;
    }
}
