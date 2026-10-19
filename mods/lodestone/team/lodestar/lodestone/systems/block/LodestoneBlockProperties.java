package team.lodestar.lodestone.systems.block;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockBehaviour.StateArgumentPredicate;
import net.minecraft.world.level.block.state.BlockBehaviour.StatePredicate;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.data.loading.DatagenModLoader;
import org.jetbrains.annotations.NotNull;
import team.lodestar.lodestone.handlers.ThrowawayBlockDataHandler;
import team.lodestar.lodestone.systems.datagen.LodestoneDatagenBlockData;

public class LodestoneBlockProperties extends Properties {
   public static LodestoneBlockProperties of() {
      return new LodestoneBlockProperties();
   }

   public static LodestoneBlockProperties copy(BlockBehaviour pBlockBehaviour) {
      LodestoneBlockProperties properties = of();
      properties.destroyTime = pBlockBehaviour.properties().destroyTime;
      properties.explosionResistance = pBlockBehaviour.properties().explosionResistance;
      properties.hasCollision = pBlockBehaviour.properties().hasCollision;
      properties.isRandomlyTicking = pBlockBehaviour.properties().isRandomlyTicking;
      properties.lightEmission = pBlockBehaviour.properties().lightEmission;
      properties.mapColor = pBlockBehaviour.properties().mapColor;
      properties.soundType = pBlockBehaviour.properties().soundType;
      properties.friction = pBlockBehaviour.properties().friction;
      properties.speedFactor = pBlockBehaviour.properties().speedFactor;
      properties.dynamicShape = pBlockBehaviour.properties().dynamicShape;
      properties.canOcclude = pBlockBehaviour.properties().canOcclude;
      properties.isAir = pBlockBehaviour.properties().isAir;
      properties.requiresCorrectToolForDrops = pBlockBehaviour.properties().requiresCorrectToolForDrops;
      properties.jumpFactor = pBlockBehaviour.properties().jumpFactor;
      properties.drops = pBlockBehaviour.properties().drops;
      properties.ignitedByLava = pBlockBehaviour.properties().ignitedByLava;
      properties.forceSolidOn = pBlockBehaviour.properties().forceSolidOn;
      properties.pushReaction = pBlockBehaviour.properties().pushReaction;
      properties.spawnTerrainParticles = pBlockBehaviour.properties().spawnTerrainParticles;
      properties.instrument = pBlockBehaviour.properties().instrument;
      properties.replaceable = pBlockBehaviour.properties().replaceable;
      properties.isValidSpawn = pBlockBehaviour.properties().isValidSpawn;
      properties.isRedstoneConductor = pBlockBehaviour.properties().isRedstoneConductor;
      properties.isSuffocating = pBlockBehaviour.properties().isSuffocating;
      properties.isViewBlocking = pBlockBehaviour.properties().isViewBlocking;
      properties.emissiveRendering = pBlockBehaviour.properties().emissiveRendering;
      properties.requiredFeatures = pBlockBehaviour.properties().requiredFeatures;
      properties.offsetFunction = pBlockBehaviour.properties().offsetFunction;
      properties.hasPostProcess = pBlockBehaviour.properties().hasPostProcess;
      return properties;
   }

   public LodestoneBlockProperties addThrowawayData(Function<LodestoneThrowawayBlockData, LodestoneThrowawayBlockData> function) {
      ThrowawayBlockDataHandler.THROWAWAY_DATA_CACHE
         .put(this, function.apply(ThrowawayBlockDataHandler.THROWAWAY_DATA_CACHE.getOrDefault(this, new LodestoneThrowawayBlockData())));
      return this;
   }

   public LodestoneThrowawayBlockData getThrowawayData() {
      return ThrowawayBlockDataHandler.THROWAWAY_DATA_CACHE.getOrDefault(this, LodestoneThrowawayBlockData.EMPTY);
   }

   public LodestoneBlockProperties setCutoutRenderType() {
      return this.setRenderType(() -> RenderType::cutoutMipped);
   }

   public LodestoneBlockProperties setRenderType(Supplier<Supplier<RenderType>> renderType) {
      if (FMLEnvironment.dist.isClient()) {
         this.addThrowawayData(d -> d.setRenderType(renderType));
      }

      return this;
   }

