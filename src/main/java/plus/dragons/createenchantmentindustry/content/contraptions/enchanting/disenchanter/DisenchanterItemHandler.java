package plus.dragons.createenchantmentindustry.content.contraptions.enchanting.disenchanter;

import org.jetbrains.annotations.NotNull;

import com.simibubi.create.content.contraptions.relays.belt.transport.TransportedItemStack;

import io.github.fabricators_of_create.porting_lib.transfer.item.ItemHandlerHelper;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;

public class DisenchanterItemHandler implements SingleSlotStorage<ItemVariant> {
    private DisenchanterBlockEntity be;
    private Direction side;

    public DisenchanterItemHandler(DisenchanterBlockEntity be, Direction side) {
        this.be = be;
        this.side = side;
    }

    @Override
    @NotNull
    public long insert(ItemVariant resource, long maxAmount, TransactionContext transaction) {
        if (!be.getHeldItemStack().isEmpty())
            return 0;

        int amount = maxAmount > 64 ? 64 : (int) maxAmount;
        ItemStack stack = resource.toStack(amount);

        ItemStack disenchanted = Disenchanting.disenchantAndInsert(be, stack);
        if (!ItemStack.matches(stack, disenchanted)) {
            return disenchanted.getCount();
        }

        long returned = amount;
        if (stack.getCount() > 1 && Disenchanting.disenchantResult(stack, be.getLevel()) != null) {
            returned = 1;
            stack = ItemHandlerHelper.copyStackWithSize(stack, 1);
        }

        TransportedItemStack heldItem = new TransportedItemStack(stack);
        heldItem.prevBeltPosition = 0;
        be.setHeldItem(heldItem, side.getOpposite());
        be.notifyUpdate();

        return returned;
    }

    @Override
    @NotNull
    public long extract(ItemVariant resource, long maxAmount, TransactionContext transaction) {
        TransportedItemStack held = be.heldItem;
        if (held == null)
            return 0;

        ItemStack stack = held.stack.copy();
        int amount = maxAmount > held.stack.getCount() ? held.stack.getCount() : (int) maxAmount;
        ItemStack extracted = stack.split(amount);
        be.heldItem.stack = stack;
        if (stack.isEmpty())
            be.heldItem = null;
        be.notifyUpdate();
        return extracted.getCount();
    }

    @Override
    public boolean isResourceBlank() {
        return this.getResource().isBlank();
    }

    @Override
    public ItemVariant getResource() {
        return be.getHeldItemStack().isEmpty() ? ItemVariant.blank() : ItemVariant.of(be.getHeldItemStack());
    }

    @Override
    public long getAmount() {
        return be.heldItem.stack.getCount();
    }

    @Override
    public long getCapacity() {
        return 64;
    }
}
