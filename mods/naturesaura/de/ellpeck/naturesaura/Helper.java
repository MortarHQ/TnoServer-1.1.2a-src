package de.ellpeck.naturesaura;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.ellpeck.naturesaura.api.aura.container.IAuraContainer;
import de.ellpeck.naturesaura.api.aura.item.IAuraRecharge;
import de.ellpeck.naturesaura.api.misc.ILevelData;
import de.ellpeck.naturesaura.blocks.tiles.BlockEntityImpl;
import de.ellpeck.naturesaura.chunk.AuraChunk;
import de.ellpeck.naturesaura.compat.Compat;
import de.ellpeck.naturesaura.misc.LevelData;
import de.ellpeck.naturesaura.packet.PacketHandler;
import de.ellpeck.naturesaura.packet.PacketParticles;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.capabilities.Capabilities.ItemHandler;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

public final class Helper {
   public static boolean getBlockEntitiesInArea(LevelAccessor level, BlockPos pos, int radius, Function<BlockEntity, Boolean> consumer) {
      for (int x = pos.getX() - radius >> 4; x <= pos.getX() + radius >> 4; x++) {
         for (int z = pos.getZ() - radius >> 4; z <= pos.getZ() + radius >> 4; z++) {
            ChunkAccess chunk = getLoadedChunk(level, x, z);
            if (chunk != null) {
               for (BlockPos tilePos : chunk.getBlockEntitiesPos()) {
                  if (tilePos.distSqr(pos) <= radius * radius) {
                     BlockEntity tile = chunk.getBlockEntity(tilePos);
                     if (tile != null && consumer.apply(tile)) {
                        return true;
                     }
                  }
               }
            }
         }
      }

      return false;
   }

   public static void getAuraChunksWithSpotsInArea(Level level, BlockPos pos, int radius, Consumer<AuraChunk> consumer) {
      LevelData data = (LevelData)ILevelData.getLevelData(level);

      for (int x = pos.getX() - radius >> 4; x <= pos.getX() + radius >> 4; x++) {
         for (int z = pos.getZ() - radius >> 4; z <= pos.getZ() + radius >> 4; z++) {
            AuraChunk chunk = (AuraChunk)data.auraChunksWithSpots.get(ChunkPos.asLong(x, z));
            if (chunk != null) {
               consumer.accept(chunk);
            }
         }
      }
   }

   public static List<ItemFrame> getAttachedItemFrames(Level level, BlockPos pos) {
      List<ItemFrame> frames = level.getEntitiesOfClass(ItemFrame.class, new AABB(pos).inflate(0.25));

      for (int i = frames.size() - 1; i >= 0; i--) {
         ItemFrame frame = frames.get(i);
         BlockPos framePos = frame.getPos().relative(frame.getDirection().getOpposite());
         if (!pos.equals(framePos)) {
            frames.remove(i);
         }
      }

      return frames;
   }

   public static ChunkAccess getLoadedChunk(LevelAccessor level, int x, int z) {
      if (level.getChunkSource() instanceof ServerChunkCache cache) {
         return cache.isPositionTicking(ChunkPos.asLong(x, z)) ? cache.getChunk(x, z, ChunkStatus.FULL, false) : null;
      } else {
         return level.getChunk(x, z, ChunkStatus.FULL, false);
      }
   }

