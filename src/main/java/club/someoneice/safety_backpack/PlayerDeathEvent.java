package club.someoneice.safety_backpack;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

@Mod.EventBusSubscriber(modid = SafetyBackpack.MODID)
public final class PlayerDeathEvent {
    @SubscribeEvent
    public static void onPlayerDeathAndDrops(LivingDropsEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        List<ItemEntity> items = Lists.newArrayList();
        event.getDrops().forEach(it -> {
            if (it.getItem().is(SafetyBackpack.SAFETY_BACKPACK.get())) {
                player.addItem(it.getItem());
            } else items.add(it);
        });
        event.getDrops().clear();
        event.getDrops().addAll(items);
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        event.getOriginal().getInventory().items.forEach(it -> {
            if (it.isEmpty()) return;
            event.getEntity().addItem(it);
        });
    }
}
