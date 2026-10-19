package mcjty.incontrol;

import java.util.HashMap;
import java.util.Map;
import mcjty.incontrol.areas.AreaSystem;
import mcjty.incontrol.commands.ModCommands;
import mcjty.incontrol.data.DataStorage;
import mcjty.incontrol.data.Statistics;
import mcjty.incontrol.events.EventsSystem;
import mcjty.incontrol.rules.EffectRule;
import mcjty.incontrol.rules.ExperienceRule;
import mcjty.incontrol.rules.HarvestRule;
import mcjty.incontrol.rules.LeftClickRule;
import mcjty.incontrol.rules.PlaceRule;
import mcjty.incontrol.rules.RightClickRule;
import mcjty.incontrol.rules.RulesManager;
import mcjty.incontrol.rules.SpawnRule;
import mcjty.incontrol.rules.support.ICResult;
import mcjty.incontrol.rules.support.SpawnWhen;
import mcjty.incontrol.spawner.SpawnerSystem;
import mcjty.incontrol.tools.varia.Tools;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.entity.living.MobDespawnEvent;
import net.neoforged.neoforge.event.entity.living.MobSpawnEvent.PositionCheck;
import net.neoforged.neoforge.event.entity.living.MobSpawnEvent.PositionCheck.Result;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.neoforged.neoforge.event.level.BlockEvent.BreakEvent;
import net.neoforged.neoforge.event.level.BlockEvent.EntityPlaceEvent;
import net.neoforged.neoforge.event.level.LevelEvent.Load;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent.Pre;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;
import org.apache.logging.log4j.Level;

public class ForgeEventHandlers {
   public static boolean debug = false;
   public static boolean loaded = false;
   public static Map<Integer, Integer> tickCounters = new HashMap<>();

   @SubscribeEvent
   public void onLevelLoad(Load event) {
      tryLoadRules();
   }

   private static void tryLoadRules() {
      if (!loaded) {
         loaded = true;
         AreaSystem.reloadRules();
         RulesManager.reloadRules();
         SpawnerSystem.reloadRules();
         EventsSystem.reloadRules();
      }
   }

   @SubscribeEvent
   public void onServerStopping(ServerStoppedEvent event) {
      loaded = false;
   }

   @SubscribeEvent
   public void registerCommands(RegisterCommandsEvent event) {
      ModCommands.register(event.getDispatcher());
   }

   @SubscribeEvent(priority = EventPriority.LOWEST)
   public void onEntityJoinWorld(EntityJoinLevelEvent event) {
      int i = 0;
      if (event.getEntity() instanceof LivingEntity) {
         if (!(event.getEntity() instanceof Player)) {
            if (!event.getLevel().isClientSide) {
               for (SpawnRule rule : RulesManager.getFilteredRules(event.getLevel(), SpawnWhen.ONJOIN)) {
                  if (rule.match(event)) {
                     ICResult result = rule.getResult();
                     if (debug) {
                        InControl.setup
                           .getLogger()
                           .log(
                              Level.INFO,
                              "Join Rule " + i + ": " + result + " entity: " + event.getEntity().getName() + " y: " + event.getEntity().blockPosition().getY()
                           );
                     }

                     switch (result) {
                        case ALLOW:
                        case DEFAULT:
                           Statistics.addSpawnStat(i, false);
                           rule.action(event);
                           break;
                        case DENY:
                           Statistics.addSpawnStat(i, true);
                           event.setCanceled(true);
                           break;
                        case DENY_WITH_ACTIONS:
                           Statistics.addSpawnStat(i, true);
                           rule.action(event);
                           event.setCanceled(true);
                     }

                     if (!rule.isDoContinue()) {
                        return;
                     }
                  }

                  i++;
               }
            }
         }
      }
   }

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public void onEntityJoinWorldLast(EntityJoinLevelEvent event) {
      if (!event.getLevel().isClientSide() && event.getEntity() instanceof LivingEntity && !(event.getEntity() instanceof Player)) {
         InControl.setup.cache.addMob(event.getLevel(), event.getEntity());
      }
   }

