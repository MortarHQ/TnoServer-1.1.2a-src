package dev.ftb.mods.ftbultimine.client;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.platform.InputConstants.Type;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.event.events.client.ClientRawInputEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.networking.NetworkManager;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.ui.GuiHelper;
import dev.ftb.mods.ftblibrary.util.PanelPositioning;
import dev.ftb.mods.ftblibrary.util.PanelPositioning.PanelPos;
import dev.ftb.mods.ftbultimine.CooldownTracker;
import dev.ftb.mods.ftbultimine.FTBUltimine;
import dev.ftb.mods.ftbultimine.api.util.CanUltimineResult;
import dev.ftb.mods.ftbultimine.config.FTBUltimineClientConfig;
import dev.ftb.mods.ftbultimine.event.LevelRenderLastEvent;
import dev.ftb.mods.ftbultimine.net.KeyPressedPacket;
import dev.ftb.mods.ftbultimine.net.ModeChangedPacket;
import dev.ftb.mods.ftbultimine.shape.ShapeRegistry;
import dev.ftb.mods.ftbultimine.utils.ShapeMerger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.joml.Matrix4f;

public class FTBUltimineClient {
   public static KeyMapping keyBindUltimine;
   public static KeyMapping keyBindNextMode;
   public static KeyMapping keyBindPrevMode;
   private static FTBUltimineClient instance;
   private boolean pressed;
   private boolean canUltimine;
   private CanUltimineResult canUltimineStatus;
   private List<BlockPos> shapeBlocks = Collections.emptyList();
   private int actualBlocks = 0;
   private List<CachedEdge> cachedEdges = null;
   private BlockPos cachedPos = null;
   public boolean hasScrolledYet = false;
   private long lastToggle = 0L;
   public final int INPUT_DELAY = 125;
   private int shapeIdx = 0;
   private int maxPanelWidth = 100;
   private List<FTBUltimineClient.IndentedLine> infoPanelList = List.of();

   public FTBUltimineClient() {
      instance = this;
      KeyMappingRegistry.register(keyBindUltimine = new KeyMapping("key.ftbultimine", Type.KEYSYM, 96, "key.categories.ftbultimine"));
      KeyMappingRegistry.register(keyBindNextMode = new KeyMapping("ftbultimine.change_shape.next", Type.KEYSYM, 265, "key.categories.ftbultimine"));
      KeyMappingRegistry.register(keyBindPrevMode = new KeyMapping("ftbultimine.change_shape.prev", Type.KEYSYM, 264, "key.categories.ftbultimine"));
      ClientTickEvent.CLIENT_PRE.register(this::clientTick);
      ClientGuiEvent.RENDER_HUD.register(this::renderGameOverlay);
      LevelRenderLastEvent.EVENT.register(this::renderInGame);
      ClientRawInputEvent.MOUSE_SCROLLED.register(this::onMouseScrolled);
      ClientRawInputEvent.KEY_PRESSED.register(this::onKeyPress);
   }

   public static FTBUltimineClient getInstance() {
      return instance;
   }

   public static Player getClientPlayer() {
      return Minecraft.getInstance().player;
   }

   public void setShape(int shapeIdx, List<BlockPos> blocks) {
      this.shapeIdx = shapeIdx;
      if (blocks != null) {
         this.actualBlocks = blocks.size();
         int maxRendered = Math.min(this.actualBlocks, (Integer)FTBUltimineClientConfig.RENDER_OUTLINE.get());
         this.shapeBlocks = blocks.subList(0, maxRendered);
         this.cachedEdges = null;
         this.updateEdges();
      }

      if (!this.pressed && (Boolean)FTBUltimineClientConfig.SHAPE_FEEDBACK_MESSAGE.get()) {
         Minecraft.getInstance()
            .player
            .displayClientMessage(
               Component.translatable("key.ftbultimine").append(" : ").append(ShapeRegistry.INSTANCE.getShape(shapeIdx).getDisplayName()), true
            );
      }
   }

   public Collection<BlockPos> getSelectedBlocks() {
      return this.actualBlocks != 0 && !this.shapeBlocks.isEmpty() ? this.shapeBlocks : null;
   }

