package club.someoneice.safety_backpack;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public final class Handler implements IGuiHandler {
    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        return id == 0 ? new BackpackGui(player.inventory) : null;
    }

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        return id == 0 ? new BackpackContainer(player.inventory) : null;
    }
}
