package io.github.Memoires.mysticism.entity.variant;

import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;

public enum OkamiVariant {
   GREY(0),
   BROWN(1);

   private static final OkamiVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(OkamiVariant::getId)).toArray(OkamiVariant[]::new);
   private final int id;
   public static final Map<OkamiVariant, ResourceLocation> LOCATION_BY_VARIANT = (Map<OkamiVariant, ResourceLocation>)Util.make(
      Maps.newEnumMap(OkamiVariant.class), variant -> {
         variant.put(GREY, ResourceLocation.fromNamespaceAndPath("mysticism", "textures/entity/monster/okami_grey.png"));
         variant.put(BROWN, ResourceLocation.fromNamespaceAndPath("mysticism", "textures/entity/monster/okami_brown.png"));
      }
   );

   OkamiVariant(int id) {
      this.id = id;
   }

   public int getId() {
      return this.id;
   }

   public static OkamiVariant byId(int id) {
      return BY_ID[id % BY_ID.length];
   }
}
