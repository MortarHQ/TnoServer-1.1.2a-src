package gardensofthedead.registry;

import com.mojang.serialization.MapCodec;
import gardensofthedead.platform.PlatformServices;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class ModBlockProperties {
   private static final int GLOWING_SOUL_SPORE_LIGHT = 7;
   private static final int BLISTERCROWN_LIGHT = 5;
   public static Properties SOUL_SPORE = Properties.of()
      .mapColor(MapColor.COLOR_BROWN)
      .sound(SoundType.WEEPING_VINES)
      .noCollission()
      .instabreak()
      .pushReaction(PushReaction.DESTROY);
   public static Properties GLOWING_SOUL_SPORE = copy(SOUL_SPORE).lightLevel(state -> 7);
   public static Properties SOULBLIGHT_FUNGUS = Properties.of()
      .mapColor(MapColor.COLOR_BROWN)
      .instabreak()
      .noCollission()
      .sound(SoundType.FUNGUS)
      .pushReaction(PushReaction.DESTROY);
   public static Properties SOULBLIGHT_SPROUTS = Properties.of()
      .mapColor(MapColor.COLOR_BROWN)
      .replaceable()
      .noCollission()
      .instabreak()
      .sound(SoundType.NETHER_SPROUTS)
      .offsetType(OffsetType.XZ)
      .pushReaction(PushReaction.DESTROY);
   public static Properties BLISTERCROWN = Properties.of()
      .mapColor(MapColor.COLOR_RED)
      .replaceable()
      .noCollission()
      .instabreak()
      .sound(SoundType.NETHER_SPROUTS)
      .offsetType(OffsetType.XZ)
      .lightLevel(state -> 5)
      .pushReaction(PushReaction.DESTROY);
   public static Properties WHISTLECANE = Properties.of()
      .mapColor(MapColor.CRIMSON_NYLIUM)
      .forceSolidOn()
      .randomTicks()
      .instabreak()
      .strength(1.0F)
      .sound(ModSoundTypes.WHISTLECANE)
      .noOcclusion()
      .dynamicShape()
      .offsetType(OffsetType.XZ)
      .pushReaction(PushReaction.DESTROY)
      .isRedstoneConductor((blockState, level, pos) -> false);
   public static Properties SOULBLIGHT_STEM = Properties.of()
      .mapColor(MapColor.COLOR_BROWN)
      .instrument(NoteBlockInstrument.BASS)
      .strength(2.0F)
      .sound(SoundType.STEM);
   public static Properties BLIGHTWART_BLOCK = Properties.of().mapColor(MapColor.COLOR_YELLOW).strength(1.0F).sound(SoundType.WART_BLOCK);
   public static Properties POTTED_GLOWING_SOUL_SPORE = pottedPlant().lightLevel(state -> 7);
   public static Properties SOULBLIGHT_PLANKS = Properties.of()
      .mapColor(MapColor.COLOR_BROWN)
      .instrument(NoteBlockInstrument.BASS)
      .strength(2.0F, 3.0F)
      .sound(SoundType.WOOD);
   public static Properties SOULBLIGHT_DOOR = copy(SOULBLIGHT_PLANKS).strength(3.0F).noOcclusion();
   public static Properties SOULBLIGHT_TRAPDOOR = copy(SOULBLIGHT_DOOR).isValidSpawn(ModBlockProperties::never);
   public static Properties SOULBLIGHT_SIGN = copy(SOULBLIGHT_PLANKS).noCollission().strength(1.0F);
   public static Properties WHISTLECANE_BLOCK = Properties.of()
      .mapColor(MapColor.CRIMSON_NYLIUM)
      .instrument(NoteBlockInstrument.BASS)
      .strength(1.0F, 2.0F)
      .sound(SoundType.BAMBOO_WOOD);
   public static Properties WHISTLECANE_DOOR = copy(WHISTLECANE_BLOCK).strength(2.0F).noOcclusion();
   public static Properties WHISTLECANE_TRAPDOOR = copy(WHISTLECANE_DOOR).isValidSpawn(ModBlockProperties::never);
   public static Properties WHISTLECANE_SIGN = copy(WHISTLECANE_BLOCK).noCollission().strength(1.0F);

   public static Properties pottedPlant() {
      return Properties.of().instabreak().noOcclusion();
   }

   public static Properties copy(Properties properties) {
      return Properties.ofFullCopy(new BlockBehaviour(properties) {
         protected MapCodec<? extends Block> codec() {
            throw new UnsupportedOperationException();
         }

         public Item asItem() {
            throw new UnsupportedOperationException();
         }

         protected Block asBlock() {
            throw new UnsupportedOperationException();
         }
      });
   }

   public static Properties copyWithLoot(Properties properties, ResourceLocation id) {
      return PlatformServices.platformHelper.copyBlockPropertiesWithLoot(properties, id);
   }

   public static Boolean never(BlockState state, BlockGetter level, BlockPos pos, EntityType<?> entityType) {
      return false;
   }
}
