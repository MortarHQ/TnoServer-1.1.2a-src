package io.github.Memoires.mysticism.neoforge.handler;

import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import java.util.Optional;
import java.util.Set;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = "mysticism")
public class MalleableHandler {
   private static final Set<ResourceKey<DamageType>> ALCHEMY_KEYS = Set.of(
      TensuraDamageTypes.FIRE_ELEMENTAL,
      TensuraDamageTypes.ICE_ELEMENTAL,
      TensuraDamageTypes.EARTH_ELEMENTAL,
      TensuraDamageTypes.WATER_ELEMENTAL,
      TensuraDamageTypes.LIGHT_ELEMENTAL,
      TensuraDamageTypes.DARKNESS_ELEMENTAL,
      TensuraDamageTypes.HOLY_DAMAGE,
      TensuraDamageTypes.POISONOUS_BREATH,
      TensuraDamageTypes.CORROSION,
      TensuraDamageTypes.LIGHTNING,
      TensuraDamageTypes.WIND_ELEMENTAL
   );

   @SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
   public static void onIncoming(LivingIncomingDamageEvent e) {
      LivingEntity target = e.getEntity();
      DamageSource source = e.getSource();
      if (!isAlchemySource(source)) {
         if (source.getEntity() instanceof LivingEntity attacker) {
            Optional<ManasSkillInstance> opt = SkillAPI.getSkillsFrom(attacker).getSkill((ManasSkill)UniqueMysticismSkills.MALLEABLE.get());
            if (!opt.isEmpty()) {
               ManasSkillInstance inst = opt.get();
               CompoundTag tag = inst.getOrCreateTag();
               if (tag.getBoolean("AlchemyActive")) {
                  String selected = tag.getString("SelectedAlchemy");
                  ResourceKey<DamageType> key = getDamageTypeFromString(selected);
                  if (ALCHEMY_KEYS.contains(key)) {
                     float dmg = e.getAmount();
                     if (!(dmg <= 0.0F)) {
                        e.setCanceled(true);
                        if (target.level() instanceof ServerLevel server) {
                           server.getServer().execute(() -> {
                              DamageSource newSource = ((TensuraSkill)inst.getSkill()).createSource(inst, attacker, key, 0);
                              target.hurt(newSource, dmg);
                           });
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private static boolean isAlchemySource(DamageSource src) {
      return src.typeHolder().unwrapKey().map(ALCHEMY_KEYS::contains).orElse(false);
   }

   private static ResourceKey<DamageType> getDamageTypeFromString(String alch) {
      return switch (alch) {
         case "Fire" -> TensuraDamageTypes.FIRE_ELEMENTAL;
         case "Ice" -> TensuraDamageTypes.ICE_ELEMENTAL;
         case "Earth" -> TensuraDamageTypes.EARTH_ELEMENTAL;
         case "Water" -> TensuraDamageTypes.WATER_ELEMENTAL;
         case "Light" -> TensuraDamageTypes.LIGHT_ELEMENTAL;
         case "Dark" -> TensuraDamageTypes.DARKNESS_ELEMENTAL;
         case "Holy" -> TensuraDamageTypes.HOLY_DAMAGE;
         case "Poison" -> TensuraDamageTypes.POISONOUS_BREATH;
         case "Corrosion" -> TensuraDamageTypes.CORROSION;
         case "Lightning" -> TensuraDamageTypes.LIGHTNING;
         case "Wind" -> TensuraDamageTypes.WIND_ELEMENTAL;
         default -> DamageTypes.PLAYER_ATTACK;
      };
   }
}
