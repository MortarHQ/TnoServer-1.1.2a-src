package com.natamus.starterkit_common_fabric.inventory;

import com.google.common.collect.Ordering;
import com.natamus.starterkit_common_fabric.data.VariablesClient;
import java.util.List;
import java.util.Optional;
import net.minecraft.class_1058;
import net.minecraft.class_1291;
import net.minecraft.class_1292;
import net.minecraft.class_1293;
import net.minecraft.class_1661;
import net.minecraft.class_1703;
import net.minecraft.class_2561;
import net.minecraft.class_2960;
import net.minecraft.class_332;
import net.minecraft.class_4074;
import net.minecraft.class_5244;
import net.minecraft.class_5250;
import net.minecraft.class_6880;
import org.jetbrains.annotations.NotNull;

public abstract class StarterKitEffectRenderingInventoryScreen<T extends class_1703> extends StarterKitAbstractContainerScreen<T> {
   private static final class_2960 EFFECT_BACKGROUND_LARGE_SPRITE = class_2960.method_60654("container/inventory/effect_background_large");
   private static final class_2960 EFFECT_BACKGROUND_SMALL_SPRITE = class_2960.method_60654("container/inventory/effect_background_small");

   public StarterKitEffectRenderingInventoryScreen(T menu, class_1661 playerInventory, class_2561 title) {
      super(menu, playerInventory, title);
   }

   @Override
   public void method_25394(@NotNull class_332 guiGraphics, int mouseX, int mouseY, float partialTick) {
      super.method_25394(guiGraphics, mouseX, mouseY, partialTick);
      this.renderEffects(guiGraphics, mouseX, mouseY);
   }

   public boolean canSeeEffects() {
      int i = this.leftPos + this.imageWidth + 2;
      int j = this.field_22789 - i;
      return j >= 32;
   }

   private void renderEffects(class_332 guiGraphics, int mouseX, int mouseY) {
      int i = this.leftPos + this.imageWidth + 2;
      int j = this.field_22789 - i;
      if (!VariablesClient.cachedStarterKitEffects.isEmpty() && j >= 32) {
         boolean bl = j >= 120;
         int k = 33;
         if (VariablesClient.cachedStarterKitEffects.size() > 5) {
            k = 132 / (VariablesClient.cachedStarterKitEffects.size() - 1);
         }

         Iterable<class_1293> iterable = Ordering.natural().sortedCopy(VariablesClient.cachedStarterKitEffects);
         this.renderBackgrounds(guiGraphics, i, k, iterable, bl);
         this.renderIcons(guiGraphics, i, k, iterable, bl);
         if (bl) {
            this.renderLabels(guiGraphics, i, k, iterable);
         } else if (mouseX >= i && mouseX <= i + 33) {
            int l = this.topPos;
            class_1293 mobEffectInstance = null;

            for (class_1293 mobEffectInstance2 : iterable) {
               if (mouseY >= l && mouseY <= l + k) {
                  mobEffectInstance = mobEffectInstance2;
               }

               l += k;
            }

            if (mobEffectInstance != null) {
               List<class_2561> list = List.of(
                  this.getEffectName(mobEffectInstance),
                  class_1292.method_5577(mobEffectInstance, 1.0F, this.field_22787.field_1687.method_54719().method_54748())
               );
               guiGraphics.method_51437(this.field_22793, list, Optional.empty(), mouseX, mouseY);
            }
         }
      }
   }

   private void renderBackgrounds(class_332 guiGraphics, int renderX, int yOffset, Iterable<class_1293> effects, boolean isSmall) {
      int i = this.topPos;

      for (class_1293 mobEffectInstance : effects) {
         if (isSmall) {
            guiGraphics.method_52706(EFFECT_BACKGROUND_LARGE_SPRITE, renderX, i, 120, 32);
         } else {
            guiGraphics.method_52706(EFFECT_BACKGROUND_SMALL_SPRITE, renderX, i, 32, 32);
         }

         i += yOffset;
      }
   }

   private void renderIcons(class_332 guiGraphics, int renderX, int yOffset, Iterable<class_1293> effects, boolean isSmall) {
      class_4074 mobEffectTextureManager = this.field_22787.method_18505();
      int i = this.topPos;

      for (class_1293 mobEffectInstance : effects) {
         class_6880<class_1291> mobEffectHolder = mobEffectInstance.method_5579();
         class_1058 textureAtlasSprite = mobEffectTextureManager.method_18663(mobEffectHolder);
         guiGraphics.method_25298(renderX + (isSmall ? 6 : 7), i + 7, 0, 18, 18, textureAtlasSprite);
         i += yOffset;
      }
   }

   private void renderLabels(class_332 guiGraphics, int renderX, int yOffset, Iterable<class_1293> effects) {
      int i = this.topPos;

      for (class_1293 mobEffectInstance : effects) {
         class_2561 component = this.getEffectName(mobEffectInstance);
         guiGraphics.method_27535(this.field_22793, component, renderX + 10 + 18, i + 6, 16777215);
         class_2561 component2 = class_1292.method_5577(mobEffectInstance, 1.0F, this.field_22787.field_1687.method_54719().method_54748());
         guiGraphics.method_27535(this.field_22793, component2, renderX + 10 + 18, i + 6 + 10, 8355711);
         i += yOffset;
      }
   }

   private class_2561 getEffectName(class_1293 effect) {
      class_5250 mutableComponent = ((class_1291)effect.method_5579().comp_349()).method_5560().method_27661();
      if (effect.method_5578() >= 1 && effect.method_5578() <= 9) {
         class_5250 var10000 = mutableComponent.method_10852(class_5244.field_41874);
         int var10001 = effect.method_5578();
         var10000.method_10852(class_2561.method_43471("enchantment.level." + (var10001 + 1)));
      }

      return mutableComponent;
   }
}
