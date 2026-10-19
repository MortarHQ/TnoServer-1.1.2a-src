package com.github.wal_bos.moreskills.ability.skill.unique;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.gene.GeneEditorProvider;
import com.github.wal_bos.moreskills.item.GeneticSyringeItem;
import com.github.wal_bos.moreskills.registry.TenaddexItems;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.data.TensuraSkillTags;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

public class GeneEditorSkill extends Skill {
   private static final String ROOT = "tensuramoreskills:gene_editor";
   private static final String ARCHIVE = "Archive";
   private static final String WORKSHOP = "Workshop";

   public GeneEditorSkill() {
      super(SkillType.UNIQUE);
   }

   private static MoreSkillsConfigs.GeneEditorConfig cfg() {
      return MoreSkillsConfigs.GRAND.geneEditor;
   }

   @NotNull
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/skill/unique/gene_editor.png");
   }

   public double getDefaultAcquiringMagiculeCost() {
      return (Double)cfg().acquirementMagiculeCost.get();
   }

   public int getModes(ManasSkillInstance instance) {
      return 2;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      return mode == 0 ? 1 : 0;
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "gene_editor.extract_sample";
         case 1 -> "gene_editor.gene_archive";
         default -> super.getModeId(instance, mode);
      };
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return mode == 0 ? (Double)cfg().extractMagiculeCost.get() : (Double)cfg().archiveMagiculeCost.get();
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (entity instanceof ServerPlayer player) {
         if (mode == 1) {
            GeneEditorProvider.open(player);
            instance.addMasteryPoint(player);
         } else if (player.isCrouching()) {
            giveEmptySyringe(player);
            instance.setCoolDown((Integer)cfg().emptySyringeCooldownTicks.get(), mode);
            instance.addMasteryPoint(player);
         } else if (!instance.onCoolDown(mode)) {
            if (!EnergyHelper.isOutOfEnergy(player, instance, mode)) {
               LivingEntity target = ObjectSelectionHelper.getTargetingEntity(player, (Double)cfg().extractRange.get(), false, true);
               if (target != null && target.isAlive() && target != player) {
                  ItemStack syringe = extractSample(player, target, instance.isMastered(player));
                  if (syringe.isEmpty()) {
                     player.displayClientMessage(
                        Component.translatable("tensuramoreskills.skill.gene_editor.no_valid_genes").withStyle(ChatFormatting.RED), true
                     );
                     instance.setCoolDown((Integer)cfg().failedExtractCooldownTicks.get(), mode);
                  } else {
                     if (!player.getInventory().add(syringe)) {
                        player.drop(syringe, false);
                     }

                     instance.setCoolDown((Integer)cfg().extractCooldownTicks.get(), mode);
                     instance.addMasteryPoint(player);
                     player.swing(InteractionHand.MAIN_HAND, true);
                     player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.PLAYERS, 0.8F, 1.25F);
                     player.displayClientMessage(
                        Component.translatable("tensuramoreskills.skill.gene_editor.extracted", new Object[]{target.getDisplayName()})
                           .withStyle(ChatFormatting.GREEN),
                        true
                     );
                  }
               } else {
                  player.displayClientMessage(Component.translatable("tensuramoreskills.skill.gene_editor.no_target").withStyle(ChatFormatting.RED), true);
               }
            }
         }
      }
   }

   public static ItemStack extractSample(ServerPlayer player, LivingEntity target, boolean mastered) {
      int picks = mastered ? (Integer)cfg().masteredExtractSkillCount.get() : (Integer)cfg().normalExtractSkillCount.get();
      List<ResourceLocation> selected = pickRandomSkills(target, picks);
      double ep = EnergyHelper.getMaxEP(target) * (Double)cfg().copiedEpMultiplier.get();
      double mp = EnergyHelper.getMaxMagicule(target) * (Double)cfg().copiedMpMultiplier.get();
      double hp = target.getMaxHealth() * (Double)cfg().copiedHpMultiplier.get();
      double shp = target.getAttributeValue(TensuraAttributes.MAX_SPIRITUAL_HEALTH) * (Double)cfg().copiedShpMultiplier.get();
      if (selected.isEmpty() && ep <= 0.0 && mp <= 0.0 && hp <= 0.0 && shp <= 0.0) {
         return ItemStack.EMPTY;
      }

      ItemStack stack = new ItemStack((ItemLike)TenaddexItems.GENETIC_SYRINGE.get());
      GeneticSyringeItem.writeSample(stack, selected, ep, mp, hp, shp);
      return stack;
   }

   private static List<ResourceLocation> pickRandomSkills(LivingEntity target, int amount) {
      List<ResourceLocation> pool = new ArrayList<>();

      for (ManasSkillInstance inst : SkillAPI.getSkillsFrom(target).getLearnedSkills()) {
         ManasSkill skill = inst.getSkill();
         if (skill != null && !(inst.getMastery() < 0.0)) {
            ResourceLocation id = skill.getRegistryName();
            if (id != null && (!(Boolean)cfg().blockNoPlunderingSkills.get() || !inst.is(TensuraSkillTags.NO_PLUNDERING))) {
               if (skill instanceof Skill tensuraSkill) {
                  SkillType type = tensuraSkill.getType();
                  if (!(Boolean)cfg().allowUltimateSkillCopies.get() && type == SkillType.ULTIMATE
                     || !(Boolean)cfg().allowUniqueSkillCopies.get() && type == SkillType.UNIQUE) {
                     continue;
                  }
               }

               pool.add(id);
            }
         }
      }

      pool.sort(Comparator.comparing(ResourceLocation::toString));
      List<ResourceLocation> selected = new ArrayList<>();
      int count = Math.min(Math.max(0, amount), pool.size());

      for (int i = 0; i < count; i++) {
         selected.add(pool.remove(target.getRandom().nextInt(pool.size())));
      }

      selected.sort(Comparator.comparing(ResourceLocation::toString));
      return selected;
   }

   private static void giveEmptySyringe(ServerPlayer player) {
      ItemStack stack = new ItemStack((ItemLike)TenaddexItems.GENETIC_SYRINGE.get());
      GeneticSyringeItem.writeEmpty(stack);
      if (!player.getInventory().add(stack)) {
         player.drop(stack, false);
      }

      player.displayClientMessage(Component.translatable("tensuramoreskills.skill.gene_editor.created_empty_syringe").withStyle(ChatFormatting.AQUA), true);
      player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 0.7F, 1.4F);
   }

   public static boolean recordSampleSyringe(ServerPlayer player, ItemStack stack) {
      if (stack.getItem() instanceof GeneticSyringeItem && GeneticSyringeItem.isSample(stack)) {
         CompoundTag data = GeneticSyringeItem.getSyringe(stack);
         List<ResourceLocation> skills = GeneticSyringeItem.readSkills(data);
         double ep = data.getDouble("EP");
         double mp = data.getDouble("MP");
         double hp = data.getDouble("HP");
         double shp = data.getDouble("SHP");
         if (skills.isEmpty() && ep <= 0.0 && mp <= 0.0 && hp <= 0.0 && shp <= 0.0) {
            return false;
         }

         storeGenes(player, skills, ep, mp, hp, shp);
         GeneticSyringeItem.writeEmpty(stack);
         player.displayClientMessage(Component.translatable("tensuramoreskills.skill.gene_editor.sample_recorded").withStyle(ChatFormatting.GREEN), true);
         player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 0.6F, 1.7F);
         return true;
      } else {
         return false;
      }
   }

   public static void storeGenes(ServerPlayer player, List<ResourceLocation> skills, double ep, double mp, double hp, double shp) {
      CompoundTag root = getRoot(player);
      CompoundTag archive = root.getCompound("Archive");

      for (ResourceLocation skill : skills) {
         addArchiveAmount(archive, "skill:" + skill, 1.0);
      }

      if (ep > 0.0) {
         addArchiveAmount(archive, "stat:ep", ep);
      }

      if (mp > 0.0) {
         addArchiveAmount(archive, "stat:mp", mp);
      }

      if (hp > 0.0) {
         addArchiveAmount(archive, "stat:hp", hp);
      }

      if (shp > 0.0) {
         addArchiveAmount(archive, "stat:shp", shp);
      }

      root.put("Archive", archive);
      saveRoot(player, root);
   }

   private static void addArchiveAmount(CompoundTag archive, String key, double amount) {
      archive.putDouble(key, archive.getDouble(key) + amount);
   }

   public static void fillStorageLists(ServerPlayer player, List<String> keys, List<String> counts, List<String> selectedAmounts) {
      CompoundTag root = getRoot(player);
      CompoundTag archive = root.getCompound("Archive");
      CompoundTag workshop = root.getCompound("Workshop");

      for (String key : new LinkedHashSet(archive.getAllKeys().stream().sorted().toList())) {
         double value = archive.getDouble(key);
         if (!(value <= 0.0)) {
            keys.add(key);
            counts.add(Double.toString(value));
            selectedAmounts.add(Double.toString(workshop.getDouble(key)));
         }
      }

      for (String key : workshop.getAllKeys().stream().sorted().toList()) {
         if (!keys.contains(key) && workshop.getDouble(key) > 0.0) {
            keys.add(key);
            counts.add("0");
            selectedAmounts.add(Double.toString(workshop.getDouble(key)));
         }
      }
   }

   public static boolean addWorkshopKey(ServerPlayer player, String key, double requested) {
      if (key != null && !key.isEmpty() && !(requested <= 0.0) && Double.isFinite(requested)) {
         CompoundTag root = getRoot(player);
         CompoundTag archive = root.getCompound("Archive");
         CompoundTag workshop = root.getCompound("Workshop");
         double available = archive.getDouble(key);
         if (available <= 0.0) {
            return false;
         }

         double add = key.startsWith("skill:") ? 1.0 : requested;
         if (!key.startsWith("skill:") && requested >= 1.0737418235E9) {
            add = available;
         }

         add = Math.min(available, Math.max(1.0, Math.floor(add)));
         if (add <= 0.0) {
            return false;
         }

         double left = available - add;
         if (left <= 0.0) {
            archive.remove(key);
         } else {
            archive.putDouble(key, left);
         }

         workshop.putDouble(key, workshop.getDouble(key) + add);
         root.put("Archive", archive);
         root.put("Workshop", workshop);
         saveRoot(player, root);
         return true;
      } else {
         return false;
      }
   }

   public static boolean removeWorkshopKey(ServerPlayer player, String key, double requested) {
      if (key != null && !key.isEmpty() && !(requested <= 0.0) && Double.isFinite(requested)) {
         CompoundTag root = getRoot(player);
         CompoundTag archive = root.getCompound("Archive");
         CompoundTag workshop = root.getCompound("Workshop");
         double current = workshop.getDouble(key);
         if (current <= 0.0) {
            return false;
         }

         double remove = key.startsWith("skill:") ? 1.0 : requested;
         if (!key.startsWith("skill:") && requested >= 1.0737418235E9) {
            remove = current;
         }

         remove = Math.min(current, Math.max(1.0, Math.floor(remove)));
         if (remove <= 0.0) {
            return false;
         }

         addArchiveAmount(archive, key, remove);
         double left = current - remove;
         if (left <= 0.0) {
            workshop.remove(key);
         } else {
            workshop.putDouble(key, left);
         }

         root.put("Archive", archive);
         root.put("Workshop", workshop);
         saveRoot(player, root);
         return true;
      } else {
         return false;
      }
   }

   public static void clearWorkshop(ServerPlayer player) {
      CompoundTag root = getRoot(player);
      CompoundTag archive = root.getCompound("Archive");
      CompoundTag workshop = root.getCompound("Workshop");

      for (String key : new ArrayList(workshop.getAllKeys())) {
         double amount = workshop.getDouble(key);
         if (amount > 0.0) {
            addArchiveAmount(archive, key, amount);
         }
      }

      root.put("Archive", archive);
      root.remove("Workshop");
      saveRoot(player, root);
   }

   public static boolean writeWorkshopToSyringe(ServerPlayer player, ItemStack syringe) {
      if (!(syringe.getItem() instanceof GeneticSyringeItem)) {
         player.displayClientMessage(Component.translatable("tensuramoreskills.skill.gene_editor.no_empty_syringe").withStyle(ChatFormatting.RED), true);
         return false;
      }

      recordSampleSyringe(player, syringe);
      if (!GeneticSyringeItem.isEmpty(syringe)) {
         player.displayClientMessage(Component.translatable("tensuramoreskills.skill.gene_editor.no_empty_syringe").withStyle(ChatFormatting.RED), true);
         return false;
      }

      CompoundTag root = getRoot(player);
      CompoundTag workshop = root.getCompound("Workshop");
      if (workshop.isEmpty()) {
         return false;
      }

      List<ResourceLocation> skills = new ArrayList<>();
      double ep = 0.0;
      double mp = 0.0;
      double hp = 0.0;
      double shp = 0.0;

      for (String key : new ArrayList(workshop.getAllKeys())) {
         double amount = workshop.getDouble(key);
         if (!(amount <= 0.0)) {
            if (key.startsWith("skill:")) {
               ResourceLocation id = ResourceLocation.parse(key.substring("skill:".length()));
               int count = (int)Math.min(2.147483647E9, Math.floor(amount));

               for (int i = 0; i < count; i++) {
                  skills.add(id);
               }
            } else if (key.equals("stat:ep")) {
               ep += amount;
            } else if (key.equals("stat:mp")) {
               mp += amount;
            } else if (key.equals("stat:hp")) {
               hp += amount;
            } else if (key.equals("stat:shp")) {
               shp += amount;
            }
         }
      }

      if (skills.isEmpty() && ep <= 0.0 && mp <= 0.0 && hp <= 0.0 && shp <= 0.0) {
         return false;
      }

      GeneticSyringeItem.writeFull(syringe, skills, ep, mp, hp, shp);
      root.remove("Workshop");
      saveRoot(player, root);
      player.displayClientMessage(Component.translatable("tensuramoreskills.skill.gene_editor.syringe_written").withStyle(ChatFormatting.GREEN), true);
      player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BREWING_STAND_BREW, SoundSource.PLAYERS, 0.8F, 1.3F);
      return true;
   }

   public static double getWorkshopAmount(ServerPlayer player, String key) {
      return getRoot(player).getCompound("Workshop").getDouble(key);
   }

   public static List<String> getWorkshopKeys(ServerPlayer player) {
      return getRoot(player).getCompound("Workshop").getAllKeys().stream().sorted().toList();
   }

   public static String getDisplayName(String key) {
      if (key.startsWith("skill:")) {
         ResourceLocation id = ResourceLocation.parse(key.substring("skill:".length()));
         ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(id);
         return skill != null && skill.getName() != null ? skill.getName().getString() : id.toString();
      }

      return switch (key) {
         case "stat:ep" -> "EP";
         case "stat:mp" -> "MP";
         case "stat:hp" -> "HP";
         case "stat:shp" -> "SHP";
         default -> key;
      };
   }

   public static String formatAmount(String key, double amount) {
      if (key.startsWith("skill:")) {
         return String.valueOf(Math.max(0, (int)Math.floor(amount)));
      } else if (amount >= 1.0E12) {
         return String.format("%.2fT", amount / 1.0E12);
      } else if (amount >= 1.0E9) {
         return String.format("%.2fB", amount / 1.0E9);
      } else if (amount >= 1000000.0) {
         return String.format("%.2fM", amount / 1000000.0);
      } else {
         return amount >= 1000.0 ? String.format("%.2fK", amount / 1000.0) : String.valueOf(Math.max(0L, (long)Math.floor(amount)));
      }
   }

   public static void writeStorageSnapshot(ServerPlayer player, RegistryFriendlyByteBuf buf) {
      List<String> keys = new ArrayList<>();
      List<String> counts = new ArrayList<>();
      List<String> selectedAmounts = new ArrayList<>();
      fillStorageLists(player, keys, counts, selectedAmounts);
      buf.writeVarInt(keys.size());

      for (int i = 0; i < keys.size(); i++) {
         buf.writeUtf(keys.get(i));
         buf.writeUtf(counts.get(i));
         buf.writeUtf(selectedAmounts.get(i));
      }

      List<String> ws = getWorkshopKeys(player);
      buf.writeVarInt(ws.size());

      for (String key : ws) {
         buf.writeUtf(key);
         buf.writeUtf(Double.toString(getWorkshopAmount(player, key)));
      }
   }

   private static CompoundTag getRoot(Player player) {
      return player.getPersistentData().getCompound("tensuramoreskills:gene_editor");
   }

   private static void saveRoot(Player player, CompoundTag root) {
      player.getPersistentData().put("tensuramoreskills:gene_editor", root);
   }
}
