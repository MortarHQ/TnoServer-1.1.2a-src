package xaero.map.effects;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class Effects {
   public static WorldMapStatusEffect NO_WORLD_MAP_UNHELD = null;
   public static WorldMapStatusEffect NO_WORLD_MAP_HARMFUL_UNHELD = null;
   public static WorldMapStatusEffect NO_CAVE_MAPS_UNHELD = null;
   public static WorldMapStatusEffect NO_CAVE_MAPS_HARMFUL_UNHELD = null;
   public static Holder<MobEffect> NO_WORLD_MAP = null;
   public static Holder<MobEffect> NO_WORLD_MAP_HARMFUL = null;
   public static Holder<MobEffect> NO_CAVE_MAPS = null;
   public static Holder<MobEffect> NO_CAVE_MAPS_HARMFUL = null;

   public static void init() {
      if (NO_WORLD_MAP == null) {
         NO_WORLD_MAP_UNHELD = new NoWorldMapEffect(MobEffectCategory.NEUTRAL);
         NO_WORLD_MAP_HARMFUL_UNHELD = new NoWorldMapEffect(MobEffectCategory.HARMFUL);
         NO_CAVE_MAPS_UNHELD = new NoCaveMapsEffect(MobEffectCategory.NEUTRAL);
         NO_CAVE_MAPS_HARMFUL_UNHELD = new NoCaveMapsEffect(MobEffectCategory.HARMFUL);
      }
   }
}
