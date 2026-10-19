package net.regions_unexplored.block.set;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.regions_unexplored.block.BlockFactory;
import net.regions_unexplored.block.RUBlockUtils;
import net.regions_unexplored.entity.custom.RuBoat;
import net.regions_unexplored.item.RUItemUtils;
import net.regions_unexplored.item.type.RuBoatItem;
import net.regions_unexplored.registry.RUBlocks;

public class WoodSet {
   public final String name;
   public final boolean fireproof;
   protected Supplier<Block> log;
   protected Supplier<Block> wood;
   protected Supplier<Block> strippedLog;
   protected Supplier<Block> strippedWood;
   protected Supplier<Block> planks;
   protected Supplier<Block> stairs;
   protected Supplier<Block> slab;
   protected Supplier<Block> fence;
   protected Supplier<Block> fenceGate;
   protected Supplier<Block> door;
   protected Supplier<Block> trapdoor;
   protected Supplier<Block> pressurePlate;
   protected Supplier<Block> button;
   protected Supplier<Block> sign;
   protected Supplier<Block> wallSign;
   protected Supplier<Block> hangingSign;
   protected Supplier<Block> wallHangingSign;
   protected Supplier<Item> itemSign;
   protected Supplier<Item> itemHangingSign;
   protected Supplier<Item> itemBoat;
   protected Supplier<Item> itemChestBoat;

   public WoodSet(String name, boolean fireproof) {
      this.name = name;
      this.fireproof = fireproof;
   }

   public static WoodSet simple(String name, WoodType woodType, SoundType sound, MapColor plankColour, MapColor logColour, boolean fireproof) {
      return simple(name, woodType, sound, plankColour, logColour, fireproof, RotatedPillarBlock::new, true);
   }

   public static WoodSet simple(
      String name,
      WoodType woodType,
      SoundType sound,
      MapColor plankColour,
      MapColor logColour,
      boolean fireproof,
      BlockFactory<Block> logFactory,
      boolean boat
   ) {
      WoodSet set = new WoodSet(name, fireproof);
      set.addLogs(name, "log", "wood", sound, plankColour, logColour, fireproof, logFactory, true);
      set.addCommonWoodBlocks(name, woodType, plankColour, sound, fireproof);
      if (boat) {
         set.addBoats(name);
      }

      RUBlocks.WOOD_SETS.add(set);
      return set;
   }

   public static WoodSet bioshroom(String name, WoodType woodType, SoundType sound, MapColor colour, boolean fireproof) {
      WoodSet set = new WoodSet(name, fireproof);
      set.addLogs(name, "stem", "hyphae", sound, colour, colour, fireproof, RotatedPillarBlock::new, true);
      set.addCommonWoodBlocks(name, woodType, colour, sound, fireproof);
      RUBlocks.WOOD_SETS.add(set);
      return set;
   }

