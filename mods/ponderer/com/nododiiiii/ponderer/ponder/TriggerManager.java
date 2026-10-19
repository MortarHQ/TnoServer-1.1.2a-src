package com.nododiiiii.ponderer.ponder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.nododiiiii.ponderer.ModKeyBindings;
import com.nododiiiii.ponderer.platform.PondererServices;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.createmod.ponder.foundation.PonderIndex;
import net.createmod.ponder.foundation.ui.PonderUI;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TriggerManager {
   private static final Logger LOGGER = LoggerFactory.getLogger("Ponderer/TriggerManager");
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   private static final int CHECK_INTERVAL = 20;
   private static int tickCounter = 0;
   private static boolean loggedOnce = false;
   @Nullable
   private static DslScene activeScene = null;
   private static int titleShownTick = -1;
   private static boolean wasInZone = false;
   private static boolean titleShownThisEntry = false;
   private static final Set<String> shownSet = new HashSet<>();
   private static final Set<String> readSet = new HashSet<>();
   private static boolean stateLoaded = false;

   private TriggerManager() {
   }

   public static void tick() {
      Minecraft mc = Minecraft.getInstance();
      if (mc.player != null && mc.level != null) {
         ensureStateLoaded();
         if (mc.screen == null) {
            if (!ModKeyBindings.TRIGGER_PONDER.consumeClick() || !tryOpenTriggeredScene(mc)) {
               tickCounter++;
               if (tickCounter >= 20) {
                  tickCounter = 0;
                  DslScene newScene = findTriggeredScene(mc);
                  boolean inZoneNow = newScene != null;
                  boolean justEntered = inZoneNow && !wasInZone;
                  boolean justLeft = !inZoneNow && wasInZone;
                  if (justLeft) {
                     activeScene = null;
                     titleShownThisEntry = false;
                  }

                  if (justEntered) {
                     titleShownThisEntry = false;
                  }

                  wasInZone = inZoneNow;
                  activeScene = newScene;
                  if (activeScene != null) {
                     String sceneKey = activeScene.sceneKey();
                     String autoFreq = resolveHintFreq(activeScene, "auto");
                     String titleFreq = resolveHintFreq(activeScene, "title");
                     String subtitleFreq = resolveHintFreq(activeScene, "subtitle");
                     if (autoFreq != null && shouldShow(autoFreq, sceneKey + ":auto") && justEntered) {
                        markShown(sceneKey, "auto");
                        openPonderFor(activeScene, sceneKey);
                        return;
                     }

                     if (titleFreq != null && shouldShow(titleFreq, sceneKey + ":title") && justEntered && !titleShownThisEntry) {
                        Component hintMsg = getHintText(activeScene.hintTitleText);
                        mc.gui.setTitle(hintMsg);
                        mc.gui.setTimes(0, 20, 10);
                        titleShownThisEntry = true;
                        markShown(sceneKey, "title");
                     }

                     if (subtitleFreq != null && shouldShow(subtitleFreq, sceneKey + ":subtitle")) {
                        Component hintMsg = getHintText(activeScene.hintSubtitleText);
                        Component greenHint = hintMsg.copy().withStyle(ChatFormatting.GREEN);
                        mc.player.displayClientMessage(greenHint, true);
                        if (justEntered) {
                           markShown(sceneKey, "subtitle");
                        }
                     }
                  }
               }
            }
         }
      } else {
         activeScene = null;
         wasInZone = false;
         titleShownThisEntry = false;
      }
   }

   @Nullable
   private static DslScene findTriggeredScene(Minecraft mc) {
      LocalPlayer player = mc.player;
      if (player == null) {
         return null;
      }

      BlockPos playerPos = player.blockPosition();
      List<DslScene> scenes = SceneRuntime.getScenes();
      if (!loggedOnce) {
         int triggerCount = 0;

         for (DslScene s : scenes) {
            if (s.triggerMode != null && !"none".equals(s.triggerMode)) {
               triggerCount++;
            }
         }

         if (triggerCount > 0) {
            LOGGER.info("TriggerManager checking {} scenes ({} with triggers)", scenes.size(), triggerCount);
         }

         loggedOnce = true;
      }

      for (DslScene scene : scenes) {
         if (scene.triggerMode != null && !"none".equals(scene.triggerMode)) {
            if ("coordinate".equals(scene.triggerMode)) {
               if (isInCoordinateRange(playerPos, scene)) {
                  return scene;
               }
            } else if ("structure".equals(scene.triggerMode) && isInStructureRange(mc, playerPos, scene)) {
               return scene;
            }
         }
      }

      return null;
   }

   private static boolean isInCoordinateRange(BlockPos playerPos, DslScene scene) {
      List<Integer> c1 = scene.triggerCoord1;
      List<Integer> c2 = scene.triggerCoord2;
      if (c1 != null && c1.size() >= 3 && c2 != null && c2.size() >= 3) {
         int minX = Math.min(c1.get(0), c2.get(0));
         int minY = Math.min(c1.get(1), c2.get(1));
         int minZ = Math.min(c1.get(2), c2.get(2));
         int maxX = Math.max(c1.get(0), c2.get(0));
         int maxY = Math.max(c1.get(1), c2.get(1));
         int maxZ = Math.max(c1.get(2), c2.get(2));
         return playerPos.getX() >= minX
            && playerPos.getX() <= maxX
            && playerPos.getY() >= minY
            && playerPos.getY() <= maxY
            && playerPos.getZ() >= minZ
            && playerPos.getZ() <= maxZ;
      } else {
         return false;
      }
   }

   private static boolean isInStructureRange(Minecraft mc, BlockPos playerPos, DslScene scene) {
      if (scene.triggerStructure != null && !scene.triggerStructure.isEmpty()) {
         ResourceLocation structureLoc = ResourceLocation.tryParse(scene.triggerStructure);
         if (structureLoc == null) {
            return false;
         }

         MinecraftServer server = mc.getSingleplayerServer();
         if (server == null) {
            return false;
         }

         try {
            ServerLevel serverLevel = server.getLevel(mc.player.level().dimension());
            if (serverLevel == null) {
               return false;
            }

            StructureManager structureManager = serverLevel.structureManager();
            Registry<Structure> registry = server.registryAccess().registryOrThrow(Registries.STRUCTURE);
            Structure structure = (Structure)registry.get(structureLoc);
            if (structure == null) {
               return false;
            }

            StructureStart directStart = structureManager.getStructureWithPieceAt(playerPos, structure);
            if (directStart != null && directStart.isValid()) {
               return true;
            }
         } catch (Exception e) {
            LOGGER.debug("Failed to check structure trigger for {}: {}", scene.triggerStructure, e.getMessage());
         }

         return false;
      } else {
         return false;
      }
   }

   private static boolean hasAnyHintEnabled(DslScene scene) {
      return scene.hintAuto == null && scene.hintTitle == null && scene.hintSubtitle == null ? scene.hintStyle != null : true;
   }

   @Nullable
   private static String resolveHintFreq(DslScene scene, String style) {
      switch (style) {
         case "auto":
            if (scene.hintAuto != null) {
               return scene.hintAuto;
            }
            break;
         case "title":
            if (scene.hintTitle != null) {
               return scene.hintTitle;
            }
            break;
         case "subtitle":
            if (scene.hintSubtitle != null) {
               return scene.hintSubtitle;
            }
      }

      if (scene.hintAuto == null && scene.hintTitle == null && scene.hintSubtitle == null && scene.hintStyle != null && style.equals(scene.hintStyle)) {
         String freq = scene.hintFrequency;
         if (freq == null && Boolean.TRUE.equals(scene.onlyFirstTime)) {
            freq = "first_time";
         }

         return freq != null ? freq : "always";
      } else {
         return null;
      }
   }

   private static boolean shouldShow(String freq, String stateKey) {
      ensureStateLoaded();
      return "first_time".equals(freq) && shownSet.contains(stateKey) ? false : !"until_read".equals(freq) || !readSet.contains(stateKey);
   }

   public static boolean onTriggerKeyPressed() {
      Minecraft mc = Minecraft.getInstance();
      boolean opened = tryOpenTriggeredScene(mc);
      if (mc.player != null) {
         LOGGER.info(
            "Trigger key pressed: opened={}, activeScene={}, pos={}",
            new Object[]{opened, activeScene != null ? activeScene.sceneKey() : "null", mc.player.blockPosition()}
         );
      } else {
         LOGGER.info("Trigger key pressed: opened={}, no player", opened);
      }

      return opened;
   }

   private static boolean tryOpenTriggeredScene(Minecraft mc) {
      DslScene sceneToOpen = activeScene != null ? activeScene : findTriggeredScene(mc);
      if (sceneToOpen == null) {
         return false;
      }

      String sceneKey = sceneToOpen.sceneKey();
      openPonderFor(sceneToOpen, sceneKey);
      activeScene = null;
      return true;
   }

   private static Component getHintText(@Nullable String customText) {
      if (customText != null && !customText.isEmpty()) {
         return Component.literal(customText);
      }

      Component keyName = ModKeyBindings.TRIGGER_PONDER.getTranslatedKeyMessage();
      return Component.translatable("ponderer.ui.trigger.hint", new Object[]{keyName});
   }

   private static void markShown(String sceneKey, String style) {
      if (shownSet.add(sceneKey + ":" + style)) {
         saveState();
      }
   }

   private static void openPonderFor(DslScene scene, String sceneKey) {
      if (scene.items != null && !scene.items.isEmpty()) {
         String itemIdStr = scene.items.get(0);
         ResourceLocation itemId = ResourceLocation.tryParse(itemIdStr);
         if (itemId != null) {
            if (!PonderIndex.getSceneAccess().doScenesExistForId(itemId)) {
               LOGGER.warn("No ponder scenes registered for item: {}", itemId);
            } else {
               boolean changed = false;
               changed |= readSet.add(sceneKey + ":auto");
               changed |= readSet.add(sceneKey + ":title");
               changed |= readSet.add(sceneKey + ":subtitle");
               if (changed) {
                  saveState();
               }

               PonderUI ui;
               if (scene.nbtFilter != null && !scene.nbtFilter.isBlank()) {
                  ui = PonderUI.of(buildFilteredStack(itemId, scene.nbtFilter));
               } else {
                  ui = PonderUI.of(itemId);
               }

               Minecraft.getInstance().setScreen(ui);
            }
         }
      }
   }

   private static ItemStack buildFilteredStack(ResourceLocation itemId, String nbtFilter) {
      try {
         Item item = (Item)BuiltInRegistries.ITEM.get(itemId);
         ItemStack stack = new ItemStack(item);
         CompoundTag tag = TagParser.parseTag(nbtFilter);
         CompoundTag fullTag = new CompoundTag();
         fullTag.putString("id", itemId.toString());
         fullTag.putInt("count", 1);
         fullTag.put("tag", tag);
         ClientLevel level = Minecraft.getInstance().level;
         if (level != null) {
            ItemStack parsed = ItemStack.parseOptional(level.registryAccess(), fullTag);
            if (!parsed.isEmpty()) {
               return parsed;
            }
         }

         stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag.copy()));
         return stack;
      } catch (Exception e) {
         Item item = (Item)BuiltInRegistries.ITEM.get(itemId);
         return new ItemStack(item);
      }
   }

   private static Path getStateFile() {
      return PondererServices.PLATFORM.getConfigDir().resolve("ponderer").resolve("trigger_state.json");
   }

   private static void ensureStateLoaded() {
      if (!stateLoaded) {
         stateLoaded = true;
         Path file = getStateFile();
         if (Files.exists(file)) {
            try (Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
               JsonObject obj = (JsonObject)GSON.fromJson(reader, JsonObject.class);
               if (obj != null) {
                  if (obj.has("shown")) {
                     for (JsonElement el : obj.getAsJsonArray("shown")) {
                        shownSet.add(el.getAsString());
                     }
                  }

                  if (obj.has("read")) {
                     for (JsonElement el : obj.getAsJsonArray("read")) {
                        readSet.add(el.getAsString());
                     }
                  }
               }
            } catch (Exception e) {
               LOGGER.warn("Failed to load trigger state: {}", e.getMessage());
            }
         }
      }
   }

   private static void saveState() {
      Path file = getStateFile();

      try {
         Files.createDirectories(file.getParent());
         JsonObject obj = new JsonObject();
         JsonArray shownArr = new JsonArray();

         for (String id : shownSet) {
            shownArr.add(id);
         }

         obj.add("shown", shownArr);
         JsonArray readArr = new JsonArray();

         for (String id : readSet) {
            readArr.add(id);
         }

         obj.add("read", readArr);

         try (Writer writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
            GSON.toJson(obj, writer);
         }
      } catch (IOException e) {
         LOGGER.warn("Failed to save trigger state: {}", e.getMessage());
      }
   }
}
