package com.github.wal_bos.moreskills.menu;

import com.github.wal_bos.moreskills.ability.skill.unique.VoidArchiveSkill;
import com.github.wal_bos.moreskills.registry.menu.ModMenus;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.subclass.ISubAbilityModeHolder;
import io.github.manasmods.tensura.storage.ability.AbilitySlot;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

public class VoidArchiveLibraryMenu extends AbstractContainerMenu {
   public static final int BUTTON_SELECT_DAMAGE_BASE = 1000;
   public static final int BUTTON_REMOVE_DAMAGE_BASE = 2000;
   public static final int BUTTON_SELECT_EFFECT_BASE = 3000;
   public static final int BUTTON_REMOVE_EFFECT_BASE = 4000;
   public static final int BUTTON_REMOVE_ECHO_BASE = 5000;
   public static final int BUTTON_CLEAR_DAMAGE = 9000;
   public static final int BUTTON_CLEAR_EFFECT = 9001;
   public static final int BUTTON_CLEAR_ECHO = 9002;
   private final Player player;
   private final boolean isServer;
   private final List<String> damageIds = new ArrayList<>();
   private final List<Integer> damageCounts = new ArrayList<>();
   private final List<String> effectIds = new ArrayList<>();
   private final List<Integer> effectAmplifiers = new ArrayList<>();
   private final List<Integer> effectDurations = new ArrayList<>();
   private final List<Integer> effectStacks = new ArrayList<>();
   private final List<String> echoNames = new ArrayList<>();
   private final List<String> echoSubs = new ArrayList<>();
   private final List<String> echoSkillIds = new ArrayList<>();
   private final List<Integer> echoModes = new ArrayList<>();
   private final DataSlot activeDamage = DataSlot.standalone();
   private final DataSlot activeEffect = DataSlot.standalone();
   private final DataSlot damageSize = DataSlot.standalone();
   private final DataSlot effectSize = DataSlot.standalone();
   private final DataSlot echoSize = DataSlot.standalone();

   private VoidArchiveLibraryMenu(int containerId, Inventory inventory, Player player) {
      super((MenuType)ModMenus.VOID_ARCHIVE_LIBRARY_MENU.get(), containerId);
      this.player = player;
      this.isServer = true;
      this.addDataSlot(this.activeDamage);
      this.addDataSlot(this.activeEffect);
      this.addDataSlot(this.damageSize);
      this.addDataSlot(this.effectSize);
      this.addDataSlot(this.echoSize);
   }

   public static VoidArchiveLibraryMenu server(int containerId, Inventory inventory, ServerPlayer player) {
      VoidArchiveLibraryMenu menu = new VoidArchiveLibraryMenu(containerId, inventory, player);
      menu.refreshFromServer();
      return menu;
   }

   public VoidArchiveLibraryMenu(int containerId, Inventory inventory, RegistryFriendlyByteBuf buf) {
      super((MenuType)ModMenus.VOID_ARCHIVE_LIBRARY_MENU.get(), containerId);
      this.player = inventory.player;
      this.isServer = false;
      this.addDataSlot(this.activeDamage);
      this.addDataSlot(this.activeEffect);
      this.addDataSlot(this.damageSize);
      this.addDataSlot(this.effectSize);
      this.addDataSlot(this.echoSize);
      int serverActiveDamage = buf.readVarInt();
      int serverActiveEffect = buf.readVarInt();
      int dSize = buf.readVarInt();

      for (int i = 0; i < dSize; i++) {
         this.damageIds.add(buf.readUtf(32767));
         this.damageCounts.add(buf.readVarInt());
      }

      int eSize = buf.readVarInt();

      for (int i = 0; i < eSize; i++) {
         this.effectIds.add(buf.readUtf(32767));
         this.effectAmplifiers.add(buf.readVarInt());
         this.effectDurations.add(buf.readVarInt());
         this.effectStacks.add(buf.readVarInt());
      }

      int echoCount = buf.readVarInt();

      for (int i = 0; i < echoCount; i++) {
         this.echoNames.add(buf.readUtf(32767));
         this.echoSubs.add(buf.readUtf(32767));
         this.echoSkillIds.add(buf.readUtf(32767));
         this.echoModes.add(buf.readVarInt());
      }

      this.activeDamage.set(serverActiveDamage);
      this.activeEffect.set(serverActiveEffect);
      this.damageSize.set(this.damageIds.size());
      this.effectSize.set(this.effectIds.size());
      this.echoSize.set(this.echoNames.size());
   }

