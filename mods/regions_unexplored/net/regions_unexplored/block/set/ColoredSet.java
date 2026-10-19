package net.regions_unexplored.block.set;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;

public final class ColoredSet {
   private final Supplier<Block> white;
   private final Supplier<Block> lightGray;
   private final Supplier<Block> gray;
   private final Supplier<Block> black;
   private final Supplier<Block> brown;
   private final Supplier<Block> red;
   private final Supplier<Block> orange;
   private final Supplier<Block> yellow;
   private final Supplier<Block> lime;
   private final Supplier<Block> green;
   private final Supplier<Block> lightBlue;
   private final Supplier<Block> cyan;
   private final Supplier<Block> blue;
   private final Supplier<Block> purple;
   private final Supplier<Block> magenta;
   private final Supplier<Block> pink;

   public ColoredSet(Function<DyeColor, Supplier<Block>> factory) {
      this.white = factory.apply(DyeColor.WHITE);
      this.lightGray = factory.apply(DyeColor.LIGHT_GRAY);
      this.gray = factory.apply(DyeColor.GRAY);
      this.black = factory.apply(DyeColor.BLACK);
      this.brown = factory.apply(DyeColor.BROWN);
      this.red = factory.apply(DyeColor.RED);
      this.orange = factory.apply(DyeColor.ORANGE);
      this.yellow = factory.apply(DyeColor.YELLOW);
      this.lime = factory.apply(DyeColor.LIME);
      this.green = factory.apply(DyeColor.GREEN);
      this.lightBlue = factory.apply(DyeColor.LIGHT_BLUE);
      this.cyan = factory.apply(DyeColor.CYAN);
      this.blue = factory.apply(DyeColor.BLUE);
      this.purple = factory.apply(DyeColor.PURPLE);
      this.magenta = factory.apply(DyeColor.MAGENTA);
      this.pink = factory.apply(DyeColor.PINK);
   }

   public ArrayList<Block> getAll() {
      ArrayList<Block> blocks = new ArrayList<>();
      blocks.add(this.white.get());
      blocks.add(this.lightGray.get());
      blocks.add(this.gray.get());
      blocks.add(this.black.get());
      blocks.add(this.brown.get());
      blocks.add(this.red.get());
      blocks.add(this.orange.get());
      blocks.add(this.yellow.get());
      blocks.add(this.lime.get());
      blocks.add(this.green.get());
      blocks.add(this.lightBlue.get());
      blocks.add(this.cyan.get());
      blocks.add(this.blue.get());
      blocks.add(this.purple.get());
      blocks.add(this.magenta.get());
      blocks.add(this.pink.get());
      return blocks;
   }

   public Map<DyeColor, Block> getMap() {
      Map<DyeColor, Block> map = new EnumMap<>(DyeColor.class);
      map.put(DyeColor.WHITE, this.white.get());
      map.put(DyeColor.LIGHT_GRAY, this.lightGray.get());
      map.put(DyeColor.GRAY, this.gray.get());
      map.put(DyeColor.BLACK, this.black.get());
      map.put(DyeColor.BROWN, this.brown.get());
      map.put(DyeColor.RED, this.red.get());
      map.put(DyeColor.ORANGE, this.orange.get());
      map.put(DyeColor.YELLOW, this.yellow.get());
      map.put(DyeColor.LIME, this.lime.get());
      map.put(DyeColor.GREEN, this.green.get());
      map.put(DyeColor.LIGHT_BLUE, this.lightBlue.get());
      map.put(DyeColor.CYAN, this.cyan.get());
      map.put(DyeColor.BLUE, this.blue.get());
      map.put(DyeColor.PURPLE, this.purple.get());
      map.put(DyeColor.MAGENTA, this.magenta.get());
      map.put(DyeColor.PINK, this.pink.get());
      return map;
   }

   public Supplier<Block> getWhite() {
      return this.white;
   }

   public Supplier<Block> getLightGray() {
      return this.lightGray;
   }

   public Supplier<Block> getGray() {
      return this.gray;
   }

   public Supplier<Block> getBlack() {
      return this.black;
   }

   public Supplier<Block> getBrown() {
      return this.brown;
   }

   public Supplier<Block> getRed() {
      return this.red;
   }

   public Supplier<Block> getOrange() {
      return this.orange;
   }

   public Supplier<Block> getYellow() {
      return this.yellow;
   }

   public Supplier<Block> getLime() {
      return this.lime;
   }

   public Supplier<Block> getGreen() {
      return this.green;
   }

   public Supplier<Block> getLightBlue() {
      return this.lightBlue;
   }

   public Supplier<Block> getCyan() {
      return this.cyan;
   }

   public Supplier<Block> getBlue() {
      return this.blue;
   }

   public Supplier<Block> getPurple() {
      return this.purple;
   }

   public Supplier<Block> getMagenta() {
      return this.magenta;
   }

   public Supplier<Block> getPink() {
      return this.pink;
   }
}
