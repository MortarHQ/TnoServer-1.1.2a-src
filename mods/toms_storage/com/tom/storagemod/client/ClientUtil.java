package com.tom.storagemod.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.tom.storagemod.Config;
import com.tom.storagemod.Content;
import com.tom.storagemod.StorageTags;
import com.tom.storagemod.block.IConfiguratorHighlight;
import com.tom.storagemod.components.ConfiguratorComponent;
import com.tom.storagemod.item.WirelessTerminalItem;
import com.tom.storagemod.platform.Platform;
import com.tom.storagemod.util.ComponentJoiner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ClientUtil {
   private static Component[] tooltipExt = new Component[0];

   public static void tooltip(String key, List<Component> tooltip, Object... args) {
      tooltip(key, true, tooltip, args);
   }

   public static void tooltip(String key, boolean addShift, List<Component> tooltip, Object... args) {
      if (Screen.hasShiftDown()) {
         String[] sp = Component.translatable("tooltip.toms_storage." + key, args).getString().split("\\\\");

         for (int i = 0; i < sp.length; i++) {
            tooltip.add(Component.literal(sp[i]));
         }
      } else if (addShift) {
         tooltip.add(
            Component.translatable("tooltip.toms_storage.hold_shift_for_info").withStyle(new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY})
         );
      }
   }

   public static void setTooltip(Component... string) {
      tooltipExt = string;
   }

   public static void collectExtraTooltips(ItemStack stack, List<Component> toolip) {
      Collections.addAll(toolip, tooltipExt);
   }

   public static void drawTerminalOutline(PoseStack ps) {
      Minecraft mc = Minecraft.getInstance();
      Player player = mc.player;
      if (player != null) {
         if (WirelessTerminalItem.isPlayerHolding(player)) {
            BlockHitResult lookingAt = rayTrace(player, Config.get().wirelessRange, true);
            BlockState state = mc.level.getBlockState(lookingAt.getBlockPos());
            if (state.is(StorageTags.REMOTE_ACTIVATE)) {
               BlockPos pos = lookingAt.getBlockPos();
               Vec3 renderPos = mc.gameRenderer.getMainCamera().getPosition();
               VertexConsumer buf = mc.renderBuffers().bufferSource().getBuffer(RenderType.lines());
               drawShape(
                  ps,
                  buf,
                  state.getOcclusionShape(player.level(), pos),
                  pos.getX() - renderPos.x,
                  pos.getY() - renderPos.y,
                  pos.getZ() - renderPos.z,
                  1.0F,
                  1.0F,
                  1.0F,
                  0.4F
               );
               mc.renderBuffers().bufferSource().endBatch(RenderType.lines());
            }
         }
      }
   }

   private static BlockHitResult rayTrace(Player player, double maxDist, boolean hitFluids) {
      if (Platform.vivecraft) {
         HitResult vr = ViveCraftHelper.rayTraceVR(maxDist, hitFluids);
         if (vr != null) {
            return (BlockHitResult)vr;
         }
      }

      return (BlockHitResult)player.pick(maxDist, 0.0F, hitFluids);
   }

   public static void drawConfiguratorOutline(PoseStack ps) {
      Minecraft mc = Minecraft.getInstance();
      Player player = mc.player;
      if (player != null) {
         ItemStack is = player.getItemInHand(InteractionHand.MAIN_HAND);
         if (!is.is(Content.invConfig.get())) {
            is = player.getItemInHand(InteractionHand.OFF_HAND);
         }

         if (is.is(Content.invConfig.get())) {
            ConfiguratorComponent c = (ConfiguratorComponent)is.get(Content.configuratorComponent.get());
            if (!c.selecting() && !c.showInvBox()) {
               drawPaintedHighlights(ps);
            } else {
               Vec3 renderPos = mc.gameRenderer.getMainCamera().getPosition();
               VertexConsumer buf = mc.renderBuffers().bufferSource().getBuffer(RenderType.lines());

               for (BlockPos pos : c.selection()) {
                  double x = pos.getX() - renderPos.x;
                  double y = pos.getY() - renderPos.y;
                  double z = pos.getZ() - renderPos.z;
                  LevelRenderer.renderLineBox(ps, buf, x, y, z, x + 1.0, y + 1.0, z + 1.0, 1.0F, 1.0F, 1.0F, 0.4F);
               }

               VertexConsumer bufNd = mc.renderBuffers().bufferSource().getBuffer(CustomRenderTypes.linesNoDepth());
               if (c.massSelect() && mc.hitResult instanceof BlockHitResult hr) {
                  int sx = c.boxStart().getX();
                  int sy = c.boxStart().getY();
                  int sz = c.boxStart().getZ();
                  AABB bb = AABB.encapsulatingFullBlocks(new BlockPos(sx, sy, sz), hr.getBlockPos());
                  bb = bb.move(-renderPos.x, -renderPos.y, -renderPos.z);
                  LevelRenderer.renderLineBox(ps, bufNd, bb, 1.0F, 1.0F, 0.0F, 0.4F);
               }

               if (c.isBound()) {
                  double x = c.bound().getX() - renderPos.x;
                  double y = c.bound().getY() - renderPos.y;
                  double z = c.bound().getZ() - renderPos.z;
                  LevelRenderer.renderLineBox(ps, bufNd, x, y, z, x + 1.0, y + 1.0, z + 1.0, 1.0F, 0.0F, 0.0F, 1.0F);
               }

               mc.renderBuffers().bufferSource().endBatch();
            }
         }
      }
   }

   private static void drawPaintedHighlights(PoseStack ps) {
      Minecraft mc = Minecraft.getInstance();
      Player player = mc.player;
      VertexConsumer buf = mc.renderBuffers().bufferSource().getBuffer(CustomRenderTypes.linesNoDepth());
      Vec3 renderPos = mc.gameRenderer.getMainCamera().getPosition();
      BlockPos.betweenClosedStream(new AABB(player.blockPosition()).inflate(7.0))
         .forEach(
            pos -> {
               double dist = renderPos.distanceToSqr(pos.getX(), pos.getY(), pos.getZ());
               if (!(dist > 49.0)) {
                  BlockState state = mc.level.getBlockState(pos);
                  if (state.getBlock() instanceof IConfiguratorHighlight h) {
                     VoxelShape shape = h.getHighlightShape(state, mc.level, pos);
                     int color = h.getHighlightColor();
                     float a = 1.0F;
                     if (dist > 25.0) {
                        a = 1.0F - (float)((dist - 25.0) / 24.0);
                     }

                     drawShape(
                        ps,
                        buf,
                        shape,
                        pos.getX() - renderPos.x,
                        pos.getY() - renderPos.y,
                        pos.getZ() - renderPos.z,
                        ARGB32.red(color) / 255.0F,
                        ARGB32.green(color) / 255.0F,
                        ARGB32.blue(color) / 255.0F,
                        a
                     );
                  }
               }
            }
         );
   }

   private static void drawShape(
      PoseStack matrices, VertexConsumer vertexConsumer, VoxelShape voxelShape, double d, double e, double f, float g, float h, float i, float j
   ) {
      Pose entry = matrices.last();
      voxelShape.forAllEdges((k, l, m, n, o, p) -> {
         float q = (float)(n - k);
         float r = (float)(o - l);
         float s = (float)(p - m);
         float t = Mth.sqrt(q * q + r * r + s * s);
         q /= t;
         r /= t;
         s /= t;
         vertexConsumer.addVertex(entry.pose(), (float)(k + d), (float)(l + e), (float)(m + f)).setColor(g, h, i, j).setNormal(entry, q, r, s);
         vertexConsumer.addVertex(entry.pose(), (float)(n + d), (float)(o + e), (float)(p + f)).setColor(g, h, i, j).setNormal(entry, q, r, s);
      });
   }

   public static Component multilineTooltip(String text, Object... objects) {
      return Arrays.stream(I18n.get(text, objects).split("\\\\"))
         .<Component>map(Component::literal)
         .collect(ComponentJoiner.joining(Component.empty(), Component.literal("\n")));
   }

   public static void drawConfiguratorOverlay(GuiGraphics gr) {
      Minecraft mc = Minecraft.getInstance();
      Player player = mc.player;
      if (player != null && mc.screen == null) {
         ItemStack is = player.getItemInHand(InteractionHand.MAIN_HAND);
         if (!is.is(Content.invConfig.get())) {
            is = player.getItemInHand(InteractionHand.OFF_HAND);
         }

         if (is.is(Content.invConfig.get())) {
            List<Component> messages = new ArrayList<>();
            Component leftClk = Component.keybind("key.attack").withStyle(ChatFormatting.GREEN);
            Component rightClk = Component.keybind("key.use").withStyle(ChatFormatting.GREEN);
            Component shift = Component.keybind("key.sneak").withStyle(ChatFormatting.GREEN);
            ConfiguratorComponent c = (ConfiguratorComponent)is.get(Content.configuratorComponent.get());
            if (c.isBound()) {
               int x = c.bound().getX();
               int y = c.bound().getY();
               int z = c.bound().getZ();
               BlockPos pos = new BlockPos(x, y, z);
               if (mc.player.distanceToSqr(x, y, z) < 4096.0) {
                  Component lookingAt = null;
                  if (mc.hitResult != null) {
                     ItemStack item = Platform.getCloneItemStack(mc.level, mc.hitResult, pos, player);
                     if (!item.isEmpty()) {
                        lookingAt = item.getHoverName();
                     }
                  }

                  if (lookingAt == null) {
                     lookingAt = Component.literal("?");
                  }

                  if (mc.showOnlyReducedInfo()) {
                     messages.add(Component.translatable("tooltip.toms_storage.config_overlay.block_alt", new Object[]{lookingAt}));
                  } else {
                     messages.add(Component.translatable("tooltip.toms_storage.config_overlay.block", new Object[]{x, y, z}));
                     messages.add(lookingAt);
                  }

                  if (c.selecting()) {
                     if (c.massSelect()) {
                        messages.add(Component.translatable("tooltip.toms_storage.config_overlay.mass_select").withStyle(ChatFormatting.GOLD));
                        messages.add(Component.translatable("tooltip.toms_storage.config_overlay.mass_select.finish", new Object[]{leftClk}));
                        messages.add(Component.translatable("tooltip.toms_storage.config_overlay.mass_select.abort", new Object[]{shift, leftClk}));
                     } else {
                        messages.add(Component.translatable("tooltip.toms_storage.config_overlay.select").withStyle(ChatFormatting.GOLD));
                        messages.add(Component.translatable("tooltip.toms_storage.config_overlay.select.single", new Object[]{rightClk}));
                        messages.add(Component.translatable("tooltip.toms_storage.config_overlay.select.finish", new Object[]{rightClk}));
                        messages.add(Component.translatable("tooltip.toms_storage.config_overlay.select.start_mass", new Object[]{leftClk}));
                     }
                  }
               } else {
                  messages.add(Component.translatable("tooltip.toms_storage.config_overlay.too_far"));
               }
            } else {
               messages.add(Component.translatable("tooltip.toms_storage.config_overlay.configure", new Object[]{rightClk}));
               messages.add(Component.translatable("tooltip.toms_storage.config_overlay.show", new Object[]{leftClk}));
            }

            if (!messages.isEmpty()) {
               gr.renderTooltip(mc.font, messages, Optional.empty(), 5, 25);
            }
         }
      }
   }
}
