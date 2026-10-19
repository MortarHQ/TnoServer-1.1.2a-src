package com.mafuyu404.oneenoughitem.data;

import cc.sighs.oelib.data.api.DataValidator.ServerContextAware;
import cc.sighs.oelib.data.api.DataValidator.ValidationResult;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;

public abstract class BaseReplacementValidator<T> implements ServerContextAware<Replacements> {
   protected abstract RegistryLookup<T> getRegistryLookup(MinecraftServer var1);

   protected abstract boolean isResultExists(String var1, RegistryLookup<T> var2);

   protected abstract ValidationStreams.Accumulator fromDomainObject(String var1, ResourceLocation var2, RegistryLookup<T> var3);

   protected abstract ValidationStreams.Accumulator fromDomainTag(String var1, ResourceLocation var2, RegistryLookup<T> var3);

   public ValidationResult validateWithContext(Replacements replacement, ResourceLocation source, MinecraftServer server) {
      if (server == null) {
         return ValidationResult.deferred("No server context, deferred validation for " + source);
      }

      RegistryLookup<T> registryLookup = this.getRegistryLookup(server);
      if (!this.isResultExists(replacement.result(), registryLookup)) {
         return ValidationResult.failure("Target not exists: '" + replacement.result() + "'");
      }

      ValidationStreams.Accumulator acc = replacement.match()
         .stream()
         .map(
            item -> item.startsWith("#") ? this.fromDomainTag(item.substring(1), source, registryLookup) : this.fromDomainObject(item, source, registryLookup)
         )
         .reduce(ValidationStreams.Accumulator.identity(), ValidationStreams.Accumulator::combine);
      return acc.toResult(
         "Contains unresolved tags, validation deferred until tag system is ready", "No valid sources found for target '" + replacement.result() + "'"
      );
   }
}
