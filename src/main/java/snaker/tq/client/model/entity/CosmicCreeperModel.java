package snaker.tq.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import org.jetbrains.annotations.NotNull;
import snaker.snakerlib.math.Maths;
import snaker.tq.level.entity.mob.CosmicCreeper;
import snaker.tq.utility.ResourcePath;

public class CosmicCreeperModel extends EntityModel<CosmicCreeper>
{
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourcePath("cosmic_creeper"), "main");
    private final ModelPart base;

    public CosmicCreeperModel(ModelPart root)
    {
        this.base = root.getChild("base");
    }

    public static LayerDefinition createBodyLayer()
    {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition base = root.addOrReplaceChild("base", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        base.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -18.0F, 0.0F));
        base.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -18.0F, 0.0F));
        base.addOrReplaceChild("legFrontRight", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -6.0F, -4.0F));
        base.addOrReplaceChild("legFrontLeft", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -6.0F, -4.0F));
        base.addOrReplaceChild("legBackRight", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -6.0F, 4.0F));
        base.addOrReplaceChild("legBackLeft", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -6.0F, 4.0F));

        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    public void setupAnim(@NotNull CosmicCreeper entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
        base.getChild("head").yRot = netHeadYaw * Maths.DEGREES_TO_RADIANS;
        base.getChild("head").xRot = headPitch * Maths.DEGREES_TO_RADIANS;
        base.getChild("legBackRight").xRot = net.minecraft.util.Mth.cos(limbSwing * 0.6F) * 1.4F * limbSwingAmount;
        base.getChild("legBackLeft").xRot = net.minecraft.util.Mth.cos(limbSwing * 0.6F + Maths.PI) * 1.4F * limbSwingAmount;
        base.getChild("legFrontRight").xRot = net.minecraft.util.Mth.cos(limbSwing * 0.6F + Maths.PI) * 1.4F * limbSwingAmount;
        base.getChild("legFrontLeft").xRot = net.minecraft.util.Mth.cos(limbSwing * 0.6F) * 1.4F * limbSwingAmount;
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack stack, @NotNull VertexConsumer consumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
    {
        base.render(stack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}