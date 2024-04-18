package club.someoneice.safety_backpack;

import club.someoneice.pineapplepsychic.inventory.SimpleInventory;
import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public final class BackpackContainer extends Container {
    private final SimpleInventory inventory;
    private final ItemStack itemIn;

    public BackpackContainer(InventoryPlayer player) {
        this.itemIn = player.getCurrentItem();

        final int maxSize = ConfigInventory.slotPosList.size();
        this.inventory = SafetyBackpack.getInvFromItemStack(itemIn, maxSize);

        for (int i = 0; i < maxSize; i++) {
            final List<Integer> pos = Lists.newArrayListWithExpectedSize(2);
            Arrays.stream(ConfigInventory.slotPosList.get(i).replaceAll(" ", "").split(",")).map(Integer::valueOf).forEach(pos::add);
            this.addSlotToContainer(new Slot(inventory, i, pos.get(0), pos.get(1)));
        }

        final List<Integer> pos = Lists.newArrayListWithExpectedSize(2);
        Arrays.stream(ConfigInventory.playerInventoryStartPos.toString().replaceAll(" ", "").split(",")).map(Integer::valueOf).forEach(pos::add);
        for (int h = 0; h < 3; ++h) for (int l = 0; l < 9; ++l) this.addSlotToContainer(new Slot(player, l + h * 9 + 9, pos.get(0) + l * 18, pos.get(1) + h * 18));
        for (int l = 0; l < 9; ++l) this.addSlotToContainer(new Slot(player, l, pos.get(0) + l * 18, pos.get(1) + 58));
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);
        // SafetyBackpack.setInvFromItemStack(this.itemIn, this.inventory);
        SafetyBackpack.setInvFromItemStack(player.getHeldItem(), this.inventory);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        // Why ?
        // return this.itemIn != null && !player.isDead && player.getHeldItem() == this.itemIn;
        return this.itemIn != null && player.getHeldItem() != null && player.getHeldItem().getItem() == this.itemIn.getItem() && !player.isDead;
    }

    public ItemStack transferStackInSlot(EntityPlayer player, int slotNumber) {
        Slot slot = (Slot) this.inventorySlots.get(slotNumber);
        if (slot == null || !slot.getHasStack()) return null;
        ItemStack item = slot.getStack();
        if (item == this.itemIn) return item;
        return mergeItemStackInPlayer(slotNumber, item, slot);
    }

    public ItemStack mergeItemStackInPlayer(int slotNumber, ItemStack item, Slot slot) {
        if (slotNumber < 27 + this.inventory.getSizeInventory()) {
            if (!this.mergeItemStack(item, 27 + this.inventory.getSizeInventory(), this.inventorySlots.size(), false))
                return null;
        }
        else if (!this.mergeItemStack(item, this.inventory.getSizeInventory(), 27 + this.inventory.getSizeInventory(), false)) return null;

        if (item.stackSize == 0) slot.putStack(null);
        else slot.onSlotChanged();

        return item;
    }
}
