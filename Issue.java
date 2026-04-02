import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Issue {

    // ── Enums for type safety ─────────────────────────────

    public enum Priority {
        LOW, MED, HIGH;

        public static boolean isValid(String value) {
            if (value == null || value.trim().isEmpty())
                return false;
            for (Priority p : values()) {
                if (p.name().equalsIgnoreCase(value))
                    return true;
            }
            return false;
        }
    }

    public enum Status {
        OPEN, IN_PROGRESS, CLOSED;

        // bug fix: previous version crashed with NullPointerException if value was null
        public static boolean isValid(String value) {
            if (value == null || value.trim().isEmpty())
                return false;
            for (Status s : values()) {
                if (s.name().equalsIgnoreCase(value.replace(" ", "_")))
                    return true;
            }
            return false;
        }
    }

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");

    // ── Fields ────────────────────────────────────────────

    private int issueId;
    private String title;
    private String description;
    private String priority;
    private String status;
    private String createdAt;

    // ── Constructors ──────────────────────────────────────

    public Issue() {
        this.createdAt = LocalDateTime.now().format(FORMATTER);
    }

    public Issue(String title, String description, String priority, String status) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.createdAt = LocalDateTime.now().format(FORMATTER);
    }

    // ── Getters & Setters ─────────────────────────────────

    public int getIssueId() {
        return issueId;
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
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

    public void setDescription(String desc) {
        this.description = desc;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    // ── toString ──────────────────────────────────────────

    @Override
    public String toString() {
        return String.format("Issue{ID=%d, Title='%s', Priority='%s', Status='%s', CreatedAt='%s'}",
                issueId, title, priority, status, createdAt);
    }
}
