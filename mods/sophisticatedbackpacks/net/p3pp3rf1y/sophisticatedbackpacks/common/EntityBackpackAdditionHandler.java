package net.p3pp3rf1y.sophisticatedbackpacks.common;

import com.google.common.primitives.Ints;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent.Post;
import net.p3pp3rf1y.sophisticatedbackpacks.Config;
import net.p3pp3rf1y.sophisticatedbackpacks.SophisticatedBackpacks;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackStorage;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.IBackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.init.ModCoreDataComponents;
import net.p3pp3rf1y.sophisticatedcore.upgrades.jukebox.DiscHandlerRegistry;
import net.p3pp3rf1y.sophisticatedcore.upgrades.jukebox.JukeboxUpgradeItem;
import net.p3pp3rf1y.sophisticatedcore.upgrades.jukebox.JukeboxUpgradeWrapper;
import net.p3pp3rf1y.sophisticatedcore.upgrades.jukebox.VanillaDiscHandler;
import net.p3pp3rf1y.sophisticatedcore.util.InventoryHelper;
import net.p3pp3rf1y.sophisticatedcore.util.RandHelper;
import net.p3pp3rf1y.sophisticatedcore.util.WeightedElement;

public class EntityBackpackAdditionHandler {
   private static final int MAX_DIFFICULTY = 3;
   private static final float MAX_LOCAL_DIFFICULTY = 6.75F;
   private static final String SPAWNED_WITH_BACKPACK = "spawnedWithBackpack";
   private static final String SPAWNED_WITH_JUKEBOX_UPGRADE = "sophisticatedbackpacks:jukebox";
   private static final List<WeightedElement<Item>> HELMET_CHANCES = List.of(
      new WeightedElement(1, Items.NETHERITE_HELMET),
      new WeightedElement(3, Items.DIAMOND_HELMET),
      new WeightedElement(9, Items.GOLDEN_HELMET),
      new WeightedElement(27, Items.IRON_HELMET),
      new WeightedElement(81, Items.LEATHER_HELMET)
   );
   private static final List<WeightedElement<Item>> LEGGINGS_CHANCES = List.of(
      new WeightedElement(1, Items.NETHERITE_LEGGINGS),
      new WeightedElement(3, Items.DIAMOND_LEGGINGS),
      new WeightedElement(9, Items.GOLDEN_LEGGINGS),
      new WeightedElement(27, Items.IRON_LEGGINGS),
      new WeightedElement(81, Items.LEATHER_LEGGINGS)
   );
   private static final List<WeightedElement<Item>> BOOTS_CHANCES = List.of(
      new WeightedElement(1, Items.NETHERITE_BOOTS),
      new WeightedElement(3, Items.DIAMOND_BOOTS),
      new WeightedElement(9, Items.GOLDEN_BOOTS),
      new WeightedElement(27, Items.IRON_BOOTS),
      new WeightedElement(81, Items.LEATHER_BOOTS)
   );
   private static final Map<Item, Float> dropChanceMultiplier = Map.of(
      (Item)ModItems.BACKPACK.get(),
      1.0F,
      (Item)ModItems.COPPER_BACKPACK.get(),
      1.25F,
      (Item)ModItems.IRON_BACKPACK.get(),
      1.5F,
      (Item)ModItems.GOLD_BACKPACK.get(),
      3.0F,
      (Item)ModItems.DIAMOND_BACKPACK.get(),
      4.5F,
      (Item)ModItems.NETHERITE_BACKPACK.get(),
      6.0F
   );
   private static final List<WeightedElement<EntityBackpackAdditionHandler.BackpackAddition>> BACKPACK_CHANCES = List.of(
      new WeightedElement(
         Config.SERVER.entityBackpackAdditions.leatherWeight.getAsInt(),
         new EntityBackpackAdditionHandler.BackpackAddition(
            (Item)ModItems.BACKPACK.get(), 0, HELMET_CHANCES.subList(3, 5), LEGGINGS_CHANCES.subList(3, 5), BOOTS_CHANCES.subList(3, 5)
         )
      ),
      new WeightedElement(
         Config.SERVER.entityBackpackAdditions.copperWeight.getAsInt(),
         new EntityBackpackAdditionHandler.BackpackAddition(
            (Item)ModItems.COPPER_BACKPACK.get(), 1, HELMET_CHANCES.subList(2, 4), LEGGINGS_CHANCES.subList(3, 5), BOOTS_CHANCES.subList(3, 5)
         )
      ),
      new WeightedElement(
         Config.SERVER.entityBackpackAdditions.ironWeight.getAsInt(),
         new EntityBackpackAdditionHandler.BackpackAddition(
            (Item)ModItems.IRON_BACKPACK.get(), 1, HELMET_CHANCES.subList(2, 4), LEGGINGS_CHANCES.subList(2, 4), BOOTS_CHANCES.subList(2, 4)
         )
      ),
      new WeightedElement(
         Config.SERVER.entityBackpackAdditions.goldWeight.getAsInt(),
         new EntityBackpackAdditionHandler.BackpackAddition(
            (Item)ModItems.GOLD_BACKPACK.get(), 2, HELMET_CHANCES.subList(1, 3), LEGGINGS_CHANCES.subList(1, 3), BOOTS_CHANCES.subList(1, 3)
         )
      ),
      new WeightedElement(
         Config.SERVER.entityBackpackAdditions.diamondWeight.getAsInt(),
         new EntityBackpackAdditionHandler.BackpackAddition(
            (Item)ModItems.DIAMOND_BACKPACK.get(), 3, HELMET_CHANCES.subList(0, 2), LEGGINGS_CHANCES.subList(0, 2), BOOTS_CHANCES.subList(0, 2)
         )
      ),
      new WeightedElement(
         Config.SERVER.entityBackpackAdditions.netheriteWeight.getAsInt(),
         new EntityBackpackAdditionHandler.BackpackAddition(
            (Item)ModItems.NETHERITE_BACKPACK.get(), 4, HELMET_CHANCES.subList(0, 1), LEGGINGS_CHANCES.subList(0, 1), BOOTS_CHANCES.subList(0, 1)
         )
      )
   );
   private static final Map<Integer, List<WeightedElement<EntityBackpackAdditionHandler.BackpackAddition>>> DIFFICULTY_BACKPACK_CHANCES = Map.of(
      0,
      BACKPACK_CHANCES,
      1,
      BACKPACK_CHANCES.subList(Config.SERVER.entityBackpackAdditions.minBackpackTierMidDifficulty.getAsInt(), 6),
      2,
      BACKPACK_CHANCES.subList(Config.SERVER.entityBackpackAdditions.minBackpackTierHighDifficulty.getAsInt(), 6)
   );
   private static final List<EntityBackpackAdditionHandler.ApplicableEffect> APPLICABLE_EFFECTS = List.of(
      new EntityBackpackAdditionHandler.ApplicableEffect(List.of(MobEffects.DAMAGE_RESISTANCE, MobEffects.REGENERATION), 1),
      new EntityBackpackAdditionHandler.ApplicableEffect(MobEffects.FIRE_RESISTANCE),
      new EntityBackpackAdditionHandler.ApplicableEffect(MobEffects.ABSORPTION),
      new EntityBackpackAdditionHandler.ApplicableEffect(MobEffects.HEALTH_BOOST),
      new EntityBackpackAdditionHandler.ApplicableEffect(MobEffects.MOVEMENT_SPEED),
      new EntityBackpackAdditionHandler.ApplicableEffect(MobEffects.DAMAGE_BOOST)
   );

