package com.tensura.naturesaura.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import de.ellpeck.naturesaura.events.ClientEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(value = ClientEvents.class, remap = false)
abstract class ClientEventsMixin {
   private static final String OVERLAY_METHOD = "onOverlayRender(Lnet/neoforged/neoforge/client/event/RenderGuiLayerEvent$Post;)V";
   private static final String FIRST_EYE_ITEM_RENDER = "Lde/ellpeck/naturesaura/Helper;renderItemInGui(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/world/item/ItemStack;IIF)V";
   private static final String SHOWING_EFFECTS_FIELD = "Lde/ellpeck/naturesaura/events/ClientEvents;SHOWING_EFFECTS:Ljava/util/Map;";
   private static final String ENABLE_DEPTH_TEST = "Lcom/mojang/blaze3d/systems/RenderSystem;enableDepthTest()V";
   private static final int HUD_BAR_THICKNESS = 6;
   private static final int HUD_BAR_GAP = 4;
   private static final int HUD_RIGHT_MARGIN = 8;
   private static final int EFFECT_ICON_WIDTH = 16;
   private static final int EFFECT_ICON_GAP = 4;

   @ModifyExpressionValue(
      method = "onOverlayRender(Lnet/neoforged/neoforge/client/event/RenderGuiLayerEvent$Post;)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z", ordinal = 0),
      slice = @Slice(
         from = @At(value = "FIELD", target = "Lde/ellpeck/naturesaura/events/ClientEvents;heldCache:Lnet/minecraft/world/item/ItemStack;", ordinal = 0),
         to = @At(
            value = "FIELD",
            target = "Lde/ellpeck/naturesaura/ModConfig;cacheBarLocation:Lnet/neoforged/neoforge/common/ModConfigSpec$ConfigValue;",
            ordinal = 0
         )
      ),
      require = 1,
      expect = 1,
      allow = 1
   )
   private boolean tensuraNaturesAura$hideOriginalCacheBar(boolean original) {
      return true;
   }

   @WrapOperation(
      method = "onOverlayRender(Lnet/neoforged/neoforge/client/event/RenderGuiLayerEvent$Post;)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V"),
      slice = @Slice(
         from = @At(
            value = "FIELD",
            target = "Lde/ellpeck/naturesaura/ModConfig;auraBarLocation:Lnet/neoforged/neoforge/common/ModConfigSpec$ConfigValue;",
            ordinal = 0
         ),
         to = @At(
            value = "INVOKE",
            target = "Lde/ellpeck/naturesaura/Helper;renderItemInGui(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/world/item/ItemStack;IIF)V",
            ordinal = 0
         )
      ),
      require = 6,
      expect = 6,
      allow = 6
   )
   private void tensuraNaturesAura$hideOriginalEyeBars(
      GuiGraphics graphics,
      ResourceLocation texture,
      int x,
      int y,
      float u,
      float v,
      int width,
      int height,
      int textureWidth,
      int textureHeight,
      Operation<Void> original
   ) {
   }

   @WrapOperation(
      method = "onOverlayRender(Lnet/neoforged/neoforge/client/event/RenderGuiLayerEvent$Post;)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;FFIZ)I"),
      slice = @Slice(
         from = @At(
            value = "FIELD",
            target = "Lde/ellpeck/naturesaura/ModConfig;auraBarLocation:Lnet/neoforged/neoforge/common/ModConfigSpec$ConfigValue;",
            ordinal = 0
         ),
         to = @At(
            value = "INVOKE",
            target = "Lde/ellpeck/naturesaura/Helper;renderItemInGui(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/world/item/ItemStack;IIF)V",
            ordinal = 0
         )
      ),
      require = 3,
      expect = 3,
      allow = 3
   )
   private int tensuraNaturesAura$hideOriginalEyeText(
      GuiGraphics graphics, Font font, String text, float x, float y, int color, boolean shadow, Operation<Integer> original
   ) {
      return 0;
   }

   @WrapOperation(
      method = "onOverlayRender(Lnet/neoforged/neoforge/client/event/RenderGuiLayerEvent$Post;)V",
      at = @At(
         value = "INVOKE",
         target = "Lde/ellpeck/naturesaura/Helper;renderItemInGui(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/world/item/ItemStack;IIF)V"
      ),
      slice = @Slice(
         from = @At(value = "FIELD", target = "Lde/ellpeck/naturesaura/events/ClientEvents;SHOWING_EFFECTS:Ljava/util/Map;"),
         to = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;enableDepthTest()V", ordinal = 0)
      ),
      require = 1,
      expect = 1,
      allow = 1
   )
   private void tensuraNaturesAura$moveAuraEffectItem(GuiGraphics graphics, ItemStack itemStack, int x, int y, float scale, Operation<Void> original) {
      original.call(new Object[]{graphics, itemStack, tensuraNaturesAura$getEffectIconX(), y, scale});
   }

   @WrapOperation(
      method = "onOverlayRender(Lnet/neoforged/neoforge/client/event/RenderGuiLayerEvent$Post;)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V"),
      slice = @Slice(
         from = @At(value = "FIELD", target = "Lde/ellpeck/naturesaura/events/ClientEvents;SHOWING_EFFECTS:Ljava/util/Map;"),
         to = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;enableDepthTest()V", ordinal = 0)
      ),
      require = 1,
      expect = 1,
      allow = 1
   )
   private void tensuraNaturesAura$moveAuraEffectCross(
      GuiGraphics graphics,
      ResourceLocation texture,
      int x,
      int y,
      float u,
      float v,
      int width,
      int height,
      int textureWidth,
      int textureHeight,
      Operation<Void> original
   ) {
      original.call(new Object[]{graphics, texture, tensuraNaturesAura$getEffectIconX(), y, u, v, width, height, textureWidth, textureHeight});
   }

   private static int tensuraNaturesAura$getEffectIconX() {
      int barCount = ClientEventsAccessor.tensuraNaturesAura$getHeldCache().isEmpty() ? 1 : 2;
      int barsWidth = barCount * 6 + (barCount - 1) * 4;
      int leftmostBarX = Minecraft.getInstance().getWindow().getGuiScaledWidth() - 8 - barsWidth;
      return leftmostBarX - 4 - 16;
   }
}
