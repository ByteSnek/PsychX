package xyz.snaker.tq.client.sound;

import java.lang.reflect.Field;
import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import xyz.snaker.hiss.sneaky.Reflection;
import xyz.snaker.snakerlib.resources.ResourceLocations;
import xyz.snaker.tq.rego.Effects;
import xyz.snaker.tq.rego.Sounds;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.resources.sounds.TickableSoundInstance;
import net.minecraft.client.sounds.ChannelAccess;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.sound.PlaySoundEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.TickEvent;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by SnakerBone on 1/11/2023
 **/
public class SoundModification
{
    private static SoundModification handler;
    private final Map<SoundInstance, Float> activeSoundVolumes = new ConcurrentHashMap<>();
    private Field playingSoundsField;
    private SoundEngine soundEngine;
    private FlashBangSound sound;

    private boolean isFlashBanged;

    public static void intialize()
    {
        if (handler == null) {
            handler = new SoundModification();
        }

        NeoForge.EVENT_BUS.register(handler);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event)
    {
        if (event.phase == TickEvent.Phase.START || Minecraft.getInstance().player == null || soundEngine == null) {
            return;
        }

        MobEffectInstance effect = Minecraft.getInstance().player.getEffect(Effects.FLASHBANG.get());

        if (effect == null) {
            if (!isFlashBanged) {
                return;
            }
        }

        if (sound == null || !Minecraft.getInstance().getSoundManager().isActive(sound)) {
            sound = new FlashBangSound();
            Minecraft.getInstance().getSoundManager().play(sound);
            return;
        }

        Map<SoundInstance, ChannelAccess.ChannelHandle> playingSounds = Reflection.getFieldDirect(SoundEngine.class, "instanceToChannel", soundEngine);

        if (effect != null) {
            try {
                playingSounds.forEach((sound, handle) ->
                {
                    if (sound == null || sound instanceof TickableSoundInstance || isFlashBangActive(sound.getSound().getLocation())) {
                        return;
                    }

                    float volume = sound instanceof ScheduledSoundMuter muted ? muted.getVolumeInitial() : sound.getVolume();
                    activeSoundVolumes.put(sound, volume);
                    handle.execute(soundSource -> soundSource.setVolume(getMutedVolume(effect.getDuration(), volume)));
                });

            } catch (ConcurrentModificationException ignored) {

            }

            isFlashBanged = true;
        } else if (isFlashBanged) {
            isFlashBanged = false;

            for (Map.Entry<SoundInstance, Float> entry : activeSoundVolumes.entrySet()) {
                ChannelAccess.ChannelHandle handle = playingSounds.get(entry.getKey());

                if (handle != null) {
                    handle.execute(soundSource -> soundSource.setVolume(entry.getValue()));
                }
            }

            activeSoundVolumes.clear();
        }
    }

    @SubscribeEvent
    public void onSoundPlay(PlaySoundEvent event)
    {
        if (soundEngine == null) {
            soundEngine = event.getEngine();
        }

        if (!isFlashBanged || Minecraft.getInstance().player == null || event.getSound() instanceof TickableSoundInstance) {
            return;
        }

        ResourceLocation location = Objects.requireNonNull(event.getSound()).getLocation();
        MobEffectInstance effect = Minecraft.getInstance().player.getEffect(Effects.FLASHBANG.get());

        int duration = effect != null ? effect.getDuration() : 0;
        boolean flashBangActive = isFlashBangActive(location);

        if (duration == 0 && flashBangActive) {
            return;
        }

        event.getSound().resolve(Minecraft.getInstance().getSoundManager());
        event.setSound(new ScheduledSoundMuter(event.getSound(), duration, flashBangActive));
    }

    private boolean isFlashBangActive(ResourceLocation location)
    {
        return location == ResourceLocations.SOUND_EVENT.getResourceLocation(Sounds.FLARE_FLASHBANG.get());
    }

    private float getMutedVolume(float duration, float volumeBase)
    {
        float volumeMin = volumeBase * 1;
        float percent = Math.min(duration, 1);

        return volumeMin + (1 - percent) * (volumeBase - volumeMin);
    }

    public static class ScheduledSoundMuter implements SoundInstance
    {
        private final SoundInstance instance;
        private final float volume;
        private float volumeInitial;

        public ScheduledSoundMuter(SoundInstance instance, int duration, boolean isFlashBangActive)
        {
            this.instance = instance;
            this.volumeInitial = Mth.clamp(instance.getVolume(), 0, 1);
            this.volume = handler.getMutedVolume(duration, this.volumeInitial);

            if (isFlashBangActive) {
                this.volumeInitial = volume;
            }
        }

        @Override
        public float getVolume()
        {
            return volume;
        }

        public float getVolumeInitial()
        {
            return volumeInitial;
        }

        @Override
        public @NotNull ResourceLocation getLocation()
        {
            return instance.getLocation();
        }

        @Override
        @Nullable
        public WeighedSoundEvents resolve(@NotNull SoundManager handler)
        {
            return instance.resolve(handler);
        }

        @Override
        public @NotNull Sound getSound()
        {
            return instance.getSound();
        }

        @Override
        public @NotNull SoundSource getSource()
        {
            return instance.getSource();
        }

        @Override
        public boolean isLooping()
        {
            return instance.isLooping();
        }

        @Override
        public boolean isRelative()
        {
            return false;
        }

        @Override
        public int getDelay()
        {
            return instance.getDelay();
        }

        @Override
        public float getPitch()
        {
            return instance.getPitch();
        }

        @Override
        public double getX()
        {
            return instance.getX();
        }

        @Override
        public double getY()
        {
            return instance.getY();
        }

        @Override
        public double getZ()
        {
            return instance.getZ();
        }

        @Override
        public SoundInstance.@NotNull Attenuation getAttenuation()
        {
            return instance.getAttenuation();
        }
    }
}
