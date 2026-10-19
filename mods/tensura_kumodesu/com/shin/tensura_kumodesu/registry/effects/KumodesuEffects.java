package com.shin.tensura_kumodesu.registry.effects;

import com.shin.tensura_kumodesu.effects.DeadlyPoisonEffect;
import com.shin.tensura_kumodesu.effects.DeadlySpiderPoisonEffect;
import com.shin.tensura_kumodesu.effects.DeterminationEffect;
import com.shin.tensura_kumodesu.effects.FatalSpiderPoisonEffect;
import com.shin.tensura_kumodesu.effects.PerseveranceEffect;
import com.shin.tensura_kumodesu.effects.ResolveEffect;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;

public class KumodesuEffects {
   private static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create("tensura_kumodesu", Registries.MOB_EFFECT);
   public static final RegistrySupplier<MobEffect> DEADLY_POISON = MOB_EFFECTS.register("deadly_poison", DeadlyPoisonEffect::new);
   public static final RegistrySupplier<MobEffect> DEADLY_SPIDER_POISON = MOB_EFFECTS.register("deadly_spider_poison", DeadlySpiderPoisonEffect::new);
   public static final RegistrySupplier<MobEffect> FATAL_SPIDER_POISON = MOB_EFFECTS.register("fatal_spider_poison", FatalSpiderPoisonEffect::new);
   public static final RegistrySupplier<MobEffect> RESOLVE = MOB_EFFECTS.register("resolve", ResolveEffect::new);
   public static final RegistrySupplier<MobEffect> DETERMINATION = MOB_EFFECTS.register("determination", DeterminationEffect::new);
   public static final RegistrySupplier<MobEffect> PERSEVERANCE = MOB_EFFECTS.register("perseverance", PerseveranceEffect::new);

   public static void init() {
      MOB_EFFECTS.register();
   }
}
