package xyz.snaker.tq.client.model.entity;

import xyz.snaker.snakerlib.math.Maths;
import xyz.snaker.snakerlib.utility.MiscStuff;
import xyz.snaker.snakerlib.utility.ResourcePath;
import xyz.snaker.tq.level.entity.boss.AntiCosmo;

import org.jetbrains.annotations.NotNull;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

/**
 * Created by SnakerBone on 18/06/2023
 **/
public class AntiCosmoModel extends EntityModel<AntiCosmo>
{
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourcePath("anti_cosmo"), "main");

    private final ModelPart base;

    public AntiCosmoModel(ModelPart root)
    {
        this.base = root.getChild("base");
    }

    public static LayerDefinition createBodyLayer()
    {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition base = root.addOrReplaceChild("base", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition top = base.addOrReplaceChild("top", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition bottom = base.addOrReplaceChild("bottom", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        top.addOrReplaceChild("upperInner1", CubeListBuilder.create().texOffs(25, 0).mirror().addBox(-0.5F, -12.5F, -0.5F, 1.0F, 25.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, -20.2035F, -2.7523F, 0.2618F, 0.0F, 0.0F));
        top.addOrReplaceChild("upperInner2", CubeListBuilder.create().texOffs(30, 0).mirror().addBox(-0.5F, -12.5F, -0.5F, 1.0F, 25.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, -19.9447F, 3.7182F, -0.2618F, 0.0F, 0.0F));
        top.addOrReplaceChild("upperInner3", CubeListBuilder.create().texOffs(35, 0).mirror().addBox(-0.5F, -12.5F, -0.5F, 1.0F, 25.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.7182F, -19.9447F, 0.5F, 0.0F, 0.0F, 0.2618F));
        top.addOrReplaceChild("upperInner4", CubeListBuilder.create().texOffs(40, 0).mirror().addBox(-0.5F, -12.5F, -0.5F, 1.0F, 25.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.7523F, -20.2035F, 0.5F, 0.0F, 0.0F, -0.2618F));

        top.addOrReplaceChild("upperOuter1", CubeListBuilder.create().texOffs(5, 0).mirror().addBox(-0.5F, -9.75F, -0.067F, 1.0F, 20.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, -16.4103F, 7.433F, -0.5236F, 0.0F, 0.0F));
        top.addOrReplaceChild("upperOuter2", CubeListBuilder.create().texOffs(10, 0).mirror().addBox(-0.732F, -10.134F, -0.5F, 1.0F, 20.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-6.299F, -15.9102F, 0.5F, 0.0F, 0.0F, -0.5236F));
        top.addOrReplaceChild("upperOuter3", CubeListBuilder.create().texOffs(15, 0).mirror().addBox(0.0196F, -9.7F, -0.5F, 1.0F, 20.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(7.433F, -16.4103F, 0.5F, 0.0F, 0.0F, 0.5236F));
        top.addOrReplaceChild("upperOuter4", CubeListBuilder.create().texOffs(20, 0).mirror().addBox(-0.5F, -9.134F, -1.0F, 1.0F, 20.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, -16.9103F, -6.567F, 0.5236F, 0.0F, 0.0F));

        base.addOrReplaceChild("centre", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-0.5F, -15.0F, -0.5F, 1.0F, 30.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.5F, -23.0F, 0.5F));

        bottom.addOrReplaceChild("lowerInner1", CubeListBuilder.create().texOffs(0, 35).mirror().addBox(-0.5F, -4.0F, -0.5F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, -4.2657F, 1.5182F, 0.2618F, 0.0F, 0.0F));
        bottom.addOrReplaceChild("lowerInner2", CubeListBuilder.create().texOffs(5, 35).mirror().addBox(-0.5F, -4.0F, -0.5F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, -4.0069F, -0.5523F, -0.2618F, 0.0F, 0.0F));
        bottom.addOrReplaceChild("lowerInner3", CubeListBuilder.create().texOffs(10, 35).mirror().addBox(-0.5F, -4.0F, -0.5F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5523F, -4.0069F, 0.5F, 0.0F, 0.0F, 0.2618F));
        bottom.addOrReplaceChild("lowerInner4", CubeListBuilder.create().texOffs(15, 35).mirror().addBox(-1.1196F, -3.8725F, -0.5F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0837F, -4.5492F, 0.5F, 0.0F, 0.0F, -0.2618F));

        bottom.addOrReplaceChild("lowerOuter1", CubeListBuilder.create().texOffs(0, 45).mirror().addBox(-0.5F, -3.4F, -0.5F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.433F, -4.7859F, 0.5F, 0.0F, 0.0F, -0.5236F));
        bottom.addOrReplaceChild("lowerOuter2", CubeListBuilder.create().texOffs(5, 45).mirror().addBox(-0.5F, -4.0F, -0.5F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.567F, -4.2859F, 0.5F, 0.0F, 0.0F, 0.5236F));
        bottom.addOrReplaceChild("lowerOuter3", CubeListBuilder.create().texOffs(10, 45).mirror().addBox(-0.5F, -4.0F, -0.5F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, -4.2859F, -3.567F, -0.5236F, 0.0F, 0.0F));
        bottom.addOrReplaceChild("lowerOuter4", CubeListBuilder.create().texOffs(15, 45).mirror().addBox(-0.5F, -3.5F, -0.5F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, -4.7859F, 4.433F, 0.5236F, 0.0F, 0.0F));

        return LayerDefinition.create(mesh, 128, 128);
    }

    @Override
    public void setupAnim(@NotNull AntiCosmo cosmo, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
        double speed = 1.35;
        base.getChild("top").y = Maths.sin(ageInTicks / 2) / 2;
        base.getChild("centre").yRot -= Maths.sin(ageInTicks * Math.PI * Math.PI) / 360;
        if (MiscStuff.isEntityMoving(cosmo)) {
            base.y = (Maths.sin(ageInTicks * speed) * Maths.E) + 22;
            base.getChild("bottom").getChild("lowerOuter1").zRot = (Maths.sin(ageInTicks * speed) / Maths.E) - 0.6F;
            base.getChild("bottom").getChild("lowerOuter2").zRot = -(Maths.sin(ageInTicks * speed) / Maths.E) + 0.6F;
            base.getChild("bottom").getChild("lowerOuter3").xRot = (Maths.sin(ageInTicks * speed) / Maths.E) - 0.6F;
            base.getChild("bottom").getChild("lowerOuter4").xRot = -(Maths.sin(ageInTicks * speed) / Maths.E) + 0.6F;
        } else {
            base.y = 24.0F;
            base.getChild("bottom").getChild("lowerOuter1").zRot = -0.5236F;
            base.getChild("bottom").getChild("lowerOuter2").zRot = 0.5236F;
            base.getChild("bottom").getChild("lowerOuter3").xRot = -0.5236F;
            base.getChild("bottom").getChild("lowerOuter4").xRot = 0.5236F;
        }
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack stack, @NotNull VertexConsumer consumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
    {
        base.render(stack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
