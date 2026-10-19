package com.mortal_cultivation.event;

import com.mortal_cultivation.registry.skill.CultivationSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.TabListNameFormat;

@EventBusSubscriber(modid = "mortal_cultivation", bus = Bus.GAME)
public class WhiteLieEventHandler {
   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void onTabListNameFormat(TabListNameFormat event) {
      Player player = event.getEntity();
      ManasSkillInstance instance = SkillUtils.getSkillOrNull(player, (ManasSkill)CultivationSkills.MISCHIEF.get());
      if (instance != null) {
         CompoundTag tag = instance.getOrCreateTag();
         if (tag.getBoolean("WhiteLieActive")) {
            event.setDisplayName(null);
         }
      }
   }
}
