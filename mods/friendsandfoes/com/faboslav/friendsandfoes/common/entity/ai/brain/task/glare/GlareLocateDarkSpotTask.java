package com.faboslav.friendsandfoes.common.entity.ai.brain.task.glare;

import com.faboslav.friendsandfoes.common.entity.GlareEntity;
import com.faboslav.friendsandfoes.common.entity.ai.brain.GlareBrain;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import java.util.ArrayList;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.TimeUtil;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import org.jetbrains.annotations.Nullable;

public final class GlareLocateDarkSpotTask extends Behavior<GlareEntity> {
   public GlareLocateDarkSpotTask() {
      super(
         Map.of(
            (MemoryModuleType)FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_LOCATING_COOLDOWN.get(),
            MemoryStatus.VALUE_ABSENT,
            (MemoryModuleType)FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_POS.get(),
            MemoryStatus.VALUE_ABSENT
         ),
         1
      );
   }

   protected boolean checkExtraStartConditions(ServerLevel world, GlareEntity glare) {
      return canLocateDarkSpot(glare);
   }

   protected void start(ServerLevel world, GlareEntity glare, long time) {
      BlockPos darkSpotPos = this.findRandomDarkSpot(glare);
      if (darkSpotPos == null) {
         GlareBrain.setDarkSpotLocatingCooldown(glare, TimeUtil.rangeOfSeconds(10, 10));
      } else {
         ResourceKey<Level> registryKey = glare.level().dimension();
         glare.getBrain().setMemory((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.GLARE_DARK_SPOT_POS.get(), GlobalPos.of(registryKey, darkSpotPos));
      }
   }

   private ArrayList<BlockPos> findDarkSpots(GlareEntity glare) {
      ServerLevel serverWorld = (ServerLevel)glare.level();
      BlockPos blockPos = glare.blockPosition();
      ArrayList<BlockPos> darkSpots = new ArrayList<>();
      int searchDistance = 16;

      for (int i = 0; i <= searchDistance; i = i > 0 ? -i : 1 - i) {
         for (int j = 0; j < searchDistance; j++) {
            for (int k = 0; k <= j; k = k > 0 ? -k : 1 - k) {
               for (int l = k < j && k > -j ? j : 0; l <= j; l = l > 0 ? -l : 1 - l) {
                  MutableBlockPos possibleDarkSpotBlockPos = new MutableBlockPos();
                  possibleDarkSpotBlockPos.setWithOffset(blockPos, k, i - 1, l);
                  boolean isBlockWithinDistance = blockPos.closerThan(possibleDarkSpotBlockPos, searchDistance);
                  boolean isSpotDarkEnough = glare.level().getBrightness(LightLayer.BLOCK, possibleDarkSpotBlockPos) == 0;
                  boolean isBlockSolidSurface = serverWorld.getBlockState(possibleDarkSpotBlockPos.below())
                     .entityCanStandOn(serverWorld, possibleDarkSpotBlockPos, glare);
                  boolean isBlockAccessible = serverWorld.isEmptyBlock(possibleDarkSpotBlockPos) && serverWorld.isEmptyBlock(possibleDarkSpotBlockPos.above());
                  if (isBlockWithinDistance && isBlockSolidSurface && isBlockAccessible && isSpotDarkEnough) {
                     darkSpots.add(possibleDarkSpotBlockPos);
                  }
               }
            }
         }
      }

      return darkSpots;
   }

   @Nullable
   private BlockPos findRandomDarkSpot(GlareEntity glare) {
      ArrayList<BlockPos> darkSpots = this.findDarkSpots(glare);
      return darkSpots.isEmpty() ? null : darkSpots.get(glare.getRandom().nextInt(darkSpots.size()));
   }

   public static boolean canLocateDarkSpot(GlareEntity glare) {
      Level level = glare.level();
      boolean isDay = level.isDay();
      boolean canSeeSky = level.canSeeSky(glare.blockPosition());
      return !glare.isLeashed() && !glare.isOrderedToSit() && !glare.isPassenger() && !glare.isBaby() && glare.isTame() && (!isDay || !canSeeSky);
   }
}
