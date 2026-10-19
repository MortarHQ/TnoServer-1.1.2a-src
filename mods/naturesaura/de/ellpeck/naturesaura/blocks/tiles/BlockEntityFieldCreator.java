package de.ellpeck.naturesaura.blocks.tiles;

import de.ellpeck.naturesaura.Helper;
import de.ellpeck.naturesaura.ModConfig;
import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import de.ellpeck.naturesaura.api.aura.type.IAuraType;
import de.ellpeck.naturesaura.packet.PacketHandler;
import de.ellpeck.naturesaura.packet.PacketParticleStream;
import de.ellpeck.naturesaura.reg.ModRegistry;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams.Builder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import net.neoforged.neoforge.event.level.BlockEvent.BreakEvent;

public class BlockEntityFieldCreator extends BlockEntityImpl implements ITickableBlockEntity {
   public BlockPos connectionOffset;
   public boolean isMain;
   public boolean isCharged;
   private int chargeTimer;

   public BlockEntityFieldCreator(BlockPos pos, BlockState state) {
      super(ModBlockEntities.FIELD_CREATOR, pos, state);
   }

   @Override
   public void tick() {
      if (!this.level.isClientSide && this.level.getGameTime() % 10L == 0L) {
         BlockPos connectedPos = this.getConnectedPos();
         if (connectedPos != null && this.level.isLoaded(connectedPos)) {
            BlockEntity other = this.level.getBlockEntity(connectedPos);
            if (!(this.isCloseEnough(connectedPos) && other instanceof BlockEntityFieldCreator creator)
               || !this.worldPosition.equals(((BlockEntityFieldCreator)other).getConnectedPos())) {
               this.connectionOffset = null;
               this.chargeTimer = 0;
               this.isCharged = false;
               this.isMain = false;
               this.sendToClients();
            } else if (this.isMain) {
               if ((this.redstonePower > 0 || creator.redstonePower > 0) && this.canUseRightNow(20)) {
                  BlockPos spot = IAuraChunk.getHighestSpot(this.level, this.worldPosition, 32, this.worldPosition);
                  IAuraChunk chunk = IAuraChunk.getAuraChunk(this.level, spot);
                  if (!this.isCharged) {
                     this.chargeTimer += 10;
                     if (this.chargeTimer >= 150) {
                        this.chargeTimer = 0;
                        this.isCharged = true;
                        this.sendToClients();
                        creator.isCharged = true;
                        creator.sendToClients();
                     }

                     chunk.drainAura(spot, 300);
                     this.sendParticles();
                  } else {
                     if (this.level.getGameTime() % 40L == 0L) {
                        chunk.drainAura(spot, 20);
                     }

                     ItemStack tool = this.getToolUsed(creator);
                     Vec3 dist = new Vec3(
                        this.worldPosition.getX() - connectedPos.getX(),
                        this.worldPosition.getY() - connectedPos.getY(),
                        this.worldPosition.getZ() - connectedPos.getZ()
                     );
                     double length = dist.length();
                     Vec3 normal = new Vec3(dist.x / length, dist.y / length, dist.z / length);

                     for (float i = Mth.floor(length); i > 0.0F; i -= 0.5F) {
                        Vec3 scaled = normal.scale(i);
                        BlockPos pos = connectedPos.offset(Mth.floor(scaled.x + 0.5), Mth.floor(scaled.y + 0.5), Mth.floor(scaled.z + 0.5));
                        if (!pos.equals(this.worldPosition) && !pos.equals(connectedPos)) {
                           BlockState state = this.level.getBlockState(pos);
                           if (!state.isAir() && state.getDestroySpeed(this.level, pos) >= 0.0F) {
                              FakePlayer fake = FakePlayerFactory.get((ServerLevel)this.level, ModRegistry.FAKE_PLAYER);
                              if (!((BreakEvent)NeoForge.EVENT_BUS.post(new BreakEvent(this.level, pos, state, fake))).isCanceled()) {
                                 List<ItemStack> drops = state.getDrops(
                                    new Builder((ServerLevel)this.level)
                                       .withParameter(LootContextParams.THIS_ENTITY, fake)
                                       .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos))
                                       .withParameter(LootContextParams.BLOCK_STATE, state)
                                       .withParameter(LootContextParams.TOOL, tool.isEmpty() ? new ItemStack(Items.DIAMOND_PICKAXE) : tool)
                                       .withOptionalParameter(LootContextParams.BLOCK_ENTITY, this.level.getBlockEntity(pos))
                                 );
                                 this.level.destroyBlock(pos, false);

                                 for (ItemStack stack : drops) {
                                    Block.popResource(this.level, pos, stack);
                                 }

                                 chunk.drainAura(spot, !tool.isEmpty() ? 300 : 100);
                                 this.sendParticles();
                              }
                           }
                        }
                     }
                  }
               } else {
                  this.chargeTimer = 0;
                  if (this.isCharged) {
                     this.isCharged = false;
                     this.sendToClients();
                     creator.isCharged = false;
                     creator.sendToClients();
                  }
               }
            }
         }
      }
   }

   private ItemStack getToolUsed(BlockEntityFieldCreator other) {
      ItemStack myTool = this.getMyTool();
      ItemStack otherTool = other.getMyTool();
      if (!myTool.isEmpty()) {
         if (!otherTool.isEmpty()) {
            return this.level.random.nextBoolean() ? myTool : otherTool;
         } else {
            return myTool;
         }
      } else {
         return otherTool;
      }
   }

   private ItemStack getMyTool() {
      for (ItemFrame frame : Helper.getAttachedItemFrames(this.level, this.worldPosition)) {
         ItemStack stack = frame.getItem();
         if (!stack.isEmpty()) {
            return stack;
         }
      }

      return ItemStack.EMPTY;
   }

   private void sendParticles() {
      for (int j = 0; j < 2; j++) {
         BlockPos p = j == 0 ? this.worldPosition : this.getConnectedPos();
         PacketHandler.sendToAllAround(
            this.level,
            p,
            32,
            new PacketParticleStream(
               p.getX() + (float)this.level.random.nextGaussian() * 3.0F,
               p.getY() + 1 + this.level.random.nextFloat() * 3.0F,
               p.getZ() + (float)this.level.random.nextGaussian() * 3.0F,
               p.getX() + 0.5F,
               p.getY() + 0.5F,
               p.getZ() + 0.5F,
               this.level.random.nextFloat() * 0.07F + 0.07F,
               IAuraType.forLevel(this.level).getColor(),
               this.level.random.nextFloat() + 0.5F
            )
         );
      }
   }

   public boolean isCloseEnough(BlockPos pos) {
      int range = (Integer)ModConfig.instance.fieldCreatorRange.get() + 1;
      return this.worldPosition.distSqr(pos) <= range * range;
   }

   public BlockPos getConnectedPos() {
      return this.connectionOffset == null ? null : this.worldPosition.offset(this.connectionOffset);
   }

   @Override
   public void writeNBT(CompoundTag compound, BlockEntityImpl.SaveType type, Provider registries) {
      super.writeNBT(compound, type, registries);
      if (type != BlockEntityImpl.SaveType.BLOCK) {
         if (this.connectionOffset != null) {
            compound.putLong("connection", this.connectionOffset.asLong());
         }

         compound.putBoolean("main", this.isMain);
         compound.putBoolean("charged", this.isCharged);
         if (type == BlockEntityImpl.SaveType.TILE) {
            compound.putInt("timer", this.chargeTimer);
         }
      }
   }

   @Override
   public void readNBT(CompoundTag compound, BlockEntityImpl.SaveType type, Provider registries) {
      super.readNBT(compound, type, registries);
      if (type != BlockEntityImpl.SaveType.BLOCK) {
         if (compound.contains("connection")) {
            this.connectionOffset = BlockPos.of(compound.getLong("connection"));
         } else {
            this.connectionOffset = null;
         }

         this.isMain = compound.getBoolean("main");
         this.isCharged = compound.getBoolean("charged");
         if (type == BlockEntityImpl.SaveType.TILE) {
            this.chargeTimer = compound.getInt("timer");
         }
      }
   }

   @Override
   public boolean allowsLowerLimiter() {
      return true;
   }
}
