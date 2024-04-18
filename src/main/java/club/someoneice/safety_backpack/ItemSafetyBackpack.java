package club.someoneice.safety_backpack;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


public class ItemSafetyBackpack extends Item {
    ItemSafetyBackpack() {
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.tabTools);
        this.setUnlocalizedName("safety_backpack");
        this.setTextureName(Blocks.chest.getItemIconName());

        GameRegistry.registerItem(this, "safety_backpack", SafetyBackpack.MODID);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
        if (!world.isRemote) player.openGui(SafetyBackpack.INSTANCE, 0, world, player.serverPosX, player.serverPosY, player.serverPosZ);
        return item;
    }


    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }
}
