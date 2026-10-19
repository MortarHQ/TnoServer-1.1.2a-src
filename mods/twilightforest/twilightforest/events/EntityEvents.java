package twilightforest.events;

import java.util.List;
import java.util.Optional;
import java.util.Map.Entry;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.ClickEvent.Action;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Interaction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.EnderMan.EndermanTakeBlockGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.LeadItem;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallSkullBlock;
import net.minecraft.world.level.block.SkullBlock.Types;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Post;
import net.neoforged.neoforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.AdvancementEvent.AdvancementEarnEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.LeftClickEmpty;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.neoforged.neoforge.event.level.BlockEvent.BreakEvent;
import net.neoforged.neoforge.event.level.ExplosionEvent.Detonate;
import net.neoforged.neoforge.event.level.LevelEvent.PotentialSpawns;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import twilightforest.TwilightForestMod;
import twilightforest.advancements.DrinkFromFlaskTrigger;
import twilightforest.beanification.Autowired;
import twilightforest.beanification.Component;
import twilightforest.beanification.PostConstruct;
import twilightforest.block.AbstractSkullCandleBlock;
import twilightforest.block.CloudBlock;
import twilightforest.block.LightableBlock;
import twilightforest.block.SkullChestBlock;
import twilightforest.block.WroughtIronFenceBlock;
import twilightforest.block.entity.SkullCandleBlockEntity;
import twilightforest.block.entity.SkullChestBlockEntity;
import twilightforest.components.entity.MultiplayerInclusivityAttachment;
import twilightforest.components.entity.PotionFlaskTrackingAttachment;
import twilightforest.config.TFConfig;
import twilightforest.data.tags.EntityTagGenerator;
import twilightforest.enchantment.ApplyFrostedEffect;
import twilightforest.entity.passive.quest.ram.QuestingRamCurrentContext;
import twilightforest.entity.projectile.ITFProjectile;
import twilightforest.entity.projectile.LichBomb;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFDamageTypes;
import twilightforest.init.TFDataAttachments;
import twilightforest.init.TFDataComponents;
import twilightforest.init.TFDataMaps;
import twilightforest.init.TFDimensionData;
import twilightforest.init.TFItems;
import twilightforest.init.TFMobEffects;
import twilightforest.init.TFSounds;
import twilightforest.init.TFStats;
import twilightforest.item.FieryArmorItem;
import twilightforest.item.YetiArmorItem;
import twilightforest.network.SyncQuestsPacket;
import twilightforest.network.WipeOreMeterPacket;
import twilightforest.util.datamaps.EntityTransformation;
import twilightforest.util.entities.EntityUtil;
import twilightforest.util.entities.OminousFireDamageSource;
import twilightforest.world.components.structures.SpawnIndexProvider;
import twilightforest.world.components.structures.start.TFStructureStart;
import twilightforest.world.components.structures.type.HollowHillStructure;
import twilightforest.world.components.structures.util.ControlledSpawns;

@Component
public class EntityEvents {
   @Autowired
   private QuestingRamCurrentContext questingRamCurrentContext;
   private static final boolean SHIELD_PARRY_MOD_LOADED = ModList.get().isLoaded("parry");

   @PostConstruct
   private void setup() {
      NeoForge.EVENT_BUS.addListener(this::ominousFireConversion);
      NeoForge.EVENT_BUS.addListener(this::zombifiedPlayerAttacks);
      NeoForge.EVENT_BUS.addListener(this::alertPlayerCastleIsWIP);
      NeoForge.EVENT_BUS.addListener(this::attachLeadToWroughtFence);
      NeoForge.EVENT_BUS.addListener(this::wipeOreMeterOnLeftClick);
      NeoForge.EVENT_BUS.addListener(this::entityHurts);
      NeoForge.EVENT_BUS.addListener(this::onCasketBreak);
      NeoForge.EVENT_BUS.addListener(this::reduceFrostedEffectIfOnFire);
      NeoForge.EVENT_BUS.addListener(this::onParryProjectile);
      NeoForge.EVENT_BUS.addListener(this::createSkullCandle);
      NeoForge.EVENT_BUS.addListener(this::addCloudJumpParticles);
      NeoForge.EVENT_BUS.addListener(this::structureSpecialSpawns);
      NeoForge.EVENT_BUS.addListener(this::removeCastleTextIfAttacked);
      NeoForge.EVENT_BUS.addListener(this::adjustEntityHealthInMultiplayerFights);
      NeoForge.EVENT_BUS.addListener(this::addQualifiedGroupPlayerIfNeeded);
      NeoForge.EVENT_BUS.addListener(this::grantGroupAdvancementIfNeeded);
      NeoForge.EVENT_BUS.addListener(this::lichBombsDontBlowUpItems);
      NeoForge.EVENT_BUS.addListener(this::handleQuestSyncing);
      NeoForge.EVENT_BUS.addListener(this::resetFlaskLogic);
      NeoForge.EVENT_BUS.addListener(this::handleLeashPathingOverrides);
      NeoForge.EVENT_BUS.addListener(this::stopEndermenFromGrabbingBlocksInTF);
   }

