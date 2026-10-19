package cn.leolezury.eternalstarlight.neoforge.mixin.client;

import cn.leolezury.eternalstarlight.common.block.entity.AbstractDuskLightBlockEntity;
import cn.leolezury.eternalstarlight.common.block.entity.AlloyFurnaceBlockEntity;
import cn.leolezury.eternalstarlight.common.block.entity.EclipseCoreBlockEntity;
import cn.leolezury.eternalstarlight.common.block.entity.EnergyTransmitterBlockEntity;
import cn.leolezury.eternalstarlight.common.block.entity.SolarEggBlockEntity;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.client.extensions.IBlockEntityRendererExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(IBlockEntityRendererExtension.class)
public interface IBlockEntityRendererExtensionMixin {
   @ModifyReturnValue(
      method = "getRenderBoundingBox(Lnet/minecraft/world/level/block/entity/BlockEntity;)Lnet/minecraft/world/phys/AABB;",
      at = @At("RETURN"),
      remap = false
   )
   default AABB getRenderBoundingBox(AABB original, @Local(argsOnly = true) BlockEntity blockEntity) {
      if (blockEntity instanceof AlloyFurnaceBlockEntity || blockEntity instanceof SolarEggBlockEntity) {
         return original.inflate(3.0);
      } else {
         return !(blockEntity instanceof EnergyTransmitterBlockEntity)
               && !(blockEntity instanceof AbstractDuskLightBlockEntity)
               && !(blockEntity instanceof EclipseCoreBlockEntity)
            ? original
            : original.inflate(64.0);
      }
   }
}