   public void renderInGame(PoseStack stack) {
      if (this.pressed && this.cachedPos != null && this.cachedEdges != null && !this.cachedEdges.isEmpty()) {
         Minecraft mc = Minecraft.getInstance();
         if (this.canUltimine) {
            Camera activeRenderInfo = mc.getEntityRenderDispatcher().camera;
            Vec3 projectedView = activeRenderInfo.getPosition();
            stack.pushPose();
            stack.translate(this.cachedPos.getX() - projectedView.x, this.cachedPos.getY() - projectedView.y, this.cachedPos.getZ() - projectedView.z);
            Matrix4f matrix = stack.last().pose();
            VertexConsumer buffer = mc.renderBuffers().bufferSource().getBuffer(UltimineRenderTypes.LINES_NORMAL);

            for (CachedEdge edge : this.cachedEdges) {
               buffer.addVertex(matrix, edge.x1(), edge.y1(), edge.z1()).setColor(255, 255, 255, 255);
               buffer.addVertex(matrix, edge.x2(), edge.y2(), edge.z2()).setColor(255, 255, 255, 255);
            }

            mc.renderBuffers().bufferSource().endBatch(UltimineRenderTypes.LINES_NORMAL);
            VertexConsumer buffer2 = mc.renderBuffers().bufferSource().getBuffer(UltimineRenderTypes.LINES_TRANSPARENT);
            int alpha = (Integer)FTBUltimineClientConfig.PREVIEW_LINE_ALPHA.get();

            for (CachedEdge edge : this.cachedEdges) {
               buffer2.addVertex(matrix, edge.x1(), edge.y1(), edge.z1()).setColor(255, 255, 255, alpha);
               buffer2.addVertex(matrix, edge.x2(), edge.y2(), edge.z2()).setColor(255, 255, 255, alpha);
            }

            mc.renderBuffers().bufferSource().endBatch(UltimineRenderTypes.LINES_TRANSPARENT);
            stack.popPose();
         }
      }
   }

   public EventResult onMouseScrolled(Minecraft client, double amountX, double amountY) {
      if (this.pressed && (amountY != 0.0 || amountX != 0.0) && this.isMenuSneaking()) {
         NetworkManager.sendToServer(new ModeChangedPacket(amountX < 0.0 || amountY < 0.0));
         this.hasScrolledYet = true;
         return EventResult.interruptFalse();
      } else {
         return EventResult.pass();
      }
   }

   public EventResult onKeyPress(Minecraft client, int keyCode, int scanCode, int action, int modifiers) {
      if (System.currentTimeMillis() - this.lastToggle < 125L) {
         return EventResult.pass();
      }

      boolean nextMode;
      if (ClientPlatformUtil.doesKeybindMatch(keyBindPrevMode, keyCode, scanCode, modifiers)) {
         nextMode = false;
      } else {
         if (!ClientPlatformUtil.doesKeybindMatch(keyBindNextMode, keyCode, scanCode, modifiers)) {
            return EventResult.pass();
         }

         nextMode = true;
      }

      if ((this.pressed || !(Boolean)FTBUltimineClientConfig.REQUIRE_ULTIMINE_KEY_FOR_CYCLING.get()) && this.isMenuSneaking()) {
         NetworkManager.sendToServer(new ModeChangedPacket(nextMode));
         this.lastToggle = System.currentTimeMillis();
         this.hasScrolledYet = true;
      }

      return EventResult.pass();
   }

   private boolean isMenuSneaking() {
      if (!(Boolean)FTBUltimineClientConfig.REQUIRE_SNEAK_FOR_MENU.get()) {
         return true;
      } else {
         return !keyBindUltimine.matches(340, 0) && !keyBindUltimine.matches(344, 0) ? Screen.hasShiftDown() : Screen.hasControlDown();
      }
   }

