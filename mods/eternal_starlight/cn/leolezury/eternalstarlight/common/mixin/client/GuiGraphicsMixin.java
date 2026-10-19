package cn.leolezury.eternalstarlight.common.mixin.client;

import cn.leolezury.eternalstarlight.common.client.handler.ESClientHandler;
import cn.leolezury.eternalstarlight.common.item.component.Accessory;
import cn.leolezury.eternalstarlight.common.registry.ESDataComponents;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiGraphics.class)
public abstract class GuiGraphicsMixin {
   @Shadow
   @Final
   private PoseStack pose;

   @Shadow
   public abstract void blit(ResourceLocation var1, int var2, int var3, float var4, float var5, int var6, int var7, int var8, int var9);

   @Inject(
      method = "renderItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V",
      at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V")
   )
   private void renderItemDecorations(Font font, ItemStack itemStack, int x, int y, String string, CallbackInfo ci) {
      if (itemStack.is(ESTags.Items.WIP)) {
         this.pose.pushPose();
         this.pose.translate(0.0F, 0.0F, 199.0F);
         this.blit(ESClientHandler.WIP_LOCATION, x, y, 0.0F, 0.0F, 16, 16, 16, 16);
         this.pose.popPose();
      }

      List<ItemStack> accessories = (List<ItemStack>)itemStack.get(ESDataComponents.ACCESSORIES.get());
      if (accessories != null && !accessories.isEmpty()) {
         for (ItemStack stack : accessories) {
            Accessory accessory = (Accessory)stack.get(ESDataComponents.ACCESSORY.get());
            if (accessory != null && accessory.overlay().isPresent()) {
               this.pose.pushPose();
               this.pose.translate(0.0F, 0.0F, 198.0F);
               this.blit(accessory.overlay().get().withSuffix(".png"), x, y, 0.0F, 0.0F, 16, 16, 16, 16);
               this.pose.popPose();
               break;
            }
         }
      }
   }
}
