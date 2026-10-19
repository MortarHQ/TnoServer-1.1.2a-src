package com.b1n_ry.yigd.components;

import com.b1n_ry.yigd.Yigd;
import com.b1n_ry.yigd.block.entity.GraveBlockEntity;
import com.b1n_ry.yigd.config.ClaimPriority;
import com.b1n_ry.yigd.config.DropType;
import com.b1n_ry.yigd.config.ExtraFeaturesConfig;
import com.b1n_ry.yigd.config.GraveConfig;
import com.b1n_ry.yigd.config.MapEntryConfig;
import com.b1n_ry.yigd.config.YigdConfig;
import com.b1n_ry.yigd.data.DeathContext;
import com.b1n_ry.yigd.data.DeathInfoManager;
import com.b1n_ry.yigd.data.DirectionalPos;
import com.b1n_ry.yigd.data.GraveItem;
import com.b1n_ry.yigd.data.GraveStatus;
import com.b1n_ry.yigd.data.GraveyardData;
import com.b1n_ry.yigd.data.TimePoint;
import com.b1n_ry.yigd.events.YigdEvents;
import com.b1n_ry.yigd.networking.LightGraveData;
import com.b1n_ry.yigd.util.DropRule;
import com.b1n_ry.yigd.util.GraveCompassHelper;
import com.b1n_ry.yigd.util.GraveOverrideAreas;
import com.b1n_ry.yigd.util.YigdTags;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Component.Serializer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForge;
import org.jetbrains.annotations.Nullable;

public class GraveComponent {
   private final ResolvableProfile owner;
   private InventoryComponent inventoryComponent;
   private ExpComponent expComponent;
   @Nullable
   private ServerLevel world;
   private ResourceKey<Level> worldRegistryKey;
   private BlockPos pos;
   private final Component deathMessage;
   private final UUID graveId;
   private GraveStatus status;
   private boolean locked;
   private final TimePoint creationTime;
   private final UUID killerId;
   public static GraveyardData graveyardData = null;

   public GraveComponent(
      ResolvableProfile owner,
      InventoryComponent inventoryComponent,
      ExpComponent expComponent,
      ServerLevel world,
      Vec3 pos,
      Component deathMessage,
      UUID killerId
   ) {
      this(
         owner,
         inventoryComponent,
         expComponent,
         world,
         BlockPos.containing(pos),
         deathMessage,
         UUID.randomUUID(),
         GraveStatus.UNCLAIMED,
         true,
         new TimePoint(world),
         killerId
      );
   }

   public GraveComponent(
      ResolvableProfile owner,
      InventoryComponent inventoryComponent,
      ExpComponent expComponent,
      ServerLevel world,
      BlockPos pos,
      Component deathMessage,
      UUID graveId,
      GraveStatus status,
      boolean locked,
      TimePoint creationTime,
      UUID killerId
   ) {
      this.owner = owner;
      this.inventoryComponent = inventoryComponent;
      this.expComponent = expComponent;
      this.world = world;
      this.worldRegistryKey = world.dimension();
      this.pos = pos;
      this.deathMessage = deathMessage;
      this.graveId = graveId;
      this.status = status;
      this.locked = locked;
      this.creationTime = creationTime;
      this.killerId = killerId;
   }

   public GraveComponent(
      ResolvableProfile owner,
      InventoryComponent inventoryComponent,
      ExpComponent expComponent,
      ResourceKey<Level> worldKey,
      BlockPos pos,
      Component deathMessage,
      UUID graveId,
      GraveStatus status,
      boolean locked,
      TimePoint creationTime,
      UUID killerId
   ) {
      this.owner = owner;
      this.inventoryComponent = inventoryComponent;
      this.expComponent = expComponent;
      this.world = null;
      this.worldRegistryKey = worldKey;
      this.pos = pos;
      this.deathMessage = deathMessage;
      this.graveId = graveId;
      this.status = status;
      this.locked = locked;
      this.creationTime = creationTime;
      this.killerId = killerId;
   }

   public ResolvableProfile getOwner() {
      return this.owner;
   }

   public InventoryComponent getInventoryComponent() {
      return this.inventoryComponent;
   }

