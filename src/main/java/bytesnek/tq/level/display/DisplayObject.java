package bytesnek.tq.level.display;

import java.util.List;
import java.util.function.Consumer;

import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import org.jetbrains.annotations.Nullable;

/**
 * Created by SnakerBone on 29/10/2023
 **/
public interface DisplayObject<T>
{
    List<T> getDisplays(@Nullable Level level);

    void setupRenderer(Consumer<IClientItemExtensions> consumer);
}
