package com.teamresourceful.resourcefullib.common.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.teamresourceful.resourcefullib.common.lib.Constants;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PackResources.ResourceOutput;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.resources.IoSupplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class GenericMemoryPack implements PackResources {
   private final Map<ResourceLocation, IoSupplier<InputStream>> data = new HashMap<>();
   private final JsonObject metaData;
   private final PackType allowedType;
   private final String id;
   private final PackLocationInfo info;

   protected GenericMemoryPack(PackType type, String id, JsonObject meta) {
      this.metaData = meta;
      this.allowedType = type;
      this.id = id;
      this.info = new PackLocationInfo(id, CommonComponents.EMPTY, PackSource.BUILT_IN, Optional.empty());
   }

   private boolean isTypeAllowed(PackType type) {
      return this.allowedType.equals(type);
   }

   public void putData(PackType type, ResourceLocation location, IoSupplier<InputStream> supplier) {
      if (this.isTypeAllowed(type)) {
         this.data.put(location, supplier);
      }
   }

   public void putJson(PackType type, ResourceLocation location, JsonElement json) {
      this.putData(type, location, () -> new ByteArrayInputStream(Constants.GSON.toJson(json).getBytes(StandardCharsets.UTF_8)));
   }

   @Nullable
   public IoSupplier<InputStream> getRootResource(String @NotNull ... files) {
      String file = String.join("/", files);
      if (!file.contains("/") && !file.contains("\\")) {
         return null;
      } else {
         throw new IllegalArgumentException("Root resources can only be filenames, not paths (no / allowed!)");
      }
   }

   @Nullable
   public IoSupplier<InputStream> getResource(@NotNull PackType type, @NotNull ResourceLocation location) {
      return !this.isTypeAllowed(type) ? null : this.data.getOrDefault(location, null);
   }

   public void listResources(@NotNull PackType type, @NotNull String namespace, @NotNull String path, @NotNull ResourceOutput output) {
      if (this.isTypeAllowed(type)) {
         this.data
            .entrySet()
            .stream()
            .filter(entry -> entry.getKey().getNamespace().equals(namespace))
            .filter(entry -> entry.getKey().getPath().startsWith(path))
            .forEach(entry -> output.accept(entry.getKey(), entry.getValue()));
      }
   }

   @NotNull
   public Set<String> getNamespaces(@NotNull PackType type) {
      return !this.isTypeAllowed(type)
         ? Collections.emptySet()
         : this.data.keySet().stream().<String>map(ResourceLocation::getNamespace).collect(Collectors.toSet());
   }

   @Nullable
   public <T> T getMetadataSection(@NotNull MetadataSectionSerializer<T> serializer) {
      return (T)(!serializer.getMetadataSectionName().equals("pack") ? null : serializer.fromJson(this.metaData));
   }

   @NotNull
   public String packId() {
      return this.id;
   }

   @NotNull
   public PackLocationInfo location() {
      return this.info;
   }

   public void close() {
      for (IoSupplier<InputStream> value : this.data.values()) {
         try {
            ((InputStream)value.get()).close();
         } catch (IOException e) {
            Constants.LOGGER.error("Failed to close input stream", e);
         }
      }
   }
}
