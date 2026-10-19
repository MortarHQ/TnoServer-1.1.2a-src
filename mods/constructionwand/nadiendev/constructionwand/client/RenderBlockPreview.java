package nadiendev.constructionwand.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Set;
import nadiendev.constructionwand.basics.WandUtil;
import nadiendev.constructionwand.items.wand.ItemWand;
import nadiendev.constructionwand.wand.WandJob;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderHighlightEvent.Block;

public class RenderBlockPreview {
   private WandJob wandJob;
   public Set<BlockPos> undoBlocks;

   @SubscribeEvent
   public void renderBlockHighlight(Block event) {
      if (event.getTarget().getType() == Type.BLOCK) {
         BlockHitResult rtr = event.getTarget();
         if (event.getCamera().getEntity() instanceof Player player) {
            float colorR = 0.0F;
            float colorG = 0.0F;
            float colorB = 0.0F;
            ItemStack wand = WandUtil.holdingWand(player);
            if (wand != null) {
               Set<BlockPos> blocks;
               if (player.isCrouching() && ClientEvents.isOptKeyDown()) {
                  blocks = this.undoBlocks;
                  colorG = 1.0F;
               } else {
                  if (this.wandJob == null || !compareRTR(this.wandJob.rayTraceResult, rtr) || !this.wandJob.wand.equals(wand) || this.wandJob.blockCount() < 2
                     )
                   {
                     this.wandJob = ItemWand.getWandJob(player, player.level(), rtr, wand);
                  }

                  blocks = this.wandJob.getBlockPositions();
               }

               if (blocks != null && !blocks.isEmpty()) {
                  PoseStack ms = event.getPoseStack();
                  MultiBufferSource buffer = event.getMultiBufferSource();
                  VertexConsumer lineBuilder = buffer.getBuffer(RenderType.LINES);
                  double partialTicks = event.getDeltaTracker().getGameTimeDeltaPartialTick(false);
                  double d0 = player.xOld + (player.getX() - player.xOld) * partialTicks;
                  double d1 = player.yOld + player.getEyeHeight() + (player.getY() - player.yOld) * partialTicks;
                  double d2 = player.zOld + (player.getZ() - player.zOld) * partialTicks;

                  for (BlockPos block : blocks) {
                     AABB aabb = new AABB(block).move(-d0, -d1, -d2);
                     LevelRenderer.renderLineBox(ms, lineBuilder, aabb, colorR, colorG, colorB, 0.4F);
                  }

                  event.setCanceled(true);
               }
            }
         }
      }
   }

   public void reset() {
      this.wandJob = null;
   }

   private static boolean compareRTR(BlockHitResult rtr1, BlockHitResult rtr2) {
      return rtr1.getBlockPos().equals(rtr2.getBlockPos()) && rtr1.getDirection().equals(rtr2.getDirection());
   }
}
