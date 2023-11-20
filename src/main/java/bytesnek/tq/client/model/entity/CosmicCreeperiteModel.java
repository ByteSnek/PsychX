package bytesnek.tq.client.model.entity;

import xyz.snaker.snakerlib.math.Maths;
import xyz.snaker.snakerlib.resources.ResourceReference;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import org.jetbrains.annotations.NotNull;

import bytesnek.tq.level.entity.mob.CosmicCreeperite;

public class CosmicCreeperiteModel extends EntityModel<CosmicCreeperite>
{
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceReference("cosmic_creeperite"), "main");

    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart leg1;
    private final ModelPart leg2;
    private final ModelPart leg3;
    private final ModelPart leg4;

    public CosmicCreeperiteModel(ModelPart root)
    {
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.leg1 = root.getChild("leg1");
        this.leg2 = root.getChild("leg2");
        this.leg3 = root.getChild("leg3");
        this.leg4 = root.getChild("leg4");
    }

    public static LayerDefinition createBodyLayer()
    {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 14.0F, 0.0F));
        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, -6.0F, -2.0F, 8.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.0F, 0.0F));
        root.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 20.0F, 4.0F));
        root.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 20.0F, 4.0F));
        root.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 20.0F, -4.0F));
        root.addOrReplaceChild("leg4", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 20.0F, -4.0F));

        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    public void setupAnim(@NotNull CosmicCreeperite creeperite, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
        this.head.yRot = netHeadYaw * Maths.DEGREES_TO_RADIANS;
        this.head.xRot = headPitch * Maths.DEGREES_TO_RADIANS;
        this.leg1.xRot = Maths.cos(limbSwing * Maths.LIMB_SWING_MULTIPLIER) * 1.4F * limbSwingAmount;
        this.leg2.xRot = Maths.cos(limbSwing * Maths.LIMB_SWING_MULTIPLIER + Maths.PI) * 1.4F * limbSwingAmount;
        this.leg3.xRot = Maths.cos(limbSwing * Maths.LIMB_SWING_MULTIPLIER + Maths.PI) * 1.4F * limbSwingAmount;
        this.leg4.xRot = Maths.cos(limbSwing * Maths.LIMB_SWING_MULTIPLIER) * 1.4F * limbSwingAmount;
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack stack, @NotNull VertexConsumer consumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
    {
        head.render(stack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        body.render(stack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        leg1.render(stack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        leg2.render(stack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        leg3.render(stack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        leg4.render(stack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}