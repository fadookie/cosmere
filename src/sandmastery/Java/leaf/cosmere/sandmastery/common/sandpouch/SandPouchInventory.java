package leaf.cosmere.sandmastery.common.sandpouch;

import leaf.cosmere.sandmastery.common.items.SandPouchItem;
import leaf.cosmere.sandmastery.common.utils.MiscHelper;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SandPouchInventory  implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static final int size = 18;
    private final IItemHandler inv = new ItemStackHandler(size) {
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack)
        {
            return !stack.isEmpty() && SandPouchItem.SUPPORTED_ITEMS.test(stack);
        }
    };

    private final LazyOptional<IItemHandler> opt = LazyOptional.of(() -> inv);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
        return ForgeCapabilities.ITEM_HANDLER.orEmpty(capability, opt);
    }

    @Override
    public CompoundTag serializeNBT() {
        return ((ItemStackHandler) inv).serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        ((ItemStackHandler) inv).deserializeNBT(nbt);
    }

}