   private void ominousFireConversion(LivingDeathEvent event) {
      if (!event.isCanceled() && event.getSource().is(TFDamageTypes.OMINOUS_FIRE)) {
         EntityTransformation dataMap = (EntityTransformation)event.getEntity().getType().builtInRegistryHolder().getData(TFDataMaps.OMINOUS_FIRE);
         if (event.getEntity() instanceof ServerPlayer player) {
            Zombie zombie = (Zombie)EntityType.ZOMBIE.create(player.level());
            zombie.setData(TFDataAttachments.ZOMBIFIED_PLAYER, player.getGameProfile());
            zombie.setCustomName(player.getName());
            zombie.copyPosition(player);
            zombie.setCanPickUpLoot(true);
            zombie.setBaby(false);
            EventHooks.finalizeMobSpawn(
               zombie, player.serverLevel(), player.level().getCurrentDifficultyAt(player.blockPosition()), MobSpawnType.CONVERSION, null
            );
            player.level().addFreshEntity(zombie);
         } else if (dataMap != null && event.getEntity().level() instanceof ServerLevel) {
            EntityUtil.convertEntity(event.getEntity(), dataMap.result());
         }
      }
   }

   private void zombifiedPlayerAttacks(LivingIncomingDamageEvent event) {
      if (!(event.getSource() instanceof OminousFireDamageSource)
         && event.getSource().getEntity() instanceof Zombie zombie
         && zombie.hasData(TFDataAttachments.ZOMBIFIED_PLAYER)) {
         float amount = event.getAmount();
         event.setCanceled(true);
         event.getEntity().hurt(new OminousFireDamageSource(event.getSource()), amount);
      }
   }

   private void alertPlayerCastleIsWIP(AdvancementEarnEvent event) {
      if (event.getAdvancement().id().equals(TwilightForestMod.prefix("progression_end"))) {
         event.getEntity()
            .sendSystemMessage(
               net.minecraft.network.chat.Component.translatable(
                  "gui.twilightforest.progression_end.message",
                  new Object[]{
                     net.minecraft.network.chat.Component.translatable("gui.twilightforest.progression_end.discord")
                        .withStyle(
                           style -> style.withColor(ChatFormatting.BLUE)
                              .applyFormat(ChatFormatting.UNDERLINE)
                              .withClickEvent(new ClickEvent(Action.OPEN_URL, "https://discord.experiment115.com/"))
                        )
                  }
               )
            );
      }
   }

