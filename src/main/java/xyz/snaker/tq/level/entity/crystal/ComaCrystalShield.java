package xyz.snaker.tq.level.entity.crystal;

import net.minecraft.sounds.SoundEvent;

import xyz.snaker.tq.rego.Sounds;

/**
 * Created by SnakerBone on 22/11/2023
 **/
public class ComaCrystalShield
{
    private float health;
    private final CrystalSounds crystalSounds;

    public ComaCrystalShield(float health)
    {
        this.health = health;
        this.crystalSounds = new CrystalSounds(
                Sounds.COMA_CRYSTAL_SHOOT.get(),
                Sounds.COMA_CRYSTAL_SHIELD.get(),
                Sounds.COMA_CRYSTAL_HIT.get(),
                Sounds.COMA_CRYSTAL_DEPLETE.get(),
                Sounds.COMA_CRYSTAL_BEAM.get()
        );
    }

    public CrystalSounds getCrystalSounds()
    {
        return crystalSounds;
    }

    public boolean hasHealth()
    {
        return health > 0;
    }

    public boolean hasNoHealth()
    {
        return health <= 0;
    }

    public float getHealth()
    {
        return health;
    }

    public void setHealth(float health)
    {
        this.health = health;
    }

    public float addHealth(float value)
    {
        return health += value;
    }

    public float subHealth(float value)
    {
        return health -= value;
    }

    public record CrystalSounds(SoundEvent shoot, SoundEvent shield, SoundEvent hit, SoundEvent deplete, SoundEvent beam) {}
}
