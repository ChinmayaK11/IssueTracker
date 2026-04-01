import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final IssueDAO issueDAO = new IssueDAO();

    public static void main(String[] args) {
        DBConnection.testConnection();

        while (true) {
            printMenu();
            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1 -> addIssue();
                case 2 -> viewAllIssues();
                case 3 -> searchByPriority();
                case 4 -> editIssue();
                case 5 -> updateStatus();
                case 6 -> deleteIssue();
                case 7 -> exit();
                default -> System.out.println("⚠️ Invalid choice. Please enter 1-7.");
            }
        }
    }

    // ── Menu ──────────────────────────────────────────────

    private static void printMenu() {
        System.out.println("\n===== ISSUE TRACKING SYSTEM =====");
        System.out.println("Total Issues: " + issueDAO.getTotalIssueCount());
        System.out.println("----------------------------------");
        System.out.println("1. Add Issue");
        System.out.println("2. View All Issues");
        System.out.println("3. Search Issues by Priority");
        System.out.println("4. Edit Issue");
        System.out.println("5. Update Issue Status");
        System.out.println("6. Delete Issue");
        System.out.println("7. Exit");
    }

    // ── Case Handlers ─────────────────────────────────────

    private static void addIssue() {
        String title = readString("Enter title: ");
        String description = readString("Enter description: ");
        String priority = readString("Enter priority (Low/Medium/High): ");

        if (title.isEmpty()) {
            System.out.println("⚠️ Title cannot be empty!");
            return;
        }

        if (!Issue.Priority.isValid(priority)) {
            System.out.println("⚠️ Invalid priority. Use Low, Medium, or High.");
            return;
        }

        issueDAO.addIssue(new Issue(title, description, priority, "Open"));
    }

    private static void viewAllIssues() {
        ArrayList<Issue> issues = issueDAO.getAllIssues();
        if (issues.isEmpty()) {
            System.out.println("No issues found.");
            return;
        }
        System.out.println("\n--- Issue List (" + issues.size() + " total) ---");
        printIssueList(issues, true);
    }

    private static void searchByPriority() {
        String priority = readString("Enter priority to search (Low/Medium/High): ");

        if (!Issue.Priority.isValid(priority)) {
            System.out.println("⚠️ Invalid priority. Use Low, Medium, or High.");
            return;
        }

        ArrayList<Issue> filtered = issueDAO.getIssuesByPriority(priority);
        if (filtered.isEmpty()) {
            System.out.println("No issues found with priority: " + priority);
            return;
        }
        System.out.println("\n--- " + priority + " Priority Issues (" + filtered.size() + ") ---");
        printIssueList(filtered, false);
    }

    private static void editIssue() {
        int id = readInt("Enter Issue ID to edit: ");
        String newTitle = readString("Enter new title: ");
        String newDescription = readString("Enter new description: ");
        String newPriority = readString("Enter new priority (Low/Medium/High): ");
        issueDAO.editIssue(id, newTitle, newDescription, newPriority);
    }

    private static void updateStatus() {
        int id = readInt("Enter Issue ID to update: ");
        String status = readString("Enter new status (Open/In Progress/Closed): ");

        if (!Issue.Status.isValid(status)) {
            System.out.println("⚠️ Invalid status. Use Open, In Progress, or Closed.");
            return;
        }

        issueDAO.updateIssueStatus(id, status);
    }

    private static void deleteIssue() {
        int id = readInt("Enter Issue ID to delete: ");
        issueDAO.deleteIssue(id);
    }

    private static void exit() {
        System.out.println("Exiting application. Goodbye! 👋");
        scanner.close();
        System.exit(0);
    }

    // ── Helpers ───────────────────────────────────────────

    private static void printIssueList(ArrayList<Issue> issues, boolean showPriority) {
        for (Issue i : issues) {
            StringBuilder sb = new StringBuilder();
            sb.append("ID: ").append(i.getIssueId());
            sb.append(" | Title: ").append(i.getTitle());
            if (showPriority) sb.append(" | Priority: ").append(i.getPriority());
            sb.append(" | Status: ").append(i.getStatus());
            sb.append(" | Created: ").append(i.getCreatedAt());
            System.out.println(sb);
        }
    }

    private static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Invalid input. Please enter a number.");
            }
        }
    }
}
