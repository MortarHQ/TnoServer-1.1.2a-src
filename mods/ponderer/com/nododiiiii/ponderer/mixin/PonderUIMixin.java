package com.nododiiiii.ponderer.mixin;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexSorting;
import com.nododiiiii.ponderer.blueprint.BlueprintFeature;
import com.nododiiiii.ponderer.platform.PondererServices;
import com.nododiiiii.ponderer.ponder.DslScene;
import com.nododiiiii.ponderer.ponder.PackStateStore;
import com.nododiiiii.ponderer.ponder.PonderSceneViewOffsetAccess;
import com.nododiiiii.ponderer.ponder.SceneRuntime;
import com.nododiiiii.ponderer.ui.InterfaceSlotEditState;
import com.nododiiiii.ponderer.ui.PickState;
import com.nododiiiii.ponderer.ui.PonderItemGridScreen;
import com.nododiiiii.ponderer.ui.PonderScreenNavigation;
import com.nododiiiii.ponderer.ui.PondererConfigScreen;
import com.nododiiiii.ponderer.ui.PondererDialogScreen;
import com.nododiiiii.ponderer.ui.ReadonlyPackImportPromptScreen;
import com.nododiiiii.ponderer.ui.SceneEditorScreen;
import com.nododiiiii.ponderer.ui.UIText;
import com.nododiiiii.ponderer.ui.UiAnchorCoords;
import com.nododiiiii.ponderer.ui.UiAnchorViewport;
import java.util.List;
import net.createmod.ponder.foundation.PonderScene;
import net.createmod.ponder.foundation.PonderScene.SceneTransform;
import net.createmod.ponder.foundation.ui.PonderButton;
import net.createmod.ponder.foundation.ui.PonderUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PonderUI.class)
public abstract class PonderUIMixin extends Screen {
   @Unique
   private PonderButton ponderer$editButton;

   protected PonderUIMixin() {
      super(CommonComponents.EMPTY);
   }

   @Inject(method = "init", at = @At("TAIL"))
   private void ponderer$addEditButton(CallbackInfo ci) {
      PonderUI self = (PonderUI)this;
      SceneRuntime.SceneMatch match = ponderer$resolveDynamicScene(self);
      if (match != null) {
         SceneEditorScreen.handlePonderUiFocusChanged(match.scene());
         if (canEdit(Minecraft.getInstance().player)) {
            int bY = this.height - 20 - 31;
            PonderButton editButton = (PonderButton)new PonderButton(this.width - 80 - 31, bY).showing(new ItemStack(Items.WRITABLE_BOOK)).enableFade(0, 5);
            editButton.withCallback(() -> {
               PonderUI current = (PonderUI)this;
               SceneRuntime.SceneMatch result = ponderer$resolveDynamicScene(current);
               if (result != null) {
                  if (!SceneEditorScreen.canModifyScene(result.scene())) {
                     ponderer$showReadonlyScenePrompt(current);
                     return;
                  }

                  String packId = result.scene().pack;
                  PackStateStore.load();
                  if (packId != null && !packId.isBlank() && !PackStateStore.isImported(packId)) {
                     new ReadonlyPackImportPromptScreen(current, packId, result.scene().sceneKey(), result.sceneIndex()).open();
                     return;
                  }

                  SceneEditorScreen.markUiToEditorTransition(result.scene());
                  PonderScreenNavigation.suppressNextPonderReturn();
                  Minecraft.getInstance().setScreen(new SceneEditorScreen(result.scene(), result.sceneIndex()));
               }
            });
            this.ponderer$editButton = editButton;
            this.addRenderableWidget(editButton);
         }
      }
   }

   @Unique
   private static void ponderer$showReadonlyScenePrompt(Screen source) {
      new PondererDialogScreen(
            source,
            List.of(Component.translatable("ponderer.ui.readonly_scene.title")),
            List.of(Component.translatable("ponderer.ui.readonly_scene.message")),
            List.of(PondererDialogScreen.button(Component.translatable("ponderer.ui.readonly_scene.open_config"), dialog -> {
               PonderScreenNavigation.ReturnState returnState = PonderScreenNavigation.captureReturnState();
               dialog.closeToSource();
               PonderScreenNavigation.suppressNextPonderReturn();
               Minecraft.getInstance().setScreen(new PondererConfigScreen(source, returnState));
            }), PondererDialogScreen.closeButton(Component.translatable("ponderer.ui.cancel")))
         )
         .open();
   }

