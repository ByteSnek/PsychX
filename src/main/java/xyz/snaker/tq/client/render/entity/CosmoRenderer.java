package xyz.snaker.tq.client.render.entity;

import java.util.Map;

import xyz.snaker.snakerlib.math.PoseStackBuilder;
import xyz.snaker.snakerlib.utility.ResourcePath;
import xyz.snaker.tq.client.layer.CosmoLayer;
import xyz.snaker.tq.client.model.entity.CosmoModel;
import xyz.snaker.tq.client.render.type.EntityRenderType;
import xyz.snaker.tq.level.entity.EntityVariants;
import xyz.snaker.tq.level.entity.mob.Cosmo;

import org.jetbrains.annotations.NotNull;

import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;

/**
 * Created by SnakerBone on 2/01/2023
 **/
public class CosmoRenderer extends MobRenderer<Cosmo, CosmoModel>
{
    public static final Map<EntityVariants.Cosmo, RenderType> TYPE = Util.make(Maps.newEnumMap(EntityVariants.Cosmo.class), (map) ->
    {
        map.put(EntityVariants.Cosmo.RED, EntityRenderType.RED_STARS.get());
        map.put(EntityVariants.Cosmo.GREEN, EntityRenderType.GREEN_STARS.get());
        map.put(EntityVariants.Cosmo.BLUE, EntityRenderType.BLUE_STARS.get());
        map.put(EntityVariants.Cosmo.YELLOW, EntityRenderType.YELLOW_STARS.get());
        map.put(EntityVariants.Cosmo.PINK, EntityRenderType.PINK_STARS.get());
        map.put(EntityVariants.Cosmo.PURPLE, EntityRenderType.PURPLE_STARS.get());
        map.put(EntityVariants.Cosmo.ALPHA, EntityRenderType.BLACK_STARS.get());
    });

    public CosmoRenderer(EntityRendererProvider.Context context)
    {
        super(context, new CosmoModel(context.bakeLayer(CosmoModel.LAYER_LOCATION)), 0.5F);
        addLayer(new CosmoLayer(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Cosmo cosmo)
    {
        return ResourcePath.SOLID_TEXTURE;
    }

    @Override
    public void render(@NotNull Cosmo cosmo, float entityYaw, float partialTicks, @NotNull PoseStack stack, @NotNull MultiBufferSource source, int packedLight)
    {
        PoseStackBuilder pose = new PoseStackBuilder(stack);
        if (cosmo.getVariant().getId() == 6) {
            pose.scale(1.25F);
        }
        super.render(cosmo, entityYaw, partialTicks, stack, source, packedLight);
    }
}