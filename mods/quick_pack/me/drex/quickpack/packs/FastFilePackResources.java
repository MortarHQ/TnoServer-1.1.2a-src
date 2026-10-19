package me.drex.quickpack.packs;

import com.mojang.logging.LogUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.AbstractPackResources;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PackResources.ResourceOutput;
import net.minecraft.server.packs.resources.IoSupplier;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public class FastFilePackResources extends AbstractPackResources {
   public static final Logger LOGGER = LogUtils.getLogger();
   private final TreeMap<String, byte[]> fileMap = new TreeMap<>();
   private final Map<String, Set<String>> namespaces = new HashMap<>();
   private final ZipFile zipFile;
   private final List<String> prefixStack;
   private final Set<String> overlays;

   public FastFilePackResources(PackLocationInfo packLocationInfo, ZipFile zipFile, List<String> overlays) {
      super(packLocationInfo);
      this.zipFile = zipFile;
      this.overlays = new HashSet<>(overlays);
      this.prefixStack = new ArrayList<>(overlays.size() + 1);

      for (int i = overlays.size() - 1; i >= 0; i--) {
         this.prefixStack.add(overlays.get(i) + "/");
      }

      this.prefixStack.add("");
      this.extractFiles();
   }

   public void extractFiles() {
      if (this.zipFile != null) {
         Enumeration<? extends ZipEntry> entries = this.zipFile.entries();

         while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            if (!entry.isDirectory()) {
               String path = entry.getName();
               this.extractNamespace(path);

               try (InputStream in = this.zipFile.getInputStream(entry)) {
                  byte[] data = in.readAllBytes();
                  this.fileMap.put(path, data);
               } catch (IOException e) {
                  LOGGER.error("Failed to load resource {}", path, e);
               }
            }
         }
      }
   }

   private void extractNamespace(String path) {
      String[] parts = path.split("/");
      if (parts.length != 0) {
         boolean isOverlay = this.overlays.contains(parts[0]);
         String type;
         String namespace;
         if (isOverlay && parts.length >= 3) {
            type = parts[1];
            namespace = parts[2];
         } else {
            if (isOverlay || parts.length < 2) {
               return;
            }

            type = parts[0];
            namespace = parts[1];
         }

         if (ResourceLocation.isValidNamespace(namespace)) {
            this.namespaces.computeIfAbsent(type, s -> new HashSet<>()).add(namespace);
         } else {
            LOGGER.warn("Non [a-z0-9_.-] character in namespace {} in pack {}, ignoring", namespace, this.zipFile);
         }
      }
   }

   @Nullable
   public IoSupplier<InputStream> getRootResource(String... parts) {
      byte[] data = this.fileMap.get(String.join("/", parts));
      return data == null ? null : () -> new ByteArrayInputStream(data);
   }

   @Nullable
   public IoSupplier<InputStream> getResource(PackType packType, ResourceLocation resourceLocation) {
      for (String prefix : this.prefixStack) {
         byte[] data = this.fileMap.get(prefix + packType.getDirectory() + "/" + resourceLocation.getNamespace() + "/" + resourceLocation.getPath());
         if (data != null) {
            return () -> new ByteArrayInputStream(data);
         }
      }

      return null;
   }

   public void listResources(PackType packType, String namespace, String path, ResourceOutput resourceOutput) {
      Map<ResourceLocation, IoSupplier<InputStream>> map = new HashMap<>();

      for (String prefix : this.prefixStack) {
         String namespacePrefix = prefix + packType.getDirectory() + "/" + namespace + "/";
         String dirPrefix = namespacePrefix + path + "/";
         String end = dirPrefix + "\uffff";
         this.fileMap.subMap(dirPrefix, end).forEach((filePath, bytes) -> {
            String rlPath = filePath.substring(namespacePrefix.length());
            ResourceLocation location = ResourceLocation.tryBuild(namespace, rlPath);
            if (location != null) {
               map.putIfAbsent(location, () -> new ByteArrayInputStream(bytes));
            } else {
               LOGGER.warn("Invalid path in datapack: {}:{}, ignoring", namespace, rlPath);
            }
         });
      }

      map.forEach(resourceOutput);
   }

   public Set<String> getNamespaces(PackType packType) {
      return this.namespaces.getOrDefault(packType.getDirectory(), Collections.emptySet());
   }

   public void close() {
      if (this.zipFile != null) {
         IOUtils.closeQuietly(this.zipFile);
      }
   }
}
