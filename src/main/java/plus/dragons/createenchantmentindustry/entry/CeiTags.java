package plus.dragons.createenchantmentindustry.entry;

import static plus.dragons.createenchantmentindustry.EnchantmentIndustry.REGISTRATE;

import java.util.Arrays;
import java.util.Locale;

import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateItemTagsProvider;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import com.tterrag.registrate.util.nullness.NonNullFunction;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import plus.dragons.createenchantmentindustry.EnchantmentIndustry;

public interface CeiTags<T, P extends RegistrateTagsProvider<T>> {
    String FORGE = "forge";
    String CREATE = "create";

    TagKey<T> tag();

    boolean hasDatagen();

    default void datagen(P pov) {
        // NO-OP
    }

    static String toTagName(String enumName) {
        return enumName.replace('$', '/').toLowerCase(Locale.ROOT);
    }

    static <T extends Block, P> NonNullFunction<BlockBuilder<T, P>, ItemBuilder<BlockItem, BlockBuilder<T, P>>> tagBlockAndItem(
            String namespace, String... paths) {
        return block -> {
            ItemBuilder<BlockItem, BlockBuilder<T, P>> item = block.item();
            for (String path : paths) {
                block.tag(TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(namespace, path)));
                item.tag(TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(namespace, path)));
            }
            return item;
        };
    }

    static void register() {
        Arrays.stream(BlockTag.values())
                .filter(CeiTags::hasDatagen)
                .forEach(tag -> REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, tag::datagen));
        Arrays.stream(ItemTag.values())
                .filter(CeiTags::hasDatagen)
                .forEach(tag -> REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, tag::datagen));
        Arrays.stream(FluidTag.values())
                .filter(CeiTags::hasDatagen)
                .forEach(tag -> REGISTRATE.addDataGenerator(ProviderType.FLUID_TAGS, tag::datagen));
    }

    enum BlockTag implements CeiTags<Block, RegistrateTagsProvider<Block>> {
        ;

        final TagKey<Block> tag;
        final boolean datagen;

        BlockTag(String namespace, boolean datagen) {
            this.tag = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(namespace, toTagName(name())));
            this.datagen = datagen;
        }

        BlockTag(boolean datagen) {
            this(EnchantmentIndustry.ID, datagen);
        }

        @Override
        public TagKey<Block> tag() {
            return tag;
        }

        @Override
        public boolean hasDatagen() {
            return datagen;
        }
    }

    enum ItemTag implements CeiTags<Item, RegistrateItemTagsProvider> {
        INK_INGREDIENT(true) {
            @Override
            public void datagen(RegistrateItemTagsProvider pov) {
                pov.tag(tag).add(Items.BLACK_DYE, Items.WITHER_ROSE, Items.INK_SAC);
            }
        },
        UPRIGHT_ON_BELT(CREATE, true) {
            @Override
            public void datagen(RegistrateItemTagsProvider pov) {
                pov.tag(tag).add(Items.EXPERIENCE_BOTTLE);
            }
        };

        final TagKey<Item> tag;
        final boolean datagen;

        ItemTag(String namespace, boolean datagen) {
            this.tag = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(namespace, toTagName(name())));
            this.datagen = datagen;
        }

        ItemTag(boolean datagen) {
            this(EnchantmentIndustry.ID, datagen);
        }

        @Override
        public TagKey<Item> tag() {
            return tag;
        }

        @Override
        public boolean hasDatagen() {
            return datagen;
        }
    }

    enum FluidTag implements CeiTags<Fluid, RegistrateTagsProvider<Fluid>> {
        // No experience fluid tag here as different ratios is not acceptable
        INK(FORGE, false),
        BLAZE_ENCHANTER_INPUT(false),
        PRINTER_INPUT(true) {
            @Override
            public void datagen(RegistrateTagsProvider<Fluid> pov) {
                pov.tag(tag).addTag(INK.tag);
            }
        };

        final TagKey<Fluid> tag;
        final boolean datagen;

        FluidTag(String namespace, boolean datagen) {
            this.tag = TagKey.create(Registry.FLUID_REGISTRY, new ResourceLocation(namespace, toTagName(name())));
            this.datagen = datagen;
        }

        FluidTag(boolean datagen) {
            this(EnchantmentIndustry.ID, datagen);
        }

        @Override
        public TagKey<Fluid> tag() {
            return tag;
        }

        @Override
        public boolean hasDatagen() {
            return datagen;
        }
    }

}
