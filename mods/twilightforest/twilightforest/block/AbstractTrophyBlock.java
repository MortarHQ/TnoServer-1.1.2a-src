package twilightforest.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.network.PacketDistributor;
import twilightforest.block.entity.TrophyBlockEntity;
import twilightforest.enums.BossVariant;
import twilightforest.init.TFBlockEntities;
import twilightforest.init.TFItems;
import twilightforest.init.TFParticleType;
import twilightforest.init.TFSounds;
import twilightforest.network.ParticlePacket;

public abstract class AbstractTrophyBlock extends BaseEntityBlock implements Equipable {
   public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
   private final BossVariant variant;
   private final int comparatorValue;

   protected AbstractTrophyBlock(BossVariant variant, int value, Properties builder) {
      super(builder);
      this.variant = variant;
      this.comparatorValue = value;
      this.registerDefaultState((BlockState)((BlockState)this.getStateDefinition().any()).setValue(POWERED, false));
   }

   public int getComparatorValue() {
      return this.comparatorValue;
   }

   public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
      if (!level.isClientSide()) {
         boolean flag = level.hasNeighborSignal(pos);
         if (flag != (Boolean)state.getValue(POWERED)) {
            if (flag) {
               this.playSound(level, pos);
            }

            level.setBlockAndUpdate(pos, (BlockState)state.setValue(POWERED, flag));
         }
      }
   }

   protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
      this.playSound(level, pos);
      this.createParticle(level, pos);
      return InteractionResult.sidedSuccess(level.isClientSide());
   }

   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new TrophyBlockEntity(pos, state);
   }

   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
      return createTickerHelper(type, (BlockEntityType)TFBlockEntities.TROPHY.get(), TrophyBlockEntity::tick);
   }

   public BossVariant getVariant() {
      return this.variant;
   }

   protected boolean isPathfindable(BlockState state, PathComputationType type) {
      return false;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{POWERED});
   }

   public void playSound(Level level, BlockPos pos) {
      BlockEntity te = level.getBlockEntity(pos);
      if (!level.isClientSide() && te instanceof TrophyBlockEntity) {
         SoundEvent sound = null;
         float volume = 1.0F;
         float pitch = 0.9F;
         switch (this.variant) {
            case NAGA:
               sound = (SoundEvent)TFSounds.NAGA_RATTLE.get();
               volume = 1.25F;
               pitch = 1.2F;
               break;
            case LICH:
               sound = (SoundEvent)TFSounds.LICH_AMBIENT.get();
               volume = 0.35F;
               pitch = 1.1F;
               break;
            case HYDRA:
               sound = (SoundEvent)TFSounds.HYDRA_GROWL.get();
               pitch = 1.2F;
               break;
            case UR_GHAST:
               sound = (SoundEvent)TFSounds.UR_GHAST_AMBIENT.get();
               pitch = 0.6F;
               break;
            case SNOW_QUEEN:
               sound = (SoundEvent)TFSounds.SNOW_QUEEN_AMBIENT.get();
               break;
            case KNIGHT_PHANTOM:
               sound = (SoundEvent)TFSounds.KNIGHT_PHANTOM_AMBIENT.get();
               pitch = 1.1F;
               break;
            case MINOSHROOM:
               sound = (SoundEvent)TFSounds.MINOSHROOM_AMBIENT.get();
               volume = 0.75F;
               pitch = 0.7F;
               break;
            case ALPHA_YETI:
               sound = level.getRandom().nextInt(50) == 0 ? (SoundEvent)TFSounds.ALPHA_YETI_ROAR.get() : (SoundEvent)TFSounds.ALPHA_YETI_GROWL.get();
               volume = 0.75F;
               pitch = 0.75F;
               break;
            case QUEST_RAM:
               sound = (SoundEvent)TFSounds.QUEST_RAM_AMBIENT.get();
               pitch = 0.7F;
         }

         if (sound != null) {
            level.playSound(null, pos, sound, SoundSource.BLOCKS, volume, level.getRandom().nextFloat() * 0.1F + pitch);
         }
      }
   }

   public void createParticle(Level level, BlockPos pos) {
      if (level.getBlockEntity(pos) instanceof TrophyBlockEntity && level instanceof ServerLevel server) {
         RandomSource rand = level.getRandom();
         ParticlePacket particlePacket = new ParticlePacket();
         switch (this.variant) {
            case NAGA:
               for (int daze = 0; daze < 10; daze++) {
                  particlePacket.queueParticle(
                     ParticleTypes.CRIT,
                     false,
                     pos.getX() + rand.nextFloat() * 0.5 * 2.0,
                     pos.getY() + 0.25,
                     pos.getZ() + rand.nextFloat() * 0.5 * 2.0,
                     rand.nextGaussian() * 0.02,
                     rand.nextGaussian() * 0.02,
                     rand.nextGaussian() * 0.02
                  );
               }
               break;
            case LICH:
               for (int a = 0; a < 5; a++) {
                  particlePacket.queueParticle(
                     (ParticleOptions)TFParticleType.ANGRY_LICH.get(),
                     false,
                     pos.getX() + rand.nextFloat() * 0.5 * 2.0 + rand.nextGaussian() * 0.02,
                     pos.getY() + 0.5 + rand.nextFloat() * 0.25 + rand.nextGaussian() * 0.02,
                     pos.getZ() + rand.nextFloat() * 0.5 * 2.0 + rand.nextGaussian() * 0.02,
                     0.0,
                     0.0,
                     0.0
                  );
               }
            case HYDRA:
            default:
               break;
            case UR_GHAST:
               for (int red = 0; red < 10; red++) {
                  particlePacket.queueParticle(
                     DustParticleOptions.REDSTONE,
                     false,
                     pos.getX() + rand.nextDouble() * 1.0,
                     pos.getY() + rand.nextDouble() * 0.5 + 0.5,
                     pos.getZ() + rand.nextDouble() * 1.0,
                     0.0,
                     0.0,
                     0.0
                  );
               }
               break;
            case SNOW_QUEEN:
               for (int b = 0; b < 20; b++) {
                  particlePacket.queueParticle(
                     (ParticleOptions)TFParticleType.SNOW_WARNING.get(),
                     false,
                     pos.getX() - 1.0 + rand.nextDouble() * 3.25,
                     pos.getY() + 5.0 + rand.nextGaussian(),
                     pos.getZ() - 1.0 + rand.nextDouble() * 3.25,
                     0.0,
                     0.0,
                     0.0
                  );
               }
               break;
            case KNIGHT_PHANTOM:
               ParticleOptions knightPhantomParticle = new ItemParticleOption(ParticleTypes.ITEM, new ItemStack((ItemLike)TFItems.KNIGHTMETAL_SWORD.get()));

               for (int brek = 0; brek < 10; brek++) {
                  particlePacket.queueParticle(
                     knightPhantomParticle,
                     false,
                     pos.getX() + 0.5 + (rand.nextFloat() - 0.5),
                     pos.getY() + rand.nextFloat() + 0.5 + 0.25 * rand.nextGaussian(),
                     pos.getZ() + 0.5 + (rand.nextFloat() - 0.5),
                     0.0,
                     0.0,
                     0.0
                  );
               }
               break;
            case MINOSHROOM:
               ParticleOptions minoshroomParticle = new BlockParticleOption(ParticleTypes.BLOCK, level.getBlockState(pos.below()));

               for (int g = 0; g < 10; g++) {
                  particlePacket.queueParticle(
                     minoshroomParticle,
                     false,
                     (double)pos.getX() + rand.nextFloat() * 10.0F - 5.0,
                     pos.getY() + 0.1F + rand.nextFloat() * 0.3F,
                     (double)pos.getZ() + rand.nextFloat() * 10.0F - 5.0,
                     0.0,
                     0.0,
                     0.0
                  );
               }
               break;
            case ALPHA_YETI:
               for (int sweat = 0; sweat < 10; sweat++) {
                  particlePacket.queueParticle(
                     ParticleTypes.SPLASH,
                     false,
                     pos.getX() + rand.nextDouble() * 1.0,
                     pos.getY() + rand.nextDouble() * 0.5 + 0.5,
                     pos.getZ() + rand.nextDouble() * 1.0,
                     0.0,
                     0.0,
                     0.0
                  );
               }
               break;
            case QUEST_RAM:
               for (int p = 0; p < 10; p++) {
                  particlePacket.queueParticle(
                     ColorParticleOption.create((ParticleType)TFParticleType.MAGIC_EFFECT.get(), rand.nextFloat(), rand.nextFloat(), rand.nextFloat()),
                     false,
                     pos.getX() + 0.5 + (rand.nextDouble() - 0.5),
                     pos.getY() + (rand.nextDouble() - 0.5),
                     pos.getZ() + 0.5 + (rand.nextDouble() - 0.5),
                     rand.nextGaussian(),
                     rand.nextGaussian(),
                     rand.nextGaussian()
                  );
               }
         }

         PacketDistributor.sendToPlayersNear(server, null, pos.getX(), pos.getY(), pos.getZ(), 32.0, particlePacket, new CustomPacketPayload[0]);
      }
   }

   public EquipmentSlot getEquipmentSlot() {
      return EquipmentSlot.HEAD;
   }
}
