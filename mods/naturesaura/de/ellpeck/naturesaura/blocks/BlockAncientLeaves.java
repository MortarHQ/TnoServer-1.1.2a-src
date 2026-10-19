package de.ellpeck.naturesaura.blocks;

import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.api.aura.container.IAuraContainer;
import de.ellpeck.naturesaura.blocks.tiles.BlockEntityAncientLeaves;
import de.ellpeck.naturesaura.data.BlockStateGenerator;
import de.ellpeck.naturesaura.reg.IColorProvidingBlock;
import de.ellpeck.naturesaura.reg.IColorProvidingItem;
import de.ellpeck.naturesaura.reg.ICustomBlockState;
import de.ellpeck.naturesaura.reg.IModItem;
import de.ellpeck.naturesaura.reg.ModRegistry;
import de.ellpeck.naturesaura.reg.ModTileType;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

public class BlockAncientLeaves extends LeavesBlock implements IModItem, IColorProvidingBlock, IColorProvidingItem, ICustomBlockState, EntityBlock {
   public BlockAncientLeaves() {
      super(Properties.of().mapColor(MapColor.COLOR_PINK).strength(0.2F).randomTicks().noOcclusion().sound(SoundType.GRASS));
      ModRegistry.ALL_ITEMS.add(this);
      ModRegistry.ALL_ITEMS.add(new ModTileType(BlockEntityAncientLeaves::new, this));
   }

   @Override
   public String getBaseName() {
      return "ancient_leaves";
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public BlockColor getBlockColor() {
      return (state, levelIn, pos, tintIndex) -> 15031191;
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public ItemColor getItemColor() {
      return (stack, tintIndex) -> 15031191;
   }

   @OnlyIn(Dist.CLIENT)
   public void animateTick(BlockState stateIn, Level levelIn, BlockPos pos, RandomSource rand) {
      super.animateTick(stateIn, levelIn, pos, rand);
      if (rand.nextFloat() >= 0.95F && !levelIn.getBlockState(pos.below()).isCollisionShapeFullBlock(levelIn, pos)) {
         BlockEntity tile = levelIn.getBlockEntity(pos);
         if (tile instanceof BlockEntityAncientLeaves) {
            IAuraContainer container = (IAuraContainer)levelIn.getCapability(
               NaturesAuraAPI.AURA_CONTAINER_BLOCK_CAPABILITY, tile.getBlockPos(), tile.getBlockState(), tile, null
            );
            if (container.getStoredAura() > 0) {
               NaturesAuraAPI.instance()
                  .spawnMagicParticle(
                     pos.getX() + rand.nextDouble(),
                     pos.getY(),
                     pos.getZ() + rand.nextDouble(),
                     0.0,
                     0.0,
                     0.0,
                     13387648,
                     rand.nextFloat() * 2.0F + 0.5F,
                     rand.nextInt(50) + 75,
                     rand.nextFloat() * 0.02F + 0.002F,
                     true,
                     true
                  );
            }
         }
      }
   }

   public void randomTick(BlockState state, ServerLevel levelIn, BlockPos pos, RandomSource random) {
      super.randomTick(state, levelIn, pos, random);
      if (!levelIn.isClientSide) {
         BlockEntity tile = levelIn.getBlockEntity(pos);
         if (tile instanceof BlockEntityAncientLeaves) {
            IAuraContainer container = (IAuraContainer)levelIn.getCapability(
               NaturesAuraAPI.AURA_CONTAINER_BLOCK_CAPABILITY, tile.getBlockPos(), tile.getBlockState(), tile, null
            );
            if (container.getStoredAura() <= 0) {
               levelIn.setBlockAndUpdate(pos, ModBlocks.DECAYED_LEAVES.defaultBlockState());
            }
         }
      }
   }

   public boolean isRandomlyTicking(BlockState state) {
      return true;
   }

   @Override
   public void generateCustomBlockState(BlockStateGenerator generator) {
      generator.simpleBlock(this, generator.models().getExistingFile(generator.modLoc(this.getBaseName())));
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new BlockEntityAncientLeaves(pos, state);
   }
}
