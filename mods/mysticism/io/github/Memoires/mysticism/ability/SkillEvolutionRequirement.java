package io.github.Memoires.mysticism.ability;

import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.race.api.Races;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.data.TensuraRaceTags;
import io.github.manasmods.tensura.registry.TensuraStats;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.SubordinateHelper;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;

public class SkillEvolutionRequirement {
   public float getProgress(LivingEntity entity) {
      return 0.0F;
   }

   public Component getRequirementComponent(LivingEntity entity) {
      return Component.empty();
   }

   public static class AbilityRequirement extends SkillEvolutionRequirement {
      private final boolean mastered;
      private final ManasSkill requirementAbility;

      public AbilityRequirement(ManasSkill requirementAbility, boolean mastered) {
         this.requirementAbility = requirementAbility;
         this.mastered = mastered;
      }

      @Override
      public float getProgress(LivingEntity entity) {
         if (this.isMastered()) {
            return SkillUtils.isSkillMastered(entity, this.getRequirementAbility()) ? 1.0F : 0.0F;
         } else {
            return SkillUtils.hasSkill(entity, this.getRequirementAbility()) ? 1.0F : 0.0F;
         }
      }

      @Override
      public Component getRequirementComponent(LivingEntity entity) {
         Component name = this.getRequirementAbility().getChatDisplayName(true);
         return this.isMastered()
            ? Component.translatable("tensura.evolution_menu.mastery_requirement", new Object[]{name})
            : Component.translatable("tensura.evolution_menu.acquire_requirement", new Object[]{name});
      }

      public boolean isMastered() {
         return this.mastered;
      }

      public ManasSkill getRequirementAbility() {
         return this.requirementAbility;
      }
   }

   public static class AwakenRequirement extends SkillEvolutionRequirement {
      @Override
      public float getProgress(LivingEntity entity) {
         IExistence existence = TensuraStorages.getExistenceFrom(entity);
         return !existence.isTrueDemonLord() && !existence.isTrueHero() ? 1.0F : 0.0F;
      }

      @Override
      public Component getRequirementComponent(LivingEntity entity) {
         return Component.translatable(
            "tensura.evolution_menu.awaken_requirement",
            new Object[]{
               Component.translatable("tensura.evolve.demon_lord").withStyle(ChatFormatting.DARK_PURPLE),
               Component.translatable("tensura.evolve.hero").withStyle(ChatFormatting.GOLD)
            }
         );
      }
   }

   public static class BiomeRequirement extends SkillEvolutionRequirement {
      private final ResourceKey<Biome> requirement;

      public BiomeRequirement(ResourceKey<Biome> requirement) {
         this.requirement = requirement;
      }

      @Override
      public float getProgress(LivingEntity entity) {
         Holder<Biome> biomeHolder = entity.level().getBiome(entity.blockPosition());
         return biomeHolder.is(this.requirement) ? 1.0F : 0.0F;
      }

      @Override
      public Component getRequirementComponent(LivingEntity entity) {
         return Component.translatable("mysticism.evolution_menu.biome_requirement", new Object[]{this.requirement.location().toString()});
      }

      public ResourceKey<Biome> getRequirement() {
         return this.requirement;
      }
   }

   public static class BossRequirement extends SkillEvolutionRequirement {
      private final int requirement;

      public BossRequirement(int requirement) {
         this.requirement = requirement;
      }

      @Override
      public float getProgress(LivingEntity entity) {
         return entity instanceof Player player ? (float)TensuraStats.getBossDefeated(player) / this.requirement : 0.0F;
      }

      @Override
      public Component getRequirementComponent(LivingEntity entity) {
         return Component.translatable("tensura.evolution_menu.boss_kill_requirement", new Object[]{this.getRequirement()});
      }

      public int getRequirement() {
         return this.requirement;
      }
   }

   public static class DivinityRequirement extends SkillEvolutionRequirement {
      private final ManasRace requirement;

      public DivinityRequirement(ManasRace requirement) {
         this.requirement = requirement;
      }

      @Override
      public float getProgress(LivingEntity entity) {
         Optional<ManasRaceInstance> optional = RaceAPI.getRaceFrom(entity).getRace();
         return optional.isPresent() && optional.get().is(TensuraRaceTags.DIVINE) ? 1.0F : 0.0F;
      }

      @Override
      public Component getRequirementComponent(LivingEntity entity) {
         return Component.translatable("mystic.evolution_menu.divine_requirement", new Object[]{this.getRequirement()});
      }

