package de.ellpeck.naturesaura.blocks.tiles;

import de.ellpeck.naturesaura.Helper;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import de.ellpeck.naturesaura.api.misc.WeatherType;
import java.util.Map.Entry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.apache.commons.lang3.tuple.Pair;

public class BlockEntityWeatherChanger extends BlockEntityImpl implements ITickableBlockEntity {
   private int processTime;
   private WeatherType type;
   private int itemAmount;

   public BlockEntityWeatherChanger(BlockPos pos, BlockState state) {
      super(ModBlockEntities.WEATHER_CHANGER, pos, state);
   }

   @Override
   public void tick() {
      if (!this.level.isClientSide) {
         if (this.processTime > 0) {
            int auraToUse = 30000 * Mth.ceil(this.itemAmount * 0.75F);
            if (!this.canUseRightNow(auraToUse)) {
               this.processTime = 0;
               this.sendToClients();
               return;
            }

            if (this.processTime % 20 == 0) {
               BlockPos spot = IAuraChunk.getHighestSpot(this.level, this.worldPosition, 35, this.worldPosition);
               IAuraChunk.getAuraChunk(this.level, spot).drainAura(spot, auraToUse);
            }

            this.processTime--;
            if (this.processTime <= 0) {
               this.sendToClients();
               int time = 6000 * this.itemAmount;
               ServerLevel server = (ServerLevel)this.level;
               switch (this.type) {
                  case SUN:
                     server.setWeatherParameters(time, 0, false, false);
                     break;
                  case RAIN:
                     server.setWeatherParameters(0, time, true, false);
                     break;
                  case THUNDERSTORM:
                     server.setWeatherParameters(0, time, true, true);
               }
            }
         } else {
            if (this.level.getGameTime() % 20L != 0L) {
               return;
            }

            Pair<WeatherType, Integer> type = this.getNextWeatherType();
            if (type == null) {
               return;
            }

            this.type = (WeatherType)type.getLeft();
            this.itemAmount = (Integer)type.getRight();
            this.processTime = 100;
            this.sendToClients();
         }
      } else if (this.level.getGameTime() % 10L == 0L) {
         if (this.processTime > 0) {
            int color = this.type == WeatherType.SUN ? 16111426 : (this.type == WeatherType.RAIN ? 5069539 : 3617351);

            for (int r = 0; r < 360; r += 20) {
               double xOff = Math.cos(Math.toRadians(r)) * 3.0;
               double zOff = Math.sin(Math.toRadians(r)) * 3.0;

               for (int i = this.level.random.nextInt(3); i > 0; i--) {
                  NaturesAuraAPI.instance()
                     .spawnMagicParticle(
                        this.worldPosition.getX() + 0.5F + xOff,
                        this.worldPosition.getY(),
                        this.worldPosition.getZ() + 0.5F + zOff,
                        this.level.random.nextGaussian() * 0.02F,
                        this.level.random.nextFloat() * 0.1F + 0.1F,
                        this.level.random.nextGaussian() * 0.02F,
                        color,
                        this.level.random.nextFloat() * 2.0F + 1.0F,
                        this.level.random.nextInt(80) + 80,
                        0.0F,
                        false,
                        true
                     );
               }
            }
         }
      }
   }

   @Override
   public void writeNBT(CompoundTag compound, BlockEntityImpl.SaveType type, Provider registries) {
      super.writeNBT(compound, type, registries);
      if (type != BlockEntityImpl.SaveType.BLOCK) {
         compound.putInt("time", this.processTime);
         if (this.type != null) {
            compound.putInt("weather", this.type.ordinal());
         }

         compound.putInt("amount", this.itemAmount);
      }
   }

   @Override
   public void readNBT(CompoundTag compound, BlockEntityImpl.SaveType type, Provider registries) {
      super.readNBT(compound, type, registries);
      if (type != BlockEntityImpl.SaveType.BLOCK) {
         this.processTime = compound.getInt("time");
         this.type = WeatherType.values()[compound.getInt("weather")];
         this.itemAmount = compound.getInt("amount");
      }
   }

   @Override
   public boolean allowsLowerLimiter() {
      return true;
   }

   private Pair<WeatherType, Integer> getNextWeatherType() {
      AABB area = new AABB(this.worldPosition).inflate(2.0);

      for (ItemEntity entity : this.level.getEntitiesOfClass(ItemEntity.class, area, Entity::isAlive)) {
         if (!entity.hasPickUpDelay()) {
            ItemStack stack = entity.getItem();

            for (Entry<ItemStack, WeatherType> entry : NaturesAuraAPI.WEATHER_CHANGER_CONVERSIONS.entrySet()) {
               if (Helper.areItemsEqual(stack, entry.getKey(), true)) {
                  entity.setItem(ItemStack.EMPTY);
                  entity.kill();
                  return Pair.of(entry.getValue(), stack.getCount());
               }
            }
         }
      }

      return null;
   }
}
