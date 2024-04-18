package club.someoneice.safety_backpack;

import com.google.common.collect.Lists;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;

import java.util.List;

public final class PlayerDeathEvent {
    @SubscribeEvent
    public void onPlayerDropItems(PlayerDropsEvent event) {
        // For sync safe, create a new list to hold items, then replace all.
        List<EntityItem> newInventory = Lists.newArrayListWithExpectedSize(event.drops.size());
        EntityPlayer player = event.entityPlayer;

        event.drops.forEach(it -> {
            if (!(it.getEntityItem().getItem() instanceof ItemSafetyBackpack)) newInventory.add(it);
            else player.inventory.addItemStackToInventory(it.getEntityItem());
        });

        event.drops.clear();
        event.drops.addAll(newInventory);
    }
}
