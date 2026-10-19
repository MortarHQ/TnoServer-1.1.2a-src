package net.unusual.block_factorys_bosses.procedures;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;

public class TPStickRightclickedProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         Entity _ent = entity;
         _ent.teleportTo(
            entity.level()
               .clip(
                  new ClipContext(
                     entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(7.0)), Block.VISUAL, Fluid.NONE, entity
                  )
               )
               .getBlockPos()
               .getX(),
            entity.level()
               .clip(
                  new ClipContext(
                     entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(7.0)), Block.VISUAL, Fluid.NONE, entity
                  )
               )
               .getBlockPos()
               .getY(),
            entity.level()
               .clip(
                  new ClipContext(
                     entity.getEyePosition(1.0F), entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(7.0)), Block.VISUAL, Fluid.NONE, entity
                  )
               )
               .getBlockPos()
               .getZ()
         );
         if (_ent instanceof ServerPlayer _serverPlayer) {
            _serverPlayer.connection
               .teleport(
                  entity.level()
                     .clip(
                        new ClipContext(
                           entity.getEyePosition(1.0F),
                           entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(7.0)),
                           Block.VISUAL,
                           Fluid.NONE,
                           entity
                        )
                     )
                     .getBlockPos()
                     .getX(),
                  entity.level()
                     .clip(
                        new ClipContext(
                           entity.getEyePosition(1.0F),
                           entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(7.0)),
                           Block.VISUAL,
                           Fluid.NONE,
                           entity
                        )
                     )
                     .getBlockPos()
                     .getY(),
                  entity.level()
                     .clip(
                        new ClipContext(
                           entity.getEyePosition(1.0F),
                           entity.getEyePosition(1.0F).add(entity.getViewVector(1.0F).scale(7.0)),
                           Block.VISUAL,
                           Fluid.NONE,
                           entity
                        )
                     )
                     .getBlockPos()
                     .getZ(),
                  _ent.getYRot(),
                  _ent.getXRot()
               );
         }
      }
   }
}
