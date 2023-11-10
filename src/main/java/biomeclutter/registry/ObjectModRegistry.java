package biomeclutter.registry;

import biomeclutter.object.CutTreeTrunkObject;
import biomeclutter.object.FallenTreeTrunkObject;
import biomeclutter.object.SmallSingleCactusObject;
import necesse.engine.registries.ObjectRegistry;
import necesse.level.gameObject.FruitBushObject;
import necesse.level.gameObject.GameObject;
import necesse.level.gameObject.SaplingObject;
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
        ObjectRegistry.registerObject("cutsprucetrunk", new CutTreeTrunkObject(spruceTree, "cutsprucetrunk", deadwood), 0F, false);
        ObjectRegistry.registerObject("cutwillowtrunk", new CutTreeTrunkObject(willowTree, "cutwillowtrunk", deadwood), 0F, false);
        ObjectRegistry.registerObject("cutpinetrunk", new CutTreeTrunkObject(pineTree, "cutpinetrunk", deadwood), 0F, false);

        ObjectRegistry.registerObject("surfacecactussmall", new SmallSingleCactusObject(), 0F, false);

        ObjectRegistry.registerObject("sweetberrybush", (new FruitBushObject("sweetberrybush", "sweetberrysapling", 900.0F, 1800.0F, "sweetberry", 1.0F, 2, new Color(115, 30, 30))).setDebrisColor(new Color(40, 87, 35)), 0.0F, false);
        ObjectRegistry.registerObject("sweetberrysapling", new SaplingObject("sweetberrysapling", "sweetberrybush", 1200, 2100, false), 30.0F, true);
    }

}
