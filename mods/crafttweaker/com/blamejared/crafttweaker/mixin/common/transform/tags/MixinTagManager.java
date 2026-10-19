package com.blamejared.crafttweaker.mixin.common.transform.tags;

import com.blamejared.crafttweaker.CraftTweakerCommon;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.tag.CraftTweakerTagRegistry;
import com.blamejared.crafttweaker.api.zencode.scriptrun.ScriptRunConfiguration;
import com.blamejared.crafttweaker.platform.helper.IAccessibleServerElementsProvider;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.core.RegistryAccess;
import net.minecraft.tags.TagManager;
import net.minecraft.tags.TagManager.LoadResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(TagManager.class)
public class MixinTagManager {
   @Shadow
   private List<LoadResult<?>> results;
   @Shadow
   @Final
   private RegistryAccess registryAccess;

   @ModifyArg(
      method = "reload",
      at = @At(
         value = "INVOKE",
         target = "Ljava/util/concurrent/CompletableFuture;thenAcceptAsync(Ljava/util/function/Consumer;Ljava/util/concurrent/Executor;)Ljava/util/concurrent/CompletableFuture;"
      )
   )
   public <T> Consumer<? super T> crafttweaker$appendConsumer(Consumer<? super T> action) {
      return action.andThen(
         o -> {
            CraftTweakerTagRegistry.INSTANCE.bind(this.results, new CraftTweakerTagRegistry.BindContext(false));
            IAccessibleServerElementsProvider asep = CraftTweakerAPI.getAccessibleElementsProvider().server();
            asep.registryAccess(this.registryAccess);
            ScriptRunConfiguration configuration = new ScriptRunConfiguration(
               "tags", CraftTweakerConstants.RELOAD_LISTENER_SOURCE_ID, ScriptRunConfiguration.RunKind.EXECUTE
            );

            try {
               CraftTweakerAPI.getScriptRunManager().createScriptRun(configuration).execute();
            } catch (Throwable e) {
               CraftTweakerCommon.logger().error("Unable to run tag scripts due to an error", e);
            }

            asep.registryAccess(null);
         }
      );
   }
}
