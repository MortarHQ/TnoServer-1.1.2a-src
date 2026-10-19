package org.crypticdev.stextras.client.screen.skilllock;

import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.impl.SkillStorage;
import io.github.manasmods.tensura.ability.TensuraSkill;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public final class STSkillLockFilters {
   public static List<ManasSkillInstance> collectAvailableAbilities(Player player) {
      List<ManasSkillInstance> abilities = new ArrayList<>();

      try {
         SkillStorage skillsContainer = SkillAPI.getSkillsFrom(player);
         if (skillsContainer != null) {
            for (ManasSkillInstance instance : skillsContainer.getLearnedSkills()) {
               if (instance != null) {
                  abilities.add(instance);
               }
            }
         }
      } catch (Throwable var5) {
      }

      abilities.removeIf(instancex -> {
         if (instancex.getDisplayName() == null) {
            return true;
         }

         ManasSkill skill = instancex.getSkill();
         if (!(skill instanceof TensuraSkill)) {
            return true;
         }

         ResourceLocation id = STSkillLockRules.skillId(instancex);
         return id == null || !STSkillLockRules.isSkillValid(id) && !STSkillLockRules.isSkillAdminLocked(id);
      });
      return abilities;
   }

   public static List<ManasSkillInstance> filterAndSort(List<ManasSkillInstance> abilities, String nameFilter, Player player) {
      List<ManasSkillInstance> filtered = new ArrayList<>();
      if (nameFilter != null && !nameFilter.isBlank()) {
         filtered.addAll(abilities.stream().filter(buildPredicate(nameFilter.trim(), player)).toList());
      } else {
         filtered.addAll(abilities);
      }

      filtered.sort(Comparator.comparing(instance -> instance.getDisplayName() == null ? "" : instance.getDisplayName().getString()));
      return filtered;
   }

   private static Predicate<ManasSkillInstance> buildPredicate(String filter, Player player) {
      String normalized = filter.toLowerCase(Locale.ROOT);
      return instance -> {
         if (instance.getSkill() instanceof TensuraSkill skill) {
            return switch (normalized) {
               case "f:active" -> skill.canBeSlotted(instance, player, 0);
               case "f:passive" -> !skill.canBeSlotted(instance, player, 0);
               case "f:mastered" -> instance.isMastered(player);
               case "f:learned" -> instance.getMastery() >= 0.0 || instance.isMastered(player);
               case "f:learning" -> instance.getMastery() < 0.0;
               case "f:temporary" -> instance.isTemporarySkill();
               case "f:toggleable" -> instance.canBeToggled(player);
               case "f:toggledon" -> instance.isToggled();
               case "f:toggledoff" -> instance.canBeToggled(player) && !instance.isToggled();
               default -> {
                  Component name = skill.getName();
                  yield name == null || name.getString().toLowerCase(Locale.ROOT).contains(normalized);
               }
            };
         } else {
            return false;
         }
      };
   }

   private STSkillLockFilters() {
   }
}
