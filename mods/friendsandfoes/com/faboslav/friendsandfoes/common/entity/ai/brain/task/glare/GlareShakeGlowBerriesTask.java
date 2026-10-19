package com.faboslav.friendsandfoes.common.entity.ai.brain.task.glare;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.GlareEntity;
import com.faboslav.friendsandfoes.common.entity.ai.brain.GlareBrain;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import com.faboslav.friendsandfoes.common.versions.VersionedGameRulesProvider;
import java.util.Map;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.block.CaveVines;
import net.minecraft.world.level.block.state.BlockState;

public final class GlareShakeGlowBerriesTask extends Behavior<GlareEntity> {
   private static final int MAX_SHAKING_TICKS = 200;
   private static final float WITHING_DISTANCE = 2.0F;
   private int shakingTicks;

   public GlareShakeGlowBerriesTask() {
      super(Map.of((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.GLARE_GLOW_BERRIES_POS.get(), MemoryStatus.VALUE_PRESENT), 200);
   }

   protected boolean checkExtraStartConditions(ServerLevel world, GlareEntity glare) {
      GlobalPos glowBerriesPos = glare.getGlowBerriesPos();
      return VersionedGameRulesProvider.getBoolean(glare, VersionedGameRulesProvider.MOB_GRIEFING)
         && FriendsAndFoes.getConfig().enableGlareGriefing
         && !glare.isLeashed()
         && !glare.isOrderedToSit()
         && glare.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()
         && glowBerriesPos != null
         && glare.canEatGlowBerriesAt(glowBerriesPos.pos())
         && glowBerriesPos.pos().closerToCenterThan(glare.position(), 2.0);
   }

   protected void start(ServerLevel world, GlareEntity glare, long time) {
      this.shakingTicks = 0;
   }

   protected boolean canStillUse(ServerLevel world, GlareEntity glare, long time) {
      GlobalPos glowBerriesPos = glare.getGlowBerriesPos();
      return VersionedGameRulesProvider.getBoolean(glare, VersionedGameRulesProvider.MOB_GRIEFING)
         && FriendsAndFoes.getConfig().enableGlareGriefing
         && !glare.isLeashed()
         && !glare.isOrderedToSit()
         && glare.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()
         && glowBerriesPos != null
         && glare.canEatGlowBerriesAt(glowBerriesPos.pos())
         && glowBerriesPos.pos().closerToCenterThan(glare.position(), 2.0);
   }

   protected void tick(ServerLevel world, GlareEntity glare, long time) {
      this.shakingTicks++;
      if (this.shakingTicks % 5 == 0 && glare.getRandom().nextFloat() > 0.85) {
         glare.playRustleSound();
      }

      if (glare.getRandom().nextFloat() < 0.05F && this.shakingTicks > 100) {
         glare.playAmbientSound();
         this.shakeOffGlowBerries(glare);
         GlareBrain.setLocatingGlowBerriesCooldown(glare);
      }
   }

   protected void stop(ServerLevel world, GlareEntity glare, long time) {
      glare.getBrain().eraseMemory((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.GLARE_GLOW_BERRIES_POS.get());
   }

   private void shakeOffGlowBerries(GlareEntity glare) {
      if (VersionedGameRulesProvider.getBoolean(glare, VersionedGameRulesProvider.MOB_GRIEFING) && FriendsAndFoes.getConfig().enableGlareGriefing) {
         GlobalPos glowBerriesPos = glare.getGlowBerriesPos();
         if (glowBerriesPos != null) {
            BlockState blockState = glare.level().getBlockState(glowBerriesPos.pos());
            if (CaveVines.hasGlowBerries(blockState)) {
               CaveVines.use(glare, blockState, glare.level(), glowBerriesPos.pos());
            }
         }
      }
   }
}
