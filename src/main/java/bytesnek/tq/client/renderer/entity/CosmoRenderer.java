package bytesnek.tq.client.renderer.entity;

import java.util.Map;

import xyz.snaker.snakerlib.math.Tensor;
import xyz.snaker.snakerlib.resources.ResourceReference;

import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;

import org.jetbrains.annotations.NotNull;

import bytesnek.tq.client.layer.CosmoLayer;
import bytesnek.tq.client.model.entity.CosmoModel;
import bytesnek.tq.client.renderer.type.EntityRenderType;
import bytesnek.tq.level.entity.EntityVariants;
import bytesnek.tq.level.entity.mob.Cosmo;

/**
 * Created by SnakerBone on 2/01/2023
 **/
public class CosmoRenderer extends MobRenderer<Cosmo, CosmoModel>
{
    public static final Map<EntityVariants.Cosmo, EntityRenderType> TYPE = Util.make(Maps.newEnumMap(EntityVariants.Cosmo.class), (map) ->
    {
        map.put(EntityVariants.Cosmo.RED, EntityRenderType.RED_STARS);
        map.put(EntityVariants.Cosmo.GREEN, EntityRenderType.GREEN_STARS);
        map.put(EntityVariants.Cosmo.BLUE, EntityRenderType.BLUE_STARS);
        map.put(EntityVariants.Cosmo.YELLOW, EntityRenderType.YELLOW_STARS);
        map.put(EntityVariants.Cosmo.PINK, EntityRenderType.PINK_STARS);
        map.put(EntityVariants.Cosmo.PURPLE, EntityRenderType.PURPLE_STARS);
        map.put(EntityVariants.Cosmo.ALPHA, EntityRenderType.BLACK_STARS);
    });

    public CosmoRenderer(EntityRendererProvider.Context context)
    {
        super(context, new CosmoModel(context.bakeLayer(CosmoModel.LAYER_LOCATION)), 0.5F);
        addLayer(new CosmoLayer(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Cosmo cosmo)
    {
        return new ResourceReference("textures/solid.png");
    }

    @Override
    public void render(@NotNull Cosmo cosmo, float entityYaw, float partialTicks, @NotNull PoseStack stack, @NotNull MultiBufferSource source, int packedLight)
    {
        Tensor pose = new Tensor(stack);
        if (cosmo.getVariant().getId() == 6) {
            pose.scale(1.25F);
        }
        super.render(cosmo, entityYaw, partialTicks, stack, source, packedLight);
    }
}