package snaker.tq.level.entity;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by SnakerBone on 8/03/2023
 **/
public class EntityVariants
{
    public enum Cosmo
    {
        RED(0),
        GREEN(1),
        BLUE(2),
        YELLOW(3),
        PINK(4),
        PURPLE(5),
        ALPHA(6);

        private static final Cosmo[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(Cosmo::getId)).toArray(Cosmo[]::new);
        private final int id;

        Cosmo(int id)
        {
            this.id = id;
        }

        public int getId()
        {
            return this.id;
        }

        public static Cosmo byId(int id)
        {
            return BY_ID[id % BY_ID.length];
        }
    }
}