   private EntityBackpackAdditionHandler() {
   }

   static void addBackpack(Monster monster, LevelAccessor level) {
      RandomSource rnd = level.getRandom();
      if (Config.SERVER.entityBackpackAdditions.canWearBackpack(monster.getType())
         && rnd.nextInt((int)(1.0 / (Double)Config.SERVER.entityBackpackAdditions.chance.get())) == 0
         && !(monster instanceof Raider raider && raider.getCurrentRaid() != null)) {
         int difficultyIndex = 0;
         if (Config.SERVER.entityBackpackAdditions.localDifficultyEffectsBackpackSpawns.getAsBoolean()) {
            float localDifficulty = level.getCurrentDifficultyAt(monster.blockPosition()).getEffectiveDifficulty();
            difficultyIndex = Ints.constrainToRange(
               (int)Math.floor(DIFFICULTY_BACKPACK_CHANCES.size() / 6.75F * localDifficulty - 0.1F), 0, DIFFICULTY_BACKPACK_CHANCES.size() - 1
            );
         }

         RandHelper.getRandomWeightedElement(rnd, DIFFICULTY_BACKPACK_CHANCES.get(difficultyIndex))
            .ifPresent(
               backpackAddition -> {
                  ItemStack backpack = new ItemStack(backpackAddition.getBackpackItem());
                  int minDifficulty = backpackAddition.getMinDifficulty();
                  equipBackpack(
                     monster,
                     backpack,
                     minDifficulty,
                     Boolean.TRUE.equals(Config.SERVER.entityBackpackAdditions.playJukebox.get()) && rnd.nextInt(4) == 0,
                     level,
                     rnd
                  );
                  applyPotions(monster, Math.max(minDifficulty, rnd.nextInt(4)), minDifficulty, rnd);
                  raiseHealth(monster, minDifficulty);
                  if (Boolean.TRUE.equals(Config.SERVER.entityBackpackAdditions.equipWithArmor.get())) {
                     equipArmorPiece(monster, rnd, minDifficulty, backpackAddition.getHelmetChances(), EquipmentSlot.HEAD, level);
                     equipArmorPiece(monster, rnd, minDifficulty, backpackAddition.getLeggingsChances(), EquipmentSlot.LEGS, level);
                     equipArmorPiece(monster, rnd, minDifficulty, backpackAddition.getBootsChances(), EquipmentSlot.FEET, level);
                  }

                  monster.addTag("spawnedWithBackpack");
               }
            );
      }
   }

