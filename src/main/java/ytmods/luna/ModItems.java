package luna.soulsmp.item;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item SOUL = register("soul", new SoulItem(new Item.Settings().maxCount(1)));

    private static Item register(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier("soulsmp", name), item);
    }

    public static void registerModItems() {
        System.out.println("Registering items for Soul SMP");
    }
}
