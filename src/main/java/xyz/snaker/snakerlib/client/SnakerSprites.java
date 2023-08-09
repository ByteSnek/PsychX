package xyz.snaker.snakerlib.client;

/**
 * Created by SnakerBone on 4/05/2023
 **/
@Deprecated // Wait until CCL updates to 1.20+
public class SnakerSprites
{
    //public static final ResourceLocation PARTICLE_LOCATION = new ResourceLocation("textures/particle/orb.png");
    //private static final SpriteRegistryHelper SAMPLER_HELPER = new SpriteRegistryHelper();
    //public static TextureAtlasSprite[] CATALYSTIC_SAMPLERS = new TextureAtlasSprite[10];
    //public static TextureAtlasSprite[] INFUSION_SAMPLERS = new TextureAtlasSprite[5];
    //public static TextureAtlasSprite CAT0, CAT1, CAT2, CAT3, CAT4, CAT5, CAT6, CAT7, CAT8, CAT9;
    //public static TextureAtlasSprite INF0, INF1, INF2, INF3, INF4;
    //public static TextureAtlasSprite ORB;

    static void register()
    {
        //SAMPLER_HELPER.addIIconRegister(atlas ->
        //{
        //    atlas.registerSprite(particle("infusion_0"), registrar ->
        //    {
        //        TextureAtlasSprite[] infusionSamplers = INFUSION_SAMPLERS;
        //        INF0 = registrar;
        //        infusionSamplers[0] = registrar;
        //    });
        //    atlas.registerSprite(particle("infusion_1"), registrar ->
        //    {
        //        TextureAtlasSprite[] infusionSamplers = INFUSION_SAMPLERS;
        //        INF1 = registrar;
        //        infusionSamplers[1] = registrar;
        //    });
        //    atlas.registerSprite(particle("infusion_2"), registrar ->
        //    {
        //        TextureAtlasSprite[] infusionSamplers = INFUSION_SAMPLERS;
        //        INF2 = registrar;
        //        infusionSamplers[2] = registrar;
        //    });
        //    atlas.registerSprite(particle("infusion_3"), registrar ->
        //    {
        //        TextureAtlasSprite[] infusionSamplers = INFUSION_SAMPLERS;
        //        INF3 = registrar;
        //        infusionSamplers[3] = registrar;
        //    });
        //    atlas.registerSprite(particle("infusion_4"), registrar ->
        //    {
        //        TextureAtlasSprite[] infusionSamplers = INFUSION_SAMPLERS;
        //        INF4 = registrar;
        //        infusionSamplers[4] = registrar;
        //    });
        //    atlas.registerSprite(sampler("catalystic_0"), registrar ->
        //    {
        //        TextureAtlasSprite[] catalysticSamplers = CATALYSTIC_SAMPLERS;
        //        CAT0 = registrar;
        //        catalysticSamplers[0] = registrar;
        //    });
        //    atlas.registerSprite(sampler("catalystic_1"), registrar ->
        //    {
        //        TextureAtlasSprite[] catalysticSamplers = CATALYSTIC_SAMPLERS;
        //        CAT1 = registrar;
        //        catalysticSamplers[1] = registrar;
        //    });
        //    atlas.registerSprite(sampler("catalystic_2"), registrar ->
        //    {
        //        TextureAtlasSprite[] catalysticSamplers = CATALYSTIC_SAMPLERS;
        //        CAT2 = registrar;
        //        catalysticSamplers[2] = registrar;
        //    });
        //    atlas.registerSprite(sampler("catalystic_3"), registrar ->
        //    {
        //        TextureAtlasSprite[] catalysticSamplers = CATALYSTIC_SAMPLERS;
        //        CAT3 = registrar;
        //        catalysticSamplers[3] = registrar;
        //    });
        //    atlas.registerSprite(sampler("catalystic_4"), registrar ->
        //    {
        //        TextureAtlasSprite[] catalysticSamplers = CATALYSTIC_SAMPLERS;
        //        CAT4 = registrar;
        //        catalysticSamplers[4] = registrar;
        //    });
        //    atlas.registerSprite(sampler("catalystic_5"), registrar ->
        //    {
        //        TextureAtlasSprite[] catalysticSamplers = CATALYSTIC_SAMPLERS;
        //        CAT5 = registrar;
        //        catalysticSamplers[5] = registrar;
        //    });
        //    atlas.registerSprite(sampler("catalystic_6"), registrar ->
        //    {
        //        TextureAtlasSprite[] catalysticSamplers = CATALYSTIC_SAMPLERS;
        //        CAT6 = registrar;
        //        catalysticSamplers[6] = registrar;
        //    });
        //    atlas.registerSprite(sampler("catalystic_7"), registrar ->
        //    {
        //        TextureAtlasSprite[] catalysticSamplers = CATALYSTIC_SAMPLERS;
        //        CAT7 = registrar;
        //        catalysticSamplers[7] = registrar;
        //    });
        //    atlas.registerSprite(sampler("catalystic_8"), registrar ->
        //    {
        //        TextureAtlasSprite[] catalysticSamplers = CATALYSTIC_SAMPLERS;
        //        CAT8 = registrar;
        //        catalysticSamplers[8] = registrar;
        //    });
        //    atlas.registerSprite(sampler("catalystic_9"), registrar ->
        //    {
        //        TextureAtlasSprite[] catalysticSamplers = CATALYSTIC_SAMPLERS;
        //        CAT9 = registrar;
        //        catalysticSamplers[9] = registrar;
        //    });
        //    atlas.registerSprite(particle("orb"), registrar -> ORB = registrar);
        //});
    }

    //private static ResourceLocation sampler(String path)
    //{
    //    return new SnakerBoneResourceLocation("sampler/" + path);
    //}

    //private static ResourceLocation particle(String path)
    //{
    //    return new SnakerBoneResourceLocation("particle/" + path);
    //}

    public static void initialize()
    {
        SnakerSprites.register();
    }
}
