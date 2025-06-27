import java.io.*;
import java.util.Scanner;

public class NotesApp {
    private static final String NOTES_FILE = "notes.txt";
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("=== Simple Notes Manager ===");

        while (true) {
            printMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    addNote();
                    break;
                case 2:
                    viewNotes();
                    break;
                case 3:
                    deleteNotes();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n1. Add a new note");
        System.out.println("2. View all notes");
        System.out.println("3. Delete all notes");
        System.out.println("4. Exit");
    }

    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a number!");
            scanner.next();
            System.out.print(prompt);
        }
        return scanner.nextInt();
    }

    private static void addNote() {
        System.out.println("\nEnter your note (type 'END' on a new line to finish):");
        scanner.nextLine(); // Consume newline

        StringBuilder noteContent = new StringBuilder();
        while (true) {
            String line = scanner.nextLine();
            if (line.equalsIgnoreCase("END")) {
                break;
            }
            noteContent.append(line).append("\n");
        }

        try (FileWriter writer = new FileWriter(NOTES_FILE, true)) {
            writer.write(noteContent.toString());
            writer.write("---\n"); // Separator between notes
            System.out.println("Note saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving note: " + e.getMessage());
        }
    }

    private static void viewNotes() {
        try (BufferedReader reader = new BufferedReader(new FileReader(NOTES_FILE))) {
            String line;
            int noteNumber = 1;

            System.out.println("\n=== Your Notes ===");
            while ((line = reader.readLine()) != null) {
                if (line.equals("---")) {
                    noteNumber++;
                    System.out.println("-----------------");
                } else {
                    System.out.println(line);
                }
            }
            if (noteNumber == 1 && line == null) {
                System.out.println("No notes found.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("No notes found. File doesn't exist yet.");
        } catch (IOException e) {
            System.out.println("Error reading notes: " + e.getMessage());
        }
    }

    private static void deleteNotes() {
        File notesFile = new File(NOTES_FILE);
        if (notesFile.exists()) {
            if (notesFile.delete()) {
                System.out.println("All notes deleted successfully!");
            } else {
                System.out.println("Failed to delete notes.");
            }
        } else {
            System.out.println("No notes file exists to delete.");
        }
    }
}


