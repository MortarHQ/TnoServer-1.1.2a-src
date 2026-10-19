package net.regions_unexplored.block;

import java.util.function.BiConsumer;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.regions_unexplored.item.RUItemUtils;
import net.regions_unexplored.module.platform.Registrar;
import org.jetbrains.annotations.Nullable;

public class RUBlockUtils {
   public static Block block(
      Properties properties, float destroyTime, float explosionResistance, MapColor colour, SoundType sound, boolean fireproof, BlockFactory<?> factory
   ) {
      applyProperties(properties, destroyTime, explosionResistance, sound, fireproof, colour);
      return factory.apply(properties);
   }

   public static Block log(Properties properties, BlockFactory<?> blockFactory, MapColor plankColour, MapColor logColour, SoundType sound, boolean fireproof) {
      applyProperties(properties, 2.0F, 2.0F, sound, fireproof, null)
         .mapColor(state -> state.getValue(RotatedPillarBlock.AXIS) == Axis.Y ? plankColour : logColour);
      return blockFactory.apply(properties);
   }

   public static RotatedPillarBlock wood(Properties properties, MapColor colour, SoundType sound, boolean fireproof) {
      applyProperties(properties, 2.0F, 3.0F, sound, fireproof, colour);
      return new RotatedPillarBlock(properties);
   }

   public static Block planks(Properties properties, MapColor colour, SoundType sound, boolean fireproof) {
      applyProperties(properties, 2.0F, 3.0F, sound, fireproof, colour);
      return new Block(properties);
   }

   public static StairBlock stairs(Properties properties, MapColor colour, SoundType sound, boolean fireproof) {
      applyProperties(properties, 2.0F, 3.0F, sound, fireproof, colour);
      return new StairBlock(Blocks.OAK_PLANKS.defaultBlockState(), properties);
   }

   public static SlabBlock slab(Properties properties, MapColor colour, SoundType sound, boolean fireproof) {
      applyProperties(properties, 2.0F, 3.0F, sound, fireproof, colour);
      return new SlabBlock(properties);
   }

   public static DoorBlock door(Properties properties, MapColor colour, SoundType sound, BlockSetType blockSetType, boolean fireproof) {
      applyProperties(properties, 3.0F, 3.0F, sound, fireproof, colour).noOcclusion();
      return new DoorBlock(blockSetType, properties);
   }

   public static TrapDoorBlock trapdoor(Properties properties, MapColor colour, SoundType sound, BlockSetType blockSetType, boolean fireproof) {
      applyProperties(properties, 3.0F, 3.0F, sound, fireproof, colour).noOcclusion();
      return new TrapDoorBlock(blockSetType, properties);
   }

   public static FenceBlock fence(Properties properties, MapColor colour, SoundType sound, boolean fireproof) {
      applyProperties(properties, 2.0F, 3.0F, sound, fireproof, colour);
      return new FenceBlock(properties);
   }

   public static FenceGateBlock fenceGate(Properties properties, MapColor colour, WoodType type, SoundType sound, boolean fireproof) {
      applyProperties(properties, 2.0F, 3.0F, sound, fireproof, colour);
      return new FenceGateBlock(type, properties);
   }

   public static PressurePlateBlock pressurePlate(Properties properties, MapColor colour, SoundType sound, BlockSetType blockSetType, boolean fireproof) {
      applyProperties(properties, 0.5F, 0.5F, sound, fireproof, colour).noCollission();
      return new PressurePlateBlock(blockSetType, properties);
   }

   public static ButtonBlock button(Properties properties, SoundType sound, BlockSetType blockSetType) {
      applyProperties(properties, 0.5F, 0.5F, sound, true, null).noCollission();
      return new ButtonBlock(blockSetType, 30, properties);
   }

   public static StandingSignBlock sign(Properties properties, SoundType sound, WoodType woodType, boolean fireproof) {
      applyProperties(properties, 1.0F, 1.0F, sound, fireproof, null).noCollission();
      return new StandingSignBlock(woodType, properties);
   }

   public static WallSignBlock wallSign(Properties properties, SoundType sound, Block standingSign, WoodType woodType, boolean fireproof) {
      applyProperties(properties, 1.0F, 1.0F, sound, fireproof, null).noCollission().dropsLike(standingSign);
      return new WallSignBlock(woodType, properties);
   }

   public static CeilingHangingSignBlock hangingSign(Properties properties, MapColor color, SoundType sound, WoodType woodType, boolean fireproof) {
      applyProperties(properties, 1.0F, 1.0F, sound, fireproof, color).noCollission().forceSolidOn();
      return new CeilingHangingSignBlock(woodType, properties);
   }

