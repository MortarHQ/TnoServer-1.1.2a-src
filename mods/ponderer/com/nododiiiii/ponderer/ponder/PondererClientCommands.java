package com.nododiiiii.ponderer.ponder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.nododiiiii.ponderer.network.DownloadStructurePayload;
import com.nododiiiii.ponderer.network.SyncRequestPayload;
import com.nododiiiii.ponderer.network.UploadScenePayload;
import com.nododiiiii.ponderer.platform.PondererServices;
import com.nododiiiii.ponderer.ui.ExportPackScreen;
import com.nododiiiii.ponderer.ui.ImportPackScreen;
import com.nododiiiii.ponderer.ui.PonderItemGridScreen;
import com.nododiiiii.ponderer.util.SafePaths;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.CompoundTagArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.storage.LevelResource;

public final class PondererClientCommands {
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalizedText.class, new LocalizedText.GsonAdapter()).create();
   private static String pendingPullMode = "check";

   private PondererClientCommands() {
   }

   public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal(
                                                "ponderer"
                                             )
                                             .then(
                                                ((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("pull").executes(ctx -> pull("check")))
                                                      .then(Commands.literal("force").executes(ctx -> pull("force"))))
                                                   .then(Commands.literal("keep_local").executes(ctx -> pull("keep_local")))
                                             ))
                                          .then(Commands.literal("reload").executes(ctx -> reloadLocal())))
                                       .then(
                                          Commands.literal("download")
                                             .then(
                                                Commands.argument("id", ResourceLocationArgument.id())
                                                   .executes(ctx -> download(ResourceLocationArgument.getId(ctx, "id")))
                                             )
                                       ))
                                    .then(
                                       ((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("push").executes(ctx -> pushAll("check")))
                                             .then(
                                                ((LiteralArgumentBuilder)Commands.literal("force").executes(ctx -> pushAll("force")))
                                                   .then(
                                                      Commands.argument("id", ResourceLocationArgument.id())
                                                         .executes(ctx -> push(ResourceLocationArgument.getId(ctx, "id"), "force"))
                                                   )
                                             ))
                                          .then(
                                             Commands.argument("id", ResourceLocationArgument.id())
                                                .executes(ctx -> push(ResourceLocationArgument.getId(ctx, "id"), "check"))
                                          )
                                    ))
                                 .then(
                                    ((LiteralArgumentBuilder)Commands.literal("convert")
                                          .then(
                                             ((LiteralArgumentBuilder)Commands.literal("to_ponderjs")
                                                   .then(Commands.literal("all").executes(ctx -> convertAllToPonderJs())))
                                                .then(
                                                   Commands.argument("id", ResourceLocationArgument.id())
                                                      .executes(ctx -> convertToPonderJs(ResourceLocationArgument.getId(ctx, "id")))
                                                )
                                          ))
                                       .then(
                                          ((LiteralArgumentBuilder)Commands.literal("from_ponderjs")
                                                .then(Commands.literal("all").executes(ctx -> convertAllFromPonderJs())))
                                             .then(
                                                Commands.argument("id", ResourceLocationArgument.id())
                                                   .executes(ctx -> convertFromPonderJs(ResourceLocationArgument.getId(ctx, "id")))
                                             )
                                       )
                                 ))
                              .then(
                                 ((LiteralArgumentBuilder)Commands.literal("new")
                                       .then(
                                          ((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("hand").executes(ctx -> newSceneFromHand(null)))
                                                .then(Commands.literal("use_held_nbt").executes(ctx -> newSceneFromHandWithHeldNbt())))
                                             .then(
                                                Commands.argument("nbt", CompoundTagArgument.compoundTag())
                                                   .executes(ctx -> newSceneFromHand(CompoundTagArgument.getCompoundTag(ctx, "nbt")))
                                             )
                                       ))
                                    .then(
                                       ((RequiredArgumentBuilder)Commands.argument("item", ResourceLocationArgument.id())
                                             .executes(ctx -> newSceneForItem(ResourceLocationArgument.getId(ctx, "item"), null)))
                                          .then(
                                             Commands.argument("nbt", CompoundTagArgument.compoundTag())
                                                .executes(
                                                   ctx -> newSceneForItem(
                                                      ResourceLocationArgument.getId(ctx, "item"), CompoundTagArgument.getCompoundTag(ctx, "nbt")
                                                   )
                                                )
                                          )
                                    )
                              ))
                           .then(
                              Commands.literal("copy")
                                 .then(
                                    Commands.argument("id", ResourceLocationArgument.id())
                                       .then(
                                          Commands.argument("target_item", ResourceLocationArgument.id())
                                             .executes(
                                                ctx -> copyScene(ResourceLocationArgument.getId(ctx, "id"), ResourceLocationArgument.getId(ctx, "target_item"))
                                             )
                                       )
                                 )
                           ))
                        .then(
                           ((LiteralArgumentBuilder)Commands.literal("delete")
                                 .then(
                                    Commands.argument("id", ResourceLocationArgument.id())
                                       .executes(ctx -> deleteScene(ResourceLocationArgument.getId(ctx, "id")))
                                 ))
                              .then(
                                 Commands.literal("item")
                                    .then(
                                       Commands.argument("item_id", ResourceLocationArgument.id())
                                          .executes(ctx -> deleteScenesForItem(ResourceLocationArgument.getId(ctx, "item_id")))
                                    )
                              )
                        ))
                     .then(Commands.literal("list").executes(ctx -> openItemList())))
                  .then(Commands.literal("export").executes(ctx -> openExportScreen())))
               .then(Commands.literal("import").executes(ctx -> openImportScreen())))
            .then(
               Commands.literal("unregister_pack")
                  .then(
                     Commands.argument("pack_name", StringArgumentType.greedyString())
                        .executes(ctx -> unregisterPack(StringArgumentType.getString(ctx, "pack_name")))
                  )
            )
      );
   }

   private static int convertToPonderJs(ResourceLocation id) {
      return PonderJsConversionService.convertToPonderJs(id);
   }

   public static int convertAllToPonderJs() {
      return PonderJsConversionService.convertAllToPonderJs();
   }

   private static int convertFromPonderJs(ResourceLocation id) {
      return PonderJsConversionService.convertFromPonderJs(id);
   }

   public static int convertAllFromPonderJs() {
      return PonderJsConversionService.convertAllFromPonderJs();
   }

   public static int pull(String mode) {
      pendingPullMode = mode;
      PondererServices.NETWORK.sendToServer(new SyncRequestPayload());
      notifyClient(Component.translatable("ponderer.cmd.pull.requesting", new Object[]{mode}));
      return 1;
   }

   public static String consumePullMode() {
      String mode = pendingPullMode;
      pendingPullMode = "check";
      return mode;
   }

   public static int reloadLocal() {
      SceneStore.AutoLoadResult autoLoadResult = SceneStore.autoLoadPonderPacks();
      int count = SceneStore.reloadFromDisk();
      Minecraft.getInstance().execute(PonderIndex::reload);

      for (SceneStore.PackUpdateInfo info : autoLoadResult.updatedPacks) {
         notifyClient(
            Component.literal("[Ponderer] ")
               .append(Component.translatable("ponderer.pack.update.readonly_newer_source", new Object[]{info.packName, info.newVersion, info.oldVersion}))
         );
      }

      notifyClient(Component.translatable("ponderer.cmd.reload.done", new Object[]{count}));
      return count;
   }

   private static int download(ResourceLocation sourceId) {
      requestStructureDownload(sourceId);
      return 1;
   }

   public static void requestStructureDownload(ResourceLocation sourceId) {
      if (sourceId != null) {
         PondererServices.NETWORK.sendToServer(new DownloadStructurePayload(sourceId.toString()));
         notifyClient(Component.translatable("ponderer.cmd.download.requesting", new Object[]{sourceId.toString()}));
      }
   }

   public static int push(ResourceLocation id, String mode) {
      Optional<DslScene> scene = SceneRuntime.getScenes().stream().filter(s -> id.toString().equals(s.id)).findFirst();
      if (scene.isEmpty()) {
         notifyClient(Component.translatable("ponderer.cmd.scene_not_found", new Object[]{id.toString()}));
         return 0;
      } else {
         return pushScene(scene.get(), mode);
      }
   }

   public static int pushByKey(String sceneKey, String mode) {
      DslScene scene = SceneRuntime.findByKey(sceneKey);
      if (scene == null) {
         notifyClient(Component.translatable("ponderer.cmd.scene_not_found", new Object[]{sceneKey}));
         return 0;
      } else {
         return pushScene(scene, mode);
      }
   }

   private static int pushScene(DslScene scene, String mode) {
      List<UploadScenePayload.StructureEntry> structures = new ArrayList<>();
      DslScene uploadScene = (DslScene)GSON.fromJson(GSON.toJson(scene), DslScene.class);
      remapStructuresForUpload(uploadScene, structures);
      String json = GSON.toJson(uploadScene);
      String metaKey = SyncMeta.metaKey("scripts", scene.id, scene.pack);
      Map<String, String> meta = SyncMeta.load();
      String lastSyncHash = meta.getOrDefault(metaKey, "");
      PondererServices.NETWORK.sendToServer(new UploadScenePayload(scene.id, scene.pack, json, structures, mode, lastSyncHash));
      notifyClient(Component.translatable("ponderer.cmd.push.uploading", new Object[]{scene.sceneKey(), mode}));
      return 1;
   }

   private static void remapStructuresForUpload(DslScene scene, List<UploadScenePayload.StructureEntry> uploadEntries) {
      Map<String, String> remapped = new HashMap<>();
      if (scene.structures != null && !scene.structures.isEmpty()) {
         List<String> mapped = new ArrayList<>();

         for (String ref : scene.structures) {
            String updated = remapStructureRef(ref, scene.pack, uploadEntries, remapped);
            mapped.add(updated == null ? ref : updated);
         }

         scene.structures = mapped;
      } else if (scene.structure != null && !scene.structure.isBlank()) {
         String updated = remapStructureRef(scene.structure, scene.pack, uploadEntries, remapped);
         if (updated != null) {
            scene.structure = updated;
         }
      }

      if (scene.scenes != null) {
         for (DslScene.SceneSegment seg : scene.scenes) {
            if (seg != null && seg.steps != null) {
               for (DslScene.DslStep step : seg.steps) {
                  if (step != null && step.structure != null && !step.structure.isBlank() && !isNumeric(step.structure.trim())) {
                     String updated = remapStructureRef(step.structure, scene.pack, uploadEntries, remapped);
                     if (updated != null) {
                        step.structure = updated;
                     }
                  }
               }
            }
         }
      }
   }

   private static String remapStructureRef(
      String ref, @Nullable String pack, List<UploadScenePayload.StructureEntry> uploadEntries, Map<String, String> remapped
   ) {
      if (ref != null && !ref.isBlank()) {
         String key = ref.trim();
         if (remapped.containsKey(key)) {
            return remapped.get(key);
         }

         ResourceLocation source = parseStructureLocation(key);
         if (source == null) {
            return null;
         }

         ResourceLocation target = ResourceLocation.fromNamespaceAndPath("ponderer", source.getPath());
         Path sourcePath = findStructureSourcePath(source, pack);
         if (sourcePath != null && Files.exists(sourcePath)) {
            Path targetPath = SceneStore.resolveLocalSyncStructurePath(target, pack);
            if (targetPath == null) {
               notifyClient(Component.translatable("ponderer.cmd.push.copy_failed", new Object[]{source.toString(), target.toString()}));
               return source.toString();
            }

            try {
               byte[] bytes = Files.readAllBytes(sourcePath);
               Files.createDirectories(targetPath.getParent());
               Files.write(targetPath, bytes);
               if (uploadEntries.stream().noneMatch(ex -> ex.id().equals(target.toString()) && Objects.equals(ex.pack(), pack))) {
                  uploadEntries.add(new UploadScenePayload.StructureEntry(target.toString(), pack, bytes));
               }

               remapped.put(key, target.toString());
               return target.toString();
            } catch (Exception e) {
               notifyClient(Component.translatable("ponderer.cmd.push.copy_failed", new Object[]{source.toString(), target.toString()}));
               return source.toString();
            }
         } else {
            notifyClient(Component.translatable("ponderer.cmd.push.structure_not_found", new Object[]{source.toString()}));
            return source.toString();
         }
      } else {
         return null;
      }
   }

   private static Path findStructureSourcePath(ResourceLocation id, @Nullable String pack) {
      if ("ponderer".equals(id.getNamespace())) {
         if (pack != null && !pack.isBlank()) {
            Path packPath = SceneStore.resolveLocalSyncStructurePath(id, pack);
            if (packPath != null && Files.exists(packPath)) {
               return packPath;
            }
         }

         return SceneStore.getStructurePath(id);
      } else {
         IntegratedServer server = Minecraft.getInstance().getSingleplayerServer();
         return server == null
            ? null
            : SafePaths.resolveRelativePath(
               server.getWorldPath(LevelResource.ROOT).resolve("generated"), id.getNamespace() + "/structures/" + id.getPath() + ".nbt"
            );
      }
   }

   private static ResourceLocation parseStructureLocation(String raw) {
      return raw.contains(":") ? ResourceLocation.tryParse(raw) : ResourceLocation.fromNamespaceAndPath("ponder", raw);
   }

   private static boolean isNumeric(String raw) {
      if (raw != null && !raw.isBlank()) {
         for (int i = 0; i < raw.length(); i++) {
            if (!Character.isDigit(raw.charAt(i))) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public static int pushAll(String mode) {
      List<DslScene> scenes = SceneRuntime.getScenes();
      if (scenes.isEmpty()) {
         notifyClient(Component.translatable("ponderer.cmd.push.no_scenes"));
         return 0;
      }

      int count = 0;

      for (DslScene scene : scenes) {
         if (scene != null && scene.id != null && !scene.id.isBlank()) {
            pushScene(scene, mode);
            count++;
         }
      }

      notifyClient(Component.translatable("ponderer.cmd.push.done", new Object[]{count, mode}));
      return count;
   }

   public static int newSceneFromHand(@Nullable CompoundTag nbt) {
      LocalPlayer player = Minecraft.getInstance().player;
      if (player == null) {
         return 0;
      } else {
         ItemStack held = player.getMainHandItem();
         if (held.isEmpty()) {
            notifyClient(Component.translatable("ponderer.cmd.new.no_item"));
            return 0;
         } else {
            ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(held.getItem());
            return newSceneForItem(itemId, nbt);
         }
      }
   }

   private static int newSceneFromHandWithHeldNbt() {
      LocalPlayer player = Minecraft.getInstance().player;
      if (player == null) {
         return 0;
      } else {
         ItemStack held = player.getMainHandItem();
         if (held.isEmpty()) {
            notifyClient(Component.translatable("ponderer.cmd.new.no_item"));
            return 0;
         } else {
            CompoundTag tag = extractStackNbtFilter(held);
            if (tag != null && !tag.isEmpty()) {
               ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(held.getItem());
               return newSceneForItem(itemId, tag);
            } else {
               notifyClient(Component.translatable("ponderer.cmd.new.no_nbt"));
               return 0;
            }
         }
      }
   }

   @Nullable
   public static CompoundTag extractStackNbtFilter(ItemStack stack) {
      if (stack != null && !stack.isEmpty()) {
         CompoundTag custom = ((CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)).copyTag();
         if (!custom.isEmpty()) {
            return custom;
         }

         ClientLevel level = Minecraft.getInstance().level;
         if (level != null && stack.saveOptional(level.registryAccess()) instanceof CompoundTag ct && ct.contains("components", 10)) {
            CompoundTag components = ct.getCompound("components");
            if (!components.isEmpty()) {
               CompoundTag filter = new CompoundTag();
               filter.put("components", components.copy());
               return filter;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   public static int newSceneForItem(ResourceLocation itemId, @Nullable CompoundTag nbt) {
      String basePath = itemId.getPath();
      String baseId = "ponderer:" + basePath;
      String sceneId = baseId;
      int suffix = 0;

      while (sceneExists(sceneId)) {
         sceneId = baseId + "_" + ++suffix;
      }

      DslScene scene = new DslScene();
      scene.id = sceneId;
      scene.items = List.of(itemId.toString());
      scene.title = LocalizedText.ofMap(Map.of("en_us", "New Scene - " + itemId.getPath(), "zh_cn", "新场景 - " + itemId.getPath()));
      scene.structures = List.of("ponderer:basic");
      scene.tags = List.of();
      if (nbt != null) {
         scene.nbtFilter = nbt.toString();
      }

      DslScene.SceneSegment seg = new DslScene.SceneSegment();
      seg.id = "scene_1";
      seg.title = LocalizedText.ofMap(Map.of("en_us", "Scene 1", "zh_cn", "场景 1"));
      DslScene.DslStep showStep = new DslScene.DslStep();
      showStep.type = "show_structure";
      showStep.attachKeyFrame = true;
      DslScene.DslStep idleStep = new DslScene.DslStep();
      idleStep.type = "idle";
      idleStep.duration = 20;
      DslScene.DslStep textStep = new DslScene.DslStep();
      textStep.type = "text";
      textStep.duration = 60;
      textStep.text = LocalizedText.ofMap(Map.of("en_us", "Edit this ponder scene!", "zh_cn", "编辑这个思索场景!"));
      textStep.point = List.of(2.5, 2.0, 2.5);
      textStep.placeNearTarget = true;
      textStep.attachKeyFrame = true;
      seg.steps = List.of(showStep, idleStep, textStep);
      scene.scenes = List.of(seg);
      if (SceneStore.saveSceneToLocal(scene)) {
         SceneStore.reloadFromDisk();
         Minecraft.getInstance().execute(PonderIndex::reload);
         notifyClient(Component.translatable("ponderer.cmd.new.created", new Object[]{sceneId, itemId.toString()}));
         return 1;
      } else {
         notifyClient(Component.translatable("ponderer.cmd.new.failed", new Object[]{itemId.toString()}));
         return 0;
      }
   }

   private static boolean sceneExists(String id) {
      return SceneRuntime.getScenes().stream().anyMatch(s -> id.equals(s.id));
   }

   public static int copyScene(ResourceLocation sceneId, ResourceLocation targetItem) {
      Optional<DslScene> source = SceneRuntime.getScenes().stream().filter(s -> sceneId.toString().equals(s.id)).findFirst();
      if (source.isEmpty()) {
         notifyClient(Component.translatable("ponderer.cmd.scene_not_found", new Object[]{sceneId.toString()}));
         return 0;
      } else {
         return doCopyScene(source.get(), targetItem);
      }
   }

   public static int copySceneByKey(String sceneKey, ResourceLocation targetItem) {
      DslScene source = SceneRuntime.findByKey(sceneKey);
      if (source == null) {
         notifyClient(Component.translatable("ponderer.cmd.scene_not_found", new Object[]{sceneKey}));
         return 0;
      } else {
         return doCopyScene(source, targetItem);
      }
   }

   private static int doCopyScene(DslScene original, ResourceLocation targetItem) {
      String json = GSON.toJson(original);
      DslScene copy = (DslScene)GSON.fromJson(json, DslScene.class);
      String baseId = "ponderer:" + targetItem.getPath();
      String newId = baseId;
      int suffix = 0;

      while (sceneExists(newId)) {
         newId = baseId + "_" + ++suffix;
      }

      copy.id = newId;
      copy.items = List.of(targetItem.toString());
      if (SceneStore.saveSceneToLocal(copy)) {
         SceneStore.reloadFromDisk();
         Minecraft.getInstance().execute(PonderIndex::reload);
         notifyClient(Component.translatable("ponderer.cmd.copy.done", new Object[]{original.id, newId, targetItem.toString()}));
         return 1;
      } else {
         notifyClient(Component.translatable("ponderer.cmd.copy.failed"));
         return 0;
      }
   }

   public static int deleteScene(ResourceLocation sceneId) {
      String id = sceneId.toString();
      Optional<DslScene> target = SceneRuntime.getScenes().stream().filter(s -> id.equals(s.id)).findFirst();
      if (target.isEmpty()) {
         notifyClient(Component.translatable("ponderer.cmd.scene_not_found", new Object[]{id}));
         return 0;
      } else if (SceneStore.deleteSceneLocal(id)) {
         SceneStore.reloadFromDisk();
         Minecraft.getInstance().execute(PonderIndex::reload);
         notifyClient(Component.translatable("ponderer.cmd.delete.done", new Object[]{id}));
         return 1;
      } else {
         notifyClient(Component.translatable("ponderer.cmd.delete.failed", new Object[]{id}));
         return 0;
      }
   }

   public static int deleteSceneByKey(String sceneKey) {
      DslScene target = SceneRuntime.findByKey(sceneKey);
      if (target == null) {
         notifyClient(Component.translatable("ponderer.cmd.scene_not_found", new Object[]{sceneKey}));
         return 0;
      } else if (SceneStore.deleteSceneByKey(sceneKey)) {
         SceneStore.reloadFromDisk();
         Minecraft.getInstance().execute(PonderIndex::reload);
         notifyClient(Component.translatable("ponderer.cmd.delete.done", new Object[]{sceneKey}));
         return 1;
      } else {
         notifyClient(Component.translatable("ponderer.cmd.delete.failed", new Object[]{sceneKey}));
         return 0;
      }
   }

   public static int deleteScenesForItem(ResourceLocation itemId) {
      String itemStr = itemId.toString();
      List<DslScene> matching = SceneRuntime.getScenes().stream().filter(s -> s.items != null && s.items.contains(itemStr)).toList();
      if (matching.isEmpty()) {
         notifyClient(Component.translatable("ponderer.cmd.delete.no_scenes", new Object[]{itemStr}));
         return 0;
      }

      int count = 0;

      for (DslScene scene : matching) {
         if (scene.id != null && SceneStore.deleteSceneLocal(scene.id)) {
            count++;
         }
      }

      SceneStore.reloadFromDisk();
      Minecraft.getInstance().execute(PonderIndex::reload);
      notifyClient(Component.translatable("ponderer.cmd.delete.item_done", new Object[]{count, itemStr}));
      return count;
   }

   public static int openItemList() {
      Minecraft.getInstance().execute(() -> Minecraft.getInstance().setScreen(new PonderItemGridScreen()));
      return 1;
   }

   private static void notifyClient(Component message) {
      if (Minecraft.getInstance().player != null) {
         Minecraft.getInstance().player.displayClientMessage(message, false);
      }
   }

   private static int openExportScreen() {
      Minecraft.getInstance().setScreen(new ExportPackScreen());
      return 1;
   }

   private static int openImportScreen() {
      Minecraft.getInstance().setScreen(new ImportPackScreen());
      return 1;
   }

   private static int unregisterPack(String packName) {
      LocalPlayer player = Minecraft.getInstance().player;
      if (player == null) {
         return 0;
      }

      PackStateStore.load();
      boolean hasLocalCopy = PackStateStore.isImported(packName) || SceneStore.getPackDir(packName) != null && Files.exists(SceneStore.getPackDir(packName));
      if (!hasLocalCopy && !PackStateStore.hasImportedState(packName)) {
         player.displayClientMessage(Component.translatable("ponderer.pack.unregister.not_found", new Object[]{packName}), false);
         return 0;
      }

      Path packDir = SceneStore.getPackDir(packName);
      if (packDir != null) {
         deleteDirectoryRecursive(packDir);
      }

      PackStateStore.removeImportedPack(packName);
      SceneStore.autoLoadPonderPacks();
      SceneStore.reloadFromDisk();
      Minecraft.getInstance().execute(PonderIndex::reload);
      player.displayClientMessage(Component.translatable("ponderer.pack.unregister.done", new Object[]{packName}), false);
      return 1;
   }

   private static void deleteDirectoryRecursive(Path dir) {
      if (Files.exists(dir)) {
         try (Stream<Path> paths = Files.walk(dir)) {
            for (Path p : paths.sorted(Comparator.reverseOrder()).toList()) {
               try {
                  Files.deleteIfExists(p);
               } catch (Exception var6) {
               }
            }
         } catch (Exception var8) {
         }
      }
   }
}
