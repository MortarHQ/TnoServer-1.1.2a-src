package com.shin.tensura_kumodesu.damage;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageType;

public class KumodesuDamageTypes {
   public static final ResourceKey<DamageType> DEADLY_POISON = create("deadly_poison");
   public static final ResourceKey<DamageType> DEADLY_SPIDER_POISON = create("deadly_spider_poison");
   public static final ResourceKey<DamageType> FATAL_SPIDER_POISON = create("fatal_spider_poison");

   public static ResourceKey<DamageType> create(String name) {
      return ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", name));
   }

   public static void bootstrap(BootstrapContext<DamageType> context) {
      createDamageType(context, DEADLY_POISON);
      createDamageType(context, DEADLY_SPIDER_POISON);
      createDamageType(context, FATAL_SPIDER_POISON);
   }

   public static void createDamageType(BootstrapContext<DamageType> context, ResourceKey<DamageType> key) {
      context.register(key, new DamageType(getMsgId(key), 0.1F));
   }

   public static void createDamageType(BootstrapContext<DamageType> context, ResourceKey<DamageType> key, DamageEffects effects) {
      context.register(key, new DamageType(getMsgId(key), 0.1F, effects));
   }

   public static String getMsgId(ResourceKey<DamageType> key) {
      return "tensura_kumodesu" + key.location().getPath();
   }
}
