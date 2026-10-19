package net.jadenxgamer.netherexp.core.block;

import java.util.Optional;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.core.block.entity.SuspiciousSoulSandBlockEntity;
import net.jadenxgamer.netherexp.core.datadriven.WispArchaeology;
import net.jadenxgamer.netherexp.core.entity.Wisp;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.jadenxgamer.netherexp.registry.JNECriteriaTriggers;
import net.jadenxgamer.netherexp.registry.JNEEntityType;
import net.jadenxgamer.netherexp.registry.JNERegistries;
import net.jadenxgamer.netherexp.util.ParticleHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BrushItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoulSandBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.BlockHitResult;

public class EctoSoulSandBlock extends SoulSandBlock {
   public static final BooleanProperty SALTED = BooleanProperty.create("salted");

   public EctoSoulSandBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)this.defaultBlockState().setValue(SALTED, false));
   }

   protected boolean isRandomlyTicking(BlockState state) {
      return !(Boolean)state.getValue(SALTED);
   }

   protected ItemInteractionResult useItemOn(
      ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult
   ) {
      if ((Boolean)JNEConfigs.BRUSH_WISPS_OUT.get() && stack.getItem() instanceof BrushItem && level.getBlockState(pos.above()).isAir()) {
         level.playSound(null, pos, SoundEvents.BRUSH_SAND, SoundSource.BLOCKS, 1.0F, 1.0F);
         if (!player.getAbilities().instabuild) {
            stack.hurtAndBreak((Integer)JNEConfigs.ECTO_SOUL_SAND_BRUSH_DAMAGE.get(), player, LivingEntity.getSlotForHand(hand));
         }

         ParticleHelper.surroundBlockParticle(level, pos, ParticleTypes.SOUL);
         if (level instanceof ServerLevel serverLevel && level.random.nextDouble() < (Double)JNEConfigs.WISP_EMERGING_CHANCE_BRUSH.get()) {
            this.spawnWisp(serverLevel, this.findAirNeighbor(serverLevel, pos), level.random);
            this.setSusSoulSand(serverLevel, pos);
            if (player instanceof ServerPlayer serverPlayer) {
               JNECriteriaTriggers.INVOLUNTARY_EVICTION.get().trigger(serverPlayer);
            }
         }

         return ItemInteractionResult.sidedSuccess(level.isClientSide);
      } else if (!(Boolean)state.getValue(SALTED) && stack.is(Items.HONEYCOMB)) {
         ParticleHelper.surroundBlockParticle(level, pos, ParticleTypes.WAX_ON);
         level.playSound(null, pos, SoundEvents.HONEYCOMB_WAX_ON, SoundSource.BLOCKS, 1.0F, 1.0F);
         level.setBlock(pos, (BlockState)state.cycle(SALTED), 3);
         return ItemInteractionResult.sidedSuccess(level.isClientSide());
      } else {
         return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
      }
   }

   protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      if (!(random.nextDouble() > (Double)JNEConfigs.WISP_EMERGING_CHANCE.get()) && level.getBlockState(pos.below()).isFaceSturdy(level, pos, Direction.UP)) {
         this.spawnWisp(level, this.findAirNeighbor(level, pos), level.random);
         this.setSusSoulSand(level, pos);
      }
   }

   private void setSusSoulSand(ServerLevel level, BlockPos pos) {
      if (!(Boolean)JNEConfigs.CONVERTS_TO_SUSPICIOUS_SOUL_SAND.get()) {
         level.setBlock(pos, Blocks.SOUL_SAND.defaultBlockState(), 3);
      } else {
         ResourceLocation id = this.obtainFromWispArchaeology(level, pos);
         ResourceKey<LootTable> lootTable = ResourceKey.create(Registries.LOOT_TABLE, id);
         level.setBlock(pos, (BlockState)JNEBlocks.SUSPICIOUS_SOUL_SAND.get().defaultBlockState().setValue(SuspiciousSoulSandBlock.PERSISTENT, false), 3);
         SuspiciousSoulSandBlockEntity.setLootTable(level, level.random, pos, lootTable);
      }
   }

   private void spawnWisp(ServerLevel level, BlockPos pos, RandomSource random) {
      if (pos != null) {
         Wisp wisp = (Wisp)JNEEntityType.WISP.get().create(level);
         if (wisp != null) {
            wisp.setBoredCounter(random.nextInt(0, 2));
            wisp.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            level.addFreshEntity(wisp);
         }
      }
   }

   private BlockPos findAirNeighbor(Level level, BlockPos pos) {
      Direction[] directions = Direction.values();

      for (Direction direction : directions) {
         BlockPos relativePos = pos.relative(direction);
         if (level.getBlockState(relativePos).isAir()) {
            return relativePos;
         }
      }

      return null;
   }

   private ResourceLocation obtainFromWispArchaeology(ServerLevel level, BlockPos pos) {
      ResourceLocation defaultTable = ResourceLocation.parse((String)JNEConfigs.WISP_ARCHAEOLOGY_DEFAULT_LOOT_TABLE.get());
      Optional<WispArchaeology> wispArchaeology = level.registryAccess()
         .registryOrThrow(JNERegistries.Keys.WISP_ARCHAEOLOGY)
         .stream()
         .filter(
            json -> {
               StructureManager structureManager = level.structureManager();
               Holder<Biome> biomeAtPos = level.getBiome(pos);
               boolean matchesStructure = json.structure().isPresent()
                  && structureManager.getStructureWithPieceAt(
                        pos, (Structure)level.registryAccess().registryOrThrow(Registries.STRUCTURE).get(json.structure().get())
                     )
                     .isValid();
               return matchesStructure ? true : json.biomes().isPresent() && json.biomes().get().contains(biomeAtPos);
            }
         )
         .findFirst();
      return wispArchaeology.isEmpty() ? defaultTable : wispArchaeology.get().lootTable();
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{SALTED});
   }
}
