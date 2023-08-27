package xyz.snaker.tq.client.model.entity;

import xyz.snaker.snakerlib.utility.ResourcePath;
import xyz.snaker.tq.level.entity.mob.EerieCretin;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

import org.jetbrains.annotations.NotNull;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

public class EerieCretinModel extends EntityModel<EerieCretin>
{
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourcePath("eerie_cretin"), "main");

    public final ModelPart cuerpo;
    public final ModelPart glandula;
    public final ModelPart pataderechadelan;
    public final ModelPart pataderechatras;
    public final ModelPart pataizquierdelant;
    public final ModelPart pataizquierdatras;

    public EerieCretinModel(ModelPart root)
    {
        this.cuerpo = root.getChild("cuerpo");
        this.glandula = root.getChild("glandula");
        this.pataderechadelan = root.getChild("pataderechadelan");
        this.pataderechatras = root.getChild("pataderechatras");
        this.pataizquierdelant = root.getChild("pataizquierdelant");
        this.pataizquierdatras = root.getChild("pataizquierdatras");
    }

    public static LayerDefinition createBodyLayer()
    {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        root.addOrReplaceChild("cuerpo", CubeListBuilder.create().texOffs(22, 6).addBox(-2.0F, -10.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
        root.addOrReplaceChild("glandula", CubeListBuilder.create().texOffs(30, 22).addBox(0.0F, -4.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
        root.addOrReplaceChild("pataderechadelan", CubeListBuilder.create().texOffs(10, 14).addBox(-0.5F, -0.75F, -1.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(-1.0F, -1.5F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 32).addBox(-1.5F, -2.25F, -1.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 27).addBox(-2.0F, -3.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(-2.5F, -3.75F, -2.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(-3.0F, -4.5F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(-3.5F, -5.25F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(-4.0F, -4.5F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(-4.5F, -3.75F, -3.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 32).addBox(-5.0F, -3.0F, -3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(-5.5F, -2.25F, -3.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 27).addBox(-6.0F, -1.5F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 32).addBox(-6.5F, -0.75F, -4.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(-7.0F, 0.0F, -4.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 32).addBox(-7.5F, 0.75F, -4.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(-8.0F, 1.5F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(-8.5F, 2.25F, -5.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 32).addBox(-9.0F, 3.0F, -5.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(-9.5F, 3.75F, -5.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 32).addBox(-10.0F, 4.5F, -6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(-10.5F, 5.25F, -6.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 18).addBox(-11.0F, 6.0F, -6.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(-11.5F, 6.75F, -6.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 32).addBox(-12.0F, 7.5F, -7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(-12.5F, 8.25F, -7.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(-13.0F, 9.0F, -7.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 14.0F, -2.0F));
        root.addOrReplaceChild("pataderechatras", CubeListBuilder.create().texOffs(10, 32).addBox(-0.5F, -0.75F, 0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(-1.5F, -2.25F, 0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 27).addBox(-1.0F, -1.5F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 18).addBox(-2.0F, -3.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 32).addBox(-2.5F, -3.75F, 1.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(-3.0F, -4.5F, 1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(-3.5F, -5.25F, 1.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(-4.0F, -4.5F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 27).addBox(-4.5F, -3.75F, 2.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(-5.0F, -3.0F, 2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(-5.5F, -2.25F, 2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 32).addBox(-6.0F, -1.5F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 18).addBox(-6.5F, -0.75F, 3.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(-7.0F, 0.0F, 3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(-7.5F, 0.75F, 3.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(-8.0F, 1.5F, 4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 32).addBox(-8.5F, 2.25F, 4.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(-9.0F, 3.0F, 4.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(-9.5F, 3.75F, 4.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 32).addBox(-10.0F, 4.5F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 18).addBox(-10.5F, 5.25F, 5.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 32).addBox(-11.0F, 6.0F, 5.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(-12.0F, 7.5F, 6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(-11.5F, 6.75F, 5.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(-13.0F, 9.0F, 6.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(-12.5F, 8.25F, 6.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 14.0F, 2.0F));
        root.addOrReplaceChild("pataizquierdelant", CubeListBuilder.create().texOffs(10, 32).addBox(-0.5F, -0.75F, -1.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(0.0F, -1.5F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 27).addBox(0.5F, -2.25F, -1.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(1.0F, -3.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(1.5F, -3.75F, -2.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(2.0F, -4.5F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(2.5F, -5.25F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(3.0F, -4.5F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 18).addBox(3.5F, -3.75F, -3.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(4.0F, -3.0F, -3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(4.5F, -2.25F, -3.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(5.0F, -1.5F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(5.5F, -0.75F, -4.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 27).addBox(6.0F, 0.0F, -4.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(6.5F, 0.75F, -4.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(7.0F, 1.5F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(7.5F, 2.25F, -5.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 32).addBox(8.0F, 3.0F, -5.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(8.5F, 3.75F, -5.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 18).addBox(9.0F, 4.5F, -6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(9.5F, 5.25F, -6.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(10.0F, 6.0F, -6.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(10.5F, 6.75F, -6.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(11.0F, 7.5F, -7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(11.5F, 8.25F, -7.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 27).addBox(12.0F, 9.0F, -7.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 14.0F, -2.0F));
        root.addOrReplaceChild("pataizquierdatras", CubeListBuilder.create().texOffs(10, 14).addBox(-0.5F, -0.75F, 0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(0.0F, -1.5F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 18).addBox(0.5F, -2.25F, 0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(1.0F, -3.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 32).addBox(1.5F, -3.75F, 1.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(2.0F, -4.5F, 1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(2.5F, -5.25F, 1.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 32).addBox(3.0F, -4.5F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(3.5F, -3.75F, 2.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(4.0F, -3.0F, 2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(5.0F, -1.5F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(4.5F, -2.25F, 2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(5.5F, -0.75F, 3.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(6.0F, 0.0F, 3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 32).addBox(6.5F, 0.75F, 3.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 27).addBox(7.5F, 2.25F, 4.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(7.0F, 1.5F, 4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(8.0F, 3.0F, 4.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(8.5F, 3.75F, 4.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(9.0F, 4.5F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 32).addBox(9.5F, 5.25F, 5.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(10.0F, 6.0F, 5.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 32).addBox(10.5F, 6.75F, 5.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 18).addBox(11.0F, 7.5F, 6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 14).addBox(11.5F, 8.25F, 6.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 32).addBox(12.0F, 9.0F, 6.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 14.0F, 2.0F));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack stack, @NotNull VertexConsumer consumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
    {
        cuerpo.render(stack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        glandula.render(stack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        pataderechadelan.render(stack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        pataderechatras.render(stack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        pataizquierdelant.render(stack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        pataizquierdatras.render(stack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void setupAnim(@NotNull EerieCretin cretin, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
        pataderechadelan.xRot = Mth.cos(limbSwing * 0.6662F + 3.1415927F) * limbSwingAmount;
        glandula.yRot = netHeadYaw / 57.295776F;
        glandula.xRot = headPitch / 57.295776F;
        pataizquierdelant.xRot = Mth.cos(limbSwing * 0.6662F) * limbSwingAmount;
        pataizquierdatras.xRot = Mth.cos(limbSwing) * -1.0F * limbSwingAmount;
        pataderechatras.xRot = Mth.cos(limbSwing) * 1.0F * limbSwingAmount;
    }
}
