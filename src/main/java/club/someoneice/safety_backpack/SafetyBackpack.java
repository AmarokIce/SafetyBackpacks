package club.someoneice.safety_backpack;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(SafetyBackpack.MODID)
public final class SafetyBackpack {
    public static final String MODID = "safety_backpack";

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final RegistryObject<Item> SAFETY_BACKPACK = ITEMS.register("safety_Backpack", ItemSafetyBackpack::new);

    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID);
    public static final RegistryObject<MenuType<ContainerBackpack>> BACKPACK =
            MENUS.register("safety_backpack", () -> IForgeMenuType.create(ContainerBackpack::new));


    public SafetyBackpack() {
        var bus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(bus);
        MENUS.register(bus);
        bus.addListener(this::clientSetup);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        MenuScreens.register(BACKPACK.get(), GUIBackpack::new);
    }
}
