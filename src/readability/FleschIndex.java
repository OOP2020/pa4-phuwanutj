package readability;

import readability.Strategy.FleschKincaidGradeLevel;
import readability.Strategy.FleschReadingEase;
import readability.Strategy.ScoringStrategy;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.FlowPane;
import javafx.geometry.Pos;
import javafx.application.Platform;
import java.io.File;

/**
 * Flesh Readability index is an application to calculate a score for text file
 * and then tell the user about the target audience.
 *
 * @author Phuwanut Jiamwatthanaloet
 */
public class FleschIndex extends Application {

    /** File name or URL. */
    private TextField fileName;
    /** Result after reading a file or url. */
    private TextArea result;
    /** A strategy to convert score using Flesch reading-ease test */
    private final ScoringStrategy strategy = new FleschReadingEase();
    /** A strategy to convert score using Flesch–Kincaid grade level */
    private final ScoringStrategy strategy2 = new FleschKincaidGradeLevel();

    /**
     * Set the application's name and run the application.
     *
     * @param stage is the primary stage for ths application.
     */
    @Override
    public void start(Stage stage) {
        Pane uiroot = initComponents();
        VBox root = new VBox();
        MenuBar menuBar = makeMenuBar();
        root.getChildren().addAll(menuBar,uiroot);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.setTitle("Readability index");
        stage.show();
    }

    /**
     * Initialize components for the scene to display.
     *
     * @return pane is the pane to be set in the scene.
     */
    private Pane initComponents() {
        FlowPane pane = new FlowPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setHgap(6.0);
        Label label = new Label("File or URL name: ");
        fileName = new TextField();
        result = new TextArea();
        result.setPrefHeight(500);
        result.setPrefWidth(500);
        result.setEditable(false);
        Button browseButton = new Button("Browse");
        browseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = new Stage();
                FileChooser chooser = new FileChooser();
                File file = chooser.showOpenDialog(stage);
                if (file != null) {
                    fileName.setText(file.getAbsolutePath());
                }
            }
        });
        Button countButton = new Button("Count");
        countButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                WordCount count = new WordCount();
                count.CountWord(fileName.getText(),fileName);
                result.setText(setResult(fileName.getText(),count.getWordCount(),count.getSyllableCount(),count.getSentenceCount()));
            }
        });
        Button clearButton = new Button("Clear");
        clearButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                fileName.clear();
                result.clear();
            }
        });
        pane.getChildren().addAll(label,fileName,browseButton,countButton,clearButton,result);
        return pane;
    }

    /**
     * Create a menu bar for this application.
     *
     * @return the new menu bar.
     */
    public MenuBar makeMenuBar() {
        MenuBar menuBar = new MenuBar();
        MenuItem exit = new MenuItem("Exit");
        MenuItem browse = new MenuItem("Help - Browse");
        MenuItem convert = new MenuItem("Help - Convert");
        MenuItem clear = new MenuItem("Help - Clear");
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Platform.exit();
            }
        });
        browse.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                result.setText("Browse button is use to select a file from your local disk.");
            }
        });
        convert.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                result.setText("Convert button is use to calculate the score of a file and display the result.");
            }
        });
        clear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                result.setText("Clear button is use to clear the file name and the result.");
            }
        });
        Menu menu = new Menu("Help");
        menu.getItems().addAll(browse,convert,clear,exit);
        menuBar.getMenus().addAll(menu);
        menuBar.setUseSystemMenuBar(true);
        return menuBar;
    }

    /**
     * Set the result in the area.
     *
     * @param fileName is the name or url of the file
     * @param wordCount is the number of word in the file.
     * @param syllableCount is the name or syllable of the file
     * @param sentenceCount is the number of sentence in the file.
     * @return text is the result from reading the file.
     */
    public String setResult(String fileName,double wordCount,double syllableCount,double sentenceCount) {
        String text = "";
        text += String.format("File name: %s\n",fileName);
        text += String.format("Number of syllables: %d\n",(int) syllableCount);
        text += String.format("Number of words: %d\n",(int) wordCount);
        text += String.format("Number of sentences: %d\n",(int) sentenceCount);
        text += String.format("Flesch index: %.1f\n",this.strategy.getScore(wordCount,syllableCount,sentenceCount));
        text += String.format("Flesch Kincaid Grade Level: %.1f\n",this.strategy2.getScore(wordCount,syllableCount,sentenceCount));
        text += String.format("Readability: %s\n",convertScore(this.strategy.getScore(wordCount,syllableCount,sentenceCount)));
        return text;
    }

    /**
     * Convert the score into target audience of the file.
     *
     * @param score is the used to convert.
     * @return the target audience of the file.
     */
    public String convertScore(double score) {
        if (score > 100) return "4th grade student";
        else if (score > 90) return "5th grade student";
        else if (score > 80) return "6th grade student";
        else if (score > 70) return "7th grade student";
        else if (score > 65) return "8th grade student";
        else if (score > 60) return "9th grade student";
        else if (score > 50) return "High school student";
        else if (score > 30) return "College student";
        else if (score >= 0) return "College graduate";
        else return "Advanced degree graduate";
    }

    /**
     * Launch the application.
     *
     * @param args not used.
     */
    public static void main(String[] args) {
        launch(args);
    }
}