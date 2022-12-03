package plus.dragons.createenchantmentindustry.foundation.mixin;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;

import com.simibubi.create.content.contraptions.fluids.OpenEndedPipe;
import io.github.fabricators_of_create.porting_lib.transfer.fluid.FluidTank;
import io.github.fabricators_of_create.porting_lib.util.FluidStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import plus.dragons.createenchantmentindustry.content.contraptions.fluids.experience.ExperienceFluid;

@Mixin(targets = "com.simibubi.create.content.contraptions.fluids.OpenEndedPipe$OpenEndFluidHandler")
public abstract class OpenEndFluidHandlerMixin extends FluidTank {
	public OpenEndFluidHandlerMixin(int capacity) {
		super(capacity);
	}

	@Final
	@Shadow(remap = false)
	OpenEndedPipe this$0;

	// Sadly, fluidStack in OpenEndedPipe#registerEffectHandler thing does not be provided as expected.
	// We intercept running before experience is handled;
	@Inject(method = "insert(Lnet/fabricmc/fabric/api/transfer/v1/fluid/FluidVariant;JLnet/fabricmc/fabric/api/transfer/v1/transaction/TransactionContext;)J",
			at = @At(value = "INVOKE",
					target = "Lio/github/fabricators_of_create/porting_lib/transfer/fluid/FluidTank;insert(Lnet/fabricmc/fabric/api/transfer/v1/fluid/FluidVariant;JLnet/fabricmc/fabric/api/transfer/v1/transaction/TransactionContext;)J",
					shift = At.Shift.BEFORE),
			remap = false, cancellable = true)
	@SuppressWarnings("UnstableApiUsage")
	private void injected(FluidVariant resource, long maxAmount, TransactionContext transaction, CallbackInfoReturnable<Long> cir) {
		if (resource.getFluid() instanceof ExperienceFluid expFluid) {
			long fill = super.insert(resource, maxAmount, transaction);
			var amount = getFluidAmount();
			if (amount != 0) {
				((OpenEndedPipeAccessor) this$0).invokeApplyEffects(new FluidStack(expFluid, amount));
				this.setFluid(FluidStack.EMPTY);
			}
			cir.setReturnValue(fill);
		}
	}

}
