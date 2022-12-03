package plus.dragons.createenchantmentindustry.foundation.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

@Mixin(ExperienceOrb.class)
public interface ExperienceOrbAccessor {
    @Invoker("tryMergeToExisting")
    static boolean invokeTryMergeToExisiting(ServerLevel serverLevel, Vec3 vec3, int i) {
        throw new AssertionError();
    }

    @Invoker("repairPlayerItems")
    int invokeRepairPlayerItems(Player player, int i);

    @Accessor("value")
    public void setValue(int value);

    @Accessor
    int getCount();

    @Accessor("count")
    public void setCount(int count);
}
