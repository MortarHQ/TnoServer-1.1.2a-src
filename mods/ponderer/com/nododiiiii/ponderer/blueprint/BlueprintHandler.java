package com.nododiiiii.ponderer.blueprint;

import com.mojang.logging.LogUtils;
import com.nododiiiii.ponderer.ponder.SceneStore;
import com.nododiiiii.ponderer.ui.UIText;
import java.nio.file.Path;
import net.createmod.catnip.math.VecHelper;
import net.createmod.catnip.outliner.Outliner;
import net.createmod.ponder.enums.PonderSpecialTextures;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import org.slf4j.Logger;

public class BlueprintHandler {
   private static final Logger LOGGER = LogUtils.getLogger();
   public static BlueprintHandler INSTANCE;
   private final Object outlineSlot = new Object();
   public BlockPos firstPos;
   public BlockPos secondPos;
   private BlockPos selectedPos;
   private Direction selectedFace;
   private int range = 10;

   public boolean mouseScrolled(double delta) {
      if (!this.isActive()) {
         return false;
      }

      if (!hasCtrlDown()) {
         return false;
      }

      if (this.secondPos == null) {
         this.range = (int)Mth.clamp(this.range + delta, 1.0, 100.0);
      }

      if (this.selectedFace == null) {
         return true;
      }

      AABB bb = new AABB(Vec3.atLowerCornerOf(this.firstPos), Vec3.atLowerCornerOf(this.secondPos));
      Vec3i vec = this.selectedFace.getNormal();
      Vec3 projectedView = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
      if (bb.contains(projectedView)) {
         delta *= -1.0;
      }

      int intDelta = (int)(delta > 0.0 ? Math.ceil(delta) : Math.floor(delta));
      int x = vec.getX() * intDelta;
      int y = vec.getY() * intDelta;
      int z = vec.getZ() * intDelta;
      AxisDirection axisDirection = this.selectedFace.getAxisDirection();
      if (axisDirection == AxisDirection.NEGATIVE) {
         bb = bb.move(-x, -y, -z);
      }

      double maxX = Math.max(bb.maxX - x * axisDirection.getStep(), bb.minX);
      double maxY = Math.max(bb.maxY - y * axisDirection.getStep(), bb.minY);
      double maxZ = Math.max(bb.maxZ - z * axisDirection.getStep(), bb.minZ);
      bb = new AABB(bb.minX, bb.minY, bb.minZ, maxX, maxY, maxZ);
      this.firstPos = BlockPos.containing(bb.minX, bb.minY, bb.minZ);
      this.secondPos = BlockPos.containing(bb.maxX, bb.maxY, bb.maxZ);
      LocalPlayer player = Minecraft.getInstance().player;
      sendStatus(
         player,
         Component.translatable("item.ponderer.blueprint.dimensions", new Object[]{(int)bb.getXsize() + 1, (int)bb.getYsize() + 1, (int)bb.getZsize() + 1})
      );
      return true;
   }

   public boolean onMouseInput(int button, boolean pressed) {
      if (!pressed || button != 1) {
         return false;
      } else if (!this.isActive()) {
         return false;
      } else {
         LocalPlayer player = Minecraft.getInstance().player;
         if (player.isShiftKeyDown()) {
            this.discard();
            return true;
         } else if (this.secondPos != null) {
            Minecraft.getInstance().setScreen(new BlueprintPromptScreen());
            return true;
         } else if (this.selectedPos == null) {
            sendStatus(player, Component.translatable("item.ponderer.blueprint.no_target"));
            return true;
         } else if (this.firstPos != null) {
            this.secondPos = this.selectedPos;
            sendStatus(player, Component.translatable("item.ponderer.blueprint.end_set"));
            return true;
         } else {
            this.firstPos = this.selectedPos;
            sendStatus(player, Component.translatable("item.ponderer.blueprint.start_set"));
            return true;
         }
      }
   }

   public void discard() {
      LocalPlayer player = Minecraft.getInstance().player;
      this.firstPos = null;
      this.secondPos = null;
      sendStatus(player, Component.translatable("item.ponderer.blueprint.abort"));
   }