   public static void writeExtraData(ServerPlayer player, RegistryFriendlyByteBuf buf) {
      Optional<ManasSkillInstance> optional = getArchiveInstance(player);
      if (!optional.isEmpty() && optional.get().getSkill() instanceof VoidArchiveSkill holder) {
         ManasSkillInstance var17 = optional.get();
         buf.writeVarInt(var17.getOrCreateTag().getInt("ActiveDamageIndex"));
         buf.writeVarInt(var17.getOrCreateTag().getInt("ActiveEffectIndex"));
         List damages = holder.getDamageRecords(var17);
         buf.writeVarInt(damages.size());

         for (VoidArchiveSkill.DamageRecord record : damages) {
            buf.writeUtf(record.id());
            buf.writeVarInt(record.count());
         }

         List<VoidArchiveSkill.EffectRecord> effects = holder.getEffectRecords(var17);
         buf.writeVarInt(effects.size());

         for (VoidArchiveSkill.EffectRecord record : effects) {
            buf.writeUtf(record.effectId().toString());
            buf.writeVarInt(record.amplifier());
            buf.writeVarInt(record.duration());
            buf.writeVarInt(record.stacks());
         }

         List<AbilitySlot> slots = new ArrayList<>(ISubAbilityModeHolder.getSubSlots(var17));
         buf.writeVarInt(slots.size());

         for (AbilitySlot slot : slots) {
            ManasSkill skill = slot.getSkill();
            String name = "Empty";
            String sub = "";
            String skillId = "";
            int mode = slot.getMode();
            if (skill != null) {
               name = skill.getName().getString();
               skillId = skill.getRegistryName() == null ? "" : skill.getRegistryName().toString();
               ManasSkillInstance subInstance = (ManasSkillInstance)var17.getSubInstances().get(skill);
               if (skill instanceof TensuraSkill tensuraSkill && subInstance != null) {
                  name = Objects.equals(tensuraSkill.getModeId(subInstance, slot.getMode()), "default")
                     ? skill.getName().getString()
                     : tensuraSkill.getModeName(subInstance, slot.getMode()).getString();
               }

               sub = skillId;
            }

            buf.writeUtf(name);
            buf.writeUtf(sub);
            buf.writeUtf(skillId);
            buf.writeVarInt(mode);
         }
      } else {
         buf.writeVarInt(0);
         buf.writeVarInt(0);
         buf.writeVarInt(0);
         buf.writeVarInt(0);
         buf.writeVarInt(0);
      }
   }