   private static void equipArmorPiece(
      Monster monster, RandomSource rnd, int minDifficulty, List<WeightedElement<Item>> armorChances, EquipmentSlot slot, LevelAccessor level
   ) {
      RandHelper.getRandomWeightedElement(rnd, armorChances)
         .ifPresent(
            armorPiece -> {
               if (armorPiece != Items.AIR) {
                  ItemStack armorStack = new ItemStack(armorPiece);
                  if (rnd.nextInt(6 - minDifficulty) == 0) {
                     float additionalDifficulty = level.getCurrentDifficultyAt(monster.blockPosition()).getSpecialMultiplier();
                     int enchantmentLevel = (int)(5.0F + additionalDifficulty * 18.0F + minDifficulty * 6);
                     EnchantmentHelper.enchantItem(
                        rnd,
                        armorStack,
                        enchantmentLevel,
                        level.registryAccess(),
                        level.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getTag(EnchantmentTags.ON_MOB_SPAWN_EQUIPMENT)
                     );
                  }

                  monster.setItemSlot(slot, armorStack);
               }
            }
         );
   }

   private static void equipBackpack(Monster monster, ItemStack backpack, int minDifficulty, boolean playMusicDisc, LevelAccessor level, RandomSource rnd) {
      getSpawnEgg(monster.getType()).ifPresent(egg -> {
         IBackpackWrapper wrapper = BackpackWrapper.fromStack(backpack);
         wrapper.setColors(egg.getColor(0) | 0xFF000000, egg.getColor(1) | 0xFF000000);
         int partialRolls = rnd.nextInt(minDifficulty, minDifficulty + 3);
         if (partialRolls != 0) {
            setLoot(monster, wrapper, level, partialRolls / 3.0F);
         }

         if (playMusicDisc) {
            wrapper.getInventoryHandler();
            if (wrapper.getUpgradeHandler().getSlots() > 0) {
               monster.addTag("sophisticatedbackpacks:jukebox");
               addJukeboxUpgradeAndRandomDisc(level.getRandom(), wrapper, rnd);
            }
         }
      });
      monster.setItemSlot(EquipmentSlot.CHEST, backpack);
      monster.setDropChance(EquipmentSlot.CHEST, 0.0F);
   }

   private static void addJukeboxUpgradeAndRandomDisc(RandomSource random, IStorageWrapper w, RandomSource rnd) {
      boolean advancedJukebox = random.nextFloat() < 0.25;
      w.getUpgradeHandler()
         .setStackInSlot(0, new ItemStack(advancedJukebox ? (ItemLike)ModItems.ADVANCED_JUKEBOX_UPGRADE.get() : (ItemLike)ModItems.JUKEBOX_UPGRADE.get()));
      Iterator<JukeboxUpgradeWrapper> it = w.getUpgradeHandler().getTypeWrappers(JukeboxUpgradeItem.TYPE).iterator();
      if (it.hasNext()) {
         if (DiscHandlerRegistry.getHandlers().isEmpty()) {
            SophisticatedBackpacks.LOGGER.warn("No music discs found to add to jukebox upgrade, either there are none registered or they are all blocked");
            return;
         }

         JukeboxUpgradeWrapper wrapper = it.next();
         int numberOfDiscs = advancedJukebox ? random.nextInt(wrapper.getDiscInventory().getSlots() / 3) + 1 : 1;
         List<ItemStack> discsUsed = new ArrayList<>();

         for (int i = 0; i < numberOfDiscs * 2; i++) {
            int slot = i;
            DiscHandlerRegistry.getRandomDisc(rnd).ifPresent(disc -> {
               if (!discsUsed.stream().anyMatch(s -> ItemStack.isSameItemSameComponents(s, disc))) {
                  wrapper.getDiscInventory().insertItem(slot, disc, false);
                  discsUsed.add(disc);
               }
            });
            if (discsUsed.size() >= numberOfDiscs) {
               break;
            }
         }
      }
   }

