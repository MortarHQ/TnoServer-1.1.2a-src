package com.github.wal_bos.moreskills.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.Map.Entry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelResource;

public final class UniqueOwnerSkillData {
   private static final String FILE_NAME = "tensuramoreskills_unique_owner_skills.json";
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   private static final Map<MinecraftServer, UniqueOwnerSkillData> CACHE = new WeakHashMap<>();
   private final MinecraftServer server;
   private final Path path;
   private final Map<ResourceLocation, UUID> skillOwners = new HashMap<>();
   private final Map<UUID, ResourceLocation> ownerSkills = new HashMap<>();

   private UniqueOwnerSkillData(MinecraftServer server) {
      this.server = server;
      this.path = server.getWorldPath(LevelResource.ROOT).resolve("data").resolve("tensuramoreskills_unique_owner_skills.json");
      this.loadNow();
   }

   public static UniqueOwnerSkillData get(MinecraftServer server) {
      synchronized (CACHE) {
         return CACHE.computeIfAbsent(server, UniqueOwnerSkillData::new);
      }
   }

   public static String fileName() {
      return "tensuramoreskills_unique_owner_skills.json";
   }

   public synchronized void loadNow() {
      this.skillOwners.clear();
      this.ownerSkills.clear();
      if (Files.exists(this.path)) {
         try (Reader reader = Files.newBufferedReader(this.path)) {
            JsonElement rootElement = JsonParser.parseReader(reader);
            if (rootElement.isJsonObject()) {
               JsonObject root = rootElement.getAsJsonObject();
               JsonElement ownersElement = root.get("Owners");
               if (ownersElement != null && ownersElement.isJsonArray()) {
                  for (JsonElement element : ownersElement.getAsJsonArray()) {
                     if (element.isJsonObject()) {
                        JsonObject entry = element.getAsJsonObject();
                        JsonElement skillElement = entry.get("Skill");
                        JsonElement ownerElement = entry.get("Owner");
                        if (skillElement != null && ownerElement != null) {
                           ResourceLocation skillId = ResourceLocation.tryParse(skillElement.getAsString());
                           if (skillId != null) {
                              UUID owner;
                              try {
                                 owner = UUID.fromString(ownerElement.getAsString());
                              } catch (IllegalArgumentException ignored) {
                                 continue;
                              }

                              if (!this.ownerSkills.containsKey(owner) && !this.skillOwners.containsKey(skillId)) {
                                 this.skillOwners.put(skillId, owner);
                                 this.ownerSkills.put(owner, skillId);
                              }
                           }
                        }
                     }
                  }
               }
            }
         } catch (Exception ignored) {
            this.skillOwners.clear();
            this.ownerSkills.clear();
         }
      }
   }

