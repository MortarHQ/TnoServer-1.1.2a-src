package io.github.Memoires.mysticism.neoforge.handler;

import io.github.Memoires.mysticism.damage.MysticismDamageHelper;
import io.github.Memoires.mysticism.damage.MysticismDamageTypes;
import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.storage.TensuraStorages;
import java.util.Optional;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = "mysticism")
public class CrasherHandler {
   @SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
   public static void onHurt(LivingIncomingDamageEvent event) {
      LivingEntity target = event.getEntity();
      DamageSource src = event.getSource();
      Entity attacker = src.getEntity();
      if (!MysticismDamageHelper.isVoidDamage(src)) {
         if (attacker instanceof LivingEntity livingAttacker) {
            if (!SkillUtils.hasSkillFully(livingAttacker, (ManasSkill)UniqueMysticismSkills.CRASHER.get())) {
               return;
            }

            if (!TensuraDamageHelper.isPhysicalAttack(src)) {
               return;
            }

            Optional<ManasSkillInstance> opt = SkillAPI.getSkillsFrom(livingAttacker).getSkill((ManasSkill)UniqueMysticismSkills.CRASHER.get());
            if (opt.isEmpty()) {
               return;
            }

            ManasSkillInstance inst = opt.get();
            if (TensuraStorages.getAbilityFrom(livingAttacker).isAbilityInActivePreset(inst.getSkill())) {
               float dmg = event.getAmount();
               event.setCanceled(true);
               ResourceKey<DamageType> type = MysticismDamageTypes.VOID;
               int mode = 0;
               DamageSource newSource = ((TensuraSkill)inst.getSkill()).createSource(inst, livingAttacker, type, mode);
               target.hurt(newSource, dmg);
            }
         }
      }
   }
}
