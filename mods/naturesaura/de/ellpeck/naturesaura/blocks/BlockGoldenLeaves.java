package de.ellpeck.naturesaura.blocks;

import de.ellpeck.naturesaura.Helper;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.data.BlockStateGenerator;
import de.ellpeck.naturesaura.reg.IColorProvidingBlock;
import de.ellpeck.naturesaura.reg.IColorProvidingItem;
import de.ellpeck.naturesaura.reg.ICustomBlockState;
import de.ellpeck.naturesaura.reg.IModItem;
import de.ellpeck.naturesaura.reg.ModRegistry;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class BlockGoldenLeaves extends LeavesBlock implements IModItem, IColorProvidingBlock, IColorProvidingItem, ICustomBlockState {
   public static final int HIGHEST_STAGE = 3;
   public static final IntegerProperty STAGE = IntegerProperty.create("stage", 0, 3);

   public BlockGoldenLeaves() {
      super(Properties.of().mapColor(MapColor.GOLD).strength(0.2F).randomTicks().noOcclusion().sound(SoundType.GRASS));
      ModRegistry.ALL_ITEMS.add(this);
   }

   public static boolean convert(Level level, BlockPos pos) {
      BlockState state = level.getBlockState(pos);
      if (state.getBlock() instanceof LeavesBlock && !(state.getBlock() instanceof BlockAncientLeaves) && !(state.getBlock() instanceof BlockGoldenLeaves)) {
         if (!level.isClientSide) {
            level.setBlockAndUpdate(
               pos,
               (BlockState)((BlockState)ModBlocks.GOLDEN_LEAVES
                     .defaultBlockState()
                     .setValue(LeavesBlock.DISTANCE, state.hasProperty(LeavesBlock.DISTANCE) ? (Integer)state.getValue(LeavesBlock.DISTANCE) : 1))
                  .setValue(LeavesBlock.PERSISTENT, state.hasProperty(LeavesBlock.PERSISTENT) ? (Boolean)state.getValue(LeavesBlock.PERSISTENT) : false)
            );
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public String getBaseName() {
      return "golden_leaves";
   }

   @OnlyIn(Dist.CLIENT)
   public void animateTick(BlockState stateIn, Level levelIn, BlockPos pos, RandomSource rand) {
      if ((Integer)stateIn.getValue(STAGE) == 3 && rand.nextFloat() >= 0.75F) {
         NaturesAuraAPI.instance()
            .spawnMagicParticle(
               pos.getX() + rand.nextFloat(),
               pos.getY() + rand.nextFloat(),
               pos.getZ() + rand.nextFloat(),
               0.0,
               0.0,
               0.0,
               15924992,
               0.5F + rand.nextFloat(),
               50,
               0.0F,
               false,
               true
            );
      }
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      super.createBlockStateDefinition(builder);
      builder.add(new Property[]{STAGE});
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public BlockColor getBlockColor() {
      return (state, levelIn, pos, tintIndex) -> {
         int color = 15924992;
         if (state != null && levelIn != null && pos != null) {
            int foliage = BiomeColors.getAverageFoliageColor(levelIn, pos);
            return Helper.blendColors(color, foliage, ((Integer)state.getValue(STAGE)).intValue() / 3.0F);
         } else {
            return color;
         }
      };
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public ItemColor getItemColor() {
      return (stack, tintIndex) -> 15924992;
   }

   public void randomTick(BlockState state, ServerLevel levelIn, BlockPos pos, RandomSource random) {
      super.randomTick(state, levelIn, pos, random);
      if (!levelIn.isClientSide) {
         int stage = (Integer)state.getValue(STAGE);
         if (stage < 3) {
            levelIn.setBlockAndUpdate(pos, (BlockState)state.setValue(STAGE, stage + 1));
         }

         if (stage > 1) {
            BlockPos offset = pos.relative(Direction.getRandom(random));
            if (levelIn.isLoaded(offset)) {
               convert(levelIn, offset);
            }
         }
      }
   }

   public boolean isRandomlyTicking(BlockState p_54449_) {
      return true;
   }

   @Override
   public void generateCustomBlockState(BlockStateGenerator generator) {
      generator.simpleBlock(this, generator.models().getExistingFile(generator.modLoc(this.getBaseName())));
   }
}
