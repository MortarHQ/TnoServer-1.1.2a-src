package bigchadguys.sellingbin.mixin;

import bigchadguys.sellingbin.init.ModRenderers;
import java.util.Map;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntityRenderers.class)
public class MixinBlockEntityRendererFactories {
   @Shadow
   @Final
   private static Map<BlockEntityType<?>, BlockEntityRendererProvider<?>> PROVIDERS;

   @Inject(method = "<clinit>()V", at = @At("RETURN"))
   private static void clinit(CallbackInfo ci) {
      ModRenderers.BlockEntities.register(PROVIDERS);
   }
}
