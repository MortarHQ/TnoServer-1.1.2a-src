package com.trbeyond.tensura_better_subs.mixin.tensura.ability.skill.common;

import com.trbeyond.tensura_better_subs.entity.ExpandedSkillStorage;
import com.trbeyond.tensura_better_subs.network.s2c.OpenHumanoidSkillMenuPayload;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.manascore.skill.impl.SkillStorage;
import io.github.manasmods.manascore.storage.impl.StorageManager;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.ability.skill.common.ThoughtCommunicationSkill;
import io.github.manasmods.tensura.data.TensuraEntityTags;
import io.github.manasmods.tensura.registry.skill.UniqueSkills;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ability.AbilitySlot;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import io.github.manasmods.tensura.util.SubordinateHelper;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ThoughtCommunicationSkill.class)
public class MixinThoughtCommunicationSkill extends Skill {
   private static final String SUB_UUID = "subordinateUUID";

   public MixinThoughtCommunicationSkill(SkillType type) {
      super(type);
   }

   @Inject(method = "getModes(Lio/github/manasmods/manascore/skill/api/ManasSkillInstance;)I", at = @At("HEAD"), cancellable = true, remap = false)
   private void getModeCount(ManasSkillInstance instance, CallbackInfoReturnable<Integer> cir) {
      cir.setReturnValue(5);
   }

