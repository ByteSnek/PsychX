package xyz.snaker.tq.utility.level;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.minecraft.world.level.levelgen.flat.FlatLayerInfo;

import com.google.common.collect.Lists;

/**
 * Created by SnakerBone on 18/10/2023
 **/
public class FlatLayerList
{
    private final List<FlatLayer> list;

    private FlatLayerList(List<FlatLayer> listType)
    {
        this.list = listType;
    }

    public static FlatLayerList create()
    {
        return new FlatLayerList(Lists.newArrayList());
    }

    public static FlatLayerList create(List<FlatLayer> listType)
    {
        return new FlatLayerList(listType);
    }

    public boolean add(FlatLayer layer)
    {
        return list.add(layer);
    }

    public boolean addAll(Collection<FlatLayer> other)
    {
        return list.addAll(other);
    }

    public boolean addAll(FlatLayer... layers)
    {
        return list.addAll(Arrays.asList(layers));
    }

    public FlatLayer get(int index)
    {
        return list.get(index);
    }

    public FlatLayerInfo getInfo(int index)
    {
        return list.get(index).getInfo();
    }

    public int size()
    {
        return list.size();
    }

    public boolean isEmpty()
    {
        return list.isEmpty();
    }

    public List<FlatLayer> getList()
    {
        return Collections.unmodifiableList(list);
    }
}