   private void attachLeadToWroughtFence(RightClickBlock event) {
      Player player = event.getEntity();
      ItemStack stack = player.getItemInHand(event.getHand());
      if (stack.is(Items.LEAD)) {
         BlockPos pos = event.getPos();
         BlockState state = event.getLevel().getBlockState(pos);
         if (state.is(TFBlocks.WROUGHT_IRON_FENCE)
            && state.getValue(WroughtIronFenceBlock.POST) != WroughtIronFenceBlock.PostState.NONE
            && !event.getLevel().isClientSide()) {
            LeadItem.bindPlayerMobs(player, event.getLevel(), event.getPos());
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.SUCCESS);
         }
      }
   }

   private void wipeOreMeterOnLeftClick(LeftClickEmpty event) {
      ItemStack item = event.getItemStack();
      if (item.is((Item)TFItems.ORE_METER.get()) && (item.has(TFDataComponents.ORE_DATA) || item.has(TFDataComponents.ORE_FILTER))) {
         PacketDistributor.sendToServer(new WipeOreMeterPacket(event.getHand()), new CustomPacketPayload[0]);
         item.remove(TFDataComponents.ORE_DATA);
         item.remove(TFDataComponents.ORE_FILTER);
         event.getLevel()
            .playSound(
               event.getEntity(),
               event.getEntity().blockPosition(),
               (SoundEvent)TFSounds.ORE_METER_CLEAR.get(),
               SoundSource.PLAYERS,
               1.25F,
               event.getLevel().getRandom().nextFloat() * 0.2F + 0.6F
            );
      }
   }

   private void entityHurts(Post event) {
      LivingEntity living = event.getEntity();
      DamageSource source = event.getSource();
      Entity trueSource = source.getEntity();
      if (source.getEntity() != null && trueSource != null && event.getOriginalDamage() > 0.0F) {
         int fireLevel = getGearCoverage(living, false) * 5;
         int chillLevel = getGearCoverage(living, true);
         if (fireLevel > 0 && living.getRandom().nextInt(25) < fireLevel && !trueSource.fireImmune()) {
            trueSource.igniteForSeconds(fireLevel / 2);
         }

         if (trueSource instanceof LivingEntity target) {
            ApplyFrostedEffect.doChillAuraEffect(target, chillLevel * 5 + 5, chillLevel, chillLevel > 0);
         }
      }

      if (source.getMsgId().equals("arrow")
         && trueSource instanceof Player player
         && player.getItemInHand(player.getUsedItemHand()).is((Item)TFItems.TRIPLE_BOW.get())) {
         living.invulnerableTime = 0;
      }
   }

   private void onCasketBreak(BreakEvent event) {
      Player player = event.getPlayer();
      if (event.getState().getBlock() instanceof SkullChestBlock && event.getLevel().getBlockEntity(event.getPos()) instanceof SkullChestBlockEntity casket) {
         ResolvableProfile checker = casket.owner;
         if (checker != null && !casket.isEmpty() && (!player.hasPermissions(3) || !player.getGameProfile().equals(checker.gameProfile()))) {
            event.setCanceled(true);
         }
      }
   }

   private void reduceFrostedEffectIfOnFire(LivingIncomingDamageEvent event) {
      if (!event.isCanceled()) {
         LivingEntity living = event.getEntity();
         Optional.ofNullable(living.getEffect(TFMobEffects.FROSTY)).ifPresent(mobEffectInstance -> {
            DamageContainer container = event.getContainer();
            if (container.getSource().typeHolder().is(DamageTypes.FREEZE)) {
               container.setNewDamage(container.getOriginalDamage() + mobEffectInstance.getAmplifier() / 2);
            } else if (container.getSource().typeHolder().is(DamageTypeTags.IS_FIRE)) {
               living.removeEffect(TFMobEffects.FROSTY);
               mobEffectInstance.amplifier--;
               if (mobEffectInstance.amplifier >= 0) {
                  living.addEffect(mobEffectInstance);
               }
            }
         });
      }
   }

   private void onParryProjectile(ProjectileImpactEvent event) {
      Projectile projectile = event.getProjectile();
      if (!projectile.getCommandSenderWorld().isClientSide()
         && !SHIELD_PARRY_MOD_LOADED
         && (TFConfig.parryNonTwilightAttacks || projectile instanceof ITFProjectile)
         && event.getRayTraceResult() instanceof EntityHitResult result
         && result.getEntity() instanceof LivingEntity entityBlocking
         && entityBlocking.isBlocking()
         && entityBlocking.getUseItem().getUseDuration(entityBlocking) - entityBlocking.getUseItemRemainingTicks() <= TFConfig.shieldParryTicks) {
         projectile.deflect(ProjectileDeflection.AIM_DEFLECT, entityBlocking, entityBlocking, true);
         event.setCanceled(true);
      }
   }

   private void createSkullCandle(RightClickBlock event) {
      ItemStack stack = event.getItemStack();
      Level level = event.getLevel();
      BlockPos pos = event.getPos();
      BlockState state = level.getBlockState(pos);
      if (!TFConfig.disableSkullCandles
         && stack.is(ItemTags.CANDLES)
         && BuiltInRegistries.ITEM.getKey(stack.getItem()).getNamespace().equals("minecraft")
         && !event.getEntity().isShiftKeyDown()
         && state.getBlock() instanceof AbstractSkullBlock skull
         && BuiltInRegistries.BLOCK.getKey(state.getBlock()).getNamespace().equals("minecraft")) {
         Types type = (Types)skull.getType();
         boolean wall = state.getBlock() instanceof WallSkullBlock;
         switch (type) {
            case SKELETON:
               if (wall) {
                  makeSkullCandle(event, (Block)TFBlocks.SKELETON_WALL_SKULL_CANDLE.get());
               } else {
                  makeSkullCandle(event, (Block)TFBlocks.SKELETON_SKULL_CANDLE.get());
               }
               break;
            case WITHER_SKELETON:
               if (wall) {
                  makeSkullCandle(event, (Block)TFBlocks.WITHER_SKELE_WALL_SKULL_CANDLE.get());
               } else {
                  makeSkullCandle(event, (Block)TFBlocks.WITHER_SKELE_SKULL_CANDLE.get());
               }
               break;
            case PLAYER:
               if (wall) {
                  makeSkullCandle(event, (Block)TFBlocks.PLAYER_WALL_SKULL_CANDLE.get());
               } else {
                  makeSkullCandle(event, (Block)TFBlocks.PLAYER_SKULL_CANDLE.get());
               }
               break;
            case ZOMBIE:
               if (wall) {
                  makeSkullCandle(event, (Block)TFBlocks.ZOMBIE_WALL_SKULL_CANDLE.get());
               } else {
                  makeSkullCandle(event, (Block)TFBlocks.ZOMBIE_SKULL_CANDLE.get());
               }
               break;
            case CREEPER:
               if (wall) {
                  makeSkullCandle(event, (Block)TFBlocks.CREEPER_WALL_SKULL_CANDLE.get());
               } else {
                  makeSkullCandle(event, (Block)TFBlocks.CREEPER_SKULL_CANDLE.get());
               }
               break;
            case PIGLIN:
               if (wall) {
                  makeSkullCandle(event, (Block)TFBlocks.PIGLIN_WALL_SKULL_CANDLE.get());
               } else {
                  makeSkullCandle(event, (Block)TFBlocks.PIGLIN_SKULL_CANDLE.get());
               }
               break;
            default:
               return;
         }

         stack.consume(1, event.getEntity());
         event.getEntity().swing(event.getHand());
         if (event.getEntity() instanceof ServerPlayer) {
            event.getEntity().awardStat((ResourceLocation)TFStats.SKULL_CANDLES_MADE.get());
         }

         event.setCanceled(true);
      }
   }

   private static void makeSkullCandle(RightClickBlock event, Block newBlock) {
      ResolvableProfile profile = null;
      Level level = event.getLevel();
      if (level.getBlockEntity(event.getPos()) instanceof SkullBlockEntity skull) {
         profile = skull.getOwnerProfile();
      }

      level.playSound(null, event.getPos(), SoundEvents.CANDLE_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
      level.setBlockAndUpdate(
         event.getPos(),
         (BlockState)newBlock.withPropertiesOf(level.getBlockState(event.getPos())).setValue(AbstractSkullCandleBlock.LIGHTING, LightableBlock.Lighting.NONE)
      );
      level.setBlockEntity(
         new SkullCandleBlockEntity(
            event.getPos(),
            (BlockState)newBlock.withPropertiesOf(level.getBlockState(event.getPos()))
               .setValue(AbstractSkullCandleBlock.LIGHTING, LightableBlock.Lighting.NONE),
            AbstractSkullCandleBlock.candleToCandleColor(event.getItemStack().getItem()).getValue()
         )
      );
      if (level.getBlockEntity(event.getPos()) instanceof SkullCandleBlockEntity sc) {
         sc.setOwner(profile);
      }
   }

   public static int getGearCoverage(LivingEntity entity, boolean yeti) {
      int amount = 0;

      for (ItemStack armor : entity.getArmorSlots()) {
         if (!armor.isEmpty() && (yeti ? armor.getItem() instanceof YetiArmorItem : armor.getItem() instanceof FieryArmorItem)) {
            amount++;
         }
      }

      return amount;
   }

   private void addCloudJumpParticles(LivingJumpEvent event) {
      LivingEntity living = event.getEntity();
      if (living.level().isClientSide() && !living.isSpectator() && living.level().getBlockState(living.getOnPos()).getBlock() instanceof CloudBlock) {
         for (int i = 0; i < 12; i++) {
            CloudBlock.addEntityMovementParticles(living.level(), living.getOnPos(), living, true);
         }
      }
   }

   private static int getSpawnListIndexAt(StructureStart start, BlockPos pos) {
      int highestFoundIndex = -1;

      for (StructurePiece component : start.getPieces()) {
         if (component.getBoundingBox().isInside(pos)
            && component instanceof SpawnIndexProvider indexProvider
            && indexProvider.getSpawnIndex() > highestFoundIndex) {
            highestFoundIndex = indexProvider.getSpawnIndex();
         }
      }

      return highestFoundIndex;
   }

   @Nullable
   public static List<SpawnerData> gatherPotentialSpawns(StructureManager structureManager, MobCategory classification, BlockPos pos) {
      for (StructureStart start : structureManager.startsForStructure(new ChunkPos(pos), s -> s instanceof ControlledSpawns)) {
         if (start.getStructure() instanceof ControlledSpawns landmark && start.isValid()) {
            if (classification != MobCategory.MONSTER) {
               return landmark.getSpawnableList(classification);
            }

            if (start instanceof TFStructureStart s && s.isConquered()) {
               return null;
            }

            if (landmark instanceof HollowHillStructure hollowHill && !hollowHill.canSpawnMob(pos, start.getBoundingBox())) {
               return null;
            }

            int index = getSpawnListIndexAt(start, pos);
            if (index < 0) {
               return null;
            }

            return landmark.getSpawnableMonsterList(index);
         }
      }

      return null;
   }

   private void structureSpecialSpawns(PotentialSpawns event) {
      if (event.getLevel() instanceof ServerLevel serverLevel) {
         List<SpawnerData> potentialStructureSpawns = gatherPotentialSpawns(serverLevel.structureManager(), event.getMobCategory(), event.getPos());
         if (potentialStructureSpawns != null) {
            List.copyOf(event.getSpawnerDataList()).forEach(event::removeSpawnerData);
            potentialStructureSpawns.forEach(event::addSpawnerData);
         }
      }
   }

   private void removeCastleTextIfAttacked(AttackEntityEvent event) {
      if (event.getTarget().level() instanceof ServerLevel level
         && event.getTarget() instanceof Interaction interaction
         && interaction.getTags().contains("final_castle_wip")) {
         AABB bounds = interaction.getBoundingBox();
         level.getEntities(interaction, bounds, e -> e instanceof Display).forEach(Entity::discard);
         interaction.discard();
      }
   }

   private void adjustEntityHealthInMultiplayerFights(FinalizeSpawnEvent event) {
      if (event.getEntity().getType().is(EntityTagGenerator.MULTIPLAYER_INCLUSIVE_ENTITIES) && TFConfig.multiplayerFightAdjuster.adjustsHealth()) {
         List<ServerPlayer> nearbyPlayers = event.getLevel()
            .getEntitiesOfClass(
               ServerPlayer.class,
               event.getEntity().getBoundingBox().inflate(32.0, 10.0, 32.0),
               player -> EntitySelector.NO_CREATIVE_OR_SPECTATOR.and(EntitySelector.ENTITY_STILL_ALIVE).test(player)
            );
         if (nearbyPlayers.size() > 1 && event.getEntity().getAttribute(Attributes.MAX_HEALTH) != null) {
            event.getEntity()
               .getAttribute(Attributes.MAX_HEALTH)
               .addPermanentModifier(
                  new AttributeModifier(
                     TwilightForestMod.prefix("group_health_boost"),
                     getHealthBasedOnDifficulty(event.getDifficulty().getDifficulty()) * (nearbyPlayers.size() - 1),
                     Operation.ADD_VALUE
                  )
               );
         }
      }
   }

   private static double getHealthBasedOnDifficulty(Difficulty difficulty) {
      return switch (difficulty) {
         case EASY -> 20.0;
         case NORMAL -> 40.0;
         case HARD -> 60.0;
         default -> 0.0;
      };
   }

   private void addQualifiedGroupPlayerIfNeeded(Post event) {
      if (event.getEntity().getType().is(EntityTagGenerator.MULTIPLAYER_INCLUSIVE_ENTITIES)) {
         MultiplayerInclusivityAttachment data = (MultiplayerInclusivityAttachment)event.getEntity().getData(TFDataAttachments.MULTIPLAYER_FIGHT);
         if (event.getSource().getEntity() != null) {
            data.maybeAddQualifiedPlayer(event.getSource().getEntity());
         }
      }
   }

   private void grantGroupAdvancementIfNeeded(LivingDeathEvent event) {
      if (!event.isCanceled() && event.getEntity().hasData(TFDataAttachments.MULTIPLAYER_FIGHT)) {
         ((MultiplayerInclusivityAttachment)event.getEntity().getData(TFDataAttachments.MULTIPLAYER_FIGHT)).grantGroupAdvancement(event.getEntity());
      }
   }

   private void lichBombsDontBlowUpItems(Detonate event) {
      if (event.getExplosion().getDirectSourceEntity() instanceof LichBomb) {
         event.getAffectedEntities().removeIf(entity -> entity instanceof ItemEntity || entity instanceof LichBomb);
      }
   }

   private void handleQuestSyncing(OnDatapackSyncEvent event) {
      if (event.getPlayer() != null) {
         PacketDistributor.sendToPlayer(event.getPlayer(), new SyncQuestsPacket(this.questingRamCurrentContext.getContext()), new CustomPacketPayload[0]);
      } else {
         event.getPlayerList()
            .getPlayers()
            .forEach(
               player -> PacketDistributor.sendToPlayer(player, new SyncQuestsPacket(this.questingRamCurrentContext.getContext()), new CustomPacketPayload[0])
            );
      }
   }

   private void resetFlaskLogic(AdvancementEarnEvent event) {
      for (Entry<String, Criterion<?>> criteria : event.getAdvancement().value().criteria().entrySet()) {
         if (criteria.getValue().trigger() instanceof DrinkFromFlaskTrigger) {
            ((PotionFlaskTrackingAttachment)event.getEntity().getData(TFDataAttachments.FLASK_DOSES)).resetDoses();
            break;
         }
      }
   }

   private void handleLeashPathingOverrides(EntityJoinLevelEvent event) {
      if (event.getEntity() instanceof PathfinderMob mob && mob.hasData(TFDataAttachments.LEASH_PATHFINDER_OVERRIDE)) {
         if (!mob.mayBeLeashed()) {
            mob.removeData(TFDataAttachments.LEASH_PATHFINDER_OVERRIDE);
         }
      }
   }

   private void stopEndermenFromGrabbingBlocksInTF(EntityJoinLevelEvent event) {
      if (event.getEntity() instanceof EnderMan enderMan) {
         enderMan.goalSelector.getAvailableGoals().stream().filter(g -> g.getGoal() instanceof EndermanTakeBlockGoal).findAny().ifPresent(g -> {
            enderMan.goalSelector.removeGoal(g.getGoal());
            enderMan.goalSelector.addGoal(g.getPriority(), new EntityEvents.ExtendedEndermanTakeBlockGoal((EndermanTakeBlockGoal)g.getGoal(), enderMan));
         });
      }
   }

   static class ExtendedEndermanTakeBlockGoal extends EndermanTakeBlockGoal {
      private final EndermanTakeBlockGoal delegate;
      private final EnderMan enderman;

      public ExtendedEndermanTakeBlockGoal(EndermanTakeBlockGoal delegate, EnderMan enderman) {
         super(enderman);
         this.delegate = delegate;
         this.enderman = enderman;
      }

      public boolean canUse() {
         return this.delegate.canUse() && !this.enderman.level().dimensionTypeRegistration().is(TFDimensionData.TWILIGHT_DIM_TYPE);
      }

      public void tick() {
         this.delegate.tick();
      }
   }
}