   public synchronized void saveNow() {
      JsonObject root = new JsonObject();
      JsonArray owners = new JsonArray();

      for (Entry<ResourceLocation, UUID> entry : this.skillOwners.entrySet()) {
         ResourceLocation skillId = entry.getKey();
         UUID owner = entry.getValue();
         if (skillId != null && owner != null) {
            ResourceLocation reverseSkill = this.ownerSkills.get(owner);
            if (skillId.equals(reverseSkill)) {
               JsonObject ownerEntry = new JsonObject();
               ownerEntry.addProperty("Skill", skillId.toString());
               ownerEntry.addProperty("Owner", owner.toString());
               owners.add(ownerEntry);
            }
         }
      }

      root.add("Owners", owners);

      try {
         Files.createDirectories(this.path.getParent());
         Path temp = this.path.resolveSibling(this.path.getFileName().toString() + ".tmp");

         try (Writer writer = Files.newBufferedWriter(temp)) {
            GSON.toJson(root, writer);
         }

         try {
            Files.move(temp, this.path, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
         } catch (AtomicMoveNotSupportedException ignored) {
            Files.move(temp, this.path, StandardCopyOption.REPLACE_EXISTING);
         }
      } catch (IOException var12) {
      }
   }

   public synchronized Set<ResourceLocation> getSkillIds() {
      return new LinkedHashSet<>(this.skillOwners.keySet());
   }

   public synchronized Set<UUID> getOwnerIds() {
      return new LinkedHashSet<>(this.ownerSkills.keySet());
   }

   public synchronized UUID getOwner(ResourceLocation skillId) {
      return skillId == null ? null : this.skillOwners.get(skillId);
   }

   public synchronized ResourceLocation getOwnedSkill(UUID owner) {
      return owner == null ? null : this.ownerSkills.get(owner);
   }

   public synchronized boolean hasAnyOwner(ResourceLocation skillId) {
      return skillId != null && this.skillOwners.containsKey(skillId);
   }

   public synchronized boolean ownerHasAnySkill(UUID owner) {
      return owner != null && this.ownerSkills.containsKey(owner);
   }

   public synchronized boolean ownerHasSkill(UUID owner, ResourceLocation skillId) {
      return owner != null && skillId != null ? skillId.equals(this.ownerSkills.get(owner)) && owner.equals(this.skillOwners.get(skillId)) : false;
   }

   public synchronized void setOwner(ResourceLocation skillId, UUID owner) {
      if (skillId != null && owner != null) {
         ResourceLocation oldSkill = this.ownerSkills.get(owner);
         if (oldSkill != null && !oldSkill.equals(skillId)) {
            this.skillOwners.remove(oldSkill);
         }

         UUID oldOwner = this.skillOwners.get(skillId);
         if (oldOwner != null && !oldOwner.equals(owner)) {
            this.ownerSkills.remove(oldOwner);
         }

         this.skillOwners.put(skillId, owner);
         this.ownerSkills.put(owner, skillId);
         this.saveNow();
      }
   }

   public synchronized void forceOwnerSkill(UUID owner, ResourceLocation skillId) {
      if (owner != null && skillId != null) {
         if (!this.ownerHasSkill(owner, skillId)) {
            ResourceLocation oldSkill = this.ownerSkills.remove(owner);
            if (oldSkill != null) {
               UUID oldSkillOwner = this.skillOwners.get(oldSkill);
               if (owner.equals(oldSkillOwner)) {
                  this.skillOwners.remove(oldSkill);
               }
            }

            UUID oldOwner = this.skillOwners.remove(skillId);
            if (oldOwner != null) {
               ResourceLocation oldOwnerSkill = this.ownerSkills.get(oldOwner);
               if (skillId.equals(oldOwnerSkill)) {
                  this.ownerSkills.remove(oldOwner);
               }
            }

            this.skillOwners.put(skillId, owner);
            this.ownerSkills.put(owner, skillId);
            this.saveNow();
         }
      }
   }

   public synchronized void removeSkillOwner(ResourceLocation skillId) {
      if (skillId != null) {
         UUID owner = this.skillOwners.remove(skillId);
         if (owner != null) {
            ResourceLocation reverseSkill = this.ownerSkills.get(owner);
            if (skillId.equals(reverseSkill)) {
               this.ownerSkills.remove(owner);
            }

            this.saveNow();
         }
      }
   }

   public synchronized void removeOwner(UUID owner) {
      if (owner != null) {
         ResourceLocation skillId = this.ownerSkills.remove(owner);
         if (skillId != null) {
            UUID savedOwner = this.skillOwners.get(skillId);
            if (owner.equals(savedOwner)) {
               this.skillOwners.remove(skillId);
            }

            this.saveNow();
         }
      }
   }

   public synchronized void removeOwnerSkill(UUID owner, ResourceLocation skillId) {
      if (owner != null && skillId != null) {
         boolean changed = false;
         UUID savedOwner = this.skillOwners.get(skillId);
         if (owner.equals(savedOwner)) {
            this.skillOwners.remove(skillId);
            changed = true;
         }

         ResourceLocation savedSkill = this.ownerSkills.get(owner);
         if (skillId.equals(savedSkill)) {
            this.ownerSkills.remove(owner);
            changed = true;
         }

         if (changed) {
            this.saveNow();
         }
      }
   }

   public synchronized void clear() {
      if (!this.skillOwners.isEmpty() || !this.ownerSkills.isEmpty()) {
         this.skillOwners.clear();
         this.ownerSkills.clear();
         this.saveNow();
      }
   }
}
