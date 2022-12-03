package plus.dragons.createenchantmentindustry.foundation.mixin;

import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;

@Mixin(ModelBakery.class)
public interface ModelBakeryAccessor {
    @Accessor("UNREFERENCED_TEXTURES")
    public static Set<Material> getUnreferencedTextures() {
        throw new AssertionError();
      }
}
