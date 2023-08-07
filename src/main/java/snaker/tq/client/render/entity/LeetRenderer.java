package snaker.tq.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import snaker.tq.client.layer.LeetLayer;
import snaker.tq.client.model.entity.LeetModel;
import snaker.tq.level.entity.mob.Leet;
import snaker.snakerlib.utility.ResourcePath;

/**
 * Created by SnakerBone on 2/01/2023
 **/
public class LeetRenderer extends MobRenderer<Leet, LeetModel>
{
    public LeetRenderer(EntityRendererProvider.Context context)
    {
        super(context, new LeetModel(context.bakeLayer(LeetModel.LAYER_LOCATION)), 0);
        addLayer(new LeetLayer(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Leet leet)
    {
        return ResourcePath.SOLID_TEXTURE;
    }

    @Override
    public void render(@NotNull Leet leet, float pEntityYaw, float pt, @NotNull PoseStack stack, @NotNull MultiBufferSource source, int pl)
    {

    }
}