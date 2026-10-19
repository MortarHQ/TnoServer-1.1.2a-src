package org.crypticdev.stextras.registary.effects;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import java.awt.Color;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import org.crypticdev.stextras.effect.skill.WispModeEffect;

public class STExtrasMobEffects {
   private static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create("stextras", Registries.MOB_EFFECT);
   public static final RegistrySupplier<MobEffect> EARTHEN_SENSE = MOB_EFFECTS.register(
      "earth_sense", () -> new TensuraMobEffect(MobEffectCategory.BENEFICIAL, new Color(216, 5, 82).getRGB())
   );
   public static final RegistrySupplier<MobEffect> WISP_MODE = MOB_EFFECTS.register("wisp_mode", WispModeEffect::new);

   public static void init() {
      MOB_EFFECTS.register();
   }
}
