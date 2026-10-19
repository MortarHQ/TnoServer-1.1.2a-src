package io.github.manasmods.tensura_ftb.rewards;

import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.reward.Reward;
import dev.ftb.mods.ftbquests.quest.reward.RewardType;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.ability.skill.resist.ResistSkill;
import io.github.manasmods.tensura.registry.skill.CommonSkills;
import io.github.manasmods.tensura_ftb.tasks.AbilityTask;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class AbilityReward extends Reward {
   public static RewardType ABILITY;
   private ResourceLocation skill = CommonSkills.SELF_REGENERATION.getId();
   private AbilityTask.AcquisitionStatus status = AbilityTask.AcquisitionStatus.LEARNT;
   private int removeTime = -1;

   public AbilityReward(long id, Quest quest) {
      super(id, quest);
   }

   public RewardType getType() {
      return ABILITY;
   }

   public void writeData(CompoundTag nbt, Provider provider) {
      super.writeData(nbt, provider);
      nbt.putString("ability", this.skill.toString());
      nbt.putString("status", this.status.toString());
      nbt.putInt("removeTime", this.removeTime);
   }

   public void readData(CompoundTag nbt, Provider provider) {
      super.readData(nbt, provider);
      this.skill = ResourceLocation.tryParse(nbt.getString("ability"));
      this.status = AbilityTask.AcquisitionStatus.valueOf(nbt.getString("status"));
      this.removeTime = nbt.getInt("removeTime");
   }

   public void writeNetData(RegistryFriendlyByteBuf buffer) {
      super.writeNetData(buffer);
      buffer.writeResourceLocation(this.skill);
      buffer.writeEnum(this.status);
      buffer.writeInt(this.removeTime);
   }

   public void readNetData(RegistryFriendlyByteBuf buffer) {
      super.readNetData(buffer);
      this.skill = buffer.readResourceLocation();
      this.status = (AbilityTask.AcquisitionStatus)buffer.readEnum(AbilityTask.AcquisitionStatus.class);
      this.removeTime = buffer.readInt();
   }

   @OnlyIn(Dist.CLIENT)
   public void fillConfigGroup(ConfigGroup config) {
      super.fillConfigGroup(config);
      config.addString("ability", this.skill.toString(), v -> this.skill = ResourceLocation.tryParse(v), CommonSkills.SELF_REGENERATION.getId().toString())
         .setNameKey("tensura_ftb.task.ability.ability");
      config.addString("status", this.status.toString(), v -> this.status = AbilityTask.AcquisitionStatus.valueOf(v), "LEARNT")
         .setNameKey("tensura_ftb.task.ability.status");
      config.addInt("removeTime", this.removeTime, v -> this.removeTime = v, -1, -2, Integer.MAX_VALUE).setNameKey("tensura_ftb.task.ability.remove_time");
   }

   public void claim(ServerPlayer player, boolean notify) {
      ManasSkill manasSkill = (ManasSkill)SkillAPI.getSkillRegistry().get(this.skill);
      if (manasSkill != null) {
         TensuraSkillInstance instance = new TensuraSkillInstance(manasSkill);
         switch (this.status) {
            case AVAILABLE:
               if (manasSkill instanceof ResistSkill resist) {
                  instance.setMastery(resist.getLearningPointRequirement() * -1);
               } else if (manasSkill instanceof TensuraSkill tensuraSkill) {
                  instance.setMastery(tensuraSkill.getAcquirementMastery(player));
               } else {
                  instance.setMastery(TensuraSkill.BASE_CONFIG.Learning.learningPointRequirement * -1);
               }
               break;
            case LEARNT:
               instance.setMastery(0.0);
               break;
            case MASTERED:
               instance.setMastery(instance.getMaxMastery());
         }

         SkillHelper.learnSkill(player, instance, this.removeTime);
      }
   }

   @OnlyIn(Dist.CLIENT)
   public Icon getAltIcon() {
      ManasSkill manasSkill = (ManasSkill)SkillAPI.getSkillRegistry().get(this.skill);
      return manasSkill != null ? Icon.getIcon(manasSkill.getSkillIcon()) : super.getAltIcon();
   }
}
