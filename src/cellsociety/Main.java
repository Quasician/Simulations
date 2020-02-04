package cellsociety;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.event.ActionEvent;
import java.beans.EventHandler;
import java.util.HashMap;

public class Main extends Application{

    private final static int WIDTH = 500;
    private final static int HEIGHT = 500;
    int currentWidth = WIDTH;
    int currentHeight = HEIGHT;
    Timeline currentTimeline;
    HashMap<String,Double> currentParams;
    Simulation currentSim;
    Visualizer currentViz;
    double seconds = 1;
    BorderPane root = new BorderPane();
    HBox bottomButtons = new HBox();
    VBox rightButtons = new VBox();


    @Override
    public void start(Stage primaryStage) throws Exception {
        currentParams = xml_parser.readPredPreyFile();
        primaryStage.setTitle("Simulation");
        primaryStage.setScene(new Scene(root, WIDTH + 100, HEIGHT));
        primaryStage.show();

        PredPreySim sim = new PredPreySim(50, 50, WIDTH, HEIGHT, currentParams);

        Visualizer vis = new Visualizer(sim.getGrid().length,sim.getGrid()[0].length,currentWidth, currentHeight, root, sim.getColorMap());
        currentSim = sim;
        currentViz = vis;

        simbutton_setup();

        Button fast = makeSpeedButton("Fast", seconds*10);
        Button normal = makeSpeedButton("Normal", seconds);
        Button slow = makeSpeedButton("Slow", seconds*0.5);

        Button step= new Button("Step");
        step.setOnAction(e ->{
            currentTimeline.setRate(0);
            currentSim.updateGrid();
            currentViz.colorGrid(currentSim.getGrid());

        });

        root.setBottom(bottomButtons);
        root.setRight(rightButtons);
        bottomButtons.getChildren().addAll(slow,normal,fast,step);

        vis.initialize(sim.getGrid());
        currentTimeline = new Timeline(
                new KeyFrame(Duration.seconds(seconds), e -> {
                    sim.updateGrid();
                    vis.colorGrid(sim.getGrid());
                })
        );
        currentTimeline.setCycleCount(Animation.INDEFINITE);
        currentTimeline.play();
    }

    public void sim_helper(Simulation temp){
        currentSim = temp;
        Visualizer vis1 = new Visualizer(temp.getGrid().length,temp.getGrid()[0].length,currentWidth, currentHeight, root, temp.getColorMap());
        currentViz = vis1;
        vis1.initialize(temp.getGrid());
        currentTimeline = new Timeline(
                new KeyFrame(Duration.seconds(seconds), j -> {
                    temp.updateGrid();
                    vis1.colorGrid(temp.getGrid());
                })
        );
        currentTimeline.setCycleCount(Animation.INDEFINITE);
        currentTimeline.play();
    }

    private Button makeSpeedButton(String name, Double speed) {
        Button button = new Button(name);
        button.setOnAction(e -> currentTimeline.setRate(speed));
        return button;
    }

    private void simbutton_setup(){
        Button seg= new Button("Segregation");
        rightButtons.getChildren().add(seg);
        seg.setOnAction(e ->{
            //sim = null;
            SegSim temp = new SegSim(100,100, WIDTH, HEIGHT, currentParams);
            currentSim = temp;
            sim_helper(temp);
        });

        Button gol= new Button("Game of Life");
        rightButtons.getChildren().add(gol);
        gol.setOnAction(e ->{
            //sim = null;
            GOLSim temp = new GOLSim(100,100, WIDTH, HEIGHT, currentParams);
            currentSim = temp;
            sim_helper(temp);
        });

        Button perc= new Button("Percolation");
        rightButtons.getChildren().add(perc);
        perc.setOnAction(e ->{
            //sim = null;
            PercSim temp = new PercSim(100,100, WIDTH, HEIGHT, currentParams);
            currentSim = temp;
            sim_helper(temp);
        });

        Button fire= new Button("Fire");
        rightButtons.getChildren().add(fire);
        fire.setOnAction(e ->{
            //sim = null;
            Simulation temp = new FireSim(100,100, WIDTH, HEIGHT, currentParams);
            sim_helper(temp);
        });

        Button pred= new Button("Pred Prey");
        rightButtons.getChildren().add(pred);
        pred.setOnAction(e ->{
            //sim = null;
            PredPreySim temp = new PredPreySim(100,100, WIDTH, HEIGHT ,currentParams);
            sim_helper(temp);
        });
    }


    public static void main (String[] args) {
        launch(args);
    }
}
