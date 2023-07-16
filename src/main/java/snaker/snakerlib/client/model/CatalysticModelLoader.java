package snaker.snakerlib.client.model;

/**
 * Created by SnakerBone on 4/05/2023
 **/
@Deprecated // Wait until CCL updates to 1.20+
public class CatalysticModelLoader //implements IGeometryLoader<CatalysticModelLoader.Geo>
{
//    public Geo read(JsonObject contents, JsonDeserializationContext context)
//    {
//        JsonObject catalystic = contents.getAsJsonObject("catalystic");
//        if (catalystic == null)
//        {
//            throw new RuntimeException("Json object 'catalystic' is missing");
//        } else
//        {
//            IntList colours = new IntArrayList();
//            JsonArray coloursArray = contents.getAsJsonArray("colours");
//            if (coloursArray != null)
//            {
//                for (JsonElement element : coloursArray)
//                {
//                    colours.add(element.getAsInt());
//                }
//            }
//            String texture = JsonUtils.getString(catalystic, "texture");
//            int colour = JsonUtils.getInt(catalystic, "colour");
//            int size = JsonUtils.getInt(catalystic, "size");
//            boolean flash = JsonUtils.getAsPrimitive(catalystic, "flash").getAsBoolean();
//            JsonObject copy = contents.deepCopy();
//            copy.remove("catalystic");
//            copy.remove("loader");
//            BlockModel model = context.deserialize(copy, BlockModel.class);
//            return new Geo(model, colours, texture, colour, size, flash);
//        }
//    }
//
//    public static class Geo implements IUnbakedGeometry<Geo>
//    {
//        private final BlockModel model;
//        private final IntList colours;
//        private final String texture;
//        private final int colour;
//        private final int size;
//        private final boolean flash;
//        private Material material;
//
//        public Geo(BlockModel model, IntList colours, String texture, int colour, int size, boolean flash)
//        {
//            this.model = model;
//            this.colours = colours;
//            this.texture = texture;
//            this.colour = colour;
//            this.size = size;
//            this.flash = flash;
//        }
//
//        public BakedModel bake(IGeometryBakingContext context, ModelBakery bakery, Function<Material, TextureAtlasSprite> materials, ModelState state, ItemOverrides overrides, ResourceLocation location)
//        {
//            BakedModel bakedModel = model.bake(bakery, model, materials, state, location, false);
//            return new CatalysticBakedModel(tintLayers(bakedModel, colours), materials.apply(material), colour, size, flash);
//        }
//
//        public Collection<Material> getMaterials(IGeometryBakingContext context, Function<ResourceLocation, UnbakedModel> models, Set<Pair<String, String>> errors)
//        {
//            Set<Material> materials = new HashSet<>();
//            material = context.getMaterial(texture);
//            if (material.texture() == MissingTextureAtlasSprite.getLocation())
//            {
//                errors.add(Pair.of(texture, context.getModelName()));
//            }
//            materials.add(material);
//            materials.addAll(model.getMaterials(models, errors));
//            return materials;
//        }
//
//        @SuppressWarnings("deprecation")
//        private static BakedModel tintLayers(BakedModel model, IntList colours)
//        {
//            if (colours.isEmpty())
//            {
//                return model;
//            } else
//            {
//                Map<Direction, List<BakedQuad>> quads = new HashMap<>();
//                Direction[] directions = Direction.values();
//                for (Direction direction : directions)
//                {
//                    quads.put(direction, transformQuads(model.getQuads(null, direction, RandomSource.create()), colours));
//                }
//                List<BakedQuad> clipped = transformQuads(model.getQuads(null, null, RandomSource.create()), colours);
//                return new SimpleBakedModel(clipped, quads, model.useAmbientOcclusion(), model.usesBlockLight(), model.isGui3d(), model.getParticleIcon(), model.getTransforms(), ItemOverrides.EMPTY);
//            }
//        }
//
//        private static List<BakedQuad> transformQuads(List<BakedQuad> quads, IntList colours)
//        {
//            List<BakedQuad> bakedQuads = new ArrayList<>(quads.size());
//            for (BakedQuad quad : quads)
//            {
//                bakedQuads.add(transformQuad(quad, colours));
//            }
//            return bakedQuads;
//        }
//
//        private static BakedQuad transformQuad(BakedQuad quad, IntList colours)
//        {
//            int tints = quad.getTintIndex();
//            if (tints != -1 && tints < colours.size())
//            {
//                int tint = colours.getInt(tints);
//                if (tint == -1)
//                {
//                    return quad;
//                } else
//                {
//                    Quad freshQuad = new Quad();
//                    freshQuad.reset(CachedFormat.BLOCK);
//                    freshQuad.pipe(freshQuad);
//                    float red = (float) (tint >> 16 & 255) / 255.0F;
//                    float green = (float) (tint >> 8 & 255) / 255.0F;
//                    float blue = (float) (tint & 255) / 255.0F;
//                    Quad.Vertex[] vertices = freshQuad.vertices;
//                    for (Quad.Vertex vertex : vertices)
//                    {
//                        float[] vertColours = vertex.color;
//                        vertColours[0] *= red;
//                        vertColours[1] *= green;
//                        vertColours[2] *= blue;
//                    }
//                    freshQuad.tintIndex = -1;
//                    return freshQuad.bake();
//                }
//            } else
//            {
//                return quad;
//            }
//        }
//    }
}
