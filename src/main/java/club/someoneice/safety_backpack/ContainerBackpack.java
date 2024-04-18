package club.someoneice.safety_backpack;

import com.google.common.collect.Lists;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

import java.util.Arrays;
import java.util.List;

public final class ContainerBackpack extends AbstractContainerMenu {
    final ItemStack item;
    final Inventory inventory;
    public ContainerBackpack(int pContainerId, Inventory inv, FriendlyByteBuf data) {
        this(pContainerId, inv);
    }

    public ContainerBackpack(int id, Inventory inventory) {
        super(SafetyBackpack.BACKPACK.get(), id);

        this.item = inventory.player.getMainHandItem();
        this.inventory = inventory;
        if (!(item.getItem() instanceof ItemSafetyBackpack)) return;

        final List<Integer> posInventory = Lists.newArrayListWithExpectedSize(2);
        Arrays.stream(ConfigInventory.playerInventoryStartPos.toString().replaceAll(" ", "").split(",")).map(Integer::valueOf).forEach(posInventory::add);
        addPlayerHotbar(inventory, posInventory);
        addPlayerInventory(inventory, posInventory);

        item.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(it -> {
            for (int i = 0; i < ConfigInventory.slotPosList.size(); i ++) {
                final List<Integer> pos = Lists.newArrayListWithExpectedSize(2);
                Arrays.stream(ConfigInventory.slotPosList.get(i).replaceAll(" ", "").split(",")).map(Integer::valueOf).forEach(pos::add);
                this.addSlot(new SlotItemHandler(it, i, pos.get(0), pos.get(1)));
            }
        });
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot sourceSlot = this.slots.get(index);
        if (!sourceSlot.hasItem()) return ItemStack.EMPTY;
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        if (index < 35)
            if (!this.moveItemStackTo(sourceStack, 35, 35, false))
                return ItemStack.EMPTY;

        if (sourceStack.getCount() == 0) sourceSlot.set(ItemStack.EMPTY);
        else sourceSlot.setChanged();

        sourceSlot.onTake(player, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return !player.isDeadOrDying() && !player.hurtMarked && player.getMainHandItem() == this.item;
    }

    private void addPlayerInventory(Inventory inventory, List<Integer> pos) {
        for (int h = 0; h < 3; ++h) for (int l = 0; l < 9; ++l)
            this.addSlot(new Slot(inventory, l + h * 9 + 9, pos.get(0) + l * 18, pos.get(1) + h * 18));
    }

    private void addPlayerHotbar(Inventory inventory, List<Integer> pos) {
        for (int l = 0; l < 9; ++l) this.addSlot(new Slot(inventory, l, pos.get(0) + l * 18, pos.get(1) + 58));
    }
}