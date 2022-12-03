package plus.dragons.createenchantmentindustry.foundation.config;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.api.ModLoadingContext;
import net.minecraftforge.api.fml.event.config.ModConfigEvents;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import plus.dragons.createenchantmentindustry.EnchantmentIndustry;

public class CeiConfigs {

    public static CeiServerConfig SERVER;
    public static ForgeConfigSpec SERVER_SPEC;

    public static void register() {
        Pair<CeiServerConfig, ForgeConfigSpec> serverConfigPair = new ForgeConfigSpec.Builder().configure(builder -> {
            CeiServerConfig config = new CeiServerConfig();
            config.registerAll(builder);
            return config;
        });
        SERVER = serverConfigPair.getKey();
        SERVER_SPEC = serverConfigPair.getValue();
        ModLoadingContext.registerConfig(EnchantmentIndustry.ID, ModConfig.Type.SERVER, SERVER_SPEC);
    }

    public static void onLoad() {
        ModConfigEvents.loading(EnchantmentIndustry.ID).register(config -> {
            if (SERVER_SPEC == config.getSpec())
                SERVER.onLoad();
        });
    }

    public static void onReload() {
        ModConfigEvents.reloading(EnchantmentIndustry.ID).register(config -> {
            if (SERVER_SPEC == config.getSpec())
                SERVER.onReload();
        });
    }

}
