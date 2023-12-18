package xyz.snaker.tq.client.model.item;

import xyz.snaker.snakerlib.client.model.GenericSwordModel;
import xyz.snaker.snakerlib.resources.ResourceReference;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;

public class ComaCrystalSwordModel extends GenericSwordModel
{
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceReference("sword.json"), "main");

    public ComaCrystalSwordModel(ModelPart root)
    {
        super(root);
    }
}