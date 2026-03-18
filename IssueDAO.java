import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class IssueDAO {

    // ── Add Issue ─────────────────────────────────────────
    public void addIssue(Issue issue) {
        if (issue == null) {
            System.err.println("❌ Cannot add null issue.");
            return;
        }

        String sql = "INSERT INTO issues (title, description, priority, status, created_at) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, issue.getTitle());
            stmt.setString(2, issue.getDescription());
            stmt.setString(3, issue.getPriority());
            stmt.setString(4, issue.getStatus());
            stmt.setString(5, issue.getCreatedAt());

            stmt.executeUpdate();
            System.out.println("✅ Issue added successfully.");

        } catch (SQLException e) {
            System.err.println("❌ Failed to add issue.");
            e.printStackTrace();
        }
    }

    // ── Get All Issues ────────────────────────────────────
    public ArrayList<Issue> getAllIssues() {
        ArrayList<Issue> issues = new ArrayList<>();
        String sql = "SELECT * FROM issues ORDER BY issue_id DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Issue issue = new Issue();
                issue.setIssueId(rs.getInt("issue_id"));
                issue.setTitle(rs.getString("title"));
                issue.setDescription(rs.getString("description"));
                issue.setPriority(rs.getString("priority"));
                issue.setStatus(rs.getString("status"));
                issue.setCreatedAt(rs.getString("created_at"));
                issues.add(issue);
            }

        } catch (SQLException e) {
            System.err.println("❌ Failed to fetch issues.");
            e.printStackTrace();
        }

        return issues;
    }

    // ── Search Issues by Priority ─────────────────────────
    public ArrayList<Issue> getIssuesByPriority(String priority) {
        ArrayList<Issue> issues = new ArrayList<>();
        String sql = "SELECT * FROM issues WHERE priority = ? ORDER BY issue_id DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, priority);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Issue issue = new Issue();
                issue.setIssueId(rs.getInt("issue_id"));
                issue.setTitle(rs.getString("title"));
                issue.setDescription(rs.getString("description"));
                issue.setPriority(rs.getString("priority"));
                issue.setStatus(rs.getString("status"));
                issue.setCreatedAt(rs.getString("created_at"));
                issues.add(issue);
            }

        } catch (SQLException e) {
            System.err.println("❌ Failed to search issues by priority.");
            e.printStackTrace();
        }

        return issues;
    }

    // ── Update Issue Status ───────────────────────────────
    public void updateIssueStatus(int issueId, String status) {
        if (status == null || status.trim().isEmpty()) {
            System.err.println("❌ Status cannot be empty.");
            return;
        }

        String sql = "UPDATE issues SET status = ? WHERE issue_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, issueId);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Issue status updated to: " + status);
            } else {
                System.out.println("⚠️ Issue not found with ID: " + issueId);
            }

        } catch (SQLException e) {
            System.err.println("❌ Failed to update issue status.");
            e.printStackTrace();
        }
    }

    // ── Delete Issue ──────────────────────────────────────
    public void deleteIssue(int issueId) {
        String sql = "DELETE FROM issues WHERE issue_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, issueId);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Issue deleted successfully.");
            } else {
                System.out.println("⚠️ Issue not found with ID: " + issueId);
            }

        } catch (SQLException e) {
            System.err.println("❌ Failed to delete issue.");
            e.printStackTrace();
        }
    }

    // ── Get Issue Count ───────────────────────────────────
    public int getTotalIssueCount() {
        String sql = "SELECT COUNT(*) FROM issues";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
