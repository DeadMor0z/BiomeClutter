package biomeclutter.registry;

import biomeclutter.object.FallenTreeTrunkObject;
import necesse.engine.registries.ObjectRegistry;
import necesse.level.gameObject.TreeObject;

public class ObjectModRegistry {

    public static void registerObjects() {
        FallenTreeTrunkObject.registerTreeBark((TreeObject) ObjectRegistry.getObject("oaktree"), "fallenoaktrunk");
        FallenTreeTrunkObject.registerTreeBark((TreeObject) ObjectRegistry.getObject("sprucetree"), "fallensprucetrunk");
        FallenTreeTrunkObject.registerTreeBark((TreeObject) ObjectRegistry.getObject("willowtree"), "fallenwillowtrunk");
        FallenTreeTrunkObject.registerTreeBark((TreeObject) ObjectRegistry.getObject("pinetree"), "fallenpinetrunk");
    }

}
