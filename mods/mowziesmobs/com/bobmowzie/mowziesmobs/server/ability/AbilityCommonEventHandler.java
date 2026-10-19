package com.bobmowzie.mowziesmobs.server.ability;

import com.bobmowzie.mowziesmobs.server.capability.AbilityData;
import com.bobmowzie.mowziesmobs.server.capability.DataHandler;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Post;
import net.neoforged.neoforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.LeftClickEmpty;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickEmpty;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickItem;

public class AbilityCommonEventHandler {
   @SubscribeEvent
   public void onPlayerInteract(RightClickEmpty event) {
      Player player = event.getEntity();
      AbilityData data = DataHandler.getData(player, DataHandler.ABILITY_DATA);

      for (Ability<?> ability : data.getAbilities()) {
         if (ability instanceof PlayerAbility) {
            ((PlayerAbility)ability).onRightClickEmpty(event);
         }
      }
   }

   @SubscribeEvent
   public void onPlayerRightClickBlock(RightClickBlock event) {
      Player player = event.getEntity();
      AbilityData data = DataHandler.getData(player, DataHandler.ABILITY_DATA);

      for (Ability<?> ability : data.getAbilities()) {
         if (ability instanceof PlayerAbility) {
            ((PlayerAbility)ability).onRightClickBlock(event);
         }
      }
   }

   @SubscribeEvent
   public void onPlayerRightClickItem(RightClickItem event) {
      Player player = event.getEntity();
      AbilityData data = DataHandler.getData(player, DataHandler.ABILITY_DATA);

      for (Ability<?> ability : data.getAbilities()) {
         if (ability instanceof PlayerAbility) {
            ((PlayerAbility)ability).onRightClickWithItem(event);
         }
      }
   }

   @SubscribeEvent
   public void onPlayerRightClickEntity(EntityInteract event) {
      Player player = event.getEntity();
      AbilityData data = DataHandler.getData(player, DataHandler.ABILITY_DATA);

      for (Ability<?> ability : data.getAbilities()) {
         if (ability instanceof PlayerAbility) {
            ((PlayerAbility)ability).onRightClickEntity(event);
         }
      }
   }

   @SubscribeEvent
   public void onPlayerLeftClickEmpty(LeftClickEmpty event) {
      Player player = event.getEntity();
      AbilityData data = DataHandler.getData(player, DataHandler.ABILITY_DATA);

      for (Ability<?> ability : data.getAbilities()) {
         if (ability instanceof PlayerAbility) {
            ((PlayerAbility)ability).onLeftClickEmpty(event);
         }
      }
   }

   @SubscribeEvent
   public void onPlayerLeftClickBlock(LeftClickBlock event) {
      Player player = event.getEntity();
      AbilityData data = DataHandler.getData(player, DataHandler.ABILITY_DATA);

      for (Ability<?> ability : data.getAbilities()) {
         if (ability instanceof PlayerAbility) {
            ((PlayerAbility)ability).onLeftClickBlock(event);
         }
      }
   }

   @SubscribeEvent
   public void onLeftClickEntity(AttackEntityEvent event) {
      Player player = event.getEntity();
      AbilityData data = DataHandler.getData(player, DataHandler.ABILITY_DATA);

      for (Ability<?> ability : data.getAbilities()) {
         if (ability instanceof PlayerAbility) {
            ((PlayerAbility)ability).onLeftClickEntity(event);
         }
      }
   }

   @SubscribeEvent
   public void onTakeDamage(Post event) {
      LivingEntity player = event.getEntity();
      AbilityData data = DataHandler.getData(player, DataHandler.ABILITY_DATA);

      for (Ability<?> ability : data.getAbilities()) {
         ability.onTakeDamage(event);
      }
   }

   @SubscribeEvent
   public void onJump(LivingJumpEvent event) {
      LivingEntity player = event.getEntity();
      AbilityData data = DataHandler.getData(player, DataHandler.ABILITY_DATA);

      for (Ability<?> ability : data.getAbilities()) {
         if (ability instanceof PlayerAbility) {
            ((PlayerAbility)ability).onJump(event);
         }
      }
   }

   @SubscribeEvent
   public void onFall(LivingFallEvent event) {
      LivingEntity player = event.getEntity();
      AbilityData data = DataHandler.getData(player, DataHandler.ABILITY_DATA);

      for (Ability<?> ability : data.getAbilities()) {
         if (ability instanceof PlayerAbility) {
            ((PlayerAbility)ability).onFall(event);
         }
      }
   }
}
