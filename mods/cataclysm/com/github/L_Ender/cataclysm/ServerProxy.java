package com.github.L_Ender.cataclysm;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ServerProxy {
   public void init() {
   }

   public boolean isFirstPersonPlayer(Entity entity) {
      return false;
   }

   public Player getClientSidePlayer() {
      return null;
   }

   public void blockRenderingEntity(UUID id) {
   }

   public void releaseRenderingEntity(UUID id) {
   }

   public void clearSoundCacheFor(Entity entity) {
   }

   public float getPartialTicks() {
      return 1.0F;
   }

   public void clearSoundCacheFor(BlockEntity entity) {
   }

   public void playWorldSound(@Nullable Object soundEmitter, byte type) {
   }
}
