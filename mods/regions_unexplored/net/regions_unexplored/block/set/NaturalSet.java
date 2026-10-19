package net.regions_unexplored.block.set;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.regions_unexplored.block.BlockFactory;
import net.regions_unexplored.block.RUBlockUtils;
import net.regions_unexplored.block.sapling.RUTreeGrowers;
import net.regions_unexplored.block.type.leaves.HangingVinesBlock;
import net.regions_unexplored.block.type.leaves.RUTintedParticlesLeavesBlock;
import net.regions_unexplored.block.type.wood.BeardBlock;
import net.regions_unexplored.block.type.wood.BranchBlock;
import net.regions_unexplored.module.version.VersionBlockHelper;
import net.regions_unexplored.registry.RUBlocks;
import net.regions_unexplored.world.level.block.plant.sapling.RuCactusSaplingBlock;
import net.regions_unexplored.world.level.block.plant.sapling.RuNetherSaplingBlock;
import net.regions_unexplored.world.level.block.plant.tall.ShrubBlock;

public class NaturalSet {
   private static final UnaryOperator<Properties> BRANCH_PROPERTIES = p -> p.noOcclusion().sound(SoundType.MANGROVE_ROOTS).strength(1.0F, 1.5F).dynamicShape();
   private static final UnaryOperator<Properties> SHRUB_PROPERTIES = p -> p.pushReaction(PushReaction.DESTROY)
      .noCollission()
      .instabreak()
      .sound(SoundType.AZALEA)
      .offsetType(OffsetType.XZ);
   public final String name;
   public final boolean fireproof;
   protected Supplier<Block> branch;
   protected Supplier<Block> shrub;
   protected Supplier<Block> leaves;
   protected Supplier<Block> vines;
   protected Supplier<Block> sapling;
   protected Supplier<Block> pottedSapling;

   public NaturalSet(String name, boolean fireproof) {
      this.name = name;
      this.fireproof = fireproof;
      RUBlocks.NATURAL_SETS.add(this);
   }

   public static NaturalSet saguaroCactus() {
      NaturalSet set = create("saguaro_cactus");
      set.sapling = RUBlockUtils.register("saguaro_cactus_flower", p -> new RuCactusSaplingBlock(RUTreeGrowers.SAGUARO_CACTUS, p), Blocks.OAK_SAPLING);
      set.pottedSapling = RUBlockUtils.registerNoItem("potted_saguaro_cactus_flower", p -> new FlowerPotBlock(set.sapling.get(), p), Blocks.POTTED_OAK_SAPLING);
      return set;
   }

   public static NaturalSet ashen() {
      NaturalSet set = create("ashen")
         .withLeaves(MapColor.COLOR_LIGHT_GRAY, RUTintedParticlesLeavesBlock.small(RUTintedParticlesLeavesBlock.TintGetter.constant(7763056)))
         .withSapling(RUTreeGrowers.ASHEN);
      set.shrub = RUBlockUtils.register(
         "ashen_shrub",
         p -> new ShrubBlock(SHRUB_PROPERTIES.apply(VersionBlockHelper.postProcessed(p)).sound(SoundType.ROOTED_DIRT).emissiveRendering((bs, br, bp) -> true))
      );
      return set;
   }

   public static NaturalSet cobalt() {
      NaturalSet set = create("cobalt");
      set.leaves = RUBlockUtils.register("cobalt_webbing", p -> RUBlockUtils.leaves(p, MapColor.COLOR_BLUE, true, LeavesBlock::new));
      set.withSapling(p -> new RuNetherSaplingBlock(RUTreeGrowers.COBALT, p.sound(SoundType.NETHER_SPROUTS)));
      return set;
   }

   public static NaturalSet create(String name) {
      return new NaturalSet(name, false);
   }

   public static NaturalSet create(String name, boolean fireproof) {
      return new NaturalSet(name, fireproof);
   }

   public NaturalSet withShrub() {
      return this.withShrub(ShrubBlock::new);
   }

   public <T extends Block> NaturalSet withShrub(BlockFactory<T> factory) {
      this.shrub = RUBlockUtils.register(this.name + "_shrub", p -> factory.apply(SHRUB_PROPERTIES.apply(p)));
      return this;
   }

   public NaturalSet withBranch() {
      this.branch = RUBlockUtils.register(this.name + "_branch", p -> new BranchBlock(BRANCH_PROPERTIES.apply(p)));
      return this;
   }

   public NaturalSet withBeard() {
      this.branch = RUBlockUtils.register(this.name + "_beard", p -> new BeardBlock(BRANCH_PROPERTIES.apply(p)));
      return this;
   }

   public NaturalSet withLeaves() {
      return this.withLeaves(MapColor.PLANT, RUTintedParticlesLeavesBlock.standard());
   }

   public NaturalSet withLeaves(MapColor color) {
      return this.withLeaves(color, RUTintedParticlesLeavesBlock.standard());
   }

   public NaturalSet withLeaves(BlockFactory<?> factory) {
      return this.withLeaves(MapColor.PLANT, factory);
   }

   public NaturalSet withLeaves(MapColor color, BlockFactory<?> factory) {
      this.leaves = RUBlockUtils.register(this.name + "_leaves", p -> RUBlockUtils.leaves(p, color, this.fireproof, factory));
      return this;
   }

   public NaturalSet withVines(MapColor color) {
      this.vines = RUBlockUtils.register(
         this.name + "_vines",
         p -> new HangingVinesBlock(p.ignitedByLava().mapColor(color).noCollission().sound(SoundType.MOSS_CARPET).pushReaction(PushReaction.DESTROY))
      );
      return this;
   }

   public NaturalSet withSapling(TreeGrower grower) {
      return this.withSapling(p -> new SaplingBlock(grower, p));
   }

   public NaturalSet withSapling(BlockFactory<Block> factory) {
      this.sapling = RUBlockUtils.register(this.name + "_sapling", factory, Blocks.OAK_SAPLING);
      this.pottedSapling = RUBlockUtils.registerNoItem(
         "potted_" + this.name + "_sapling", p -> new FlowerPotBlock(this.getSapling(), p), Blocks.POTTED_OAK_SAPLING
      );
      return this;
   }

   public Block getBranch() {
      return this.branch != null ? this.branch.get() : null;
   }

   public Block getShrub() {
      return this.shrub != null ? this.shrub.get() : null;
   }

   public Block getLeaves() {
      return this.leaves != null ? this.leaves.get() : null;
   }

   public Block getVines() {
      return this.vines != null ? this.vines.get() : null;
   }

   public Block getSapling() {
      return this.sapling != null ? this.sapling.get() : null;
   }

   public Block getPottedSapling() {
      return this.pottedSapling != null ? this.pottedSapling.get() : null;
   }
}
