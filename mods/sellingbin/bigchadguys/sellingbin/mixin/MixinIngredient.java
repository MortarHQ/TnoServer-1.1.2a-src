package bigchadguys.sellingbin.mixin;

import bigchadguys.sellingbin.data.nbt.PartialCompoundNbt;
import bigchadguys.sellingbin.util.ItemUtils;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Ingredient.ItemValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Ingredient.class)
public class MixinIngredient {
   @Unique
   private final ThreadLocal<ItemStack> self = new ThreadLocal<>();

   @Inject(method = "test(Lnet/minecraft/world/item/ItemStack;)Z", at = @At("HEAD"))
   public void testHead(ItemStack itemStack, CallbackInfoReturnable<Boolean> ci) {
      this.self.set(itemStack);
   }

   @Inject(method = "test(Lnet/minecraft/world/item/ItemStack;)Z", at = @At("RETURN"))
   public void testReturn(ItemStack itemStack, CallbackInfoReturnable<Boolean> ci) {
      this.self.remove();
   }

   @Redirect(
      method = "test(Lnet/minecraft/world/item/ItemStack;)Z",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z")
   )
   public boolean test(ItemStack other, Item item) {
      if (!other.is(this.self.get().getItem())) {
         return false;
      } else {
         CompoundTag otherNbt = ItemUtils.getNbt(other);
         CompoundTag selfNbt = ItemUtils.getNbt(this.self.get());
         if (otherNbt != null) {
            PartialCompoundNbt a = PartialCompoundNbt.of(otherNbt);
            PartialCompoundNbt b = PartialCompoundNbt.of(selfNbt);
            return a.isSubsetOf(b);
         } else {
            return true;
         }
      }
   }

   @Inject(method = "<clinit>()V", at = @At("RETURN"))
   private static void clinit(CallbackInfo ci) {
      ItemValue.CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               ItemStack.SIMPLE_ITEM_CODEC.fieldOf("item").forGetter(ItemValue::item), CompoundTag.CODEC.fieldOf("nbt").forGetter(object -> null)
            )
            .apply(instance, (stack, nbt) -> {
               ItemValue entry = new ItemValue(stack);
               if (nbt != null) {
                  ItemUtils.setNbt(stack, nbt);
               }

               return entry;
            })
      );
   }
}
