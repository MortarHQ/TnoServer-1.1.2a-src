package it.hurts.octostudios.immersiveui.util;

import com.mojang.math.Axis;
import it.hurts.octostudios.immersiveui.ImmersiveUI;
import it.hurts.octostudios.immersiveui.client.MouseInfo;
import it.hurts.octostudios.immersiveui.client.RenderInfo;
import it.hurts.octostudios.immersiveui.client.VariableStorage;
import it.hurts.octostudios.immersiveui.client.particle.FlameUIParticle;
import it.hurts.octostudios.immersiveui.client.particle.RarityUIParticle;
import it.hurts.octostudios.octolib.client.particle.UIParticle;
import it.hurts.octostudios.octolib.client.particle.UIParticle.Layer;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector2f;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class CommonCode {
   public static void gooeyRenderCode(float partialTick) {
      VariableStorage.currentTime = System.currentTimeMillis();
      VariableStorage.elapsedTime = VariableStorage.currentTime - VariableStorage.lastExecutedTime;
      if (VariableStorage.elapsedTime >= 350L) {
         VariableStorage.lastExecutedTime = VariableStorage.currentTime;
      }
   }

   public static void renderFurnaceParticles(Screen screen, Slot fuelSlot, boolean burstCondition, boolean isLit, AtomicBoolean shouldBurst) {
      if (isLit) {
         Random random = new Random();
         if (burstCondition && !shouldBurst.get()) {
            shouldBurst.set(true);

            for (int ii = 0; ii < 8; ii++) {
               FlameUIParticle particle = new FlameUIParticle(
                  fuelSlot.x + 8 + random.nextInt(-6, 6), fuelSlot.y + 10 + random.nextInt(-6, 6), random.nextInt(16, 24)
               );
               particle.setScreen(screen);
               particle.instantiate();
            }
         }

         if (!burstCondition && shouldBurst.get()) {
            shouldBurst.set(false);
         }
      }
   }

   public static void renderFloating(
      Screen screen,
      GuiGraphics guiGraphics,
      MouseInfo mouseInfo,
      int i,
      int j,
      ItemStack itemStack,
      Random random,
      RenderInfo renderInfo,
      String string,
      CallbackInfo ci
   ) {
      float scale = ImmersiveUI.CONFIG.getFloatingItemScale();
      float deltaTime = Minecraft.getInstance().getTimer().getRealtimeDeltaTicks();
      float amplitude = ImmersiveUI.CONFIG.getFloatingItemRotationAmplitude();
      if (mouseInfo.oX != Integer.MIN_VALUE && mouseInfo.oY != Integer.MIN_VALUE) {
         renderInfo.targetAngle = Mth.clamp(
            -mouseInfo.deltaX / 8.0F * amplitude, (float) (-Math.PI / 2) / (2.0F / amplitude), (float) (Math.PI / 2) / (2.0F / amplitude)
         );
         renderInfo.currentAngleVelocity = renderInfo.currentAngleVelocity
            + (renderInfo.targetAngle - renderInfo.currentAngle) * renderInfo.easingSpeed * deltaTime;
      }

      renderInfo.currentAngle = Mth.clamp(
         renderInfo.currentAngle + renderInfo.currentAngleVelocity * deltaTime,
         (float) (-Math.PI / 2) / (2.0F / amplitude),
         (float) (Math.PI / 2) / (2.0F / amplitude)
      );
      renderInfo.currentAngleVelocity = renderInfo.currentAngleVelocity * (float)Math.pow(0.75, deltaTime);
      guiGraphics.pose().pushPose();
      guiGraphics.pose().translate(i + 8, j + 8, 300.0F);
      guiGraphics.pose().scale(scale, scale, 1.0F);
      if (ImmersiveUI.CONFIG.isEnableFloatingItemRotation()) {
         guiGraphics.pose().mulPose(Axis.ZP.rotation(Mth.abs(renderInfo.currentAngle) > 0.01F ? renderInfo.currentAngle : 0.0F));
      }

      guiGraphics.renderItem(itemStack, -8, -8);
      if (ImmersiveUI.CONFIG.isEnableRarityParticles()) {
         List<Integer> colors = itemStack.getHoverName()
            .getSiblings()
            .stream()
            .map(c -> c.getStyle().getColor() == null ? 0 : c.getStyle().getColor().getValue())
            .toList();
         int color = colors.isEmpty() ? 16777215 : colors.get(random.nextInt(colors.size()));
         color = colors.isEmpty()
            ? (itemStack.getDisplayName().getStyle().getColor() != null ? itemStack.getDisplayName().getStyle().getColor().getValue() : 16777215)
            : color;
         if (color != 16777215 && (Mth.abs(mouseInfo.deltaX) > 0.0F || Mth.abs(mouseInfo.deltaY) > 0.0F)) {
            Vector2f direction = new Vector2f(mouseInfo.deltaX, mouseInfo.deltaY);
            UIParticle particle = new RarityUIParticle(
               random.nextFloat(0.5F, 0.625F) * direction.length(),
               random.nextInt(12, 20),
               i + 8 + random.nextFloat(-4.0F, 4.0F),
               j + 8 + random.nextFloat(-4.0F, 4.0F),
               -direction.x,
               -direction.y,
               random.nextFloat(-10.0F, 10.0F),
               color,
               Layer.SCREEN,
               233.0F
            );
            particle.setScreen(screen);
            particle.instantiate();
         }
      }

      Font font = Minecraft.getInstance().font;
      guiGraphics.renderItemDecorations(font, itemStack, -8, -8, string);
      guiGraphics.pose().popPose();
      ci.cancel();
   }

   public static void shakeScreen(GuiGraphics guiGraphics, Screen screen, AtomicReference<Float> timer, float durationMultiplier) {
      boolean shouldShake = VariableStorage.shakeScreen.contains(screen);
      if (shouldShake) {
         VariableStorage.shakeScreen.remove(screen);
         timer.set(ImmersiveUI.CONFIG.getShakeTimer() * durationMultiplier);
      }

      if (ImmersiveUI.CONFIG.isEnableScreenShake()) {
         if (timer.get() > 0.0F) {
            Random rand = new Random();
            timer.set(Mth.clamp(timer.get() - Minecraft.getInstance().getTimer().getRealtimeDeltaTicks(), 0.0F, ImmersiveUI.CONFIG.getShakeTimer()));
            Vector2f shakeDirection = new Vector2f(rand.nextFloat(-1.0F, 1.0F), rand.nextFloat(-1.0F, 1.0F)).normalize(ImmersiveUI.CONFIG.getShakeAmplitude());
            guiGraphics.pose()
               .translate(
                  shakeDirection.x * (timer.get() / ImmersiveUI.CONFIG.getShakeTimer()),
                  shakeDirection.y * (timer.get() / ImmersiveUI.CONFIG.getShakeTimer()),
                  0.0F
               );
         }
      }
   }

   public static void floatingRenderSize(GuiGraphics guiGraphics, Slot slot, Slot hoveredSlot, Map<Slot, Float> expandingProgress) {
      LocalPlayer player = Minecraft.getInstance().player;
      if (player != null && slot != null) {
         ItemStack carried = player.containerMenu.getCarried();
         if (!carried.isEmpty() && ItemStack.isSameItemSameComponents(slot.getItem(), carried) && ImmersiveUI.CONFIG.isEnableMatchingItemHovering()) {
            guiGraphics.pose()
               .translate(
                  Mth.sin(Minecraft.getInstance().player.tickCount * 0.215F + Objects.hash(slot.x, slot.y))
                     * ImmersiveUI.CONFIG.getMatchingItemHoverAmplitude(),
                  Mth.cos(Minecraft.getInstance().player.tickCount * 0.13F + Objects.hash(slot.x, slot.y)) * ImmersiveUI.CONFIG.getMatchingItemHoverAmplitude(),
                  0.0F
               );
         }

         boolean hovering = hoveredSlot == slot && (carried.isEmpty() || ItemStack.isSameItemSameComponents(slot.getItem(), carried));
         float deltaTime = Minecraft.getInstance().getTimer().getRealtimeDeltaTicks() / 4.0F;
         expandingProgress.put(slot, Mth.clamp(expandingProgress.getOrDefault(slot, 0.0F) + deltaTime * (hovering ? 1 : -1), 0.0F, 1.0F));
         float progress = Easing.lerp(
            1.0F, ImmersiveUI.CONFIG.getHoveredItemScale(), Easing.animate(hovering ? Easing.Type.EASE_OUT : Easing.Type.EASE_IN, expandingProgress.get(slot))
         );
         guiGraphics.pose().translate(slot.x + 8, slot.y + 8, 0.0F);
         guiGraphics.pose().scale(progress, progress, 1.0F);
         guiGraphics.pose().translate(-slot.x - 8, -slot.y - 8, 0.0F);
      }
   }
}
