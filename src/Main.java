
import model.Notebook;
import presenter.NotebookPresenter;
import view.ConsoleNotebookView;
import view.NotebookView;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        Notebook model = new Notebook();
        NotebookView view = new ConsoleNotebookView();
        NotebookPresenter presenter = new NotebookPresenter(model,view);
        presenter.handleUserInput();
    }
}