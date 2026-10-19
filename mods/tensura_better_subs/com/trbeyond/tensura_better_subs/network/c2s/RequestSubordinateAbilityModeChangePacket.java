package com.trbeyond.tensura_better_subs.network.c2s;

import com.trbeyond.tensura_better_subs.entity.ExpandedSkillStorage;
import dev.architectury.networking.NetworkManager.PacketContext;
import dev.architectury.utils.Env;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.SkillEvents;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.manascore.skill.api.SkillEvents.SkillToggleEvent;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.network.c2s.RequestAbilityModeChangePacket.Action;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ability.AbilitySlot;
import io.github.manasmods.tensura.storage.ability.IAbility;
import io.github.manasmods.tensura.util.SubordinateHelper;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public record RequestSubordinateAbilityModeChangePacket(
   UUID entityUUID, String presetName, int presetId, double presetDelta, int slot, boolean alternative, ResourceLocation location, Action action
) implements CustomPacketPayload {
   public static final Type<RequestSubordinateAbilityModeChangePacket> TYPE = new Type(
      ResourceLocation.fromNamespaceAndPath("tensura_better_subs", "request_subordinate_ability_mode_change")
   );
   public static final StreamCodec<FriendlyByteBuf, RequestSubordinateAbilityModeChangePacket> STREAM_CODEC = CustomPacketPayload.codec(
      RequestSubordinateAbilityModeChangePacket::encode, RequestSubordinateAbilityModeChangePacket::new
   );

   public RequestSubordinateAbilityModeChangePacket(FriendlyByteBuf buf) {
      this(
         buf.readUUID(),
         buf.readUtf(),
         buf.readInt(),
         buf.readDouble(),
         buf.readInt(),
         buf.readBoolean(),
         buf.readResourceLocation(),
         (Action)buf.readEnum(Action.class)
      );
   }

   public static RequestSubordinateAbilityModeChangePacket changeAbilityPacket(UUID entityUUID, int slot, int preset, ResourceLocation location, int mode) {
      if (location == null) {
         location = ResourceLocation.withDefaultNamespace("empty");
      }

      return new RequestSubordinateAbilityModeChangePacket(entityUUID, "", preset, mode, slot, false, location, Action.CHANGE_ABILITY);
   }

   public static RequestSubordinateAbilityModeChangePacket changeAbilityPacket(UUID entityUUID, int slot, int preset, ResourceLocation location) {
      return changeAbilityPacket(entityUUID, slot, preset, location, 0);
   }

   public static RequestSubordinateAbilityModeChangePacket toggleAbilityPacket(UUID entityUUID, ResourceLocation location) {
      if (location == null) {
         location = ResourceLocation.withDefaultNamespace("empty");
      }

      return new RequestSubordinateAbilityModeChangePacket(entityUUID, "", -1, 0.0, -1, false, location, Action.TOGGLE_ABILITY);
   }

   public static RequestSubordinateAbilityModeChangePacket changeModePacket(UUID entityUUID, int slot, int preset, boolean reverse) {
      return new RequestSubordinateAbilityModeChangePacket(
         entityUUID, "", preset, 0.0, slot, reverse, ResourceLocation.withDefaultNamespace("empty"), Action.CHANGE_MODE
      );
   }

   public static RequestSubordinateAbilityModeChangePacket changeModePacket(UUID entityUUID, int slot, boolean reverse) {
      return changeModePacket(entityUUID, slot, -1, reverse);
   }

   public static RequestSubordinateAbilityModeChangePacket changePresetPacket(UUID entityUUID, double presetDelta, boolean scroll) {
      return new RequestSubordinateAbilityModeChangePacket(
         entityUUID, "", -1, presetDelta, -1, scroll, ResourceLocation.withDefaultNamespace("empty"), Action.CHANGE_PRESET
      );
   }

   public void encode(FriendlyByteBuf buf) {
      buf.writeUUID(this.entityUUID);
      buf.writeUtf(this.presetName);
      buf.writeInt(this.presetId);
      buf.writeDouble(this.presetDelta);
      buf.writeInt(this.slot);
      buf.writeBoolean(this.alternative);
      buf.writeResourceLocation(this.location);
      buf.writeEnum(this.action);
   }

   public void handle(PacketContext context) {
      if (context.getEnvironment() == Env.SERVER) {
         context.queue(
            () -> {
               ServerPlayer player = (ServerPlayer)context.getPlayer();
               if (player != null) {
                  Entity entity = player.serverLevel().getEntity(this.entityUUID);
                  if (!(entity instanceof LivingEntity subordinate)) {
                     return;
                  }

                  if (!SubordinateHelper.isSubordinate(player, subordinate)) {
                     return;
                  }

                  if ((!ExpandedSkillStorage.config.subordinatePlayerSkillMenu || !ExpandedSkillStorage.config.subordinatePlayerSkillManipulation)
                     && subordinate instanceof Player) {
                     context.getPlayer().sendSystemMessage(Component.translatable("tensura.targeting.not_allowed"));
                     return;
                  }

                  switch (this.action.ordinal()) {
                     case 0:
                        this.changeMode(subordinate);
                        break;
                     case 1:
                        this.changePreset(subordinate);
                     case 3:
                        this.changeAbility(subordinate);
                     case 2:
                     default:
                        break;
                     case 4:
                        this.toggleAbility(subordinate);
                  }

                  entity.manasCore$sync(player);
               }
            }
         );
      }
   }

   public void changeMode(LivingEntity subordinate) {
      IAbility ability = TensuraStorages.getAbilityFrom(subordinate);
      int preset = this.presetId == -1 ? ability.getActivePreset() : this.presetId;
      AbilitySlot abilitySlot = ability.getAbilitySlot(preset, this.slot);
      ManasSkill skill = abilitySlot.getSkill();
      if (skill != null) {
         Skills skills = SkillAPI.getSkillsFrom(subordinate);
         Optional<ManasSkillInstance> optional = skills.getSkill(skill);
         if (!optional.isEmpty()) {
            ManasSkillInstance instance = optional.get();
            if (instance.canInteractSkill(subordinate)) {
               int mode = abilitySlot.getMode();
               if (instance.getModes() > 1 && skill instanceof TensuraSkill tensuraSkill) {
                  int nextMode = tensuraSkill.nextMode(subordinate, instance, mode, this.alternative);
                  if (nextMode != -1) {
                     ability.setAbilitySlot(preset, this.slot, tensuraSkill, nextMode);
                     ability.markDirty();
                  }
               }
            }
         }
      }
   }

   public void changePreset(LivingEntity subordinate) {
      IAbility ability = TensuraStorages.getAbilityFrom(subordinate);
      if (this.alternative) {
         int active = ability.getActivePreset();
         int newPreset = (int)(active + this.presetDelta);
         if (newPreset > 8) {
            newPreset = 0;
         }

         if (newPreset < 0) {
            newPreset = 8;
         }

         if (newPreset != active) {
            ability.setActivePreset(newPreset);
            ability.markDirty();
         }
      } else if (this.presetDelta < 10.0 && this.presetDelta > 0.0) {
         int key = (int)this.presetDelta - 1;
         if (ability.getActivePreset() != key) {
            ability.setActivePreset(key);
            ability.markDirty();
         }
      }
   }

   public void changeAbility(LivingEntity subordinate) {
      IAbility ability = TensuraStorages.getAbilityFrom(subordinate);
      if (this.location.equals(ResourceLocation.withDefaultNamespace("empty"))) {
         ability.setAbilitySlot(this.presetId, this.slot, (ManasSkill)null, 0);
         ability.markDirty();
      } else {
         Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(subordinate).getSkill(this.location);
         if (!optional.isEmpty()) {
            ManasSkillInstance instance = optional.get();
            ability.setAbilitySlot(this.presetId, this.slot, instance.getSkill(), (int)this.presetDelta);
            ability.markDirty();
         }
      }
   }

   public void toggleAbility(LivingEntity subordinate) {
      Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(subordinate).getSkill(this.location);
      if (!optional.isEmpty()) {
         ManasSkillInstance instance = optional.get();
         Changeable<ManasSkillInstance> changeable = Changeable.of(instance);
         if (!instance.canBeToggled(subordinate) || ((SkillToggleEvent)SkillEvents.TOGGLE_SKILL.invoker()).toggleSkill(changeable, subordinate).isFalse()) {
            return;
         }

         instance = (ManasSkillInstance)changeable.get();
         if (instance == null || !instance.canInteractSkill(subordinate)) {
            return;
         }

         instance.setToggled(!instance.isToggled());
         if (instance.isToggled()) {
            instance.onToggleOn(subordinate);
            subordinate.level()
               .playSound(
                  null,
                  subordinate.getX(),
                  subordinate.getY(),
                  subordinate.getZ(),
                  (SoundEvent)TensuraSoundEvents.BUFF_ACTIVATE.get(),
                  SoundSource.NEUTRAL,
                  0.75F,
                  1.0F
               );
         } else {
            instance.onToggleOff(subordinate);
            subordinate.level()
               .playSound(
                  null,
                  subordinate.getX(),
                  subordinate.getY(),
                  subordinate.getZ(),
                  (SoundEvent)TensuraSoundEvents.BUFF_DEACTIVATE.get(),
                  SoundSource.NEUTRAL,
                  0.75F,
                  1.0F
               );
         }
      }
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
