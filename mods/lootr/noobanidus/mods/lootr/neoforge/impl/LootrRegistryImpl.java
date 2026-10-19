package noobanidus.mods.lootr.neoforge.impl;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.stats.Stat;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import noobanidus.mods.lootr.common.api.advancement.IAdvancementTrigger;
import noobanidus.mods.lootr.common.api.advancement.IContainerTrigger;
import noobanidus.mods.lootr.common.api.advancement.ILootedStatTrigger;
import noobanidus.mods.lootr.common.api.registry.ILootrRegistry;
import noobanidus.mods.lootr.common.particle.ParticleColorOption;
import noobanidus.mods.lootr.neoforge.init.ModAdvancements;
import noobanidus.mods.lootr.neoforge.init.ModBlockEntities;
import noobanidus.mods.lootr.neoforge.init.ModBlocks;
import noobanidus.mods.lootr.neoforge.init.ModEntities;
import noobanidus.mods.lootr.neoforge.init.ModItems;
import noobanidus.mods.lootr.neoforge.init.ModLoot;
import noobanidus.mods.lootr.neoforge.init.ModParticles;
import noobanidus.mods.lootr.neoforge.init.ModStats;
import noobanidus.mods.lootr.neoforge.init.ModTabs;

public class LootrRegistryImpl implements ILootrRegistry {
   @Override
   public Block getBarrelBlock() {
      return (Block)ModBlocks.BARREL.get();
   }

   @Override
   public Block getChestBlock() {
      return (Block)ModBlocks.CHEST.get();
   }

   @Override
   public Block getTrappedChestBlock() {
      return (Block)ModBlocks.TRAPPED_CHEST.get();
   }

   @Override
   public Block getInventoryBlock() {
      return (Block)ModBlocks.INVENTORY.get();
   }

   @Override
   public Block getTrophyBlock() {
      return (Block)ModBlocks.TROPHY.get();
   }

   @Override
   public Block getShulkerBlock() {
      return (Block)ModBlocks.SHULKER.get();
   }

   @Override
   public Block getSuspiciousSandBlock() {
      return (Block)ModBlocks.SUSPICIOUS_SAND.get();
   }

   @Override
   public Block getSuspiciousGravelBlock() {
      return (Block)ModBlocks.SUSPICIOUS_GRAVEL.get();
   }

   @Override
   public Block getDecoratedPotBlock() {
      return (Block)ModBlocks.DECORATED_POT.get();
   }

   @Override
   public BlockEntityType<?> getBarrelBlockEntity() {
      return (BlockEntityType<?>)ModBlockEntities.LOOTR_BARREL.get();
   }

   @Override
   public BlockEntityType<? extends ChestBlockEntity> getChestBlockEntity() {
      return (BlockEntityType<? extends ChestBlockEntity>)ModBlockEntities.LOOTR_CHEST.get();
   }

   @Override
   public BlockEntityType<? extends ChestBlockEntity> getTrappedChestBlockEntity() {
      return (BlockEntityType<? extends ChestBlockEntity>)ModBlockEntities.LOOTR_TRAPPED_CHEST.get();
   }

   @Override
   public BlockEntityType<? extends ChestBlockEntity> getInventoryBlockEntity() {
      return (BlockEntityType<? extends ChestBlockEntity>)ModBlockEntities.LOOTR_INVENTORY.get();
   }

   @Override
   public BlockEntityType<?> getShulkerBlockEntity() {
      return (BlockEntityType<?>)ModBlockEntities.LOOTR_SHULKER.get();
   }

   @Override
   public BlockEntityType<?> getDecoratedPotBlockEntity() {
      return (BlockEntityType<?>)ModBlockEntities.LOOTR_DECORATED_POT.get();
   }

   @Override
   public Item getBarrelItem() {
      return (Item)ModItems.BARREL.get();
   }

   @Override
   public Item getChestItem() {
      return (Item)ModItems.CHEST.get();
   }

   @Override
   public Item getTrappedChestItem() {
      return (Item)ModItems.TRAPPED_CHEST.get();
   }

   @Override
   public Item getInventoryItem() {
      return (Item)ModItems.INVENTORY.get();
   }

   @Override
   public Item getTrophyItem() {
      return (Item)ModItems.TROPHY.get();
   }

   @Override
   public Item getShulkerItem() {
      return (Item)ModItems.SHULKER.get();
   }

   @Override
   public Item getSuspiciousSandItem() {
      return (Item)ModItems.SUSPICIOUS_SAND.get();
   }

   @Override
   public Item getSuspiciousGravelItem() {
      return (Item)ModItems.SUSPICIOUS_GRAVEL.get();
   }

   @Override
   public Item getDecoratedPotItem() {
      return (Item)ModItems.DECORATED_POT.get();
   }

   @Override
   public EntityType<?> getMinecart() {
      return (EntityType<?>)ModEntities.LOOTR_MINECART_ENTITY.get();
   }

   @Override
   public EntityType<? extends ItemFrame> getItemFrame() {
      return (EntityType<? extends ItemFrame>)ModEntities.ITEM_FRAME.get();
   }

   @Override
   public BlockEntityType<?> getBrushableBlockEntity() {
      return (BlockEntityType<?>)ModBlockEntities.LOOTR_BRUSHABLE_BLOCK.get();
   }

   @Override
   public IAdvancementTrigger getAdvancementTrigger() {
      return (IAdvancementTrigger)ModAdvancements.ADVANCEMENT.get();
   }

   @Override
   public IContainerTrigger getChestTrigger() {
      return (IContainerTrigger)ModAdvancements.CHEST.get();
   }

   @Override
   public IContainerTrigger getBarrelTrigger() {
      return (IContainerTrigger)ModAdvancements.BARREL.get();
   }

   @Override
   public IContainerTrigger getCartTrigger() {
      return (IContainerTrigger)ModAdvancements.CART.get();
   }

   @Override
   public IContainerTrigger getShulkerTrigger() {
      return (IContainerTrigger)ModAdvancements.SHULKER.get();
   }

   @Override
   public ILootedStatTrigger getStatTrigger() {
      return (ILootedStatTrigger)ModAdvancements.STAT.get();
   }

   @Override
   public IContainerTrigger getSandTrigger() {
      return (IContainerTrigger)ModAdvancements.SAND.get();
   }

   @Override
   public IContainerTrigger getGravelTrigger() {
      return (IContainerTrigger)ModAdvancements.GRAVEL.get();
   }

   @Override
   public IContainerTrigger getPotTrigger() {
      return (IContainerTrigger)ModAdvancements.POT.get();
   }

   @Override
   public LootItemConditionType getLootCount() {
      return (LootItemConditionType)ModLoot.LOOT_COUNT.get();
   }

   @Override
   public Stat<?> getLootedStat() {
      if (ModStats.LOOTED_STAT == null) {
         ModStats.load();
      }

      return ModStats.LOOTED_STAT;
   }

   @Override
   public CreativeModeTab getTab() {
      return (CreativeModeTab)ModTabs.LOOTR.get();
   }

   @Override
   public IContainerTrigger getItemFrameTrigger() {
      return (IContainerTrigger)ModAdvancements.ITEM_FRAME.get();
   }

   @Override
   public ParticleType<ParticleColorOption> getUnopenedParticleType() {
      return (ParticleType<ParticleColorOption>)ModParticles.UNOPENED_PARTICLE.get();
   }

   @Override
   public IContainerTrigger getTrappedTrigger() {
      return (IContainerTrigger)ModAdvancements.TRAPPED_CHEST.get();
   }
}
