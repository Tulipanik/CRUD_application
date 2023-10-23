package UI;

import com.example.backend.repositories.NoteRepositoryH2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class UI {

    //TODO: probably should be instance of Note Repository
    private static NoteRepositoryH2 databaseHandler = null;

    public void Start() {
        System.out.println("Witamy w aplikacji Notatki!");
        startMenu();

        String mode = readFromUser();
        choosingStartStage(mode);
    }

    private void startMenu() {
        System.out.println("Wybierz operację którą chcesz wykonać:");
        System.out.println("1- Wyświetlanie");
        System.out.println("2- Tworzenie");
        System.out.println("3- Modyfikacja");
        System.out.println("4- Usuwanie");
    }

    private void choosingStartStage(String mode) {
        String newMode = "";

        switch (mode) {
            case "1":
                System.out.println("1- Wyświetl wszystkie notatki");
                System.out.println("2- Wyświetl notatkę o wybranym tytule");

                newMode = readFromUser();
                choosingReadStage(newMode);

            case "2":
                System.out.println("1- Dodaj 1 notatkę");
                System.out.println("2- Dodaj wiele notatek");

                newMode = readFromUser();


            case "3":
                System.out.println("1 - Wpisz tytuł notatki, którą chcesz edytować");
                System.out.println("2 - Przypomnij mi jakie mam notatki (wyświetl wszystkie notatki)");

                newMode = readFromUser();
                choosingModifyStage(newMode);

            case "4":
                System.out.println("1 - Wpisz tytuł notatki, którą chcesz usunąć");
                System.out.println("2 - Usuń wiele notatek na raz");
                System.out.println("3 - Przypomnij mi jakie mam notatki (wyświetl wszystkie notatki)");

                newMode = readFromUser();
                choosingDeleteStage(newMode);

            default:
                System.out.println("Podałeś nieprawidłową operację!");
                startMenu();
                newMode = readFromUser();
                choosingStartStage(newMode);
        }
    }

    private void choosingReadStage(String mode) {

        switch (mode) {
            case "1":

            case "2":

            default:
        }
    }

    private void choosingCreateStage(String mode) {

        switch (mode) {
            case "1":
                System.out.println("Podaj tytuł notatki:");
                String title = readFromUser();

                System.out.println("Podaj treść notatki:");
                String note = readFromUser();
//                TODO: add note to database
//                databaseHandler.addNote(new Note(title, note, "1"));

            case "2":

            default:
        }
    }

    private void choosingModifyStage(String mode) {

        switch (mode) {
            case "1":
                System.out.println("Podaj tytuł notatki, którą chcesz edytować:");
                String title = readFromUser();

            case "2":

            default:
        }
    }

    private void choosingDeleteStage(String mode) {

        switch (mode) {
            case "1":
                System.out.println("Podaj tytuł notatki, którą chcesz usunąć:");
                String title = readFromUser();

            case "2":
                System.out.println("Ile notatek chcesz usunąć?");
                Integer num = Integer.valueOf(readFromUser());
                List<String> titles = new ArrayList<>();
                for (int i = 0; i < num; i++) {
                    System.out.println("Podaj tytuł " + num + " notatki do usunięcia: ");
                    String next_title = readFromUser();
                    titles.add(next_title);
                }

            default:
        }
    }

    private String readFromUser() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
