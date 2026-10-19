package io.github.manasmods.tensura_ftb.rewards;

import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.reward.Reward;
import dev.ftb.mods.ftbquests.quest.reward.RewardType;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.ability.battlewill.Battlewill;
import io.github.manasmods.tensura.ability.magic.Magic;
import io.github.manasmods.tensura.ability.magic.Magic.MagicType;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura_ftb.tasks.AbilityTask;
import java.util.List;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Predicate;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class RandomAbilityReward extends Reward {
   public static RewardType RANDOM_ABILITY;
   private RandomAbilityReward.RandomType random = RandomAbilityReward.RandomType.ALL;
   private AbilityTask.AcquisitionStatus status = AbilityTask.AcquisitionStatus.LEARNT;
   private int removeTime = -1;
   private boolean checkForNewAbility = true;

   public RandomAbilityReward(long id, Quest quest) {
      super(id, quest);
   }

   public RewardType getType() {
      return RANDOM_ABILITY;
   }

   public void writeData(CompoundTag nbt, Provider provider) {
      super.writeData(nbt, provider);
      nbt.putString("random", this.random.toString());
      nbt.putString("status", this.status.toString());
      nbt.putInt("removeTime", this.removeTime);
      nbt.putBoolean("checkForNewAbility", this.checkForNewAbility);
   }

   public void readData(CompoundTag nbt, Provider provider) {
      super.readData(nbt, provider);
      this.random = RandomAbilityReward.RandomType.valueOf(nbt.getString("random"));
      this.status = AbilityTask.AcquisitionStatus.valueOf(nbt.getString("status"));
      this.removeTime = nbt.getInt("removeTime");
      this.checkForNewAbility = nbt.getBoolean("checkForNewAbility");
   }

   public void writeNetData(RegistryFriendlyByteBuf buffer) {
      super.writeNetData(buffer);
      buffer.writeEnum(this.random);
      buffer.writeEnum(this.status);
      buffer.writeInt(this.removeTime);
      buffer.writeBoolean(this.checkForNewAbility);
   }

   public void readNetData(RegistryFriendlyByteBuf buffer) {
      super.readNetData(buffer);
      this.random = (RandomAbilityReward.RandomType)buffer.readEnum(RandomAbilityReward.RandomType.class);
      this.status = (AbilityTask.AcquisitionStatus)buffer.readEnum(AbilityTask.AcquisitionStatus.class);
      this.removeTime = buffer.readInt();
      this.checkForNewAbility = buffer.readBoolean();
   }

   @OnlyIn(Dist.CLIENT)
   public void fillConfigGroup(ConfigGroup config) {
      super.fillConfigGroup(config);
      config.addString("random", this.random.toString(), v -> this.random = RandomAbilityReward.RandomType.valueOf(v), "ALL")
         .setNameKey("tensura_ftb.reward.ability.random");
      config.addString("status", this.status.toString(), v -> this.status = AbilityTask.AcquisitionStatus.valueOf(v), "LEARNT")
         .setNameKey("tensura_ftb.task.ability.status");
      config.addInt("removeTime", this.removeTime, v -> this.removeTime = v, -1, -2, Integer.MAX_VALUE).setNameKey("tensura_ftb.task.ability.remove_time");
      config.addBool("checkForNewAbility", this.checkForNewAbility, v -> this.checkForNewAbility = v, Boolean.TRUE)
         .setNameKey("tensura_ftb.reward.ability.new_ability");
   }

   public void claim(ServerPlayer player, boolean notify) {
      if (player != null) {
         Skills skills = SkillAPI.getSkillsFrom(player);
         List<ManasSkill> list = SkillAPI.getSkillRegistry()
            .entrySet()
            .stream()
            .map(Entry::getValue)
            .filter(manasSkillx -> this.checkNewAbility(skills, manasSkillx))
            .toList();
         if (!list.isEmpty()) {
            ManasSkill manasSkill = list.get(player.level().getRandom().nextInt(list.size()));
            if (manasSkill != null) {
               SkillHelper.learnSkill(player, new TensuraSkillInstance(manasSkill), this.removeTime);
            }
         }
      }
   }

   private boolean checkNewAbility(Skills skills, ManasSkill skill) {
      if (!this.random.predicate.test(skill)) {
         return false;
      }

      if (this.checkForNewAbility) {
         Optional<ManasSkillInstance> optional = skills.getSkill(skill);
         if (optional.isEmpty()) {
            return true;
         }

         if (this.status.equals(AbilityTask.AcquisitionStatus.LEARNT)) {
            return optional.get().getMastery() < 0.0;
         }

         if (this.status.equals(AbilityTask.AcquisitionStatus.MASTERED)) {
            return optional.get().getMastery() < optional.get().getMaxMastery();
         }
      }

      return true;
   }

   public enum RandomType {
      ALL(skill -> true),
      SKILL(skill -> skill instanceof Skill),
      RESISTANCE(skill -> skill instanceof Skill pSkill && pSkill.getType().equals(SkillType.RESISTANCE)),
      COMMON(skill -> skill instanceof Skill pSkill && pSkill.getType().equals(SkillType.COMMON)),
      INTRINSIC(skill -> skill instanceof Skill pSkill && pSkill.getType().equals(SkillType.INTRINSIC)),
      EXTRA(skill -> skill instanceof Skill pSkill && pSkill.getType().equals(SkillType.EXTRA)),
      UNIQUE(skill -> skill instanceof Skill pSkill && pSkill.getType().equals(SkillType.UNIQUE)),
      ULTIMATE(skill -> skill instanceof Skill pSkill && pSkill.getType().equals(SkillType.ULTIMATE)),
      MAGIC(skill -> skill instanceof Magic),
      ELEMENTAL(skill -> skill instanceof Magic magic && magic.getType().equals(MagicType.ASPECTUAL)),
      SPIRITUAL(skill -> skill instanceof Magic magic && magic.getType().equals(MagicType.SPIRITUAL)),
      SUMMONING(skill -> skill instanceof Magic magic && magic.getType().equals(MagicType.SUMMONING)),
      MISC(skill -> skill instanceof Magic magic && magic.getType().equals(MagicType.MISC)),
      BATTLEWILL(skill -> skill instanceof Battlewill);

      public final Predicate<ManasSkill> predicate;

      RandomType(Predicate<ManasSkill> predicate) {
         this.predicate = predicate;
      }
   }
}
