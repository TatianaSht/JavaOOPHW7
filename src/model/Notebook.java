package model;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Notebook {

    private final List<Note> notes = new ArrayList<>();
    public void add(Note note) {
        notes.add(note);
    }
    public List<Note> getNotes() {
        return new ArrayList<>(notes);
    }
    public List<Note> getNotesForDay(LocalDateTime dateTime) {
        return notes.stream()
                .filter(note ->
                        note.getDateTime().toLocalDate().isEqual(dateTime.toLocalDate()))
                .sorted(Comparator.comparing(Note::getDateTime))
                .collect(Collectors.toList());
    }


    public List<Note> getNotesForWeek(LocalDateTime startOfWeek) {
        LocalDateTime endOfWeek = startOfWeek.plusWeeks(1);
        return notes.stream()
                .filter(note ->
                        !note.getDateTime().isBefore(startOfWeek) &&
                                !note.getDateTime().isAfter(endOfWeek))
                .sorted(Comparator.comparing(Note::getDateTime))
                .collect(Collectors.toList());
    }


    public void saveToFile() throws IOException {
        String filePath = "src/resources/notes.txt";
        if (!checkFile(filePath)) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.newLine();
            for (Note note : notes) {
                writer.write(note.toString());
                writer.newLine();
                writer.flush();
            }
        }    else {
            FileWriter writer = new FileWriter(filePath, true);
            writer.write("\r\n");
            for (Note note : notes) {
                writer.write(note.toString());
                writer.flush();
            }
        }
    }


    public void loadFromFile() throws IOException {
        String filePath = "src/resources/notes.txt";
        notes.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(": ", 2);
                LocalDateTime dateTime = LocalDateTime.parse(parts[0]);
                String description = parts[1];
                notes.add(new Note(dateTime, description));
            }
        }
        for(Note note: notes) {
            System.out.println(note);
        }
    }


    public boolean checkFile(String filePath) {
        return Files.exists(Path.of(filePath));
    }

}
