package biomeclutter;

import necesse.gfx.gameTexture.GameTexture;

public class ModResources {

    public static class Textures {
        public static GameTexture rollingtumbleweed;
        public static GameTexture rollingtumbleweed_shadow;

        // empty constructor for calling
        public Textures() {
        }


        public static void load() {
            rollingtumbleweed = fromFile("rollingtumbleweed");
            rollingtumbleweed_shadow = fromFile("rollingtumbleweed_shadow");
        }

        public static GameTexture fromFile(String path) {
            return GameTexture.fromFile("mobs/" + path);
        }

    }
}