   private List<FTBUltimineClient.IndentedLine> addPressedInfo() {
      Builder<FTBUltimineClient.IndentedLine> builder = ImmutableList.builder();
      boolean isActive = true;
      Component msg;
      if (CooldownTracker.isOnCooldown(getClientPlayer())) {
         msg = Component.translatable("ftbultimine.info.cooldown").withStyle(style -> style.withColor(TextColor.fromRgb(12566380)));
      } else if (this.canUltimine && this.actualBlocks > 0) {
         msg = Component.translatable("ftbultimine.info.active").withStyle(style -> style.withColor(TextColor.fromRgb(10731148)));
      } else {
         msg = Component.translatable("ftbultimine.info.not_active").withStyle(style -> style.withColor(TextColor.fromRgb(12542314)));
         isActive = false;
      }

      builder.add(new FTBUltimineClient.IndentedLine(0, Component.translatable("ftbultimine.info.base", new Object[]{msg})));
      if (!isActive) {
         builder.add(
            new FTBUltimineClient.IndentedLine(
               8, Component.translatable(this.canUltimineStatus.getTranslationKey()).withStyle(style -> style.withColor(TextColor.fromRgb(12542314)))
            )
         );
      }

      ShapeRegistry shapeRegistry = ShapeRegistry.INSTANCE;
      int context = Math.min((shapeRegistry.shapeCount() - 1) / 2, (Integer)FTBUltimineClientConfig.SHAPE_MENU_CONTEXT_LINES.get());
      boolean showingMenu = this.isMenuSneaking();
      if (showingMenu) {
         if (isActive) {
            builder.add(new FTBUltimineClient.IndentedLine(0, Component.empty()));
         }

         for (int i = -context; i < 0; i++) {
            builder.add(new FTBUltimineClient.IndentedLine(16, shapeRegistry.getShape(this.shapeIdx + i).getDisplayName().withStyle(ChatFormatting.GRAY)));
         }
      } else {
         builder.add(
            new FTBUltimineClient.IndentedLine(
               0, Component.translatable("ftbultimine.change_shape.short").withStyle(new ChatFormatting[]{ChatFormatting.GRAY, ChatFormatting.ITALIC})
            )
         );
         builder.add(new FTBUltimineClient.IndentedLine(0, Component.empty()));
      }

      builder.add(
         new FTBUltimineClient.IndentedLine(showingMenu ? 16 : 0, shapeRegistry.getShape(this.shapeIdx).getDisplayName().withStyle(ChatFormatting.YELLOW))
      );
      if (showingMenu) {
         for (int i = 1; i <= context; i++) {
            builder.add(new FTBUltimineClient.IndentedLine(16, shapeRegistry.getShape(this.shapeIdx + i).getDisplayName().withStyle(ChatFormatting.GRAY)));
         }
      }

      if (this.canUltimine && this.actualBlocks != 0) {
         MutableComponent mining = Component.empty();
         mining.append(Component.translatable("ftbultimine.info.blocks", new Object[]{this.actualBlocks}));
         if (this.actualBlocks > this.shapeBlocks.size()) {
            mining.append(", ").append(Component.translatable("ftbultimine.info.partial_render", new Object[]{this.shapeBlocks.size()}));
         }

         builder.add(new FTBUltimineClient.IndentedLine(0, Component.empty()));
         builder.add(new FTBUltimineClient.IndentedLine(0, mining.withColor(10731148)));
      }

      return builder.build();
   }