   public LodestoneBlockProperties addDatagenData(Function<LodestoneDatagenBlockData, LodestoneDatagenBlockData> function) {
      if (DatagenModLoader.isRunningDataGen()) {
         ThrowawayBlockDataHandler.DATAGEN_DATA_CACHE
            .put(this, function.apply(ThrowawayBlockDataHandler.DATAGEN_DATA_CACHE.getOrDefault(this, new LodestoneDatagenBlockData())));
      }

      return this;
   }

   public LodestoneDatagenBlockData getDatagenData() {
      return ThrowawayBlockDataHandler.DATAGEN_DATA_CACHE.getOrDefault(this, LodestoneDatagenBlockData.EMPTY);
   }

   public LodestoneBlockProperties addTag(TagKey<Block> tag) {
      this.addDatagenData(d -> d.addTag(tag));
      return this;
   }

   @SafeVarargs
   public final LodestoneBlockProperties addTags(TagKey<Block>... tags) {
      this.addDatagenData(d -> d.addTags(tags));
      return this;
   }

   public LodestoneBlockProperties noLootDatagen() {
      this.addDatagenData(LodestoneDatagenBlockData::noLootDatagen);
      return this;
   }

   public LodestoneBlockProperties needsPickaxe() {
      this.addDatagenData(LodestoneDatagenBlockData::needsPickaxe);
      return this;
   }

   public LodestoneBlockProperties needsAxe() {
      this.addDatagenData(LodestoneDatagenBlockData::needsAxe);
      return this;
   }

   public LodestoneBlockProperties needsShovel() {
      this.addDatagenData(LodestoneDatagenBlockData::needsShovel);
      return this;
   }

   public LodestoneBlockProperties needsHoe() {
      this.addDatagenData(LodestoneDatagenBlockData::needsHoe);
      return this;
   }

   public LodestoneBlockProperties needsStone() {
      this.addDatagenData(LodestoneDatagenBlockData::needsStone);
      return this;
   }

   public LodestoneBlockProperties needsIron() {
      this.addDatagenData(LodestoneDatagenBlockData::needsIron);
      return this;
   }

   public LodestoneBlockProperties needsDiamond() {
      this.addDatagenData(LodestoneDatagenBlockData::needsDiamond);
      return this;
   }

   @NotNull
   public LodestoneBlockProperties noCollission() {
      return (LodestoneBlockProperties)super.noCollission();
   }

   @NotNull
   public LodestoneBlockProperties noOcclusion() {
      return (LodestoneBlockProperties)super.noOcclusion();
   }

   @NotNull
   public LodestoneBlockProperties friction(float friction) {
      return (LodestoneBlockProperties)super.friction(friction);
   }

   @NotNull
   public LodestoneBlockProperties speedFactor(float factor) {
      return (LodestoneBlockProperties)super.speedFactor(factor);
   }

   @NotNull
   public LodestoneBlockProperties jumpFactor(float factor) {
      return (LodestoneBlockProperties)super.jumpFactor(factor);
   }

   @NotNull
   public LodestoneBlockProperties sound(@NotNull SoundType type) {
      return (LodestoneBlockProperties)super.sound(type);
   }

   @NotNull
   public LodestoneBlockProperties lightLevel(@NotNull ToIntFunction<BlockState> lightMap) {
      return (LodestoneBlockProperties)super.lightLevel(lightMap);
   }

   @NotNull
   public LodestoneBlockProperties strength(float destroyTime, float explosionResistance) {
      return (LodestoneBlockProperties)super.strength(destroyTime, explosionResistance);
   }

   @NotNull
   public LodestoneBlockProperties instabreak() {
      return (LodestoneBlockProperties)super.instabreak();
   }

   @NotNull
   public LodestoneBlockProperties strength(float strength) {
      return (LodestoneBlockProperties)super.strength(strength);
   }

   @NotNull
   public LodestoneBlockProperties randomTicks() {
      return (LodestoneBlockProperties)super.randomTicks();
   }

   @NotNull
   public LodestoneBlockProperties dynamicShape() {
      return (LodestoneBlockProperties)super.dynamicShape();
   }

   @NotNull
   public LodestoneBlockProperties noLootTable() {
      this.noLootDatagen();
      return (LodestoneBlockProperties)super.noLootTable();
   }

   @NotNull
   public LodestoneBlockProperties dropsLike(@NotNull Block block) {
      this.noLootDatagen();
      return (LodestoneBlockProperties)super.dropsLike(block);
   }