   public boolean clickMenuButton(Player player, int buttonId) {
      Optional<ManasSkillInstance> optional = getArchiveInstance(player);
      if (optional.isEmpty()) {
         return false;
      } else if (!(optional.get().getSkill() instanceof VoidArchiveSkill holder)) {
         return false;
      } else if (!(player instanceof ServerPlayer sp)) {
         return false;
      } else {
         ManasSkillInstance instance = optional.get();
         if (buttonId == 9000) {
            while (!holder.getDamageRecords(instance).isEmpty()) {
               holder.removeDamageRecord(instance, holder.getDamageRecords(instance).size() - 1);
            }

            syncArchive(sp, instance);
            return true;
         } else if (buttonId != 9001) {
            if (buttonId == 9002) {
               List<AbilitySlot> slots = new ArrayList<>(ISubAbilityModeHolder.getSubSlots(instance));

               for (int i = slots.size() - 1; i >= 0; i--) {
                  AbilitySlot slot = slots.get(i);
                  if (slot.getSkill() != null) {
                     holder.removeSubSkill(instance, sp, slot.getSkill(), slot.getMode());
                  }
               }

               syncArchive(sp, instance);
               return true;
            } else {
               if (buttonId >= 1000 && buttonId < 2000) {
                  holder.setActiveDamage(instance, buttonId - 1000);
                  syncArchive(sp, instance);
                  return true;
               }

               if (buttonId >= 2000 && buttonId < 3000) {
                  holder.removeDamageRecord(instance, buttonId - 2000);
                  syncArchive(sp, instance);
                  return true;
               }

               if (buttonId >= 3000 && buttonId < 4000) {
                  holder.setActiveEffect(instance, buttonId - 3000);
                  syncArchive(sp, instance);
                  return true;
               }

               if (buttonId >= 4000 && buttonId < 5000) {
                  holder.removeEffectRecord(instance, buttonId - 4000);
                  syncArchive(sp, instance);
                  return true;
               }

               if (buttonId >= 5000) {
                  int index = buttonId - 5000;
                  List<AbilitySlot> slots = new ArrayList<>(ISubAbilityModeHolder.getSubSlots(instance));
                  if (index >= 0 && index < slots.size()) {
                     AbilitySlot slot = slots.get(index);
                     if (slot.getSkill() == null) {
                        return false;
                     }

                     holder.removeSubSkill(instance, sp, slot.getSkill(), slot.getMode());
                     syncArchive(sp, instance);
                     return true;
                  } else {
                     return false;
                  }
               } else {
                  return false;
               }
            }
         } else {
            while (!holder.getEffectRecords(instance).isEmpty()) {
               holder.removeEffectRecord(instance, holder.getEffectRecords(instance).size() - 1);
            }

            syncArchive(sp, instance);
            return true;
         }
      }
   }

   private void refreshFromServer() {
      Optional<ManasSkillInstance> optional = getArchiveInstance(this.player);
      if (!optional.isEmpty() && optional.get().getSkill() instanceof VoidArchiveSkill holder) {
         ManasSkillInstance var13 = optional.get();
         this.damageIds.clear();
         this.damageCounts.clear();

         for (VoidArchiveSkill.DamageRecord record : holder.getDamageRecords(var13)) {
            this.damageIds.add(record.id());
            this.damageCounts.add(record.count());
         }

         this.effectIds.clear();
         this.effectAmplifiers.clear();
         this.effectDurations.clear();
         this.effectStacks.clear();

         for (VoidArchiveSkill.EffectRecord record : holder.getEffectRecords(var13)) {
            this.effectIds.add(record.effectId().toString());
            this.effectAmplifiers.add(record.amplifier());
            this.effectDurations.add(record.duration());
            this.effectStacks.add(record.stacks());
         }

         this.echoNames.clear();
         this.echoSubs.clear();
         this.echoSkillIds.clear();
         this.echoModes.clear();

         for (AbilitySlot slot : new ArrayList(ISubAbilityModeHolder.getSubSlots(var13))) {
            ManasSkill skill = slot.getSkill();
            String name = "Empty";
            String sub = "";
            String skillId = "";
            int mode = slot.getMode();
            if (skill != null) {
               name = skill.getName().getString();
               skillId = skill.getRegistryName() == null ? "" : skill.getRegistryName().toString();
               ManasSkillInstance subInstance = (ManasSkillInstance)var13.getSubInstances().get(skill);
               if (skill instanceof TensuraSkill tensuraSkill && subInstance != null) {
                  name = Objects.equals(tensuraSkill.getModeId(subInstance, slot.getMode()), "default")
                     ? skill.getName().getString()
                     : tensuraSkill.getModeName(subInstance, slot.getMode()).getString();
               }

               sub = skillId;
            }

            this.echoNames.add(name);
            this.echoSubs.add(sub);
            this.echoSkillIds.add(skillId);
            this.echoModes.add(mode);
         }

         this.activeDamage.set(var13.getOrCreateTag().getInt("ActiveDamageIndex"));
         this.activeEffect.set(var13.getOrCreateTag().getInt("ActiveEffectIndex"));
         this.damageSize.set(this.damageIds.size());
         this.effectSize.set(this.effectIds.size());
         this.echoSize.set(this.echoNames.size());
      } else {
         this.damageIds.clear();
         this.damageCounts.clear();
         this.effectIds.clear();
         this.effectAmplifiers.clear();
         this.effectDurations.clear();
         this.effectStacks.clear();
         this.echoNames.clear();
         this.echoSubs.clear();
         this.echoSkillIds.clear();
         this.echoModes.clear();
         this.activeDamage.set(0);
         this.activeEffect.set(0);
         this.damageSize.set(0);
         this.effectSize.set(0);
         this.echoSize.set(0);
      }
   }