   @SubscribeEvent
   public void onEntityLeavesWorld(EntityLeaveLevelEvent event) {
      if (!(event.getEntity() instanceof Player)) {
         if (!event.getLevel().isClientSide) {
            InControl.setup.cache.removeMob(event.getLevel(), event.getEntity());
         }
      }
   }

   @SubscribeEvent
   public void onWorldTick(Pre event) {
      if (!event.getLevel().isClientSide) {
         InControl.setup.cache.performCount(event.getLevel());
         if (!event.getLevel().players().isEmpty()) {
            SpawnerSystem.checkRules(event);
         }

         if (event.getLevel().dimension().equals(net.minecraft.world.level.Level.OVERWORLD)) {
            DataStorage.getData(event.getLevel()).tick(event.getLevel());
         }

         EventsSystem.onLevelTick(event);
      }
   }

   @SubscribeEvent(priority = EventPriority.LOWEST)
   public void onEntitySpawnEvent(FinalizeSpawnEvent event) {
      int i = 0;

      for (SpawnRule rule : RulesManager.getFilteredRules(event.getEntity().getCommandSenderWorld(), SpawnWhen.FINALIZE)) {
         if (rule.match(event)) {
            ICResult result = rule.getResult();
            if (debug) {
               Holder<Biome> biome = event.getLevel().getBiome(new BlockPos((int)event.getX(), (int)event.getY(), (int)event.getZ()));
               String biomeId = Tools.getBiomeId(biome);
               InControl.setup
                  .getLogger()
                  .log(
                     Level.INFO,
                     "Finalize Rule " + i + ": " + result + " entity: " + event.getEntity().getName() + " y: " + event.getY() + " biome: " + biomeId
                  );
            }

            switch (result) {
               case ALLOW:
                  rule.action(event);
                  event.setCanceled(true);
                  break;
               case DEFAULT:
                  rule.action(event);
                  break;
               case DENY:
                  event.setCanceled(true);
                  event.setSpawnCancelled(true);
                  break;
               case DENY_WITH_ACTIONS:
                  rule.action(event);
                  event.setCanceled(true);
                  event.setSpawnCancelled(true);
            }

            if (!rule.isDoContinue()) {
               return;
            }
         }

         i++;
      }
   }

   @SubscribeEvent(priority = EventPriority.LOWEST)
   public void onPositionCheck(PositionCheck event) {
      int i = 0;

      for (SpawnRule rule : RulesManager.getFilteredRules(event.getEntity().getCommandSenderWorld(), SpawnWhen.POSITION)) {
         if (rule.match(event)) {
            ICResult result = rule.getResult();
            if (debug) {
               Holder<Biome> biome = event.getLevel().getBiome(new BlockPos((int)event.getX(), (int)event.getY(), (int)event.getZ()));
               String biomeId = Tools.getBiomeId(biome);
               InControl.setup
                  .getLogger()
                  .log(
                     Level.INFO,
                     "Position Rule " + i + ": " + result + " entity: " + event.getEntity().getName() + " y: " + event.getY() + " biome: " + biomeId
                  );
            }

            switch (result) {
               case ALLOW:
                  event.setResult(Result.SUCCEED);
                  Statistics.addSpawnStat(i, false);
                  rule.action(event);
                  break;
               case DEFAULT:
                  event.setResult(Result.DEFAULT);
                  Statistics.addSpawnStat(i, false);
                  rule.action(event);
                  break;
               case DENY:
                  event.setResult(Result.FAIL);
                  Statistics.addSpawnStat(i, true);
                  break;
               case DENY_WITH_ACTIONS:
                  event.setResult(Result.FAIL);
                  Statistics.addSpawnStat(i, true);
                  rule.action(event);
            }

            if (!rule.isDoContinue()) {
               return;
            }
         }

         i++;
      }
   }

