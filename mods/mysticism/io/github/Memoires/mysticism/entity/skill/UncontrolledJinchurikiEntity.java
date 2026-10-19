package io.github.Memoires.mysticism.entity.skill;

import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.Memoires.mysticism.handler.skill.JinchurikiHandler;
import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.entity.human.CloneEntity;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.SubordinateHelper;
import java.util.Optional;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class UncontrolledJinchurikiEntity extends CloneEntity {
   public static final UniqueMysticismSkillConfig.Jinchuriki CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(UniqueMysticismSkillConfig.class)).Jinchuriki;
   private int remainingLife = CONFIG.cloakPreControlLifespan * 20;

   public UncontrolledJinchurikiEntity(EntityType<? extends UncontrolledJinchurikiEntity> type, Level level) {
      super(type, level);
   }

   public static Builder setAttributes() {
      return Player.createAttributes().add(Attributes.STEP_HEIGHT, 1.0).add(Attributes.FOLLOW_RANGE, 64.0);
   }

   public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
      return !mobEffectInstance.getEffect().equals(TensuraMobEffects.getReference(TensuraMobEffects.MAGICULE_POISON));
   }

   @NotNull
   public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnData) {
      SpawnGroupData result = super.finalizeSpawn(level, difficulty, spawnType, spawnData);
      SubordinateHelper.setAggressive(this);
      return result;
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      tag.putInt("RemainingLife", this.remainingLife);
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      if (tag.contains("RemainingLife")) {
         this.remainingLife = tag.getInt("RemainingLife");
      }
   }

   protected void dropAllDeathLoot(ServerLevel serverLevel, DamageSource damageSource) {
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide() && this.getOwner() instanceof ServerPlayer owner && owner.getCamera() != this) {
         owner.setCamera(this);
      }

      if (!this.level().isClientSide() && --this.remainingLife <= 0) {
         this.expireNaturally();
      }
   }

   private void expireNaturally() {
      if (this.getOwner() instanceof ServerPlayer owner) {
         JinchurikiHandler.restoreGameMode(owner);
         this.discard();
         Optional<ManasSkillInstance> opt = SkillAPI.getSkillsFrom(owner).getSkill((ManasSkill)UniqueMysticismSkills.JINCHURIKI.get());
         if (opt.isEmpty()) {
            return;
         }

         int cooldown = opt.get().isMastered(owner) ? CONFIG.tailedBeastCloakCooldownMastered : CONFIG.tailedBeastCloakCooldown;
         opt.get().setCoolDown(cooldown, 1);
         owner.level().playSound(null, owner.getX(), owner.getY(), owner.getZ(), TensuraSoundEvents.BUFF_DEACTIVATE, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
         owner.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0, false, false, false));
         owner.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 1, false, false, false));
      }
   }
}
