package de.ellpeck.naturesaura.api;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import de.ellpeck.naturesaura.api.aura.chunk.IDrainSpotEffect;
import de.ellpeck.naturesaura.api.aura.container.IAuraContainer;
import de.ellpeck.naturesaura.api.aura.item.IAuraRecharge;
import de.ellpeck.naturesaura.api.aura.type.BasicAuraType;
import de.ellpeck.naturesaura.api.aura.type.IAuraType;
import de.ellpeck.naturesaura.api.misc.ILevelData;
import de.ellpeck.naturesaura.api.misc.WeatherType;
import de.ellpeck.naturesaura.api.misc.WeightedOre;
import de.ellpeck.naturesaura.api.multiblock.IMultiblock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;
import org.apache.commons.lang3.tuple.Pair;

public final class NaturesAuraAPI {
   public static final String MOD_ID = "naturesaura";
   public static final BiMap<BlockState, BlockState> BOTANIST_PICKAXE_CONVERSIONS = HashBiMap.create();
   public static final Map<ResourceLocation, IAuraType> AURA_TYPES = new HashMap<>();
   public static final BasicAuraType TYPE_OVERWORLD = new BasicAuraType(
         ResourceLocation.fromNamespaceAndPath("naturesaura", "overworld"), Level.OVERWORLD, 9030711, 0
      )
      .register();
   public static final BasicAuraType TYPE_NETHER = new BasicAuraType(ResourceLocation.fromNamespaceAndPath("naturesaura", "nether"), Level.NETHER, 8854540, 0)
      .register();
   public static final BasicAuraType TYPE_END = new BasicAuraType(ResourceLocation.fromNamespaceAndPath("naturesaura", "end"), Level.END, 3155492, 0)
      .register();
   public static final BasicAuraType TYPE_OTHER = new BasicAuraType(
         ResourceLocation.fromNamespaceAndPath("naturesaura", "other"), null, 3123360, Integer.MIN_VALUE
      )
      .register();
   public static final Map<ResourceLocation, Supplier<IDrainSpotEffect>> DRAIN_SPOT_EFFECTS = new HashMap<>();
   public static final Map<ResourceLocation, Integer> EFFECT_POWDERS = new HashMap<>();
   public static final Map<ResourceLocation, IMultiblock> MULTIBLOCKS = new HashMap<>();
   public static final List<WeightedOre> OVERWORLD_ORES = new ArrayList<>();
   public static final List<WeightedOre> NETHER_ORES = new ArrayList<>();
   public static final Map<EntityType<?>, Integer> PROJECTILE_GENERATIONS = new HashMap<>();
   public static final Map<ItemStack, WeatherType> WEATHER_CHANGER_CONVERSIONS = new HashMap<>();
   public static final ItemCapability<IAuraContainer, Void> AURA_CONTAINER_ITEM_CAPABILITY = ItemCapability.createVoid(
      ResourceLocation.fromNamespaceAndPath("naturesaura", "aura_container_item"), IAuraContainer.class
   );
   public static final BlockCapability<IAuraContainer, Direction> AURA_CONTAINER_BLOCK_CAPABILITY = BlockCapability.create(
      ResourceLocation.fromNamespaceAndPath("naturesaura", "aura_container_block"), IAuraContainer.class, Direction.class
   );
   public static final ItemCapability<IAuraRecharge, Void> AURA_RECHARGE_CAPABILITY = ItemCapability.createVoid(
      ResourceLocation.fromNamespaceAndPath("naturesaura", "aura_recharge"), IAuraRecharge.class
   );
   public static final AttachmentType<IAuraChunk> AURA_CHUNK_ATTACHMENT = AttachmentType.serializable(h -> instance().createAuraChunk((LevelChunk)h)).build();
   private static final NaturesAuraAPI.IInternalHooks INSTANCE;

   public static NaturesAuraAPI.IInternalHooks instance() {
      return INSTANCE;
   }

   static {
      try {
         INSTANCE = (NaturesAuraAPI.IInternalHooks)Class.forName("de.ellpeck.naturesaura.InternalHooks").getConstructor().newInstance();
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
   }

   public interface IInternalHooks {
      boolean extractAuraFromPlayer(Player var1, int var2, boolean var3);

      boolean insertAuraIntoPlayer(Player var1, int var2, boolean var3);

      void spawnMagicParticle(
         double var1,
         double var3,
         double var5,
         double var7,
         double var9,
         double var11,
         int var13,
         float var14,
         int var15,
         float var16,
         boolean var17,
         boolean var18
      );

      void spawnParticleStream(float var1, float var2, float var3, float var4, float var5, float var6, float var7, int var8, float var9);

      void setParticleDepth(boolean var1);

      void setParticleSpawnRange(int var1);

      void setParticleCulling(boolean var1);

      IMultiblock createMultiblock(ResourceLocation var1, String[][] var2, Object... var3);

      List<Tuple<Vec3, Integer>> getActiveEffectPowders(Level var1, AABB var2, ResourceLocation var3);

      boolean isEffectPowderActive(Level var1, BlockPos var2, ResourceLocation var3);

      void getAuraSpotsInArea(Level var1, BlockPos var2, int var3, BiConsumer<BlockPos, Integer> var4);

      int getSpotAmountInArea(Level var1, BlockPos var2, int var3);

      int getAuraInArea(Level var1, BlockPos var2, int var3);

      Pair<Integer, Integer> getAuraAndSpotAmountInArea(Level var1, BlockPos var2, int var3);

      int triangulateAuraInArea(Level var1, BlockPos var2, int var3);

      BlockPos getLowestAuraDrainSpot(Level var1, BlockPos var2, int var3, BlockPos var4);

      BlockPos getHighestAuraDrainSpot(Level var1, BlockPos var2, int var3, BlockPos var4);

      ILevelData getLevelData(Level var1);

      IAuraChunk createAuraChunk(LevelChunk var1);
   }
}