   public void setInventoryComponent(InventoryComponent inventoryComponent) {
      this.inventoryComponent = inventoryComponent;
      DeathInfoManager.INSTANCE.setDirty();
   }

   public ExpComponent getExpComponent() {
      return this.expComponent;
   }

   public void setExpComponent(ExpComponent expComponent) {
      this.expComponent = expComponent;
      DeathInfoManager.INSTANCE.setDirty();
   }

   @Nullable
   public ServerLevel getWorld() {
      return this.world;
   }

   public ResourceKey<Level> getWorldRegistryKey() {
      return this.worldRegistryKey;
   }

   public BlockPos getPos() {
      return this.pos;
   }

   public Component getDeathMessage() {
      return this.deathMessage;
   }

   public UUID getGraveId() {
      return this.graveId;
   }

   public GraveStatus getStatus() {
      return this.status;
   }

   public boolean isLocked() {
      return this.locked;
   }

   public TimePoint getCreationTime() {
      return this.creationTime;
   }

   public UUID getKillerId() {
      return this.killerId;
   }

   public void setLocked(boolean locked) {
      this.locked = locked;
      DeathInfoManager.INSTANCE.setDirty();
   }

   public void setPos(BlockPos pos) {
      this.pos = pos;
      DeathInfoManager.INSTANCE.setDirty();
   }

   public void setWorld(ServerLevel world) {
      this.world = world;
      this.worldRegistryKey = world.dimension();
   }

   public void setStatus(GraveStatus status) {
      if (this.status == GraveStatus.UNCLAIMED
         && YigdConfig.getConfig().extraFeatures.graveCompass.pointToClosest != ExtraFeaturesConfig.GraveCompassConfig.CompassGraveTarget.DISABLED) {
         GraveCompassHelper.setClaimed(this.worldRegistryKey, this.pos);
      }

      this.status = status;
      DeathInfoManager.INSTANCE.setDirty();
   }

   public boolean isGraveEmpty() {
      return this.inventoryComponent.isGraveEmpty() && this.expComponent.isEmpty();
   }

   public boolean isEmpty() {
      return this.inventoryComponent.isEmpty() && this.expComponent.isEmpty();
   }

   public DirectionalPos findGravePos(Direction defaultDirection) {
      if (this.world == null) {
         Yigd.LOGGER.error("GraveComponent's associated world is null. Failed to find suitable position");
         return new DirectionalPos(this.pos, defaultDirection);
      }

      YigdConfig config = YigdConfig.getConfig();
      int y = this.pos.getY();
      Map<String, Integer> minimumYMap = new HashMap<>();

      for (MapEntryConfig.IntType entry : config.graveConfig.minimumGraveYLevel) {
         minimumYMap.put(entry.key, entry.value);
      }

      String dimName = this.worldRegistryKey.location().toString();
      if (!minimumYMap.containsKey(dimName)) {
         dimName = "misc";
      }

      int lowerAcceptableY = this.world.getMinBuildHeight();
      if (minimumYMap.containsKey(dimName)) {
         lowerAcceptableY = minimumYMap.get(dimName);
      } else {
         Yigd.LOGGER.error("Couldn't find minimum Y level for dimension {}, using world min build height instead", dimName);
      }

      if (config.graveConfig.generateGraveInVoid && this.pos.getY() <= lowerAcceptableY) {
         y = lowerAcceptableY;
      }

      int topY = this.world.getMaxBuildHeight() - 1;
      if (y > topY) {
         y = topY;
      }

      int x = this.pos.getX();
      int z = this.pos.getZ();
      if (config.graveConfig.generateOnlyWithinBorder) {
         WorldBorder border = this.world.getWorldBorder();
         if (!border.isWithinBounds(x, z)) {
            x = (int)Math.max(x, border.getMinX());
            x = (int)Math.min(x, border.getMaxX());
            z = (int)Math.max(z, border.getMinZ());
            z = (int)Math.min(z, border.getMaxZ());
         }
      }

      this.pos = new BlockPos(x, y, z);
      if (this.world.dimension().equals(Level.END)
         && Math.abs(this.pos.getX()) + Math.abs(this.pos.getZ()) < 25
         && this.world.getBlockState(this.pos.below()).is(Blocks.BEDROCK)) {
         this.pos = this.pos.above();
      }

      DeathInfoManager.INSTANCE.setDirty();
      DirectionalPos graveyardPos = this.findPosInGraveyard(defaultDirection);
      if (graveyardPos != null) {
         return graveyardPos;
      }

      if (config.graveConfig.tryGenerateOnGround) {
         for (BlockPos pos = this.pos.below(); pos.getY() >= this.world.getMinBuildHeight(); pos = pos.below()) {
            if (!this.world.getBlockState(pos).is(YigdTags.REPLACE_SOFT_WHITELIST)) {
               this.pos = pos.above();
               break;
            }
         }
      }

      GraveConfig.Range generationMaxDistance = config.graveConfig.generationMaxDistance;

      for (int i = 0; i < 50; i++) {
         for (BlockPos iPos : BlockPos.withinManhattan(this.pos, generationMaxDistance.x, generationMaxDistance.y, generationMaxDistance.z)) {
            YigdEvents.GraveGenerationEvent event = (YigdEvents.GraveGenerationEvent)NeoForge.EVENT_BUS
               .post(new YigdEvents.GraveGenerationEvent(this.world, iPos, i));
            if (event.canGenerate()) {
               this.pos = iPos;
               DeathInfoManager.INSTANCE.setDirty();
               return new DirectionalPos(iPos, defaultDirection);
            }
         }
      }

      return new DirectionalPos(this.pos, defaultDirection);
   }

