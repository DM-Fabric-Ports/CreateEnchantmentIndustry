package plus.dragons.createenchantmentindustry.entry;

import java.util.Locale;

import com.simibubi.create.Create;
import com.simibubi.create.foundation.utility.Lang;
import com.tterrag.registrate.providers.RegistrateTagsProvider;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import plus.dragons.createenchantmentindustry.EnchantmentIndustry;

public class CeiTags {
    public static <T> TagKey<T> conventionalTag(ResourceKey<Registry<T>> registry, String path) {
        return TagKey.create(registry, new ResourceLocation("c", path));
    }

    public static TagKey<Block> conventionalBlockTag(String path) {
        return conventionalTag(Registry.BLOCK_REGISTRY, path);
    }

    public static TagKey<Item> conventionalItemTag(String path) {
        return conventionalTag(Registry.ITEM_REGISTRY, path);
    }

    public static TagKey<Fluid> conventionalFluidTag(String path) {
        return conventionalTag(Registry.FLUID_REGISTRY, path);
    }

    String CONVENTIONAL = "c";
    String CREATE = "create";

    static String toTagName(String enumName) {
        return enumName.replace('$', '/').toLowerCase(Locale.ROOT);
    }

    public enum NameSpace {
        MOD(EnchantmentIndustry.ID, false, true),
        CREATE(Create.ID, false, true),
        FORGE("conventional");

        public final String id;
        public final boolean optionalDefault;
        public final boolean alwaysDatagenDefault;

        NameSpace(String id) {
            this(id, true, false);
        }

        NameSpace(String id, boolean optionalDefault, boolean alwaysDatagenDefault) {
            this.id = id;
            this.optionalDefault = optionalDefault;
            this.alwaysDatagenDefault = alwaysDatagenDefault;
        }
    }

    public enum BlockTag {
        ;

        public final TagKey<Block> tag;
        public final boolean alwaysDatagen;

        BlockTag() {
            this(NameSpace.MOD);
        }

        BlockTag(NameSpace namespace) {
            this(namespace, namespace.optionalDefault, namespace.alwaysDatagenDefault);
        }

        BlockTag(boolean alwaysDatagen) {
            this(NameSpace.MOD, NameSpace.MOD.optionalDefault, alwaysDatagen);
        }

        BlockTag(NameSpace namespace, boolean alwaysDatagen) {
            this(namespace, namespace.optionalDefault, alwaysDatagen);
        }

        BlockTag(NameSpace namespace, String path) {
            this(namespace, path, namespace.optionalDefault, namespace.alwaysDatagenDefault);
        }

        BlockTag(NameSpace namespace, boolean optional, boolean alwaysDatagen) {
            this(namespace, null, optional, alwaysDatagen);
        }

        BlockTag(NameSpace namespace, String path, boolean optional, boolean alwaysDatagen) {
            ResourceLocation id = new ResourceLocation(namespace.id, path == null ? Lang.asId(name()) : path);
            tag = TagKey.create(Registry.BLOCK_REGISTRY, id);
            this.alwaysDatagen = alwaysDatagen;
        }

        @SuppressWarnings("deprecation")
        public boolean matches(Block block) {
            return block.builtInRegistryHolder()
                    .is(tag);
        }

        public boolean matches(BlockState state) {
            return state.is(tag);
        }

        private static void init() {
        }
    }

    public enum ItemTag {
        INK_INGREDIENT(true),
        UPRIGHT_ON_BELT(NameSpace.CREATE, true);

        public final TagKey<Item> tag;
        public final boolean alwaysDatagen;

        ItemTag() {
            this(NameSpace.MOD);
        }

        ItemTag(NameSpace namespace) {
            this(namespace, namespace.optionalDefault, namespace.alwaysDatagenDefault);
        }

        ItemTag(boolean alwaysDatagen) {
            this(NameSpace.MOD, NameSpace.MOD.optionalDefault, alwaysDatagen);
        }

        ItemTag(NameSpace namespace, boolean alwaysDatagen) {
            this(namespace, namespace.optionalDefault, alwaysDatagen);
        }

        ItemTag(NameSpace namespace, String path) {
            this(namespace, path, namespace.optionalDefault, namespace.alwaysDatagenDefault);
        }

        ItemTag(NameSpace namespace, boolean optional, boolean alwaysDatagen) {
            this(namespace, null, optional, alwaysDatagen);
        }

        ItemTag(NameSpace namespace, String path, boolean optional, boolean alwaysDatagen) {
            ResourceLocation id = new ResourceLocation(namespace.id, path == null ? Lang.asId(name()) : path);
            tag = TagKey.create(Registry.ITEM_REGISTRY, id);
            this.alwaysDatagen = alwaysDatagen;
        }

        @SuppressWarnings("deprecation")
        public boolean matches(Item item) {
            return item.builtInRegistryHolder()
                    .is(tag);
        }

        public boolean matches(ItemStack stack) {
            return stack.is(tag);
        }

        private static void init() {
        }
    }

    public enum FluidTag {
        // No experience fluid tag here as different ratios is not acceptable
        INK(NameSpace.FORGE, false),
        BLAZE_ENCHANTER_INPUT(false),
        PRINTER_INPUT(true);

        public final TagKey<Fluid> tag;
        public final boolean alwaysDatagen;

        FluidTag() {
            this(NameSpace.MOD);
        }

        FluidTag(NameSpace namespace, boolean alwaysDatagen) {
            this(namespace, namespace.optionalDefault, alwaysDatagen);
        }

        FluidTag(boolean alwaysDatagen) {
            this(NameSpace.MOD, NameSpace.MOD.optionalDefault, alwaysDatagen);
        }

        FluidTag(NameSpace namespace) {
            this(namespace, namespace.optionalDefault, namespace.alwaysDatagenDefault);
        }

        FluidTag(NameSpace namespace, String path) {
            this(namespace, path, namespace.optionalDefault, namespace.alwaysDatagenDefault);
        }

        FluidTag(NameSpace namespace, boolean optional, boolean alwaysDatagen) {
            this(namespace, null, optional, alwaysDatagen);
        }

        FluidTag(NameSpace namespace, String path, boolean optional, boolean alwaysDatagen) {
            ResourceLocation id = new ResourceLocation(namespace.id, path == null ? Lang.asId(name()) : path);
            tag = TagKey.create(Registry.FLUID_REGISTRY, id);
            this.alwaysDatagen = alwaysDatagen;
        }

        @SuppressWarnings("deprecation")
        public boolean matches(Fluid fluid) {
            return fluid.is(tag);
        }

        public boolean matches(FluidState state) {
            return state.is(tag);
        }

        private static void init() {
        }
    }

    public static void register() {
        BlockTag.init();
        ItemTag.init();
        FluidTag.init();
    }

    public static void genFluidTag(RegistrateTagsProvider<Fluid> pov) {
        pov.tag(FluidTag.PRINTER_INPUT.tag).addTag(FluidTag.INK.tag);
    }

    public static void genItemTag(RegistrateTagsProvider<Item> pov) {
        pov.tag(ItemTag.INK_INGREDIENT.tag).add(Items.BLACK_DYE, Items.WITHER_ROSE, Items.INK_SAC);
        pov.tag(ItemTag.UPRIGHT_ON_BELT.tag).add(Items.EXPERIENCE_BOTTLE);
    }

}