   public static WallHangingSignBlock wallHangingSign(
      Properties properties, MapColor color, SoundType sound, Block hangingSign, WoodType woodType, boolean fireproof
   ) {
      applyProperties(properties, 1.0F, 1.0F, sound, fireproof, color).noCollission().dropsLike(hangingSign).forceSolidOn();
      return new WallHangingSignBlock(woodType, properties);
   }

   private static Properties applyProperties(
      Properties properties, float destroyTime, float explosionResistance, SoundType sound, boolean fireproof, MapColor color
   ) {
      properties.instrument(NoteBlockInstrument.BASS).strength(destroyTime, explosionResistance).sound(sound);
      if (!fireproof) {
         properties.ignitedByLava();
      }

      if (color != null) {
         properties.mapColor(color);
      }

      return properties;
   }

   public static <T extends Block> Supplier<T> register(String name, BlockFactory<T> factory) {
      return register(name, factory, RUItemUtils::registerBlock, null);
   }

   public static <T extends Block> Supplier<T> register(String name, BlockFactory<T> factory, T copiedBlock) {
      return register(name, factory, RUItemUtils::registerBlock, () -> copiedBlock);
   }

   public static <T extends Block> Supplier<T> register(String name, BlockFactory<T> factory, Supplier<T> copiedBlock) {
      return register(name, factory, RUItemUtils::registerBlock, copiedBlock);
   }

   public static <T extends Block> Supplier<T> registerNoItem(String name, BlockFactory<T> factory) {
      return register(name, factory, (a, b) -> {}, null);
   }

   public static <T extends Block> Supplier<T> registerNoItem(String name, BlockFactory<T> factory, T copiedBlock) {
      return register(name, factory, (a, b) -> {}, () -> copiedBlock);
   }

   public static <T extends Block> Supplier<T> registerNoItem(String name, BlockFactory<T> factory, Supplier<T> copiedBlock) {
      return register(name, factory, (a, b) -> {}, copiedBlock);
   }

   public static <T extends Block> Supplier<T> register(
      String name, BlockFactory<T> factory, BiConsumer<String, Supplier<T>> itemCreator, @Nullable Supplier<T> copiedBlock
   ) {
      Supplier<T> block = Registrar.register(BuiltInRegistries.BLOCK, name, () -> factory.apply(createProperties(copiedBlock)));
      itemCreator.accept(name, block);
      return block;
   }

   private static <T extends Block> Properties createProperties(@Nullable Supplier<T> copiedBlock) {
      return copiedBlock != null ? Properties.ofFullCopy((BlockBehaviour)copiedBlock.get()) : Properties.of();
   }

   public static Block leaves(Properties properties, MapColor colour, boolean fireproof, BlockFactory<?> factory) {
      properties.mapColor(colour)
         .strength(0.2F)
         .randomTicks()
         .sound(SoundType.GRASS)
         .noOcclusion()
         .isValidSpawn(RUBlockUtils::ocelotOrParrot)
         .isSuffocating(RUBlockUtils::never)
         .isViewBlocking(RUBlockUtils::never)
         .pushReaction(PushReaction.DESTROY)
         .isRedstoneConductor(RUBlockUtils::never);
      if (!fireproof) {
         properties.ignitedByLava();
      }

      return factory.apply(properties);
   }

   public static Boolean always(BlockState state, BlockGetter getter, BlockPos pos, EntityType<?> type) {
      return true;
   }

   public static boolean always(BlockState state, BlockGetter getter, BlockPos pos) {
      return true;
   }

   public static Boolean never(BlockState state, BlockGetter getter, BlockPos pos, EntityType<?> type) {
      return false;
   }

   public static boolean never(BlockState state, BlockGetter getter, BlockPos pos) {
      return false;
   }

   public static Boolean ocelotOrParrot(BlockState state, BlockGetter getter, BlockPos pos, EntityType<?> type) {
      return type == EntityType.OCELOT || type == EntityType.PARROT;
   }

   public static VoxelShape column(double sizeXZ, double minY, double maxY) {
      return column(sizeXZ, sizeXZ, minY, maxY);
   }

   public static VoxelShape column(double sizeX, double sizeZ, double minY, double maxY) {
      double halfX = sizeX / 2.0;
      double halfZ = sizeZ / 2.0;
      return box(8.0 - halfX, minY, 8.0 - halfZ, 8.0 + halfX, maxY, 8.0 + halfZ);
   }

   public static VoxelShape box(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
      return Shapes.box(minX / 16.0, minY / 16.0, minZ / 16.0, maxX / 16.0, maxY / 16.0, maxZ / 16.0);
   }
}
