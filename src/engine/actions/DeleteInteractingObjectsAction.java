package engine.actions;

import java.util.Iterator;
import engine.GameManager;
import engine.gameObject.GameObject;
import engine.render.Renderer;

public class DeleteInteractingObjectsAction extends TypeAction {
    private transient Renderer myRenderer;
    
    public DeleteInteractingObjectsAction(String type){
            super(type);
            
    }
    
    @Override
    public void initialize(GameManager manager){
            super.initialize(manager);
            myRenderer = manager.getRenderer();
    }
    
    
    @Override
    public void execute() {
            for (Iterator<GameObject> it = myCurrentLevel.getGameObjectsCollection().iterator(); it.hasNext();){
                    GameObject object = it.next();
                    if (object.getIdentifier().getType().equals(myType) && object.isCollisionEnabled()){
                       
                            myRenderer.removeRenderedNode(object.getIdentifier());
                            it.remove();
                    }
            }
            
    }

}
