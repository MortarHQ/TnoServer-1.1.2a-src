package com.chronodawn.items;

import com.chronodawn.compat.CompatHandlers;
import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.registry.ModItems;
import com.chronodawn.registry.ModSounds;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;

public class TimeCompassItem extends Item {
   public static final String NBT_TARGET_STRUCTURE = "TargetStructure";
   public static final String NBT_TARGET_POS_X = "TargetX";
   public static final String NBT_TARGET_POS_Y = "TargetY";
   public static final String NBT_TARGET_POS_Z = "TargetZ";
   public static final String NBT_TARGET_DIMENSION = "TargetDimension";
   public static final String STRUCTURE_ANCIENT_RUINS = "ancient_ruins";
   public static final String STRUCTURE_DESERT_CLOCK_TOWER = "desert_clock_tower";
   public static final String STRUCTURE_MASTER_CLOCK = "master_clock";
   public static final String STRUCTURE_PHANTOM_CATACOMBS = "phantom_catacombs";
   public static final String STRUCTURE_GUARDIAN_VAULT = "guardian_vault";
   public static final String STRUCTURE_CLOCKWORK_DEPTHS = "clockwork_depths";
   public static final String STRUCTURE_ENTROPY_CRYPT = "entropy_crypt";

   public TimeCompassItem(Properties properties) {
      super(properties);
   }

   public static Properties createProperties() {
      return new Properties().stacksTo(1);
   }

   public static ItemStack createCompass(String structureType) {
      ItemStack stack = new ItemStack((ItemLike)ModItems.TIME_COMPASS.get());
      CompatHandlers.ITEM_DATA.setString(stack, "TargetStructure", structureType);
      return stack;
   }

   public static String getTargetStructure(ItemStack stack) {
      String target = CompatHandlers.ITEM_DATA.getString(stack, "TargetStructure");
      return target.isEmpty() ? null : target;
   }

   public static void setTargetPosition(ItemStack stack, GlobalPos globalPos) {
      CompatHandlers.ITEM_DATA.updateCustomData(stack, tag -> {
         tag.putInt("TargetX", globalPos.pos().getX());
         tag.putInt("TargetY", globalPos.pos().getY());
         tag.putInt("TargetZ", globalPos.pos().getZ());
         tag.putString("TargetDimension", globalPos.dimension().location().toString());
      });
   }

