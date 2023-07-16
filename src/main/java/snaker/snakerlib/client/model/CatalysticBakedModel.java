package snaker.snakerlib.client.model;

/**
 * Created by SnakerBone on 4/05/2023
 **/
@Deprecated // Wait until CCL updates to 1.20+
public class CatalysticBakedModel //extends WrappedItemModel implements IItemRenderer
{
//    private static final RandomSource RANDOM = RandomSource.create();
//    private final BakedQuad quad;
//    private final boolean flash;
//
//    public CatalysticBakedModel(BakedModel model, TextureAtlasSprite sprite, int colour, int size, boolean flash)
//    {
//        super(model);
//        this.quad = generateQuad(sprite, size, colour);
//        this.flash = flash;
//    }
//
//    @SuppressWarnings("deprecation")
//    public void renderItem(ItemStack item, ItemTransforms.TransformType type, PoseStack stack, MultiBufferSource source, int packedLight, int packedOverlay)
//    {
//        if (type == ItemTransforms.TransformType.GUI) {
//            ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
//
//            renderer.renderQuadList(stack, source.getBuffer(ItemBlockRenderTypes.getRenderType(item, true)), List.of(quad), ItemStack.EMPTY, packedLight, packedOverlay);
//
//            if (flash) {
//                stack.pushPose();
//
//                float scale = RANDOM.nextFloat() * 0.15F + 0.95F;
//                float translate = (1 - scale) / 2;
//
//                stack.translate(translate, translate, 0);
//                stack.scale(scale, scale, 1);
//
//                renderWrapped(item, stack, source, packedLight, packedOverlay, true, (delegate) -> new AlphaOverrideVertexConsumer(delegate, 0.6));
//
//                stack.popPose();
//            }
//        }
//
//        renderWrapped(item, stack, source, packedLight, packedOverlay, true);
//    }
//
//    public @Nullable PerspectiveModelState getModelState()
//    {
//        return (PerspectiveModelState) parentState;
//    }
//
//    public boolean useAmbientOcclusion()
//    {
//        return wrapped.useAmbientOcclusion();
//    }
//
//    public boolean isGui3d()
//    {
//        return wrapped.isGui3d();
//    }
//
//    public boolean usesBlockLight()
//    {
//        return wrapped.usesBlockLight();
//    }
//
//    private static BakedQuad generateQuad(TextureAtlasSprite sprite, int size, int colour)
//    {
//        float[] colours = (new ColourARGB(colour)).getRGBA();
//
//        double spread = size / 16D;
//        double min = 0 - spread;
//        double max = 1 + spread;
//
//        float minU = sprite.getU0();
//        float maxU = sprite.getU1();
//        float minV = sprite.getV0();
//        float maxV = sprite.getV1();
//
//        Quad quad = new Quad();
//
//        quad.reset(CachedFormat.BLOCK);
//        quad.setTexture(sprite);
//
//        putVertex(quad.vertices[0], max, max, 0.0, maxU, minV);
//        putVertex(quad.vertices[1], min, max, 0.0, minU, minV);
//        putVertex(quad.vertices[2], min, min, 0.0, minU, maxV);
//        putVertex(quad.vertices[3], max, min, 0.0, maxU, maxV);
//
//        for (int vert = 0; vert < 4; ++vert) {
//            System.arraycopy(colours, 0, quad.vertices[vert].color, 0, 4);
//        }
//
//        quad.calculateOrientation(true);
//
//        return quad.bake();
//    }
//
//    @SuppressWarnings("SameParameterValue")
//    private static void putVertex(Quad.Vertex vertex, double x, double y, double z, double u, double v)
//    {
//        vertex.vec[0] = (float) x;
//        vertex.vec[1] = (float) y;
//        vertex.vec[2] = (float) z;
//
//        vertex.uv[0] = (float) u;
//        vertex.uv[1] = (float) v;
//    }
}
//