   @SubscribeEvent(priority = EventPriority.LOWEST)
   public void onCheckDespawn(MobDespawnEvent event) {
      int i = 0;

      for (SpawnRule rule : RulesManager.getFilteredRules(event.getEntity().getCommandSenderWorld(), SpawnWhen.DESPAWN)) {
         if (rule.match(event)) {
            ICResult result = rule.getResult();
            if (debug) {
               Holder<Biome> biome = event.getLevel().getBiome(new BlockPos((int)event.getX(), (int)event.getY(), (int)event.getZ()));
               String biomeId = Tools.getBiomeId(biome);
               InControl.setup
                  .getLogger()
                  .log(
                     Level.INFO, "Despawn Rule " + i + ": " + result + " entity: " + event.getEntity().getName() + " y: " + event.getY() + " biome: " + biomeId
                  );
            }

            switch (result) {
               case ALLOW:
                  event.setResult(net.neoforged.neoforge.event.entity.living.MobDespawnEvent.Result.ALLOW);
                  rule.action(event);
                  break;
               case DEFAULT:
                  event.setResult(net.neoforged.neoforge.event.entity.living.MobDespawnEvent.Result.DEFAULT);
                  rule.action(event);
                  break;
               case DENY:
                  event.setResult(net.neoforged.neoforge.event.entity.living.MobDespawnEvent.Result.DENY);
                  break;
               case DENY_WITH_ACTIONS:
                  event.setResult(net.neoforged.neoforge.event.entity.living.MobDespawnEvent.Result.DENY);
                  rule.action(event);
            }

            if (!rule.isDoContinue()) {
               return;
            }
         }

         i++;
      }
   }

   @SubscribeEvent(priority = EventPriority.LOWEST)
   public void onLivingExperienceDrop(LivingExperienceDropEvent event) {
      int i = 0;

      for (ExperienceRule rule : RulesManager.getFilteredExperienceRuiles(event.getEntity().level())) {
         if (rule.match(event)) {
            net.neoforged.neoforge.event.entity.living.MobSpawnEvent.SpawnPlacementCheck.Result result = rule.getResult();
            if (debug) {
               InControl.setup
                  .getLogger()
                  .log(
                     Level.INFO,
                     "Experience Rule " + i + ": " + result + " entity: " + event.getEntity().getName() + " y: " + event.getEntity().blockPosition().getY()
                  );
            }

            if (result != net.neoforged.neoforge.event.entity.living.MobSpawnEvent.SpawnPlacementCheck.Result.FAIL) {
               int newxp = rule.modifyXp(event.getDroppedExperience());
               event.setDroppedExperience(newxp);
            } else {
               event.setCanceled(true);
            }

            return;
         }

         i++;
      }
   }

   @SubscribeEvent
   public void onRightClickEvent(RightClickBlock event) {
      if (!event.getLevel().isClientSide) {
         int i = 0;

         for (RightClickRule rule : RulesManager.getFilteredRightClickRules(event.getLevel())) {
            if (rule.match(event)) {
               net.neoforged.neoforge.event.entity.living.MobSpawnEvent.SpawnPlacementCheck.Result result = rule.getResult();
               if (debug) {
                  Holder<Biome> biome = event.getLevel().getBiome(event.getPos());
                  String biomeId = Tools.getBiomeId(biome);
                  InControl.setup
                     .getLogger()
                     .log(
                        Level.INFO,
                        "Rule " + i + ": " + result + " entity: " + event.getEntity().getName() + " y: " + event.getPos().getY() + " biome: " + biomeId
                     );
               }

               rule.action(event);
               switch (result) {
                  case SUCCEED:
                     event.setUseBlock(TriState.TRUE);
                     break;
                  case DEFAULT:
                     event.setUseBlock(TriState.DEFAULT);
                     break;
                  case FAIL:
                     event.setUseBlock(TriState.FALSE);
                     event.setCanceled(true);
               }

               return;
            }

            i++;
         }
      }
   }

