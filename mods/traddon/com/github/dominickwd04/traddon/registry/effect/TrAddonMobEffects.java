package com.github.dominickwd04.traddon.registry.effect;

import com.github.dominickwd04.traddon.effect.AtomicAnnihilationEffect;
import com.github.dominickwd04.traddon.effect.DespairEffect;
import com.github.dominickwd04.traddon.effect.PhotonBladeEffect;
import com.github.dominickwd04.traddon.effect.PostMortemSurgeEffect;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;

public class TrAddonMobEffects {
   private static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create("traddon", Registries.MOB_EFFECT);
   public static final RegistrySupplier<MobEffect> DESPAIR = MOB_EFFECTS.register("despair", DespairEffect::new);
   public static final RegistrySupplier<MobEffect> POST_MORTEM_SURGE = MOB_EFFECTS.register("post_mortem_surge", PostMortemSurgeEffect::new);
   public static final RegistrySupplier<MobEffect> ATOMIC_ANNIHILATION = MOB_EFFECTS.register("atomic_annihilation", AtomicAnnihilationEffect::new);
   public static final RegistrySupplier<MobEffect> PHOTON_BLADE = MOB_EFFECTS.register("photon_blade", PhotonBladeEffect::new);

   public static void init() {
      MOB_EFFECTS.register();
   }

   public static Holder<MobEffect> getReference(RegistrySupplier<MobEffect> mobEffect) {
      ResourceKey<MobEffect> key = ResourceKey.create(Registries.MOB_EFFECT, mobEffect.getId());
      return BuiltInRegistries.MOB_EFFECT.getHolderOrThrow(key);
   }
}