   public void tick() {
      if (this.isActive()) {
         LocalPlayer player = Minecraft.getInstance().player;
         if (hasCtrlDown()) {
            float pt = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(false);
            Vec3 targetVec = player.getEyePosition(pt).add(player.getLookAngle().scale(this.range));
            this.selectedPos = BlockPos.containing(targetVec);
         } else {
            BlockHitResult trace = RaycastHelper.rayTraceRange(player.level(), player, 75.0);
            if (trace != null && trace.getType() == Type.BLOCK) {
               BlockPos hit = trace.getBlockPos();
               boolean replaceable = player.level()
                  .getBlockState(hit)
                  .canBeReplaced(new BlockPlaceContext(new UseOnContext(player, InteractionHand.MAIN_HAND, trace)));
               if (trace.getDirection().getAxis().isVertical() && !replaceable) {
                  hit = hit.relative(trace.getDirection());
               }

               this.selectedPos = hit;
            } else {
               this.selectedPos = null;
            }
         }

         this.selectedFace = null;
         if (this.secondPos != null) {
            AABB bb = new AABB(Vec3.atLowerCornerOf(this.firstPos), Vec3.atLowerCornerOf(this.secondPos)).expandTowards(1.0, 1.0, 1.0).inflate(0.45F);
            Vec3 projectedView = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
            boolean inside = bb.contains(projectedView);
            RaycastHelper.PredicateTraceResult result = RaycastHelper.rayTraceUntil(player, 70.0, pos -> inside ^ bb.contains(VecHelper.getCenterOf(pos)));
            this.selectedFace = result != null && !result.missed() ? (inside ? result.getFacing().getOpposite() : result.getFacing()) : null;
         }

         AABB currentSelectionBox = this.getCurrentSelectionBox();
         if (currentSelectionBox != null) {
            Outliner.getInstance()
               .chaseAABB(this.outlineSlot, currentSelectionBox)
               .colored(6850245)
               .lineWidth(0.0625F)
               .withFaceTexture(PonderSpecialTextures.BLANK)
               .highlightFace(this.selectedFace);
         }
      }
   }

   private AABB getCurrentSelectionBox() {
      if (this.secondPos == null) {
         if (this.firstPos == null) {
            return this.selectedPos == null ? null : new AABB(this.selectedPos);
         } else {
            return this.selectedPos == null
               ? new AABB(this.firstPos)
               : new AABB(Vec3.atLowerCornerOf(this.firstPos), Vec3.atLowerCornerOf(this.selectedPos)).expandTowards(1.0, 1.0, 1.0);
         }
      } else {
         return new AABB(Vec3.atLowerCornerOf(this.firstPos), Vec3.atLowerCornerOf(this.secondPos)).expandTowards(1.0, 1.0, 1.0);
      }
   }

   public BlueprintExport.SaveResult saveBlueprint(String name) {
      Minecraft mc = Minecraft.getInstance();
      LocalPlayer player = mc.player;
      if (player != null && this.firstPos != null && this.secondPos != null && mc.level != null) {
         Path dir = SceneStore.getStructureDir();
         BlueprintExport.SaveResult result = BlueprintExport.saveBlueprint(dir, name, true, mc.level, this.firstPos, this.secondPos);
         if (result.isSuccess() && result.exportResult() != null) {
            Path file = result.exportResult().file();
            String savedName = file.getFileName().toString();
            if (savedName.endsWith(".nbt")) {
               savedName = savedName.substring(0, savedName.length() - 4);
            }

            String id = "ponderer:" + savedName;
            sendStatus(player, Component.translatable("item.ponderer.blueprint.saved", new Object[]{id}).withStyle(ChatFormatting.GREEN));
            this.firstPos = null;
            this.secondPos = null;
            return result;
         } else {
            sendStatus(player, Component.literal(UIText.of(result.uiMessageKey(), result.uiMessageArgs())).withStyle(ChatFormatting.RED));
            return result;
         }
      } else {
         BlueprintExport.SaveResult failure = BlueprintExport.SaveResult.failure(
            "Cannot save blueprint because the selection is incomplete", "ponderer.ui.save_error.io", "Selection is incomplete"
         );
         if (player != null) {
            sendStatus(player, Component.literal(UIText.of(failure.uiMessageKey(), failure.uiMessageArgs())).withStyle(ChatFormatting.RED));
         }

         return failure;
      }
   }

   private boolean isActive() {
      return this.isPresent() && this.isBlueprintInHand();
   }

   private boolean isPresent() {
      Minecraft mc = Minecraft.getInstance();
      return mc != null && mc.level != null && mc.screen == null;
   }

   private boolean isBlueprintInHand() {
      Minecraft mc = Minecraft.getInstance();
      return mc.player == null ? false : BlueprintFeature.matchesCarrierStack(mc.player.getMainHandItem());
   }

   private static boolean hasCtrlDown() {
      return Screen.hasControlDown();
   }

   private static void sendStatus(LocalPlayer player, Component message) {
      if (player != null) {
         player.displayClientMessage(message, true);
      }
   }
}
