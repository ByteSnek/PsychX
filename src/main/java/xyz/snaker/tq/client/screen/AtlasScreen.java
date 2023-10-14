package xyz.snaker.tq.client.screen;

import xyz.snaker.snakerlib.utility.ResourcePath;
import xyz.snaker.tq.client.render.entity.UtterflyRenderer;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import com.github.alexthe666.citadel.client.gui.GuiBasicBook;

/**
 * Created by SnakerBone on 14/10/2023
 **/
public class AtlasScreen extends GuiBasicBook
{
    public AtlasScreen(ItemStack stack)
    {
        super(stack, Component.translatable("atlas.tq.title"));
    }

    public void render(GuiGraphics guiGraphics, int x, int y, float partialTicks)
    {
        UtterflyRenderer.renderForAtlas = true;
        super.render(guiGraphics, x, y, partialTicks);
        UtterflyRenderer.renderForAtlas = true;
    }

    protected int getBindingColor()
    {
        return 0x606B26;
    }

    public ResourceLocation getRootPage()
    {
        return new ResourcePath("book/atlas/root.json");
    }

    public String getTextFileDirectory()
    {
        return new ResourcePath("book/atlas/").toString();
    }
}