   @Inject(
      method = "nextMode(Lnet/minecraft/world/entity/LivingEntity;Lio/github/manasmods/manascore/skill/api/ManasSkillInstance;IZ)I",
      at = @At("HEAD"),
      cancellable = true,
      remap = true
   )
   private void nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse, CallbackInfoReturnable<Integer> cir) {
      if (instance.isMastered(entity)) {
         cir.setReturnValue(mode < 4 ? mode + 1 : 0);
      } else {
         cir.setReturnValue(mode == 0 ? 2 : 0);
      }
   }

   @Inject(
      method = "getModeId(Lio/github/manasmods/manascore/skill/api/ManasSkillInstance;I)Ljava/lang/String;",
      at = @At("HEAD"),
      cancellable = true,
      remap = false
   )
   private void getModeId(ManasSkillInstance instance, int mode, CallbackInfoReturnable<String> cir) {
      cir.setReturnValue(switch (mode) {
         case 0 -> "thought_communication.movement";
         case 1 -> "thought_communication.targeting";
         case 2 -> "thought_communication.skills";
         case 3 -> "thought_communication.select_subordinate";
         case 4 -> "thought_communication.use_skill";
         default -> "default";
      });
   }

   @Inject(
      method = "onPressed(Lio/github/manasmods/manascore/skill/api/ManasSkillInstance;Lnet/minecraft/world/entity/LivingEntity;II)V",
      at = @At("HEAD"),
      cancellable = true,
      remap = true
   )
   private void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode, CallbackInfo ci) {
      switch (mode) {
         case 2:
            this.attemptOpenSubordinateStatusMenu(instance, entity);
            ci.cancel();
            break;
         case 3:
            this.attemptSelectSubordinate(instance, entity, keyNumber);
            ci.cancel();
            break;
         case 4:
            this.attemptCommandSubordinateSkill(instance, entity, keyNumber, true);
            ci.cancel();
      }
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      if (mode == 4) {
         this.attemptCommandSubordinateSkill(instance, entity, keyNumber, false);
      } else {
         super.onRelease(instance, entity, heldTicks, keyNumber, mode);
      }
   }

   private void attemptOpenSubordinateStatusMenu(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getOrCreateTag();
      LivingEntity target = ObjectSelectionHelper.getTargetingEntity(entity, 8.0, false, false);
      if (target == null) {
         if (entity instanceof Player player) {
            player.displayClientMessage(Component.translatable("tensura.targeting.not_targeted").setStyle(Style.EMPTY.withColor(ChatFormatting.AQUA)), true);
         }
      } else if (entity instanceof Player player) {
         if (SubordinateHelper.isSubordinate(entity, target) && (ExpandedSkillStorage.config.subordinatePlayerSkillMenu || !(target instanceof Player))) {
            NetworkManager.sendToPlayer((ServerPlayer)player, new OpenHumanoidSkillMenuPayload(target.getId()));
         } else {
            player.displayClientMessage(Component.translatable("tensura.targeting.not_allowed"), true);
         }
      }
   }

   private void attemptSelectSubordinate(ManasSkillInstance instance, LivingEntity entity, int keyNumber) {
      CompoundTag tag = instance.getOrCreateTag();
      int activePreset = TensuraStorages.getAbilityFrom(entity).getActivePreset();
      LivingEntity target = ObjectSelectionHelper.getTargetingEntity(entity, 8.0, false, false);
      if (target == null) {
         if (entity instanceof Player player) {
            player.displayClientMessage(Component.translatable("tensura.targeting.not_targeted").setStyle(Style.EMPTY.withColor(ChatFormatting.AQUA)), true);
         }

         tag.remove("subordinateUUID" + activePreset);
      } else if (SubordinateHelper.isSubordinate(entity, target) && !(target instanceof Player)) {
         target.level().playSound(null, target.getX(), target.getY(), target.getZ(), TensuraSoundEvents.GENERIC_CAST, SoundSource.PLAYERS, 0.5F, 1.0F);
         if (entity instanceof Player player) {
            player.displayClientMessage(
               Component.translatable("tensura_better_subs.targeting.sub_selected", new Object[]{target.getName()})
                  .setStyle(Style.EMPTY.withColor(ChatFormatting.AQUA)),
               true
            );
         }

         tag.putUUID("subordinateUUID" + activePreset, target.getUUID());
      } else if (entity instanceof Player player) {
         player.displayClientMessage(Component.translatable("tensura.targeting.not_allowed").setStyle(Style.EMPTY.withColor(ChatFormatting.AQUA)), true);
      }
   }

   private void attemptCommandSubordinateSkill(ManasSkillInstance instance, LivingEntity entity, int keyNumber, boolean press) {
      if (!entity.level().isClientSide()) {
         CompoundTag tag = instance.getOrCreateTag();
         int activePreset = TensuraStorages.getAbilityFrom(entity).getActivePreset();
         UUID subordinateUUID = null;
         if (tag.contains("subordinateUUID" + activePreset)) {
            try {
               subordinateUUID = tag.getUUID("subordinateUUID" + activePreset);
            } catch (Exception var16) {
            }
         }

         if (subordinateUUID != null) {
            ServerLevel level = (ServerLevel)entity.level();
            Entity selectedEntity = level.getEntity(subordinateUUID);
            if (!(
               selectedEntity instanceof LivingEntity subordinate
                  && !(selectedEntity.distanceToSqr(entity) > 1024.0)
                  && !(selectedEntity instanceof Player)
                  && SubordinateHelper.isSubordinate(entity, subordinate)
            )) {
               if (entity instanceof Player player) {
                  player.displayClientMessage(
                     Component.translatable("tensura.targeting.not_targeted").setStyle(Style.EMPTY.withColor(ChatFormatting.AQUA)), true
                  );
               }

               return;
            }

            AbilitySlot slot = TensuraStorages.getAbilityFrom(subordinate).getAbilitySlot(keyNumber);
            ManasSkill skill = slot.getSkill();
            if (skill == null) {
               if (press && entity instanceof Player player) {
                  player.displayClientMessage(
                     Component.translatable("tensura_better_subs.skill.request_skill_use_failure", new Object[]{subordinate.getName(), "None"})
                        .setStyle(Style.EMPTY.withColor(ChatFormatting.AQUA)),
                     true
                  );
               }

               return;
            }

            if (skill == UniqueSkills.THROWER.get() && selectedEntity.getType().is(TensuraEntityTags.CLONES)) {
               if (press && entity instanceof Player player) {
                  player.displayClientMessage(
                     Component.translatable("tensura_better_subs.skill.request_skill_use_failure", new Object[]{subordinate.getName(), skill.getName()})
                        .setStyle(Style.EMPTY.withColor(ChatFormatting.AQUA)),
                     true
                  );
               }

               return;
            }

            if (press) {
               Skills storage = SkillAPI.getSkillsFrom(subordinate);
               ManasSkillInstance skillInstance = SkillUtils.getSkillOrNull(subordinate, skill);
               if (storage.startHoldSkill(skillInstance, keyNumber, slot.getMode())) {
                  if (entity instanceof Player player) {
                     player.displayClientMessage(
                        Component.translatable("tensura_better_subs.skill.request_skill_use_successful", new Object[]{subordinate.getName(), skill.getName()})
                           .setStyle(Style.EMPTY.withColor(ChatFormatting.AQUA)),
                        true
                     );
                  }
               } else if (entity instanceof Player player) {
                  player.displayClientMessage(
                     Component.translatable("tensura_better_subs.skill.request_skill_use_failure", new Object[]{subordinate.getName(), skill.getName()})
                        .setStyle(Style.EMPTY.withColor(ChatFormatting.AQUA)),
                     true
                  );
               }
            } else {
               SkillStorage storage = (SkillStorage)StorageManager.getStorage(subordinate, SkillStorage.getKey());
               if (storage == null) {
                  return;
               }

               storage.getSkill(skill).ifPresent(skillInstancex -> storage.handleSkillRelease(skillInstancex, keyNumber, slot.getMode(), false));
            }
         }
      }
   }
}
