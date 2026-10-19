package com.foxablazeultimate.client;

import com.foxablazeultimate.client.render.FoxAblazeRenderTypes;
import com.foxablazeultimate.registry.FoxAblazeUltimateDataComponents;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;

public final class VelgryndGlintRenderState {
   private VelgryndGlintRenderState() {
   }

   public static boolean isCharged(ItemStack stack) {
      return !stack.isEmpty()
         && (
            stack.has((DataComponentType)FoxAblazeUltimateDataComponents.VELGRYND_CHARGE.get())
               || stack.has((DataComponentType)FoxAblazeUltimateDataComponents.AZATHOTH_CHARGE.get())
         );
   }

   public static MultiBufferSource wrap(ItemStack stack, MultiBufferSource original) {
      return !isCharged(stack)
         ? original
         : type -> {
            RenderType replacement = stack.has((DataComponentType)FoxAblazeUltimateDataComponents.AZATHOTH_CHARGE.get())
               ? azathothReplacement(type)
               : replacement(type);
            return original.getBuffer(replacement == null ? type : replacement);
         };
   }

   public static RenderType replacement(RenderType type) {
      if (type == RenderType.glint()) {
         return FoxAblazeRenderTypes.VELGRYND_GLINT;
      } else if (type == RenderType.glintTranslucent()) {
         return FoxAblazeRenderTypes.VELGRYND_GLINT_TRANSLUCENT;
      } else if (type == RenderType.entityGlint()) {
         return FoxAblazeRenderTypes.VELGRYND_ENTITY_GLINT;
      } else {
         return type == RenderType.entityGlintDirect() ? FoxAblazeRenderTypes.VELGRYND_ENTITY_GLINT_DIRECT : null;
      }
   }

   public static RenderType azathothReplacement(RenderType type) {
      if (type == RenderType.glint()) {
         return FoxAblazeRenderTypes.AZATHOTH_GLINT;
      } else if (type == RenderType.glintTranslucent()) {
         return FoxAblazeRenderTypes.AZATHOTH_GLINT_TRANSLUCENT;
      } else if (type == RenderType.entityGlint()) {
         return FoxAblazeRenderTypes.AZATHOTH_ENTITY_GLINT;
      } else {
         return type == RenderType.entityGlintDirect() ? FoxAblazeRenderTypes.AZATHOTH_ENTITY_GLINT_DIRECT : null;
      }
   }
}
