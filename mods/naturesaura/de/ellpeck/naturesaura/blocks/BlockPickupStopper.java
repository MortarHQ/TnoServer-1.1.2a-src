package de.ellpeck.naturesaura.blocks;

import de.ellpeck.naturesaura.api.misc.ILevelData;
import de.ellpeck.naturesaura.api.render.IVisualizable;
import de.ellpeck.naturesaura.blocks.tiles.BlockEntityPickupStopper;
import de.ellpeck.naturesaura.data.BlockStateGenerator;
import de.ellpeck.naturesaura.misc.LevelData;
import de.ellpeck.naturesaura.packet.PacketHandler;
import de.ellpeck.naturesaura.packet.PacketParticles;
import de.ellpeck.naturesaura.reg.ICustomBlockState;
import de.ellpeck.naturesaura.reg.IPickaxeBreakable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.AABB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent.Pre;

public class BlockPickupStopper extends BlockContainerImpl implements IVisualizable, ICustomBlockState, IPickaxeBreakable {
   public BlockPickupStopper() {
      super("pickup_stopper", BlockEntityPickupStopper.class, Properties.of().strength(2.0F).sound(SoundType.STONE));
      NeoForge.EVENT_BUS.register(this);
   }

   @SubscribeEvent
   public void onPickup(Pre event) {
      Player player = event.getPlayer();
      if (player != null && !player.isShiftKeyDown()) {
         ItemEntity item = event.getItemEntity();
         LevelData data = (LevelData)ILevelData.getLevelData(item.level());

         for (BlockEntityPickupStopper tile : data.pickupStoppers) {
            float radius = tile.getRadius();
            if (!(radius <= 0.0F)) {
               BlockPos stopperPos = tile.getBlockPos();
               if (new AABB(stopperPos).inflate(radius).intersects(item.getBoundingBox())) {
                  event.setCanPickup(TriState.FALSE);
                  if (item.level().getGameTime() % 3L == 0L) {
                     PacketHandler.sendToAllAround(
                        item.level(),
                        item.blockPosition(),
                        32,
                        new PacketParticles((float)item.getX(), (float)item.getY(), (float)item.getZ(), PacketParticles.Type.PICKUP_STOPPER)
                     );
                  }
                  break;
               }
            }
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public AABB getVisualizationBounds(Level level, BlockPos pos) {
      BlockEntity tile = level.getBlockEntity(pos);
      if (tile instanceof BlockEntityPickupStopper) {
         double radius = ((BlockEntityPickupStopper)tile).getRadius();
         if (radius > 0.0) {
            return new AABB(pos).inflate(radius);
         }
      }

      return null;
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public int getVisualizationColor(Level level, BlockPos pos) {
      return 16034370;
   }

   @Override
   public void generateCustomBlockState(BlockStateGenerator generator) {
      generator.simpleBlock(
         this,
         generator.models()
            .cubeBottomTop(
               this.getBaseName(),
               generator.modLoc("block/" + this.getBaseName()),
               generator.modLoc("block/" + this.getBaseName() + "_top"),
               generator.modLoc("block/" + this.getBaseName() + "_top")
            )
      );
   }
}
