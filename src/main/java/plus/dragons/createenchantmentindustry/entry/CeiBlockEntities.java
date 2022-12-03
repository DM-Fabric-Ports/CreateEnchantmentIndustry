package plus.dragons.createenchantmentindustry.entry;

import static plus.dragons.createenchantmentindustry.EnchantmentIndustry.REGISTRATE;

import com.tterrag.registrate.util.entry.BlockEntityEntry;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import plus.dragons.createenchantmentindustry.content.contraptions.enchanting.disenchanter.DisenchanterBlockEntity;
import plus.dragons.createenchantmentindustry.content.contraptions.enchanting.disenchanter.DisenchanterRenderer;
import plus.dragons.createenchantmentindustry.content.contraptions.enchanting.enchanter.BlazeEnchanterBlockEntity;
import plus.dragons.createenchantmentindustry.content.contraptions.enchanting.enchanter.BlazeEnchanterRenderer;
import plus.dragons.createenchantmentindustry.content.contraptions.enchanting.printer.PrinterBlockEntity;
import plus.dragons.createenchantmentindustry.content.contraptions.enchanting.printer.PrinterRenderer;

public class CeiBlockEntities {

        public static final BlockEntityEntry<DisenchanterBlockEntity> DISENCHANTER = REGISTRATE
                        .tileEntity("disenchanter", DisenchanterBlockEntity::new)
                        .validBlocks(CeiBlocks.DISENCHANTER)
                        .renderer(() -> DisenchanterRenderer::new)
                        .register();

        public static final BlockEntityEntry<PrinterBlockEntity> PRINTER = REGISTRATE
                        .tileEntity("printer", PrinterBlockEntity::new)
                        .validBlocks(CeiBlocks.PRINTER)
                        .renderer(() -> PrinterRenderer::new)
                        .register();

        public static final BlockEntityEntry<BlazeEnchanterBlockEntity> BLAZE_ENCHANTER = REGISTRATE
                        .tileEntity("blaze_enchanter", BlazeEnchanterBlockEntity::new)
                        .validBlocks(CeiBlocks.BLAZE_ENCHANTER)
                        .renderer(() -> BlazeEnchanterRenderer::new)
                        .register();

        // public static void remap(MissingMappingsEvent event) {
        // var mappings = event.getMappings(ForgeRegistries.Keys.BLOCK_ENTITY_TYPES,
        // EnchantmentIndustry.ID);
        // var remaps = ImmutableMap.<ResourceLocation, BlockEntityEntry<?>>builder()
        // .put(EnchantmentIndustry.genRL("copier_machine"), PRINTER)
        // .put(EnchantmentIndustry.genRL("blaze_enchanting_later"), BLAZE_ENCHANTER)
        // .build();
        // for (var mapping : mappings) {
        // var key = mapping.getKey();
        // var remap = remaps.get(key);
        // if (remap != null) {
        // mapping.remap(remap.get());
        // EnchantmentIndustry.LOGGER.warn("Remapping block entity [{}] to [{}]...",
        // key, remap.getId());
        // }
        // }
        // }

        public static void register() {

        }

        public static void registerTransfer() {
                ItemStorage.SIDED.registerForBlockEntity(DisenchanterBlockEntity::getItemStorage, DISENCHANTER.get());
                FluidStorage.SIDED.registerForBlockEntity(DisenchanterBlockEntity::getFluidStorage, DISENCHANTER.get());

                ItemStorage.SIDED.registerForBlockEntity(BlazeEnchanterBlockEntity::getItemStorage,
                                BLAZE_ENCHANTER.get());
                FluidStorage.SIDED.registerForBlockEntity(BlazeEnchanterBlockEntity::getFluidStorage,
                                BLAZE_ENCHANTER.get());

                FluidStorage.SIDED.registerForBlockEntity(PrinterBlockEntity::getStorage, PRINTER.get());
        }
}
