package ytmods.luna;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Random;

public class SoulItem extends Item {
    public SoulItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (stack.getNbt() == null && entity instanceof PlayerEntity player) {
            assignSoul(stack, player);
        }

        if (entity instanceof PlayerEntity player && stack.getOrCreateNbt().getString("OwnerUUID").equals(player.getUuidAsString())) {
            applySoulEffects(stack, player);
        }
    }

    private void assignSoul(ItemStack stack, PlayerEntity player) {
        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.putString("OwnerUUID", player.getUuidAsString());
        nbt.putString("OwnerName", player.getName().getString());
        nbt.putString("Buff", getRandomBuff());
        nbt.putString("Debuff", getRandomDebuff());
        nbt.putString("Ability", getRandomAbility());
        stack.setCustomName(Text.literal(player.getName().getString() + "'s Soul"));
    }

    private void applySoulEffects(ItemStack stack, PlayerEntity player) {
        String buff = stack.getOrCreateNbt().getString("Buff");
        String debuff = stack.getOrCreateNbt().getString("Debuff");
        applyEffectFromString(buff, player, true);
        applyEffectFromString(debuff, player, false);
    }

    private void applyEffectFromString(String effectId, PlayerEntity player, boolean isBuff) {
        Identifier id = new Identifier("minecraft", effectId.replace("minecraft:", ""));
        RegistryEntry<StatusEffect> effectEntry = Registries.STATUS_EFFECT.getEntry(id);
        if (effectEntry != null && !player.hasStatusEffect(effectEntry)) {
            player.addStatusEffect(new StatusEffectInstance(effectEntry, 200, isBuff ? 1 : 0, true, false));
        }
    }

    private String getRandomBuff() {
        String[] buffs = {"minecraft:speed", "minecraft:strength", "minecraft:regeneration"};
        return buffs[new Random().nextInt(buffs.length)];
    }

    private String getRandomDebuff() {
        String[] debuffs = {"minecraft:slowness", "minecraft:weakness", "minecraft:mining_fatigue"};
        return debuffs[new Random().nextInt(debuffs.length)];
    }

    private String getRandomAbility() {
        String[] abilities = {"explode_on_hit", "summon_lightning", "teleport_randomly"};
        return abilities[new Random().nextInt(abilities.length)];
    }
}
