package club.someoneice.safety_backpack.asm;

import net.minecraft.entity.player.EntityPlayer;
import net.tclproject.mysteriumlib.asm.annotations.EnumReturnSetting;
import net.tclproject.mysteriumlib.asm.annotations.Fix;
import net.tclproject.mysteriumlib.asm.annotations.FixOrder;

public class PlayerASM {
    @Fix(allThatExtend = true, order = FixOrder.USUAL, returnSetting = EnumReturnSetting.NEVER)
    public static void clonePlayer(EntityPlayer thiz, EntityPlayer player, boolean flag) {
        thiz.inventory.copyInventory(player.inventory);
    }
}
