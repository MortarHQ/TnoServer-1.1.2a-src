package io.github.Memoires.mysticism.registry.effects;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.Memoires.mysticism.effect.ability.AuraHealingEffect;
import io.github.Memoires.mysticism.effect.ability.AwakenedForesightEffect;
import io.github.Memoires.mysticism.effect.ability.CollectiveEffect;
import io.github.Memoires.mysticism.effect.ability.CounteringEffect;
import io.github.Memoires.mysticism.effect.ability.CultivatingEffect;
import io.github.Memoires.mysticism.effect.ability.DazzledEffect;
import io.github.Memoires.mysticism.effect.ability.DragonBurnEffect;
import io.github.Memoires.mysticism.effect.ability.FixationEffect;
import io.github.Memoires.mysticism.effect.ability.GravityFluctuationEffect;
import io.github.Memoires.mysticism.effect.ability.IntangibleEffect;
import io.github.Memoires.mysticism.effect.ability.LightningModeEffect;
import io.github.Memoires.mysticism.effect.ability.MarkedForDeathEffect;
import io.github.Memoires.mysticism.effect.ability.PayItForwardEffect;
import io.github.Memoires.mysticism.effect.ability.ProviderBoostEffect;
import io.github.Memoires.mysticism.effect.ability.ReducerHolyCoatEffect;
import io.github.Memoires.mysticism.effect.ability.ReducerPurityEdgeEffect;
import io.github.Memoires.mysticism.effect.ability.RevenantHorrorEffect;
import io.github.Memoires.mysticism.effect.ability.RupturingEffect;
import io.github.Memoires.mysticism.effect.ability.SanctifyingLightEffect;
import io.github.Memoires.mysticism.effect.ability.SeveredArmEffect;
import io.github.Memoires.mysticism.effect.ability.SeveredLegEffect;
import io.github.Memoires.mysticism.effect.ability.StagnateEffect;
import io.github.Memoires.mysticism.effect.ability.TailedBeastCloakEffect;
import io.github.Memoires.mysticism.effect.ability.UnstableRequiemEffect;
import io.github.Memoires.mysticism.effect.ability.constant.ConstantDestructionEffect;
import io.github.Memoires.mysticism.effect.ability.constant.ConstantEnergyEffect;
import io.github.Memoires.mysticism.effect.ability.constant.ConstantHealthEffect;
import io.github.Memoires.mysticism.effect.debuff.BrimstoneFlamesEffect;
import io.github.Memoires.mysticism.effect.debuff.ConstantEnergyDebuffEffect;
import io.github.Memoires.mysticism.effect.debuff.EnkiduEffect;
import io.github.Memoires.mysticism.effect.debuff.EternalPermafrostEffect;
import io.github.Memoires.mysticism.effect.debuff.ImbalancedEffect;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import java.awt.Color;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class MysticismMobEffects {
   private static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create("mysticism", Registries.MOB_EFFECT);
   public static final RegistrySupplier<MobEffect> AURA_HEALING = MOB_EFFECTS.register("aura_healing", AuraHealingEffect::new);
   public static final RegistrySupplier<MobEffect> AWAKENED_FORESIGHT = MOB_EFFECTS.register("awakened_foresight", AwakenedForesightEffect::new);
   public static final RegistrySupplier<MobEffect> BRIMSTONE_FLAMES = MOB_EFFECTS.register("brimstone_flames", BrimstoneFlamesEffect::new);
   public static final RegistrySupplier<MobEffect> COLLECTIVE = MOB_EFFECTS.register("collective", CollectiveEffect::new);
   public static final RegistrySupplier<MobEffect> CONSTANT_DESTRUCTION = MOB_EFFECTS.register("constant_destruction", ConstantDestructionEffect::new);
   public static final RegistrySupplier<MobEffect> CONSTANT_ENERGY = MOB_EFFECTS.register("constant_energy", ConstantEnergyEffect::new);
   public static final RegistrySupplier<MobEffect> CONSTANT_ENERGY_DEBUFF = MOB_EFFECTS.register("constant_energy_debuff", ConstantEnergyDebuffEffect::new);
   public static final RegistrySupplier<MobEffect> CONSTANT_HEALTH = MOB_EFFECTS.register("constant_health", ConstantHealthEffect::new);
   public static final RegistrySupplier<MobEffect> CULTIVATING = MOB_EFFECTS.register("cultivating", CultivatingEffect::new);
   public static final RegistrySupplier<MobEffect> COUNTERING = MOB_EFFECTS.register("countering", CounteringEffect::new);
   public static final RegistrySupplier<MobEffect> DAZZLED = MOB_EFFECTS.register("dazzled", DazzledEffect::new);
   public static final RegistrySupplier<MobEffect> DRAGON_BURN = MOB_EFFECTS.register("dragon_burn", DragonBurnEffect::new);
   public static final RegistrySupplier<MobEffect> ETERNAL_PERMAFROST = MOB_EFFECTS.register("eternal_permafrost", EternalPermafrostEffect::new);
   public static final RegistrySupplier<MobEffect> FIXATION = MOB_EFFECTS.register("fixation", FixationEffect::new);
   public static final RegistrySupplier<MobEffect> GRAVITY_FLUCTUATION = MOB_EFFECTS.register("gravity_fluctuation", GravityFluctuationEffect::new);
   public static final RegistrySupplier<MobEffect> INTANGIBLE = MOB_EFFECTS.register("intangible", IntangibleEffect::new);
   public static final RegistrySupplier<MobEffect> IMBALANCED = MOB_EFFECTS.register("imbalanced", ImbalancedEffect::new);
   public static final RegistrySupplier<MobEffect> LIGHTNING_MODE = MOB_EFFECTS.register("lightning_mode", LightningModeEffect::new);
   public static final RegistrySupplier<MobEffect> MARKED_FOR_DEATH = MOB_EFFECTS.register("marked_for_death", MarkedForDeathEffect::new);
   public static final RegistrySupplier<MobEffect> PAY_IT_FORWARD = MOB_EFFECTS.register("pay_it_forward", PayItForwardEffect::new);
   public static final RegistrySupplier<MobEffect> PRESSURE = MOB_EFFECTS.register(
      "pressure", () -> new TensuraMobEffect(MobEffectCategory.HARMFUL, new Color(89, 10, 126).getRGB())
   );
   public static final RegistrySupplier<MobEffect> PROVIDER_BOOST = MOB_EFFECTS.register("provider_boost", ProviderBoostEffect::new);
   public static final RegistrySupplier<MobEffect> REDUCER_HOLY_COAT = MOB_EFFECTS.register("reducer_holy_coat", ReducerHolyCoatEffect::new);
   public static final RegistrySupplier<MobEffect> REDUCER_PURITY_EDGE = MOB_EFFECTS.register("reducer_purity_edge", ReducerPurityEdgeEffect::new);
   public static final RegistrySupplier<MobEffect> REVENANT_HORROR = MOB_EFFECTS.register("revenants_horror", RevenantHorrorEffect::new);
   public static final RegistrySupplier<MobEffect> RUPTURING = MOB_EFFECTS.register("rupturing", RupturingEffect::new);
   public static final RegistrySupplier<MobEffect> SANCTIFYING_LIGHT = MOB_EFFECTS.register("sanctifying_light", SanctifyingLightEffect::new);
   public static final RegistrySupplier<MobEffect> SEVERED_ARM = MOB_EFFECTS.register("severed_arm", SeveredArmEffect::new);
   public static final RegistrySupplier<MobEffect> SEVERED_LEG = MOB_EFFECTS.register("severed_leg", SeveredLegEffect::new);
   public static final RegistrySupplier<MobEffect> STAGNATE = MOB_EFFECTS.register("stagnate", StagnateEffect::new);
   public static final RegistrySupplier<MobEffect> STILLNESS = MOB_EFFECTS.register(
      "stillness", () -> new TensuraMobEffect(MobEffectCategory.BENEFICIAL, new Color(89, 10, 126).getRGB())
   );
   public static final RegistrySupplier<MobEffect> TAILED_BEAST_CLOAK = MOB_EFFECTS.register("tailed_beast_cloak", TailedBeastCloakEffect::new);
   public static final RegistrySupplier<MobEffect> UNSTABLE_REQUIEM = MOB_EFFECTS.register("unstable_requiem", UnstableRequiemEffect::new);
   public static final RegistrySupplier<MobEffect> ENKIDU = MOB_EFFECTS.register("enkidu", EnkiduEffect::new);

   public static void init() {
      MOB_EFFECTS.register();
   }
}
