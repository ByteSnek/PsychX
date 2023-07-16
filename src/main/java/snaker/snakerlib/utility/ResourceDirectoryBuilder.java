package snaker.snakerlib.utility;

import net.minecraft.resources.ResourceLocation;

/**
 * Created by SnakerBone on 13/06/2023
 **/
public class ResourceDirectoryBuilder
{
    protected final String modId;
    protected String path;

    public ResourceDirectoryBuilder(String modId)
    {
        this.modId = modId;
    }

    public ResourceDirectoryBuilder blockstates()
    {
        path = "blockstates/";
        return this;
    }

    public ResourceDirectoryBuilder lang()
    {
        path = "lang/";
        return this;
    }

    public ResourceDirectoryBuilder geo()
    {
        path = "geo/";
        return this;
    }

    public ModelLocationBuilder models()
    {
        path = "models/";
        return new ModelLocationBuilder(modId);
    }

    public ResourceDirectoryBuilder particles()
    {
        path = "particles/";
        return this;
    }

    public ShaderLocationBuilder shaders()
    {
        path = "shaders/";
        return new ShaderLocationBuilder(modId);
    }

    public ResourceDirectoryBuilder sounds()
    {
        path = "sounds/";
        return this;
    }

    public TextureLocationBuilder textures()
    {
        path = "textures/";
        return new TextureLocationBuilder(modId);
    }

    public ResourceDirectoryBuilder custom(String pathOrPathToFile)
    {
        path = path + pathOrPathToFile;
        return this;
    }

    public ResourceLocation build()
    {
        return new ResourceLocation(modId, path);
    }

    public ResourceLocation build(String fileOrPathToFile)
    {
        return new ResourceLocation(modId, path + fileOrPathToFile);
    }

    public String getPath()
    {
        return path;
    }

    public static class ModelLocationBuilder extends ResourceDirectoryBuilder
    {
        public ModelLocationBuilder(String modId)
        {
            super(modId);
        }

        public ModelLocationBuilder block()
        {
            path = path + "block/";
            return this;
        }

        public ModelLocationBuilder item()
        {
            path = path + "item/";
            return this;
        }
    }

    public static class ShaderLocationBuilder extends ResourceDirectoryBuilder
    {
        public ShaderLocationBuilder(String modId)
        {
            super(modId);
        }

        public ShaderLocationBuilder core()
        {
            path = path + "core/";
            return this;
        }

        public ShaderLocationBuilder include()
        {
            path = path + "include/";
            return this;
        }

        public ShaderLocationBuilder post()
        {
            path = path + "post/";
            return this;
        }

        public ShaderLocationBuilder program()
        {
            path = path + "program/";
            return this;
        }
    }

    public static class TextureLocationBuilder extends ResourceDirectoryBuilder
    {

        public TextureLocationBuilder(String modId)
        {
            super(modId);
        }

        public TextureLocationBuilder block()
        {
            path = path + "block/";
            return this;
        }

        public TextureLocationBuilder colormap()
        {
            path = path + "colormap/";
            return this;
        }

        public TextureLocationBuilder effect()
        {
            path = path + "effect/";
            return this;
        }

        public TextureLocationBuilder entity()
        {
            path = path + "entity/";
            return this;
        }

        public TextureLocationBuilder environment()
        {
            path = path + "environment/";
            return this;
        }

        public TextureLocationBuilder font()
        {
            path = path + "font/";
            return this;
        }

        public TextureLocationBuilder gui()
        {
            path = path + "gui/";
            return this;
        }

        public TextureLocationBuilder item()
        {
            path = path + "item/";
            return this;
        }

        public TextureLocationBuilder map()
        {
            path = path + "map/";
            return this;
        }

        public TextureLocationBuilder misc()
        {
            path = path + "misc/";
            return this;
        }

        public TextureLocationBuilder mobEffect()
        {
            path = path + "mob_effect/";
            return this;
        }

        public TextureLocationBuilder armor()
        {
            path = path + "models/armor";
            return this;
        }

        public TextureLocationBuilder painting()
        {
            path = path + "painting/";
            return this;
        }

        public TextureLocationBuilder particle()
        {
            path = path + "particle/";
            return this;
        }

        public TextureLocationBuilder trims()
        {
            path = path + "trims/";
            return this;
        }
    }
}
