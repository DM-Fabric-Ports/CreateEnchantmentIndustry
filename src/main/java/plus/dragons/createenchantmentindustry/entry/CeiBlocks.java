package plus.dragons.createenchantmentindustry.entry;

import static plus.dragons.createenchantmentindustry.EnchantmentIndustry.REGISTRATE;

import com.simibubi.create.Create;
import com.simibubi.create.content.AllSections;
import com.simibubi.create.content.contraptions.components.AssemblyOperatorBlockItem;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;

import plus.dragons.createenchantmentindustry.content.contraptions.enchanting.disenchanter.DisenchanterBlock;
import plus.dragons.createenchantmentindustry.content.contraptions.enchanting.enchanter.BlazeEnchanterBlock;
import plus.dragons.createenchantmentindustry.content.contraptions.enchanting.printer.PrinterBlock;


public class CeiBlocks {
    
    static {
        REGISTRATE.creativeModeTab(() -> Create.BASE_CREATIVE_TAB).startSection(AllSections.KINETICS);
    }

    public static final BlockEntry<DisenchanterBlock> DISENCHANTER = REGISTRATE
            .block("disenchanter", DisenchanterBlock::new)
            .initialProperties(SharedProperties::copperMetal)
            .transform(TagGen.pickaxeOnly())
            .blockstate((ctx, pov) -> pov.simpleBlock(ctx.get(), AssetLookup.standardModel(ctx, pov)))
            .simpleItem()
            .register();

    public static final BlockEntry<PrinterBlock> PRINTER = REGISTRATE
            .block("printer", PrinterBlock::new)
            .initialProperties(SharedProperties::copperMetal)
            .transform(TagGen.pickaxeOnly())
            .blockstate((ctx, pov) -> pov.simpleBlock(ctx.get(), AssetLookup.partialBaseModel(ctx, pov)))
            .item(AssemblyOperatorBlockItem::new)
            .model(AssetLookup::customItemModel)
            .build()
            .register();

    public static final BlockEntry<BlazeEnchanterBlock> BLAZE_ENCHANTER = REGISTRATE
            .block("blaze_enchanter", BlazeEnchanterBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .transform(TagGen.pickaxeOnly())
            .blockstate((ctx, pov) -> pov.simpleBlock(ctx.get(), AssetLookup.standardModel(ctx, pov)))
            .register();
    
    // public static void remap(MissingMappingsEvent event) {
    //     var mappings = event.getMappings(ForgeRegistries.Keys.BLOCKS, EnchantmentIndustry.ID);
    //     var remaps = ImmutableMap.<ResourceLocation, BlockEntry<?>>builder()
    //         .put(EnchantmentIndustry.genRL("copier"), PRINTER)
    //         .build();
    //     for (var mapping : mappings) {
    //         var key = mapping.getKey();
    //         var remap = remaps.get(key);
    //         if (remap != null) {
    //             mapping.remap(remap.get());
    //             EnchantmentIndustry.LOGGER.warn("Remapping block [{}] to [{}]...", key, remap.getId());
    //         }
    //     }
    // }

    public static void register() {}
    
}
