import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // Test DB connection on startup
        DBConnection.testConnection();

        IssueDAO issueDAO = new IssueDAO();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== ISSUE TRACKING SYSTEM =====");
            System.out.println("Total Issues: " + issueDAO.getTotalIssueCount());
            System.out.println("----------------------------------");
            System.out.println("1. Add Issue");
            System.out.println("2. View All Issues");
            System.out.println("3. Search Issues by Priority");
            System.out.println("4. Update Issue Status");
            System.out.println("5. Delete Issue");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {

                case 1:
                    System.out.print("Enter title: ");
                    String title = scanner.nextLine().trim();

                    System.out.print("Enter description: ");
                    String description = scanner.nextLine().trim();

                    System.out.print("Enter priority (Low/Medium/High): ");
                    String priority = scanner.nextLine().trim();

                    if (title.isEmpty() || priority.isEmpty()) {
                        System.out.println("⚠️ Title and Priority cannot be empty!");
                        break;
                    }

                    Issue issue = new Issue(title, description, priority, "Open");
                    issueDAO.addIssue(issue);
                    break;

                case 2:
                    ArrayList<Issue> issues = issueDAO.getAllIssues();
                    if (issues.isEmpty()) {
                        System.out.println("No issues found.");
                        break;
                    }
                    System.out.println("\n--- Issue List (" + issues.size() + " total) ---");
                    for (Issue i : issues) {
                        System.out.println(
                            "ID: " + i.getIssueId() +
                            " | Title: " + i.getTitle() +
                            " | Priority: " + i.getPriority() +
                            " | Status: " + i.getStatus() +
                            " | Created: " + i.getCreatedAt()
                        );
                    }
                    break;

                case 3:
                    System.out.print("Enter priority to search (Low/Medium/High): ");
                    String searchPriority = scanner.nextLine().trim();

                    ArrayList<Issue> filtered = issueDAO.getIssuesByPriority(searchPriority);
                    if (filtered.isEmpty()) {
                        System.out.println("No issues found with priority: " + searchPriority);
                        break;
                    }
                    System.out.println("\n--- " + searchPriority + " Priority Issues (" + filtered.size() + ") ---");
                    for (Issue i : filtered) {
                        System.out.println(
                            "ID: " + i.getIssueId() +
                            " | Title: " + i.getTitle() +
                            " | Status: " + i.getStatus() +
                            " | Created: " + i.getCreatedAt()
                        );
                    }
                    break;

                case 4:
                    System.out.print("Enter Issue ID to update: ");
                    int updateId;
                    try {
                        updateId = Integer.parseInt(scanner.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("⚠️ Invalid ID.");
                        break;
                    }

                    System.out.print("Enter new status (Open/In Progress/Closed): ");
                    String status = scanner.nextLine().trim();
                    issueDAO.updateIssueStatus(updateId, status);
                    break;

                case 5:
                    System.out.print("Enter Issue ID to delete: ");
                    int deleteId;
                    try {
                        deleteId = Integer.parseInt(scanner.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("⚠️ Invalid ID.");
                        break;
                    }
                    issueDAO.deleteIssue(deleteId);
                    break;

                case 6:
                    System.out.println("Exiting application. Goodbye! 👋");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("⚠️ Invalid choice. Please enter 1-6.");
            }
        }
    }
}
