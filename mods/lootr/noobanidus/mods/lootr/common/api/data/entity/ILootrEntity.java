package noobanidus.mods.lootr.common.api.data.entity;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import noobanidus.mods.lootr.common.api.LootrAPI;
import noobanidus.mods.lootr.common.api.PlatformAPI;
import noobanidus.mods.lootr.common.api.data.ILootrInfo;
import noobanidus.mods.lootr.common.api.data.ILootrInfoProvider;

public interface ILootrEntity extends ILootrInfoProvider {
   @Deprecated
   @Override
   default ILootrInfo.LootrInfoType getInfoType() {
      return ILootrInfo.LootrInfoType.CONTAINER_ENTITY;
   }

   default Entity asEntity() {
      if (this instanceof Entity entity) {
         return entity;
      } else {
         throw new NullPointerException("ILootrEntity implementation is not an Entity and doesn't provide asEntity()!");
      }
   }

   @Override
   default void performOpen(ServerPlayer player) {
      PlatformAPI.performEntityOpen(this, player);
   }

   @Override
   default void performOpen() {
      PlatformAPI.performEntityOpen(this);
   }

   @Override
   default void performClose(ServerPlayer player) {
      PlatformAPI.performEntityClose(this, player);
   }

   @Override
   default void performClose() {
      PlatformAPI.performEntityClose(this);
   }

   @Override
   default void performDecay() {
      Level level = this.getInfoLevel();
      if (level != null && !level.isClientSide()) {
         boolean replaceWhenDecayed = LootrAPI.shouldReplaceWhenDecayed();
         Entity entity = this.asEntity();
         if (replaceWhenDecayed) {
            EntityType<?> type = this.getReplacementEntity();
            if (type != null) {
               Entity newCart = type.create(level);
               if (newCart != null) {
                  newCart.setPos(entity.position());
                  newCart.setXRot(entity.getXRot());
                  newCart.setYRot(entity.getYRot());
                  level.addFreshEntity(newCart);
               }
            }
         }

         entity.discard();
      }
   }

   @Override
   default Vec3 getParticleCenter() {
      return this.asEntity().position().subtract(0.5, 0.0, 0.5);
   }
}
