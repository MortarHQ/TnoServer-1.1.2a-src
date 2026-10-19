package net.unusual.block_factorys_bosses.procedures;

import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.unusual.block_factorys_bosses.entity.boss.dragon.boss.InfernalDragonEntity;
import net.unusual.block_factorys_bosses.entity.boss.knight.UnderworldKnightEntity;
import net.unusual.block_factorys_bosses.entity.boss.sandworm.SandwormEntity;
import net.unusual.block_factorys_bosses.entity.boss.yeti.YetiEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;
import net.unusual.block_factorys_bosses.init.BossesRiseTags;

@EventBusSubscriber
public class BossCancelDie2Procedure {
   @SubscribeEvent
   public static void onEntityAttacked(LivingIncomingDamageEvent event) {
      execute(
         event,
         event.getEntity().level(),
         event.getEntity().getX(),
         event.getEntity().getY(),
         event.getEntity().getZ(),
         event.getEntity(),
         event.getSource().getEntity(),
         event.getAmount()
      );
   }

   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity, double amount) {
      execute(null, world, x, y, z, entity, sourceentity, amount);
   }

   private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity, double amount) {
      if (entity != null && sourceentity != null) {
         if (entity.getType().is(BossesRiseTags.EntityTypes.BF_BOSS)) {
            if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1.0F) - amount <= 0.0) {
               if ((entity instanceof SandwormEntity _datEntI ? (Integer)_datEntI.getEntityData().get(SandwormEntity.DATA_DIE_ANIMTIME) : 0) == 0) {
                  if (entity instanceof SandwormEntity _datEntSetS) {
                     _datEntSetS.getEntityData().set(SandwormEntity.DATA_SOURCE_UUID, sourceentity.getStringUUID());
                  }
               } else if ((entity instanceof InfernalDragonEntity _datEntI ? (Integer)_datEntI.getEntityData().get(InfernalDragonEntity.DATA_DIE_ANIMTIME) : 0)
                  == 0) {
                  if (entity instanceof InfernalDragonEntity _datEntSetS) {
                     _datEntSetS.getEntityData().set(InfernalDragonEntity.DATA_SOURCE_UUID, sourceentity.getStringUUID());
                  }
               } else if ((entity instanceof YetiEntity _datEntI ? (Integer)_datEntI.getEntityData().get(YetiEntity.DATA_DIE_ANIMTIME) : 0) == 0) {
                  if (entity instanceof YetiEntity _datEntSetS) {
                     _datEntSetS.getEntityData().set(YetiEntity.DATA_SOURCE_UUID, sourceentity.getStringUUID());
                  }
               } else if ((
                        entity instanceof UnderworldKnightEntity _datEntI ? (Integer)_datEntI.getEntityData().get(UnderworldKnightEntity.DATA_DIE_ANIMTIME) : 0
                     )
                     == 0
                  && entity instanceof UnderworldKnightEntity _datEntSetS) {
                  _datEntSetS.getEntityData().set(UnderworldKnightEntity.DATA_SOURCE_UUID, sourceentity.getStringUUID());
               }
            }
         } else if (entity instanceof EnderDragon
            && (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == BossesRiseItems.KNIGHT_SWORD.get()
            && (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1.0F) - amount <= 0.0) {
            for (int index0 = 0; index0 < 12; index0++) {
               if (world instanceof ServerLevel _level) {
                  ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack((ItemLike)BossesRiseItems.DRAGON_SHANK.get()));
                  entityToSpawn.setPickUpDelay(10);
                  _level.addFreshEntity(entityToSpawn);
               }
            }
         }
      }
   }
}
