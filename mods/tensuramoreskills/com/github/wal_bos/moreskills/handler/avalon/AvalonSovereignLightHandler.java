package com.github.wal_bos.moreskills.handler.avalon;

import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;

public final class AvalonSovereignLightHandler implements AvalonModeHandler {
   private static final String SELECTED = "AvalonSelectedSovereignLight";
   private static final List<AvalonSovereignModeHandler> MODES = List.of(
      new AvalonSovereignJudgementHandler(), new AvalonSovereignHeavensFallHandler(), new AvalonSovereignSanctuaryHandler()
   );

   @Override
   public String modeId() {
      return "avalon_god_of_eternal_kingdom.sovereign_light";
   }

   @Override
   public double magiculeCost(LivingEntity entity, ManasSkillInstance instance) {
      int selected = Math.floorMod(instance.getOrCreateTag().getInt("AvalonSelectedSovereignLight"), MODES.size());
      return MODES.get(selected).magiculeCost(entity, instance);
   }

   @Override
   public void activate(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      CompoundTag tag = instance.getOrCreateTag();
      int selected = Math.floorMod(tag.getInt("AvalonSelectedSovereignLight"), MODES.size());
      if (entity.isShiftKeyDown()) {
         selected = (selected + 1) % MODES.size();
         tag.putInt("AvalonSelectedSovereignLight", selected);
         instance.markDirty();
         entity.sendSystemMessage(Component.literal("Sovereign Light selected: " + MODES.get(selected).name()).withStyle(ChatFormatting.GOLD));
      } else {
         MODES.get(selected).activate(instance, entity, mode);
      }
   }

   @Override
   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      int selected = Math.floorMod(instance.getOrCreateTag().getInt("AvalonSelectedSovereignLight"), MODES.size());
      return MODES.get(selected).onHeld(instance, entity, heldTicks, mode);
   }

   @Override
   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      for (AvalonSovereignModeHandler handler : MODES) {
         handler.onRelease(instance, entity, heldTicks, keyNumber, mode);
      }
   }

   @Override
   public boolean shouldTriggerReleaseOnHeldInterrupt(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      for (AvalonSovereignModeHandler handler : MODES) {
         if (handler.shouldTriggerReleaseOnHeldInterrupt(instance, entity, keyNumber, mode)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public void tick(ManasSkillInstance instance, LivingEntity entity) {
      for (AvalonSovereignModeHandler handler : MODES) {
         handler.tick(instance, entity);
      }
   }

   @Override
   public void cleanup(LivingEntity entity) {
      for (AvalonSovereignModeHandler handler : MODES) {
         handler.cleanup(entity);
      }
   }
}
