package twilightforest.events;

import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.BlockSnapshot;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.neoforged.neoforge.event.level.BlockEvent.BreakEvent;
import net.neoforged.neoforge.event.level.BlockEvent.EntityMultiPlaceEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;
import net.neoforged.neoforge.network.PacketDistributor;
import twilightforest.advancements.SimpleAdvancementTrigger;
import twilightforest.beanification.Component;
import twilightforest.beanification.PostConstruct;
import twilightforest.block.TFPortalBlock;
import twilightforest.config.TFConfig;
import twilightforest.data.tags.BlockTagGenerator;
import twilightforest.data.tags.ItemTagGenerator;
import twilightforest.entity.monster.Kobold;
import twilightforest.init.TFAdvancements;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFDimension;
import twilightforest.network.AreaProtectionPacket;
import twilightforest.network.MissingAdvancementToastPacket;
import twilightforest.network.StructureProtectionPacket;
import twilightforest.util.Enforcement;
import twilightforest.util.PlayerHelper;
import twilightforest.util.landmarks.LandmarkUtil;
import twilightforest.world.components.structures.util.AdvancementLockedStructure;
import twilightforest.world.components.structures.util.ProgressionPiece;
import twilightforest.world.components.structures.util.ProgressionStructure;

@Component
public class ProgressionEvents {
   @PostConstruct
   private void setup() {
      NeoForge.EVENT_BUS.addListener(this::preventLockedAreaBlockBreaking);
      NeoForge.EVENT_BUS.addListener(this::preventLockedAreaBlockPlacing);
      NeoForge.EVENT_BUS.addListener(this::preventLockedAreaBlockInteracting);
      NeoForge.EVENT_BUS.addListener(this::preventLockedAreaMultiblocks);
      NeoForge.EVENT_BUS.addListener(this::preventLockedAreaEntityDamage);
      NeoForge.EVENT_BUS.addListener(this::performProtectionAndPortalChecks);
   }

   private void preventLockedAreaBlockBreaking(BreakEvent event) {
      if (event.getLevel() instanceof ServerLevel level && !event.isCanceled()) {
         BlockPos pos = event.getPos();
         if (isBlockProtectedFromBreaking(level, pos) && isAreaProtected(level, event.getPlayer(), pos)) {
            event.setCanceled(true);
         }
      }
   }

   private void preventLockedAreaBlockPlacing(RightClickBlock event) {
      if (event.getLevel() instanceof ServerLevel level && !event.isCanceled()) {
         BlockPos pos = event.getPos();
         if (isAreaProtected(level, event.getEntity(), pos)) {
            event.setCanceled(true);
            event.getEntity().inventoryMenu.sendAllDataToRemote();
         }
      }
   }

   private void preventLockedAreaMultiblocks(EntityMultiPlaceEvent event) {
      Entity entity = event.getEntity();
      if (event.getLevel() instanceof ServerLevel level && entity instanceof Player player && !event.isCanceled()) {
         for (BlockSnapshot snapshot : event.getReplacedBlockSnapshots()) {
            BlockPos pos = snapshot.getPos();
            if (isBlockProtectedFromBreaking(level, pos) && isAreaProtected(level, player, pos)) {
               event.setCanceled(true);
               player.inventoryMenu.sendAllDataToRemote();
               break;
            }
         }
      }
   }

   private void preventLockedAreaBlockInteracting(RightClickBlock event) {
      Player player = event.getEntity();
      Level level = player.level();
      if (!level.isClientSide()
         && level instanceof ServerLevel serverLevel
         && isBlockProtectedFromInteraction(level, event.getPos())
         && isAreaProtected(serverLevel, player, event.getPos())) {
         event.setUseBlock(TriState.FALSE);
      }
   }