   public static WoodSet alpha() {
      WoodSet set = new WoodSet("alpha", false);
      set.log = RUBlockUtils.register(
         "alpha_log", p -> new Block(p.mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.0F).ignitedByLava().sound(SoundType.WOOD))
      );
      set.planks = RUBlockUtils.register("alpha_planks", p -> RUBlockUtils.planks(p, MapColor.WOOD, SoundType.WOOD, false));
      set.stairs = RUBlockUtils.register("alpha_stairs", p -> RUBlockUtils.stairs(p, MapColor.WOOD, SoundType.WOOD, false));
      set.slab = RUBlockUtils.register(
         "alpha_slab", p -> new SlabBlock(p.mapColor(MapColor.WOOD).strength(1.5F).sound(SoundType.STONE).requiresCorrectToolForDrops())
      );
      RUBlocks.WOOD_SETS.add(set);
      return set;
   }

   public static WoodSet onlyLogs(String name, SoundType sound, MapColor plankColor, MapColor logColor, boolean fireproof, BlockFactory<Block> logFactory) {
      WoodSet set = new WoodSet(name, false);
      set.addLogs(name, "log", "wood", sound, plankColor, logColor, fireproof, logFactory, false);
      RUBlocks.WOOD_SETS.add(set);
      return set;
   }

   protected void addLogs(
      String typeName,
      String logName,
      String woodName,
      SoundType sound,
      MapColor plankColour,
      MapColor logColour,
      boolean fireproof,
      BlockFactory<Block> logFactory,
      boolean generateStripped
   ) {
      this.log = RUBlockUtils.register(typeName + "_" + logName, p -> RUBlockUtils.log(p, logFactory, plankColour, logColour, sound, fireproof));
      this.wood = RUBlockUtils.register(typeName + "_" + woodName, p -> RUBlockUtils.log(p, RotatedPillarBlock::new, plankColour, logColour, sound, fireproof));
      if (generateStripped) {
         this.strippedLog = RUBlockUtils.register("stripped_" + typeName + "_" + logName, p -> RUBlockUtils.wood(p, plankColour, sound, fireproof));
         this.strippedWood = RUBlockUtils.register("stripped_" + typeName + "_" + woodName, p -> RUBlockUtils.wood(p, plankColour, sound, fireproof));
      }
   }

   protected void addCommonWoodBlocks(String name, WoodType woodType, MapColor colour, SoundType sound, boolean fireproof) {
      this.planks = RUBlockUtils.register(name + "_planks", p -> RUBlockUtils.planks(p, colour, sound, fireproof));
      this.stairs = RUBlockUtils.register(name + "_stairs", p -> RUBlockUtils.stairs(p, colour, sound, fireproof));
      this.slab = RUBlockUtils.register(name + "_slab", p -> RUBlockUtils.slab(p, colour, sound, fireproof));
      this.fence = RUBlockUtils.register(name + "_fence", p -> RUBlockUtils.fence(p, colour, sound, fireproof));
      this.fenceGate = RUBlockUtils.register(name + "_fence_gate", p -> RUBlockUtils.fenceGate(p, colour, woodType, sound, fireproof));
      this.door = RUBlockUtils.register(name + "_door", p -> RUBlockUtils.door(p, colour, sound, woodType.setType(), fireproof));
      this.trapdoor = RUBlockUtils.register(name + "_trapdoor", p -> RUBlockUtils.trapdoor(p, colour, sound, woodType.setType(), fireproof));
      this.pressurePlate = RUBlockUtils.register(name + "_pressure_plate", p -> RUBlockUtils.pressurePlate(p, colour, sound, woodType.setType(), fireproof));
      this.button = RUBlockUtils.register(name + "_button", p -> RUBlockUtils.button(p, sound, woodType.setType()));
      this.sign = RUBlockUtils.registerNoItem(name + "_sign", p -> RUBlockUtils.sign(p, sound, woodType, fireproof));
      this.wallSign = RUBlockUtils.registerNoItem(name + "_wall_sign", p -> RUBlockUtils.wallSign(p, sound, this.sign.get(), woodType, fireproof));
      this.hangingSign = RUBlockUtils.registerNoItem(name + "_hanging_sign", p -> RUBlockUtils.hangingSign(p, colour, sound, woodType, fireproof));
      this.wallHangingSign = RUBlockUtils.registerNoItem(
         name + "_wall_hanging_sign", p -> RUBlockUtils.wallHangingSign(p, colour, sound, this.hangingSign.get(), woodType, fireproof)
      );
      this.itemSign = RUItemUtils.register(name + "_sign", p -> new SignItem(p.stacksTo(16), this.sign.get(), this.wallSign.get()));
      this.itemHangingSign = RUItemUtils.register(
         name + "_hanging_sign", p -> new HangingSignItem(this.hangingSign.get(), this.wallHangingSign.get(), p.stacksTo(16))
      );
   }

   protected void addBoats(String name) {
      this.itemBoat = RUItemUtils.register(name + "_boat", p -> new RuBoatItem(false, RuBoat.ModelType.byName(name), p.stacksTo(1)));
      this.itemChestBoat = RUItemUtils.register(name + "_chest_boat", p -> new RuBoatItem(true, RuBoat.ModelType.byName(name), p.stacksTo(1)));
   }

   public Block getLog() {
      return this.log != null ? this.log.get() : null;
   }

   public Block getWood() {
      return this.wood != null ? this.wood.get() : null;
   }

   public Block getStrippedLog() {
      return this.strippedLog != null ? this.strippedLog.get() : null;
   }

   public Block getStrippedWood() {
      return this.strippedWood != null ? this.strippedWood.get() : null;
   }

   public Block getPlanks() {
      return this.planks != null ? this.planks.get() : null;
   }

   public Block getStairs() {
      return this.stairs != null ? this.stairs.get() : null;
   }

   public Block getSlab() {
      return this.slab != null ? this.slab.get() : null;
   }

   public Block getFence() {
      return this.fence != null ? this.fence.get() : null;
   }

   public Block getFenceGate() {
      return this.fenceGate != null ? this.fenceGate.get() : null;
   }

   public Block getDoor() {
      return this.door != null ? this.door.get() : null;
   }

   public Block getTrapdoor() {
      return this.trapdoor != null ? this.trapdoor.get() : null;
   }

   public Block getPressurePlate() {
      return this.pressurePlate != null ? this.pressurePlate.get() : null;
   }

   public Block getButton() {
      return this.button != null ? this.button.get() : null;
   }

   public Block getSign() {
      return this.sign != null ? this.sign.get() : null;
   }

   public Block getWallSign() {
      return this.wallSign != null ? this.wallSign.get() : null;
   }

   public Block getHangingSign() {
      return this.hangingSign != null ? this.hangingSign.get() : null;
   }

   public Block getWallHangingSign() {
      return this.wallHangingSign != null ? this.wallHangingSign.get() : null;
   }

   public Item getBoat() {
      return this.itemBoat != null ? this.itemBoat.get() : null;
   }

   public Item getChestBoat() {
      return this.itemChestBoat != null ? this.itemChestBoat.get() : null;
   }

   public List<Item> getAllBlocks() {
      ArrayList<Item> items = new ArrayList<>();
      this.addItemToList(WoodSet::getLog, items);
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
      this.addItemToList(WoodSet::getSign, items);
      this.addItemToList(WoodSet::getHangingSign, items);
      return items;
   }

   public List<Item> getBuildingBlocksTabItems() {
      ArrayList<Item> items = new ArrayList<>();
      this.addItemToList(WoodSet::getLog, items);
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

   public List<Item> getFunctionalBlocksTabItems() {
      ArrayList<Item> items = new ArrayList<>();
      this.addItemToList(WoodSet::getSign, items);
      this.addItemToList(WoodSet::getHangingSign, items);
      return items;
   }

   public List<Item> getToolsAndUtilitiesTabItems() {
      ArrayList<Item> items = new ArrayList<>();
      this.addItemToList(WoodSet::getBoat, items);
      this.addItemToList(WoodSet::getChestBoat, items);
      return items;
   }

   protected <T extends WoodSet> void addItemToList(Function<T, ItemLike> getter, ArrayList<Item> items) {
      if (getter.apply((T)this) != null) {
         items.add(getter.apply((T)this).asItem());
      }
   }
}
