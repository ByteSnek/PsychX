package xyz.snaker.snakerlib.client.model;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;

import org.jetbrains.annotations.NotNull;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

/**
 * Created by SnakerBone on 12/06/2023
 * <p>
 * The minecraft sword with a shorter blade as its own model
 **/
public abstract class TrimmedSwordModel extends Model
{
    protected final ModelPart blade;
    protected final ModelPart hilt;
    protected final ModelPart handle;
    protected final ModelPart pervex;

    protected final ModelPart innerBlade;
    protected final ModelPart outerBlade;
    protected final ModelPart innerHilt;
    protected final ModelPart outerHilt;
    protected final ModelPart innerBauble;
    protected final ModelPart outerBauble;

    public TrimmedSwordModel(ModelPart root)
    {
        super(RenderType::entitySolid);
        this.blade = root.getChild("blade");
        this.hilt = root.getChild("hilt");
        this.handle = root.getChild("handle");
        this.pervex = root.getChild("pervex");
        this.innerBlade = blade.getChild("innerBlade");
        this.outerBlade = blade.getChild("outerBlade");
        this.innerHilt = hilt.getChild("innerHilt");
        this.outerHilt = hilt.getChild("outerHilt");
        this.innerBauble = pervex.getChild("innerPervex");
        this.outerBauble = pervex.getChild("outerPervex");
    }

    public static LayerDefinition createBodyLayer()
    {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition blade = root.addOrReplaceChild("blade", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition hilt = root.addOrReplaceChild("hilt", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition pervex = root.addOrReplaceChild("pervex", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        blade.addOrReplaceChild("outerBlade", CubeListBuilder.create().texOffs(15, 7).addBox(0.0F, -1.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(9, 15).addBox(1.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(15, 9).addBox(2.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(15, 11).addBox(3.0F, -1.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(15, 13).addBox(4.0F, -1.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(15, 15).addBox(5.0F, -1.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 16).addBox(6.0F, -1.0F, 4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(6, 16).addBox(7.0F, -1.0F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(3, 1).addBox(7.0F, -1.0F, 6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(12, 16).addBox(4.0F, -1.0F, 6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(3, 17).addBox(3.0F, -1.0F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(9, 17).addBox(2.0F, -1.0F, 4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(15, 17).addBox(1.0F, -1.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 18).addBox(0.0F, -1.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(18, 0).addBox(-1.0F, -1.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(18, 2).addBox(-2.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        blade.addOrReplaceChild("innerBlade", CubeListBuilder.create().texOffs(0, 14).addBox(-2.0F, -1.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(9, 9).addBox(-2.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(9, 5).addBox(-1.0F, -1.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(9, 13).addBox(-1.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 10).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(3, 13).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(9, 3).addBox(1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(6, 10).addBox(0.0F, -1.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(3, 11).addBox(1.0F, -1.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(3, 9).addBox(2.0F, -1.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(12, 12).addBox(1.0F, -1.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(9, 11).addBox(2.0F, -1.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(12, 10).addBox(2.0F, -1.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(12, 8).addBox(3.0F, -1.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 12).addBox(3.0F, -1.0F, 4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(6, 8).addBox(4.0F, -1.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(9, 1).addBox(3.0F, -1.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(12, 6).addBox(4.0F, -1.0F, 4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(12, 0).addBox(4.0F, -1.0F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(6, 12).addBox(5.0F, -1.0F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(12, 2).addBox(5.0F, -1.0F, 6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(12, 4).addBox(6.0F, -1.0F, 6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(3, 7).addBox(6.0F, -1.0F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 8).addBox(5.0F, -1.0F, 4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(9, 7).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        hilt.addOrReplaceChild("innerHilt", CubeListBuilder.create().texOffs(15, 1).addBox(-5.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(15, 5).addBox(-4.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(-4.0F, -1.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(12, 14).addBox(-3.0F, -1.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 2).addBox(-2.0F, -1.0F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(15, 3).addBox(-1.0F, -1.0F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(3, 15).addBox(0.0F, -1.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        hilt.addOrReplaceChild("outerHilt", CubeListBuilder.create().texOffs(12, 18).addBox(-5.0F, -1.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(6, 4).addBox(-6.0F, -1.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(18, 12).addBox(-6.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(18, 6).addBox(-4.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(6, 18).addBox(-3.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(6, 2).addBox(-3.0F, -1.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(3, 19).addBox(-4.0F, -1.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(18, 18).addBox(-5.0F, -1.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(6, 6).addBox(-5.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(9, 19).addBox(-3.0F, -1.0F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(6, 0).addBox(-2.0F, -1.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(18, 4).addBox(-1.0F, -1.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(18, 16).addBox(-1.0F, -1.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(3, 5).addBox(-2.0F, -1.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 6).addBox(0.0F, -1.0F, -6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(18, 14).addBox(1.0F, -1.0F, -6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(18, 10).addBox(1.0F, -1.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(18, 8).addBox(0.0F, -1.0F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("handle", CubeListBuilder.create().texOffs(15, 19).addBox(-4.0F, -1.0F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(21, 1).addBox(-6.0F, -1.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(3, 21).addBox(-6.0F, -1.0F, -6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 20).addBox(-5.0F, -1.0F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(12, 20).addBox(-5.0F, -1.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(18, 20).addBox(-5.0F, -1.0F, -6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(6, 20).addBox(-4.0F, -1.0F, -5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
        pervex.addOrReplaceChild("innerPervex", CubeListBuilder.create().texOffs(6, 14).addBox(-7.0F, -1.0F, -7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        pervex.addOrReplaceChild("outerPervex", CubeListBuilder.create().texOffs(21, 9).addBox(-6.0F, -1.0F, -8.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(3, 3).addBox(-7.0F, -1.0F, -8.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(21, 7).addBox(-6.0F, -1.0F, -7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(21, 5).addBox(-7.0F, -1.0F, -6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(21, 3).addBox(-8.0F, -1.0F, -8.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(9, 21).addBox(-8.0F, -1.0F, -6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 4).addBox(-8.0F, -1.0F, -7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(mesh, 32, 32);
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack stack, @NotNull VertexConsumer consumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
    {
        stack.scale(0.9F, 0.9F, 0.9F);
        stack.mulPose(Axis.XN.rotationDegrees(145));
        stack.mulPose(Axis.ZN.rotationDegrees(90));
        stack.translate(1, -0.915, 0.01);

        blade.render(stack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        hilt.render(stack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        handle.render(stack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
        pervex.render(stack, consumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}