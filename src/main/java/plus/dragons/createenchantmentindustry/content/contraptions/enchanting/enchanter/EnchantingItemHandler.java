package plus.dragons.createenchantmentindustry.content.contraptions.enchanting.enchanter;

import javax.annotation.ParametersAreNonnullByDefault;

import org.jetbrains.annotations.NotNull;

import com.simibubi.create.content.contraptions.relays.belt.transport.TransportedItemStack;

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class EnchantingItemHandler implements SingleSlotStorage<ItemVariant> {
    private final BlazeEnchanterBlockEntity be;
    private final Direction side;

    public EnchantingItemHandler(BlazeEnchanterBlockEntity be, Direction side) {
        this.be = be;
        this.side = side;
    }

    @Override
    @NotNull
    public long insert(ItemVariant resource, long maxAmount, TransactionContext transaction) {
        if (!be.getHeldItemStack()
                .isEmpty() || maxAmount <= 0)
            return 0;

        long returned = maxAmount > 64 ? 64 : maxAmount;
        ItemStack stack = resource.toStack((int) returned);

        if (Enchanting.getValidEnchantment(stack, be.targetItem, be.hyper()) != null) {
            returned = 1;
            stack = resource.toStack();
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
        return be.getHeldItemStack().getCount();
    }

    @Override
    public long getCapacity() {
        return 64;
    }
}
