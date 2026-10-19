package com.github.wal_bos.moreskills.handler;

import com.github.wal_bos.moreskills.ability.skill.unique.GodlyScientistSkill;
import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.registry.skill.AllSkills;
import dev.architectury.event.EventResult;
import io.github.manasmods.manascore.skill.api.EntityEvents;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.SkillEvents;
import io.github.manasmods.manascore.skill.api.EntityEvents.ProjectileHitEvent;
import io.github.manasmods.manascore.skill.api.SkillEvents.UnlockSkillEvent;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.event.TensuraEntityEvents;
import io.github.manasmods.tensura.registry.item.TensuraMobDropItems;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import net.minecraft.core.Holder;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MaceItem;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent.Finish;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent.Stop;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.Clone;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.ItemCraftedEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;

public final class GodlyScienceSmitheryHandler {
   private static final String ROOT = "tms_godly_science_smithery";
   private static final String ELEMENTAL_ESSENCE = "elemental_essence";
   private static final String DRAGON_ESSENCE = "dragon_essence";
   private static final String ENDER_DRAGON_KILL = "ender_dragon_kill";
   private static final String SOUL_BUFF = "soul_buff";
   private static final String GENESIS_WEAPON = "tms_godly_genesis_weapon";
   private static final String GENESIS_PROJECTILE = "tms_godly_genesis_projectile";
   private static final String GENESIS_PROJECTILE_UNTIL = "genesis_projectile_until";
   private static final ResourceLocation SOUL_BUFF_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "godly_smithery_soul_buff");
   private static boolean initialized;

   private GodlyScienceSmitheryHandler() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(GodlyScienceSmitheryHandler::onItemFinished);
         NeoForge.EVENT_BUS.addListener(GodlyScienceSmitheryHandler::onItemStopped);
         NeoForge.EVENT_BUS.addListener(GodlyScienceSmitheryHandler::onLivingDeath);
         NeoForge.EVENT_BUS.addListener(GodlyScienceSmitheryHandler::onClone);
         NeoForge.EVENT_BUS.addListener(GodlyScienceSmitheryHandler::onLogin);
         NeoForge.EVENT_BUS.addListener(GodlyScienceSmitheryHandler::onCrafted);
         NeoForge.EVENT_BUS.addListener(GodlyScienceSmitheryHandler::onAttackEntity);
         NeoForge.EVENT_BUS.addListener(GodlyScienceSmitheryHandler::onRespawn);
         NeoForge.EVENT_BUS.addListener(GodlyScienceSmitheryHandler::onEntityJoin);
         TensuraEntityEvents.LIVING_POST_DAMAGE.register(GodlyScienceSmitheryHandler::onPostDamage);
         EntityEvents.PROJECTILE_HIT.register((ProjectileHitEvent)(hitResult, projectile, deflection, result) -> {
            if (projectile.getPersistentData().getBoolean("tms_godly_genesis_projectile") && projectile.level() instanceof ServerLevel level) {
               level.getServer().execute(projectile::discard);
            }
         });
         SkillEvents.UNLOCK_SKILL
            .register(
               (UnlockSkillEvent)(instance, entity, message) -> {
                  if (entity instanceof ServerPlayer player
                     && SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)AllSkills.GODLY_SCIENTIST.get()).isPresent()
                     && GodlyScientistSkill.isMagic(instance)) {
                     ResourceLocation skillId = instance.getSkillId();
                     player.server
                        .execute(
                           () -> SkillAPI.getSkillsFrom(player).getSkill(skillId).ifPresent(value -> GodlyScientistSkill.masterMagicInstance(player, value))
                        );
                  }

                  return EventResult.pass();
               }
            );
      }
   }

   public static int getElementalEssenceCount(Player player) {
      return root(player).getInt("elemental_essence");
   }

   public static int getDragonEssenceCount(Player player) {
      return root(player).getInt("dragon_essence");
   }

   public static boolean hasKilledEnderDragon(Player player) {
      return root(player).getBoolean("ender_dragon_kill");
   }

   public static long getSoulBuffCount(Player player) {
      return root(player).getLong("soul_buff");
   }

   public static void markGenesisWeapon(ItemStack stack) {
      CompoundTag tag = ((CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)).copyTag();
      tag.putBoolean("tms_godly_genesis_weapon", true);
      stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
      stack.set(DataComponents.MAX_STACK_SIZE, 1);
      stack.set(DataComponents.MAX_DAMAGE, 1);
      stack.set(DataComponents.DAMAGE, 0);
      stack.remove(DataComponents.UNBREAKABLE);
   }

   public static boolean isGenesisWeapon(ItemStack stack) {
      return !stack.isEmpty() && stack.has(DataComponents.CUSTOM_DATA)
         ? ((CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)).copyTag().getBoolean("tms_godly_genesis_weapon")
         : false;
   }

   public static void applySoulBuff(LivingEntity entity) {
      removeSoulBuff(entity);
      if (entity instanceof Player player) {
         long count = getSoulBuffCount(player);
         if (count > 0L && !SkillAPI.getSkillsFrom(entity).getSkill((ManasSkill)AllSkills.GODLY_SMITHERY.get()).isEmpty()) {
            double amount = count * (Double)MoreSkillsConfigs.GRAND.godlySmithery.soulBuffPerEssence.get();
            BuiltInRegistries.ATTRIBUTE.holders().forEach(holder -> {
               AttributeInstance instance = entity.getAttribute(holder);
               if (instance != null) {
                  instance.addOrReplacePermanentModifier(new AttributeModifier(SOUL_BUFF_ID, amount, Operation.ADD_MULTIPLIED_TOTAL));
               }
            });
         }
      }
   }

   public static void removeSoulBuff(LivingEntity entity) {
      BuiltInRegistries.ATTRIBUTE.holders().forEach(holder -> {
         AttributeInstance instance = entity.getAttribute(holder);
         if (instance != null) {
            instance.removeModifier(SOUL_BUFF_ID);
         }
      });
   }

   private static void onItemFinished(Finish event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         ItemStack consumed = event.getItem();
         if (consumed.is((Item)TensuraMobDropItems.ELEMENTAL_ESSENCE.get())) {
            addPersistentInt(player, "elemental_essence", 1);
         }

         if (consumed.is((Item)TensuraMobDropItems.DRAGON_ESSENCE.get())) {
            addPersistentInt(player, "dragon_essence", 1);
         }

         ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(consumed.getItem());
         if (itemId != null
            && itemId.getPath().contains("essence")
            && SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)AllSkills.GODLY_SMITHERY.get()).isPresent()) {
            addPersistentLong(player, "soul_buff", 1L);
            applySoulBuff(player);
         }
      }
   }

   private static void onItemStopped(Stop event) {
      if (event.getEntity() instanceof ServerPlayer player && isGenesisWeapon(event.getItem()) && isProjectileCapable(event.getItem())) {
         armGenesisProjectile(player);
      }
   }

   private static void onLivingDeath(LivingDeathEvent event) {
      if (event.getEntity() instanceof EnderDragon dragon) {
         if (event.getSource().getEntity() instanceof ServerPlayer player) {
            setPersistentBoolean(player, "ender_dragon_kill", true);
         } else if (dragon.getKillCredit() instanceof ServerPlayer player) {
            setPersistentBoolean(player, "ender_dragon_kill", true);
         }
      }
   }

   private static void onClone(Clone event) {
      CompoundTag oldRoot = root(event.getOriginal()).copy();
      CompoundTag newRoot = root(event.getEntity());
      newRoot.putInt("elemental_essence", oldRoot.getInt("elemental_essence"));
      newRoot.putInt("dragon_essence", oldRoot.getInt("dragon_essence"));
      newRoot.putBoolean("ender_dragon_kill", oldRoot.getBoolean("ender_dragon_kill"));
      newRoot.putLong("soul_buff", event.isWasDeath() ? 0L : oldRoot.getLong("soul_buff"));
      event.getEntity().getPersistentData().put("tms_godly_science_smithery", newRoot);
      removeSoulBuff(event.getEntity());
      if (!event.isWasDeath()) {
         applySoulBuff(event.getEntity());
      }
   }

   private static void onLogin(PlayerLoggedInEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         GodlyScientistSkill.restorePassiveState(player);
         applySoulBuff(player);
      }
   }

   private static void onRespawn(PlayerRespawnEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         GodlyScientistSkill.restorePassiveState(player);
         applySoulBuff(player);
      }
   }

   private static void onCrafted(ItemCraftedEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         if (!SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)AllSkills.GODLY_SMITHERY.get()).isEmpty()) {
            ItemStack crafted = event.getCrafting();
            if (isWeapon(crafted)) {
               addRandomEnchantments(player, crafted, (Integer)MoreSkillsConfigs.GRAND.godlySmithery.randomCraftedEnchantments.get());
               SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)AllSkills.GODLY_SMITHERY.get()).ifPresent(instance -> instance.addMasteryPoint(player));
            }
         }
      }
   }

   private static void onAttackEntity(AttackEntityEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         ItemStack stack = player.getMainHandItem();
         if (isGenesisWeapon(stack)) {
            player.server.execute(() -> deleteGenesisStack(player, stack));
         }
      }
   }

   private static void onPostDamage(LivingEntity target, DamageSource source, float amount) {
      if (source.getEntity() instanceof ServerPlayer player) {
         if (TensuraDamageHelper.isPhysicalAttack(source)) {
            ItemStack mainHand = player.getMainHandItem();
            if (isGenesisWeapon(mainHand)) {
               deleteGenesisStack(player, mainHand);
            } else {
               ItemStack offhand = player.getOffhandItem();
               if (isGenesisWeapon(offhand)) {
                  deleteGenesisStack(player, offhand);
               }
            }
         }
      }
   }

   private static void onEntityJoin(EntityJoinLevelEvent event) {
      if (!event.getLevel().isClientSide() && event.getEntity() instanceof Projectile projectile && projectile.getOwner() instanceof ServerPlayer player) {
         ItemStack var9 = player.getMainHandItem();
         ItemStack offhand = player.getOffhandItem();
         ItemStack genesis = isGenesisWeapon(var9) && isProjectileCapable(var9)
            ? var9
            : (isGenesisWeapon(offhand) && isProjectileCapable(offhand) ? offhand : ItemStack.EMPTY);
         CompoundTag data = root(player);
         boolean armed = data.getLong("genesis_projectile_until") >= player.level().getGameTime();
         if (!genesis.isEmpty() || armed) {
            projectile.getPersistentData().putBoolean("tms_godly_genesis_projectile", true);
            armGenesisProjectile(player);
            if (!genesis.isEmpty()) {
               player.server.execute(() -> deleteGenesisStack(player, genesis));
            }
         }
      }
   }

   private static boolean isProjectileCapable(ItemStack stack) {
      if (!(stack.getItem() instanceof ProjectileWeaponItem) && !(stack.getItem() instanceof TridentItem)) {
         ResourceLocation id = BuiltInRegistries.ITEM.getKey(stack.getItem());
         if (id == null) {
            return false;
         }

         String path = id.getPath();
         String className = stack.getItem().getClass().getSimpleName().toLowerCase(Locale.ROOT);
         return path.contains("bow")
            || path.contains("crossbow")
            || path.contains("gun")
            || path.contains("rifle")
            || path.contains("cannon")
            || path.contains("blaster")
            || path.contains("launcher")
            || path.contains("trident")
            || path.contains("staff")
            || path.contains("wand")
            || className.contains("bow")
            || className.contains("gun")
            || className.contains("rifle")
            || className.contains("projectile")
            || className.contains("staff")
            || className.contains("wand");
      } else {
         return true;
      }
   }

   private static void armGenesisProjectile(ServerPlayer player) {
      CompoundTag data = root(player);
      data.putLong("genesis_projectile_until", player.level().getGameTime() + 1L);
      player.getPersistentData().put("tms_godly_science_smithery", data);
   }

   private static void deleteGenesisStack(ServerPlayer player, ItemStack stack) {
      if (isGenesisWeapon(stack)) {
         stack.shrink(stack.getCount());
         player.getInventory().setChanged();
      }
   }

   private static void addRandomEnchantments(ServerPlayer player, ItemStack stack, int count) {
      ItemEnchantments existing = (ItemEnchantments)stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
      List<Reference<Enchantment>> compatible = new ArrayList<>();
      List<Reference<Enchantment>> fallback = new ArrayList<>();
      player.registryAccess().registryOrThrow(Registries.ENCHANTMENT).holders().forEach(holder -> {
         boolean present = existing.entrySet().stream().anyMatch(entry -> ((Holder)entry.getKey()).equals(holder));
         if (!present) {
            fallback.add((Reference<Enchantment>)holder);
            if (((Enchantment)holder.value()).canEnchant(stack)) {
               compatible.add((Reference<Enchantment>)holder);
            }
         }
      });
      Collections.shuffle(compatible, new Random(player.getRandom().nextLong()));
      Collections.shuffle(fallback, new Random(player.getRandom().nextLong()));
      List<Reference<Enchantment>> selected = new ArrayList<>();

      for (Reference<Enchantment> enchantment : compatible) {
         if (selected.size() >= count) {
            break;
         }

         selected.add(enchantment);
      }

      for (Reference<Enchantment> enchantment : fallback) {
         if (selected.size() >= count) {
            break;
         }

         if (!selected.contains(enchantment)) {
            selected.add(enchantment);
         }
      }

      for (Reference<Enchantment> enchantment : selected) {
         int max = Math.max(1, ((Enchantment)enchantment.value()).getMaxLevel());
         stack.enchant(enchantment, 1 + player.getRandom().nextInt(max));
      }
   }

   public static boolean isWeapon(ItemStack stack) {
      if (stack.isEmpty()) {
         return false;
      }

      if (!(stack.getItem() instanceof SwordItem)
         && !(stack.getItem() instanceof ProjectileWeaponItem)
         && !(stack.getItem() instanceof TridentItem)
         && !(stack.getItem() instanceof MaceItem)) {
         ResourceLocation id = BuiltInRegistries.ITEM.getKey(stack.getItem());
         if (id == null) {
            return false;
         }

         String path = id.getPath().toLowerCase(Locale.ROOT);
         String className = stack.getItem().getClass().getSimpleName().toLowerCase(Locale.ROOT);
         return isExcludedWeaponEntry(path, className) ? false : hasWeaponName(path, className);
      } else {
         return true;
      }
   }

   private static boolean isExcludedWeaponEntry(String path, String className) {
      return path.contains("pottery")
         || path.contains("sherd")
         || path.contains("pattern")
         || path.contains("template")
         || path.contains("spawn_egg")
         || path.contains("music_disc")
         || path.contains("banner")
         || path.contains("pickaxe")
         || path.contains("shovel")
         || path.endsWith("_hoe")
         || path.contains("_tool")
         || className.contains("pickaxe")
         || className.contains("shovel")
         || className.endsWith("hoeitem")
         || className.contains("toolitem");
   }

   private static boolean hasWeaponName(String path, String className) {
      return path.contains("sword")
         || path.contains("blade")
         || path.contains("dagger")
         || path.contains("knife")
         || path.contains("katana")
         || path.contains("rapier")
         || path.contains("saber")
         || path.contains("sabre")
         || path.contains("scimitar")
         || path.contains("spear")
         || path.contains("lance")
         || path.contains("halberd")
         || path.contains("glaive")
         || path.contains("scythe")
         || path.contains("trident")
         || path.contains("chakram")
         || path.contains("boomerang")
         || path.contains("whip")
         || path.contains("gauntlet")
         || path.contains("claw")
         || path.contains("battleaxe")
         || path.contains("battle_axe")
         || path.contains("waraxe")
         || path.contains("war_axe")
         || path.contains("warhammer")
         || path.contains("war_hammer")
         || path.contains("battlehammer")
         || path.contains("battle_hammer")
         || path.contains("crossbow")
         || path.equals("bow")
         || path.endsWith("_bow")
         || path.startsWith("bow_")
         || path.contains("_bow_")
         || path.endsWith("longbow")
         || path.endsWith("shortbow")
         || path.contains("pistol")
         || path.contains("musket")
         || path.contains("rifle")
         || path.contains("blaster")
         || path.contains("launcher")
         || path.contains("cannon")
         || path.contains("firearm")
         || path.equals("gun")
         || path.endsWith("_gun")
         || path.startsWith("gun_")
         || path.contains("_gun_")
         || path.endsWith("_staff")
         || path.endsWith("_wand")
         || path.contains("weapon")
         || className.contains("sword")
         || className.contains("blade")
         || className.contains("dagger")
         || className.contains("knife")
         || className.contains("katana")
         || className.contains("rapier")
         || className.contains("spear")
         || className.contains("lance")
         || className.contains("halberd")
         || className.contains("glaive")
         || className.contains("scythe")
         || className.contains("trident")
         || className.contains("mace")
         || className.contains("weapon")
         || className.endsWith("bowitem")
         || className.endsWith("gunitem")
         || className.endsWith("rifleitem")
         || className.endsWith("pistolitem")
         || className.endsWith("staffitem")
         || className.endsWith("wanditem");
   }

   private static CompoundTag root(Player player) {
      CompoundTag root = player.getPersistentData().getCompound("tms_godly_science_smithery");
      player.getPersistentData().put("tms_godly_science_smithery", root);
      return root;
   }

   private static void addPersistentInt(Player player, String key, int amount) {
      CompoundTag root = root(player);
      long value = (long)root.getInt(key) + amount;
      root.putInt(key, value > 2147483647L ? Integer.MAX_VALUE : Math.max(0, (int)value));
      player.getPersistentData().put("tms_godly_science_smithery", root);
   }

   private static void addPersistentLong(Player player, String key, long amount) {
      CompoundTag root = root(player);
      long current = root.getLong(key);
      long value = amount > 0L && current > Long.MAX_VALUE - amount ? Long.MAX_VALUE : current + amount;
      root.putLong(key, Math.max(0L, value));
      player.getPersistentData().put("tms_godly_science_smithery", root);
   }

   private static void setPersistentLong(Player player, String key, long value) {
      CompoundTag root = root(player);
      root.putLong(key, Math.max(0L, value));
      player.getPersistentData().put("tms_godly_science_smithery", root);
   }

   private static void setPersistentBoolean(Player player, String key, boolean value) {
      CompoundTag root = root(player);
      root.putBoolean(key, value);
      player.getPersistentData().put("tms_godly_science_smithery", root);
   }
}
