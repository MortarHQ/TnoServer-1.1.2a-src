package com.shin.tensura_kumodesu.enums;

import net.minecraft.resources.ResourceLocation;

public enum TaratectTextureEnum {
   SMALL_LESSER("taratect_small_lesser"),
   ARACHNE("taratect_arachne"),
   ARCH("taratect_arch"),
   EDE_SAINE("taratect_ede_saine"),
   GREATER("taratect_greater"),
   GREAT_POISON("taratect_great_poison"),
   GREAT("taratect_great"),
   LESSER_POISON("taratect_lesser_poison"),
   LESSER("taratect_lesser"),
   ORTHOCADINAHT("taratect_orthocadinaht"),
   POISON("taratect_poison"),
   QUEEN("taratect_queen"),
   SMALL_POISON("taratect_small_poison"),
   SMALL("taratect_small"),
   TARATECT("taratect"),
   ZANA_HOROWA("taratect_zana_horowa"),
   ZOA_ELE("taratect_zoa_ele");

   private final ResourceLocation normal;
   private final ResourceLocation kumo;
   private final ResourceLocation eyes;

   TaratectTextureEnum(String name) {
      this.normal = ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "textures/entities/" + name + ".png");
      this.kumo = ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "textures/entities/" + name + "_kumo.png");
      this.eyes = ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "textures/entities/" + name + "_eyes.png");
   }

   public ResourceLocation getNormal() {
      return this.normal;
   }

   public ResourceLocation getKumo() {
      return this.kumo;
   }

   public ResourceLocation getEyes() {
      return this.eyes;
   }
}