   public void renderGameOverlay(GuiGraphics graphics, DeltaTracker tickDelta) {
      if (this.pressed) {
         Minecraft mc = Minecraft.getInstance();
         RenderSystem.enableBlend();
         RenderSystem.blendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
         Font font = mc.font;
         int width = Math.max(this.maxPanelWidth, 10 + this.infoPanelList.stream().map(l -> font.width(l.text)).max(Integer::compareTo).orElse(100));
         this.maxPanelWidth = width;
         int height = 9 * this.infoPanelList.size();
         float scale = ((Double)FTBUltimineClientConfig.OVERLAY_SCALE.get()).floatValue();
         int insetX = (Integer)FTBUltimineClientConfig.OVERLAY_INSET_X.get();
         int insetY = (Integer)FTBUltimineClientConfig.OVERLAY_INSET_Y.get();
         PanelPos pos = ((PanelPositioning)FTBUltimineClientConfig.OVERLAY_POS.get())
            .getPanelPos(mc.getWindow().getGuiScaledWidth(), mc.getWindow().getGuiScaledHeight(), (int)(width * scale), (int)(height * scale), insetX, insetY);
         graphics.pose().pushPose();
         graphics.pose().translate(pos.x(), pos.y(), 100.0F);
         graphics.pose().scale(scale, scale, 1.0F);
         Color4I.DARK_GRAY.withAlpha(128).draw(graphics, -2, -2, width + 4, height + 4);
         GuiHelper.drawHollowRect(graphics, -2, -2, width + 4, height + 4, Color4I.GRAY.withAlpha(128), false);
         float f = CooldownTracker.getCooldownRemaining(getClientPlayer());
         if (f < 1.0F) {
            Color4I.rgba(-1438605248).draw(graphics, 0, 0, (int)(width * f) + 1, 9 + 1);
         }

         if (this.isMenuSneaking()) {
            int barUpper = 9 * 2;
            int barHeight = 9 * (ShapeRegistry.INSTANCE.shapeCount() - 1) - 2;
            Color4I col = Color4I.WHITE.withAlpha(192);
            col.draw(graphics, 3, barUpper, 2, barHeight);
            col.draw(graphics, 2, barUpper + 1, 4, 1);
            col.draw(graphics, 1, barUpper + 2, 6, 1);
            col.draw(graphics, 2, barUpper + barHeight - 2, 4, 1);
            col.draw(graphics, 1, barUpper + barHeight - 3, 6, 1);
         }

         int top = 0;

         for (FTBUltimineClient.IndentedLine line : this.infoPanelList) {
            FormattedCharSequence formatted = line.text.getVisualOrderText();
            graphics.drawString(font, formatted, line.indent, top, 15527924, true);
            top += 9;
         }

         graphics.pose().popPose();
      }
   }

   public void clientTick(Minecraft mc) {
      if (Minecraft.getInstance().player != null) {
         boolean p = this.pressed;
         if ((this.pressed = keyBindUltimine.isDown()) != p) {
            NetworkManager.sendToServer(new KeyPressedPacket(this.pressed));
            if (this.pressed && !this.hasScrolledYet && mc.player != null) {
               MutableComponent msg1 = Component.translatable(
                  FTBUltimineClientConfig.REQUIRE_SNEAK_FOR_MENU.get() ? "ftbultimine.change_shape" : "ftbultimine.change_shape.no_shift"
               );
               mc.player.displayClientMessage(msg1, true);
            }
         }

         this.canUltimineStatus = mc.hitResult instanceof BlockHitResult b && b.getType() == net.minecraft.world.phys.HitResult.Type.BLOCK
            ? FTBUltimine.getInstance().canUltimine(mc.player, b.getBlockPos(), mc.player.level().getBlockState(b.getBlockPos()))
            : CanUltimineResult.NO_BLOCK_TARGETED;
         this.canUltimine = this.pressed && this.canUltimineStatus.isAllowed();
         if (this.pressed) {
            this.infoPanelList = this.addPressedInfo();
         }
      }
   }

   private void updateEdges() {
      if (this.cachedEdges == null) {
         if (this.shapeBlocks.isEmpty()) {
            this.cachedEdges = Collections.emptyList();
         } else {
            this.cachedPos = this.shapeBlocks.getFirst();
            this.cachedEdges = new ArrayList<>();
            Collection<VoxelShape> shapes = new HashSet<>();

            for (AABB aabb : ShapeMerger.merge(this.shapeBlocks, this.cachedPos)) {
               shapes.add(Shapes.create(aabb.inflate(0.005)));
            }

            orShapes(shapes).forAllEdges((x1, y1, z1, x2, y2, z2) -> this.cachedEdges.add(CachedEdge.fromDoubles(x1, y1, z1, x2, y2, z2)));
         }
      }
   }

   static VoxelShape orShapes(Collection<VoxelShape> shapes) {
      VoxelShape combinedShape = Shapes.empty();

      for (VoxelShape shape : shapes) {
         combinedShape = Shapes.joinUnoptimized(combinedShape, shape, BooleanOp.OR);
      }

      return combinedShape.optimize();
   }

   private record IndentedLine(int indent, Component text) {
   }
}
