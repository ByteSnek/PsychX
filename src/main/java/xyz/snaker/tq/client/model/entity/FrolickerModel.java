package xyz.snaker.tq.client.model.entity;

import xyz.snaker.hiss.math.Maths;
import xyz.snaker.snakerlib.resources.ResourceReference;
import xyz.snaker.tq.level.entity.creature.Frolicker;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import org.jetbrains.annotations.NotNull;

public class FrolickerModel extends EntityModel<Frolicker>
{
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceReference("frolicker"), "main");
    private final ModelPart base;

    public FrolickerModel(ModelPart root)
    {
        this.base = root.getChild("base");
    }

    public static LayerDefinition createBodyLayer()
    {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition base = root.addOrReplaceChild("base", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition head = base.addOrReplaceChild("head", CubeListBuilder.create().texOffs(16, 15).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -5.0F));

        base.addOrReplaceChild("pervex", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -5.0F, 2.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        head.addOrReplaceChild("antenna", CubeListBuilder.create().texOffs(0, 0).addBox(-0.8F, -3.5F, -6.5F, 0.5F, 1.5F, 0.5F, new CubeDeformation(0.0F)).texOffs(2, 0).addBox(-0.8F, -3.5F, -6.0F, 0.5F, 0.5F, 0.5F, new CubeDeformation(0.0F)).texOffs(0, 2).addBox(0.2F, -3.0F, -6.5F, 0.5F, 1.0F, 0.5F, new CubeDeformation(0.0F)).texOffs(2, 1).addBox(0.2F, -3.0F, -6.0F, 0.5F, 0.5F, 0.5F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 5.0F));
        base.addOrReplaceChild("leftWing", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -1.0F, -2.5F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(17, 0).addBox(-5.0F, -1.0F, -2.5F, 5.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -1.0F, -2.5F));
        base.addOrReplaceChild("rightWing", CubeListBuilder.create().texOffs(0, 15).addBox(0.0F, -1.0F, -2.5F, 5.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(0, 6).addBox(5.0F, -1.0F, -2.5F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -1.0F, -2.5F));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(@NotNull Frolicker frolicker, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
        if (frolicker.isActuallyOnGround()) {
            base.getChild("leftWing").zRot = Maths.toRad(10 + Maths.cos(ageInTicks / 16) * 20);
            base.getChild("rightWing").zRot = Maths.toRad(-10 - Maths.cos(ageInTicks / 16) * 20);
        } else {
            base.getChild("leftWing").zRot = Maths.sin(ageInTicks / 8) / 1.35F;
            base.getChild("rightWing").zRot = Maths.sin(-ageInTicks / 8) / 1.35F;
        }
        base.getChild("head").xRot = headPitch * Maths.DEGREES_TO_RADIANS;
        // I think the calculation on this is extremely inaccurate xD
        // I think it looks fine in game (I guess??)
        base.getChild("head").yRot = (netHeadYaw * -Maths.DEGREES_TO_RADIANS) / Maths.PI;
        base.getChild("head").zRot = netHeadYaw * Maths.DEGREES_TO_RADIANS;
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack stack, @NotNull VertexConsumer consumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
    {
        base.render(stack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}