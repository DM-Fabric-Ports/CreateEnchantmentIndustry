package plus.dragons.createenchantmentindustry;

import com.simibubi.create.foundation.config.ui.BaseConfigScreen;

import io.github.fabricators_of_create.porting_lib.event.client.FogEvents;
import io.github.fabricators_of_create.porting_lib.event.client.ModelLoadCallback;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import plus.dragons.createenchantmentindustry.content.contraptions.enchanting.enchanter.BlazeEnchanterRenderer;
import plus.dragons.createenchantmentindustry.content.contraptions.fluids.ink.InkRenderingCamera;
import plus.dragons.createenchantmentindustry.entry.CeiBlockPartials;
import plus.dragons.createenchantmentindustry.entry.CeiEntityTypes;
import plus.dragons.createenchantmentindustry.entry.CeiPackets;
import plus.dragons.createenchantmentindustry.foundation.config.CeiConfigs;
import plus.dragons.createenchantmentindustry.foundation.ponder.content.CeiPonderIndex;

public class EnchantmentIndustryClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        CeiEntityTypes.registerClient();
        CeiBlockPartials.register();
        registerEvents();
        CeiPackets.channel.initClientListener();
        CeiPonderIndex.register();
        CeiPonderIndex.registerTags();

        BaseConfigScreen.setDefaultActionFor(EnchantmentIndustry.ID, screen -> screen
                .withTitles(null, null, "Gameplay Settings")
                .withSpecs(null, null, CeiConfigs.SERVER_SPEC));
    }

    private void registerEvents() {
        FogEvents.SET_COLOR.register(InkRenderingCamera::handleInkFogColor);
        ModelLoadCallback.EVENT.register(this::modelRegistry);
    }

    public void modelRegistry(ResourceManager manager, BlockColors colors, ProfilerFiller profiler, int mipLevel) {
        ModelBakery.UNREFERENCED_TEXTURES.add(BlazeEnchanterRenderer.BOOK_MATERIAL);
    }

}
