package plus.dragons.createenchantmentindustry.foundation.mixin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import com.simibubi.create.AllItems;
import com.simibubi.create.content.contraptions.components.deployer.DeployerFakePlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import plus.dragons.createenchantmentindustry.foundation.config.CeiConfigs;

@Mixin(value = DeployerFakePlayer.class, remap = false)
public class DeployerFakePlayerMixin {

	@Inject(method = "deployerKillsDoNotSpawnXP", at = @At("HEAD"))
	private static void deployerKillsSpawnXpNuggets(int xp, Player rawPlayer, CallbackInfoReturnable<Integer> cir) {
		if (xp <= 0 || !(rawPlayer instanceof DeployerFakePlayer player)) return;
		if (player.getRandom().nextFloat() > CeiConfigs.SERVER.deployerXpDropChance.getF())
			return;
		int amount = xp / 3 + (player.getRandom().nextInt(3) < xp % 3 ? 1 : 0);
		if (amount <= 0) return;
		Item nugget = AllItems.EXP_NUGGET.get();
		int maxStackSize = nugget.getMaxStackSize();
		for (int i = amount / maxStackSize; i > 0; --i) {
			player.getInventory().placeItemBackInInventory(new ItemStack(nugget, maxStackSize));
		}
		amount %= maxStackSize;
		if (amount > 0)
			player.getInventory().placeItemBackInInventory(new ItemStack(nugget, amount));
	}

}