   @Inject(method = "renderWindow", at = @At("TAIL"), remap = false)
   private void ponderer$renderWidgetsOnTop(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
      graphics.pose().pushPose();
      graphics.pose().translate(0.0F, 0.0F, 1200.0F);

      for (GuiEventListener child : this.children()) {
         if (child instanceof PonderButton button && button.visible) {
            button.render(graphics, mouseX, mouseY, partialTicks);
         }
      }

      graphics.pose().popPose();
   }

   @Inject(method = "replay", at = @At("TAIL"), remap = false)
   private void ponderer$resetViewOnReplay(CallbackInfo ci) {
      PonderUI self = (PonderUI)this;
      ponderer$resetCustomView(self.getActiveScene());
   }

   @Inject(method = "scroll", at = @At("RETURN"), remap = false)
   private void ponderer$resetViewOnScroll(boolean forward, CallbackInfoReturnable<Boolean> cir) {
      if ((Boolean)cir.getReturnValue()) {
         PonderUI self = (PonderUI)this;
         ponderer$resetCustomView(self.getActiveScene());
         SceneRuntime.SceneMatch match = ponderer$resolveDynamicScene(self);
         if (match != null) {
            SceneEditorScreen.handlePonderUiFocusChanged(match.scene());
         } else {
            SceneEditorScreen.handlePonderUiFocusChanged(null);
         }
      }
   }

   @Inject(method = "removed", at = @At("TAIL"), remap = false)
   private void ponderer$clearSceneEditorUndoOnTrueExit(CallbackInfo ci) {
      PonderUI self = (PonderUI)this;
      SceneRuntime.SceneMatch match = ponderer$resolveDynamicScene(self);
      if (match != null) {
         SceneEditorScreen.handlePonderUiRemoved(match.scene());
      } else {
         SceneEditorScreen.handlePonderUiRemoved(null);
      }
   }

   private static void ponderer$resetCustomView(PonderScene scene) {
      if (scene instanceof PonderSceneViewOffsetAccess viewOffset) {
         viewOffset.ponderer$resetViewOffset();
         if (scene instanceof PonderSceneAccessor accessor) {
            float defaultScale = viewOffset.ponderer$getDefaultScale();
            if (!Float.isNaN(defaultScale)) {
               accessor.ponderer$setScaleFactor(defaultScale);
            }
         }

         viewOffset.ponderer$setDefaultScale(Float.NaN);
      }
   }

   private static boolean canEdit(Player player) {
      if (player == null) {
         return false;
      }

      if (player.isCreative()) {
         return true;
      }

      for (ItemStack stack : player.getInventory().items) {
         if (BlueprintFeature.matchesCarrierStack(stack)) {
            return true;
         }
      }

      return false;
   }

   private static int ponderer$computeOccurrenceIndex(PonderUI ui, PonderScene target) {
      PonderUIAccessor accessor = (PonderUIAccessor)ui;
      List<PonderScene> allScenes = accessor.ponderer$getScenes();
      ResourceLocation targetId = target.getId();
      int occurrence = 0;

      for (PonderScene s : allScenes) {
         if (s == target) {
            return occurrence;
         }

         if (s.getId().equals(targetId)) {
            occurrence++;
         }
      }

      return 0;
   }

   @Unique
   private static SceneRuntime.SceneMatch ponderer$resolveDynamicScene(PonderUI ui) {
      PonderScene active = ui.getActiveScene();
      if (!"ponderer".equals(active.getNamespace())) {
         return null;
      }

      int occurrence = ponderer$computeOccurrenceIndex(ui, active);
      return SceneRuntime.findBySceneId(active.getId(), occurrence);
   }

