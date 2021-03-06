package application;
///////////////////////////////////////////////////////////////////////////////
//
//Class File:       Team.java
//Semester:         Spring 2018
//
//Author:           Yaakov Levin, Anthony Leung, Sharon Lin, Ben Lewis
//Credits:          none
//
/////////////////////////////////////////////////////////////////////////////////
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class Match implements Comparable{

    private int x, y;
    private int width, height;
    protected Team team1, team2;
    private boolean isLeft;
    protected Team winningTeam;
    protected Team lostTeam; 

    private Label team1Label;
    private Label team2Label;

    protected TextField team1TextField;
    protected TextField team2TextField;

    protected Button scoreButton;
    private boolean isActive;
    private boolean isSemiFinal;

    /**
     * Creates a Match at a specified x, y, width, and height oriented towards the left or right
     * @param x X value at the top-left of Match object
     * @param y Y value at the top-left of Match object 
     * @param width Width of the Match object
     * @param height Height of the Match object
     * @param isLeft Whether or not the Match is on the left side
     */
    public Match(int x, int y, int width, int height, boolean isLeft) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isLeft = isLeft;
        this.isSemiFinal = false;

        /* ******************** TEAM 1 ******************** */
        
        team1 = null;

        //Set up team1's label
        team1Label = new Label("TBD");
        team1Label.setLayoutX(x);
        team1Label.setLayoutY(y);

        //Set up team1's input box
        team1TextField = new TextField();
        team1TextField.setPromptText("Score");
        team1TextField.setLayoutX(x + (width / 2.0));
        team1TextField.setLayoutY(y);
        team1TextField.setMaxSize(60,10);
        
        /* ******************** TEAM 2 ******************** */
        
        team2 = null;

        //Set up team2's label
        team2Label = new Label("TBD");
        team2Label.setLayoutX(x);
        team2Label.setLayoutY(y + (height * 2) / 3.0);

        //Set up team2's input box
        team2TextField = new TextField();
        team2TextField.setPromptText("Score");
        team2TextField.setLayoutX(x + (width / 2.0));
        team2TextField.setLayoutY(y + (height * 2) / 3.0);
        team2TextField.setMaxSize(60, 10);

        /* ***************** SCORE BUTTON ***************** */
        scoreButton = new Button();
        scoreButton.setText("Submit");
        scoreButton.setLayoutX(x);
        scoreButton.setLayoutY(y + height / 3.0);
        scoreButton.setMaxSize(80, 10);

        //Creates the event handler for the score button
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() { 
            @Override 
            public void handle(MouseEvent e) { 
                
                try { 
                    
                    int score1 = Integer.parseInt(team1TextField.getText());
                    int score2 = Integer.parseInt(team2TextField.getText());
                    
                    //Ensures arguments are valid
                    if (score1 < 0 || score2 < 0 || score1 == score2) {
                        throw new IllegalArgumentException();
                    }
                    
                    winningTeam = score1 > score2 ? team1 : team2;
                    
                    if (isSemiFinal) {
                        lostTeam = score1 > score2 ? team2 : team1;
                        lostTeam.setScore(score1 > score2 ? score2 : score1);
                    }
                    
                    //Tells main match is done
                    finished();
                    setActive(false);
                    
                }
                catch (Exception ex) {
                    if (ex instanceof IllegalArgumentException || ex instanceof NumberFormatException) {
                        team1TextField.clear();
                        team2TextField.clear();
                        System.out.println("Please enter only valid Integers as scores.");
                    }                   
                }
            } 
        };  
        
        //Registering the event filter 
        scoreButton.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler); 

        /* *************** IS LEFT HANDLING *************** */
        if (!isLeft) {
            double tmp = team1Label.getLayoutX();
            team1Label.setLayoutX(team1TextField.getLayoutX());
            team1TextField.setLayoutX(tmp);

            tmp = team2Label.getLayoutX();
            team2Label.setLayoutX(team2TextField.getLayoutX());
            team2TextField.setLayoutX(tmp);
            
            scoreButton.setLayoutX(x + 1);
        }
        
        /* ********************IS ACTIVE ****************** */
        this.setActive(false);
    }

    /**
     * Creates a Match at a specified x, y, width, and height oriented towards the left or right
     * @param x X value at the top-left of Match object
     * @param y Y value at the top-left of Match object
     * @param width Width of the Match object
     * @param height Height of the Match object
     * @param isLeft Whether or not the Match is on the left side
     * @param team1 First team in the match
     * @param team2 Second team in the match
     */
    public Match(int x, int y, int width, int height, boolean isLeft, Team team1, Team team2) {
        this(x, y, width, height, isLeft);

        this.team1 = team1;
        team1Label.setText(team1.getName());
        this.team2 = team2;
        team2Label.setText(team2.getName());
        
        this.setActive(true);
    }

    /**
     * Adds the control elements into the provided GridPane
     * @param grid GridPane to add Match into
     */
    public void addToLayout(GridPane grid) {
        grid.add(team1Label, (int) team1Label.getLayoutX(), (int) team1Label.getLayoutY());
        grid.add(team2Label, (int) team2Label.getLayoutX(), (int) team2Label.getLayoutY());
        grid.add(team1TextField, (int) team1TextField.getLayoutX(),
                        (int) team1TextField.getLayoutY());
        grid.add(team2TextField, (int) team2TextField.getLayoutX(),
                        (int) team2TextField.getLayoutY());
        grid.add(scoreButton, (int) scoreButton.getLayoutX(), (int) scoreButton.getLayoutY());

    }
    
    /**
     * Sets the first team and handles if Match needs activation
     * @param team1 Team to set as team1
     */
    public void setTeam1(Team team1) {
        this.team1 = team1;
        team1Label.setText(team1.getName());
        if (team2 != null) {
            this.setActive(true);
        }
    }
    
    /**
     * Sets the second team and handles if Match needs activation
     * @param team2 Team to set as team2
     */
    public void setTeam2(Team team2) {
        this.team2 = team2;
        team2Label.setText(team2.getName());
        if (team1 != null) {
            this.setActive(true);
        }
    }
    
    /**
     * Return lost team in semifinal game
     */
    public Team getLostTeam() {
        if (this.isSemiFinal==true) {
            return lostTeam; 
        }
        return null;
    }
    
    /**
     * Sets Match's Active status to true or false
     * @param isActive 
     */
    public void setActive(boolean isActive) {
        this.isActive = isActive;
        
        if (isActive == true) {
            team1TextField.setEditable(true);
            team2TextField.setEditable(true);
        }else {
            team1TextField.setEditable(false);
            team2TextField.setEditable(false);
        }
    }
    
    public void activateSemifinal() {
        this.isSemiFinal = true;
    }
    
    /**
     * Returns if match is active
     * @return if match is active
     */
    public boolean getIsActive() {
        return isActive;
    }
    
    /**
     * Returns team that won the match
     * @return winning team
     */
    public Team getWinningTeam() {
        return winningTeam;
    }
    
    @Override
    /**
     * Compares matches: returns 1 if they are equal, otherwise -1
     */
    public int compareTo(Object o) {
        if (o instanceof Match) {
            if (((Match)o).team1 == team1 && ((Match)o).team2 == team2)
                return 1;
        }
        return -1;
    }
    
    /**
     * Called as interim method for event handler to Main's matchFinished method
     */
    private void finished() {
        Main.matchFinished(this);
    }
}
