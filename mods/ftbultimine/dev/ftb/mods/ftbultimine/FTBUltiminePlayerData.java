package dev.ftb.mods.ftbultimine;

import dev.architectury.networking.NetworkManager;
import dev.ftb.mods.ftbultimine.api.shape.Shape;
import dev.ftb.mods.ftbultimine.api.shape.ShapeContext;
import dev.ftb.mods.ftbultimine.config.FTBUltimineServerConfig;
import dev.ftb.mods.ftbultimine.net.SendShapePacket;
import dev.ftb.mods.ftbultimine.shape.BlockMatchers;
import dev.ftb.mods.ftbultimine.shape.ShapeRegistry;
import dev.ftb.mods.ftbultimine.utils.XPUtils;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.IntSupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import org.jetbrains.annotations.Nullable;

public class FTBUltiminePlayerData {
   private final UUID playerId;
   private boolean pressed = false;
   private int shapeIndex = 0;
   private double pendingXPCost;
   private BlockPos cachedPos;
   private Direction cachedDirection;
   private List<BlockPos> cachedBlocks;

   public FTBUltiminePlayerData(UUID playerId) {
      this.playerId = playerId;
   }

   public void clearCache() {
      this.cachedPos = null;
      this.cachedDirection = null;
      this.cachedBlocks = null;
   }

   public UUID getPlayerId() {
      return this.playerId;
   }

   public boolean isPressed() {
      return this.pressed;
   }

   public void setPressed(boolean pressed) {
      this.pressed = pressed;
   }

   public boolean hasCachedPositions() {
      return this.cachedBlocks != null && !this.cachedBlocks.isEmpty();
   }

   public Collection<BlockPos> cachedPositions() {
      return this.cachedBlocks;
   }

   public static HitResult rayTrace(ServerPlayer player) {
      double distance = player.getAttributeValue(Attributes.BLOCK_INTERACTION_RANGE);
      return player.pick(player.isCreative() ? distance + 0.5 : distance, 1.0F, false);
   }

   public Shape getCurrentShape() {
      return ShapeRegistry.INSTANCE.getShape(this.shapeIndex);
   }

   public int getCurrentShapeIndex() {
      return this.shapeIndex;
   }

   public void cycleShape(boolean next) {
      int nShapes = ShapeRegistry.INSTANCE.shapeCount();
      if (next) {
         if (++this.shapeIndex >= nShapes) {
            this.shapeIndex = 0;
         }
      } else if (--this.shapeIndex < 0) {
         this.shapeIndex = nShapes - 1;
      }
   }

   public void addPendingXPCost(ServerPlayer player, int blockCount) {
      this.pendingXPCost = this.pendingXPCost + blockCount * FTBUltimineServerConfig.getExperiencePerBlock(player);
   }

   public void takePendingXP(ServerPlayer player) {
      if (this.pendingXPCost > 1.0) {
         int toTake = (int)this.pendingXPCost;
         XPUtils.addPlayerXP(player, -toTake);
         this.pendingXPCost -= toTake;
      }
   }

   public void checkBlocks(ServerPlayer player, boolean sendUpdate, IntSupplier maxBlocks) {
      if (this.pressed) {
         HitResult result = rayTrace(player);
         if (result instanceof BlockHitResult hitResult && result.getType() == Type.BLOCK) {
            if (this.cachedDirection != hitResult.getDirection() || this.cachedPos == null || !this.cachedPos.equals(hitResult.getBlockPos())) {
               this.updateBlocks(player, hitResult.getBlockPos(), hitResult.getDirection(), sendUpdate, maxBlocks.getAsInt());
            }
         } else {
            if (this.cachedBlocks != null && !this.cachedBlocks.isEmpty()) {
               this.clearCache();
               if (sendUpdate) {
                  NetworkManager.sendToPlayer(player, SendShapePacket.adjustShapeAndBlockPos(this.getCurrentShapeIndex(), Collections.emptyList()));
               }
            }
         }
      }
   }

   @Nullable
   public ShapeContext updateBlocks(ServerPlayer player, BlockPos pos, Direction dir, boolean sendUpdate, int maxBlocks) {
      ShapeContext context = null;
      this.cachedPos = pos.immutable();
      this.cachedDirection = dir;
      Shape shape = this.getCurrentShape();
      if (maxBlocks <= 0) {
         this.cachedBlocks = Collections.emptyList();
      } else {
         BlockState origState = player.level().getBlockState(this.cachedPos);
         ShapeContext.Matcher matcher = BlockMatchers.determineBestMatcher(player.level(), this.cachedPos, origState, shape);
         context = new ShapeContext(player, this.cachedPos, this.cachedDirection, origState, matcher, maxBlocks);
         this.cachedBlocks = shape.getBlocks(context);
         if (FTBUltimineServerConfig.getExperiencePerBlock(player) > 0.0) {
            int max = (int)(XPUtils.getPlayerXP(player) / FTBUltimineServerConfig.getExperiencePerBlock(player));
            if (max < this.cachedBlocks.size()) {
               this.cachedBlocks = this.cachedBlocks.subList(0, max);
            }
         }
      }

      if (sendUpdate) {
         NetworkManager.sendToPlayer(player, SendShapePacket.adjustShapeAndBlockPos(this.getCurrentShapeIndex(), this.cachedBlocks));
      }

      return context;
   }
}
