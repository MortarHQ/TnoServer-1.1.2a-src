package com.nododiiiii.ponderer.ui;

import com.nododiiiii.ponderer.blueprint.RaycastHelper;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.createmod.catnip.math.VecHelper;
import net.createmod.catnip.outliner.Outliner;
import net.createmod.ponder.enums.PonderSpecialTextures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public final class CoordPickState {
   private static boolean active = false;
   private static int phase = 0;
   @Nullable
   private static BlockPos firstPos;
   @Nullable
   private static BlockPos secondPos;
   @Nullable
   private static BlockPos selectedPos;
   @Nullable
   private static Direction selectedFace;
   private static int range = 10;
   private static Map<String, String> formSnapshot = new HashMap<>();
   @Nullable
   private static SnapshotReturnContext context;
   private static final Object OUTLINE_SLOT = new Object();
   private static final double PICK_RANGE = 75.0;

   private CoordPickState() {
   }

   public static void startPick(Map<String, String> snapshot, SnapshotReturnContext context) {
      active = true;
      phase = 1;
      firstPos = null;
      secondPos = null;
      selectedPos = null;
      selectedFace = null;
      range = 10;
      formSnapshot = new HashMap<>(snapshot);
      CoordPickState.context = context;
   }

   public static boolean isActive() {
      return active;
   }

   public static void onClientTick() {
      if (active) {
         Minecraft mc = Minecraft.getInstance();
         if (mc.player != null && mc.screen == null) {
            selectedPos = getCurrentHoverPos(mc);
            selectedFace = null;
            if (phase >= 3 && firstPos != null && secondPos != null) {
               AABB bb = new AABB(Vec3.atLowerCornerOf(firstPos), Vec3.atLowerCornerOf(secondPos)).expandTowards(1.0, 1.0, 1.0).inflate(0.45F);
               Vec3 projectedView = mc.gameRenderer.getMainCamera().getPosition();
               boolean inside = bb.contains(projectedView);
               RaycastHelper.PredicateTraceResult result = RaycastHelper.rayTraceUntil(mc.player, 70.0, pos -> inside ^ bb.contains(VecHelper.getCenterOf(pos)));
               selectedFace = result != null && !result.missed() ? (inside ? result.getFacing().getOpposite() : result.getFacing()) : null;
            }

            AABB previewBox = getPreviewBox();
            if (previewBox != null) {
               Outliner.getInstance()
                  .chaseAABB(OUTLINE_SLOT, previewBox)
                  .colored(5635925)
                  .lineWidth(0.0625F)
                  .withFaceTexture(PonderSpecialTextures.BLANK)
                  .highlightFace(selectedFace);
            }

            String promptKey;
            if (phase == 1) {
               promptKey = "ponderer.ui.trigger_editor.pick_prompt.first";
            } else if (phase == 2) {
               promptKey = "ponderer.ui.trigger_editor.pick_prompt.second";
            } else {
               promptKey = "ponderer.ui.trigger_editor.pick_prompt.third";
            }

            mc.player.displayClientMessage(Component.translatable(promptKey), true);
         }
      }
   }

   @Nullable
   private static AABB getPreviewBox() {
      if (phase == 1) {
         return selectedPos != null ? new AABB(selectedPos) : null;
      }

      if (phase == 2) {
         if (firstPos == null) {
            return null;
         } else {
            return selectedPos == null
               ? new AABB(firstPos)
               : new AABB(Vec3.atLowerCornerOf(firstPos), Vec3.atLowerCornerOf(selectedPos)).expandTowards(1.0, 1.0, 1.0);
         }
      } else {
         return firstPos != null && secondPos != null
            ? new AABB(Vec3.atLowerCornerOf(firstPos), Vec3.atLowerCornerOf(secondPos)).expandTowards(1.0, 1.0, 1.0)
            : null;
      }
   }

   public static boolean handleMiddleClick() {
      if (!active) {
         return false;
      }

      Minecraft mc = Minecraft.getInstance();
      if (mc.player == null || mc.level == null) {
         return false;
      }

      if (mc.screen != null) {
         return false;
      }

      BlockPos pos = selectedPos;
      if (pos == null) {
         BlockHitResult hit = getLookingAtBlock(mc);
         if (hit == null || hit.getType() == Type.MISS) {
            return true;
         }

         pos = hit.getBlockPos();
      }

      if (phase == 1) {
         firstPos = pos;
         formSnapshot.put("coord1_x", String.valueOf(pos.getX()));
         formSnapshot.put("coord1_y", String.valueOf(pos.getY()));
         formSnapshot.put("coord1_z", String.valueOf(pos.getZ()));
         phase = 2;
         return true;
      } else if (phase == 2) {
         secondPos = pos;
         phase = 3;
         return true;
      } else if (phase != 3) {
         return true;
      } else if (firstPos != null && secondPos != null) {
         formSnapshot.put("coord1_x", String.valueOf(firstPos.getX()));
         formSnapshot.put("coord1_y", String.valueOf(firstPos.getY()));
         formSnapshot.put("coord1_z", String.valueOf(firstPos.getZ()));
         formSnapshot.put("coord2_x", String.valueOf(secondPos.getX()));
         formSnapshot.put("coord2_y", String.valueOf(secondPos.getY()));
         formSnapshot.put("coord2_z", String.valueOf(secondPos.getZ()));
         reopenEditor();
         return true;
      } else {
         return true;
      }
   }

   public static boolean handleMouseScrolled(double delta) {
      if (!active) {
         return false;
      }

      if (!Screen.hasControlDown()) {
         return false;
      }

      if (delta == 0.0) {
         return true;
      }

      if (phase < 3) {
         range = (int)Mth.clamp(range + delta, 1.0, 100.0);
         return true;
      }

      if (firstPos != null && secondPos != null && selectedFace != null) {
         AABB bb = new AABB(Vec3.atLowerCornerOf(firstPos), Vec3.atLowerCornerOf(secondPos));
         Vec3 vec = Vec3.atLowerCornerOf(selectedFace.getNormal());
         Vec3 projectedView = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
         if (bb.contains(projectedView)) {
            delta *= -1.0;
         }

         int intDelta = (int)(delta > 0.0 ? Math.ceil(delta) : Math.floor(delta));
         int x = (int)vec.x * intDelta;
         int y = (int)vec.y * intDelta;
         int z = (int)vec.z * intDelta;
         AxisDirection axisDirection = selectedFace.getAxisDirection();
         if (axisDirection == AxisDirection.NEGATIVE) {
            bb = bb.move(-x, -y, -z);
         }

         double maxX = Math.max(bb.maxX - x * axisDirection.getStep(), bb.minX);
         double maxY = Math.max(bb.maxY - y * axisDirection.getStep(), bb.minY);
         double maxZ = Math.max(bb.maxZ - z * axisDirection.getStep(), bb.minZ);
         bb = new AABB(bb.minX, bb.minY, bb.minZ, maxX, maxY, maxZ);
         firstPos = BlockPos.containing(bb.minX, bb.minY, bb.minZ);
         secondPos = BlockPos.containing(bb.maxX, bb.maxY, bb.maxZ);
         LocalPlayer player = Minecraft.getInstance().player;
         if (player != null) {
            player.displayClientMessage(
               Component.translatable(
                  "item.ponderer.blueprint.dimensions", new Object[]{(int)bb.getXsize() + 1, (int)bb.getYsize() + 1, (int)bb.getZsize() + 1}
               ),
               true
            );
         }

         return true;
      } else {
         return true;
      }
   }

   public static void reset() {
      active = false;
      phase = 0;
      firstPos = null;
      secondPos = null;
      selectedPos = null;
      selectedFace = null;
      context = null;
      formSnapshot.clear();
   }

   @Nullable
   private static BlockHitResult getLookingAtBlock(Minecraft mc) {
      if (mc.player != null && mc.player.level() != null) {
         BlockHitResult hit = RaycastHelper.rayTraceRange(mc.player.level(), mc.player, 75.0);
         return hit != null && hit.getType() == Type.BLOCK ? hit : null;
      } else {
         return null;
      }
   }

   @Nullable
   private static BlockPos getCurrentHoverPos(Minecraft mc) {
      if (mc.player == null || mc.level == null) {
         return null;
      } else if (Screen.hasControlDown() && phase < 3) {
         float pt = mc.getTimer().getGameTimeDeltaPartialTick(false);
         Vec3 targetVec = mc.player.getEyePosition(pt).add(mc.player.getLookAngle().scale(range));
         return BlockPos.containing(targetVec);
      } else {
         BlockHitResult hit = getLookingAtBlock(mc);
         return hit != null ? hit.getBlockPos() : null;
      }
   }

   private static void reopenEditor() {
      SnapshotReturnContext reopenContext = context;
      active = false;
      phase = 0;
      firstPos = null;
      secondPos = null;
      selectedPos = null;
      selectedFace = null;
      context = null;
      if (reopenContext != null) {
         reopenContext.reopenEditor(formSnapshot);
      } else {
         formSnapshot.clear();
      }
   }
}
