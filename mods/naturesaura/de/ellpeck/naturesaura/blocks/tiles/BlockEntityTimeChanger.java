package de.ellpeck.naturesaura.blocks.tiles;

import de.ellpeck.naturesaura.Helper;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import de.ellpeck.naturesaura.api.aura.type.IAuraType;
import de.ellpeck.naturesaura.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.players.PlayerList;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.phys.AABB;

public class BlockEntityTimeChanger extends BlockEntityImpl implements ITickableBlockEntity {
   private long goalTime;

   public BlockEntityTimeChanger(BlockPos pos, BlockState state) {
      super(ModBlockEntities.TIME_CHANGER, pos, state);
   }

   @Override
   public void tick() {
      if (!this.level.isClientSide) {
         for (ItemFrame frame : Helper.getAttachedItemFrames(this.level, this.worldPosition)) {
            ItemStack frameStack = frame.getItem();
            if (!frameStack.isEmpty() && frameStack.getItem() == ModItems.CLOCK_HAND) {
               if (this.goalTime > 0L) {
                  long current = this.level.getDayTime();
                  long toAdd = Math.min(75L, this.goalTime - current);
                  if (toAdd > 0L && this.canUseRightNow((int)toAdd * 20)) {
                     ((ServerLevelData)this.level.getLevelData()).setDayTime(current + toAdd);
                     BlockPos spot = IAuraChunk.getHighestSpot(this.level, this.worldPosition, 35, this.worldPosition);
                     IAuraChunk.getAuraChunk(this.level, spot).drainAura(spot, (int)toAdd * 20);
                     if (this.level instanceof ServerLevel) {
                        PlayerList list = this.level.getServer().getPlayerList();
                        list.broadcastAll(
                           new ClientboundSetTimePacket(
                              this.level.getGameTime(), this.level.getDayTime(), this.level.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)
                           )
                        );
                     }

                     return;
                  }

                  this.goalTime = 0L;
                  this.sendToClients();
                  return;
               }

               if (this.level.getGameTime() % 20L != 0L) {
                  return;
               }

               for (ItemEntity item : this.level.getEntitiesOfClass(ItemEntity.class, new AABB(this.worldPosition).inflate(1.0), Entity::isAlive)) {
                  if (!item.hasPickUpDelay()) {
                     ItemStack stack = item.getItem();
                     if (!stack.isEmpty() && stack.getItem() == Items.CLOCK) {
                        int dayGoal = Mth.floor(frame.getRotation() / 8.0F * 24000.0F) + 18000;
                        long current = this.level.getDayTime();
                        long toMove = (24000L - current % 24000L + dayGoal) % 24000L;
                        this.goalTime = current + toMove;
                        this.sendToClients();
                        if (stack.getCount() <= 1) {
                           item.kill();
                        } else {
                           stack.shrink(1);
                           item.setItem(stack);
                        }

                        return;
                     }
                  }
               }
            }
         }

         if (this.goalTime > 0L) {
            this.goalTime = 0L;
            this.sendToClients();
         }
      } else if (this.goalTime > 0L && this.level.random.nextFloat() >= 0.25F) {
         double angle = Math.toRadians((float)this.level.getDayTime() * 5.0F % 360.0F);
         double x = this.worldPosition.getX() + 0.5 + Math.sin(angle) * 3.0;
         double z = this.worldPosition.getZ() + 0.5 + Math.cos(angle) * 3.0;
         int color = this.goalTime % 24000L > 12000L ? 14869218 : 16771366;
         NaturesAuraAPI.instance()
            .spawnMagicParticle(
               x,
               this.worldPosition.getY() + 0.1F,
               z,
               0.0,
               0.12F,
               0.0,
               color,
               1.0F + this.level.random.nextFloat() * 2.0F,
               this.level.random.nextInt(100) + 100,
               0.0F,
               false,
               true
            );
         NaturesAuraAPI.instance()
            .spawnMagicParticle(
               x,
               this.worldPosition.getY() + 0.1F,
               z,
               0.0,
               0.0,
               0.0,
               IAuraType.forLevel(this.level).getColor(),
               1.0F + this.level.random.nextFloat(),
               150,
               0.0F,
               false,
               true
            );
      }
   }

   @Override
   public void writeNBT(CompoundTag compound, BlockEntityImpl.SaveType type, Provider registries) {
      super.writeNBT(compound, type, registries);
      if (type != BlockEntityImpl.SaveType.BLOCK) {
         compound.putLong("goal", this.goalTime);
      }
   }

   @Override
   public void readNBT(CompoundTag compound, BlockEntityImpl.SaveType type, Provider registries) {
      super.readNBT(compound, type, registries);
      if (type != BlockEntityImpl.SaveType.BLOCK) {
         this.goalTime = compound.getLong("goal");
      }
   }

   @Override
   public boolean allowsLowerLimiter() {
      return true;
   }
}
