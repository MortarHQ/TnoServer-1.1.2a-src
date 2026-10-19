package io.github.Memoires.mysticism.data;

import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

public class MysticismSkillTags {
   public static final TagKey<ManasSkill> LIGHT_SKILLS = modTag("light_skills");
   public static final TagKey<ManasSkill> DARKNESS_SKILLS = modTag("darkness_skills");
   public static final TagKey<ManasSkill> ICE_SKILLS = modTag("ice_skills");
   public static final TagKey<ManasSkill> VIRTUE_SKILLS = modTag("virtue_skills");
   public static final TagKey<ManasSkill> SIN_SKILLS = modTag("sin_skills");
   public static final TagKey<ManasSkill> RESISTANCES = modTag("resistances");
   public static final TagKey<ManasSkill> NULLIFICATIONS = modTag("nullifications");
   public static final TagKey<ManasSkill> RESTRICTED_LEARNABLE = modTag("restricted_learnable");

   static TagKey<ManasSkill> modTag(String name) {
      return create(ResourceLocation.fromNamespaceAndPath("tensura", name));
   }

   static TagKey<ManasSkill> create(ResourceLocation name) {
      return TagKey.create(SkillAPI.getSkillRegistryKey(), name);
   }
}