   @NotNull
   public LodestoneBlockProperties lootFrom(@NotNull Supplier<? extends Block> blockIn) {
      this.noLootDatagen();
      return (LodestoneBlockProperties)super.lootFrom(blockIn);
   }

   @NotNull
   public LodestoneBlockProperties air() {
      return (LodestoneBlockProperties)super.air();
   }

   @NotNull
   public LodestoneBlockProperties isValidSpawn(@NotNull StateArgumentPredicate<EntityType<?>> predicate) {
      return (LodestoneBlockProperties)super.isValidSpawn(predicate);
   }

   @NotNull
   public LodestoneBlockProperties isRedstoneConductor(@NotNull StatePredicate predicate) {
      return (LodestoneBlockProperties)super.isRedstoneConductor(predicate);
   }

   @NotNull
   public LodestoneBlockProperties isSuffocating(@NotNull StatePredicate predicate) {
      return (LodestoneBlockProperties)super.isSuffocating(predicate);
   }

   @NotNull
   public LodestoneBlockProperties isViewBlocking(@NotNull StatePredicate predicate) {
      return (LodestoneBlockProperties)super.isViewBlocking(predicate);
   }

   @NotNull
   public LodestoneBlockProperties hasPostProcess(@NotNull StatePredicate predicate) {
      return (LodestoneBlockProperties)super.hasPostProcess(predicate);
   }

   @NotNull
   public LodestoneBlockProperties emissiveRendering(@NotNull StatePredicate predicate) {
      return (LodestoneBlockProperties)super.emissiveRendering(predicate);
   }

   @NotNull
   public LodestoneBlockProperties requiresCorrectToolForDrops() {
      return (LodestoneBlockProperties)super.requiresCorrectToolForDrops();
   }

   @NotNull
   public LodestoneBlockProperties mapColor(@NotNull Function<BlockState, MapColor> p_285406_) {
      return (LodestoneBlockProperties)super.mapColor(p_285406_);
   }

   @NotNull
   public LodestoneBlockProperties mapColor(@NotNull DyeColor p_285331_) {
      return (LodestoneBlockProperties)super.mapColor(p_285331_);
   }

   @NotNull
   public LodestoneBlockProperties mapColor(@NotNull MapColor p_285137_) {
      return (LodestoneBlockProperties)super.mapColor(p_285137_);
   }

   @NotNull
   public LodestoneBlockProperties destroyTime(float destroyTime) {
      return (LodestoneBlockProperties)super.destroyTime(destroyTime);
   }

   @NotNull
   public LodestoneBlockProperties explosionResistance(float explosionResistance) {
      return (LodestoneBlockProperties)super.explosionResistance(explosionResistance);
   }

   @NotNull
   public LodestoneBlockProperties ignitedByLava() {
      return (LodestoneBlockProperties)super.ignitedByLava();
   }

   @NotNull
   public LodestoneBlockProperties liquid() {
      return (LodestoneBlockProperties)super.liquid();
   }

   @NotNull
   public LodestoneBlockProperties forceSolidOn() {
      return (LodestoneBlockProperties)super.forceSolidOn();
   }

   @NotNull
   public LodestoneBlockProperties pushReaction(@NotNull PushReaction p_278265_) {
      return (LodestoneBlockProperties)super.pushReaction(p_278265_);
   }

   @NotNull
   public LodestoneBlockProperties offsetType(@NotNull OffsetType pOffsetType) {
      return (LodestoneBlockProperties)super.offsetType(pOffsetType);
   }

   @NotNull
   public LodestoneBlockProperties noTerrainParticles() {
      return (LodestoneBlockProperties)super.noTerrainParticles();
   }

   @NotNull
   public LodestoneBlockProperties requiredFeatures(FeatureFlag @NotNull ... pRequiredFeatures) {
      return (LodestoneBlockProperties)super.requiredFeatures(pRequiredFeatures);
   }

   @NotNull
   public LodestoneBlockProperties instrument(@NotNull NoteBlockInstrument p_282170_) {
      return (LodestoneBlockProperties)super.instrument(p_282170_);
   }

   @NotNull
   public LodestoneBlockProperties replaceable() {
      return (LodestoneBlockProperties)super.replaceable();
   }
}
