package twilightforest.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.gameevent.GameEvent;
import twilightforest.entity.monster.BlockChainGoblin;
import twilightforest.init.TFSounds;

public class SpikeBlock extends BlockChainGoblin.MultipartGenericsAreDumb {
   private final Entity goblin;
   private boolean isCollideBlock;

   public SpikeBlock(Entity goblin) {
      super(goblin);
      this.goblin = goblin;
      this.setSize(EntityDimensions.scalable(0.75F, 0.75F));
   }

   @Override
   public void tick() {
      super.tick();
      if (!this.goblin.isAlive()) {
         this.doFall();
      }
   }

   public void doFall() {
      if (this.onGround() && !this.isCollideBlock) {
         this.playSound((SoundEvent)TFSounds.BLOCK_AND_CHAIN_COLLIDE.get(), 0.65F, 0.75F);
         this.gameEvent(GameEvent.HIT_GROUND);
         this.isCollideBlock = true;
      } else {
         this.setDeltaMovement(0.0, this.getDeltaMovement().y() - 0.04F, 0.0);
         this.move(MoverType.SELF, this.getDeltaMovement());
      }
   }

   public boolean hurt(DamageSource source, float amount) {
      return false;
   }

   @Override
   public boolean isPickable() {
      return false;
   }

   public boolean isPushable() {
      return false;
   }

   public boolean is(Entity entity) {
      return this == entity || this.getParent() == entity;
   }

   public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity entity) {
      throw new UnsupportedOperationException();
   }

   protected void defineSynchedData(Builder builder) {
   }

   protected boolean canRide(Entity entity) {
      return false;
   }

   protected void readAdditionalSaveData(CompoundTag compound) {
   }

   protected void addAdditionalSaveData(CompoundTag compound) {
   }
}
