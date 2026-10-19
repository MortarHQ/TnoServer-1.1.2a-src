package de.cristelknight.cristellib.builtinpacks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import de.cristelknight.cristellib.CristelLib;
import de.cristelknight.cristellib.util.JanksonUtil;
import de.cristelknight.cristellib.util.RuntimePackUtil;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.FilePackResources;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PackResources.ResourceOutput;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.repository.KnownPack;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.util.GsonHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RuntimePack implements PackResources {
   public static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();
   private final Lock waiting = new ReentrantLock();
   private final Map<ResourceLocation, Supplier<byte[]>> data = new ConcurrentHashMap<>();
   private final Map<List<String>, Supplier<byte[]>> root = new ConcurrentHashMap<>();
   public final int packVersion;
   private final String id;
   private final PackLocationInfo metadata;

   public RuntimePack(ResourceLocation id, int version, String description, @Nullable Path imageFile) {
      this.packVersion = version;
      this.id = id.toString();
      this.metadata = new PackLocationInfo(
         this.id, Component.literal(description), new BuiltinResourcePackSource(), Optional.of(new KnownPack("cristellib", this.id, String.valueOf(version)))
      );
      if (imageFile != null) {
         byte[] image = RuntimePackUtil.extractImageBytes(imageFile);
         if (image != null) {
            this.addRootResource("pack.png", image);
         }
      }

      if (!this.hasRootResource("pack.mcmeta")) {
         JsonObject object = new JsonObject();
         JsonObject pack = new JsonObject();
         pack.addProperty("pack_format", this.packVersion);
         pack.addProperty("min_format", this.packVersion);
         pack.addProperty("max_format", this.packVersion);
         pack.addProperty("description", description);
         object.add("pack", pack);
         this.addRootResource("pack.mcmeta", RuntimePackUtil.serializeJson(object));
      }
   }

   public byte[] addStructureSet(ResourceLocation identifier, JsonObject set) {
      return this.addDataForJsonLocation("worldgen/structure_set", identifier, set);
   }

   public byte[] addBiome(ResourceLocation identifier, JsonObject biome) {
      return this.addDataForJsonLocation("worldgen/biome", identifier, biome);
   }

   public byte[] addStructure(ResourceLocation identifier, JsonObject structure) {
      return this.addDataForJsonLocation("worldgen/structure", identifier, structure);
   }

   public byte[] addLootTable(ResourceLocation identifier, JsonObject table) {
      return this.addDataForJsonLocation("loot_tables", identifier, table);
   }

   public byte @Nullable [] addDataForJsonLocationFromPath(String prefix, ResourceLocation identifier, String fromSubPath, String fromModID) {
      return JanksonUtil.getElement(fromModID, fromSubPath) instanceof JsonObject object ? this.addDataForJsonLocation(prefix, identifier, object) : null;
   }

   public byte[] addDataForJsonLocation(String prefix, ResourceLocation identifier, JsonObject object) {
      return this.addAndSerializeDataForLocation(prefix, "json", identifier, object);
   }

   public byte[] addAndSerializeDataForLocation(String prefix, String end, ResourceLocation identifier, JsonObject object) {
      return this.addData(
         ResourceLocation.fromNamespaceAndPath(identifier.getNamespace(), prefix + "/" + identifier.getPath() + "." + end),
         RuntimePackUtil.serializeJson(object)
      );
   }

   public byte[] addData(ResourceLocation path, byte[] data) {
      this.data.put(path, () -> data);
      return data;
   }

   public void removeData(ResourceLocation path) {
      this.data.remove(path);
   }

   public byte[] addRootResource(String path, byte[] data) {
      this.root.put(Arrays.asList(path.split("/")), () -> data);
      return data;
   }

   @Nullable
   public IoSupplier<InputStream> getRootResource(String @NotNull ... strings) {
      this.lock();
      Supplier<byte[]> supplier = this.root.get(Arrays.asList(strings));
      if (supplier == null) {
         this.waiting.unlock();
         return null;
      } else {
         this.waiting.unlock();
         return () -> new ByteArrayInputStream(supplier.get());
      }
   }

   public boolean hasRootResource(String @NotNull ... strings) {
      return this.root.containsKey(Arrays.asList(strings));
   }

   @Nullable
   public IoSupplier<InputStream> getResource(@NotNull PackType packType, @NotNull ResourceLocation id) {
      this.lock();
      Supplier<byte[]> supplier = this.data.get(id);
      if (supplier == null) {
         this.waiting.unlock();
         return null;
      } else {
         this.waiting.unlock();
         return () -> new ByteArrayInputStream(supplier.get());
      }
   }

   public boolean hasResource(ResourceLocation location) {
      return this.data.containsKey(location);
   }

   public void listResources(@NotNull PackType packType, @NotNull String namespace, @NotNull String prefix, @NotNull ResourceOutput resourceOutput) {
      this.lock();

      for (ResourceLocation identifier : this.data.keySet()) {
         Supplier<byte[]> supplier = this.data.get(identifier);
         if (supplier == null) {
            this.waiting.unlock();
         } else if (identifier.getNamespace().equals(namespace) && identifier.getPath().contains(prefix + "/")) {
            IoSupplier<InputStream> inputSupplier = () -> new ByteArrayInputStream(supplier.get());
            resourceOutput.accept(identifier, inputSupplier);
         }
      }

      this.waiting.unlock();
   }

   @NotNull
   public Set<String> getNamespaces(@NotNull PackType packType) {
      this.lock();
      Set<String> namespaces = new HashSet<>();

      for (ResourceLocation identifier : this.data.keySet()) {
         namespaces.add(identifier.getNamespace());
      }

      this.waiting.unlock();
      return namespaces;
   }

   @Nullable
   public <T> T getMetadataSection(MetadataSectionSerializer<T> metadataSectionSerializer) {
      InputStream stream = null;

      try {
         IoSupplier<InputStream> supplier = this.getRootResource("pack.mcmeta");
         if (supplier != null) {
            stream = (InputStream)supplier.get();
         }
      } catch (IOException e) {
         throw new RuntimeException(e);
      }

      if (stream == null) {
         CristelLib.LOGGER.error("Couldn't find pack.mcmeta of the Runtime Pack: {}", this.id);
      }

      return (T)FilePackResources.getMetadataFromStream(metadataSectionSerializer, stream);
   }

   @NotNull
   public PackLocationInfo location() {
      return this.metadata;
   }

   @NotNull
   public String packId() {
      return this.id;
   }

   private void lock() {
      if (!this.waiting.tryLock()) {
         this.waiting.lock();
      }
   }

   public void close() {
      CristelLib.LOGGER.debug("Closing RDP: {}", this.id);
   }

   public void load(Path dir) throws IOException {
      Stream<Path> stream = Files.walk(dir);

      for (Path file : () -> stream.filter(x$0 -> Files.isRegularFile(x$0)).map(dir::relativize).iterator()) {
         String s = file.toString();
         if (s.startsWith("data")) {
            String path = s.substring("data".length() + 1);
            this.load(path, this.data, Files.readAllBytes(file));
         } else if (!s.startsWith("assets")) {
            byte[] data = Files.readAllBytes(file);
            this.root.put(Arrays.asList(s.split("/")), () -> data);
         }
      }
   }

   public void load(ZipInputStream stream) throws IOException {
      ZipEntry entry;
      while ((entry = stream.getNextEntry()) != null) {
         String s = entry.toString();
         if (s.startsWith("data")) {
            String path = s.substring("data".length() + 1);
            this.load(path, this.data, this.read(entry, stream));
         } else if (!s.startsWith("assets")) {
            byte[] data = this.read(entry, stream);
            this.root.put(Arrays.asList(s.split("/")), () -> data);
         }
      }
   }

   protected byte[] read(ZipEntry entry, InputStream stream) throws IOException {
      byte[] data = new byte[Math.toIntExact(entry.getSize())];
      if (stream.read(data) != data.length) {
         throw new IOException("Zip stream was cut off! (maybe incorrect zip entry length? maybe u didn't flush your stream?)");
      } else {
         return data;
      }
   }

   protected void load(String fullPath, Map<ResourceLocation, Supplier<byte[]>> map, byte[] data) {
      int sep = fullPath.indexOf(47);
      String namespace = fullPath.substring(0, sep);
      String path = fullPath.substring(sep + 1);
      map.put(ResourceLocation.fromNamespaceAndPath(namespace, path), () -> data);
   }

   @Nullable
   public JsonObject getResource(ResourceLocation location) {
      IoSupplier<InputStream> stream = this.getResource(PackType.SERVER_DATA, location);

      try {
         return GsonHelper.parse(new BufferedReader(new InputStreamReader((InputStream)stream.get(), StandardCharsets.UTF_8)));
      } catch (IOException | NullPointerException ex) {
         CristelLib.LOGGER.error("Couldn't get JsonObject from location: {}", location, ex);
         return null;
      }
   }

   public void dumpToFolder(Path output) throws IOException {
      this.lock();

      try {
         for (Entry<List<String>, Supplier<byte[]>> entry : this.root.entrySet()) {
            List<String> pathParts = entry.getKey();
            Path filePath = output.resolve(Paths.get("", pathParts.toArray(new String[0])));
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, entry.getValue().get(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
         }

         for (Entry<ResourceLocation, Supplier<byte[]>> entry : this.data.entrySet()) {
            ResourceLocation rl = entry.getKey();
            Path filePath = output.resolve(Paths.get("data", rl.getNamespace(), rl.getPath()));
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, entry.getValue().get(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
         }
      } finally {
         this.waiting.unlock();
      }
   }
}
