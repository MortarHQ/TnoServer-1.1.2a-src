package net.unusual.block_factorys_bosses.attachment.entity;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.network.PacketDistributor;
import net.unusual.block_factorys_bosses.network.BlockFactorysBossesModVariables;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PlayerVariables implements INBTSerializable<CompoundTag> {
   public double random = 0.0;
   public boolean boss_no_hit = false;

   public CompoundTag serializeNBT(Provider lookupProvider) {
      CompoundTag nbt = new CompoundTag();
      nbt.putDouble("random", this.random);
      nbt.putBoolean("boss_no_hit", this.boss_no_hit);
      return nbt;
   }

   public void deserializeNBT(Provider lookupProvider, CompoundTag nbt) {
      this.random = nbt.getDouble("random");
      this.boss_no_hit = nbt.getBoolean("boss_no_hit");
   }

   public void syncPlayerVariables(Entity entity) {
      if (entity instanceof ServerPlayer serverPlayer) {
         PacketDistributor.sendToPlayer(serverPlayer, new BlockFactorysBossesModVariables.PlayerVariablesSyncMessage(this), new CustomPacketPayload[0]);
      }
   }
}
