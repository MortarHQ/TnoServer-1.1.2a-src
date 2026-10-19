package io.github.Memoires.mysticism.damage;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageType;

public class MysticismDamageTypes {
   public static final ResourceKey<DamageType> BALANCE = create("balance");
   public static final ResourceKey<DamageType> BRIMSTONE_FLAMES = create("brimstone_flames");
   public static final ResourceKey<DamageType> DRAGON_EMBER = create("dragon_ember");
   public static final ResourceKey<DamageType> DREAM_EATER = create("dream_eater");
   public static final ResourceKey<DamageType> REPEATED = create("repeated");
   public static final ResourceKey<DamageType> RUPTURED_VEINS = create("ruptured_veins");
   public static final ResourceKey<DamageType> SIZZLING_CORROSION = create("sizzling_corrosion");
   public static final ResourceKey<DamageType> TIME_DAMAGE = create("time_damage");
   public static final ResourceKey<DamageType> VOID = create("void");

   public static ResourceKey<DamageType> create(String name) {
      return ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath("mysticism", name));
   }

   public static void bootstrap(BootstrapContext<DamageType> context) {
      createDamageType(context, BRIMSTONE_FLAMES, DamageEffects.BURNING);
      createDamageType(context, DREAM_EATER);
      createDamageType(context, DRAGON_EMBER, DamageEffects.BURNING);
      createDamageType(context, REPEATED);
      createDamageType(context, RUPTURED_VEINS);
      createDamageType(context, SIZZLING_CORROSION);
      createDamageType(context, TIME_DAMAGE);
      createDamageType(context, VOID);
   }

   public static void createDamageType(BootstrapContext<DamageType> context, ResourceKey<DamageType> key) {
      context.register(key, new DamageType(getMsgId(key), 0.1F));
   }

   public static void createDamageType(BootstrapContext<DamageType> context, ResourceKey<DamageType> key, DamageEffects effects) {
      context.register(key, new DamageType(getMsgId(key), 0.1F, effects));
   }

   public static String getMsgId(ResourceKey<DamageType> key) {
      return "mysticism." + key.location().getPath();
   }
}
