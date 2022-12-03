package plus.dragons.createenchantmentindustry.entry;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import plus.dragons.createenchantmentindustry.EnchantmentIndustry;
import plus.dragons.createenchantmentindustry.content.contraptions.fluids.experience.HyperExperienceBottle;
import plus.dragons.createenchantmentindustry.content.contraptions.fluids.experience.HyperExperienceOrb;
import plus.dragons.createenchantmentindustry.content.contraptions.fluids.experience.HyperExperienceOrbRenderer;

public class CeiEntityTypes {

    public static final EntityType<HyperExperienceOrb> HYPER_EXPERIENCE_ORB = Registry.register(Registry.ENTITY_TYPE,
            EnchantmentIndustry.genRL("hyper_experience_orb"),
            FabricEntityTypeBuilder.<HyperExperienceOrb>create(MobCategory.MISC, HyperExperienceOrb::new)
                    .trackRangeChunks(6).trackedUpdateRate(20).forceTrackedVelocityUpdates(true)
                    .dimensions(EntityDimensions.fixed(.5f, .5f)).build());

    public static final EntityType<HyperExperienceBottle> HYPER_EXPERIENCE_BOTTLE = Registry.register(
            Registry.ENTITY_TYPE,
            EnchantmentIndustry.genRL("hyper_experience_bottle"),
            FabricEntityTypeBuilder.<HyperExperienceBottle>create(MobCategory.MISC, HyperExperienceBottle::new)
                    .trackRangeChunks(4).trackedUpdateRate(10).forceTrackedVelocityUpdates(true)
                    .dimensions(EntityDimensions.fixed(.25f, .25f)).build());

    public static void register() {
    }

    public static void registerClient() {
        EntityRendererRegistry.register(HYPER_EXPERIENCE_ORB, HyperExperienceOrbRenderer::new);
        EntityRendererRegistry.register(HYPER_EXPERIENCE_BOTTLE, ThrownItemRenderer<HyperExperienceBottle>::new);
    }
}
