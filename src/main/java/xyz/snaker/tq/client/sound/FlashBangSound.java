package xyz.snaker.tq.client.sound;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;

import xyz.snaker.tq.rego.Effects;
import xyz.snaker.tq.rego.Sounds;

/**
 * Created by SnakerBone on 1/11/2023
 **/
public class FlashBangSound extends AbstractTickableSoundInstance
{
    public FlashBangSound()
    {
        super(Sounds.FLARE_FLASHBANG.get(), SoundSource.MASTER, SoundInstance.createUnseededRandom());
        this.looping = true;
        this.attenuation = Attenuation.NONE;
        this.tick();
    }

    @Override
    public void tick()
    {
        Player player = Minecraft.getInstance().player;

        if (player != null && player.isAlive()) {
            MobEffectInstance effect = player.getEffect(Effects.FLASHBANG.get());

            if (effect != null) {
                x = (float) player.getX();
                y = (float) player.getY();
                z = (float) player.getZ();

                float percent = Math.min((effect.getDuration() / 450F), 1F);

                volume = percent * 10;

                return;
            }
        }

        stop();
    }
}
