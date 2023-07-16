package snaker.tq.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import snaker.snakerlib.math.Mh;
import snaker.snakerlib.resources.Identifier;
import snaker.tq.level.entity.mob.Leet;

public class LeetModel extends EntityModel<Leet>
{
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new Identifier("leet"), "main");

    public final ModelPart head;
    public final ModelPart upperTorso;
    public final ModelPart frontLeftArm;
    public final ModelPart frontRightArm;
    public final ModelPart backLeftArm;
    public final ModelPart backRightArm;

    public LeetModel(ModelPart root)
    {
        this.head = root.getChild("head");
        this.upperTorso = root.getChild("upperTorso");
        this.frontLeftArm = root.getChild("frontLeftArm");
        this.frontRightArm = root.getChild("frontRightArm");
        this.backLeftArm = root.getChild("backLeftArm");
        this.backRightArm = root.getChild("backRightArm");
    }

    public static LayerDefinition createBodyLayer()
    {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition upperTorso = root.addOrReplaceChild("upperTorso", CubeListBuilder.create().texOffs(45, 45).mirror().addBox(-5.0F, 0.0F, -1.0F, 10.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2974F, 0.0F, 0.0F));
        PartDefinition frontLeftArm = root.addOrReplaceChild("frontLeftArm", CubeListBuilder.create().texOffs(25, 40).mirror().addBox(0.0F, 0.0F, 0.0F, 3.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.0F, 0.0F, -1.0F, -0.2603F, 0.0744F, -0.409F));
        PartDefinition frontRightArm = root.addOrReplaceChild("frontRightArm", CubeListBuilder.create().texOffs(112, 40).mirror().addBox(-3.0F, 0.0F, 0.0F, 3.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-5.0F, 0.0F, -1.0F, -0.2602F, -0.0827F, 0.409F));
        PartDefinition backLeftArm = root.addOrReplaceChild("backLeftArm", CubeListBuilder.create().texOffs(10, 40).mirror().addBox(-1.5F, -6.0F, -1.5F, 3.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(10.4395F, 0.9084F, 9.5727F, 0.0744F, 0.2974F, -0.6692F));
        PartDefinition backRightArm = root.addOrReplaceChild("backRightArm", CubeListBuilder.create().texOffs(112, 75).mirror().addBox(-1.5F, -6.0F, -1.5F, 3.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-10.4394F, 0.9084F, 9.5727F, 0.0744F, -0.2974F, 0.6692F));

        root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(45, 15).mirror().addBox(-8.0F, -12.0F, -2.0F, 16.0F, 12.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.4461F, 0.0F, 0.0F));
        upperTorso.addOrReplaceChild("midTorso", CubeListBuilder.create().texOffs(45, 66).mirror().addBox(-4.0F, 8.0F, 1.0F, 8.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1487F, 0.0F, 0.0F));
        upperTorso.addOrReplaceChild("lowerTorso", CubeListBuilder.create().texOffs(45, 83).mirror().addBox(-3.0F, 14.0F, 3.0F, 6.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0478F, 0.0F, 0.0F));
        frontLeftArm.addOrReplaceChild("frontLeftArmLower", CubeListBuilder.create().texOffs(29, 55).mirror().addBox(1.9F, 5.7F, 7.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7436F, -0.0372F, 0.1115F));
        frontRightArm.addOrReplaceChild("frontRightArmLower", CubeListBuilder.create().texOffs(112, 55).mirror().addBox(-3.6F, 5.5F, 7.4F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7436F, -0.0372F, -0.1115F));
        backLeftArm.addOrReplaceChild("backLeftArmLower", CubeListBuilder.create().texOffs(10, 55).mirror().addBox(-3.7F, -7.0F, 1.7F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.7007F, 9.5096F, -1.4825F, -0.5577F, -0.8923F, 0.1115F));
        backRightArm.addOrReplaceChild("backRightArmLower", CubeListBuilder.create().texOffs(112, 90).mirror().addBox(1.4F, -6.0F, 2.6F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-5.7006F, 9.5097F, -1.4825F, -0.5577F, 0.8923F, -0.1115F));

        return LayerDefinition.create(mesh, 128, 128);
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack stack, @NotNull VertexConsumer consumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
    {
        head.render(stack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        upperTorso.render(stack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        frontLeftArm.render(stack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        frontRightArm.render(stack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        backLeftArm.render(stack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        backRightArm.render(stack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void setupAnim(@NotNull Leet leet, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
        frontRightArm.xRot = Mth.cos(limbSwing * Mh.MAGIC_ANIM_CONSTANT + Mh.PI) * limbSwingAmount;
        frontLeftArm.xRot = Mth.cos(limbSwing) * -1 * limbSwingAmount;
        backLeftArm.xRot = Mth.cos(limbSwing * Mh.MAGIC_ANIM_CONSTANT + Mh.PI) * limbSwingAmount;
        backRightArm.xRot = Mth.cos(limbSwing * Mh.MAGIC_ANIM_CONSTANT) * limbSwingAmount;
    }
}
