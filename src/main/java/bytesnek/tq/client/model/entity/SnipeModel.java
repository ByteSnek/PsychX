package bytesnek.tq.client.model.entity;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import org.jetbrains.annotations.NotNull;

import bytesnek.snakerlib.resources.ResourceReference;
import bytesnek.tq.level.entity.mob.Snipe;

public class SnipeModel extends EntityModel<Snipe>
{
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceReference("snipe"), "main");

    private final ModelPart body;

    private float headPitch;

    public SnipeModel(ModelPart root)
    {
        this.body = root.getChild("base");
    }

    public static LayerDefinition createBodyLayer()
    {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition base = root.addOrReplaceChild("base", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, -1.5708F, 0.0F));
        PartDefinition frame = base.addOrReplaceChild("frame", CubeListBuilder.create().texOffs(20, 20).addBox(0.0F, -24.0F, -8.0F, 2.0F, 2.0F, 16.0F, new CubeDeformation(-0.1F)).texOffs(20, 2).addBox(0.0F, -6.0F, -8.0F, 2.0F, 2.0F, 16.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition inner = base.addOrReplaceChild("inner", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition corner = base.addOrReplaceChild("corner", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition core = base.addOrReplaceChild("core", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        core.addOrReplaceChild("coreR1", CubeListBuilder.create().texOffs(0, 36).addBox(0.0F, -4.0F, 10.0F, 2.0F, 8.0F, 8.0F, new CubeDeformation(0.15F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));
        inner.addOrReplaceChild("innerR1", CubeListBuilder.create().texOffs(36, 38).addBox(0.0F, -2.0F, -22.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.1F)).texOffs(40, 0).addBox(0.0F, 4.0F, -16.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.1F)).texOffs(24, 38).addBox(0.0F, -2.0F, -10.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.1F)).texOffs(12, 36).addBox(0.0F, -8.0F, -16.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.5708F, 0.0F, 0.0F));
        frame.addOrReplaceChild("frameR1", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, 8.0F, -22.0F, 2.0F, 2.0F, 16.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.5708F, 0.0F, 0.0F));
        frame.addOrReplaceChild("frameR2", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, 8.0F, -22.0F, 2.0F, 2.0F, 16.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.5708F, 3.1416F, 0.0F));
        corner.addOrReplaceChild("cornerR1", CubeListBuilder.create().texOffs(0, 18).addBox(-2.0F, 6.0F, -26.0F, 2.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.3562F, 3.1416F, 0.0F));
        corner.addOrReplaceChild("cornerR2", CubeListBuilder.create().texOffs(20, 0).addBox(0.0F, 6.0F, -26.0F, 2.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.3562F, 0.0F, 0.0F));
        corner.addOrReplaceChild("cornerR3", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, 6.0F, -6.0F, 2.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.3562F, 3.1416F, 0.0F));
        corner.addOrReplaceChild("cornerR4", CubeListBuilder.create().texOffs(20, 20).addBox(0.0F, 6.0F, -6.0F, 2.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.3562F, 0.0F, 0.0F));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(@NotNull Snipe snipe, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
        this.headPitch = headPitch;
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack stack, @NotNull VertexConsumer consumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
    {
        stack.mulPose(Axis.XP.rotationDegrees(headPitch));
        body.render(stack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}