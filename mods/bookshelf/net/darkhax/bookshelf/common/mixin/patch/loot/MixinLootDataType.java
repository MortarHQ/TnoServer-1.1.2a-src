package net.darkhax.bookshelf.common.mixin.patch.loot;

import com.google.gson.JsonObject;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import java.util.Optional;
import net.darkhax.bookshelf.common.api.data.conditions.LoadConditions;
import net.darkhax.bookshelf.common.impl.data.loot.modifiers.LootModificationHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootDataType;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LootDataType.class)
public class MixinLootDataType {
   @Inject(
      method = "deserialize(Lnet/minecraft/resources/ResourceLocation;Lcom/mojang/serialization/DynamicOps;Ljava/lang/Object;)Ljava/util/Optional;",
      at = @At(value = "INVOKE", target = "Lcom/mojang/serialization/DataResult;error()Ljava/util/Optional;"),
      locals = LocalCapture.CAPTURE_FAILHARD,
      cancellable = true
   )
   private void onDeserialize(ResourceLocation id, DynamicOps<?> ops, Object value, CallbackInfoReturnable<Optional<?>> cir, DataResult<?> result) {
      if (value instanceof JsonObject obj && !LoadConditions.canLoad(obj)) {
         cir.setReturnValue(Optional.empty());
      } else {
         if (this == LootDataType.TABLE && value instanceof JsonObject && result.error().isEmpty()) {
            Object rst = result.result().orElse(null);
            LootTable table = bookshelf$getLootTable(rst);
            if (table != null) {
               LootModificationHandler.HANDLER.get().processLootTable(id, table);
            }
         }
      }
   }

   @Unique
   @Nullable
   private static LootTable bookshelf$getLootTable(Object rst) {
      LootTable table = null;
      if (rst instanceof LootTable lt) {
         table = lt;
      } else if (rst instanceof Optional<?> optionalObj && optionalObj.orElse(null) instanceof LootTable lt) {
         table = lt;
      }

      return table;
   }
}
