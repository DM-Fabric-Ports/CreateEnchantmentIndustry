package plus.dragons.createenchantmentindustry.content.contraptions.enchanting.enchanter;

import com.simibubi.create.foundation.utility.Pair;

import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;
import plus.dragons.createenchantmentindustry.EnchantmentIndustry;
import plus.dragons.createenchantmentindustry.foundation.config.CeiConfigs;

public class EnchantmentEntry extends Pair<Enchantment, Integer> {

    public static final TagKey<Enchantment> HYPER_ENCHANTABLE = TagKey.create(Registry.ENCHANTMENT_REGISTRY,
            EnchantmentIndustry.genRL("hyper_enchantable"));

    protected EnchantmentEntry(Enchantment first, Integer second) {
        super(first, second);
    }

    public static EnchantmentEntry of(Enchantment enchantment, Integer level) {
        return new EnchantmentEntry(enchantment, level);
    }

    public static EnchantmentEntry of(Enchantment enchantment, int level) {
        return new EnchantmentEntry(enchantment, level);
    }

    public boolean valid() {
        var enchantment = getFirst();
        int level = getSecond();
        int maxLevel = enchantment.getMaxLevel();
        if (enchantment.getMaxLevel() == 1 && level > 1 &&
                !Registry.ENCHANTMENT.getTag(HYPER_ENCHANTABLE).stream()
                        .anyMatch(e -> e.stream().anyMatch(h -> h.value() == enchantment)))
            return false;
        return level <= maxLevel + CeiConfigs.SERVER.maxHyperEnchantingLevelExtension.get();
    }

}
