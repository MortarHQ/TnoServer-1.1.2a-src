package com.yungnickyoung.minecraft.yungscavebiomes.mixin.frosted_caves;

import com.yungnickyoung.minecraft.yungscavebiomes.entity.IcicleProjectileEntity;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import java.util.function.Predicate;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CrossbowItem.class)
public abstract class CrossbowItemMixin {
   @Inject(method = "createProjectile", at = @At("HEAD"), cancellable = true)
   private void yungscavebiomes_createIcicleProjectile(
      Level level, LivingEntity livingEntity, ItemStack weaponItemStack, ItemStack ammoItemStack, boolean $$4, CallbackInfoReturnable<Projectile> cir
   ) {
      if (ammoItemStack.is(((Block)BlockModule.ICICLE.get()).asItem())) {
         IcicleProjectileEntity icicle = new IcicleProjectileEntity(level, livingEntity, ammoItemStack, weaponItemStack);
         cir.setReturnValue(icicle);
      }
   }

   @Inject(method = "getSupportedHeldProjectiles", at = @At("RETURN"), cancellable = true)
   private void yungscavebiomes_supportIciclesInCrossbows(CallbackInfoReturnable<Predicate<ItemStack>> cir) {
      cir.setReturnValue(((Predicate)cir.getReturnValue()).or(itemStack -> itemStack.is(((Block)BlockModule.ICICLE.get()).asItem())));
   }

   @Inject(method = "getAllSupportedProjectiles", at = @At("RETURN"), cancellable = true)
   private void yungscavebiomes_supportIciclesInCrossbows2(CallbackInfoReturnable<Predicate<ItemStack>> cir) {
      cir.setReturnValue(((Predicate)cir.getReturnValue()).or(itemStack -> itemStack.is(((Block)BlockModule.ICICLE.get()).asItem())));
   }
}
