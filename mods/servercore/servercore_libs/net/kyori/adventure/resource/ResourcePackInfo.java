package servercore_libs.net.kyori.adventure.resource;

import java.net.URI;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import servercore_libs.net.kyori.adventure.builder.AbstractBuilder;
import servercore_libs.net.kyori.examination.Examinable;

public interface ResourcePackInfo extends Examinable, ResourcePackInfoLike {
   @NotNull
   static ResourcePackInfo resourcePackInfo(@NotNull final UUID id, @NotNull final URI uri, @NotNull final String hash) {
      return new ResourcePackInfoImpl(id, uri, hash);
   }

   @NotNull
   static ResourcePackInfo.Builder resourcePackInfo() {
      return new ResourcePackInfoImpl.BuilderImpl();
   }

   @NotNull
   UUID id();

   @NotNull
   URI uri();

   @NotNull
   String hash();

   @NotNull
   @Override
   default ResourcePackInfo asResourcePackInfo() {
      return this;
   }

   interface Builder extends AbstractBuilder<ResourcePackInfo>, ResourcePackInfoLike {
      @Contract("_ -> this")
      @NotNull
      ResourcePackInfo.Builder id(@NotNull final UUID id);

      @Contract("_ -> this")
      @NotNull
      ResourcePackInfo.Builder uri(@NotNull final URI uri);

      @Contract("_ -> this")
      @NotNull
      ResourcePackInfo.Builder hash(@NotNull final String hash);

      @NotNull
      ResourcePackInfo build();

      @NotNull
      default CompletableFuture<ResourcePackInfo> computeHashAndBuild() {
         return this.computeHashAndBuild(ForkJoinPool.commonPool());
      }

      @NotNull
      CompletableFuture<ResourcePackInfo> computeHashAndBuild(@NotNull final Executor executor);

      @NotNull
      @Override
      default ResourcePackInfo asResourcePackInfo() {
         return this.build();
      }
   }
}
