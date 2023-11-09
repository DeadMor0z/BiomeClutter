package biomeclutter.registry;

import biomeclutter.object.CutTreeTrunkObject;
import biomeclutter.object.FallenTreeTrunkObject;
import necesse.engine.registries.ObjectRegistry;
import necesse.level.gameObject.GameObject;
import necesse.level.gameObject.TreeObject;

import java.awt.*;

public class ObjectModRegistry {

    public static void registerObjects() {
        TreeObject oakTree = (TreeObject) ObjectRegistry.getObject("oaktree");
        TreeObject spruceTree = (TreeObject) ObjectRegistry.getObject("sprucetree");
        TreeObject willowTree = (TreeObject) ObjectRegistry.getObject("willowtree");
        TreeObject pineTree = (TreeObject) ObjectRegistry.getObject("pinetree");
        Color deadwood = new Color(86, 69, 40);

        FallenTreeTrunkObject.registerTreeBark(oakTree, "fallenoaktrunk");
        FallenTreeTrunkObject.registerTreeBark(spruceTree, "fallensprucetrunk");
        FallenTreeTrunkObject.registerTreeBark(willowTree, "fallenwillowtrunk");
        FallenTreeTrunkObject.registerTreeBark(pineTree, "fallenpinetrunk");

        ObjectRegistry.registerObject("cutoaktrunk", new CutTreeTrunkObject(oakTree, "cutoaktrunk", deadwood), 0F, false);
        ObjectRegistry.registerObject("cutsprucetrunk", new CutTreeTrunkObject(oakTree, "cutsprucetrunk", deadwood), 0F, false);
        ObjectRegistry.registerObject("cutwillowtrunk", new CutTreeTrunkObject(oakTree, "cutwillowtrunk", deadwood), 0F, false);
        ObjectRegistry.registerObject("cutpinetrunk", new CutTreeTrunkObject(oakTree, "cutpinetrunk", deadwood), 0F, false);
    }

}
