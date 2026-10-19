package com.github.L_Ender.cataclysm.init;

import com.github.L_Ender.cataclysm.effects.EffectAbyssal_Burn;
import com.github.L_Ender.cataclysm.effects.EffectAbyssal_Curse;
import com.github.L_Ender.cataclysm.effects.EffectAbyssal_Fear;
import com.github.L_Ender.cataclysm.effects.EffectBlazing_Brand;
import com.github.L_Ender.cataclysm.effects.EffectBlessing_Of_Amethyst;
import com.github.L_Ender.cataclysm.effects.EffectBone_Fracture;
import com.github.L_Ender.cataclysm.effects.EffectCurse_Of_Desert;
import com.github.L_Ender.cataclysm.effects.EffectGhostForm;
import com.github.L_Ender.cataclysm.effects.EffectGhost_Sickness;
import com.github.L_Ender.cataclysm.effects.EffectMonstrous;
import com.github.L_Ender.cataclysm.effects.EffectStun;
import com.github.L_Ender.cataclysm.effects.EffectWetness;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEffect {
   public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, "cataclysm");
   public static final DeferredHolder<MobEffect, MobEffect> EFFECTMONSTROUS = EFFECTS.register("monstrous", EffectMonstrous::new);
   public static final DeferredHolder<MobEffect, MobEffect> EFFECTBLAZING_BRAND = EFFECTS.register("blazing_brand", EffectBlazing_Brand::new);
   public static final DeferredHolder<MobEffect, MobEffect> EFFECTSTUN = EFFECTS.register("stun", EffectStun::new);
   public static final DeferredHolder<MobEffect, MobEffect> EFFECTABYSSAL_BURN = EFFECTS.register("abyssal_burn", EffectAbyssal_Burn::new);
   public static final DeferredHolder<MobEffect, MobEffect> EFFECTBONE_FRACTURE = EFFECTS.register("bone_fracture", EffectBone_Fracture::new);
   public static final DeferredHolder<MobEffect, MobEffect> EFFECTABYSSAL_FEAR = EFFECTS.register("abyssal_fear", EffectAbyssal_Fear::new);
   public static final DeferredHolder<MobEffect, MobEffect> EFFECTABYSSAL_CURSE = EFFECTS.register("abyssal_curse", EffectAbyssal_Curse::new);
   public static final DeferredHolder<MobEffect, MobEffect> EFFECTBLESSING_OF_AMETHYST = EFFECTS.register(
      "blessing_of_amethyst", EffectBlessing_Of_Amethyst::new
   );
   public static final DeferredHolder<MobEffect, MobEffect> EFFECTCURSE_OF_DESERT = EFFECTS.register("curse_of_desert", EffectCurse_Of_Desert::new);
   public static final DeferredHolder<MobEffect, MobEffect> EFFECTGHOST_FORM = EFFECTS.register("ghost_form", EffectGhostForm::new);
   public static final DeferredHolder<MobEffect, MobEffect> EFFECTGHOST_SICKNESS = EFFECTS.register("ghost_sickness", EffectGhost_Sickness::new);
   public static final DeferredHolder<MobEffect, MobEffect> EFFECTWETNESS = EFFECTS.register("wetness", EffectWetness::new);
}
