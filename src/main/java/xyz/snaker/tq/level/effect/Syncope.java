package xyz.snaker.tq.level.effect;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.extensions.common.IClientMobEffectExtensions;
import org.jetbrains.annotations.NotNull;
import xyz.snaker.snakerlib.SnakerLib;
import xyz.snaker.snakerlib.utility.ResourcePath;

import java.util.function.Consumer;

/**
 * Created by SnakerBone on 6/07/2023
 **/
public class Syncope extends MobEffect
{
    public Syncope()
    {
        super(MobEffectCategory.NEUTRAL, SnakerLib.hexToInt("FF00FF"));
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity entity, int amplifier)
    {

    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier)
    {
        return duration > 0;
    }

    @Override
    public void initializeClient(Consumer<IClientMobEffectExtensions> consumer)
    {
        consumer.accept(new IClientMobEffectExtensions()
        {
            public static final ResourceLocation ICON = new ResourcePath("textures/mob_effect/syncope.png");

            @Override
            public boolean renderGuiIcon(MobEffectInstance instance, Gui gui, GuiGraphics graphics, int x, int y, float z, float alpha)
            {
                graphics.blit(ICON, x + 3, y + 3, 18, 18, 0, 0, 255, 255, 256, 256);
                return true;
            }
        });
    }
}
