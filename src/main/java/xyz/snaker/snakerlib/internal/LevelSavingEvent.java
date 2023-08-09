package xyz.snaker.snakerlib.internal;

import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.Event;

/**
 * Created by SnakerBone on 25/06/2023
 * <p>
 * Similar to {@link LevelEvent.Unload} except doesn't fire when changing dimensions
 **/
public class LevelSavingEvent extends Event
{
    public LevelSavingEvent()
    {
        super();
    }
}