      public ManasRace getRequirement() {
         return this.requirement;
      }
   }

   public static class EPRequirement extends SkillEvolutionRequirement {
      private final double requirement;

      public EPRequirement(double requirement) {
         this.requirement = requirement;
      }

      @Override
      public float getProgress(LivingEntity entity) {
         return (float)(EnergyHelper.getBaseMaxEP(entity) / this.getRequirement());
      }

      @Override
      public Component getRequirementComponent(LivingEntity entity) {
         return Component.translatable("tensura.evolution_menu.ep_requirement", new Object[]{this.getRequirement()});
      }

      public double getRequirement() {
         return this.requirement;
      }
   }

   public static class FightRequirement extends SkillEvolutionRequirement {
      @Override
      public float getProgress(LivingEntity entity) {
         LivingEntity lastHurtBy = entity.getLastHurtByMob();
         return lastHurtBy != null
               && SubordinateHelper.getSubordinateOwnerUUID(lastHurtBy) == null
               && lastHurtBy.getHealth() < lastHurtBy.getMaxHealth() / 2.0F
               && EnergyHelper.getMaxEP(lastHurtBy) >= EnergyHelper.getMaxEP(entity)
               && !SubordinateHelper.isAlly(entity, lastHurtBy)
            ? 1.0F
            : 0.0F;
      }

      @Override
      public Component getRequirementComponent(LivingEntity entity) {
         return Component.translatable("mysticism.evolution_menu.fight_requirement");
      }
   }

   public static class ItemConsumeRequirement extends SkillEvolutionRequirement {
      private final int requirement;
      private final Item requirementItem;

      public ItemConsumeRequirement(Item item, int requirement) {
         this.requirementItem = item;
         this.requirement = requirement;
      }

      @Override
      public float getProgress(LivingEntity entity) {
         int essence = 0;
         if (entity instanceof Player player) {
            if (player.isLocalPlayer()) {
               essence = ((LocalPlayer)entity).getStats().getValue(Stats.ITEM_USED.get(this.getRequirementItem()));
            } else {
               essence = ((ServerPlayer)entity).getStats().getValue(Stats.ITEM_USED.get(this.getRequirementItem()));
            }
         }

         return (float)essence / this.getRequirement();
      }

      @Override
      public Component getRequirementComponent(LivingEntity entity) {
         return Component.translatable(
            "tensura.evolution_menu.consume_requirement", new Object[]{this.getRequirement(), this.getRequirementItem().getDefaultInstance().getDisplayName()}
         );
      }

      public int getRequirement() {
         return this.requirement;
      }

      public Item getRequirementItem() {
         return this.requirementItem;
      }
   }

   public static class RaceRequirement extends SkillEvolutionRequirement {
      private final ManasRace requirement;

      public RaceRequirement(ManasRace requirement) {
         this.requirement = requirement;
      }

      @Override
      public float getProgress(LivingEntity entity) {
         Races raceData = RaceAPI.getRaceFrom(entity);
         ManasRaceInstance race = (ManasRaceInstance)raceData.getRace().orElse(null);
         if (race != null) {
            ManasRace desiredRace = (ManasRace)RaceAPI.getRaceRegistry().get(this.requirement.getRegistryName());
            if (desiredRace != null) {
               return race.getRace() == desiredRace ? 1.0F : 0.0F;
            }
         }

         return 0.0F;
      }

      @Override
      public Component getRequirementComponent(LivingEntity entity) {
         return Component.translatable("mysticism.evolution_menu.race_requirement", new Object[]{this.getRequirement()});
      }

      public ManasRace getRequirement() {
         return this.requirement;
      }
   }

   public static class SpiritualRequirement extends SkillEvolutionRequirement {
      private final ManasRace requirement;

      public SpiritualRequirement(ManasRace requirement) {
         this.requirement = requirement;
      }

      @Override
      public float getProgress(LivingEntity entity) {
         Optional<ManasRaceInstance> optional = RaceAPI.getRaceFrom(entity).getRace();
         return optional.isPresent() && optional.get().is(TensuraRaceTags.SPIRITUAL) ? 1.0F : 0.0F;
      }

      @Override
      public Component getRequirementComponent(LivingEntity entity) {
         return Component.translatable("mysticism.evolution_menu.spiritual_requirement", new Object[]{this.getRequirement()});
      }

      public ManasRace getRequirement() {
         return this.requirement;
      }
   }
}
