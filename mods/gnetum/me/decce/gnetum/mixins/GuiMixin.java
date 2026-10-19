package me.decce.gnetum.mixins;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import me.decce.gnetum.ElementType;
import me.decce.gnetum.FramebufferManager;
import me.decce.gnetum.Gnetum;
import me.decce.gnetum.GuiHelper;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.neoforge.client.event.RenderGuiEvent.Post;
import net.neoforged.neoforge.client.event.RenderGuiEvent.Pre;
import net.neoforged.neoforge.client.gui.GuiLayerManager;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {
   @Shadow
   public int leftHeight;
   @Shadow
   public int rightHeight;
   @Unique
   private int gnetum$lastLeftHeight = 39;
   @Unique
   private int gnetum$lastRightHeight = 39;
   @Unique
   private int gnetum$currentLeftHeight;
   @Unique
   private int gnetum$currentRightHeight;
   @Unique
   private final Matrix4f gnetum$defaultGuiPose = new Matrix4f();

   @WrapWithCondition(
      method = "render",
      at = @At(
         value = "INVOKE",
         target = "Lnet/neoforged/neoforge/client/gui/GuiLayerManager;render(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/DeltaTracker;)V"
      )
   )
   public boolean gnetum$render(GuiLayerManager instance, GuiGraphics guiGraphics, DeltaTracker partialTick) {
      if (!Gnetum.rendering) {
         return true;
      }

      Matrix4f pose = guiGraphics.pose().last().pose();
      if (!pose.equals(this.gnetum$defaultGuiPose, 0.01F)) {
         FramebufferManager.getInstance().markForCatchUp();
      }

      if (Gnetum.passManager.current == 1) {
         this.gnetum$currentLeftHeight = 39;
         this.gnetum$currentRightHeight = 39;
      }

      if (Gnetum.passManager.current > 0) {
         Gnetum.renderingCanceled = ((Pre)GuiHelper.postEvent(
               new Pre(guiGraphics, partialTick), guiGraphics.pose(), modid -> Gnetum.passManager.shouldRender(modid, ElementType.PRE)
            ))
            .isCanceled();
         if (Gnetum.passManager.current != 1) {
            this.leftHeight = this.gnetum$currentLeftHeight;
            this.rightHeight = this.gnetum$currentRightHeight;
         }

         GuiHelper.renderLayers(GuiHelper.getGuiLayerManagerAccessor().getLayers(), guiGraphics, partialTick, rl -> Gnetum.passManager.shouldRender(rl));
         GuiHelper.postEvent(new Post(guiGraphics, partialTick), guiGraphics.pose(), modid -> Gnetum.passManager.shouldRender(modid, ElementType.POST));
         this.gnetum$currentLeftHeight = this.leftHeight;
         this.gnetum$currentRightHeight = this.rightHeight;
      }

      if (Gnetum.passManager.current != Gnetum.config.numberOfPasses) {
         this.leftHeight = this.gnetum$lastLeftHeight;
         this.rightHeight = this.gnetum$lastRightHeight;
      } else {
         this.gnetum$lastLeftHeight = this.leftHeight;
         this.gnetum$lastRightHeight = this.rightHeight;
      }

      return false;
   }

   @Inject(method = "render", at = @At("TAIL"), cancellable = true, order = 100)
   private void gnetum$postRender(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
      if (Gnetum.rendering && Gnetum.passManager.current != Gnetum.config.numberOfPasses) {
         ci.cancel();
      }
   }
}
