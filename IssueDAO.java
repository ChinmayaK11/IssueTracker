import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class IssueDAO {

    // ── Private helper: maps a ResultSet row → Issue ──────

    private Issue mapRow(ResultSet rs) throws SQLException {
        Issue issue = new Issue();
        issue.setIssueId(rs.getInt("issue_id"));
        issue.setTitle(rs.getString("title"));
        issue.setDescription(rs.getString("description"));
        issue.setPriority(rs.getString("priority"));
        issue.setStatus(rs.getString("status"));
        issue.setCreatedAt(rs.getString("created_at"));
        return issue;
    }

    // ── Add Issue ─────────────────────────────────────────

    public void addIssue(Issue issue) {
        if (issue == null) {
            System.err.println(" Cannot add null issue.");
            return;
        }

        String sql = "INSERT INTO issues (title, description, priority, status, created_at) " +
                "VALUES (?, ?, ?, ?, ?)";

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
            System.err.println("❌ Failed to add issue: " + e.getMessage());
        }
    }

    // ── Get All Issues ────────────────────────────────────

    public ArrayList<Issue> getAllIssues() {
        ArrayList<Issue> issues = new ArrayList<>();
        String sql = "SELECT * FROM issues ORDER BY issue_id DESC";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next())
                issues.add(mapRow(rs));

        } catch (SQLException e) {
            System.err.println("❌ Failed to fetch issues: " + e.getMessage());
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
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next())
                    issues.add(mapRow(rs));
            }

        } catch (SQLException e) {
            System.err.println("❌ Failed to search by priority: " + e.getMessage());
        }

        return issues;
    }

    // ── Search Issues by Status ───────────────────────────

    public ArrayList<Issue> getIssuesByStatus(String status) {
        ArrayList<Issue> issues = new ArrayList<>();
        String sql = "SELECT * FROM issues WHERE status = ? ORDER BY issue_id DESC";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next())
                    issues.add(mapRow(rs));
            }

        } catch (SQLException e) {
            System.err.println("❌ Failed to search by status: " + e.getMessage());
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
            System.out.println(rows > 0
                    ? "✅ Status updated to: " + status
                    : "⚠️ No issue found with ID: " + issueId);

        } catch (SQLException e) {
            System.err.println("❌ Failed to update status: " + e.getMessage());
        }
    }

    // ── Delete Issue ──────────────────────────────────────

    public void deleteIssue(int issueId) {
        String sql = "DELETE FROM issues WHERE issue_id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, issueId);

            int rows = stmt.executeUpdate();
            System.out.println(rows > 0
                    ? "✅ Issue deleted successfully."
                    : "⚠️ No issue found with ID: " + issueId);

        } catch (SQLException e) {
            System.err.println("❌ Failed to delete issue: " + e.getMessage());
        }
    }

    // ── Get Total Issue Count ─────────────────────────────

    public int getTotalIssueCount() {
        String sql = "SELECT COUNT(*) FROM issues";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            if (rs.next())
                return rs.getInt(1);

        } catch (SQLException e) {
            System.err.println("❌ Failed to get issue count: " + e.getMessage());
        }

        return 0;
    }

    // ── Summary Report (new feature) ──────────────────────
    // Prints count of issues grouped by priority and status

    public void printSummaryReport() {
        String prioritySql = "SELECT priority, COUNT(*) as count FROM issues GROUP BY priority";
        String statusSql = "SELECT status,   COUNT(*) as count FROM issues GROUP BY status";

        System.out.println("\n========== ISSUE SUMMARY REPORT ==========");

        try (Connection conn = DBConnection.getConnection()) {

            System.out.println("\n📊 By Priority:");
            try (PreparedStatement stmt = conn.prepareStatement(prioritySql);
                    ResultSet rs = stmt.executeQuery()) {
                boolean any = false;
                while (rs.next()) {
                    System.out.printf("   %-10s → %d issue(s)%n",
                            rs.getString("priority"), rs.getInt("count"));
                    any = true;
                }
                if (!any)
                    System.out.println("   No data.");
            }

            System.out.println("\n📋 By Status:");
            try (PreparedStatement stmt = conn.prepareStatement(statusSql);
                    ResultSet rs = stmt.executeQuery()) {
                boolean any = false;
                while (rs.next()) {
                    System.out.printf("   %-15s → %d issue(s)%n",
                            rs.getString("status"), rs.getInt("count"));
                    any = true;
                }
                if (!any)
                    System.out.println("   No data.");
            }

        } catch (SQLException e) {
            System.err.println("❌ Failed to generate report: " + e.getMessage());
        }

        System.out.println("===========================================");
    }

    // ── Close All Issues by Priority (new feature) ────────
    // Marks every Open/In Progress issue of a given priority as Closed

    public void closeAllByPriority(String priority) {
        if (priority == null || priority.trim().isEmpty()) {
            System.err.println("❌ Priority cannot be empty.");
            return;
        }

        String sql = "UPDATE issues SET status = 'Closed' " +
                "WHERE priority = ? AND status != 'Closed'";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, priority.trim());

            int rows = stmt.executeUpdate();
            System.out.println(rows > 0
                    ? "✅ Closed " + rows + " " + priority + "-priority issue(s)."
                    : "⚠️ No open issues found with priority: " + priority);

        } catch (SQLException e) {
            System.err.println("❌ Failed to close issues: " + e.getMessage());
        }
    }
}
