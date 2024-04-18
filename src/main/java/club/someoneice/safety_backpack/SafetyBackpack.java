package club.someoneice.safety_backpack;

import club.someoneice.pineapplepsychic.inventory.SimpleInventory;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@Mod(modid = SafetyBackpack.MODID, name = SafetyBackpack.NAME, useMetadata = true)
public final class SafetyBackpack {
    public static final String MODID = "safety_backpack";
    public static final String NAME = "Safety Backpack";
    public static final String VERSION = "0.0.1";

    public static final Logger LOG = LogManager.getLogger(NAME);

    public static ItemSafetyBackpack ITEM_SAFETY_BACKPACK;

    @Mod.Instance(MODID)
    static SafetyBackpack INSTANCE;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) throws IOException {
        INSTANCE = this;

        ConfigInventory.configInit();
        ITEM_SAFETY_BACKPACK = new ItemSafetyBackpack();

        registryEvent(new PlayerDeathEvent());
    }

    @Mod.EventHandler
    public void commonInit(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new Handler());
    }

    @SuppressWarnings("all")
    static SimpleInventory getInvFromItemStack(ItemStack item, int size) {
        SimpleInventory inventory = new SimpleInventory(size);
        if (item == null || item.stackTagCompound == null) return inventory;
        if (item.getTagCompound().hasKey("inv_data"))
            inventory.load(item.getTagCompound().getCompoundTag("inv_data"));
        return inventory;
    }

    @SuppressWarnings("all")
    static void setInvFromItemStack(ItemStack item, SimpleInventory inventory) {
        if (item == null) return;
        if (item.stackTagCompound == null) item.stackTagCompound = new NBTTagCompound();
        item.stackTagCompound.setTag("inv_data", inventory.write());
    }

    private void registryEvent(Object eventObj) {
        MinecraftForge.EVENT_BUS.register(eventObj);
        FMLCommonHandler.instance().bus().register(eventObj);
    }
}
