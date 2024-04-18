package club.someoneice.safety_backpack;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ItemSafetyBackpack extends Item {
    public ItemSafetyBackpack() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        var item = player.getItemInHand(hand);

        if (!world.isClientSide) NetworkHooks.openScreen((ServerPlayer) player, new SimpleMenuProvider((int var1, Inventory var2, Player var3) -> new ContainerBackpack(var1, var2), Component.empty()));
        // TODO Open GUI
        return InteractionResultHolder.success(item);
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack item, CompoundTag nbt) {
        super.initCapabilities(item, nbt);
        return new CapabilityHandle();
    }

    @Override
    public CompoundTag getShareTag(ItemStack item) {
        var result = Objects.requireNonNullElse(super.getShareTag(item), new CompoundTag());
        item.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(it -> result.put("items", ((ItemStackHandler) it).serializeNBT()));
        return result;
    }

    @Override
    public void readShareTag(ItemStack stack, @javax.annotation.Nullable CompoundTag nbt) {
        super.readShareTag(stack, nbt);
        if (nbt != null)
            stack.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(it ->
                    ((ItemStackHandler) it).deserializeNBT(nbt.getCompound("items")));
    }

    public static class CapabilityHandle implements ICapabilitySerializable<CompoundTag> {
        private final LazyOptional<ItemStackHandler> handler;

        public CapabilityHandle() {
            handler = LazyOptional.of(() -> new ItemStackHandler(9) {
                @Override
                public int getSlotLimit(int slot) {
                    return 1;
                }
            });
        }

        @NotNull
        @Override
        public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @org.jetbrains.annotations.Nullable Direction side) {
            return cap == ForgeCapabilities.ITEM_HANDLER ? handler.cast() : LazyOptional.empty();
        }

        @Override
        public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
            return cap == ForgeCapabilities.ITEM_HANDLER ? handler.cast() : LazyOptional.empty();
        }

        private ItemStackHandler getItemHandler() {
            return handler.orElseThrow(RuntimeException::new);
        }

        @Override
        public CompoundTag serializeNBT() {
            return getItemHandler().serializeNBT();
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            getItemHandler().deserializeNBT(nbt);
        }
    }
}

