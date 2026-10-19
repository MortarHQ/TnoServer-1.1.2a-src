package org.crypticdev.stextras.ability.skill.unique;

import dev.architectury.registry.menu.MenuRegistry;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.crypticdev.stextras.ability.skill.subclass.IImitator;
import org.crypticdev.stextras.config.ability.skill.STExtrasUniqueSkillConfig;
import org.crypticdev.stextras.menu.ImitatorMenu;
import org.jetbrains.annotations.Nullable;

public class ImitatorSkill extends Skill implements IImitator {
   public static final STExtrasUniqueSkillConfig.Imitator CONFIG = ((STExtrasUniqueSkillConfig)ConfigRegistry.getConfig(STExtrasUniqueSkillConfig.class)).Imitator;

   public ImitatorSkill() {
      super(SkillType.UNIQUE);
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("stextras", "textures/skill/unique/imitator_skill.png");
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (entity instanceof ServerPlayer player) {
         ArrayList unlockedEntities = new ArrayList();

         for (String entry : CONFIG.entries) {
            try {
               String[] parts = entry.split("\\|");
               if (parts.length >= 4) {
                  String entityIdStr = parts[0].trim();
                  ResourceLocation entityId = ResourceLocation.tryParse(entityIdStr);
                  if (entityId != null) {
                     EntityType<?> type = (EntityType<?>)BuiltInRegistries.ENTITY_TYPE.get(entityId);
                     if ((type != EntityType.PIG || entityIdStr.equals("minecraft:pig")) && player.getStats().getValue(Stats.ENTITY_KILLED.get(type)) > 0) {
                        unlockedEntities.add(entityId);
                     }
                  }
               }
            } catch (Exception e) {
               System.err.println("Imitator Skill: Error parsing config entry: " + entry);
            }
         }

         MenuRegistry.openExtendedMenu(
            player,
            new SimpleMenuProvider((id, inv, p) -> new ImitatorMenu(id, inv, unlockedEntities), Component.translatable("stextras.skill_imitator")),
            buf -> buf.writeCollection(unlockedEntities, FriendlyByteBuf::writeResourceLocation)
         );
      }
   }

   public static List<ResourceLocation> getSkillsForEntity(ResourceLocation targetEntityId) {
      List<ResourceLocation> skills = new ArrayList<>();

      for (String entry : CONFIG.entries) {
         String[] parts = entry.split("\\|");
         if (parts.length >= 4) {
            ResourceLocation currentId = ResourceLocation.tryParse(parts[0].trim());
            if (currentId != null && currentId.equals(targetEntityId)) {
               String[] skillStrings = parts[3].split(",");

               for (String s : skillStrings) {
                  ResourceLocation sl = ResourceLocation.tryParse(s.trim());
                  if (sl != null) {
                     skills.add(sl);
                  }
               }
               break;
            }
         }
      }

      return skills;
   }

   @Override
   public int getCreationCooldown(ManasSkillInstance instance, Player player) {
      return CONFIG.cooldown;
   }
}
