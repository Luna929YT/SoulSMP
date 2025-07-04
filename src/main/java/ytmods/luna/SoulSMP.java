package luna.soulsmp;

import luna.soulsmp.item.ModItems;
import net.fabricmc.api.ModInitializer;

public class SoulSMP implements ModInitializer {
    public static final String MOD_ID = "soulsmp";

    @Override
    public void onInitialize() {
        ModItems.registerModItems();
    }
}
