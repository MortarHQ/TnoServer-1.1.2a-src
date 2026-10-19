package com.tensura_tno.mixin.client;

import java.lang.reflect.Field;
import java.util.Optional;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "bigchadguys.sellingbin.data.adapter.util.ItemStackAdapter", remap = false)
public class SellingBinItemStackAdapterFixMixin {
   @Inject(method = "readNbt", at = @At("HEAD"), cancellable = true, remap = false, require = 0)
   private void tensuraTno$safeReadNbt(Tag nbt, CallbackInfoReturnable<Optional<ItemStack>> cir) {
      if (!(nbt instanceof CompoundTag)) {
         cir.setReturnValue(Optional.empty());
      } else {
         RegistryAccess access = getSafeRegistryAccess();
         if (access == null) {
            cir.setReturnValue(Optional.empty());
         } else {
            try {
               cir.setReturnValue(ItemStack.parse(access, nbt));
            } catch (Exception e) {
               cir.setReturnValue(Optional.empty());
            }
         }
      }
   }

   @Inject(method = "writeNbt", at = @At("HEAD"), cancellable = true, remap = false, require = 0)
   private void tensuraTno$safeWriteNbt(ItemStack value, CallbackInfoReturnable<Optional<Tag>> cir) {
      if (value == null) {
         cir.setReturnValue(Optional.empty());
      } else {
         RegistryAccess access = getSafeRegistryAccess();
         if (access == null) {
            cir.setReturnValue(Optional.empty());
         } else {
            try {
               cir.setReturnValue(Optional.of(value.save(access)));
            } catch (Exception e) {
               cir.setReturnValue(Optional.empty());
            }
         }
      }
   }

   private static RegistryAccess getSafeRegistryAccess() {
      try {
         Class<?> modClass = Class.forName("bigchadguys.sellingbin.SellingBinMod");
         Field serverField = modClass.getDeclaredField("SERVER");
         serverField.setAccessible(true);
         if (serverField.get(null) instanceof MinecraftServer ms) {
            return ms.registryAccess();
         }
      } catch (Exception var6) {
      }

      try {
         Class<?> minecraftClass = Class.forName("net.minecraft.client.Minecraft");
         Object mc = minecraftClass.getMethod("getInstance").invoke(null);
         if (mc != null) {
            Object level = minecraftClass.getField("level").get(mc);
            if (level != null && level.getClass().getMethod("registryAccess").invoke(level) instanceof RegistryAccess registryAccess) {
               return registryAccess;
            }
         }
      } catch (Exception var5) {
      }

      return null;
   }
}
