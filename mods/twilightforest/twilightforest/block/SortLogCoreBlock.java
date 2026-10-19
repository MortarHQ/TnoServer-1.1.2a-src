package twilightforest.block;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.capabilities.Capabilities.ItemHandler;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.network.PacketDistributor;
import twilightforest.config.TFConfig;
import twilightforest.data.tags.EntityTagGenerator;
import twilightforest.init.TFParticleType;
import twilightforest.network.ParticlePacket;
import twilightforest.util.BlockCapabilityDirectionalCache;
import twilightforest.util.WorldUtil;

public class SortLogCoreBlock extends SpecialMagicLogBlock {
   private final BlockCapabilityDirectionalCache<IItemHandler> capabilityCache = new BlockCapabilityDirectionalCache<>();

   public SortLogCoreBlock(Properties properties) {
      super(properties);
   }

   @Override
   public boolean doesCoreFunction() {
      return !TFConfig.disableSortingCore;
   }

   @Override
   void performTreeEffect(ServerLevel level, BlockPos pos, RandomSource rand) {
      Map<List<IItemHandler>, Vec3> inputMap = new HashMap<>();
      Map<IItemHandler, Vec3> outputMap = new HashMap<>();

      for (BlockPos blockPos : WorldUtil.getAllAround(pos, TFConfig.sortingCoreRange)) {
         if (!blockPos.equals(pos)) {
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            if (blockEntity != null) {
               if (Math.abs(blockPos.getX() - pos.getX()) <= 2 && Math.abs(blockPos.getY() - pos.getY()) <= 2 && Math.abs(blockPos.getZ() - pos.getZ()) <= 2) {
                  List<IItemHandler> handlers = new ArrayList<>();

                  for (Direction side : Direction.values()) {
                     IItemHandler handler = this.capabilityCache.get(ItemHandler.BLOCK, level, blockPos, side);
                     if (handler != null) {
                        handlers.add(handler);
                     }
                  }

                  if (!handlers.isEmpty()) {
                     inputMap.put(handlers, Vec3.upFromBottomCenterOf(blockPos, 1.9));
                  }
               } else {
                  for (Direction side : Direction.values()) {
                     IItemHandler handler = this.capabilityCache.get(ItemHandler.BLOCK, level, blockPos, side);
                     if (handler != null) {
                        outputMap.put(handler, Vec3.upFromBottomCenterOf(blockPos, 1.9));
                     }
                  }
               }
            }
         }
      }

      List<Entity> alreadyUsedForInput = new ArrayList<>();
      level.getEntities((Entity)null, new AABB(pos).inflate(2.0), entity -> entity.isAlive() && entity.getType().is(EntityTagGenerator.SORTABLE_ENTITIES))
         .forEach(entity -> {
            List<IItemHandler> handlers = new ArrayList<>();

            for (Direction side : Direction.values()) {
               IItemHandler handler = (IItemHandler)entity.getCapability(ItemHandler.ENTITY_AUTOMATION, side);
               if (handler != null) {
                  handlers.add(handler);
               }
            }

            if (!handlers.isEmpty()) {
               inputMap.put(handlers, entity.position().add(0.0, entity.getBbHeight() + 0.9, 0.0));
               alreadyUsedForInput.add(entity);
            }
         });
      if (!inputMap.isEmpty()) {
         level.getEntities(
               (Entity)null,
               new AABB(pos).inflate(16.0),
               entity -> entity.isAlive() && !alreadyUsedForInput.contains(entity) && entity.getType().is(EntityTagGenerator.SORTABLE_ENTITIES)
            )
            .forEach(entity -> {
               for (Direction side : Direction.values()) {
                  IItemHandler handler = (IItemHandler)entity.getCapability(ItemHandler.ENTITY_AUTOMATION, side);
                  if (handler != null) {
                     outputMap.put(handler, entity.position().add(0.0, entity.getBbHeight() + 0.9, 0.0));
                  }
               }
            });
         if (!outputMap.isEmpty()) {
            for (Entry<List<IItemHandler>, Vec3> inputHandlers : inputMap.entrySet()) {
               boolean transferred = false;

               for (IItemHandler inputIItemHandler : inputHandlers.getKey()) {
                  for (int i = 0; i < inputIItemHandler.getSlots(); i++) {
                     ItemStack inputStack = inputIItemHandler.extractItem(i, 1, true);
                     if (!inputStack.isEmpty()) {
                        Map<Integer, IItemHandler> outputsByCount = new HashMap<>();

                        for (IItemHandler outputIItemHandler : outputMap.keySet()) {
                           int count = 0;

                           for (int j = 0; j < outputIItemHandler.getSlots(); j++) {
                              ItemStack stack = outputIItemHandler.getStackInSlot(j);
                              if (stack.is(inputStack.getItem())) {
                                 count += stack.getCount();
                              }
                           }

                           if (count > 0) {
                              outputsByCount.put(count, outputIItemHandler);
                           }
                        }

                        for (Integer count : outputsByCount.keySet().stream().sorted(Comparator.comparingInt(Integer::intValue).reversed()).toList()) {
                           IItemHandler outputIItemHandler = outputsByCount.get(count);
                           int firstProperStack = -1;

                           for (int j = 0; j < outputIItemHandler.getSlots(); j++) {
                              if (outputIItemHandler.isItemValid(j, inputStack)) {
                                 ItemStack outputStack = outputIItemHandler.getStackInSlot(j);
                                 if (firstProperStack == -1 && outputStack.isEmpty()) {
                                    firstProperStack = j;
                                 } else if (ItemStack.isSameItemSameComponents(inputStack, outputStack)
                                    && outputStack.getCount() < outputStack.getMaxStackSize()
                                    && outputStack.getCount() < outputIItemHandler.getSlotLimit(j)) {
                                    firstProperStack = j;
                                    break;
                                 }
                              }
                           }

                           if (firstProperStack != -1) {
                              ItemStack newStack = inputIItemHandler.extractItem(i, 1, false);
                              if (!newStack.isEmpty() && outputIItemHandler.insertItem(firstProperStack, newStack, true).isEmpty()) {
                                 outputIItemHandler.insertItem(firstProperStack, newStack, false);
                                 transferred = true;
                                 Vec3 xyz = outputMap.get(outputIItemHandler);
                                 Vec3 diff = inputHandlers.getValue().subtract(xyz);
                                 ParticlePacket particlePacket = new ParticlePacket();
                                 double x = diff.x - 0.25 + rand.nextDouble() * 0.5;
                                 double y = diff.y - 1.75 + rand.nextDouble() * 0.5;
                                 double z = diff.z - 0.25 + rand.nextDouble() * 0.5;
                                 particlePacket.queueParticle(
                                    (ParticleOptions)TFParticleType.SORTING_PARTICLE.get(), false, xyz, new Vec3(x, y, z).scale(1.0 / diff.length())
                                 );
                                 PacketDistributor.sendToPlayersNear(level, null, xyz.x(), xyz.y(), xyz.z(), 64.0, particlePacket, new CustomPacketPayload[0]);
                                 break;
                              }
                           }
                        }
                     }

                     if (transferred) {
                        break;
                     }
                  }

                  if (transferred) {
                     break;
                  }
               }
            }
         }
      }
   }
}
