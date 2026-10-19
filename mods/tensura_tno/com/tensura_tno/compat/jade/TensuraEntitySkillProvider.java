package com.tensura_tno.compat.jade;

import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.TensuraSkill;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum TensuraEntitySkillProvider implements IEntityComponentProvider, IServerDataProvider<EntityAccessor> {
   INSTANCE;

   public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath("tensura_tno", "entity_skills");
   private static final String DATA_KEY = "TensuraTNOEntitySkills";
   private static final String SKILLS_KEY = "Skills";
   private static final String TOTAL_KEY = "Total";
   private static final String SOURCE_KEY = "Source";
   private static final int NORMAL_LIMIT = 5;
   private static final int DETAIL_LIMIT = 15;

   public ResourceLocation getUid() {
      return UID;
   }

   public boolean isRequired() {
      return false;
   }

   public int getDefaultPriority() {
      return 6000;
   }

   public boolean shouldRequestData(EntityAccessor accessor) {
      Entity entity = accessor.getEntity();
      return entity instanceof LivingEntity;
   }

   public void appendServerData(CompoundTag data, EntityAccessor accessor) {
      Entity entity = accessor.getEntity();
      if (entity instanceof LivingEntity living) {
         List<ResourceLocation> ids = getSkillIds(living);
         if (!ids.isEmpty()) {
            CompoundTag root = new CompoundTag();
            ListTag list = new ListTag();

            for (int i = 0; i < Math.min(ids.size(), 15); i++) {
               list.add(StringTag.valueOf(ids.get(i).toString()));
            }

            root.put("Skills", list);
            root.putInt("Total", ids.size());
            root.putString("Source", entity.getStringUUID());
            data.put("TensuraTNOEntitySkills", root);
         }
      }
   }

   public void appendTooltip(ITooltip tooltip, EntityAccessor accessor, IPluginConfig config) {
      if (config.get(UID)) {
         CompoundTag serverData = accessor.getServerData();
         if (serverData.contains("TensuraTNOEntitySkills", 10)) {
            CompoundTag root = serverData.getCompound("TensuraTNOEntitySkills");
            ListTag skillList = root.getList("Skills", 8);
            if (!skillList.isEmpty()) {
               List<String> skillIds = new ArrayList<>();

               for (int i = 0; i < skillList.size(); i++) {
                  skillIds.add(skillList.getString(i));
               }

               appendSkillTooltip(tooltip, skillIds, Math.max(root.getInt("Total"), skillIds.size()), accessor.showDetails());
            }
         }
      }
   }

   private static void appendSkillTooltip(ITooltip tooltip, List<String> skillIds, int total, boolean showDetails) {
      int limit = showDetails ? 15 : 5;
      int shown = Math.min(limit, skillIds.size());

      for (int i = 0; i < shown; i++) {
         String rawId = skillIds.get(i);
         Component skillName = getSkillName(rawId);
         tooltip.add(Component.literal(" - ").withStyle(ChatFormatting.GRAY).append(skillName));
      }

      if (total > shown) {
         tooltip.add(Component.translatable("tooltip.tensura_tno.jade.skills.more", new Object[]{total - shown}).withStyle(ChatFormatting.GRAY));
      }
   }

   private static List<ResourceLocation> getSkillIds(LivingEntity living) {
      List<ResourceLocation> ids = new ArrayList<>();

      try {
         for (ManasSkillInstance instance : SkillAPI.getSkillsFrom(living).getLearnedSkills()) {
            if (instance != null && !(instance.getMastery() < 0.0)) {
               ManasSkill skill = getSkill(instance);
               if (skill != null && skill.getRegistryName() != null) {
                  ids.add(skill.getRegistryName());
               }
            }
         }
      } catch (RuntimeException exception) {
         return List.of();
      }

      ids.sort(Comparator.comparing(ResourceLocation::toString));
      return ids;
   }

   private static ManasSkill getSkill(ManasSkillInstance instance) {
      if (instance == null) {
         return null;
      }

      try {
         return instance.getSkill();
      } catch (RuntimeException ignored) {
         return null;
      }
   }

   private static Component getSkillName(String rawId) {
      ResourceLocation id = ResourceLocation.tryParse(rawId);
      if (id == null) {
         return Component.literal(rawId);
      }

      try {
         ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(id);
         if (skill != null) {
            Component name = skill instanceof TensuraSkill tensuraSkill ? tensuraSkill.getColoredName() : skill.getName();
            if (name != null) {
               return name;
            }
         }
      } catch (RuntimeException var5) {
      }

      return Component.literal(id.toString());
   }
}
