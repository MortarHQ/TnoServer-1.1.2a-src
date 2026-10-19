package de.ellpeck.naturesaura.blocks;

import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import de.ellpeck.naturesaura.api.misc.ILevelData;
import de.ellpeck.naturesaura.api.render.IVisualizable;
import de.ellpeck.naturesaura.blocks.tiles.BlockEntitySpawnLamp;
import de.ellpeck.naturesaura.data.BlockStateGenerator;
import de.ellpeck.naturesaura.misc.LevelData;
import de.ellpeck.naturesaura.packet.PacketHandler;
import de.ellpeck.naturesaura.packet.PacketParticles;
import de.ellpeck.naturesaura.reg.ICustomBlockState;
import de.ellpeck.naturesaura.reg.IPickaxeBreakable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.MobSpawnEvent.PositionCheck;
import net.neoforged.neoforge.event.entity.living.MobSpawnEvent.PositionCheck.Result;

public class BlockSpawnLamp extends BlockContainerImpl implements IVisualizable, ICustomBlockState, IPickaxeBreakable {
   private static final VoxelShape SHAPE = Shapes.create(0.25, 0.0, 0.25, 0.75, 0.8125, 0.75);

   public BlockSpawnLamp() {
      super("spawn_lamp", BlockEntitySpawnLamp.class, Properties.of().strength(3.0F).lightLevel(s -> 15).sound(SoundType.METAL));
      NeoForge.EVENT_BUS.register(this);
   }

   @Override
   protected boolean hasWaterlogging() {
      return true;
   }

   @SubscribeEvent
   public void onSpawn(PositionCheck event) {
      int amountToUse = 200;
      if (event.getSpawner() == null) {
         ServerLevelAccessor accessor = event.getLevel();
         BlockPos pos = BlockPos.containing(event.getX(), event.getY(), event.getZ());
         if (accessor instanceof Level level) {
            LevelData data = (LevelData)ILevelData.getLevelData(level);

            for (BlockEntitySpawnLamp lamp : data.spawnLamps) {
               if (!lamp.isRemoved() && lamp.canUseRightNow(amountToUse)) {
                  int range = lamp.getRadius();
                  if (range > 0) {
                     BlockPos lampPos = lamp.getBlockPos();
                     if (new AABB(lampPos).inflate(range).contains(new Vec3(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5))) {
                        Mob entity = event.getEntity();
                        if (entity.checkSpawnRules(level, event.getSpawnType()) && entity.checkSpawnObstruction(level)) {
                           BlockPos spot = IAuraChunk.getHighestSpot(level, lampPos, 32, lampPos);
                           IAuraChunk.getAuraChunk(level, spot).drainAura(spot, amountToUse);
                           PacketHandler.sendToAllAround(
                              level, lampPos, 32, new PacketParticles(lampPos.getX(), lampPos.getY(), lampPos.getZ(), PacketParticles.Type.SPAWN_LAMP)
                           );
                        }

                        event.setResult(Result.FAIL);
                        break;
                     }
                  }
               }
            }
         }
      }
   }

   public VoxelShape getShape(BlockState state, BlockGetter levelIn, BlockPos pos, CollisionContext context) {
      return SHAPE;
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public AABB getVisualizationBounds(Level level, BlockPos pos) {
      BlockEntity tile = level.getBlockEntity(pos);
      if (tile instanceof BlockEntitySpawnLamp) {
         int radius = ((BlockEntitySpawnLamp)tile).getRadius();
         if (radius > 0) {
            return new AABB(pos).inflate(radius);
         }
      }

      return null;
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public int getVisualizationColor(Level level, BlockPos pos) {
      return 8543973;
   }

   @Override
   public void generateCustomBlockState(BlockStateGenerator generator) {
      generator.simpleBlock(this, generator.models().getExistingFile(generator.modLoc(this.getBaseName())));
   }
}
