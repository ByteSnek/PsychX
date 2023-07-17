package snaker.snakerlib.resources;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.jarjar.nio.pathfs.PathPath;
import org.jetbrains.annotations.NotNull;
import snaker.snakerlib.SnakerLib;
import snaker.snakerlib.utility.AestheticUtil;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;

/**
 * Created by SnakerBone on 17/07/2023
 **/
public class StrictResourceLocation implements Comparable<StrictResourceLocation>
{
    private final String namespace;
    private final String path;

    public StrictResourceLocation(String namespace, String path)
    {
        this.namespace = namespace;
        this.path = path;
    }

    public StrictResourceLocation(String path)
    {
        this.namespace = ResourceLocation.DEFAULT_NAMESPACE;
        this.path = path;
    }

    public static StrictResourceLocation fromResourceLocation(ResourceLocation in)
    {
        return new StrictResourceLocation(in.getNamespace(), in.getPath());
    }

    public ResourceLocation toResourceLocation()
    {
        return new ResourceLocation(namespace == null ? ResourceLocation.DEFAULT_NAMESPACE : namespace + ResourceLocation.NAMESPACE_SEPARATOR + path);
    }

    public String addOrCreateFile(String fileName)
    {
        return absolute().concat(PathPath.ROOT + fileName);
    }

    public String addOrCreateFile(String fileName, String fileType)
    {
        return absolute().concat(PathPath.ROOT + fileName + "." + fileType);
    }

    public String absolute()
    {
        return asFile().getAbsolutePath();
    }

    public Path asPath()
    {
        return asFile().toPath();
    }

    public File asFile()
    {
        return new File(asUri());
    }

    public URI asUri()
    {
        return AestheticUtil.urlToUri(asUrl());
    }

    public URL asUrl()
    {
        return base(namespace == null ? ResourceLocation.DEFAULT_NAMESPACE : namespace + PathPath.ROOT + path);
    }

    private URL base(String path)
    {
        return resource("assets" + PathPath.ROOT + path);
    }

    private URL resource(String path)
    {
        return callerClassLoader().getResource(path);
    }

    private ClassLoader callerClassLoader()
    {
        return SnakerLib.getCallerClassLoaderReference();
    }

    public String namespace()
    {
        return namespace;
    }

    public String path()
    {
        return path;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
        } else if (obj instanceof StrictResourceLocation other) {
            return namespace.equals(other.namespace) && path.equals(other.path);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode()
    {
        return namespace.hashCode() + path.hashCode();
    }

    @Override
    public String toString()
    {
        return absolute();
    }

    @Override
    public int compareTo(@NotNull StrictResourceLocation other)
    {
        int cPath = path.compareTo(other.path);
        int cNamespace = namespace.compareTo(other.namespace);
        cPath ^= (cPath == 0 ? cNamespace : 0);
        return cPath;
    }
}
