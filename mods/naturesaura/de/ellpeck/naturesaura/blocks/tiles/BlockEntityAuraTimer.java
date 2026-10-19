package de.ellpeck.naturesaura.blocks.tiles;

import com.google.common.collect.ImmutableMap;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.api.aura.type.IAuraType;
import de.ellpeck.naturesaura.items.ItemAuraBottle;
import de.ellpeck.naturesaura.items.ModItems;
import de.ellpeck.naturesaura.packet.PacketHandler;
import de.ellpeck.naturesaura.packet.PacketParticles;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class BlockEntityAuraTimer extends BlockEntityImpl implements ITickableBlockEntity {
   private static final Map<IAuraType, Integer> TIMES = ImmutableMap.builder()
      .put(NaturesAuraAPI.TYPE_OVERWORLD, 20)
      .put(NaturesAuraAPI.TYPE_NETHER, 1200)
      .put(NaturesAuraAPI.TYPE_END, 72000)
      .build();
   public final ItemStackHandlerNA itemHandler = new ItemStackHandlerNA(1, this, true) {
      @Override
      protected boolean canInsert(ItemStack stack, int slot) {
         return stack.getItem() == ModItems.AURA_BOTTLE;
      }
   };
   private int timer;

   public BlockEntityAuraTimer(BlockPos pos, BlockState state) {
      super(ModBlockEntities.AURA_TIMER, pos, state);
   }

   @Override
   public void onRedstonePowerChange(int newPower) {
      if (this.redstonePower <= 0 && newPower > 0) {
         this.timer = 0;
         int color = ItemAuraBottle.getType(this.itemHandler.getStackInSlot(0)).getColor();
         PacketHandler.sendToAllAround(
            this.level,
            this.worldPosition,
            32,
            new PacketParticles(this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ(), PacketParticles.Type.TIMER_RESET, color)
         );
         this.sendToClients();
      }

      super.onRedstonePowerChange(newPower);
   }

   @Override
   public void tick() {
      int total = this.getTotalTime();
      if (total <= 0) {
         this.timer = 0;
      } else if (this.level.isClientSide) {
         if (this.level.getGameTime() % 8L == 0L) {
            int color = ItemAuraBottle.getType(this.itemHandler.getStackInSlot(0)).getColor();
            NaturesAuraAPI.instance()
               .spawnMagicParticle(
                  this.worldPosition.getX() + 0.0625F + this.level.random.nextFloat() * 14.0F / 16.0F,
                  this.worldPosition.getY() + 0.0625F + this.level.random.nextFloat() * 14.0F / 16.0F,
                  this.worldPosition.getZ() + 0.0625F + this.level.random.nextFloat() * 14.0F / 16.0F,
                  0.0,
                  0.0,
                  0.0,
                  color,
                  1.0F,
                  80 + this.level.random.nextInt(50),
                  0.0F,
                  false,
                  true
               );
         }
      } else {
         this.timer++;
         if (this.timer >= total) {
            this.timer = 0;
            BlockState state = this.getBlockState();
            this.level.setBlock(this.worldPosition, (BlockState)state.setValue(BlockStateProperties.POWERED, true), 1);
            this.level.scheduleTick(this.worldPosition, state.getBlock(), 4);
            int color = ItemAuraBottle.getType(this.itemHandler.getStackInSlot(0)).getColor();
            PacketHandler.sendToAllAround(
               this.level,
               this.worldPosition,
               32,
               new PacketParticles(this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ(), PacketParticles.Type.TIMER_RESET, color)
            );
         }

         if (this.timer % 2 == 0) {
            this.sendToClients();
         }
      }
   }

   public int getTotalTime() {
      ItemStack stack = this.itemHandler.getStackInSlot(0);
      if (stack.isEmpty()) {
         return 0;
      }

      Integer amount = TIMES.get(ItemAuraBottle.getType(stack));
      return amount == null ? 0 : amount * stack.getCount();
   }

   public int getTimeLeft() {
      return this.getTotalTime() - this.timer;
   }

   public float getTimerPercentage() {
      return (float)this.timer / this.getTotalTime();
   }

   @Override
   public void writeNBT(CompoundTag compound, BlockEntityImpl.SaveType type, Provider registries) {
      super.writeNBT(compound, type, registries);
      if (type != BlockEntityImpl.SaveType.BLOCK) {
         compound.put("items", this.itemHandler.serializeNBT(registries));
         compound.putInt("timer", this.timer);
      }
   }

   @Override
   public void readNBT(CompoundTag compound, BlockEntityImpl.SaveType type, Provider registries) {
      super.readNBT(compound, type, registries);
      if (type != BlockEntityImpl.SaveType.BLOCK) {
         this.itemHandler.deserializeNBT(registries, compound.getCompound("items"));
         this.timer = compound.getInt("timer");
      }
   }
}
