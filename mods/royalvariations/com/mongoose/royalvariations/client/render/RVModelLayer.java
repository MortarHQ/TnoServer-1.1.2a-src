package com.mongoose.royalvariations.client.render;

import com.google.common.collect.Sets;
import com.mongoose.royalvariations.RoyalVariations;
import java.util.Set;
import net.minecraft.client.model.geom.ModelLayerLocation;

public class RVModelLayer {
   private static final Set<ModelLayerLocation> ALL_MODELS = Sets.newHashSet();
   public static final ModelLayerLocation ROYAL_BOMB_PROJECTILE = register("royal_bomb_projectile");
   public static final ModelLayerLocation ROYAL_BOMB = register("royal_bomb");
   public static final ModelLayerLocation NET = register("net");
   public static final ModelLayerLocation SPECTRAL_ANVIL = register("spectral_anvil");
   public static final ModelLayerLocation ROYAL_ZOMBIE = register("royal_zombie");
   public static final ModelLayerLocation ROYAL_SKELETON = register("royal_skeleton");
   public static final ModelLayerLocation ROYAL_CREEPER = register("royal_creeper");
   public static final ModelLayerLocation ROYAL_CREEPER_ARMOR = register("royal_creeper_armor");
   public static final ModelLayerLocation ROYAL_ENDERMAN = register("royal_enderman");
   public static final ModelLayerLocation GLOVE = register("glove");
   public static final ModelLayerLocation ROYAL_KNIGHT_HELMET = register("royal_knight_helmet");
   public static final ModelLayerLocation ROYAL_KNIGHT_CUIRASS = register("royal_knight_cuirass");
   public static final ModelLayerLocation ROYAL_KNIGHT_LEGGINGS = register("royal_knight_leggings");
   public static final ModelLayerLocation ROYAL_KNIGHT_BOOTS = register("royal_knight_boots");

   private static ModelLayerLocation register(String p_171294_) {
      return register(p_171294_, "main");
   }

   private static ModelLayerLocation register(String p_171296_, String p_171297_) {
      ModelLayerLocation modellayerlocation = createLocation(p_171296_, p_171297_);
      if (!ALL_MODELS.add(modellayerlocation)) {
         throw new IllegalStateException("Duplicate registration for " + modellayerlocation);
      } else {
         return modellayerlocation;
      }
   }

   private static ModelLayerLocation createLocation(String p_171301_, String p_171302_) {
      return new ModelLayerLocation(RoyalVariations.location(p_171301_), p_171302_);
   }

   private static ModelLayerLocation registerInnerArmor(String p_171299_) {
      return register(p_171299_, "inner_armor");
   }

   private static ModelLayerLocation registerOuterArmor(String p_171304_) {
      return register(p_171304_, "outer_armor");
   }
}