   private static void raiseHealth(Monster monster, int minDifficulty) {
      if (!Boolean.FALSE.equals(Config.SERVER.entityBackpackAdditions.buffHealth.get())) {
         AttributeInstance maxHealth = monster.getAttribute(Attributes.MAX_HEALTH);
         if (maxHealth != null) {
            double healthAddition = maxHealth.getBaseValue() * minDifficulty;
            if (healthAddition > 0.1) {
               maxHealth.addPermanentModifier(
                  new AttributeModifier(SophisticatedBackpacks.getRL("backpack_bearer_health_bonus"), healthAddition, Operation.ADD_VALUE)
               );
            }

            monster.setHealth(monster.getMaxHealth());
         }
      }
   }

   private static Optional<SpawnEggItem> getSpawnEgg(EntityType<?> entityType) {
      return Optional.ofNullable((SpawnEggItem)SpawnEggItem.BY_ID.get(entityType));
   }

   private static void setLoot(Monster monster, IBackpackWrapper backpackWrapper, LevelAccessor level, float lootFactor) {
      MinecraftServer server = level.getServer();
      if (server != null) {
         if (Boolean.TRUE.equals(Config.SERVER.entityBackpackAdditions.addLoot.get())) {
            addLoot(monster, backpackWrapper, lootFactor);
         }
      }
   }

   private static void applyPotions(Monster monster, int difficulty, int minDifficulty, RandomSource rnd) {
      if (Boolean.TRUE.equals(Config.SERVER.entityBackpackAdditions.buffWithPotionEffects.get())) {
         RandHelper.getNRandomElements(APPLICABLE_EFFECTS, difficulty + 2).forEach(applicableEffect -> {
            int amplifier = Math.min(Math.max(minDifficulty, rnd.nextInt(difficulty + 1)), applicableEffect.getMaxAmplifier());
            monster.addEffect(new MobEffectInstance(applicableEffect.getRandomEffect(rnd), 36000, amplifier));
         });
      }
   }

   private static void addLoot(Monster monster, IBackpackWrapper backpackWrapper, float lootFactor) {
      Config.SERVER.entityBackpackAdditions.getLootTableName(monster.getType()).ifPresent(lootTableName -> backpackWrapper.setLoot(lootTableName, lootFactor));
   }

   static void handleBackpackDrop(LivingDropsEvent event) {
      if (event.getEntity().getTags().contains("spawnedWithBackpack")) {
         LivingEntity mob = event.getEntity();
         ItemStack backpack = mob.getItemBySlot(EquipmentSlot.CHEST);
         Config.Server.EntityBackpackAdditionsConfig additionsConfig = Config.SERVER.entityBackpackAdditions;
         if (shouldDropBackpack(event, additionsConfig, mob, backpack)) {
            putJukeboxItemsInContainerAndRemoveStorageUuid(event, backpack);
            ItemEntity backpackEntity = new ItemEntity(mob.level(), mob.getX(), mob.getY(), mob.getZ(), backpack);
            event.getDrops().add(backpackEntity);
            mob.setItemSlot(EquipmentSlot.CHEST, ItemStack.EMPTY);
            event.getEntity().getTags().remove("spawnedWithBackpack");
         } else {
            removeContentsUuid(backpack);
         }
      }
   }