   private static boolean isAreaProtected(ServerLevel level, Player player, BlockPos pos) {
      if (!player.getAbilities().instabuild && !player.isSpectator() && LandmarkUtil.isProgressionEnforced(level) && !(player instanceof FakePlayer)) {
         Optional<StructureStart> struct = LandmarkUtil.locateNearestLandmarkStart(
            level, SectionPos.blockToSectionCoord(pos.getX()), SectionPos.blockToSectionCoord(pos.getZ())
         );
         if (struct.isPresent()) {
            StructureStart structureStart = struct.get();
            if (structureStart.getPieces()
                  .stream()
                  .anyMatch(structurePiece -> structurePiece.getBoundingBox().isInside(pos) && isPieceProtected(structurePiece))
               && structureStart.getStructure() instanceof ProgressionStructure structureHints
               && !structureHints.doesPlayerHaveRequiredAdvancements(player)) {
               List<BoundingBox> boxes = new ArrayList<>();
               structureStart.getPieces().forEach(piece -> {
                  if (piece.getBoundingBox().isInside(pos)) {
                     boxes.add(piece.getBoundingBox());
                  }
               });
               sendAreaProtectionPacket(level, pos, boxes);
               structureHints.trySpawnHintMonster(level, player, pos);
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private void preventLockedAreaEntityDamage(LivingIncomingDamageEvent event) {
      LivingEntity living = event.getEntity();
      if (living.level() instanceof ServerLevel serverLevel
         && living instanceof Enemy
         && event.getSource().getEntity() instanceof Player
         && !(living instanceof Kobold)
         && isAreaProtected(serverLevel, (Player)event.getSource().getEntity(), new BlockPos(living.blockPosition()))) {
         event.setCanceled(true);
      }
   }

   private void performProtectionAndPortalChecks(Post event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         if (player.level() instanceof ServerLevel world) {
            if (!TFConfig.disablePortalCreation
               && player.tickCount % (!TFConfig.checkPortalPlacement ? 100 : 20) == 0
               && world.getServer().getProfilePermissions(player.getGameProfile()) >= TFConfig.portalCreationPermission) {
               checkForPortalCreation(player, world, TFConfig.portalCreationPermission >= 3 ? 4.0F : 32.0F);
            }

            if (player.tickCount % 20 == 0 && LandmarkUtil.isProgressionEnforced(world) && !player.isCreative() && !player.isSpectator()) {
               Enforcement.enforceBiomeProgression(player, world);
            }

            if (player.tickCount % 100 == 0 && LandmarkUtil.isProgressionEnforced(world)) {
               if (!player.isCreative() && !player.isSpectator()) {
                  checkForLockedStructuresSendPacket(player, world);
               } else {
                  sendAllClearPacket(player);
               }
            }
         }
      }
   }

   private static boolean checkForLockedStructuresSendPacket(Player player, ServerLevel world) {
      ChunkPos chunkPlayer = player.chunkPosition();
      return LandmarkUtil.locateNearestLandmarkStart(world, chunkPlayer.x, chunkPlayer.z)
         .map(
            structureStart -> {
               if (structureStart.getStructure() instanceof AdvancementLockedStructure advancementLockedStructure
                  && !advancementLockedStructure.doesPlayerHaveRequiredAdvancements(player)) {
                  List<Pair<BoundingBox, Boolean>> boundingBoxesData = structureStart.getPieces()
                     .stream()
                     .map(piece -> Pair.of(isPieceProtected(piece) ? piece.getBoundingBox().inflatedBy(4) : piece.getBoundingBox(), isPieceProtected(piece)))
                     .toList();
                  sendStructureProtectionPacket(player, boundingBoxesData);
                  return true;
               } else {
                  sendAllClearPacket(player);
                  return false;
               }
            }
         )
         .orElse(false);
   }

   private static void checkForPortalCreation(ServerPlayer player, Level world, float rangeToCheck) {
      if (world.dimension().location().equals(ResourceLocation.parse(TFConfig.originDimension))
         || TFDimension.isTwilightPortalDestination(world)
         || TFConfig.allowPortalsInOtherDimensions) {
         List<ItemEntity> itemList = world.getEntitiesOfClass(ItemEntity.class, player.getBoundingBox().inflate(rangeToCheck));
         ItemEntity qualified = null;

         for (ItemEntity entityItem : itemList) {
            if (entityItem.getItem().is(ItemTagGenerator.PORTAL_ACTIVATOR)
               && ((TFPortalBlock)TFBlocks.TWILIGHT_PORTAL.get()).canFormPortal(world.getBlockState(entityItem.blockPosition()))
               && Objects.equals(entityItem.getOwner(), player)) {
               qualified = entityItem;
               break;
            }
         }

         if (qualified == null) {
            return;
         }

         if (!player.isCreative() && !player.isSpectator() && TFConfig.getPortalLockingAdvancement(player) != null) {
            AdvancementHolder requirement = PlayerHelper.getAdvancement(player, Objects.requireNonNull(TFConfig.getPortalLockingAdvancement(player)));
            if (requirement != null && !PlayerHelper.doesPlayerHaveRequiredAdvancement(player, requirement)) {
               player.displayClientMessage(TFPortalBlock.PORTAL_UNWORTHY, true);
               if (!TFPortalBlock.isPlayerNotifiedOfRequirement(player)) {
                  DisplayInfo info = (DisplayInfo)requirement.value().display().orElse(null);
                  PacketDistributor.sendToPlayer(
                     player,
                     info == null
                        ? new MissingAdvancementToastPacket(
                           net.minecraft.network.chat.Component.translatable("twilightforest.ui.advancement.no_title"),
                           new ItemStack((ItemLike)TFBlocks.TWILIGHT_PORTAL_MINIATURE_STRUCTURE.get())
                        )
                        : new MissingAdvancementToastPacket(info.getTitle(), info.getIcon()),
                     new CustomPacketPayload[0]
                  );
                  TFPortalBlock.playerNotifiedOfRequirement(player);
               }

               return;
            }
         }

         Random rand = new Random();

         for (int i = 0; i < 2; i++) {
            double vx = rand.nextGaussian() * 0.02;
            double vy = rand.nextGaussian() * 0.02;
            double vz = rand.nextGaussian() * 0.02;
            world.addParticle(ParticleTypes.EFFECT, qualified.getX(), qualified.getY() + 0.2, qualified.getZ(), vx, vy, vz);
         }

         if (((TFPortalBlock)TFBlocks.TWILIGHT_PORTAL.get()).tryToCreatePortal(world, qualified.blockPosition(), qualified, player)) {
            ((SimpleAdvancementTrigger)TFAdvancements.MADE_TF_PORTAL.get()).trigger(player);
         }
      }
   }

   private static boolean isPieceProtected(StructurePiece piece) {
      return !(piece instanceof ProgressionPiece progressionPiece && !progressionPiece.isComponentProtected());
   }

   private static boolean isBlockProtectedFromInteraction(BlockGetter level, BlockPos pos) {
      return level.getBlockState(pos).is(BlockTagGenerator.STRUCTURE_BANNED_INTERACTIONS);
   }

   private static boolean isBlockProtectedFromBreaking(BlockGetter level, BlockPos pos) {
      return !level.getBlockState(pos).is(BlockTagGenerator.PROGRESSION_ALLOW_BREAKING);
   }

   private static void sendAreaProtectionPacket(ServerLevel level, BlockPos pos, List<BoundingBox> sbb) {
      PacketDistributor.sendToPlayersNear(level, null, pos.getX(), pos.getY(), pos.getZ(), 64.0, new AreaProtectionPacket(sbb, pos), new CustomPacketPayload[0]);
   }

   private static void sendStructureProtectionPacket(Player player, List<Pair<BoundingBox, Boolean>> sbbData) {
      if (player instanceof ServerPlayer sp) {
         PacketDistributor.sendToPlayer(sp, new StructureProtectionPacket(Optional.of(sbbData)), new CustomPacketPayload[0]);
      }
   }

   private static void sendAllClearPacket(Player player) {
      if (player instanceof ServerPlayer sp) {
         PacketDistributor.sendToPlayer(sp, new StructureProtectionPacket(Optional.empty()), new CustomPacketPayload[0]);
      }
   }
}
