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
import snaker.tq.level.entity.mob.Flare;

public class FlareModel extends EntityModel<Flare>
{
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new Identifier("flare"), "main");

    private final ModelPart base;

    public FlareModel(ModelPart root)
    {
        this.base = root.getChild("base");
    }

    public static LayerDefinition createBodyLayer()
    {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition base = root.addOrReplaceChild("base", CubeListBuilder.create(), PartPose.offset(-1.5F, 23.0F, 0.0F));
        PartDefinition inner = base.addOrReplaceChild("inner", CubeListBuilder.create().texOffs(43, 25).addBox(-3.6097F, 8.3091F, -0.4768F, 7.1159F, 2.3958F, 1.4303F, new CubeDeformation(0.0F)).texOffs(12, 43).addBox(-3.6097F, -7.4603F, -0.4768F, 7.1159F, 2.3958F, 1.4303F, new CubeDeformation(0.0F)).texOffs(36, 43).addBox(6.9343F, -1.2338F, -0.4768F, 2.5388F, 5.6856F, 1.4303F, new CubeDeformation(0.0F)).texOffs(28, 43).addBox(-9.6125F, -1.2338F, -0.4768F, 2.5388F, 5.6856F, 1.4303F, new CubeDeformation(0.0F)), PartPose.offset(1.2616F, -17.3559F, 0.4768F));
        PartDefinition outer = base.addOrReplaceChild("outer", CubeListBuilder.create().texOffs(0, 8).addBox(-5.9698F, 13.0053F, 0.0F, 11.8598F, 6.3769F, 2.3839F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(-5.9698F, -15.6609F, 0.0F, 11.8598F, 6.3769F, 2.3839F, new CubeDeformation(0.0F)).texOffs(0, 36).addBox(11.6051F, -2.8981F, 0.0014F, 4.2286F, 9.4732F, 2.3811F, new CubeDeformation(-0.001F)).texOffs(28, 0).addBox(-15.973F, -2.8981F, 0.0014F, 4.2286F, 9.4732F, 2.3811F, new CubeDeformation(-0.001F)), PartPose.offset(1.2616F, -17.3559F, 0.0F));
        PartDefinition core = base.addOrReplaceChild("core", CubeListBuilder.create().texOffs(51, 51).addBox(-4.2255F, -1.6282F, -0.4776F, 2.8474F, 2.3706F, 0.5976F, new CubeDeformation(-0.002F)).texOffs(7, 52).addBox(0.8812F, -1.6282F, -0.4776F, 2.8474F, 2.3706F, 0.5976F, new CubeDeformation(-0.002F)).texOffs(0, 47).addBox(-1.7316F, -4.8258F, -0.4849F, 2.9811F, 4.5902F, 0.6121F, new CubeDeformation(-0.02F)).texOffs(43, 49).addBox(-1.7275F, -0.6351F, -0.4808F, 2.973F, 4.5822F, 0.604F, new CubeDeformation(-0.01F)), PartPose.offset(1.2616F, -15.3559F, 0.9536F));

        inner.addOrReplaceChild("inner_r1", CubeListBuilder.create().texOffs(46, 32).addBox(-19.1689F, -2.8602F, -0.7147F, 2.8169F, 4.8623F, 1.4295F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(7.2296F, 8.1349F, 0.2384F, 0.0F, 0.0F, 0.6109F));
        inner.addOrReplaceChild("inner_r2", CubeListBuilder.create().texOffs(28, 11).addBox(-1.8604F, -19.2389F, -0.7161F, 3.9353F, 2.8268F, 1.4322F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.7232F, 9.4891F, 0.2384F, 0.0F, 0.0F, -0.6109F));
        inner.addOrReplaceChild("inner_r3", CubeListBuilder.create().texOffs(44, 16).addBox(-2.0736F, -19.2375F, -0.7147F, 3.9325F, 2.824F, 1.4295F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(-5.8625F, 9.4891F, 0.2384F, 0.0F, 0.0F, 0.6109F));
        inner.addOrReplaceChild("inner_r4", CubeListBuilder.create().texOffs(44, 43).addBox(16.3506F, -2.8616F, -0.7161F, 2.8197F, 4.865F, 1.4322F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.3689F, 8.1349F, 0.2384F, 0.0F, 0.0F, -0.6109F));
        inner.addOrReplaceChild("inner_r5", CubeListBuilder.create().texOffs(19, 46).addBox(-19.1702F, -2.0034F, -0.7161F, 2.8197F, 4.865F, 1.4322F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.2296F, -4.8903F, 0.2384F, 0.0F, 0.0F, -0.6109F));
        inner.addOrReplaceChild("inner_r6", CubeListBuilder.create().texOffs(44, 28).addBox(-1.859F, 16.4135F, -0.7147F, 3.9325F, 2.824F, 1.4295F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(5.7232F, -6.2445F, 0.2384F, 0.0F, 0.0F, 0.6109F));
        inner.addOrReplaceChild("inner_r7", CubeListBuilder.create().texOffs(44, 20).addBox(-2.0736F, 16.4135F, -0.7147F, 3.9325F, 2.824F, 1.4295F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(-5.8625F, -6.2445F, 0.2384F, 0.0F, 0.0F, -0.6109F));
        inner.addOrReplaceChild("inner_r8", CubeListBuilder.create().texOffs(11, 46).addBox(16.3506F, -2.0034F, -0.7161F, 2.8197F, 4.865F, 1.4322F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.3689F, -4.8903F, 0.2384F, 0.0F, 0.0F, 0.6109F));

        outer.addOrReplaceChild("outer_r1", CubeListBuilder.create().texOffs(22, 16).addBox(-36.7166F, -4.7678F, -1.1919F, 9.464F, 8.1052F, 2.3839F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.0958F, 12.715F, 1.1919F, 0.0F, 0.0F, 0.6109F));
        outer.addOrReplaceChild("outer_r2", CubeListBuilder.create().texOffs(30, 36).addBox(-3.0977F, -32.0618F, -1.1906F, 6.5529F, 4.7054F, 2.3811F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(9.5851F, 14.972F, 1.1919F, 0.0F, 0.0F, -0.6109F));
        outer.addOrReplaceChild("outer_r3", CubeListBuilder.create().texOffs(12, 36).addBox(-3.4552F, -32.0618F, -1.1906F, 6.5529F, 4.7054F, 2.3811F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(-9.7244F, 14.972F, 1.1919F, 0.0F, 0.0F, 0.6109F));
        outer.addOrReplaceChild("outer_r4", CubeListBuilder.create().texOffs(0, 16).addBox(27.2526F, -4.7678F, -1.1919F, 9.464F, 8.1052F, 2.3839F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.2351F, 12.715F, 1.1919F, 0.0F, 0.0F, -0.6109F));
        outer.addOrReplaceChild("outer_r5", CubeListBuilder.create().texOffs(22, 26).addBox(-36.7166F, -3.3374F, -1.1919F, 9.464F, 8.1052F, 2.3839F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.0958F, -8.9937F, 1.1919F, 0.0F, 0.0F, -0.6109F));
        outer.addOrReplaceChild("outer_r6", CubeListBuilder.create().texOffs(40, 0).addBox(-3.0977F, 27.3564F, -1.1906F, 6.5529F, 4.7054F, 2.3811F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(9.5851F, -11.2507F, 1.1919F, 0.0F, 0.0F, 0.6109F));
        outer.addOrReplaceChild("outer_r7", CubeListBuilder.create().texOffs(38, 9).addBox(-3.4552F, 27.3564F, -1.1906F, 6.5529F, 4.7054F, 2.3811F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(-9.7244F, -11.2507F, 1.1919F, 0.0F, 0.0F, -0.6109F));
        outer.addOrReplaceChild("outer_r8", CubeListBuilder.create().texOffs(0, 26).addBox(27.2526F, -3.3374F, -1.1919F, 9.464F, 8.1052F, 2.3839F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.2351F, -8.9937F, 1.1919F, 0.0F, 0.0F, 0.6109F));

        core.addOrReplaceChild("core_r1", CubeListBuilder.create().texOffs(51, 48).addBox(-9.1791F, -0.8344F, -0.298F, 3.5579F, 2.0263F, 0.596F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.7929F, -3.1509F, -0.1788F, 0.0F, 0.0F, -0.6109F));
        core.addOrReplaceChild("core_r2", CubeListBuilder.create().texOffs(52, 44).addBox(-0.7752F, 6.2424F, -0.2984F, 1.6397F, 1.7738F, 0.5968F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(2.1652F, -3.7152F, -0.1788F, 0.0F, 0.0F, 0.6109F));
        core.addOrReplaceChild("core_r3", CubeListBuilder.create().texOffs(21, 52).addBox(-0.8646F, 6.2424F, -0.2984F, 1.6397F, 1.7738F, 0.5968F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(-2.6621F, -3.7152F, -0.1788F, 0.0F, 0.0F, -0.6109F));
        core.addOrReplaceChild("core_r4", CubeListBuilder.create().texOffs(51, 41).addBox(5.6212F, -0.8344F, -0.298F, 3.5579F, 2.0263F, 0.596F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.2898F, -3.1509F, -0.1788F, 0.0F, 0.0F, 0.6109F));
        core.addOrReplaceChild("core_r5", CubeListBuilder.create().texOffs(27, 50).addBox(-9.1845F, -1.1973F, -0.3034F, 3.5687F, 2.037F, 0.6067F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.7929F, 2.2763F, -0.1788F, 0.0F, 0.0F, 0.6109F));
        core.addOrReplaceChild("core_r6", CubeListBuilder.create().texOffs(15, 52).addBox(-0.7752F, -8.0162F, -0.2984F, 1.6397F, 1.7738F, 0.5968F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(2.1652F, 2.8405F, -0.1788F, 0.0F, 0.0F, -0.6109F));
        core.addOrReplaceChild("core_r7", CubeListBuilder.create().texOffs(37, 50).addBox(-0.8646F, -8.0162F, -0.2984F, 1.6397F, 1.7738F, 0.5968F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(-2.6621F, 2.8405F, -0.1788F, 0.0F, 0.0F, 0.6109F));
        core.addOrReplaceChild("core_r8", CubeListBuilder.create().texOffs(48, 38).addBox(5.6158F, -1.1973F, -0.3034F, 3.5687F, 2.037F, 0.6067F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.2898F, 2.2763F, -0.1788F, 0.0F, 0.0F, -0.6109F));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(@NotNull Flare flare, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
        float mul = flare.isAggressive() ? 3 : 2;
        base.getChild("outer").yRot = (ageInTicks * 16) * Mh.DEGREES_TO_RADIANS * mul;
        base.getChild("inner").yRot = ageInTicks * Mh.DEGREES_TO_RADIANS * 2 * mul;
        base.getChild("core").xRot = (ageInTicks * 8) * Mh.DEGREES_TO_RADIANS * mul;
        base.getChild("core").zRot = (ageInTicks * 4) * Mh.DEGREES_TO_RADIANS * mul;
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack stack, @NotNull VertexConsumer consumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
    {
        base.render(stack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}