   public static Optional<GlobalPos> getTargetPosition(ItemStack stack) {
      CompoundTag tag = CompatHandlers.ITEM_DATA.getCustomData(stack);
      if (tag.contains("TargetX") && tag.contains("TargetDimension")) {
         int x = tag.getInt("TargetX");
         int y = tag.getInt("TargetY");
         int z = tag.getInt("TargetZ");
         String dimensionStr = tag.getString("TargetDimension");

         try {
            ResourceKey<Level> dimension = ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse(dimensionStr));
            return Optional.of(GlobalPos.of(dimension, new BlockPos(x, y, z)));
         } catch (Exception e) {
            return Optional.empty();
         }
      } else {
         return Optional.empty();
      }
   }

   private static String getStructureDisplayName(String structureType) {
      return switch (structureType) {
         case "ancient_ruins" -> "item.chronodawn.time_compass.target.ancient_ruins";
         case "desert_clock_tower" -> "item.chronodawn.time_compass.target.desert_clock_tower";
         case "master_clock" -> "item.chronodawn.time_compass.target.master_clock";
         case "phantom_catacombs" -> "item.chronodawn.time_compass.target.phantom_catacombs";
         case "guardian_vault" -> "item.chronodawn.time_compass.target.guardian_vault";
         case "clockwork_depths" -> "item.chronodawn.time_compass.target.clockwork_depths";
         case "entropy_crypt" -> "item.chronodawn.time_compass.target.entropy_crypt";
         default -> "item.chronodawn.time_compass.target.unknown";
      };
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
      String targetStructure = getTargetStructure(stack);
      if (targetStructure != null) {
         tooltip.add(Component.translatable("item.chronodawn.time_compass.tooltip.target").withStyle(ChatFormatting.GRAY));
         tooltip.add(Component.literal("  ").append(Component.translatable(getStructureDisplayName(targetStructure))).withStyle(ChatFormatting.AQUA));
         Optional<GlobalPos> targetPos = getTargetPosition(stack);
         if (targetPos.isPresent()) {
            BlockPos pos = targetPos.get().pos();
            tooltip.add(
               Component.literal("  ").append(Component.literal(String.format("(%d, %d)", pos.getX(), pos.getZ()))).withStyle(ChatFormatting.DARK_GRAY)
            );
         }
      } else {
         tooltip.add(
            Component.translatable("item.chronodawn.time_compass.tooltip.no_target").withStyle(new ChatFormatting[]{ChatFormatting.RED, ChatFormatting.ITALIC})
         );
      }
   }

   public boolean isFoil(ItemStack stack) {
      return true;
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
         String targetStructure = getTargetStructure(stack);
         if (targetStructure == null) {
            player.displayClientMessage(Component.translatable("item.chronodawn.time_compass.no_target").withStyle(ChatFormatting.RED), true);
            return InteractionResultHolder.fail(stack);
         }

         Optional<GlobalPos> existingPos = getTargetPosition(stack);
         if (existingPos.isPresent()) {
            BlockPos targetPos = existingPos.get().pos();
            BlockPos playerPos = player.blockPosition();
            int dx = targetPos.getX() - playerPos.getX();
            int dz = targetPos.getZ() - playerPos.getZ();
            double distance = Math.sqrt(dx * dx + dz * dz);
            String direction = getDirection(dx, dz);
            Component structureName = Component.translatable(getStructureDisplayName(targetStructure));
            player.displayClientMessage(
               Component.translatable(
                     "item.chronodawn.time_compass.current_location",
                     new Object[]{
                        structureName,
                        targetPos.getX(),
                        targetPos.getZ(),
                        playerPos.getX(),
                        playerPos.getZ(),
                        (int)distance,
                        Component.translatable("direction." + direction)
                     }
                  )
                  .withStyle(ChatFormatting.AQUA),
               false
            );
            level.playSound(null, player.blockPosition(), (SoundEvent)ModSounds.TIME_COMPASS_CHIME.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            return InteractionResultHolder.success(stack);
         }

         ServerLevel serverLevel = (ServerLevel)level;
         boolean success = locateAndSetStructure(serverLevel, stack, targetStructure, serverPlayer);
         if (success) {
            Optional<GlobalPos> newPos = getTargetPosition(stack);
            if (newPos.isPresent()) {
               BlockPos targetPos = newPos.get().pos();
               BlockPos playerPos = player.blockPosition();
               int dx = targetPos.getX() - playerPos.getX();
               int dz = targetPos.getZ() - playerPos.getZ();
               double distance = Math.sqrt(dx * dx + dz * dz);
               String direction = getDirection(dx, dz);
               Component structureName = Component.translatable(getStructureDisplayName(targetStructure));
               player.displayClientMessage(
                  Component.translatable(
                        "item.chronodawn.time_compass.located",
                        new Object[]{structureName, targetPos.getX(), targetPos.getZ(), (int)distance, Component.translatable("direction." + direction)}
                     )
                     .withStyle(ChatFormatting.GREEN),
                  false
               );
               level.playSound(null, player.blockPosition(), (SoundEvent)ModSounds.TIME_COMPASS_UPDATE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            }

            return InteractionResultHolder.success(stack);
         } else {
            Component structureName = Component.translatable(getStructureDisplayName(targetStructure));
            player.displayClientMessage(
               Component.translatable("item.chronodawn.time_compass.not_found", new Object[]{structureName}).withStyle(ChatFormatting.YELLOW), false
            );
            level.playSound(null, player.blockPosition(), (SoundEvent)ModSounds.TIME_COMPASS_BREAK.get(), SoundSource.PLAYERS, 1.0F, 0.5F);
            return InteractionResultHolder.fail(stack);
         }
      } else {
         return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
      }
   }

   private static boolean locateAndSetStructure(ServerLevel serverLevel, ItemStack stack, String structureType, ServerPlayer player) {
      ServerLevel searchLevel;
      ResourceLocation structureId;
      switch (structureType) {
         case "ancient_ruins":
            searchLevel = serverLevel.getServer().getLevel(Level.OVERWORLD);
            structureId = CompatResourceLocation.create("chronodawn", "ancient_ruins");
            break;
         case "desert_clock_tower":
            searchLevel = serverLevel.getServer().getLevel(ResourceKey.create(Registries.DIMENSION, CompatResourceLocation.create("chronodawn", "chronodawn")));
            structureId = CompatResourceLocation.create("chronodawn", "desert_clock_tower");
            break;
         case "master_clock":
            searchLevel = serverLevel.getServer().getLevel(ResourceKey.create(Registries.DIMENSION, CompatResourceLocation.create("chronodawn", "chronodawn")));
            structureId = CompatResourceLocation.create("chronodawn", "master_clock");
            break;
         case "phantom_catacombs":
            searchLevel = serverLevel.getServer().getLevel(ResourceKey.create(Registries.DIMENSION, CompatResourceLocation.create("chronodawn", "chronodawn")));
            structureId = CompatResourceLocation.create("chronodawn", "phantom_catacombs");
            break;
         case "guardian_vault":
            searchLevel = serverLevel.getServer().getLevel(ResourceKey.create(Registries.DIMENSION, CompatResourceLocation.create("chronodawn", "chronodawn")));
            structureId = CompatResourceLocation.create("chronodawn", "guardian_vault");
            break;
         case "clockwork_depths":
            searchLevel = serverLevel.getServer().getLevel(ResourceKey.create(Registries.DIMENSION, CompatResourceLocation.create("chronodawn", "chronodawn")));
            structureId = CompatResourceLocation.create("chronodawn", "clockwork_depths");
            break;
         case "entropy_crypt":
            searchLevel = serverLevel.getServer().getLevel(ResourceKey.create(Registries.DIMENSION, CompatResourceLocation.create("chronodawn", "chronodawn")));
            structureId = CompatResourceLocation.create("chronodawn", "entropy_crypt");
            break;
         default:
            return false;
      }

      if (searchLevel == null) {
         return false;
      }

      BlockPos searchOrigin = player.blockPosition();
      Registry<Structure> structureRegistry = searchLevel.registryAccess().registryOrThrow(Registries.STRUCTURE);
      Structure structureHolder = (Structure)structureRegistry.get(structureId);
      if (structureHolder != null) {
         HolderSet<Structure> structureSet = HolderSet.direct(new Holder[]{structureRegistry.wrapAsHolder(structureHolder)});
         Pair<BlockPos, Holder<Structure>> structurePair = searchLevel.getChunkSource()
            .getGenerator()
            .findNearestMapStructure(searchLevel, structureSet, searchOrigin, 100, false);
         if (structurePair != null) {
            BlockPos structurePos = (BlockPos)structurePair.getFirst();
            GlobalPos globalPos = GlobalPos.of(searchLevel.dimension(), structurePos);
            setTargetPosition(stack, globalPos);
            return true;
         }
      }

      return false;
   }

   private static String getDirection(int dx, int dz) {
      double angle = Math.toDegrees(Math.atan2(dz, dx));
      if (angle < 0.0) {
         angle += 360.0;
      }

      angle = (90.0 - angle + 360.0) % 360.0;
      if (angle >= 337.5 || angle < 22.5) {
         return "north";
      } else if (angle >= 22.5 && angle < 67.5) {
         return "northeast";
      } else if (angle >= 67.5 && angle < 112.5) {
         return "east";
      } else if (angle >= 112.5 && angle < 157.5) {
         return "southeast";
      } else if (angle >= 157.5 && angle < 202.5) {
         return "south";
      } else if (angle >= 202.5 && angle < 247.5) {
         return "southwest";
      } else {
         return angle >= 247.5 && angle < 292.5 ? "west" : "northwest";
      }
   }
}
