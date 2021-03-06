package engine;
import authoring.model.GameData;
import authoring.model.collections.ConditionsCollection;
import authoring.model.collections.GameObjectsCollection;
import authoring.model.collections.LevelsCollection;
import authoring.model.collections.SoundsCollection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.util.Duration;
import engine.render.Renderer;
import engine.sound.SoundUtility;
import engine.conditions.Condition;
import engine.gameObject.*;
import engine.level.LevelManager;

/**
 * central game manager responsible for holding game data, setting the timeline speed
 * and processing each game frame
 * 
 * @author Davis
 *
 */

public class GameManager {
    private ConditionsCollection myGameConditions;
    private GameObjectsCollection myGameObjects;
    private Renderer myGameObjectRenderer;
    private SoundUtility mySoundUtility;
    private Group myRootGroup;
    private Timeline myAnimation;
    private LevelManager myLevelManager;
    private LevelsCollection myLevels;
    private String myRelativePath;
    private static final double DEFAULT_SPEED = 60.0;
    
    public GameManager (ConditionsCollection myGameConditions, GameObjectsCollection myGameObjects, LevelsCollection myLevels, Group myRootGroup, String relativePath) {
        super();
        this.myGameConditions = myGameConditions;
        this.myGameObjects = myGameObjects;
        this.myRootGroup = myRootGroup;
        this.myLevels = myLevels;
        this.myGameObjectRenderer = new Renderer(myRootGroup,relativePath);
        this.mySoundUtility = new SoundUtility(myLevels, relativePath);
        this.myRelativePath = relativePath;
        createLevelManager();
        initializeConditions();
        
    }
    
    /**
     * initialize the game
     */
    public void initialize(){
        addFramesToGroup();
        setGameSpeed(DEFAULT_SPEED,true);
    }
    
    /**
     * remove and cleanup the existing game
     */
    public void clear(){
        myAnimation.stop();
        //other cleanup
    }
    
    private void addFramesToGroup(){
        for (GameObject s : myGameObjects){
            //myRootGroup.getChildren().add(s.getNode());
        }
    }
    
    /**
    *
    * change the game speed
    *
    * @param speed
    *            speed of the simulation
    * @param play
    *            whether the animation should play after the speed change
    */
   public void setGameSpeed(double speed, boolean play) {
       // set game loop
       KeyFrame frame = new KeyFrame(Duration.millis(1000 / speed), oneFrame);
       if (myAnimation == null) {
           myAnimation = new Timeline();
       }
       myAnimation.stop();
       myAnimation.setCycleCount(Animation.INDEFINITE);
       myAnimation.getKeyFrames().clear();
       myAnimation.getKeyFrames().add(frame);
       if (play) {
           myAnimation.play();
       }
   }
   
   /**
    * toggle the play/pause state of the game timeline
    */
   public void togglePause(){
       if (myAnimation.getStatus() == Animation.Status.RUNNING){
           myAnimation.pause();
       }
       else{
           myAnimation.play();
       }
   }
   
   
   /**
    * Function to do each game frame
    */
   private EventHandler<ActionEvent> oneFrame = new EventHandler<ActionEvent>() {
       @Override
       public void handle(ActionEvent evt) {
           processFrame();
       }
   };

    /**
     * run updates on the level
     */
    public void processFrame(){
        myLevelManager.update();
    }
    
    private void createLevelManager(){
        myLevelManager = new LevelManager(myLevels,myGameObjects,myGameConditions,myGameObjectRenderer,mySoundUtility);
    }
    
    public void load(GameData data){
    	myLevels=data.getLevels();
    	myGameConditions=data.getConditions();
    	myGameObjects=data.getGameObjects();
    }
    
    private void initializeConditions(){
        for (Condition c : myGameConditions){
            c.initialize(this);
        }
    }
    
    public GameObject objectForIdentifier(Identifier Id){
        //TODO: justify why this comes from the level manager
        return myLevelManager.objectForIdentifier(Id);
    }
    
    public GameData getGameData(){
    	return new GameData(myLevels, myGameConditions, myGameObjects);
    }
    
    public String getRelativePath(){
        return myRelativePath;
    }

    public GameObjectsCollection getAllGameObjects () {
        return myGameObjects;
    }
    
    public ConditionsCollection getAllConditions () {
        return myGameConditions;
    }
    
    public Renderer getRenderer () {
        return myGameObjectRenderer;
    }
    
    public LevelManager getLevelManager(){
    	return myLevelManager; 
    }
    
    public SoundUtility getSoundUtility () {
        return mySoundUtility;
    }
    
    public Group getGroup(){
    	return myRootGroup; 
    }
    
}
