package xyz.snaker.tq.client.renderer.entity;

import xyz.snaker.tq.level.entity.crystal.ComaCrystalLightningBolt;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

/**
 * Created by SnakerBone on 9/12/2023
 **/
public class ComaCrystalLightningBoltRenderer extends EntityRenderer<ComaCrystalLightningBolt>
{
    public ComaCrystalLightningBoltRenderer(EntityRendererProvider.Context context)
    {
        super(context);
    }

    public void render(ComaCrystalLightningBolt bolt, float netHeadYaw, float partialTicks, @NotNull PoseStack stack, @NotNull MultiBufferSource source, int packedLight)
    {
        float[] innerQuads = new float[8];
        float[] outerQuads = new float[8];

        float innerQuadSize = 0F;
        float outerQuadSize = 0F;

        RandomSource random = RandomSource.create(bolt.seed);

        for (int i = 7; i >= 0; i--) {
            innerQuads[i] = innerQuadSize;
            outerQuads[i] = outerQuadSize;

            innerQuadSize += random.nextInt(11) - 5F;
            outerQuadSize += random.nextInt(11) - 5F;
        }

        VertexConsumer consumer = source.getBuffer(RenderType.lightning());
        Matrix4f matrix = stack.last().pose();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                int quadIndex = 7;
                int quadSize = 0;

                if (j > 0) {
                    quadIndex = 7 - j;
                }

                if (j > 0) {
                    quadSize = quadIndex - 2;
                }

                float startX = innerQuads[quadIndex] - innerQuadSize;
                float startZ = outerQuads[quadIndex] - outerQuadSize;

                for (int index = quadIndex; index >= quadSize; index--) {
                    float endX = startX;
                    float endZ = startZ;

                    if (j == 0) {
                        startX += random.nextInt(11) - 5F;
                        startZ += random.nextInt(11) - 5F;
                    } else {
                        startX += random.nextInt(31) - 15F;
                        startZ += random.nextInt(31) - 15F;
                    }

                    float red = 1F;
                    float green = 0F;
                    float blue = 1F;

                    float absInner = 0.1F + (float) i * 0.2F;

                    if (j == 0) {
                        absInner *= (float) index * 0.1F + 1F;
                    }

                    float absOuter = 0.1F + (float) i * 0.2F;

                    if (j == 0) {
                        absOuter *= ((float) index - 1F) * 0.1F + 1F;
                    }

                    quad(matrix, consumer, startX, startZ, index, endX, endZ, red, green, blue, absInner, absOuter, false, false, true, false);
                    quad(matrix, consumer, startX, startZ, index, endX, endZ, red, green, blue, absInner, absOuter, true, false, true, true);
                    quad(matrix, consumer, startX, startZ, index, endX, endZ, red, green, blue, absInner, absOuter, true, true, false, true);
                    quad(matrix, consumer, startX, startZ, index, endX, endZ, red, green, blue, absInner, absOuter, false, true, false, false);
                }
            }
        }
    }

    private static void quad(Matrix4f matrix, VertexConsumer consumer, float startX, float startZ, int index, float endX, float endZ, float red, float green, float blue, float absInner, float absOuter, boolean flipStartX, boolean flipStartZ, boolean flipEndX, boolean flipEndZ)
    {
        consumer.vertex(matrix, startX + (flipStartX ? absOuter : -absOuter), index * 16F, startZ + (flipStartZ ? absOuter : -absOuter))
                .color(red, green, blue, 0.5F)
                .endVertex();
        consumer.vertex(matrix, endX + (flipStartX ? absInner : -absInner), (index + 1) * 16F, endZ + (flipStartZ ? absInner : -absInner))
                .color(red, green, blue, 0.5F)
                .endVertex();
        consumer.vertex(matrix, endX + (flipEndX ? absInner : -absInner), (index + 1) * 16F, endZ + (flipEndZ ? absInner : -absInner))
                .color(red, green, blue, 0.5F)
                .endVertex();
        consumer.vertex(matrix, startX + (flipEndX ? absOuter : -absOuter), index * 16F, startZ + (flipEndZ ? absOuter : -absOuter))
                .color(red, green, blue, 0.5F)
                .endVertex();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull ComaCrystalLightningBolt bolt)
    {
        return InventoryMenu.BLOCK_ATLAS;
    }
}
