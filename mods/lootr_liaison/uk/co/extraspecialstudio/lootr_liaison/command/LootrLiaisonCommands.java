package uk.co.extraspecialstudio.lootr_liaison.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import uk.co.extraspecialstudio.lootr_liaison.LiaisonNbt;

public final class LootrLiaisonCommands {
   public static void register(CommandDispatcher<CommandSourceStack> d) {
      d.register(root("lootr_liaison"));
      d.register(root("lootr_liason"));
   }

   private static LiteralArgumentBuilder<CommandSourceStack> root(String name) {
      return (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)Commands.literal(name).requires(s -> s.hasPermission(2)))
         .then(
            ((LiteralArgumentBuilder)Commands.literal("mimics").then(Commands.literal("stats").executes(c -> mimicsStats((CommandSourceStack)c.getSource()))))
               .then(
                  Commands.literal("reset")
                     .then(
                        ((LiteralArgumentBuilder)Commands.literal("here").executes(ctx -> mimicsReset((CommandSourceStack)ctx.getSource(), 8)))
                           .then(
                              Commands.argument("radius", IntegerArgumentType.integer(1, 64))
                                 .executes(ctx -> mimicsReset((CommandSourceStack)ctx.getSource(), IntegerArgumentType.getInteger(ctx, "radius")))
                           )
                     )
               )
         );
   }

   private static int mimicsStats(CommandSourceStack src) {
      Level level = src.getLevel();
      BlockPos center = src.getPlayer() != null ? src.getPlayer().blockPosition() : BlockPos.ZERO;
      int chunkR = 4;
      int checked = 0;
      int armed = 0;
      int spawned = 0;
      int cx0 = (center.getX() >> 4) - chunkR;
      int cx1 = (center.getX() >> 4) + chunkR;
      int cz0 = (center.getZ() >> 4) - chunkR;
      int cz1 = (center.getZ() >> 4) + chunkR;

      for (int cx = cx0; cx <= cx1; cx++) {
         for (int cz = cz0; cz <= cz1; cz++) {
            if (level.hasChunk(cx, cz)) {
               LevelChunk chunk = level.getChunk(cx, cz);
               if (chunk instanceof LevelChunk) {
                  for (BlockEntity be : chunk.getBlockEntities().values()) {
                     if (be instanceof RandomizableContainerBlockEntity) {
                        CompoundTag tag = be.getPersistentData();
                        if (LiaisonNbt.isMimicChecked(tag)) {
                           checked++;
                           String res = LiaisonNbt.mimicResult(tag);
                           if ("armed".equals(res)) {
                              armed++;
                           } else if ("spawned".equals(res)) {
                              spawned++;
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      String msg = "Mimics: checked=" + checked + " armed=" + armed + " spawned=" + spawned;
      src.sendSuccess(() -> Component.literal(msg), false);
      return 1;
   }

   private static int mimicsReset(CommandSourceStack src, int radius) {
      if (src.getPlayer() == null) {
         src.sendFailure(Component.literal("Can only run as a player"));
         return 0;
      }

      Level level = src.getLevel();
      BlockPos center = src.getPlayer().blockPosition();
      int cleared = 0;

      for (int x = -radius; x <= radius; x++) {
         for (int y = -radius; y <= radius; y++) {
            for (int z = -radius; z <= radius; z++) {
               BlockPos pos = center.offset(x, y, z);
               BlockEntity be = level.getBlockEntity(pos);
               if (be instanceof RandomizableContainerBlockEntity) {
                  CompoundTag tag = be.getPersistentData();
                  if (LiaisonNbt.isMimicChecked(tag)) {
                     LiaisonNbt.clearMimicStamps(tag);
                     be.setChanged();
                     cleared++;
                  }
               }
            }
         }
      }

      String msg = "Cleared mimic stamps: " + cleared + " (radius " + radius + ")";
      src.sendSuccess(() -> Component.literal(msg), false);
      return 1;
   }
}
