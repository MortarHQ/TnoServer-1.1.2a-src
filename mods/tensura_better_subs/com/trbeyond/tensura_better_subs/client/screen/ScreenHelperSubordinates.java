package com.trbeyond.tensura_better_subs.client.screen;

import io.github.manasmods.tensura.ability.magic.Magic.MagicType;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.client.screen.AbilityCategoriesScreen.AbilityType;
import io.github.manasmods.tensura.client.screen.widgets.SimpleButton;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.client.ScreenHelper;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class ScreenHelperSubordinates extends ScreenHelper {
   public static void openScreen(int id, LivingEntity targetEntity) {
      switch (id) {
         case 0:
            openAbilityScreenSubordinate(AbilityType.SKILL, SkillType.UNIQUE, (MagicType)null, targetEntity);
            break;
         case 1:
            openCategoryScreenSubordinate(AbilityType.SKILL, targetEntity);
            break;
         case 2:
            openCategoryScreenSubordinate(AbilityType.MAGIC, targetEntity);
            break;
         case 3:
            openAbilityScreenSubordinate(AbilityType.BATTLEWILL, (SkillType)null, (MagicType)null, targetEntity);
            break;
         case 4:
            openScreen(new MainScreenSubordinate(targetEntity));
      }
   }

   public static void openSkillGrantScreen(Player player, LivingEntity subordinate, ResourceLocation skillGrantingSkill) {
      SkillGrantScreen screen = new SkillGrantScreen(subordinate, skillGrantingSkill);
      ScreenHelper.openScreen(screen);
      player.playSound((SoundEvent)TensuraSoundEvents.SPATIAL_STORAGE.get(), 0.5F, 1.0F);
   }

   public static void openAbilityScreenSubordinate(AbilityType abilityType, SkillType skillType, MagicType magicType, LivingEntity entity) {
      AbilitySelectionScreenEntity screen = new AbilitySelectionScreenEntity(abilityType, skillType, magicType, entity);
      openScreen(screen);
   }

   public static void openCategoryScreenSubordinate(AbilityType abilityType, LivingEntity entity) {
      AbilityCategoriesScreenEntity screen = new AbilityCategoriesScreenEntity(abilityType, entity);
      openScreen(screen);
   }

   public static List<SimpleButton> getMenuTabs(int currentTab, int guiLeft, int guiTop, LivingEntity target) {
      List<SimpleButton> list = new ArrayList<>();

      for (int i = 1; i < 8; i++) {
         if (i != currentTab) {
            if (i < 4) {
               int x = guiLeft + 4 + (i - 1) * 26;
               int y = guiTop + 2;
               int finalI1 = i;
               SimpleButton button = new SimpleButton(
                  x, y, 24, 21, getTabTexture(i), (Component)null, getMenuName(i), self -> clicked(() -> openScreen(finalI1, target))
               );
               list.add(button);
            } else {
               int x = guiLeft + 233;
               int y = guiTop + 30 + (i - 4) * 26;
               int finalI = i;
               SimpleButton button = new SimpleButton(
                  x, y, 21, 24, getTabTexture(i), (Component)null, getMenuName(i), self -> clicked(() -> openScreen(finalI, target))
               );
               list.add(button);
            }
         }
      }

      return list;
   }

   public static boolean isScreenValid(LivingEntity entity, Player player) {
      return entity.isAlive() && entity.distanceTo(player) < 8.0F;
   }
}
