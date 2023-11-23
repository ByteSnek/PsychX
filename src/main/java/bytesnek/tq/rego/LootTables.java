package bytesnek.tq.rego;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import bytesnek.snakerlib.resources.ResourceReference;

import net.minecraft.resources.ResourceLocation;

/**
 * Created by SnakerBone on 17/10/2023
 **/
public class LootTables
{
    private static final Set<ResourceLocation> LOCATIONS = new HashSet<>();

    public static final ResourceLocation COSMO_RED = register("entities/cosmo/red");
    public static final ResourceLocation COSMO_GREEN = register("entities/cosmo/green");
    public static final ResourceLocation COSMO_BLUE = register("entities/cosmo/blue");
    public static final ResourceLocation COSMO_YELLOW = register("entities/cosmo/yellow");
    public static final ResourceLocation COSMO_PINK = register("entities/cosmo/pink");
    public static final ResourceLocation COSMO_PURPLE = register("entities/cosmo/purple");
    public static final ResourceLocation COSMO_ALPHA = register("entities/cosmo/alpha");

    static ResourceLocation register(String name)
    {
        ResourceReference path = new ResourceReference(name);

        if (LOCATIONS.add(path)) {
            return path;
        } else {
            throw new RuntimeException("LootTable '%s' has already been registered".formatted(name));
        }
    }

    public static Set<ResourceLocation> all()
    {
        return Collections.unmodifiableSet(LOCATIONS);
    }
}
