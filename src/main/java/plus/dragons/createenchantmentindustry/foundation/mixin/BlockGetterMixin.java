package plus.dragons.createenchantmentindustry.foundation.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import plus.dragons.createenchantmentindustry.content.contraptions.enchanting.enchanter.BlazeEnchanterBlock;

@Mixin(BlockGetter.class)
public class BlockGetterMixin {

	@Inject(method = "getLightEmission", at = @At("HEAD"), cancellable = true)
	private void getLightEmission(BlockPos blockPos, CallbackInfoReturnable<Integer> cir) {
		if (((BlockGetter) this).getBlockState(blockPos).getBlock() instanceof BlazeEnchanterBlock block)
			cir.setReturnValue(
					block.getLightEmission(((BlockGetter) this).getBlockState(blockPos), (BlockGetter) this, blockPos));
	}
}