   private static void putJukeboxItemsInContainerAndRemoveStorageUuid(LivingDropsEvent event, ItemStack backpack) {
      if (event.getEntity().getTags().remove("sophisticatedbackpacks:jukebox")) {
         List<ItemStack> inventoryItems = new ArrayList<>();
         IBackpackWrapper backpackwrapper = BackpackWrapper.fromStack(backpack);
         backpackwrapper.getUpgradeHandler().getTypeWrappers(JukeboxUpgradeItem.TYPE).forEach(wrapper -> {
            wrapper.stop(event.getEntity());
            InventoryHelper.iterate(wrapper.getDiscInventory(), (slot, stack) -> {
               if (!stack.isEmpty()) {
                  inventoryItems.add(wrapper.getDiscInventory().extractItem(slot, stack.getCount(), false));
               }
            });
         });
         InventoryHelper.iterate(backpackwrapper.getUpgradeHandler(), (slot, stack) -> {
            if (!stack.isEmpty()) {
               inventoryItems.add(backpackwrapper.getUpgradeHandler().extractItem(slot, stack.getCount(), false));
            }
         });
         UUID backpackUuid = (UUID)backpack.remove(ModCoreDataComponents.STORAGE_UUID);
         if (backpackUuid != null) {
            BackpackStorage.get().removeBackpackContents(backpackUuid);
         }

         backpack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(inventoryItems));
      }
   }

   private static boolean shouldDropBackpack(
      LivingDropsEvent event, Config.Server.EntityBackpackAdditionsConfig additionsConfig, LivingEntity mob, ItemStack backpack
   ) {
      if (event.getSource().getEntity() instanceof Player player) {
         if (!Boolean.TRUE.equals(additionsConfig.dropToFakePlayers.get()) && event.getSource().getEntity() instanceof FakePlayer) {
            return false;
         }

         float lootingChanceMultiplier = dropChanceMultiplier.getOrDefault(backpack.getItem(), 1.0F);
         int lootingLevel = player.level()
            .registryAccess()
            .registry(Registries.ENCHANTMENT)
            .map(registry -> player.getMainHandItem().getEnchantmentLevel(registry.getHolderOrThrow(Enchantments.LOOTING)))
            .orElse(0);
         return mob.level().random.nextFloat()
            < ((Double)additionsConfig.backpackDropChance.get() + lootingLevel * (Double)additionsConfig.lootingChanceIncreasePerLevel.get())
               * lootingChanceMultiplier;
      } else {
         return false;
      }
   }

   public static void removeBeneficialEffects(Creeper creeper) {
      if (creeper.getTags().contains("spawnedWithBackpack")) {
         creeper.getActiveEffects().removeIf(e -> ((MobEffect)e.getEffect().value()).isBeneficial());
      }
   }

   public static void removeBackpackUuid(Monster entity, Level level) {
      if (!level.isClientSide() && entity.getTags().contains("spawnedWithBackpack")) {
         ItemStack stack = entity.getItemBySlot(EquipmentSlot.CHEST);
         removeContentsUuid(stack);
      }
   }

   private static void removeContentsUuid(ItemStack stack) {
      BackpackWrapper.fromStack(stack).getContentsUuid().ifPresent(uuid -> BackpackStorage.get().removeBackpackContents(uuid));
   }

   public static void onLivingUpdate(Post event) {
      Entity entity = event.getEntity();
      if (entity instanceof LivingEntity livingEntity && entity.getTags().contains("sophisticatedbackpacks:jukebox")) {
         IBackpackWrapper backpackWrapper = BackpackWrapper.fromStack(livingEntity.getItemBySlot(EquipmentSlot.CHEST));
         backpackWrapper.getUpgradeHandler().getTypeWrappers(JukeboxUpgradeItem.TYPE).forEach(wrapper -> {
            if (wrapper.isPlaying()) {
               wrapper.tick(livingEntity, entity.level(), entity.blockPosition());
            } else {
               wrapper.play(livingEntity);
            }
         });
      }
   }

   static {
      VanillaDiscHandler.setDiscBlockListGetter(Config.SERVER.entityBackpackAdditions.discBlockList);
   }

   private static class ApplicableEffect {
      private final List<Holder<MobEffect>> effects;
      private final int maxAmplifier;

      private ApplicableEffect(Holder<MobEffect> effect) {
         this(List.of(effect), Integer.MAX_VALUE);
      }

      private ApplicableEffect(List<Holder<MobEffect>> effects, int maxAmplifier) {
         this.effects = effects;
         this.maxAmplifier = maxAmplifier;
      }

      public Holder<MobEffect> getRandomEffect(RandomSource random) {
         return this.effects.get(random.nextInt(this.effects.size()));
      }

      public int getMaxAmplifier() {
         return this.maxAmplifier;
      }
   }

   private record BackpackAddition(
      Item backpackItem,
      int minDifficulty,
      List<WeightedElement<Item>> helmetChances,
      List<WeightedElement<Item>> leggingsChances,
      List<WeightedElement<Item>> bootsChances
   ) {
      public List<WeightedElement<Item>> getHelmetChances() {
         return this.helmetChances;
      }

      public List<WeightedElement<Item>> getLeggingsChances() {
         return this.leggingsChances;
      }

      public List<WeightedElement<Item>> getBootsChances() {
         return this.bootsChances;
      }

      public Item getBackpackItem() {
         return this.backpackItem;
      }

      public int getMinDifficulty() {
         return this.minDifficulty;
      }
   }
}
