package presenter;

import model.Note;
import model.Notebook;
import view.NotebookView;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class NotebookPresenter {

    private final Notebook model;
    private final NotebookView view;
    public NotebookPresenter(Notebook model, NotebookView view) {
        this.model = model;
        this.view = view;
    }


    public void addNote() {
        try {
            LocalDateTime dateTime = view.getDateTimeInput();
            String description = view.getDescriptionInput();
            model.add(new Note(dateTime, description));
            view.showMessage("Notes added\n");
        } catch (DateTimeException e) {
            view.showMessage("Error! Invalid date format. Try again: \n" );
        }
    }


    public void showNotesForDay() throws IOException {
        LocalDateTime dateTime = view.getDateTimeInput();
        List<Note> notes = model.getNotesForDay(dateTime);
        System.out.println("Notes for Day:");
        view.showNotes(notes);
        System.out.println();
    }


    public void showNotesForWeek() {
        LocalDateTime startOfWeek = view.getDateTimeInput();
        List<Note> notes = model.getNotesForWeek(startOfWeek);
        System.out.println("Notes for Week:");
        view.showNotes(notes);
        System.out.println();
    }


    public void saveNotesToFile()  {
        try {
            model.saveToFile();
            view.showMessage("Notes saved to notes.txt\n");
        } catch (IOException e) {
            view.showMessage("Failed to save notes: " +
                    e.getMessage());
        }
    }


    public void showAllNotes() {
        try {
            view.showMessage("List of all notes:");
            model.loadFromFile();
            System.out.println();
        } catch (IOException e) {
            view.showMessage("Failed to load notes: " +
                    e.getMessage());
        }
    }



    public void handleUserInput() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the notes!");
        System.out.println("-------------------------------");
        while (true) {
            System.out.println("""
                    Enter the number corresponding to the command:
                    [1] - Add Note
                    [2] - Show all Notes
                    [3] - Show Notes for Day
                    [4] - Show Notes for Week
                    [5] - Save Notes to File
                    [0] - Exit
                    """);
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> addNote();
                case 2 -> showAllNotes();
                case 3 -> showNotesForDay();
                case 4 -> showNotesForWeek();
                case 5 -> saveNotesToFile();
                case 0 -> {
                    System.out.println("See you soon!");
                    return;
                }
                default -> System.out.println("Error! Invalid command number. Try again: \n");
            }
        }
    }
}
