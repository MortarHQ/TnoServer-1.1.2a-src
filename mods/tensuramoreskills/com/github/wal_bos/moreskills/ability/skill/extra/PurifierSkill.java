package com.github.wal_bos.moreskills.ability.skill.extra;

import com.github.wal_bos.moreskills.configs.MoreskillsRaceConfig;
import com.github.wal_bos.moreskills.handler.BlackBloodConquestData;
import com.github.wal_bos.moreskills.handler.BlackBloodDomainData;
import com.github.wal_bos.moreskills.registry.block.ModBlocks;
import com.github.wal_bos.moreskills.util.SkillObtainmentHelper;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult.Type;
import org.jetbrains.annotations.NotNull;

public class PurifierSkill extends Skill {
   private static final double ACQUIREMENT_COST = 1000000.0;
   private static final double RANGE = 32.0;
   private static final int AREA_RADIUS = 7;
   private static final int AREA_COOLDOWN = 20;
   private static final Field CONQUEST_MEMORY = findConquestMemoryField();

   public PurifierSkill() {
      super(SkillType.EXTRA);
   }

   public double getDefaultAcquiringMagiculeCost() {
      return 1000000.0;
   }

   public int getAcquirementMastery(LivingEntity entity) {
      return 0;
   }

   public boolean checkAcquiringRequirement(Player entity, double newEP) {
      return (Boolean)MoreskillsRaceConfig.get().BlackCommunion.blockInfectionEnabled.get() && SkillObtainmentHelper.canAcquire(entity, 1000000.0, "", false);
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return 0.0;
   }

   public int getMaxMastery() {
      return 1000;
   }

   public int getModes(ManasSkillInstance instance) {
      return 2;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      return mode == 0 ? 1 : 0;
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return mode == 0 ? "purifier.single" : "purifier.area";
   }

