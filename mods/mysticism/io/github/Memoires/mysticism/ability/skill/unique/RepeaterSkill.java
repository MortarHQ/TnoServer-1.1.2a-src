package io.github.Memoires.mysticism.ability.skill.unique;

import io.github.Memoires.mysticism.ability.subclass.IFlarefrostStorehouse;
import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.Memoires.mysticism.damage.MysticismDamageHelper;
import io.github.Memoires.mysticism.damage.MysticismDamageTypes;
import io.github.Memoires.mysticism.registry.item.MysticismWeaponItems;
import io.github.Memoires.mysticism.util.TimerUtils;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.menu.container.SpatialStorageContainer;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RepeaterSkill extends Skill implements IFlarefrostStorehouse<RepeaterSkill> {
   protected static final UniqueMysticismSkillConfig.Repeater CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(UniqueMysticismSkillConfig.class)).Repeater;

   public RepeaterSkill() {
      super(SkillType.UNIQUE);
   }

   public int getModes(ManasSkillInstance instance) {
      return 2;
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/unique/repeater.png");
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      if (instance.getOrCreateTag().getBoolean("DefeatedMemoires")) {
         return mode == 0 ? 1 : 0;
      } else {
         return -1;
      }
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return instance.getMastery() >= 0.0;
   }

   public double getAcquiringMagiculeCost(ManasSkillInstance instance) {
      return CONFIG.mpAcquirement;
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "repeater.recursion_depth";
         case 1 -> "repeater.flarefrost_storehouse";
         default -> super.getModeId(instance, mode);
      };
   }

   public boolean onDamageEntity(ManasSkillInstance instance, LivingEntity owner, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      CompoundTag tag = instance.getOrCreateTag();
      boolean isRepeating = instance.getOrCreateTag().getBoolean("IsRepeating");
      if (!TensuraDamageHelper.isPhysicalAttack(source)) {
         return true;
      }

      if (!target.isDeadOrDying() && target.isAlive()) {
         if (target.getHealth() <= 0.0F) {
            return true;
         }

         DamageSource repeater = MysticismDamageHelper.create(owner, MysticismDamageTypes.REPEATED);
         if (tag.getBoolean("RecursionDepthActive") && !isRepeating) {
            CompoundTag rd = tag.getCompound("RecursionDepth");
            CompoundTag uuidMap = rd.getCompound("UUID");
            String uuidKey = target.getUUID().toString();
            CompoundTag entry = uuidMap.getCompound(uuidKey);
            float prev = entry.getFloat("prev");
            float applied = prev == 0.0F ? (Float)amount.get() : prev * 0.9F;
            amount.set(applied);
            float total = entry.getFloat("total") + (instance.isToggled() ? applied * 2.0F : applied);
            entry.putFloat("prev", applied);
            entry.putFloat("total", total);
            uuidMap.put(uuidKey, entry);
            rd.put("UUID", uuidMap);
            tag.put("RecursionDepth", rd);
            instance.markDirty();
         }

         if (instance.isToggled() && !isRepeating) {
            tag.putBoolean("IsRepeating", true);

            try {
               target.invulnerableTime = 0;
               target.hurt(repeater, (Float)amount.get());
            } finally {
               tag.putBoolean("IsRepeating", false);
            }
         }

         return true;
      } else {
         return true;
      }
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      switch (mode) {
         case 0:
            this.recursionDepth(entity, instance);
            break;
         case 1:
            this.handleStorehouse(entity, instance);
      }
   }

   @NotNull
   public SpatialStorageContainer getSpatialStorage(ManasSkillInstance instance, Provider provide) {
      SpatialStorageContainer container = new SpatialStorageContainer(2, 1);
      container.fromTag(instance.getOrCreateTag().getList("Storehouse", 10), provide);
      return container;
   }

   private void recursionDepth(LivingEntity entity, ManasSkillInstance instance) {
      instance.addMasteryPoint(entity);
      if (entity instanceof Player player) {
         player.displayClientMessage(
            Component.translatable("mysticism.skill.mode.repeater.recursion_depth.activated").setStyle(Style.EMPTY.withColor(ChatFormatting.AQUA)), true
         );
         player.playNotifySound((SoundEvent)TensuraSoundEvents.BUFF_ACTIVATE.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
      }

      instance.getOrCreateTag().putBoolean("RecursionDepthActive", true);
      TimerUtils.Timer timer = TimerUtils.getOrCreateTimer("Repeater");
      timer.start(
         CONFIG.recursionDepthTimer,
         () -> {
            CompoundTag tag = instance.getOrCreateTag();
            tag.putBoolean("RecursionDepthActive", false);
            if (entity instanceof Player player) {
               player.displayClientMessage(
                  Component.translatable("mysticism.skill.mode.repeater.recursion_depth.deactivated").setStyle(Style.EMPTY.withColor(ChatFormatting.AQUA)),
                  true
               );
               player.playNotifySound((SoundEvent)TensuraSoundEvents.BUFF_DEACTIVATE.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
            }

            if (entity.level() instanceof ServerLevel server) {
               CompoundTag rd = tag.getCompound("RecursionDepth");
               CompoundTag uuidMap = rd.getCompound("UUID");
               DamageSource repeaterSrc = MysticismDamageHelper.create(entity, MysticismDamageTypes.REPEATED);

               for (String uuidStr : uuidMap.getAllKeys()) {
                  CompoundTag entry = uuidMap.getCompound(uuidStr);
                  float total = entry.getFloat("total");
                  if (!(total <= 0.0F)) {
                     float burst = total * 1.5F;

                     try {
                        UUID id = UUID.fromString(uuidStr);
                        if (server.getEntity(id) instanceof LivingEntity target && target.isAlive()) {
                           target.invulnerableTime = 0;
                           target.hurt(repeaterSrc, burst);
                        }
                     } catch (IllegalArgumentException var16) {
                     }
                  }
               }
            }

            tag.remove("RecursionDepth");
            timer.stop();
            timer.reset();
         }
      );
      instance.setCoolDown(instance.isMastered(entity) ? CONFIG.recursionDepthCooldownMastered : CONFIG.recursionDepthCooldown, 0);
   }

   private void handleStorehouse(LivingEntity entity, ManasSkillInstance instance) {
      if (entity.isCrouching()) {
         this.openStorehouseMenu(entity, instance);
      } else if (entity instanceof ServerPlayer player) {
         SpatialStorageContainer storage = this.getSpatialStorage(instance, player.registryAccess());
         ItemStack storedAxiom = storage.getItem(0);
         ItemStack storedWaltz = storage.getItem(1);
         ItemStack main = player.getMainHandItem();
         ItemStack off = player.getOffhandItem();
         boolean mainIsAxiom = !main.isEmpty() && main.is((Item)MysticismWeaponItems.AXIOM.get());
         boolean offIsWaltz = !off.isEmpty() && off.is((Item)MysticismWeaponItems.WALTZ.get());
         boolean storageEmpty = storedAxiom.isEmpty() && storedWaltz.isEmpty();
         if (storageEmpty) {
            if (mainIsAxiom && offIsWaltz) {
               storage.setItem(0, main.copy());
               storage.setItem(1, off.copy());
               player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
               player.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
               this.saveContainer(instance, player, storage);
               player.playNotifySound((SoundEvent)TensuraSoundEvents.SPATIAL_STORAGE.get(), TensuraSkill.ABILITY_SOUND, 0.6F, 0.9F);
               player.inventoryMenu.broadcastChanges();
            } else {
               player.playNotifySound((SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(), TensuraSkill.ABILITY_SOUND, 0.5F, 1.0F);
               player.sendSystemMessage(
                  Component.translatable("mysticism.skill.mode.repeater.flarefrost_storehouse.storehouse_empty").withStyle(ChatFormatting.RED)
               );
            }
         } else {
            ItemStack axiom = !storedAxiom.isEmpty() && storedAxiom.is((Item)MysticismWeaponItems.AXIOM.get()) ? storedAxiom.copy() : ItemStack.EMPTY;
            ItemStack waltz = !storedWaltz.isEmpty() && storedWaltz.is((Item)MysticismWeaponItems.WALTZ.get()) ? storedWaltz.copy() : ItemStack.EMPTY;
            if (axiom.isEmpty() && waltz.isEmpty()) {
               player.playNotifySound((SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(), TensuraSkill.ABILITY_SOUND, 0.5F, 1.0F);
               player.sendSystemMessage(Component.literal("Storehouse contains invalid items.").withStyle(ChatFormatting.RED));
            } else {
               ItemStack oldMain = main.copy();
               ItemStack oldOff = off.copy();
               boolean replaceMain = !axiom.isEmpty();
               boolean replaceOff = !waltz.isEmpty();
               if (replaceMain) {
                  player.setItemInHand(InteractionHand.MAIN_HAND, axiom);
               }

               if (replaceOff) {
                  player.setItemInHand(InteractionHand.OFF_HAND, waltz);
               }

               if (replaceMain && !oldMain.isEmpty()) {
                  player.getInventory().placeItemBackInInventory(oldMain);
               }

               if (replaceOff && !oldOff.isEmpty()) {
                  player.getInventory().placeItemBackInInventory(oldOff);
               }

               if (replaceMain) {
                  storage.setItem(0, ItemStack.EMPTY);
               }

               if (replaceOff) {
                  storage.setItem(1, ItemStack.EMPTY);
               }

               this.saveContainer(instance, player, storage);
               player.playNotifySound((SoundEvent)TensuraSoundEvents.SPATIAL_STORAGE.get(), TensuraSkill.ABILITY_SOUND, 0.6F, 1.2F);
               player.inventoryMenu.broadcastChanges();
            }
         }
      }
   }
}
