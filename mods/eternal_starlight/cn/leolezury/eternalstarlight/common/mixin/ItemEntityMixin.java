package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.item.misc.GalacticQuiverItem;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import java.util.UUID;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {
   @Shadow
   @Nullable
   private UUID target;
   @Shadow
   private int pickupDelay;
   @Shadow
   private int age;

   @Shadow
   public abstract ItemStack getItem();

   @Inject(method = "tick()V", at = @At("HEAD"))
   public void tick(CallbackInfo ci) {
      ItemEntity itemEntity = (ItemEntity)this;
      if (this.age < 3000
         && this.getItem().get(DataComponents.FOOD) != null
         && (
            itemEntity.level().getBlockState(itemEntity.blockPosition()).is(ESTags.Blocks.TOOTH_OF_HUNGER_BLOCKS)
               || itemEntity.level().getBlockState(itemEntity.blockPosition().below()).is(ESTags.Blocks.TOOTH_OF_HUNGER_BLOCKS)
         )) {
         this.age = 3000;
      }
   }

   @Inject(method = "hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z", at = @At("HEAD"), cancellable = true)
   public void hurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
      ItemEntity itemEntity = (ItemEntity)this;
      if (!source.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && ESDataAttachments.IMPORTANT_ITEM.getData(itemEntity)) {
         cir.setReturnValue(false);
      }
   }

   @Inject(method = "playerTouch(Lnet/minecraft/world/entity/player/Player;)V", at = @At("HEAD"), cancellable = true)
   public void playerTouch(Player player, CallbackInfo ci) {
      ItemEntity itemEntity = (ItemEntity)this;
      if (!itemEntity.level().isClientSide) {
         if (this.pickupDelay == 0 && (this.target == null || this.target.equals(player.getUUID())) && this.getItem().is(ESItems.MANA_CRYSTAL_SHARD.get())) {
            ci.cancel();
            player.take(itemEntity, this.getItem().getCount());
            itemEntity.discard();
            Inventory inventory = player.getInventory();

            for (int i = 0; i < inventory.getContainerSize(); i++) {
               ItemStack stack = inventory.getItem(i);
               if (stack.is(ESTags.Items.MANA_CRYSTALS) && stack.isDamaged()) {
                  stack.setDamageValue(Math.max(stack.getDamageValue() - this.getItem().getCount(), 0));
                  return;
               }
            }
         }
      }
   }

   @WrapOperation(
      method = "playerTouch(Lnet/minecraft/world/entity/player/Player;)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;add(Lnet/minecraft/world/item/ItemStack;)Z")
   )
   public boolean addToInventory(Inventory instance, ItemStack itemStack, Operation<Boolean> original) {
      if (!itemStack.is(ItemTags.ARROWS)) {
         return (Boolean)original.call(new Object[]{instance, itemStack});
      }

      boolean arrowSuccess = GalacticQuiverItem.addArrowToInventory(instance, itemStack);
      return arrowSuccess || (Boolean)original.call(new Object[]{instance, itemStack});
   }
}
