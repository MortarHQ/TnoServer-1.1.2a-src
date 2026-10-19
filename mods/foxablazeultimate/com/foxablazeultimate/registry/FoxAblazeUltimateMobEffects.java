package com.foxablazeultimate.registry;

import com.foxablazeultimate.effect.SatanaelCombatEffect;
import com.foxablazeultimate.effect.SatanaelRampageEffect;
import com.foxablazeultimate.effect.SatanaelWeaknessEffect;
import com.foxablazeultimate.effect.VelgryndExcitationEffect;
import com.foxablazeultimate.effect.WisdomBuffEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class FoxAblazeUltimateMobEffects {
   public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, "foxablazeultimate");
   public static final DeferredHolder<MobEffect, WisdomBuffEffect> WISDOM_BUFF = MOB_EFFECTS.register("wisdom_buff", WisdomBuffEffect::new);
   public static final DeferredHolder<MobEffect, SatanaelRampageEffect> SATANAEL_RAMPAGE = MOB_EFFECTS.register("satanael_rampage", SatanaelRampageEffect::new);
   public static final DeferredHolder<MobEffect, SatanaelWeaknessEffect> SATANAEL_WEAKNESS = MOB_EFFECTS.register(
      "satanael_weakness", SatanaelWeaknessEffect::new
   );
   public static final DeferredHolder<MobEffect, SatanaelCombatEffect> SATANAEL_COMBAT = MOB_EFFECTS.register("satanael_combat", SatanaelCombatEffect::new);
   public static final DeferredHolder<MobEffect, VelgryndExcitationEffect> VELGRYND_EXCITATION = MOB_EFFECTS.register(
      "velgrynd_excitation", VelgryndExcitationEffect::new
   );

   private FoxAblazeUltimateMobEffects() {
   }

   public static void register(IEventBus modBus) {
      MOB_EFFECTS.register(modBus);
   }
}
