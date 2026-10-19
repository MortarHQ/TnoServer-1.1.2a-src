package net.darkhax.bookshelf.common.mixin.patch.packs;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Map;
import net.darkhax.bookshelf.common.api.data.conditions.LoadConditions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SimpleJsonResourceReloadListener.class)
public class MixinSimpleJsonResourceReloadListener {
   @Inject(
      method = "prepare(Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)Ljava/util/Map;",
      at = @At("RETURN")
   )
   private void prepare(ResourceManager manager, ProfilerFiller profiler, CallbackInfoReturnable<Map<ResourceLocation, JsonElement>> cbi) {
      ((Map)cbi.getReturnValue()).entrySet().removeIf(entry -> entry.getValue() instanceof JsonObject obj && !LoadConditions.canLoad(obj));
   }
}
