package net.jadenxgamer.netherexp.mixin.item;

import java.util.function.Predicate;
import net.jadenxgamer.netherexp.core.entity.BlackIcicle;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CrossbowItem.class)
public abstract class CrossbowItemMixin {
   @Inject(method = "getAllSupportedProjectiles", at = @At("RETURN"), cancellable = true)
   private void netherexp$appendGetAllSupportedProjectiles(CallbackInfoReturnable<Predicate<ItemStack>> cir) {
      Predicate<ItemStack> basePredicate = (Predicate<ItemStack>)cir.getReturnValue();
      cir.setReturnValue(basePredicate.or(stack -> stack.is(JNEBlocks.BLACK_ICICLE.get().asItem())));
   }

   @Inject(method = "getSupportedHeldProjectiles", at = @At("RETURN"), cancellable = true)
   private void netherexp$appendGetSupportedHeldProjectiles(CallbackInfoReturnable<Predicate<ItemStack>> cir) {
      Predicate<ItemStack> basePredicate = (Predicate<ItemStack>)cir.getReturnValue();
      cir.setReturnValue(basePredicate.or(stack -> stack.is(JNEBlocks.BLACK_ICICLE.get().asItem())));
   }

   @Inject(method = "createProjectile", at = @At("HEAD"), cancellable = true)
   private void netherexp$createCustomProjectile(
      Level level, LivingEntity shooter, ItemStack weapon, ItemStack ammo, boolean isCrit, CallbackInfoReturnable<Projectile> cir
   ) {
      if (ammo.is(JNEBlocks.BLACK_ICICLE.get().asItem())) {
         BlackIcicle icicle = new BlackIcicle(level, shooter, ammo, weapon);
         cir.setReturnValue(icicle);
      }
   }
}
