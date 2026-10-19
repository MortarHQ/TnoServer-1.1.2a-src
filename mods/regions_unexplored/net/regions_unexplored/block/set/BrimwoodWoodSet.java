package net.regions_unexplored.block.set;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.regions_unexplored.block.RUBlockUtils;
import net.regions_unexplored.registry.RUBlocks;
import net.regions_unexplored.world.level.block.wood.MagmaLogBlock;

public class BrimwoodWoodSet extends WoodSet {
   protected Supplier<Block> logMagma;

   public BrimwoodWoodSet(String name, boolean fireproof) {
      super(name, fireproof);
   }

   public static BrimwoodWoodSet brimwood(String name, WoodType woodType, SoundType sound, MapColor plankColour, MapColor logColour, boolean fireproof) {
      BrimwoodWoodSet set = new BrimwoodWoodSet(name, fireproof);
      set.addLogs(name, "log", "wood", sound, plankColour, logColour, fireproof, RotatedPillarBlock::new, true);
      set.logMagma = RUBlockUtils.register(name + "_log_magma", p -> RUBlockUtils.block(p, 2.0F, 2.0F, plankColour, sound, fireproof, MagmaLogBlock::new));
      set.addCommonWoodBlocks(name, woodType, plankColour, sound, fireproof);
      RUBlocks.WOOD_SETS.add(set);
      return set;
   }

   public Block getLogMagma() {
      return this.logMagma != null ? this.logMagma.get() : null;
   }

   @Override
   public List<Item> getBuildingBlocksTabItems() {
      ArrayList<Item> items = new ArrayList<>();
      this.addItemToList(WoodSet::getLog, items);
      this.addItemToList(BrimwoodWoodSet::getLogMagma, items);
      this.addItemToList(WoodSet::getWood, items);
      this.addItemToList(WoodSet::getStrippedLog, items);
      this.addItemToList(WoodSet::getStrippedWood, items);
      this.addItemToList(WoodSet::getPlanks, items);
      this.addItemToList(WoodSet::getStairs, items);
      this.addItemToList(WoodSet::getSlab, items);
      this.addItemToList(WoodSet::getFence, items);
      this.addItemToList(WoodSet::getFenceGate, items);
      this.addItemToList(WoodSet::getDoor, items);
      this.addItemToList(WoodSet::getTrapdoor, items);
      this.addItemToList(WoodSet::getPressurePlate, items);
      this.addItemToList(WoodSet::getButton, items);
      return items;
   }
}
