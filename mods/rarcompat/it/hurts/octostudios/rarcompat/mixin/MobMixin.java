package it.hurts.octostudios.rarcompat.mixin;

import artifacts.registry.ModItems;
import it.hurts.octostudios.rarcompat.items.hat.CowboyHatItem;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mob.class)
abstract class MobMixin {
   @Inject(method = "getControllingPassenger", at = @At("HEAD"), cancellable = true)
   public void getControllingPassenger(CallbackInfoReturnable<LivingEntity> cir) {
      Mob mob = (Mob)this;
      if (!mob.isNoAi() && mob.getFirstPassenger() instanceof Player player) {
         ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ModItems.COWBOY_HAT.value());
         if (stack.getItem() instanceof CowboyHatItem relic && relic.getToggled(stack)) {
            cir.setReturnValue(player);
         }
      }
   }
}
