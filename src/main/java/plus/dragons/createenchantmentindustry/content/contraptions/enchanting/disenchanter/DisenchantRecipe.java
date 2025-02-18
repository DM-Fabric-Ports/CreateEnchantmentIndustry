package plus.dragons.createenchantmentindustry.content.contraptions.enchanting.disenchanter;

import com.simibubi.create.content.contraptions.processing.ProcessingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;

import io.github.fabricators_of_create.porting_lib.transfer.item.RecipeWrapper;
import io.github.fabricators_of_create.porting_lib.util.FluidStack;
import net.minecraft.world.level.Level;
import plus.dragons.createenchantmentindustry.entry.CeiFluids;
import plus.dragons.createenchantmentindustry.entry.CeiRecipeTypes;

public class DisenchantRecipe extends ProcessingRecipe<RecipeWrapper> {

    private final long experience;

    public DisenchantRecipe(ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        super(CeiRecipeTypes.DISENCHANTING, params);
        if (fluidResults.isEmpty())
            throw new IllegalArgumentException("Illegal Disenchanting Recipe: " + id.toString() + " has no fluid output!");
        FluidStack fluid = fluidResults.get(0);
        if (!fluid.getFluid().isSame(CeiFluids.EXPERIENCE.get().getSource()))
            throw new IllegalArgumentException("Illegal Disenchanting Recipe: " + id.toString() + " has wrong type of fluid output!");
        this.experience = fluid.getAmount();
    }

    @Override
    public boolean matches(RecipeWrapper inv, Level pLevel) {
        return ingredients.get(0).test(inv.getItem(0));
    }

    @Override
    protected int getMaxInputCount() {
        return 1;
    }

    @Override
    protected int getMaxOutputCount() {
        return 1;
    }

    @Override
    protected int getMaxFluidOutputCount() {
        return 1;
    }

    @Override
    protected boolean canSpecifyDuration() {
        return false;
    }

    public boolean hasNoResult() {
        return results.isEmpty();
    }

    public long getExperience() {
        return experience;
    }
}