   public static int blendColors(int c1, int c2, float ratio) {
      int a = (int)((c1 >> 24 & 0xFF) * ratio + (c2 >> 24 & 0xFF) * (1.0F - ratio));
      int r = (int)((c1 >> 16 & 0xFF) * ratio + (c2 >> 16 & 0xFF) * (1.0F - ratio));
      int g = (int)((c1 >> 8 & 0xFF) * ratio + (c2 >> 8 & 0xFF) * (1.0F - ratio));
      int b = (int)((c1 & 0xFF) * ratio + (c2 & 0xFF) * (1.0F - ratio));
      return (a & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF;
   }

   public static boolean areItemsEqual(ItemStack first, ItemStack second, boolean nbt) {
      return nbt ? ItemStack.isSameItemSameComponents(first, second) : ItemStack.isSameItem(first, second);
   }

   @OnlyIn(Dist.CLIENT)
   public static void renderItemInGui(GuiGraphics graphics, ItemStack stack, int x, int y, float scale) {
      PoseStack poseStack = graphics.pose();
      poseStack.pushPose();
      poseStack.translate(x, y, 0.0F);
      poseStack.scale(scale, scale, scale);
      graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
      graphics.renderItem(stack, 0, 0);
      graphics.renderItemDecorations(Minecraft.getInstance().font, stack, 0, 0, null);
      poseStack.popPose();
   }

   @OnlyIn(Dist.CLIENT)
   public static void renderWeirdBox(
      VertexConsumer buffer, float x, float y, float z, float width, float height, float depth, float r, float g, float b, float a
   ) {
      buffer.addVertex(x, y + height, z).setColor(r, g, b, a);
      buffer.addVertex(x + width, y + height, z).setColor(r, g, b, a);
      buffer.addVertex(x + width, y, z).setColor(r, g, b, a);
      buffer.addVertex(x, y, z).setColor(r, g, b, a);
      buffer.addVertex(x + width, y, z + depth).setColor(r, g, b, a);
      buffer.addVertex(x + width, y, z).setColor(r, g, b, a);
      buffer.addVertex(x + width, y + height, z).setColor(r, g, b, a);
      buffer.addVertex(x + width, y + height, z + depth).setColor(r, g, b, a);
      buffer.addVertex(x + width, y + height, z + depth).setColor(r, g, b, a);
      buffer.addVertex(x, y + height, z + depth).setColor(r, g, b, a);
      buffer.addVertex(x, y, z + depth).setColor(r, g, b, a);
      buffer.addVertex(x + width, y, z + depth).setColor(r, g, b, a);
      buffer.addVertex(x, y + height, z + depth).setColor(r, g, b, a);
      buffer.addVertex(x, y + height, z).setColor(r, g, b, a);
      buffer.addVertex(x, y, z).setColor(r, g, b, a);
      buffer.addVertex(x, y, z + depth).setColor(r, g, b, a);
      buffer.addVertex(x, y + height, z).setColor(r, g, b, a);
      buffer.addVertex(x, y + height, z + depth).setColor(r, g, b, a);
      buffer.addVertex(x + width, y + height, z + depth).setColor(r, g, b, a);
      buffer.addVertex(x + width, y + height, z).setColor(r, g, b, a);
      buffer.addVertex(x + width, y, z).setColor(r, g, b, a);
      buffer.addVertex(x + width, y, z + depth).setColor(r, g, b, a);
      buffer.addVertex(x, y, z + depth).setColor(r, g, b, a);
      buffer.addVertex(x, y, z).setColor(r, g, b, a);
   }

   public static ItemInteractionResult putStackOnTile(Player player, InteractionHand hand, BlockPos pos, int slot, boolean sound) {
      BlockEntity tile = player.level().getBlockEntity(pos);
      if (tile instanceof BlockEntityImpl) {
         IItemHandlerModifiable handler = (IItemHandlerModifiable)tile.getLevel()
            .getCapability(ItemHandler.BLOCK, tile.getBlockPos(), tile.getBlockState(), tile, null);
         if (handler != null) {
            ItemStack handStack = player.getItemInHand(hand);
            if (!handStack.isEmpty()) {
               ItemStack remain = handler.insertItem(slot, handStack, player.level().isClientSide);
               if (!ItemStack.matches(remain, handStack)) {
                  if (sound) {
                     player.level()
                        .playSound(
                           player, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.PLAYERS, 0.75F, 1.0F
                        );
                  }

                  if (!player.level().isClientSide) {
                     player.setItemInHand(hand, remain);
                  }

                  return ItemInteractionResult.SUCCESS;
               }
            }

            if (!handler.getStackInSlot(slot).isEmpty()) {
               if (sound) {
                  player.level()
                     .playSound(
                        player, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.PLAYERS, 0.75F, 1.0F
                     );
               }

               if (!player.level().isClientSide) {
                  ItemStack stack = handler.getStackInSlot(slot);
                  if (!player.addItem(stack)) {
                     ItemEntity item = new ItemEntity(player.level(), player.getX(), player.getY(), player.getZ(), stack);
                     player.level().addFreshEntity(item);
                  }

                  handler.setStackInSlot(slot, ItemStack.EMPTY);
               }

               return ItemInteractionResult.SUCCESS;
            }
         }
      }

      return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
   }

   public static ICapabilityProvider<ItemStack, Void, IAuraRecharge> makeRechargeProvider(boolean needsSelected) {
      return (stack, ctx) -> (container, containerSlot, itemSlot, isSelected) -> !isSelected && needsSelected ? false : rechargeAuraItem(stack, container, 300);
   }

   public static boolean rechargeAuraItem(ItemStack stack, IAuraContainer container, int toDrain) {
      if (stack.getDamageValue() > 0 && container.drainAura(toDrain, true) >= toDrain) {
         stack.setDamageValue(stack.getDamageValue() - 1);
         container.drainAura(toDrain, false);
         return true;
      } else {
         return false;
      }
   }

   public static BlockState getStateFromString(String raw) {
      String[] split = raw.split("\\[");
      Block block = (Block)BuiltInRegistries.BLOCK.get(ResourceLocation.parse(split[0]));
      if (block == null) {
         return null;
      }

      BlockState state = block.defaultBlockState();
      if (split.length > 1) {
         for (String part : split[1].replace("]", "").split(",")) {
            String[] keyValue = part.split("=");

            for (Property<?> prop : state.getProperties()) {
               BlockState changed = findProperty(state, prop, keyValue[0], keyValue[1]);
               if (changed != null) {
                  state = changed;
                  break;
               }
            }
         }
      }

      return state;
   }

   private static <T extends Comparable<T>> BlockState findProperty(BlockState state, Property<T> prop, String key, String newValue) {
      if (key.equals(prop.getName())) {
         for (T value : prop.getPossibleValues()) {
            if (prop.getName(value).equals(newValue)) {
               return (BlockState)state.setValue(prop, value);
            }
         }
      }

      return null;
   }

   public static void addAdvancement(Player player, ResourceLocation advancement, String criterion) {
      if (player instanceof ServerPlayer playerMp) {
         AdvancementHolder adv = playerMp.level().getServer().getAdvancements().get(advancement);
         if (adv != null) {
            playerMp.getAdvancements().award(adv, criterion);
         }
      }
   }

   public static int getIngredientAmount(Ingredient ingredient) {
      int highestAmount = 0;

      for (ItemStack stack : ingredient.getItems()) {
         if (stack.getCount() > highestAmount) {
            highestAmount = stack.getCount();
         }
      }

      return highestAmount;
   }

   public static boolean isHoldingItem(Player player, Item item) {
      for (InteractionHand hand : InteractionHand.values()) {
         ItemStack stack = player.getItemInHand(hand);
         if (!stack.isEmpty() && stack.getItem() == item) {
            return true;
         }
      }

      return false;
   }

   public static boolean isEmpty(IItemHandler handler) {
      for (int i = 0; i < handler.getSlots(); i++) {
         if (!handler.getStackInSlot(i).isEmpty()) {
            return false;
         }
      }

      return true;
   }

   public static AABB aabb(Vec3 pos) {
      return new AABB(pos.x, pos.y, pos.z, pos.x, pos.y, pos.z);
   }

   public static <T> void populateObjectHolders(Class<?> clazz, Registry<T> registry, boolean useHolders) {
      for (Field entry : clazz.getFields()) {
         if (Modifier.isStatic(entry.getModifiers())) {
            ResourceLocation location = ResourceLocation.fromNamespaceAndPath("naturesaura", entry.getName().toLowerCase(Locale.ROOT));
            if (!registry.containsKey(location)) {
               NaturesAura.LOGGER.fatal("Couldn't find entry named " + location + " in registry");
            } else {
               try {
                  entry.set(null, useHolders ? registry.getHolder(location).orElseThrow() : registry.get(location));
               } catch (IllegalAccessException e) {
                  NaturesAura.LOGGER.error(e);
               }
            }
         }
      }
   }

   public static ItemStack getEquippedItem(Predicate<ItemStack> predicate, Player player, boolean hotbarOnly) {
      if (Compat.hasCompat("curios")) {
         Optional<ICuriosItemHandler> inventory = CuriosApi.getCuriosInventory(player);
         if (inventory.isPresent()) {
            Optional<ItemStack> stack = inventory.get().findFirstCurio(predicate).map(SlotResult::stack);
            if (stack.isPresent()) {
               return stack.get();
            }
         }
      }

      int invSize = hotbarOnly ? 9 : player.getInventory().getContainerSize();

      for (int i = 0; i < invSize; i++) {
         ItemStack slot = player.getInventory().getItem(i);
         if (!slot.isEmpty() && predicate.test(slot)) {
            return slot;
         }
      }

      return ItemStack.EMPTY;
   }

   public static BlockPos getClosestAirAboveGround(Level level, BlockPos pos, int radius) {
      for (int i = 0; i < radius; i++) {
         BlockPos up = pos.above(i);
         if (level.isEmptyBlock(up) && !level.isEmptyBlock(up.below())) {
            return up;
         }

         BlockPos dn = pos.below(i);
         if (level.isEmptyBlock(dn) && !level.isEmptyBlock(dn.below())) {
            return dn;
         }
      }

      return pos;
   }

   public static void mineRecursively(
      Level level, BlockPos pos, BlockPos start, ItemStack tool, int horizontalRange, int verticalRange, Predicate<BlockState> filter
   ) {
      if (Math.abs(pos.getX() - start.getX()) < horizontalRange
         && Math.abs(pos.getZ() - start.getZ()) < horizontalRange
         && Math.abs(pos.getY() - start.getY()) < verticalRange) {
         if (!tool.isEmpty()) {
            BlockState state = level.getBlockState(pos);
            level.destroyBlock(pos, false);
            Block.dropResources(state, level, pos, state.hasBlockEntity() ? level.getBlockEntity(pos) : null, null, tool);
         } else {
            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            PacketHandler.sendToAllAround(level, pos, 32, new PacketParticles(pos.getX(), pos.getY(), pos.getZ(), PacketParticles.Type.TR_DISAPPEAR));
         }

         for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
               for (int z = -1; z <= 1; z++) {
                  if (x != 0 || y != 0 || z != 0) {
                     BlockPos offset = pos.offset(x, y, z);
                     BlockState state = level.getBlockState(offset);
                     if (filter.test(state)) {
                        mineRecursively(level, offset, start, tool, horizontalRange, verticalRange, filter);
                     }
                  }
               }
            }
         }
      }
   }

   public static boolean isToolEnabled(ItemStack stack) {
      return stack.has(Helper.DisableableToolData.TYPE) && !((Helper.DisableableToolData)stack.get(Helper.DisableableToolData.TYPE)).disabled;
   }

   public static boolean toggleToolEnabled(Player player, ItemStack stack) {
      if (!player.isShiftKeyDown()) {
         return false;
      }

      stack.set(Helper.DisableableToolData.TYPE, new Helper.DisableableToolData(isToolEnabled(stack)));
      player.level()
         .playSound(null, player.getX() + 0.5, player.getY() + 0.5, player.getZ() + 0.5, SoundEvents.ARROW_HIT_PLAYER, SoundSource.PLAYERS, 0.65F, 1.0F);
      return true;
   }

   public static BlockPos readBlockPos(Tag tag) {
      if (tag instanceof IntArrayTag i) {
         int[] arr = i.getAsIntArray();
         if (arr.length == 3) {
            return new BlockPos(arr[0], arr[1], arr[2]);
         }
      }

      return null;
   }

   public record DisableableToolData(boolean disabled) {
      public static final Codec<Helper.DisableableToolData> CODEC = RecordCodecBuilder.create(
         i -> i.group(Codec.BOOL.fieldOf("disabled").forGetter(d -> d.disabled)).apply(i, Helper.DisableableToolData::new)
      );
      public static final DataComponentType<Helper.DisableableToolData> TYPE = DataComponentType.builder().persistent(CODEC).cacheEncoding().build();
   }
}