   @SubscribeEvent
   public void onLeftClickEvent(LeftClickBlock event) {
      if (!event.getLevel().isClientSide) {
         int i = 0;

         for (LeftClickRule rule : RulesManager.getFilteredLeftClickRules(event.getLevel())) {
            if (rule.match(event)) {
               net.neoforged.neoforge.event.entity.living.MobSpawnEvent.SpawnPlacementCheck.Result result = rule.getResult();
               if (debug) {
                  Holder<Biome> biome = event.getLevel().getBiome(event.getPos());
                  String biomeId = Tools.getBiomeId(biome);
                  InControl.setup
                     .getLogger()
                     .log(
                        Level.INFO,
                        "Rule " + i + ": " + result + " entity: " + event.getEntity().getName() + " y: " + event.getPos().getY() + " biome: " + biomeId
                     );
               }

               rule.action(event);
               switch (result) {
                  case SUCCEED:
                     event.setUseBlock(TriState.TRUE);
                     break;
                  case DEFAULT:
                     event.setUseBlock(TriState.DEFAULT);
                     break;
                  case FAIL:
                     event.setUseBlock(TriState.FALSE);
                     event.setCanceled(true);
               }

               return;
            }

            i++;
         }
      }
   }

   @SubscribeEvent
   public void onBlockPaceEvent(EntityPlaceEvent event) {
      if (!event.getLevel().isClientSide()) {
         int i = 0;

         for (PlaceRule rule : RulesManager.getFilteredPlaceRules(event.getLevel())) {
            if (rule.match(event)) {
               net.neoforged.neoforge.event.entity.living.MobSpawnEvent.SpawnPlacementCheck.Result result = rule.getResult();
               if (debug) {
                  Holder<Biome> biome = event.getLevel().getBiome(event.getPos());
                  String biomeId = Tools.getBiomeId(biome);
                  InControl.setup
                     .getLogger()
                     .log(
                        Level.INFO,
                        "Rule " + i + ": " + result + " entity: " + event.getEntity().getName() + " y: " + event.getPos().getY() + " biome: " + biomeId
                     );
               }

               rule.action(event);
               if (result == net.neoforged.neoforge.event.entity.living.MobSpawnEvent.SpawnPlacementCheck.Result.FAIL) {
                  event.setCanceled(true);
               }

               return;
            }

            i++;
         }
      }
   }

   @SubscribeEvent
   public void onBlockBreakEvent(BreakEvent event) {
      if (!event.getLevel().isClientSide()) {
         int i = 0;

         for (HarvestRule rule : RulesManager.getFilteredHarvestRules(event.getLevel())) {
            if (rule.match(event)) {
               net.neoforged.neoforge.event.entity.living.MobSpawnEvent.SpawnPlacementCheck.Result result = rule.getResult();
               if (debug) {
                  Holder<Biome> biome = event.getLevel().getBiome(event.getPos());
                  String biomeId = Tools.getBiomeId(biome);
                  InControl.setup
                     .getLogger()
                     .log(
                        Level.INFO,
                        "Rule " + i + ": " + result + " entity: " + event.getPlayer().getName() + " y: " + event.getPos().getY() + " biome: " + biomeId
                     );
               }

               rule.action(event);
               if (result == net.neoforged.neoforge.event.entity.living.MobSpawnEvent.SpawnPlacementCheck.Result.FAIL) {
                  event.setCanceled(true);
               }

               return;
            }

            i++;
         }

         EventsSystem.onBlockBreak(event);
      }
   }

   @SubscribeEvent
   public void onPlayerTick(Post event) {
      if (!event.getEntity().level().isClientSide) {
         int id = event.getEntity().getId();
         if (!tickCounters.containsKey(id)) {
            tickCounters.put(id, 0);
         }

         int tickCounter = tickCounters.get(id) + 1;
         tickCounters.put(id, tickCounter);
         int i = 0;

         for (EffectRule rule : RulesManager.getFilteredEffectRules(event.getEntity().getCommandSenderWorld())) {
            if (tickCounter % rule.getTimeout() == 0 && rule.match(event)) {
               if (debug) {
                  InControl.setup
                     .getLogger()
                     .log(Level.INFO, "Join Rule " + i + " entity: " + event.getEntity().getName() + " y: " + event.getEntity().blockPosition().getY());
               }

               rule.action(event);
               return;
            }

            i++;
         }
      }
   }

   @SubscribeEvent
   public void onEntityKilled(LivingDeathEvent event) {
      if (!event.getEntity().level().isClientSide) {
         EventsSystem.onEntityKilled(event);
      }
   }
}
