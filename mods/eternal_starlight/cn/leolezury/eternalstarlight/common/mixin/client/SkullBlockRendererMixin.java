package cn.leolezury.eternalstarlight.common.mixin.client;

import cn.leolezury.eternalstarlight.common.block.ESSkullType;
import java.util.Map;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.block.SkullBlock.Type;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SkullBlockRenderer.class)
public abstract class SkullBlockRendererMixin {
   @Shadow
   @Final
   public static Map<Type, ResourceLocation> SKIN_BY_TYPE;

   @Inject(
      method = "getRenderType(Lnet/minecraft/world/level/block/SkullBlock$Type;Lnet/minecraft/world/item/component/ResolvableProfile;)Lnet/minecraft/client/renderer/RenderType;",
      at = @At("RETURN"),
      cancellable = true
   )
   private static void getRenderType(Type type, ResolvableProfile resolvableProfile, CallbackInfoReturnable<RenderType> cir) {
      if (type == ESSkullType.TANGLED) {
         cir.setReturnValue(RenderType.entityTranslucent(SKIN_BY_TYPE.get(type)));
      }
   }
}
