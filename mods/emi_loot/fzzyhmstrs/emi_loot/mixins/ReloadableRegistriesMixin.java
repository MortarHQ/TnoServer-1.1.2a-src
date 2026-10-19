package fzzyhmstrs.emi_loot.mixins;

import com.google.gson.JsonElement;
import fzzyhmstrs.emi_loot.EMILoot;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.ReloadableServerRegistries;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.storage.loot.LootDataType;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ReloadableServerRegistries.class, priority = 10000)
public class ReloadableRegistriesMixin {
   @Inject(
      method = "lambda$scheduleElementParse$4(Lnet/minecraft/world/level/storage/loot/LootDataType;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/resources/RegistryOps;)Lnet/minecraft/core/WritableRegistry;",
      at = @At("RETURN")
   )
   private static <T> void onLootTablesLoaded(
      LootDataType<T> lootDataType, ResourceManager resourceManager, RegistryOps<JsonElement> registryOps, CallbackInfoReturnable<WritableRegistry<?>> cir
   ) {
      if (lootDataType == LootDataType.TABLE) {
         EMILoot.parseTables(resourceManager, (Registry<LootTable>)cir.getReturnValue(), registryOps);
      }
   }
}
