package plus.dragons.createenchantmentindustry.content.contraptions.fluids;

import java.util.ArrayList;

import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.behaviour.BehaviourType;
import com.simibubi.create.foundation.tileEntity.behaviour.fluid.SmartFluidTankBehaviour;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import plus.dragons.createenchantmentindustry.foundation.mixin.TankSegmentAccessor;

public class FilteringFluidTankBehaviour extends SmartFluidTankBehaviour {

    protected final FluidFilter filter;

    @FunctionalInterface
    public static interface FluidFilter {
        boolean test(FluidVariant variant, long amount);
    }

    @SuppressWarnings("unchecked")
    public FilteringFluidTankBehaviour(BehaviourType<SmartFluidTankBehaviour> type,
            FluidFilter filter,
            SmartTileEntity te,
            int tanks, int tankCapacity,
            boolean enforceVariety) {
        super(type, te, tanks, tankCapacity, enforceVariety);
        this.filter = filter;
        ArrayList<SingleVariantStorage<FluidVariant>> handlers = new ArrayList<>();
        for (int i = 0; i < tanks; i++) {
            TankSegment tankSegment = new TankSegment(tankCapacity);
            this.tanks[i] = tankSegment;
            handlers.set(i, ((TankSegmentAccessor) tankSegment).getTank());
        }
        this.capability = new InternalFluidHandler((SingleVariantStorage<FluidVariant>[]) handlers.toArray(), enforceVariety);
    }

    public static FilteringFluidTankBehaviour single(FluidFilter filter, SmartTileEntity te, int capacity) {
        return new FilteringFluidTankBehaviour(TYPE, filter, te, 1, capacity, false);
    }

    public class InternalFluidHandler extends SmartFluidTankBehaviour.InternalFluidHandler {

        public InternalFluidHandler(SingleVariantStorage<FluidVariant>[] handlers, boolean enforceVariety) {
            super(handlers, enforceVariety);
        }

        @Override
        public long insert(FluidVariant resource, long maxAmount, TransactionContext transaction) {
            if (!filter.test(resource, maxAmount))
                return 0;
            return super.insert(resource, maxAmount, transaction);
        }

    }

}
