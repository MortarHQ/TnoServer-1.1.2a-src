package de.ellpeck.naturesaura.blocks;

import de.ellpeck.naturesaura.Helper;
import de.ellpeck.naturesaura.api.render.IVisualizable;
import de.ellpeck.naturesaura.blocks.tiles.BlockEntityAnimalGenerator;
import de.ellpeck.naturesaura.data.BlockStateGenerator;
import de.ellpeck.naturesaura.packet.PacketHandler;
import de.ellpeck.naturesaura.packet.PacketParticles;
import de.ellpeck.naturesaura.reg.ICustomBlockState;
import de.ellpeck.naturesaura.reg.IPickaxeBreakable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.Npc;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.AABB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent.Post;

public class BlockAnimalGenerator extends BlockContainerImpl implements IVisualizable, ICustomBlockState, IPickaxeBreakable {
   public BlockAnimalGenerator() {
      super("animal_generator", BlockEntityAnimalGenerator.class, Properties.of().strength(3.0F).sound(SoundType.STONE));
      NeoForge.EVENT_BUS.register(this);
   }

   @SubscribeEvent
   public void onLivingUpdate(Post event) {
      Entity entity = event.getEntity();
      if (!entity.level().isClientSide && entity.level().getGameTime() % 40L == 0L && entity instanceof Animal && !(entity instanceof Npc)) {
         CompoundTag data = entity.getPersistentData();
         int timeAlive = data.getInt("naturesaura:time_alive");
         data.putInt("naturesaura:time_alive", timeAlive + 40);
      }
   }

   @SubscribeEvent
   public void onEntityDeath(LivingDeathEvent event) {
      LivingEntity entity = event.getEntity();
      if (!entity.level().isClientSide && entity instanceof Animal && !(entity instanceof Npc)) {
         BlockPos pos = entity.blockPosition();
         Helper.getBlockEntitiesInArea(
            entity.level(),
            pos,
            5,
            tile -> {
               if (tile instanceof BlockEntityAnimalGenerator gen) {
                  CompoundTag data = entity.getPersistentData();
                  data.putBoolean("naturesaura:no_drops", true);
                  if (gen.isBusy()) {
                     return false;
                  } else {
                     boolean child = entity.isBaby();
                     float timeMod = child ? 0.5F : 1.0F;
                     float amountMod = child ? 0.667F : 1.0F;
                     int timeAlive = data.getInt("naturesaura:time_alive");
                     int time = Math.min(Mth.floor((timeAlive - 15000) / 500.0F * timeMod), 200);
                     int amount = Math.min(Mth.floor((timeAlive - 8000) / 2.0F * amountMod), 25000);
                     if (time > 0 && amount > 0) {
                        gen.setGenerationValues(time, amount);
                        BlockPos genPos = gen.getBlockPos();
                        PacketHandler.sendToAllAround(
                           entity.level(),
                           pos,
                           32,
                           new PacketParticles(
                              (float)entity.getX(),
                              (float)entity.getY(),
                              (float)entity.getZ(),
                              PacketParticles.Type.ANIMAL_GEN_CONSUME,
                              child ? 1 : 0,
                              (int)(entity.getEyeHeight() * 10.0F),
                              genPos.getX(),
                              genPos.getY(),
                              genPos.getZ()
                           )
                        );
                        return true;
                     } else {
                        return false;
                     }
                  }
               } else {
                  return false;
               }
            }
         );
      }
   }

   @SubscribeEvent
   public void onEntityDrops(LivingDropsEvent event) {
      LivingEntity entity = event.getEntity();
      if (entity.getPersistentData().getBoolean("naturesaura:no_drops")) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public void onEntityExp(LivingExperienceDropEvent event) {
      LivingEntity entity = event.getEntity();
      if (entity.getPersistentData().getBoolean("naturesaura:no_drops")) {
         event.setCanceled(true);
      }
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public AABB getVisualizationBounds(Level level, BlockPos pos) {
      return new AABB(pos).inflate(5.0);
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public int getVisualizationColor(Level level, BlockPos pos) {
      return 1128314;
   }

   @Override
   public void generateCustomBlockState(BlockStateGenerator generator) {
      generator.simpleBlock(
         this,
         generator.models()
            .cubeBottomTop(
               this.getBaseName(),
               generator.modLoc("block/" + this.getBaseName()),
               generator.modLoc("block/" + this.getBaseName() + "_bottom"),
               generator.modLoc("block/" + this.getBaseName() + "_top")
            )
      );
   }
}
