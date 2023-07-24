package snaker.tq.datagen;

import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import snaker.tq.Tourniqueted;

/**
 * Created by SnakerBone on 21/03/2023
 **/
@Mod.EventBusSubscriber(modid = Tourniqueted.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators
{
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event)
    {
        event.getGenerator().addProvider(event.includeClient(), new DataProviders.Languages(event.getGenerator().getPackOutput()));
        event.getGenerator().addProvider(event.includeServer(), new DataProviders.BlockStates(event.getGenerator().getPackOutput(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(event.includeServer(), new DataProviders.ItemModels(event.getGenerator().getPackOutput(), event.getExistingFileHelper()));
    }
}
