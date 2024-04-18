package club.someoneice.safety_backpack;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public final class BackpackGui extends GuiContainer {
    private final ResourceLocation tex = new ResourceLocation(SafetyBackpack.MODID ,"textures/gui/safety_backpack.png");
    public BackpackGui(InventoryPlayer player) {
        super(new BackpackContainer(player));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(tex);
        int foo = (this.width - this.xSize) / 2;
        int bar = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(foo, bar, 0, 0, this.xSize, this.ySize);
    }
}
