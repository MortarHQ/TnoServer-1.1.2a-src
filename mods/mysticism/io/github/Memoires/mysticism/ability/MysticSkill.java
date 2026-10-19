package io.github.Memoires.mysticism.ability;

import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.ability.skill.resist.ResistSkill;
import io.github.manasmods.tensura.ability.skill.resist.ResistSkill.ResistType;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class MysticSkill extends ResistSkill {
   private final MysticSkill.UltTier tier;

   public MysticSkill(SkillType type, ResistType resistType, MysticSkill.UltTier ultTier) {
      super(type, resistType);
      this.tier = ultTier;
   }

   public double getAcquiringSoulEnergyCost(ManasSkillInstance instance) {
      if (instance.isTemporarySkill()) {
         return 0.0;
      } else {
         return instance.getMastery() < 0.0 ? 0.0 : this.getDefaultAcquiringSoulEnergyCost();
      }
   }

   public MutableComponent getSkillTitle() {
      ResourceLocation id = this.getRegistryName();
      return id == null ? Component.empty() : Component.translatable(String.format("%s.skill.%s.title", id.getNamespace(), id.getPath().replace('/', '.')));
   }

   public MutableComponent getSkillDescription() {
      ResourceLocation id = this.getRegistryName();
      return id == null
         ? Component.empty()
         : Component.translatable(String.format("%s.skill.%s.description", id.getNamespace(), id.getPath().replace('/', '.')));
   }

   public double getDefaultAcquiringSoulEnergyCost() {
      return 0.0;
   }

   public double getDefaultAcquiringMagiculeCost() {
      return 0.0;
   }

   public boolean canBeSlotted(ManasSkillInstance instance, int mode) {
      return true;
   }

   public int getLearningPointRequirement() {
      return -1;
   }

   public List<ManasSkill> getPreviousSkillsRequirement(LivingEntity entity) {
      return List.of();
   }

   public Map<SkillEvolutionRequirement, Float> getSkillEvolutionRequirements(LivingEntity entity) {
      return Map.of();
   }

   public MysticSkill.UltTier getTier() {
      return this.tier;
   }

   public boolean canEvolve(LivingEntity entity) {
      Map<SkillEvolutionRequirement, Float> requirements = this.getSkillEvolutionRequirements(entity);

      for (SkillEvolutionRequirement req : requirements.keySet()) {
         if (req instanceof SkillEvolutionRequirement.FightRequirement) {
            return true;
         }
      }

      float totalProgress = 0.0F;
      float totalWeight = 0.0F;

      for (Entry<SkillEvolutionRequirement, Float> entry : requirements.entrySet()) {
         SkillEvolutionRequirement req = entry.getKey();
         float weight = entry.getValue();
         float progress = Math.min(1.0F, req.getProgress(entity));
         totalProgress += progress * weight;
         totalWeight += weight;
      }

      return totalProgress >= totalWeight;
   }

   public boolean canEvolveDuringFight(LivingEntity entity) {
      Map<SkillEvolutionRequirement, Float> requirements = this.getSkillEvolutionRequirements(entity);
      float totalProgress = 0.0F;
      float totalWeight = 0.0F;

      for (Entry<SkillEvolutionRequirement, Float> entry : requirements.entrySet()) {
         SkillEvolutionRequirement req = entry.getKey();
         float weight = entry.getValue();
         float progress = Math.min(1.0F, req.getProgress(entity));
         totalProgress += progress * weight;
         totalWeight += weight;
      }

      return totalProgress >= totalWeight;
   }

   public boolean noFightRequired(LivingEntity entity) {
      Map<SkillEvolutionRequirement, Float> requirements = this.getSkillEvolutionRequirements(entity);

      for (SkillEvolutionRequirement req : requirements.keySet()) {
         if (req instanceof SkillEvolutionRequirement.FightRequirement) {
            return false;
         }
      }

      return true;
   }

   public enum UltTier {
      UNIQUE("unique", Component.translatable("mysticism.skill.unique")),
      LORD("lord", Component.translatable("mysticism.skill.lord")),
      KING("king", Component.translatable("mysticism.skill.king")),
      GOD("god", Component.translatable("mysticism.skill.god"));

      private final String namespace;
      private final Component component;

      public String getNamespace() {
         return this.namespace;
      }

      public Component getSkillTitle() {
         return this.component;
      }

      UltTier(final String namespace, final Component component) {
         this.namespace = namespace;
         this.component = component;
      }
   }
}