   private DirectionalPos findPosInGraveyard(Direction defaultDirection) {
      if (this.world == null) {
         return null;
      }

      if (graveyardData != null && !graveyardData.graveLocations.isEmpty()) {
         MinecraftServer server = this.world.getServer();
         ServerLevel graveyardWorld = server.getLevel(ResourceKey.create(Registries.DIMENSION, graveyardData.dimensionId));
         if (graveyardWorld == null) {
            graveyardWorld = server.overworld();
         }

         DirectionalPos closest = null;

         for (GraveyardData.GraveLocation location : graveyardData.graveLocations) {
            BlockPos graveyardPos = new BlockPos(location.x, location.y, location.z);
            if (graveyardWorld.getBlockState(graveyardPos).is(YigdTags.REPLACE_SOFT_WHITELIST) && graveyardWorld.getBlockEntity(graveyardPos) == null) {
               Optional<String> maybeName = this.owner.name();
               if (location.forPlayer == null || location.forPlayer.equalsIgnoreCase(maybeName.orElse(""))) {
                  Direction direction = location.direction != null ? location.direction : defaultDirection;
                  DirectionalPos maybePos = new DirectionalPos(location.x, location.y, location.z, direction);
                  if (!graveyardData.useClosest) {
                     closest = maybePos;
                     break;
                  }

                  if (closest == null || maybePos.getSquaredDistance(this.pos) < closest.getSquaredDistance(this.pos)) {
                     closest = maybePos;
                  }
               }
            }
         }

         if (closest != null) {
            this.setWorld(graveyardWorld);
            this.setPos(closest.pos());
            return closest;
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   public boolean tryPlaceGrave(BlockState state) {
      if (this.world == null) {
         Yigd.LOGGER.error("GraveComponent tried to place grave without knowing the ServerLevel");
         return false;
      } else {
         this.placeBlockUnder();
         return this.world.setBlockAndUpdate(this.pos, state);
      }
   }

   public void placeAndLoad(Direction direction, DeathContext context, BlockPos pos, ServerLevel level, RespawnComponent respawnComponent) {
      YigdConfig config = YigdConfig.getConfig();
      ServerLevel deathWorld = context.world();
      Vec3 deathPos = context.deathPos();
      if (!config.graveConfig.storeItems) {
         this.inventoryComponent.dropGraveItems(deathWorld, deathPos);
      }

      if (!config.graveConfig.storeXp) {
         this.expComponent.dropAll(deathWorld, deathPos);
         this.getExpComponent().clear();
      }

      boolean waterlogged = level.getFluidState(pos).is(Fluids.WATER);
      BlockState graveBlock = (BlockState)((BlockState)((Block)Yigd.GRAVE.get())
            .defaultBlockState()
            .setValue(BlockStateProperties.HORIZONTAL_FACING, direction))
         .setValue(BlockStateProperties.WATERLOGGED, waterlogged);
      if (config.graveConfig.generateEmptyGraves || !this.isGraveEmpty()) {
         Yigd.END_OF_TICK
            .add(
               () -> {
                  BlockState previousState = level.getBlockState(pos);
                  boolean placed = this.tryPlaceGrave(graveBlock);
                  BlockPos placedPos = this.getPos();
                  if (!placed) {
                     Yigd.LOGGER
                        .error(
                           "Failed to generate grave at X: {}, Y: {}, Z: {}, {}. Grave block placement failed",
                           new Object[]{placedPos.getX(), placedPos.getY(), placedPos.getZ(), level.dimension().location()}
                        );
                     Yigd.LOGGER.info("Dropping items on ground instead of in grave");
                     context.player().sendSystemMessage(Component.translatable("text.yigd.message.grave_generation_error"));
                     this.getInventoryComponent().dropGraveItems(level, Vec3.atLowerCornerOf(placedPos));
                     this.getExpComponent().dropAll(level, Vec3.atLowerCornerOf(placedPos));
                  } else {
                     respawnComponent.setGraveGenerated(true);
                     DeathInfoManager.INSTANCE.setDirty();
                     GraveBlockEntity be = (GraveBlockEntity)level.getBlockEntity(placedPos);
                     if (be != null) {
                        be.setPreviousState(previousState);
                        be.setComponent(this);
                     }
                  }
               }
            );
      }
   }

   public void generateOrDrop(Direction playerDirection, DeathContext context, RespawnComponent respawnComponent) {
      ServerLevel world = context.world();
      Vec3 pos = context.deathPos();
      YigdEvents.AllowGraveGenerationEvent event = (YigdEvents.AllowGraveGenerationEvent)NeoForge.EVENT_BUS
         .post(new YigdEvents.AllowGraveGenerationEvent(context, this));
      if (YigdConfig.getConfig().inventoryConfig.itemLoss.enabled) {
         this.inventoryComponent.applyLoss();
      }

      if (!event.isGenerationAllowed()) {
         this.inventoryComponent.dropGraveItems(world, pos);
         this.expComponent.dropAll(world, pos);
      } else {
         DirectionalPos dirGravePos = this.findGravePos(playerDirection);
         BlockPos gravePos = dirGravePos.pos();
         Direction direction = dirGravePos.dir();
         ServerLevel graveWorld = this.getWorld();
         assert graveWorld != null;
         this.placeAndLoad(direction, context, gravePos, graveWorld, respawnComponent);
      }
   }

   private void placeBlockUnder() {
      if (this.world == null) {
         Yigd.LOGGER.error("Tried to place block under a grave but world was null");
      } else {
         GraveConfig.BlockUnderGrave config = YigdConfig.getConfig().graveConfig.blockUnderGrave;
         if (config.enabled) {
            BlockState currentUnder = this.world.getBlockState(this.pos.below());
            YigdEvents.AllowBlockUnderGraveGenerationEvent event = (YigdEvents.AllowBlockUnderGraveGenerationEvent)NeoForge.EVENT_BUS
               .post(new YigdEvents.AllowBlockUnderGraveGenerationEvent(this, currentUnder));
            if (event.isPlacementAllowed()) {
               Map<String, String> blockInDimMap = new HashMap<>();

               for (MapEntryConfig.StringType pair : config.blockInDimensions) {
                  blockInDimMap.put(pair.key, pair.value);
               }

               String dimName = this.worldRegistryKey.location().toString();
               if (!blockInDimMap.containsKey(dimName)) {
                  dimName = "misc";
               }

               String blockName = blockInDimMap.get(dimName);
               if (blockName == null) {
                  Yigd.LOGGER
                     .warn(
                        "Didn't place supporting block under grave in {}, at {}, {}, {}. Couldn't find dimension key in config",
                        new Object[]{dimName, this.pos.getX(), this.pos.getY(), this.pos.getZ()}
                     );
               } else {
                  Block blockUnder = (Block)BuiltInRegistries.BLOCK.get(ResourceLocation.parse(blockName));
                  boolean placed = this.world.setBlockAndUpdate(this.pos.below(), blockUnder.defaultBlockState());
                  if (!placed) {
                     Yigd.LOGGER
                        .warn(
                           "Didn't place supporting block under grave in {}, at {}, {}, {}. Block placement failed",
                           new Object[]{dimName, this.pos.getX(), this.pos.getY(), this.pos.getZ()}
                        );
                  }
               }
            }
         }
      }
   }

   public boolean replaceWithOld(BlockState newState, boolean safetyOn) {
      if (this.world == null) {
         return false;
      }

      if (safetyOn && newState.is(YigdTags.REPLACE_GRAVE_BLACKLIST)) {
         return false;
      }

      boolean placed = this.world.setBlockAndUpdate(this.pos, newState);
      newState.getBlock().setPlacedBy(this.world, this.pos, newState, null, ItemStack.EMPTY);
      return placed;
   }

   public void backUp() {
      DeathInfoManager.INSTANCE.addBackup(this.owner, this);
      DeathInfoManager.INSTANCE.setDirty();
   }

   public boolean hasExistedTicks(long time) {
      return this.world == null ? false : this.world.getGameTime() - this.creationTime.getTime() >= time;
   }

   public String getTimeUntilRobbable() {
      if (this.world == null) {
         return "0";
      }

      int tps = 20;
      GraveConfig.GraveRobbing robConfig = YigdConfig.getConfig().graveConfig.graveRobbing;
      long delay = robConfig.timeUnit.toSeconds(robConfig.afterTime) * 20L;
      long timePassed = (this.creationTime.getTime() - this.world.getGameTime() + delay) / 20L;
      long seconds = timePassed % 60L;
      long minutes = timePassed / 60L % 60L;
      long hours = timePassed / 3600L;
      return "%02d:%02d:%02d".formatted(hours, minutes, seconds);
   }

   public InteractionResult claim(ServerPlayer player, ServerLevel world, BlockState previousState, BlockPos pos, ItemStack tool) {
      YigdConfig config = YigdConfig.getConfig();
      if (this.status == GraveStatus.CLAIMED) {
         return InteractionResult.FAIL;
      }

      YigdEvents.GraveClaimEvent event = (YigdEvents.GraveClaimEvent)NeoForge.EVENT_BUS.post(new YigdEvents.GraveClaimEvent(player, world, pos, this, tool));
      if (!event.allowClaim()) {
         return InteractionResult.FAIL;
      }

      this.handleRandomSpawn(config.graveConfig.randomSpawn, world, player.getGameProfile());
      boolean thisIsARobbery = !player.getUUID().equals(this.owner.id().orElse(null));
      ItemStack graveItem = new ItemStack(Yigd.GRAVE.asItem());
      boolean addGraveItem = config.graveConfig.dropGraveBlock;
      if (config.graveConfig.dropOnRetrieve == DropType.IN_INVENTORY) {
         this.applyToPlayer(player, world, pos.getCenter(), !thisIsARobbery);
         if (addGraveItem) {
            player.addItem(graveItem);
         }
      } else if (config.graveConfig.dropOnRetrieve == DropType.ON_GROUND) {
         this.dropAllGraveItems();
         if (this.world != null && addGraveItem) {
            InventoryComponent.dropItemIfToBeDropped(graveItem, this.pos.getX(), this.pos.getY(), this.pos.getZ(), this.world);
         }
      }

      this.setStatus(GraveStatus.CLAIMED);
      if (!config.graveConfig.persistentGraves.enabled) {
         boolean replaced = false;
         if (config.graveConfig.replaceOldWhenClaimed && previousState != null) {
            replaced = this.replaceWithOld(previousState, true);
         }

         if (!replaced) {
            world.removeBlock(pos, false);
         }
      } else {
         GraveBlockEntity be = (GraveBlockEntity)world.getBlockEntity(pos);
         if (be != null) {
            BlockState state = world.getBlockState(pos);
            be.setClaimed(true);
            be.setChanged();
            world.sendBlockUpdated(pos, state, state, 3);
         }
      }

      if (thisIsARobbery && config.graveConfig.graveRobbing.notifyWhenRobbed) {
         MinecraftServer server = world.getServer();
         String robberName = player.getGameProfile().getName();
         Optional<UUID> ownerId = this.owner.id();
         ServerPlayer robbedPlayer = ownerId.<ServerPlayer>map(value -> server.getPlayerList().getPlayer(value)).orElse(null);
         if (robbedPlayer != null) {
            if (config.graveConfig.graveRobbing.tellWhoRobbed) {
               robbedPlayer.sendSystemMessage(
                  Component.translatable("text.yigd.message.inform_robbery.with_details", new Object[]{player.getGameProfile().getName()})
               );
            } else {
               robbedPlayer.sendSystemMessage(Component.translatable("text.yigd.message.inform_robbery"));
            }
         } else {
            ownerId.ifPresent(value -> Yigd.NOT_NOTIFIED_ROBBERIES.computeIfAbsent(value, uuid -> new ArrayList<>()).add(robberName));
         }
      }

      Yigd.LOGGER
         .info(
            "{} claimed a grave belonging to {} at {}, {}, {}, {}",
            new Object[]{
               player.getGameProfile().getName(),
               this.owner.name().orElse("PLAYER_NOT_FOUND"),
               this.pos.getX(),
               this.pos.getY(),
               this.pos.getZ(),
               this.worldRegistryKey.location()
            }
         );
      return InteractionResult.SUCCESS;
   }

   public boolean removeGraveBlock() {
      if (this.status == GraveStatus.UNCLAIMED) {
         this.setStatus(GraveStatus.DESTROYED);
      }

      if (this.world == null) {
         return false;
      } else if (this.world.getBlockEntity(this.pos) instanceof GraveBlockEntity grave) {
         BlockState previousState = grave.getPreviousState();
         return previousState == null ? this.world.removeBlock(this.pos, false) : this.replaceWithOld(previousState, false);
      } else {
         return false;
      }
   }

   private void handleRandomSpawn(GraveConfig.RandomSpawn config, ServerLevel world, GameProfile looter) {
      if (config.percentSpawnChance > world.random.nextInt(100)) {
         IntArrayTag ownerIdNbt = this.owner.id().<IntArrayTag>map(NbtUtils::createUUID).orElse(new IntArrayTag(new int[0]));
         IntArrayTag looterIdNbt = NbtUtils.createUUID(looter.getId());
         String summonNbt = config.spawnNbt
            .replaceAll("\\$\\{owner\\.name}", this.owner.name().orElse("Steve"))
            .replaceAll("\\$\\{owner\\.uuid}", ownerIdNbt.toString())
            .replaceAll("\\$\\{looter\\.name}", looter.getName())
            .replaceAll("\\$\\{looter\\.uuid}", looterIdNbt.toString());
         NonNullList<GraveItem> items = this.inventoryComponent.getItems();

         Matcher nbtMatcher;
         do {
            Pattern nbtPattern = Pattern.compile("\\$\\{!?item\\[[0-9]+]}");
            nbtMatcher = nbtPattern.matcher(summonNbt);
            if (!nbtMatcher.find()) {
               break;
            }

            Pattern pattern = Pattern.compile("(?<=\\$\\{!?item\\[)[0-9]+(?=]})");
            Matcher matcher = pattern.matcher(summonNbt);
            if (!matcher.find()) {
               break;
            }

            String res = matcher.group();
            if (!res.matches("[0-9]+")) {
               break;
            }

            int itemNumber = Integer.parseInt(res);
            ItemStack item = ((GraveItem)items.get(itemNumber)).stack;
            if (item.isEmpty()) {
               summonNbt = summonNbt.replaceAll("\\$\\{!?item\\[" + itemNumber + "]}", "{}");
            } else {
               try {
                  CompoundTag itemNbt = (CompoundTag)item.save(world.registryAccess());
                  boolean removeItem = summonNbt.contains("${!item[" + itemNumber + "]}");
                  summonNbt = summonNbt.replaceAll("\\$\\{!?item\\[" + itemNumber + "]}", itemNbt.toString());
                  if (removeItem) {
                     items.set(itemNumber, new GraveItem(ItemStack.EMPTY, GraveOverrideAreas.INSTANCE.defaultDropRule));
                  }
               } catch (Exception e) {
                  Yigd.LOGGER.error("Error while converting item to NBT: {}", item, e);
               }
            }
         } while (nbtMatcher.find());

         try {
            CompoundTag nbt = NbtUtils.snbtToStructure(summonNbt);
            nbt.putString("id", config.spawnEntity);
            Entity entity = EntityType.loadEntityRecursive(nbt, world, ex -> {
               ex.moveTo(this.pos, ex.getYRot(), ex.getXRot());
               return ex;
            });
            if (entity == null) {
               return;
            }

            world.addFreshEntity(entity);
         } catch (CommandSyntaxException e) {
            Yigd.LOGGER.error("Failed spawning entity on grave", e);
         }
      }
   }

   public void applyToPlayer(ServerPlayer player, ServerLevel world, Vec3 pos, boolean isGraveOwner) {
      this.applyToPlayer(player, world, pos, isGraveOwner, dropRule -> dropRule == DropRule.PUT_IN_GRAVE);
   }

   public void applyToPlayer(ServerPlayer player, ServerLevel world, Vec3 pos, boolean isGraveOwner, Predicate<DropRule> itemFilter) {
      YigdConfig config = YigdConfig.getConfig();
      this.expComponent.applyToPlayer(player);
      InventoryComponent currentPlayerInv = new InventoryComponent(player);
      InventoryComponent.clearPlayer(player);
      NonNullList<ItemStack> extraItems = NonNullList.create();
      UUID playerId = player.getUUID();
      ClaimPriority claimPriority = Yigd.CLAIM_PRIORITIES.containsKey(playerId) ? Yigd.CLAIM_PRIORITIES.get(playerId) : config.graveConfig.claimPriority;
      ClaimPriority robPriority = Yigd.ROB_PRIORITIES.containsKey(playerId) ? Yigd.CLAIM_PRIORITIES.get(playerId) : config.graveConfig.graveRobbing.robPriority;
      ClaimPriority priority = isGraveOwner ? claimPriority : robPriority;
      InventoryComponent graveInv = this.inventoryComponent.filteredInv(itemFilter);
      if (config.graveConfig.treatBindingCurse) {
         extraItems.addAll(graveInv.pullBindingCurseItems(player));
      }

      if (priority == ClaimPriority.GRAVE) {
         extraItems.addAll(graveInv.merge(currentPlayerInv, player));
         extraItems.addAll(graveInv.applyToPlayer(player));
      } else {
         extraItems.addAll(currentPlayerInv.merge(graveInv, player));
         extraItems.addAll(currentPlayerInv.applyToPlayer(player));
      }

      for (ItemStack stack : extraItems) {
         if (!player.addItem(stack)) {
            double x = pos.x();
            double y = pos.y();
            double z = pos.z();
            InventoryComponent.dropItemIfToBeDropped(stack, x, y, z, world);
         }
      }
   }

   public void dropAllGraveItems() {
      this.inventoryComponent.filteredInv(rule -> rule == DropRule.PUT_IN_GRAVE).dropAll(this.world, this.pos.getCenter());
      this.expComponent.dropAll(this.world, this.pos.getCenter());
   }

   public void onDestroyed() {
      this.setStatus(GraveStatus.DESTROYED);
      if (this.world != null) {
         PlayerList playerManager = this.world.getServer().getPlayerList();
         ServerPlayer owner = this.owner.id().<ServerPlayer>map(playerManager::getPlayer).orElse(null);
         if (owner != null) {
            YigdConfig config = YigdConfig.getConfig();
            Yigd.LOGGER
               .info(
                  "Grave belonging to {} was detected destroyed at X: {}, Y: {}, Z: {} / {}",
                  new Object[]{owner.getGameProfile().getName(), this.pos.getX(), this.pos.getY(), this.pos.getZ(), this.worldRegistryKey.location()}
               );
            if (config.graveConfig.notifyOwnerIfDestroyed) {
               owner.sendSystemMessage(Component.translatable("text.yigd.message.grave_destroyed"));
            }

            if (YigdConfig.getConfig().graveConfig.dropItemsIfDestroyed) {
               this.dropAllGraveItems();
            }
         }
      }
   }

   public LightGraveData toLightData() {
      return new LightGraveData(
         this.inventoryComponent.graveSize(), this.pos, this.expComponent.getStoredXp(), this.worldRegistryKey, this.deathMessage, this.graveId, this.status
      );
   }

   public CompoundTag toNbt(Provider lookupRegistry) {
      CompoundTag nbt = new CompoundTag();
      nbt.put("owner", (Tag)ResolvableProfile.CODEC.encodeStart(NbtOps.INSTANCE, this.owner).getOrThrow());
      nbt.put("inventory", this.inventoryComponent.toNbt(lookupRegistry));
      nbt.put("exp", this.expComponent.toNbt());
      nbt.put("world", this.getWorldRegistryKeyNbt(this.worldRegistryKey));
      nbt.put("pos", NbtUtils.writeBlockPos(this.pos));
      nbt.putString("deathMessage", Serializer.toJson(this.deathMessage, lookupRegistry));
      nbt.putUUID("graveId", this.graveId);
      nbt.putString("status", this.status.toString());
      nbt.putBoolean("locked", this.locked);
      nbt.put("creationTime", this.creationTime.toNbt());
      if (this.killerId != null) {
         nbt.putUUID("killerId", this.killerId);
      }

      return nbt;
   }

   private CompoundTag getWorldRegistryKeyNbt(ResourceKey<?> key) {
      CompoundTag nbt = new CompoundTag();
      nbt.putString("registry", key.registry().toString());
      nbt.putString("value", key.location().toString());
      return nbt;
   }

   public static GraveComponent fromNbt(CompoundTag nbt, Provider lookupRegistry, @Nullable MinecraftServer server) {
      if (nbt == null) {
         return null;
      }

      ResolvableProfile owner = (ResolvableProfile)ResolvableProfile.CODEC.parse(NbtOps.INSTANCE, nbt.get("owner")).getOrThrow();
      InventoryComponent inventoryComponent = InventoryComponent.fromNbt(nbt.getCompound("inventory"), lookupRegistry);
      ExpComponent expComponent = ExpComponent.fromNbt(nbt.getCompound("exp"));
      ResourceKey<Level> worldKey = getRegistryKeyFromNbt(nbt.getCompound("world"));
      Optional<BlockPos> pos = NbtUtils.readBlockPos(nbt, "pos");
      Component deathMessage = Serializer.fromJson(nbt.getString("deathMessage"), lookupRegistry);
      UUID graveId = nbt.getUUID("graveId");
      GraveStatus status = GraveStatus.valueOf(nbt.getString("status"));
      boolean locked = nbt.getBoolean("locked");
      TimePoint creationTime = TimePoint.fromNbt(nbt.getCompound("creationTime"));
      UUID killerId = nbt.contains("killerId") ? nbt.getUUID("killerId") : null;
      if (server != null) {
         ServerLevel world = server.getLevel(worldKey);
         if (world != null) {
            return new GraveComponent(
               owner, inventoryComponent, expComponent, world, pos.orElse(BlockPos.ZERO), deathMessage, graveId, status, locked, creationTime, killerId
            );
         }
      }

      return new GraveComponent(
         owner, inventoryComponent, expComponent, worldKey, pos.orElse(BlockPos.ZERO), deathMessage, graveId, status, locked, creationTime, killerId
      );
   }

   private static ResourceKey<Level> getRegistryKeyFromNbt(CompoundTag nbt) {
      String registry = nbt.getString("registry");
      String value = nbt.getString("value");
      ResourceKey<Registry<Level>> r = ResourceKey.createRegistryKey(ResourceLocation.parse(registry));
      return ResourceKey.create(r, ResourceLocation.parse(value));
   }
}