   public int getActiveDamageIndex() {
      return this.activeDamage.get();
   }

   public int getActiveEffectIndex() {
      return this.activeEffect.get();
   }

   public int getDamageCount() {
      return this.damageSize.get();
   }

   public int getEffectCount() {
      return this.effectSize.get();
   }

   public int getEchoCount() {
      return this.echoSize.get();
   }

   public Component getDamageEntryName(int index) {
      return (Component)(index >= 0 && index < this.damageIds.size() ? VoidArchiveSkill.getDamageDisplay(this.damageIds.get(index)) : Component.empty());
   }

   public Component getDamageEntrySubtitle(int index) {
      return index >= 0 && index < this.damageCounts.size() ? Component.literal("Stacks: " + this.damageCounts.get(index)) : Component.empty();
   }

   public Component getEffectEntryName(int index) {
      if (index >= 0 && index < this.effectIds.size()) {
         try {
            return VoidArchiveSkill.getEffectDisplay(
               new VoidArchiveSkill.EffectRecord(
                  ResourceLocation.parse(this.effectIds.get(index)),
                  this.effectAmplifiers.get(index),
                  this.effectDurations.get(index),
                  this.effectStacks.get(index)
               )
            );
         } catch (Exception ignored) {
            return Component.literal(this.effectIds.get(index));
         }
      } else {
         return Component.empty();
      }
   }

   public Component getEffectEntrySubtitle(int index) {
      return index >= 0 && index < this.effectIds.size()
         ? Component.literal(
            "Amp " + (this.effectAmplifiers.get(index) + 1) + " | " + this.effectDurations.get(index) / 20 + "s | Stacks " + this.effectStacks.get(index)
         )
         : Component.empty();
   }

   public Component getEchoEntryName(int index) {
      return index >= 0 && index < this.echoNames.size() ? Component.literal(this.echoNames.get(index)) : Component.empty();
   }

   public Component getEchoEntrySubtitle(int index) {
      return index >= 0 && index < this.echoSubs.size() ? Component.literal(this.echoSubs.get(index)) : Component.empty();
   }

   private static Optional<ManasSkillInstance> getArchiveInstance(Player player) {
      if (player == null) {
         return Optional.empty();
      }

      Skills skills = SkillAPI.getSkillsFrom(player);
      return skills == null ? Optional.empty() : skills.getSkill(VoidArchiveSkill.SKILL_ID);
   }

   private static void syncArchive(ServerPlayer sp, ManasSkillInstance instance) {
      instance.markDirty();
      Skills skills = SkillAPI.getSkillsFrom(sp);
      if (skills != null) {
         skills.markDirty();
      }

      sp.containerMenu.broadcastChanges();
   }

   public void broadcastChanges() {
      super.broadcastChanges();
      if (this.isServer) {
         this.refreshFromServer();
      }
   }

   public ItemStack quickMoveStack(Player player, int index) {
      return ItemStack.EMPTY;
   }

   public boolean stillValid(Player player) {
      return true;
   }
}
