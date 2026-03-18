import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Issue {

    private int issueId;
    private String title;
    private String description;
    private String priority;
    private String status;
    private String createdAt;

    public Issue() {
        this.createdAt = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a"));
    }

    public Issue(String title, String description, String priority, String status) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.createdAt = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a"));
    }

    // ── Getters & Setters ──────────────────────────────────

    public int getIssueId() { return issueId; }
    public void setIssueId(int issueId) { this.issueId = issueId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    // ── toString ──────────────────────────────────────────

    @Override
    public String toString() {
        return "Issue{" +
            "ID=" + issueId +
            ", Title='" + title + '\'' +
            ", Priority='" + priority + '\'' +
            ", Status='" + status + '\'' +
            ", CreatedAt='" + createdAt + '\'' +
            '}';
    }
}
