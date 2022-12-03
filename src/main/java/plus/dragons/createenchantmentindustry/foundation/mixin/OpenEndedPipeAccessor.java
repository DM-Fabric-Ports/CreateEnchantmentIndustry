package plus.dragons.createenchantmentindustry.foundation.mixin;

import com.simibubi.create.content.contraptions.fluids.OpenEndedPipe;
import io.github.fabricators_of_create.porting_lib.util.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(OpenEndedPipe.class)
public interface OpenEndedPipeAccessor {

    @Invoker(remap = false)
    void invokeApplyEffects(FluidStack fluid);
}
