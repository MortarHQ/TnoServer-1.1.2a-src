package de.ellpeck.naturesaura.blocks;

import com.mojang.serialization.MapCodec;
import de.ellpeck.naturesaura.blocks.tiles.BlockEntityEndFlower;
import de.ellpeck.naturesaura.blocks.tiles.ITickableBlockEntity;
import de.ellpeck.naturesaura.blocks.tiles.ModBlockEntities;
import de.ellpeck.naturesaura.data.BlockStateGenerator;
import de.ellpeck.naturesaura.data.ItemModelGenerator;
import de.ellpeck.naturesaura.reg.ICustomBlockState;
import de.ellpeck.naturesaura.reg.ICustomItemModel;
import de.ellpeck.naturesaura.reg.IModItem;
import de.ellpeck.naturesaura.reg.ModRegistry;
import de.ellpeck.naturesaura.reg.ModTileType;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.loot.LootParams.Builder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.EntityTickEvent.Pre;

public class BlockEndFlower extends BushBlock implements IModItem, ICustomBlockState, ICustomItemModel, EntityBlock {
   protected static final VoxelShape SHAPE = Block.box(5.0, 0.0, 5.0, 11.0, 10.0, 11.0);

   public BlockEndFlower() {
      super(Properties.of().noCollission().strength(0.5F).sound(SoundType.GRASS));
      NeoForge.EVENT_BUS.register(this);
      ModRegistry.ALL_ITEMS.add(this);
      ModRegistry.ALL_ITEMS.add(new ModTileType(BlockEntityEndFlower::new, this));
   }

   public VoxelShape getShape(BlockState state, BlockGetter levelIn, BlockPos pos, CollisionContext context) {
      Vec3 vec3d = state.getOffset(levelIn, pos);
      return SHAPE.move(vec3d.x, vec3d.y, vec3d.z);
   }

   @SubscribeEvent
   public void onDragonTick(Pre event) {
      Entity living = event.getEntity();
      if (!living.level().isClientSide && living instanceof EnderDragon dragon) {
         if (dragon.dragonDeathTime >= 150 && dragon.dragonDeathTime % 10 == 0) {
            for (int i = 0; i < 6; i++) {
               int x = dragon.level().random.nextInt(256) - 128;
               int z = dragon.level().random.nextInt(256) - 128;
               BlockPos pos = new BlockPos(x, dragon.level().getHeight(Types.WORLD_SURFACE, x, z), z);
               if (dragon.level().isLoaded(pos) && dragon.level().getBlockState(pos.below()).getBlock() == Blocks.END_STONE) {
                  dragon.level().setBlockAndUpdate(pos, this.defaultBlockState());
               }
            }
         }
      }
   }

   protected MapCodec<? extends BushBlock> codec() {
      return null;
   }

   public boolean canSurvive(BlockState state, LevelReader levelIn, BlockPos pos) {
      return levelIn.getBlockState(pos.below()).getBlock() == Blocks.END_STONE;
   }

   @Override
   public String getBaseName() {
      return "end_flower";
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new BlockEntityEndFlower(pos, state);
   }

   @org.jetbrains.annotations.Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
      return ITickableBlockEntity.createTickerHelper(type, ModBlockEntities.END_FLOWER);
   }

   public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
      return willHarvest || super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
   }

   public void playerDestroy(Level levelIn, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity te, ItemStack stack) {
      super.playerDestroy(levelIn, player, pos, state, te, stack);
      levelIn.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
   }

   public List<ItemStack> getDrops(BlockState state, Builder builder) {
      BlockEntity tile = (BlockEntity)builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
      return (List<ItemStack>)(tile instanceof BlockEntityEndFlower f && f.isDrainMode ? NonNullList.create() : super.getDrops(state, builder));
   }

   @Override
   public void generateCustomBlockState(BlockStateGenerator generator) {
      generator.simpleBlock(
         this, ((BlockModelBuilder)generator.models().cross(this.getBaseName(), generator.modLoc("block/" + this.getBaseName()))).renderType("cutout")
      );
   }

   @Override
   public void generateCustomItemModel(ItemModelGenerator generator) {
      ((ItemModelBuilder)generator.withExistingParent(this.getBaseName(), "item/generated")).texture("layer0", "block/" + this.getBaseName());
   }
}