   @Unique
   private static boolean ponderer$shouldRenderFabricShowInterfaceNotice(PonderUI ui) {
      if (!"fabric".equals(PondererServices.PLATFORM.getPlatformName())) {
         return false;
      } else {
         return PondererServices.PLATFORM.supportsEmbeddedInterfacePreview() ? false : ponderer$isShowInterfaceScene(ponderer$resolveDynamicScene(ui));
      }
   }

   @Unique
   private static boolean ponderer$isShowInterfaceScene(SceneRuntime.SceneMatch match) {
      if (match == null) {
         return false;
      }

      DslScene scene = match.scene();
      int sceneIndex = match.sceneIndex();
      if (scene != null && scene.scenes != null && sceneIndex >= 0 && sceneIndex < scene.scenes.size()) {
         DslScene.SceneSegment segment = scene.scenes.get(sceneIndex);
         if (segment != null && segment.steps != null) {
            for (DslScene.DslStep step : segment.steps) {
               if (step != null && step.type != null && !step.type.isBlank()) {
                  return "show_interface".equalsIgnoreCase(step.type);
               }
            }

            return false;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   @Inject(method = "renderWidgets", at = @At("TAIL"), remap = false)
   private void ponderer$renderFabricShowInterfaceNotice(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
      PonderUI self = (PonderUI)this;
      if (ponderer$shouldRenderFabricShowInterfaceNotice(self)) {
         UiAnchorViewport.Rect viewport = UiAnchorViewport.resolve(Minecraft.getInstance());
         if (viewport.isValid()) {
            String notice = UIText.of("ponderer.ui.show_interface.fabric_preview_unavailable");
            Font font = Minecraft.getInstance().font;
            int textWidth = font.width(notice);
            float maxWidth = (float)Math.max(1.0, viewport.width() - 12.0);
            float scale = textWidth > maxWidth ? Math.max(0.65F, maxWidth / textWidth) : 1.0F;
            int centerX = (int)Math.round(viewport.left() + viewport.width() * 0.5);
            int centerY = (int)Math.round(viewport.top() + viewport.height() * 0.5);
            int boxHalfWidth = (int)Math.ceil(textWidth * scale * 0.5F) + 8;
            int boxHalfHeight = (int)Math.ceil(9.0F * scale * 0.5F) + 6;
            graphics.flush();
            graphics.pose().pushPose();
            graphics.pose().translate(0.0F, 0.0F, 120.0F);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableDepthTest();
            graphics.fill(centerX - boxHalfWidth - 1, centerY - boxHalfHeight - 1, centerX + boxHalfWidth + 1, centerY + boxHalfHeight + 1, -1066773728);
            graphics.fill(centerX - boxHalfWidth, centerY - boxHalfHeight, centerX + boxHalfWidth, centerY + boxHalfHeight, -535687672);
            graphics.pose().pushPose();
            graphics.pose().translate(centerX, centerY - 9.0F * scale * 0.5F, 1.0F);
            graphics.pose().scale(scale, scale, 1.0F);
            graphics.drawString(font, notice, Math.round(-textWidth * 0.5F), 0, 16311728, false);
            graphics.pose().popPose();
            RenderSystem.enableDepthTest();
            graphics.pose().popPose();
            graphics.flush();
         }
      }
   }

   @Inject(method = "tick", at = @At("HEAD"))
   private void ponderer$tickPickModeReset(CallbackInfo ci) {
      if (PickState.isActive()) {
         if (!PickState.isUiPointPickActive()) {
            PonderUIAccessor accessor = (PonderUIAccessor)this;
            accessor.ponderer$setIdentifyMode(false);
         }
      }
   }

   @Inject(
      method = "tick",
      at = @At(
         value = "INVOKE",
         target = "Lnet/createmod/ponder/foundation/ui/PonderUI;updateIdentifiedItem(Lnet/createmod/ponder/foundation/PonderScene;)V",
         remap = false
      )
   )
   private void ponderer$tickPickModeEnable(CallbackInfo ci) {
      if (PickState.isActive()) {
         if (!PickState.isUiPointPickActive()) {
            PonderUIAccessor accessor = (PonderUIAccessor)this;
            accessor.ponderer$setIdentifyMode(true);
         }
      }
   }

   @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
   private void ponderer$onPickClick(double x, double y, int button, CallbackInfoReturnable<Boolean> cir) {
      if (PickState.isActive()) {
         if (PickState.isUiPointPickActive()) {
            if (button == 0) {
               UiAnchorViewport.Rect viewport = UiAnchorViewport.resolve(Minecraft.getInstance());
               double nx = UiAnchorCoords.normalizeX(x - viewport.left(), (int)Math.max(1.0, viewport.width()));
               double ny = UiAnchorCoords.normalizeY(y - viewport.top(), (int)Math.max(1.0, viewport.height()));
               PickState.completeUiPick(nx, ny);
               cir.setReturnValue(true);
            }
         } else {
            if (button == 0 || button == 1) {
               PonderUIAccessor accessor = (PonderUIAccessor)this;
               accessor.ponderer$setIdentifyMode(true);
               PonderUI self = (PonderUI)this;
               self.updateIdentifiedItem(self.getActiveScene());
               BlockPos pos = accessor.ponderer$getHoveredBlockPos();
               if (pos != null) {
                  Direction face = this.ponderer$getHitFace(self.getActiveScene(), pos);
                  if (button == 1) {
                     pos = pos.relative(face);
                  }

                  PickState.completePick(pos, face);
                  cir.setReturnValue(true);
                  return;
               }
            }
         }
      }
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (!InterfaceSlotEditState.isActive() || keyCode != 256 && keyCode != 259) {
         if (!PickState.isActive() || keyCode != 256 && keyCode != 259) {
            return super.keyPressed(keyCode, scanCode, modifiers);
         }

         PickState.cancelPick();
         return true;
      } else {
         InterfaceSlotEditState.finishAndReopenEditor();
         return true;
      }
   }

   @Inject(method = "renderWidgets", at = @At("TAIL"), remap = false)
   private void ponderer$renderPickHint(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
      if (PickState.isActive() || InterfaceSlotEditState.isActive()) {
         Font font = Minecraft.getInstance().font;
         graphics.pose().pushPose();
         graphics.pose().translate(0.0F, 0.0F, 1000.0F);
         if (InterfaceSlotEditState.isActive()) {
            String line1 = UIText.of("ponderer.ui.change_interface_slot.hint.drag");
            String line2 = UIText.of("ponderer.ui.change_interface_slot.hint.exit", InterfaceSlotEditState.bindingCount());
            int w1 = font.width(line1);
            int w2 = font.width(line2);
            int boxW = Math.max(w1, w2) + 8;
            int boxH = 26;
            int tx = mouseX + 10;
            int ty = mouseY - boxH - 17;
            if (tx < 2) {
               tx = 2;
            }

            if (tx + boxW > this.width - 2) {
               tx = this.width - boxW - 2;
            }

            if (ty < 2) {
               ty = 2;
            }

            graphics.fill(tx - 2, ty - 2, tx + boxW + 2, ty + boxH + 2, -267386848);
            graphics.fill(tx - 1, ty - 1, tx + boxW + 1, ty + boxH + 1, -1069909398);
            graphics.fill(tx, ty, tx + boxW, ty + boxH, -267386848);
            graphics.drawString(font, line1, tx + 4, ty + 3, 6750156);
            graphics.drawString(font, line2, tx + 4, ty + 15, 12632256);
            graphics.pose().popPose();
         } else if (PickState.isUiPointPickActive()) {
            UiAnchorViewport.Rect viewport = UiAnchorViewport.resolve(Minecraft.getInstance());
            double nx = UiAnchorCoords.normalizeX(mouseX - viewport.left(), (int)Math.max(1.0, viewport.width()));
            double ny = UiAnchorCoords.normalizeY(mouseY - viewport.top(), (int)Math.max(1.0, viewport.height()));
            String line1 = String.format("UI锚点 [%.3f, %.3f] 左键选取", nx, ny);
            String line2 = "ESC/Backspace 返回";
            int w1 = font.width(line1);
            int w2 = font.width(line2);
            int boxW = Math.max(w1, w2) + 8;
            int boxH = 26;
            int tx = mouseX + 10;
            int ty = mouseY - boxH - 17;
            if (tx < 2) {
               tx = 2;
            }

            if (tx + boxW > this.width - 2) {
               tx = this.width - boxW - 2;
            }

            if (ty < 2) {
               ty = 2;
            }

            graphics.fill(tx - 2, ty - 2, tx + boxW + 2, ty + boxH + 2, -267386848);
            graphics.fill(tx - 1, ty - 1, tx + boxW + 1, ty + boxH + 1, -1068482400);
            graphics.fill(tx, ty, tx + boxW, ty + boxH, -267386848);
            graphics.drawString(font, line1, tx + 4, ty + 3, 6750054);
            graphics.drawString(font, line2, tx + 4, ty + 15, 8421504);
            graphics.pose().popPose();
         } else {
            PonderUIAccessor accessor = (PonderUIAccessor)this;
            BlockPos pos = accessor.ponderer$getHoveredBlockPos();
            if (pos != null) {
               PonderUI self = (PonderUI)this;
               Direction face = this.ponderer$getHitFace(self.getActiveScene(), pos);
               BlockPos adjacent = pos.relative(face);
               String line1;
               String line2;
               if (PickState.isHalfOffset()) {
                  Axis faceAxis = face.getAxis();
                  line1 = "[ "
                     + ponderer$fmtCoord(pos.getX(), faceAxis != Axis.X)
                     + ", "
                     + ponderer$fmtCoord(pos.getY(), faceAxis != Axis.Y)
                     + ", "
                     + ponderer$fmtCoord(pos.getZ(), faceAxis != Axis.Z)
                     + " ] 左键选取";
                  line2 = "[ "
                     + ponderer$fmtCoord(adjacent.getX(), faceAxis != Axis.X)
                     + ", "
                     + ponderer$fmtCoord(adjacent.getY(), faceAxis != Axis.Y)
                     + ", "
                     + ponderer$fmtCoord(adjacent.getZ(), faceAxis != Axis.Z)
                     + " ] 右键选取";
               } else {
                  line1 = "[ " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + " ] 左键选取";
                  line2 = "[ " + adjacent.getX() + ", " + adjacent.getY() + ", " + adjacent.getZ() + " ] 右键选取";
               }

               int w1 = font.width(line1);
               int w2 = font.width(line2);
               int boxW = Math.max(w1, w2) + 8;
               int boxH = 26;
               int tx = mouseX + 10;
               int ty = mouseY - boxH - 17;
               if (tx < 2) {
                  tx = 2;
               }

               if (tx + boxW > this.width - 2) {
                  tx = this.width - boxW - 2;
               }

               if (ty < 2) {
                  ty = 2;
               }

               graphics.fill(tx - 2, ty - 2, tx + boxW + 2, ty + boxH + 2, -267386848);
               graphics.fill(tx - 1, ty - 1, tx + boxW + 1, ty + boxH + 1, -1068482400);
               graphics.fill(tx, ty, tx + boxW, ty + boxH, -267386848);
               graphics.drawString(font, line1, tx + 4, ty + 3, 16766720);
               graphics.drawString(font, line2, tx + 4, ty + 15, 6750054);
            } else {
               String hint = "ESC/Backspace 返回";
               int textW = font.width(hint) + 8;
               int tx = mouseX + 10;
               int ty = mouseY - 31;
               if (tx < 2) {
                  tx = 2;
               }

               if (tx + textW > this.width - 2) {
                  tx = this.width - textW - 2;
               }

               if (ty < 2) {
                  ty = 2;
               }

               graphics.fill(tx - 2, ty - 2, tx + textW + 2, ty + 16, -267386848);
               graphics.fill(tx - 1, ty - 1, tx + textW + 1, ty + 15, -1068482400);
               graphics.fill(tx, ty, tx + textW, ty + 14, -267386848);
               graphics.drawString(font, hint, tx + 4, ty + 3, 8421504);
            }

            graphics.pose().popPose();
         }
      }
   }

   @Inject(method = "renderOverlay", at = @At("HEAD"), remap = false)
   private void ponderer$overlayNoDepthPre(GuiGraphics graphics, int i, float partialTicks, CallbackInfo ci) {
      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
   }

   @Inject(method = "renderScene", at = @At("TAIL"), remap = false)
   private void ponderer$extendProjectionDepth(GuiGraphics graphics, int mouseX, int mouseY, int i, float partialTicks, CallbackInfo ci) {
      Matrix4f projection = new Matrix4f(RenderSystem.getProjectionMatrix());
      projection.translate(0.0F, 0.0F, 400.0F);
      RenderSystem.setProjectionMatrix(projection, VertexSorting.DISTANCE_TO_ORIGIN);
   }

   @Inject(method = "renderOverlay", at = @At("RETURN"), remap = false)
   private void ponderer$overlayNoDepthPost(GuiGraphics graphics, int i, float partialTicks, CallbackInfo ci) {
      RenderSystem.depthMask(true);
      RenderSystem.enableDepthTest();
   }

   @Inject(method = "removed", at = @At("TAIL"))
   private void ponderer$onRemoved(CallbackInfo ci) {
      if (PickState.isActive()) {
         PickState.reset();
      }

      if (InterfaceSlotEditState.isActive()) {
         InterfaceSlotEditState.reset();
      }

      if (!PonderScreenNavigation.consumeSuppressNextPonderReturn()) {
         if (PonderItemGridScreen.returnScreen != null) {
            PonderItemGridScreen ret = PonderItemGridScreen.returnScreen;
            PonderItemGridScreen.returnScreen = null;
            Minecraft.getInstance().execute(() -> Minecraft.getInstance().setScreen(ret));
         }
      }
   }

   private static String ponderer$fmtCoord(int value, boolean offset) {
      return offset ? value + 0.5 + "" : String.valueOf(value);
   }

   private Direction ponderer$getHitFace(PonderScene activeScene, BlockPos pos) {
      Minecraft mc = Minecraft.getInstance();
      Window w = mc.getWindow();
      double mx = mc.mouseHandler.xpos() * w.getGuiScaledWidth() / w.getScreenWidth();
      double my = mc.mouseHandler.ypos() * w.getGuiScaledHeight() / w.getScreenHeight();
      SceneTransform t = activeScene.getTransform();
      Vec3 from = t.screenToScene(mx, my, 1000, 0.0F);
      Vec3 to = t.screenToScene(mx, my, -100, 0.0F);
      Vec3 dir = to.subtract(from);
      double minX = pos.getX();
      double minY = pos.getY();
      double minZ = pos.getZ();
      double maxX = minX + 1.0;
      double maxY = minY + 1.0;
      double maxZ = minZ + 1.0;
      double tMin = Double.NEGATIVE_INFINITY;
      Direction result = Direction.UP;
      if (Math.abs(dir.x) > 1.0E-10) {
         double t1 = (minX - from.x) / dir.x;
         double t2 = (maxX - from.x) / dir.x;
         double tEnter = Math.min(t1, t2);
         Direction face = t1 < t2 ? Direction.WEST : Direction.EAST;
         if (tEnter > tMin) {
            tMin = tEnter;
            result = face;
         }
      }

      if (Math.abs(dir.y) > 1.0E-10) {
         double t1 = (minY - from.y) / dir.y;
         double t2 = (maxY - from.y) / dir.y;
         double tEnter = Math.min(t1, t2);
         Direction face = t1 < t2 ? Direction.DOWN : Direction.UP;
         if (tEnter > tMin) {
            tMin = tEnter;
            result = face;
         }
      }

      if (Math.abs(dir.z) > 1.0E-10) {
         double t1 = (minZ - from.z) / dir.z;
         double t2 = (maxZ - from.z) / dir.z;
         double tEnter = Math.min(t1, t2);
         Direction face = t1 < t2 ? Direction.NORTH : Direction.SOUTH;
         if (tEnter > tMin) {
            result = face;
         }
      }

      return result;
   }
}