   @NotNull
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/skill/unique/parasite.png");
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (!entity.level().isClientSide() && entity.level() instanceof ServerLevel level) {
         if (!(Boolean)MoreskillsRaceConfig.get().BlackCommunion.blockInfectionEnabled.get()) {
            fail(entity, "Block infection is disabled.");
         } else {
            BlockHitResult hit = ObjectSelectionHelper.getPlayerPOVHitResult(level, entity, Fluid.NONE, 32.0);
            if (hit.getType() == Type.MISS) {
               fail(entity, "No block in purification range.");
            } else {
               BlockPos target = hit.getBlockPos();
               PurifierSkill.PurificationResult result = mode == 0 ? purifySingle(level, target) : purifyArea(level, target);
               if (result.blocks() <= 0 && result.hidden() <= 0) {
                  fail(entity, "No Black Blood infection was found.");
               } else {
                  instance.addMasteryPoint(entity);
                  if (mode != 0) {
                     instance.setCoolDown(20, mode);
                  }

                  entity.swing(InteractionHand.MAIN_HAND, true);
                  level.playSound(null, target, SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, mode == 0 ? 0.7F : 1.15F, mode == 0 ? 1.35F : 0.9F);
                  level.sendParticles(
                     ParticleTypes.END_ROD,
                     target.getX() + 0.5,
                     target.getY() + 0.5,
                     target.getZ() + 0.5,
                     mode == 0 ? 10 : 90,
                     mode == 0 ? 0.25 : 4.5,
                     mode == 0 ? 0.25 : 4.5,
                     mode == 0 ? 0.25 : 4.5,
                     0.025
                  );
                  if (entity instanceof Player player) {
                     player.displayClientMessage(
                        Component.literal("Purified " + result.blocks() + " infection block(s) and cleared " + result.hidden() + " nodes.")
                           .withStyle(ChatFormatting.AQUA),
                        true
                     );
                  }
               }
            }
         }
      }
   }

   private static PurifierSkill.PurificationResult purifySingle(ServerLevel level, BlockPos pos) {
      if (!level.isLoaded(pos)) {
         return PurifierSkill.PurificationResult.EMPTY;
      }

      BlackBloodDomainData domain = BlackBloodDomainData.get(level);
      BlackBloodConquestData conquest = BlackBloodConquestData.get(level);
      int hidden = 0;
      if (domain.removeNode(pos)) {
         hidden++;
      }

      if (conquest.removeRoot(pos)) {
         hidden++;
      }

      if (conquest.removeBloom(pos)) {
         hidden++;
      }

      boolean changed = purifyBlock(level, pos);
      if (changed) {
         domain.subtractChunkValue(pos, Math.max(1, (Integer)MoreskillsRaceConfig.get().BlackCommunion.domainChunkValuePerBlock.get()));
      }

      return new PurifierSkill.PurificationResult(changed ? 1 : 0, hidden);
   }

   private static PurifierSkill.PurificationResult purifyArea(ServerLevel level, BlockPos center) {
      BlackBloodDomainData domain = BlackBloodDomainData.get(level);
      BlackBloodConquestData conquest = BlackBloodConquestData.get(level);
      Set<Long> chunks = new HashSet<>();
      int blocks = 0;
      int hidden = 0;

      for (int x = -7; x <= 7; x++) {
         for (int y = -7; y <= 7; y++) {
            for (int z = -7; z <= 7; z++) {
               BlockPos pos = center.offset(x, y, z);
               if (level.isLoaded(pos)) {
                  chunks.add(ChunkPos.asLong(pos));
                  if (domain.removeNode(pos)) {
                     hidden++;
                  }

                  if (conquest.removeRoot(pos)) {
                     hidden++;
                  }

                  if (conquest.removeBloom(pos)) {
                     hidden++;
                  }

                  if (purifyBlock(level, pos)) {
                     blocks++;
                  }
               }
            }
         }
      }

      List<BlockPos> rootsToRemove = new ArrayList<>();

      for (Entry<BlockPos, BlackBloodConquestData.RootData> entry : conquest.roots()) {
         if (chunks.contains(entry.getValue().targetChunk())) {
            rootsToRemove.add(entry.getKey());
         }
      }

      for (BlockPos root : rootsToRemove) {
         if (conquest.removeRoot(root)) {
            hidden++;
         }
      }

      List<Long> pendingToRemove = new ArrayList<>();

      for (Entry<Long, BlackBloodConquestData.PendingRootLaunch> entry : conquest.pendingRootLaunches()) {
         if (chunks.contains(entry.getKey())) {
            pendingToRemove.add(entry.getKey());
         }
      }

      for (long targetChunk : pendingToRemove) {
         if (conquest.removePendingRootLaunch(targetChunk)) {
            hidden++;
         }
      }

      for (long chunk : chunks) {
         domain.subtractChunkValue(new BlockPos(ChunkPos.getX(chunk) << 4, center.getY(), ChunkPos.getZ(chunk) << 4), Integer.MAX_VALUE);
      }

      hidden += clearConquestPressure(conquest, chunks);
      return new PurifierSkill.PurificationResult(blocks, hidden);
   }

   private static boolean purifyBlock(ServerLevel level, BlockPos pos) {
      BlockState state = level.getBlockState(pos);
      BlockState replacement = getPurifiedState(level, pos, state);
      return replacement != null && !replacement.equals(state) ? level.setBlock(pos, replacement, 3) : false;
   }

   private static BlockState getPurifiedState(ServerLevel level, BlockPos pos, BlockState state) {
      if (state.is((Block)ModBlocks.INFECTED_GRASS.get())) {
         return Blocks.GRASS_BLOCK.defaultBlockState();
      } else if (state.is((Block)ModBlocks.INFECTED_DIRT.get())) {
         return Blocks.DIRT.defaultBlockState();
      } else if (state.is((Block)ModBlocks.INFECTED_WOOD.get())) {
         return Blocks.OAK_LOG.defaultBlockState();
      } else if (state.is((Block)ModBlocks.INFECTED_LEAVES.get())) {
         return Blocks.OAK_LEAVES.defaultBlockState();
      } else if (state.is((Block)ModBlocks.BLACK_BLOOD_MIRE.get())) {
         return inferMireOriginal(level, pos);
      } else {
         return !state.is((Block)ModBlocks.BLOOD_FLOWER_ROOT.get())
               && !state.is((Block)ModBlocks.BLOOD_FLOWER_PETAL.get())
               && !state.is((Block)ModBlocks.BLOOD_FLOWER_CORE.get())
            ? null
            : Blocks.AIR.defaultBlockState();
      }
   }

   private static BlockState inferMireOriginal(ServerLevel level, BlockPos pos) {
      Map<Block, Integer> candidates = new HashMap<>();

      for (Direction direction : Direction.values()) {
         BlockState nearby = level.getBlockState(pos.relative(direction));
         Block block = naturalCandidate(nearby);
         if (block != null) {
            candidates.put(block, candidates.getOrDefault(block, 0) + 1);
         }
      }

      Block best = null;
      int score = 0;

      for (Entry<Block, Integer> entry : candidates.entrySet()) {
         if (entry.getValue() > score) {
            score = entry.getValue();
            best = entry.getKey();
         }
      }

      if (best != null) {
         return best.defaultBlockState();
      } else if (level.dimensionType().ultraWarm()) {
         return Blocks.NETHERRACK.defaultBlockState();
      } else {
         return pos.getY() < 0 ? Blocks.DEEPSLATE.defaultBlockState() : Blocks.STONE.defaultBlockState();
      }
   }

   private static Block naturalCandidate(BlockState state) {
      if (state.is(BlockTags.BASE_STONE_OVERWORLD)
         || state.is(BlockTags.BASE_STONE_NETHER)
         || state.is(BlockTags.DIRT)
         || state.is(BlockTags.SAND)
         || state.is(Blocks.GRAVEL)
         || state.is(Blocks.CLAY)
         || state.is(Blocks.MUD)
         || state.is(Blocks.MOSS_BLOCK)
         || state.is(Blocks.NETHERRACK)
         || state.is(Blocks.SOUL_SAND)
         || state.is(Blocks.SOUL_SOIL)) {
         return state.getBlock();
      } else if (state.is((Block)ModBlocks.INFECTED_GRASS.get())) {
         return Blocks.GRASS_BLOCK;
      } else {
         return state.is((Block)ModBlocks.INFECTED_DIRT.get()) ? Blocks.DIRT : null;
      }
   }

   private static Field findConquestMemoryField() {
      try {
         Field field = BlackBloodConquestData.class.getDeclaredField("chunkMemory");
         field.setAccessible(true);
         return field;
      } catch (ReflectiveOperationException ignored) {
         return null;
      }
   }

   private static int clearConquestPressure(BlackBloodConquestData conquest, Set<Long> chunks) {
      if (CONQUEST_MEMORY != null && !chunks.isEmpty()) {
         try {
            Map<Long, BlackBloodConquestData.ChunkMemory> memory = (Map<Long, BlackBloodConquestData.ChunkMemory>)CONQUEST_MEMORY.get(conquest);
            int cleared = 0;

            for (long chunk : chunks) {
               BlackBloodConquestData.ChunkMemory old = memory.get(chunk);
               if (old != null && old.infectionPressure() > 0) {
                  memory.put(chunk, new BlackBloodConquestData.ChunkMemory(old.playerHeat(), old.baseHeat(), 0, old.lastSeen(), old.breaches()));
                  cleared++;
               }
            }

            if (cleared > 0) {
               conquest.setDirty();
            }

            return cleared;
         } catch (IllegalAccessException ignored) {
            return 0;
         }
      } else {
         return 0;
      }
   }

   private static void fail(LivingEntity entity, String message) {
      if (entity instanceof Player player) {
         player.displayClientMessage(Component.literal(message).withStyle(ChatFormatting.RED), true);
      }

      entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.BEACON_DEACTIVATE, SoundSource.PLAYERS, 0.8F, 0.8F);
   }

   private record PurificationResult(int blocks, int hidden) {
      private static final PurifierSkill.PurificationResult EMPTY = new PurifierSkill.PurificationResult(0, 0);
   }
}
