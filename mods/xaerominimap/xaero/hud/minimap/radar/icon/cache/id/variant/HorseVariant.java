package xaero.hud.minimap.radar.icon.cache.id.variant;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Objects;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.horse.Markings;

public class HorseVariant {
   public static final Map<Markings, ResourceLocation> HORSE_MARKINGS = (Map<Markings, ResourceLocation>)Util.make(Maps.newEnumMap(Markings.class), map -> {
      map.put(Markings.NONE, (ResourceLocation)null);
      map.put(Markings.WHITE, ResourceLocation.parse("textures/entity/horse/horse_markings_white.png"));
      map.put(Markings.WHITE_FIELD, ResourceLocation.parse("textures/entity/horse/horse_markings_whitefield.png"));
      map.put(Markings.WHITE_DOTS, ResourceLocation.parse("textures/entity/horse/horse_markings_whitedots.png"));
      map.put(Markings.BLACK_DOTS, ResourceLocation.parse("textures/entity/horse/horse_markings_blackdots.png"));
   });
   private final ResourceLocation texture;
   private final Markings markings;

   public HorseVariant(ResourceLocation texture, Markings markings) {
      this.texture = texture;
      this.markings = markings;
   }

   @Override
   public String toString() {
      return this.texture + "%" + HORSE_MARKINGS.get(this.markings);
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         HorseVariant that = (HorseVariant)o;
         return Objects.equals(this.texture, that.texture) && this.markings == that.markings;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.texture, this.markings);
   }
}
