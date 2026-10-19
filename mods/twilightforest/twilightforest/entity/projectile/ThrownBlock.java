package twilightforest.entity.projectile;

import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;
import twilightforest.entity.monster.Troll;
import twilightforest.init.TFDamageTypes;
import twilightforest.init.TFEntities;

public class ThrownBlock extends TFThrowable {
   private BlockState state = Blocks.STONE.defaultBlockState();

   public ThrownBlock(EntityType<? extends TFThrowable> type, Level worldIn) {
      super(type, worldIn);
   }

   public ThrownBlock(Level world, LivingEntity thrower, @Nullable BlockState state) {
      super((EntityType<? extends TFThrowable>)TFEntities.THROWN_BLOCK.get(), world, thrower);
      if (state != null) {
         this.state = state;
      }
   }

   protected void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      tag.put("BlockState", NbtUtils.writeBlockState(this.state));
   }

   protected void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      this.state = NbtUtils.readBlockState(this.level().holderLookup(Registries.BLOCK), tag.getCompound("BlockState"));
   }

   public void handleEntityEvent(byte id) {
      if (id == 3) {
         ParticleOptions particle = new BlockParticleOption(ParticleTypes.BLOCK, this.state);

         for (int i = 0; i < 20; i++) {
            this.level()
               .addParticle(
                  particle,
                  false,
                  this.getX(),
                  this.getY(),
                  this.getZ(),
                  this.random.nextGaussian() * 0.05,
                  this.random.nextDouble() * 0.2,
                  this.random.nextGaussian() * 0.05
               );
         }
      } else {
         super.handleEntityEvent(id);
      }
   }

   protected void onHitEntity(EntityHitResult result) {
      super.onHitEntity(result);
      if (result.getEntity() instanceof LivingEntity living && !(living instanceof Troll) && !this.level().isClientSide()) {
         living.hurt(TFDamageTypes.getDamageSource(this.level(), TFDamageTypes.THROWN_BLOCK), 6.0F);
         this.level().broadcastEntityEvent(this, (byte)3);
         this.discard();
      }
   }

   protected void onHitBlock(BlockHitResult result) {
      super.onHitBlock(result);
      if (!this.level().isClientSide()) {
         this.level().broadcastEntityEvent(this, (byte)3);
         this.gameEvent(GameEvent.BLOCK_DESTROY, this.getOwner());
         this.discard();
      }
   }

   public BlockState getBlockState() {
      return this.state;
   }

   public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity entity) {
      return new ClientboundAddEntityPacket(this, entity, Block.getId(this.getBlockState()));
   }

   public Component getTypeName() {
      return this.getBlockState().getBlock().getName();
   }

   public void recreateFromPacket(ClientboundAddEntityPacket packet) {
      super.recreateFromPacket(packet);
      this.state = Block.stateById(packet.getData());
   }
}
