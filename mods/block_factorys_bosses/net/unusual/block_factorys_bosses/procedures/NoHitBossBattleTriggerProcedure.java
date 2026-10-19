package net.unusual.block_factorys_bosses.procedures;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.unusual.block_factorys_bosses.attachment.entity.PlayerVariables;
import net.unusual.block_factorys_bosses.entity.boss.knight.UnderworldKnightEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseDataAttachments;
import net.unusual.block_factorys_bosses.init.BossesRiseTags;

@EventBusSubscriber
public class NoHitBossBattleTriggerProcedure {
   @SubscribeEvent
   public static void onEntityAttacked(LivingIncomingDamageEvent event) {
      if (event.getEntity() != null) {
         execute(
            event,
            event.getEntity().level(),
            event.getEntity().getX(),
            event.getEntity().getY(),
            event.getEntity().getZ(),
            event.getEntity(),
            event.getSource().getDirectEntity(),
            event.getSource().getEntity()
         );
      }
   }

   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity immediatesourceentity, Entity sourceentity) {
      execute(null, world, x, y, z, entity, immediatesourceentity, sourceentity);
   }

   private static void execute(
      @Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity, Entity immediatesourceentity, Entity sourceentity
   ) {
      if (entity != null && immediatesourceentity != null && sourceentity != null) {
         if (sourceentity.getType().is(BossesRiseTags.EntityTypes.BF_BOSS)) {
            PlayerVariables _vars = (PlayerVariables)entity.getData(BossesRiseDataAttachments.PLAYER_VARIABLES);
            _vars.boss_no_hit = false;
            _vars.syncPlayerVariables(entity);
         }

         if (immediatesourceentity instanceof UnderworldKnightEntity && entity instanceof LivingEntity _livEnt2 && _livEnt2.isBlocking() && Math.random() < 0.6
            )
          {
            if (entity instanceof Player _player) {
               _player.getCooldowns().addCooldown((entity instanceof LivingEntity _entUseItem3 ? _entUseItem3.getUseItem() : ItemStack.EMPTY).getItem(), 80);
            }

            if (world instanceof Level _level) {
               if (!_level.isClientSide()) {
                  _level.playSound(null, BlockPos.containing(x, y, z), SoundEvents.SHIELD_BREAK, SoundSource.PLAYERS, 0.9F, 1.0F);
               } else {
                  _level.playLocalSound(x, y, z, SoundEvents.SHIELD_BREAK, SoundSource.PLAYERS, 0.9F, 1.0F, false);
               }
            }

            if (entity instanceof LivingEntity _entity) {
               _entity.stopUsingItem();
            }
         }
      }
   }
}
