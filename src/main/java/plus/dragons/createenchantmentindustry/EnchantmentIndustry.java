package plus.dragons.createenchantmentindustry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.github.fabricators_of_create.porting_lib.event.common.LivingEntityEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.resources.ResourceLocation;
import plus.dragons.createdragonlib.advancement.AdvancementFactory;
import plus.dragons.createdragonlib.init.FillCreateItemGroupEvent;
import plus.dragons.createdragonlib.init.SafeRegistrate;
import plus.dragons.createdragonlib.lang.Lang;
import plus.dragons.createdragonlib.lang.LangFactory;
import plus.dragons.createenchantmentindustry.content.contraptions.fluids.OpenEndedPipeEffects;
import plus.dragons.createenchantmentindustry.entry.CeiBlockEntities;
import plus.dragons.createenchantmentindustry.entry.CeiBlocks;
import plus.dragons.createenchantmentindustry.entry.CeiContainerTypes;
import plus.dragons.createenchantmentindustry.entry.CeiEntityTypes;
import plus.dragons.createenchantmentindustry.entry.CeiFluids;
import plus.dragons.createenchantmentindustry.entry.CeiItems;
import plus.dragons.createenchantmentindustry.entry.CeiPackets;
import plus.dragons.createenchantmentindustry.entry.CeiRecipeTypes;
import plus.dragons.createenchantmentindustry.entry.CeiTags;
import plus.dragons.createenchantmentindustry.foundation.advancement.CeiAdvancements;
import plus.dragons.createenchantmentindustry.foundation.config.CeiConfigs;
import plus.dragons.createenchantmentindustry.foundation.ponder.content.CeiPonderIndex;

public class EnchantmentIndustry implements ModInitializer, DataGeneratorEntrypoint {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String NAME = "Create: Enchantment Industry";
    public static final String ID = "create_enchantment_industry";
    public static final SafeRegistrate REGISTRATE = new SafeRegistrate(ID);
    public static final Lang LANG = new Lang(ID);
    public static final AdvancementFactory ADVANCEMENT_FACTORY = AdvancementFactory.create(NAME, ID,
            CeiAdvancements::register);
    private static final LangFactory LANG_FACTORY = LangFactory.create(NAME, ID)
            .advancements(CeiAdvancements::register)
            .ponders(() -> {
                CeiPonderIndex.register();
                CeiPonderIndex.registerTags();
            })
            .tooltips()
            .ui();

    @Override
    public void onInitialize() {
        CeiConfigs.register();
        registerEntries();
        registerEvents();
        CeiAdvancements.register();
        CeiPackets.registerPackets();
        CeiPackets.channel.initServerListener();
        CeiFluids.registerLavaReaction();
        OpenEndedPipeEffects.register();
        CeiBlockEntities.registerTransfer();
    }

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        ADVANCEMENT_FACTORY.datagen(fabricDataGenerator);
        LANG_FACTORY.datagen(fabricDataGenerator);
    }

    private void registerEntries() {
        CeiBlocks.register();
        CeiBlockEntities.register();
        CeiContainerTypes.register();
        CeiEntityTypes.register();
        CeiFluids.register();
        CeiItems.register();
        CeiRecipeTypes.register();
        CeiTags.register();
        REGISTRATE.register();
    }

    private void registerEvents() {
        FillCreateItemGroupEvent.INSTANCE.register(CeiItems::fillCreateItemGroup);
        LivingEntityEvents.TICK.register(CeiFluids::handleInkEffect);
    }

    public static ResourceLocation genRL(String name) {
        return new ResourceLocation(ID, name);
    }

}
