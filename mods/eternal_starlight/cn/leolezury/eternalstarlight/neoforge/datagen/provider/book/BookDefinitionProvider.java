package cn.leolezury.eternalstarlight.neoforge.datagen.provider.book;

import cn.leolezury.eternalstarlight.common.client.book.BookDefinition;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableList.Builder;
import com.google.gson.JsonElement;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.PackOutput.PathProvider;
import net.minecraft.data.PackOutput.Target;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.neoforged.neoforge.common.conditions.ConditionalOps;
import net.neoforged.neoforge.common.conditions.ICondition.IContext;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.ExistingFileHelper.ResourceType;

public abstract class BookDefinitionProvider implements DataProvider {
   private static final String DIRECTORY = "eternal_starlight/books";
   protected final ResourceType resourceType;
   protected final PathProvider pathProvider;
   protected final ExistingFileHelper existingFileHelper;
   protected final CompletableFuture<Provider> lookupProvider;
   protected final String modid;
   protected final Map<ResourceLocation, BookDefinition> definitions = Maps.newHashMap();

   public BookDefinitionProvider(PackOutput output, CompletableFuture<Provider> lookupProvider, String modId, ExistingFileHelper existingFileHelper) {
      this.resourceType = new ResourceType(PackType.CLIENT_RESOURCES, ".json", "eternal_starlight/books");
      this.pathProvider = output.createPathProvider(Target.RESOURCE_PACK, "eternal_starlight/books");
      this.existingFileHelper = existingFileHelper;
      this.modid = modId;
      this.lookupProvider = lookupProvider;
   }

   public CompletableFuture<?> run(CachedOutput output) {
      Builder<CompletableFuture<?>> futuresBuilder = new Builder();
      return this.lookupProvider
         .thenCompose(
            provider -> {
               this.gather(provider);
               DynamicOps<JsonElement> dynamicOps = new ConditionalOps(RegistryOps.create(JsonOps.INSTANCE, provider), IContext.EMPTY);
               this.definitions
                  .forEach(
                     (id, definition) -> {
                        Path path = this.pathProvider.json(id);
                        futuresBuilder.add(
                           CompletableFuture.<JsonElement>supplyAsync(
                                 () -> (JsonElement)BookDefinition.CODEC
                                    .encodeStart(dynamicOps, definition)
                                    .getOrThrow(msg -> new RuntimeException("Failed to encode %s: %s".formatted(path, msg)))
                              )
                              .thenComposeAsync(encoded -> DataProvider.saveStable(output, encoded, path))
                        );
                     }
                  );
               return CompletableFuture.allOf((CompletableFuture<?>[])futuresBuilder.build().toArray(CompletableFuture[]::new));
            }
         );
   }

   protected abstract void gather(Provider var1);

   public void add(ResourceLocation id, BookDefinition definition) {
      this.definitions.put(id, definition);
   }

   public String getName() {
      return String.format("Book definitions generator for %s", this.modid);
   }
}
