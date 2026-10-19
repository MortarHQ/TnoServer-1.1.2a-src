package io.github.manasmods.tensura_ftb.tasks;

import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.TeamData;
import dev.ftb.mods.ftbquests.quest.task.AbstractBooleanTask;
import dev.ftb.mods.ftbquests.quest.task.TaskType;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.tensura.registry.skill.UniqueSkills;
import java.util.Optional;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class AbilityTask extends AbstractBooleanTask {
   public static TaskType ABILITY;
   private ResourceLocation skill = UniqueSkills.PREDATOR.getId();
   private AbilityTask.AcquisitionStatus status = AbilityTask.AcquisitionStatus.LEARNT;

   public AbilityTask(long id, Quest quest) {
      super(id, quest);
   }

   public TaskType getType() {
      return ABILITY;
   }

   public void writeData(CompoundTag nbt, Provider provider) {
      super.writeData(nbt, provider);
      nbt.putString("ability", this.skill.toString());
      nbt.putString("status", this.status.toString());
   }

   public void readData(CompoundTag nbt, Provider provider) {
      super.readData(nbt, provider);
      this.skill = ResourceLocation.tryParse(nbt.getString("ability"));
      this.status = AbilityTask.AcquisitionStatus.valueOf(nbt.getString("status"));
   }

   public void writeNetData(RegistryFriendlyByteBuf buffer) {
      super.writeNetData(buffer);
      buffer.writeResourceLocation(this.skill);
      buffer.writeEnum(this.status);
   }

   public void readNetData(RegistryFriendlyByteBuf buffer) {
      super.readNetData(buffer);
      this.skill = buffer.readResourceLocation();
      this.status = (AbilityTask.AcquisitionStatus)buffer.readEnum(AbilityTask.AcquisitionStatus.class);
   }

   @OnlyIn(Dist.CLIENT)
   public void fillConfigGroup(ConfigGroup config) {
      super.fillConfigGroup(config);
      config.addString("ability", this.skill.toString(), v -> this.skill = ResourceLocation.tryParse(v), UniqueSkills.PREDATOR.getId().toString())
         .setNameKey("tensura_ftb.task.ability.ability");
      config.addString("status", this.status.toString(), v -> this.status = AbilityTask.AcquisitionStatus.valueOf(v), "LEARNT")
         .setNameKey("tensura_ftb.task.ability.status");
   }

   @OnlyIn(Dist.CLIENT)
   public Icon getAltIcon() {
      ManasSkill manasSkill = (ManasSkill)SkillAPI.getSkillRegistry().get(this.skill);
      return manasSkill != null ? Icon.getIcon(manasSkill.getSkillIcon()) : super.getAltIcon();
   }

   public boolean canSubmit(TeamData teamData, ServerPlayer player) {
      Skills skills = SkillAPI.getSkillsFrom(player);
      Optional<ManasSkillInstance> optional = skills.getSkill(this.skill);

      return switch (this.status) {
         case AVAILABLE -> optional.isPresent();
         case LEARNT -> optional.isPresent() && optional.get().getMastery() >= 0.0;
         case MASTERED -> optional.isPresent() && optional.get().isMastered(player);
      };
   }

   public enum AcquisitionStatus {
      AVAILABLE,
      LEARNT,
      MASTERED;
   }
}
