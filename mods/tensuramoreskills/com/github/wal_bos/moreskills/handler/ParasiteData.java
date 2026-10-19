package com.github.wal_bos.moreskills.handler;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public final class ParasiteData {
   public static final String ROOT = "TensuraMoreSkillsParasite";
   private static final String FORBIDDEN = "ForbiddenHosts";
   private static final String BODY_SKILLS = "BodySkills";
   private static final String TEMP_CONTROL_SKILLS = "TempControlSkills";
   private static final String BODY_STATS = "BodyStats";
   private static final String TEMP_ORIGINAL_BODY = "TempOriginalBody";

   private ParasiteData() {
   }

   private static MoreSkillsConfigs.ParasiteConfig cfg() {
      return MoreSkillsConfigs.GRAND.parasite;
   }

   public static CompoundTag root(LivingEntity entity) {
      CompoundTag persistent = entity.getPersistentData();
      if (!persistent.contains("TensuraMoreSkillsParasite")) {
         persistent.put("TensuraMoreSkillsParasite", new CompoundTag());
      }

      return persistent.getCompound("TensuraMoreSkillsParasite");
   }

   public static void save(LivingEntity entity, CompoundTag root) {
      entity.getPersistentData().put("TensuraMoreSkillsParasite", root);
   }

   public static boolean hasAnyData(LivingEntity entity) {
      return entity.getPersistentData().contains("TensuraMoreSkillsParasite") && !entity.getPersistentData().getCompound("TensuraMoreSkillsParasite").isEmpty();
   }

   public static boolean hasBond(LivingEntity entity) {
      return root(entity).hasUUID("Parasite") || root(entity).hasUUID("Host");
   }

   public static boolean isHost(LivingEntity entity) {
      return root(entity).hasUUID("Parasite");
   }

   public static boolean isParasite(LivingEntity entity) {
      return root(entity).hasUUID("Host");
   }

   public static UUID getParasite(LivingEntity host) {
      CompoundTag root = root(host);
      return root.hasUUID("Parasite") ? root.getUUID("Parasite") : null;
   }

   public static UUID getHost(LivingEntity parasite) {
      CompoundTag root = root(parasite);
      return root.hasUUID("Host") ? root.getUUID("Host") : null;
   }

   public static int getPlan(LivingEntity entity) {
      return root(entity).getInt("Plan");
   }

   public static int getRelationship(LivingEntity entity) {
      return (int)Math.floor(getRelationshipDouble(entity));
   }

   public static double getRelationshipDouble(LivingEntity entity) {
      CompoundTag root = root(entity);
      return root.contains("RelationshipDouble") ? root.getDouble("RelationshipDouble") : root.getInt("Relationship");
   }

   public static int getTakeover(LivingEntity entity) {
      return root(entity).getInt("Takeover");
   }

   public static boolean parasiteControlsHost(LivingEntity entity) {
      return root(entity).getBoolean("ParasiteControl");
   }

   public static boolean tempControlActive(LivingEntity entity) {
      return root(entity).getBoolean("TempControl");
   }

   public static boolean npcControlActive(LivingEntity entity) {
      return root(entity).getBoolean("NpcControl");
   }

   public static void setParasiteControl(LivingEntity entity, boolean value) {
      CompoundTag root = root(entity);
      root.putBoolean("ParasiteControl", value);
      root.putBoolean("TempControl", value);
      if (!value) {
         root.putBoolean("NpcControl", false);
      }

      save(entity, root);
   }

   public static void setNpcControl(LivingEntity entity, boolean value) {
      CompoundTag root = root(entity);
      root.putBoolean("ParasiteControl", value);
      root.putBoolean("TempControl", value);
      root.putBoolean("NpcControl", value);
      save(entity, root);
   }

   public static boolean npcRandomEnabled(LivingEntity entity) {
      CompoundTag root = root(entity);
      return !root.contains("NpcRandom") || root.getBoolean("NpcRandom");
   }

   public static void setNpcRandomEnabled(LivingEntity entity, boolean value) {
      CompoundTag root = root(entity);
      root.putBoolean("NpcRandom", value);
      save(entity, root);
   }

   public static long getNextNpcToggle(LivingEntity entity) {
      return root(entity).getLong("NextNpcToggle");
   }

   public static void setNextNpcToggle(LivingEntity entity, long value) {
      CompoundTag root = root(entity);
      root.putLong("NextNpcToggle", Math.max(0L, value));
      save(entity, root);
   }

   public static long getReattachAfter(LivingEntity entity) {
      return root(entity).getLong("ReattachAfter");
   }

   public static void setReattachAfter(LivingEntity entity, long value) {
      CompoundTag root = root(entity);
      if (value <= 0L) {
         root.remove("ReattachAfter");
      } else {
         root.putLong("ReattachAfter", value);
      }

      save(entity, root);
   }

   public static void setRelationship(LivingEntity entity, int value) {
      setRelationship(entity, (double)value);
   }

   public static void setRelationship(LivingEntity entity, double value) {
      CompoundTag root = root(entity);
      double clamped = Math.max(0.0, Math.min(((Integer)cfg().maxRelationship.get()).intValue(), value));
      root.putDouble("RelationshipDouble", clamped);
      root.putInt("Relationship", (int)Math.floor(clamped));
      save(entity, root);
   }

   public static void addRelationship(LivingEntity entity, int value) {
      addRelationship(entity, (double)value);
   }

   public static void addRelationship(LivingEntity entity, double value) {
      setRelationship(entity, getRelationshipDouble(entity) + value);
   }

   public static void addTakeover(LivingEntity entity, int value) {
      CompoundTag root = root(entity);
      int maximum = Math.max(1, (Integer)cfg().takeoverRequiredPoints.get());
      root.putInt("Takeover", Math.max(0, Math.min(maximum, root.getInt("Takeover") + value)));
      save(entity, root);
   }

   public static void createBond(LivingEntity parasite, LivingEntity host, int plan) {
      CompoundTag p = root(parasite);
      CompoundTag h = root(host);
      p.putUUID("Host", host.getUUID());
      p.putInt("Plan", plan);
      p.putInt("Relationship", 0);
      p.putDouble("RelationshipDouble", 0.0);
      p.putInt("Takeover", 0);
      p.putBoolean("ParasiteControl", false);
      p.putBoolean("TempControl", false);
      p.putBoolean("NpcControl", false);
      h.putUUID("Parasite", parasite.getUUID());
      h.putInt("Plan", plan);
      h.putInt("Relationship", 0);
      h.putDouble("RelationshipDouble", 0.0);
      h.putInt("Takeover", 0);
      h.putBoolean("ParasiteControl", false);
      h.putBoolean("TempControl", false);
      h.putBoolean("NpcControl", false);
      if (!(host instanceof Player)) {
         h.putBoolean("NpcRandom", true);
      }

      save(parasite, p);
      save(host, h);
   }

   public static void mirror(LivingEntity from, LivingEntity to) {
      CompoundTag a = root(from);
      CompoundTag b = root(to);
      b.putInt("Plan", a.getInt("Plan"));
      b.putInt("Relationship", a.getInt("Relationship"));
      b.putDouble("RelationshipDouble", a.contains("RelationshipDouble") ? a.getDouble("RelationshipDouble") : a.getInt("Relationship"));
      b.putInt("Takeover", a.getInt("Takeover"));
      b.putBoolean("ParasiteControl", a.getBoolean("ParasiteControl"));
      b.putBoolean("TempControl", a.getBoolean("TempControl"));
      b.putBoolean("NpcControl", a.getBoolean("NpcControl"));
      save(to, b);
   }

   public static void clearBond(LivingEntity parasite, LivingEntity host) {
      if (parasite != null) {
         CompoundTag p = root(parasite);
         p.remove("Host");
         p.remove("Plan");
         p.remove("Takeover");
         p.remove("ParasiteControl");
         p.remove("TempControl");
         p.remove("NpcControl");
         p.remove("ReattachAfter");
         p.remove("TempControlSkills");
         save(parasite, p);
      }

      if (host != null) {
         CompoundTag h = root(host);
         h.remove("Parasite");
         h.remove("Plan");
         h.remove("Takeover");
         h.remove("ParasiteControl");
         h.remove("TempControl");
         h.remove("NpcControl");
         h.remove("NpcRandom");
         h.remove("NextNpcToggle");
         h.remove("LastPassiveDay");
         h.remove("LastSymbiosisInterval");
         save(host, h);
      }
   }

   public static boolean isForbidden(LivingEntity parasite, UUID host) {
      ListTag list = root(parasite).getList("ForbiddenHosts", 8);

      for (int i = 0; i < list.size(); i++) {
         if (list.getString(i).equals(host.toString())) {
            return true;
         }
      }

      return false;
   }

   public static void addForbidden(LivingEntity parasite, UUID host) {
      if (host != null) {
         CompoundTag root = root(parasite);
         ListTag list = root.getList("ForbiddenHosts", 8);
         String id = host.toString();

         for (int i = 0; i < list.size(); i++) {
            if (list.getString(i).equals(id)) {
               return;
            }
         }

         list.add(StringTag.valueOf(id));
         root.put("ForbiddenHosts", list);
         save(parasite, root);
      }
   }

   public static void setBodySkills(LivingEntity entity, List<ResourceLocation> skills) {
      CompoundTag root = root(entity);
      ListTag list = new ListTag();

      for (ResourceLocation id : skills) {
         if (id != null) {
            list.add(StringTag.valueOf(id.toString()));
         }
      }

      root.put("BodySkills", list);
      save(entity, root);
   }

   public static List<ResourceLocation> getBodySkills(LivingEntity entity) {
      List<ResourceLocation> result = new ArrayList<>();
      ListTag list = root(entity).getList("BodySkills", 8);

      for (int i = 0; i < list.size(); i++) {
         result.add(ResourceLocation.parse(list.getString(i)));
      }

      return result;
   }

   public static void clearBodySkills(LivingEntity entity) {
      CompoundTag root = root(entity);
      root.remove("BodySkills");
      save(entity, root);
   }

   public static boolean hasBodySkills(LivingEntity entity) {
      return !getBodySkills(entity).isEmpty();
   }

   public static void setBodyStats(LivingEntity entity, CompoundTag stats) {
      CompoundTag root = root(entity);
      root.put("BodyStats", stats.copy());
      save(entity, root);
   }

   public static CompoundTag getBodyStats(LivingEntity entity) {
      return root(entity).getCompound("BodyStats");
   }

   public static boolean hasBodyStats(LivingEntity entity) {
      return !getBodyStats(entity).isEmpty();
   }

   public static void clearBodyStats(LivingEntity entity) {
      CompoundTag root = root(entity);
      root.remove("BodyStats");
      save(entity, root);
   }

   public static void setTempControlSkills(LivingEntity entity, List<ResourceLocation> skills) {
      CompoundTag root = root(entity);
      ListTag list = new ListTag();

      for (ResourceLocation id : skills) {
         if (id != null) {
            list.add(StringTag.valueOf(id.toString()));
         }
      }

      root.put("TempControlSkills", list);
      save(entity, root);
   }

   public static List<ResourceLocation> getTempControlSkills(LivingEntity entity) {
      List<ResourceLocation> result = new ArrayList<>();
      ListTag list = root(entity).getList("TempControlSkills", 8);

      for (int i = 0; i < list.size(); i++) {
         result.add(ResourceLocation.parse(list.getString(i)));
      }

      return result;
   }

   public static void clearTempControlSkills(LivingEntity entity) {
      CompoundTag root = root(entity);
      root.remove("TempControlSkills");
      save(entity, root);
   }

   public static void setTempOriginalBody(LivingEntity entity, CompoundTag body) {
      CompoundTag root = root(entity);
      root.put("TempOriginalBody", body.copy());
      save(entity, root);
   }

   public static CompoundTag getTempOriginalBody(LivingEntity entity) {
      return root(entity).getCompound("TempOriginalBody");
   }

   public static boolean hasTempOriginalBody(LivingEntity entity) {
      return !getTempOriginalBody(entity).isEmpty();
   }

   public static void clearTempOriginalBody(LivingEntity entity) {
      CompoundTag root = root(entity);
      root.remove("TempOriginalBody");
      save(entity, root);
   }
}
