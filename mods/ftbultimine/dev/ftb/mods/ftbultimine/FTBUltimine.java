package dev.ftb.mods.ftbultimine;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.BlockEvent;
import dev.architectury.event.events.common.CommandRegistrationEvent;
import dev.architectury.event.events.common.EntityEvent;
import dev.architectury.event.events.common.InteractionEvent;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.event.events.common.TickEvent;
import dev.architectury.hooks.level.entity.PlayerHooks;
import dev.architectury.networking.NetworkManager;
import dev.architectury.registry.ReloadListenerRegistry;
import dev.architectury.utils.value.IntValue;
import dev.ftb.mods.ftblibrary.config.manager.ConfigManager;
import dev.ftb.mods.ftbultimine.api.FTBUltimineAPI;
import dev.ftb.mods.ftbultimine.api.blockbreaking.BlockBreakHandler;
import dev.ftb.mods.ftbultimine.api.blockbreaking.RegisterBlockBreakHandlerEvent;
import dev.ftb.mods.ftbultimine.api.blockselection.RegisterBlockSelectionHandlerEvent;
import dev.ftb.mods.ftbultimine.api.crop.RegisterCropLikeEvent;
import dev.ftb.mods.ftbultimine.api.restriction.RegisterRestrictionHandlerEvent;
import dev.ftb.mods.ftbultimine.api.rightclick.RegisterRightClickHandlerEvent;
import dev.ftb.mods.ftbultimine.api.shape.RegisterShapeEvent;
import dev.ftb.mods.ftbultimine.api.shape.Shape;
import dev.ftb.mods.ftbultimine.api.shape.ShapeContext;
import dev.ftb.mods.ftbultimine.api.util.CanUltimineResult;
import dev.ftb.mods.ftbultimine.api.util.ItemCollector;
import dev.ftb.mods.ftbultimine.config.FTBUltimineClientConfig;
import dev.ftb.mods.ftbultimine.config.FTBUltimineServerConfig;
import dev.ftb.mods.ftbultimine.crops.CropLikeRegistry;
import dev.ftb.mods.ftbultimine.crops.VanillaCropLikeHandler;
import dev.ftb.mods.ftbultimine.integration.IntegrationHandler;
import dev.ftb.mods.ftbultimine.integration.acceldecay.AcceleratedDecay;
import dev.ftb.mods.ftbultimine.integration.acceldecay.LogBreakTracker;
import dev.ftb.mods.ftbultimine.net.FTBUltimineNet;
import dev.ftb.mods.ftbultimine.net.SendShapePacket;
import dev.ftb.mods.ftbultimine.net.SyncUltimineTimePacket;
import dev.ftb.mods.ftbultimine.registry.ModAttributes;
import dev.ftb.mods.ftbultimine.rightclick.AxeStripping;
import dev.ftb.mods.ftbultimine.rightclick.CropHarvesting;
import dev.ftb.mods.ftbultimine.rightclick.FarmlandConversion;
import dev.ftb.mods.ftbultimine.rightclick.RightClickDispatcher;
import dev.ftb.mods.ftbultimine.rightclick.ShovelFlattening;
import dev.ftb.mods.ftbultimine.shape.BlockMatcher;
import dev.ftb.mods.ftbultimine.shape.EscapeTunnelShape;
import dev.ftb.mods.ftbultimine.shape.LargeTunnelShape;
import dev.ftb.mods.ftbultimine.shape.MiningTunnelShape;
import dev.ftb.mods.ftbultimine.shape.ShapeRegistry;
import dev.ftb.mods.ftbultimine.shape.ShapelessShape;
import dev.ftb.mods.ftbultimine.shape.SmallSquareShape;
import dev.ftb.mods.ftbultimine.shape.SmallTunnelShape;
import dev.ftb.mods.ftbultimine.utils.PlatformUtil;
import dev.ftb.mods.ftbultimine.utils.XPUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FTBUltimine {
   private static FTBUltimine instance;
   public static final Logger LOGGER = LogManager.getLogger();
   private Map<UUID, FTBUltiminePlayerData> cachedDataMap;
   private boolean isBreakingBlock;
   private int tempBlockDroppedXp;
   private ItemCollector tempBlockDropsList;
   public static final TagKey<Item> DENY_TAG = TagKey.create(Registries.ITEM, FTBUltimineAPI.id("excluded_tools"));
   public static final TagKey<Item> STRICT_DENY_TAG = TagKey.create(Registries.ITEM, FTBUltimineAPI.id("excluded_tools/strict"));
   public static final TagKey<Item> ALLOW_TAG = TagKey.create(Registries.ITEM, FTBUltimineAPI.id("included_tools"));
   public static final TagKey<Block> EXCLUDED_BLOCKS = TagKey.create(Registries.BLOCK, FTBUltimineAPI.id("excluded_blocks"));
   public static final TagKey<Block> BLOCK_WHITELIST = TagKey.create(Registries.BLOCK, FTBUltimineAPI.id("block_whitelist"));
   public static final TagKey<Block> TILLABLE_TAG = TagKey.create(Registries.BLOCK, FTBUltimineAPI.id("farmland_tillable"));
   public static final TagKey<Block> FLATTENABLE_TAG = TagKey.create(Registries.BLOCK, FTBUltimineAPI.id("shovel_flattenable"));
   public static final TagKey<Block> SINGLE_CROP_HARVESTING_BLACKLIST = TagKey.create(Registries.BLOCK, FTBUltimineAPI.id("single_crop_harvesting_blacklist"));
   private static Predicate<Player> permissionOverride = player -> true;

   public static void setPermissionOverride(Predicate<Player> p) {
      permissionOverride = p;
   }

   public FTBUltimine() {
      instance = this;
      FTBUltimineAPI._init(FTBUltimineAPIImpl.INSTANCE);
      ConfigManager.getInstance().registerClientConfig(FTBUltimineClientConfig.CONFIG, "ftbultimine.client_settings");
      ConfigManager.getInstance()
         .registerServerConfig(FTBUltimineServerConfig.CONFIG, "ftbultimine.server_settings", true, FTBUltimineServerConfig::onConfigChanged);
      FTBUltimineNet.init();
      ModAttributes.init();
      IntegrationHandler.init();
      ReloadListenerRegistry.register(PackType.SERVER_DATA, new FTBUltimine.DataReloadListener());
      LifecycleEvent.SETUP.register(this::commonSetup);
      PlayerEvent.PLAYER_JOIN.register(this::playerJoined);
      LifecycleEvent.SERVER_BEFORE_START.register(this::serverStarting);
      LifecycleEvent.SERVER_STOPPING.register(this::serverStopping);
      BlockEvent.BREAK.register(this::blockBroken);
      InteractionEvent.RIGHT_CLICK_BLOCK.register(this::blockRightClick);
      TickEvent.PLAYER_PRE.register(this::playerTick);
      EntityEvent.ADD.register(this::entityJoinedWorld);
      CommandRegistrationEvent.EVENT.register(FTBUltimineCommands::registerCommands);
      RegisterRightClickHandlerEvent.REGISTER.register(this::registerBuiltinHandlers);
      RegisterShapeEvent.REGISTER.register(this::registerBuiltinShapes);
      RegisterCropLikeEvent.REGISTER.register(this::registerCropHandlers);
   }

   public static FTBUltimine getInstance() {
      return instance;
   }

   private void commonSetup() {
      ((RegisterRestrictionHandlerEvent)RegisterRestrictionHandlerEvent.REGISTER.invoker()).register(RestrictionHandlerRegistry.INSTANCE);
      ((RegisterShapeEvent)RegisterShapeEvent.REGISTER.invoker()).register(ShapeRegistry.INSTANCE);
   }

   private void registerBuiltinShapes(RegisterShapeEvent.Registry shapeRegistry) {
      shapeRegistry.register(new ShapelessShape());
      shapeRegistry.register(new SmallTunnelShape());
      shapeRegistry.register(new SmallSquareShape());
      shapeRegistry.register(new LargeTunnelShape());
      shapeRegistry.register(new MiningTunnelShape());
      shapeRegistry.register(new EscapeTunnelShape());
   }

   private void registerCropHandlers(RegisterCropLikeEvent.Dispatcher dispatcher) {
      dispatcher.registerHandler(VanillaCropLikeHandler.INSTANCE);
   }

   private void registerBuiltinHandlers(RegisterRightClickHandlerEvent.Dispatcher dispatcher) {
      dispatcher.registerHandler(AxeStripping.INSTANCE);
      dispatcher.registerHandler(ShovelFlattening.INSTANCE);
      dispatcher.registerHandler(FarmlandConversion.INSTANCE);
      dispatcher.registerHandler(CropHarvesting.INSTANCE);
   }

   @NotNull
   public FTBUltiminePlayerData getOrCreatePlayerData(Player player) {
      return this.cachedDataMap.computeIfAbsent(player.getUUID(), FTBUltiminePlayerData::new);
   }

   private void playerJoined(ServerPlayer serverPlayer) {
      NetworkManager.sendToPlayer(
         serverPlayer, new SyncUltimineTimePacket(FTBUltimineServerConfig.getUltimineCooldown(serverPlayer), SyncUltimineTimePacket.TimeType.COOLDOWN)
      );
   }

   private void serverStarting(MinecraftServer server) {
      this.cachedDataMap = new HashMap<>();
      ((RegisterRightClickHandlerEvent)RegisterRightClickHandlerEvent.REGISTER.invoker()).register(RightClickDispatcher.getInstance());
      ((RegisterCropLikeEvent)RegisterCropLikeEvent.REGISTER.invoker()).register(CropLikeRegistry.getInstance());
      ((RegisterBlockBreakHandlerEvent)RegisterBlockBreakHandlerEvent.REGISTER.invoker()).register(BlockBreakingRegistry.getInstance());
      ((RegisterBlockSelectionHandlerEvent)RegisterBlockSelectionHandlerEvent.REGISTER.invoker()).register(BlockSelectionRegistry.getInstance());
   }

   private void serverStopping(MinecraftServer server) {
      RightClickDispatcher.getInstance().clear();
      CropLikeRegistry.getInstance().clear();
      BlockBreakingRegistry.getInstance().clear();
      BlockSelectionRegistry.getInstance().clear();
   }

   public void setKeyPressed(ServerPlayer player, boolean pressed) {
      FTBUltiminePlayerData data = this.getOrCreatePlayerData(player);
      data.setPressed(pressed);
      data.clearCache();
      if (!data.isPressed()) {
         NetworkManager.sendToPlayer(player, SendShapePacket.adjustShapeOnly(data.getCurrentShapeIndex()));
      }
   }

   public void modeChanged(ServerPlayer player, boolean next) {
      FTBUltiminePlayerData data = this.getOrCreatePlayerData(player);
      data.cycleShape(next);
      data.clearCache();
      NetworkManager.sendToPlayer(player, SendShapePacket.adjustShapeOnly(data.getCurrentShapeIndex()));
   }

   public static boolean isValidTool(Player player, BlockPos pos, BlockState state) {
      ItemStack mainHand = player.getMainHandItem();
      boolean hasAnyTool = !(Boolean)FTBUltimineServerConfig.REQUIRE_TOOL.get()
         || !mainHand.isEmpty() && (mainHand.getItem() instanceof TieredItem || mainHand.isDamageableItem() || mainHand.is(ALLOW_TAG));
      return !hasAnyTool
         ? false
         : !(Boolean)FTBUltimineServerConfig.REQUIRE_VALID_TOOL_FOR_BLOCK.get()
            || !state.requiresCorrectToolForDrops()
            || PlatformUtil.playerHasCorrectTool(player, pos, state);
   }

   public CanUltimineResult canUltimine(Player player, BlockPos pos, BlockState state) {
      if (PlayerHooks.isFake(player) || player.getUUID() == null) {
         return CanUltimineResult.OTHER_RESTRICTION;
      } else if (CooldownTracker.isOnCooldown(player)) {
         return CanUltimineResult.ON_COOLDOWN;
      } else if (player.getFoodData().getFoodLevel() <= 0 && !player.isCreative()) {
         return CanUltimineResult.NO_FOOD;
      } else if (!permissionOverride.test(player)) {
         return CanUltimineResult.NO_PERMISSION;
      } else {
         ItemStack mainHand = player.getMainHandItem();
         ItemStack offHand = player.getOffhandItem();
         if (!mainHand.is(STRICT_DENY_TAG) && !offHand.is(STRICT_DENY_TAG) && !mainHand.is(DENY_TAG)) {
            return isValidTool(player, pos, state) ? RestrictionHandlerRegistry.INSTANCE.canUltimine(player) : CanUltimineResult.NO_TOOL;
         } else {
            return CanUltimineResult.BLOCKED_TOOL;
         }
      }
   }

   public EventResult blockBroken(Level world, BlockPos origPos, BlockState state, ServerPlayer player, @Nullable IntValue xp) {
      if (!this.isBreakingBlock && this.canUltimine(player, origPos, state).isAllowed()) {
         FTBUltiminePlayerData data = this.getOrCreatePlayerData(player);
         if (!data.isPressed()) {
            return EventResult.pass();
         }

         HitResult hitResult = FTBUltiminePlayerData.rayTrace(player);
         if (hitResult instanceof BlockHitResult bhr && hitResult.getType() == Type.BLOCK) {
            data.clearCache();
            data.updateBlocks(player, origPos, bhr.getDirection(), false, FTBUltimineServerConfig.getMaxBlocks(player));
            if (!data.hasCachedPositions()) {
               return EventResult.pass();
            }

            double xpPerBlock = FTBUltimineServerConfig.getExperiencePerBlock(player);
            if (xpPerBlock > 0.0 && XPUtils.getPlayerXP(player) < Objects.requireNonNull(data.cachedPositions()).size() * xpPerBlock) {
               return EventResult.pass();
            }

            this.isBreakingBlock = true;
            this.tempBlockDropsList = new ItemCollector();
            this.tempBlockDroppedXp = 0;
            boolean hadItem = !player.getMainHandItem().isEmpty();
            Shape shape = data.getCurrentShape();
            float baseSpeed = state.getDestroySpeed(world, origPos);
            int blocksMined = 0;

            for (BlockPos pos : data.cachedPositions()) {
               BlockState state1 = world.getBlockState(pos);
               if (AcceleratedDecay.available && state1.is(BlockTags.LEAVES) && LogBreakTracker.INSTANCE.playerRecentlyBrokeLog(player, 1500L)) {
                  world.destroyBlock(pos, true, player);
               } else {
                  float destroySpeed = state1.getDestroySpeed(world, pos);
                  if (player.isCreative() || !(destroySpeed < 0.0F) && !(destroySpeed > baseSpeed) && isValidTool(player, pos, state1)) {
                     if (!tryBreakBlock(player, pos, state, shape, bhr) && (Boolean)FTBUltimineServerConfig.CANCEL_ON_BLOCK_BREAK_FAIL.get()) {
                        break;
                     }

                     if (!player.isCreative()) {
                        player.causeFoodExhaustion((float)(FTBUltimineServerConfig.getExhaustionPerBlock(player) * 0.005));
                        if (FTBUltimineAPI.isTooExhausted(player)) {
                           break;
                        }
                     }

                     ItemStack stack = player.getMainHandItem();
                     if (hadItem && stack.isEmpty()
                        || hadItem
                           && (Integer)FTBUltimineServerConfig.PREVENT_TOOL_BREAK.get() > 0
                           && stack.isDamageableItem()
                           && stack.getDamageValue() >= stack.getMaxDamage() - (Integer)FTBUltimineServerConfig.PREVENT_TOOL_BREAK.get()) {
                        break;
                     }

                     blocksMined++;
                  }
               }
            }

            BlockBreakingRegistry.INSTANCE.getHandlers().forEach(h -> h.postBreak(player));
            if (!player.isCreative()) {
               CooldownTracker.setLastUltimineTime(player, System.currentTimeMillis());
               data.addPendingXPCost(player, Math.max(0, blocksMined - 1));
            }

            this.isBreakingBlock = false;
            this.tempBlockDropsList.drop(player.level(), origPos);
            if (this.tempBlockDroppedXp > 0) {
               player.level()
                  .addFreshEntity(new ExperienceOrb(player.level(), origPos.getX() + 0.5, origPos.getY() + 0.5, origPos.getZ() + 0.5, this.tempBlockDroppedXp));
            }

            data.clearCache();
            NetworkManager.sendToPlayer(player, SendShapePacket.adjustShapeAndBlockPos(data.getCurrentShapeIndex(), List.of()));
            return EventResult.interruptFalse();
         } else {
            return EventResult.pass();
         }
      } else {
         return EventResult.pass();
      }
   }

   private static boolean tryBreakBlock(ServerPlayer player, BlockPos pos, BlockState state, Shape shape, BlockHitResult bhr) {
      for (BlockBreakHandler handler : BlockBreakingRegistry.INSTANCE.getHandlers()) {
         switch (handler.breakBlock(player, pos, state, shape, bhr)) {
            case SUCCESS:
               return true;
            case FAIL:
               return false;
         }
      }

      return player.gameMode.destroyBlock(pos);
   }

   public EventResult blockRightClick(Player player, InteractionHand hand, BlockPos clickPos, Direction face) {
      if (PlayerHooks.isFake(player) && CropLikeRegistry.checkForSingleCropHarvesting(player, clickPos)) {
         return EventResult.interruptTrue();
      }

      if (player instanceof ServerPlayer serverPlayer && !PlayerHooks.isFake(player) && player.getUUID() != null) {
         FTBUltiminePlayerData data = this.getOrCreatePlayerData(player);
         if (!data.isPressed()) {
            return CropLikeRegistry.checkForSingleCropHarvesting(player, clickPos) ? EventResult.interruptTrue() : EventResult.pass();
         }

         if (player.getFoodData().getFoodLevel() <= 0 && !player.isCreative()) {
            return EventResult.pass();
         }

         HitResult result = FTBUltiminePlayerData.rayTrace(serverPlayer);
         if (result instanceof BlockHitResult blockHitResult && result.getType() == Type.BLOCK) {
            data.clearCache();
            ShapeContext shapeContext = data.updateBlocks(
               serverPlayer, clickPos, blockHitResult.getDirection(), false, FTBUltimineServerConfig.getMaxBlocks(serverPlayer)
            );
            if (shapeContext != null && data.hasCachedPositions()) {
               int didWork = RightClickDispatcher.INSTANCE.dispatchRightClick(shapeContext, hand, data);
               if (didWork > 0) {
                  serverPlayer.swing(hand);
                  if (!player.isCreative()) {
                     CooldownTracker.setLastUltimineTime(player, System.currentTimeMillis());
                     data.addPendingXPCost(serverPlayer, Math.max(0, didWork - 1));
                  }

                  return EventResult.interruptTrue();
               } else {
                  return EventResult.pass();
               }
            } else {
               return EventResult.pass();
            }
         } else {
            return EventResult.pass();
         }
      } else {
         return EventResult.pass();
      }
   }

   public void playerTick(Player player) {
      if (player instanceof ServerPlayer serverPlayer) {
         FTBUltiminePlayerData data = this.getOrCreatePlayerData(player);
         data.checkBlocks(serverPlayer, true, () -> FTBUltimineServerConfig.getMaxBlocks(serverPlayer));
         data.takePendingXP(serverPlayer);
      }
   }

   public EventResult entityJoinedWorld(Entity entity, Level level) {
      if (entity.isAlive()) {
         if (this.isBreakingBlock && entity instanceof ItemEntity item) {
            if (!item.getItem().isEmpty()) {
               this.tempBlockDropsList.add(item.getItem());
               item.setItem(ItemStack.EMPTY);
            }

            return EventResult.interruptFalse();
         }

         if (this.isBreakingBlock && entity instanceof ExperienceOrb orb) {
            this.tempBlockDroppedXp = this.tempBlockDroppedXp + orb.getValue();
            entity.kill();
            return EventResult.interruptFalse();
         }
      }

      return EventResult.pass();
   }

   private static class DataReloadListener implements ResourceManagerReloadListener {
      public void onResourceManagerReload(ResourceManager resourceManager) {
         BlockMatcher.TagCache.onReload();
      }
   }
}
