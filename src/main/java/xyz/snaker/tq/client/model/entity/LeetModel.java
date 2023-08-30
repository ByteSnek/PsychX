package xyz.snaker.tq.client.model.entity;

import xyz.snaker.snakerlib.math.Maths;
import xyz.snaker.snakerlib.utility.ResourcePath;
import xyz.snaker.tq.level.entity.mob.Leet;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

import org.jetbrains.annotations.NotNull;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

public class LeetModel extends EntityModel<Leet>
{
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourcePath("test"), "main");
    private final ModelPart base;


    public LeetModel(ModelPart root)
    {
        this.base = root.getChild("base");
    }

    public static LayerDefinition createBodyLayer()
    {
        MeshDefinition mesh = new MeshDefinition();

        PartDefinition root = mesh.getRoot();
        PartDefinition base = root.addOrReplaceChild("base", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        CubeListBuilder body = CubeListBuilder.create();
        CubeListBuilder outer = CubeListBuilder.create();

        for (int i = 0; i < 360; i++) {
            float bodyX = Maths.sin(i) * (i / 4F);
            float bodyZ = Maths.cos(i) * (i / 4F);

            float outerX = Maths.sin(i) * (i / 4F);
            float outerZ = Maths.cos(i) * (i / 4F);

            body.addBox(bodyX, 0, bodyZ, 1, 1, 1, CubeDeformation.NONE.extend(1));

            if (i > 270) {
                outer.addBox(outerX, 0, outerZ, 1, 1, 1, CubeDeformation.NONE.extend(3));
            }
        }

        base.addOrReplaceChild("body", body, PartPose.ZERO);
        base.addOrReplaceChild("outer", outer, PartPose.ZERO);

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(@NotNull Leet leet, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
        base.yRot = ageInTicks * Maths.DEGREES_TO_RADIANS * (leet.isCranky() ? 8 : 2);
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack stack, @NotNull VertexConsumer consumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
    {
        base.render(stack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
