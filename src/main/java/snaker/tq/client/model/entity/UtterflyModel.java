package snaker.tq.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import org.jetbrains.annotations.NotNull;
import snaker.snakerlib.math.Mh;
import snaker.snakerlib.resources.Identifier;
import snaker.tq.level.entity.boss.Utterfly;

/**
 * Created by SnakerBone on 4/01/2023
 **/
public class UtterflyModel extends EntityModel<Utterfly>
{
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new Identifier("utterfly"), "main");

    private final ModelPart base;

    public UtterflyModel(ModelPart root)
    {
        this.base = root.getChild("base");
    }

    public static LayerDefinition createBodyLayer()
    {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition base = root.addOrReplaceChild("base", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        base.addOrReplaceChild("leftWingLower", CubeListBuilder.create().texOffs(19, 12).addBox(-11.0F, -1.0F, -9.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(38, 9).addBox(-7.5F, -1.0F, -7.0F, 6.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(0, 18).addBox(-4.5F, -1.0F, 1.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(0, 23).addBox(-9.5F, -1.0F, 3.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        base.addOrReplaceChild("rightWingLower", CubeListBuilder.create().texOffs(19, 6).addBox(6.0F, -1.0F, -9.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(28, 37).addBox(1.5F, -1.0F, -7.0F, 6.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(0, 10).addBox(1.5F, -1.0F, 1.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(19, 0).addBox(4.5F, -1.0F, 3.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        base.addOrReplaceChild("leftWingUpper", CubeListBuilder.create().texOffs(19, 9).addBox(-11.0F, -2.0F, -9.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(38, 0).addBox(-7.5F, -2.0F, -7.0F, 6.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(-4.5F, -2.0F, 1.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(19, 19).addBox(-9.5F, -2.0F, 3.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        base.addOrReplaceChild("rightWingUpper", CubeListBuilder.create().texOffs(19, 3).addBox(6.0F, -2.0F, -9.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 37).addBox(1.5F, -2.0F, -7.0F, 6.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(0, 5).addBox(1.5F, -2.0F, 1.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(19, 22).addBox(4.5F, -2.0F, 3.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        base.addOrReplaceChild("pervex", CubeListBuilder.create().texOffs(38, 19).addBox(-0.5F, -1.5F, -8.0F, 1.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(0.5F, -1.0F, -7.0F, 1.0F, 1.0F, 17.0F, new CubeDeformation(0.0F)).texOffs(19, 1).addBox(0.5F, -2.0F, -7.0F, 1.0F, 1.0F, 17.0F, new CubeDeformation(0.0F)).texOffs(0, 18).addBox(-1.5F, -1.0F, -7.0F, 1.0F, 1.0F, 17.0F, new CubeDeformation(0.0F)).texOffs(19, 19).addBox(-1.5F, -2.0F, -7.0F, 1.0F, 1.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(mesh, 128, 128);
    }

    @Override
    public void setupAnim(@NotNull Utterfly utterfly, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
        base.getChild("leftWingLower").zRot = Mh.sin(ageInTicks / 5) / 3.3F - 0.3F;
        base.getChild("rightWingLower").zRot = Mh.sin(-ageInTicks / 5) / 3.3F + 0.3F;
        base.getChild("leftWingUpper").zRot = Mh.sin(ageInTicks / 5) / -3.3F + 0.3F;
        base.getChild("rightWingUpper").zRot = Mh.sin(-ageInTicks / 5) / -3.3F - 0.3F;
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack stack, @NotNull VertexConsumer consumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
    {
        base.render(stack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
