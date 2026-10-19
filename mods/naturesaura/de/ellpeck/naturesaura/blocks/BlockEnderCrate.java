package de.ellpeck.naturesaura.blocks;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.ellpeck.naturesaura.api.misc.ILevelData;
import de.ellpeck.naturesaura.blocks.tiles.BlockEntityEnderCrate;
import de.ellpeck.naturesaura.blocks.tiles.ModBlockEntities;
import de.ellpeck.naturesaura.blocks.tiles.render.RenderEnderCrate;
import de.ellpeck.naturesaura.data.BlockStateGenerator;
import de.ellpeck.naturesaura.items.ModItems;
import de.ellpeck.naturesaura.reg.ICustomBlockState;
import de.ellpeck.naturesaura.reg.IPickaxeBreakable;
import de.ellpeck.naturesaura.reg.ITESRProvider;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AnvilUpdateEvent;

public class BlockEnderCrate extends BlockContainerImpl implements ITESRProvider<BlockEntityEnderCrate>, ICustomBlockState, IPickaxeBreakable {
   public BlockEnderCrate() {
      super("ender_crate", BlockEntityEnderCrate.class, Properties.of().strength(5.0F).lightLevel(s -> 7).sound(SoundType.STONE));
      NeoForge.EVENT_BUS.register(this);
   }

   public static String getEnderName(ItemStack stack) {
      return !stack.has(BlockEnderCrate.Data.TYPE) ? "" : ((BlockEnderCrate.Data)stack.get(BlockEnderCrate.Data.TYPE)).enderName;
   }

   @OnlyIn(Dist.CLIENT)
   public static void addEnderNameInfo(ItemStack stack, List<Component> tooltip) {
      String name = getEnderName(stack);
      if (name != null && !name.isEmpty()) {
         tooltip.add(
            Component.literal(
               ChatFormatting.DARK_PURPLE + I18n.get("info.naturesaura.ender_name", new Object[]{ChatFormatting.ITALIC + name + ChatFormatting.RESET})
            )
         );
      } else {
         tooltip.add(Component.literal(ChatFormatting.DARK_PURPLE + I18n.get("info.naturesaura.ender_name.missing", new Object[0])));
      }
   }

   @SubscribeEvent
   public void onAnvilUpdate(AnvilUpdateEvent event) {
      Player player = event.getPlayer();
      if (player != null) {
         ItemStack stack = event.getLeft();
         if (stack.getItem() == this.asItem() || stack.getItem() == ModItems.ENDER_ACCESS) {
            ItemStack second = event.getRight();
            if (second.getItem() == Items.ENDER_EYE && second.getCount() >= stack.getCount()) {
               String name = event.getName();
               if (name != null && !name.isEmpty()) {
                  if (!ILevelData.getOverworldData(player.level()).isEnderStorageLocked(name)) {
                     ItemStack output = stack.copy();
                     output.set(BlockEnderCrate.Data.TYPE, new BlockEnderCrate.Data(name));
                     event.setOutput(output);
                     event.setMaterialCost(stack.getCount());
                     event.setCost(1L);
                  }
               }
            }
         }
      }
   }

   protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
      if (!level.isClientSide && level.getBlockEntity(pos) instanceof BlockEntityEnderCrate crate && crate.canOpen() && crate.canUseRightNow(2500)) {
         crate.drainAura(2500);
         player.openMenu(crate, pos);
      }

      return InteractionResult.SUCCESS;
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
      addEnderNameInfo(stack, tooltipComponents);
   }

   @OnlyIn(Dist.CLIENT)
   public void animateTick(BlockState stateIn, Level levelIn, BlockPos pos, RandomSource rand) {
      for (int i = 0; i < 3; i++) {
         int j = rand.nextInt(2) * 2 - 1;
         int k = rand.nextInt(2) * 2 - 1;
         double d0 = pos.getX() + 0.5 + 0.25 * j;
         double d1 = pos.getY() + rand.nextFloat();
         double d2 = pos.getZ() + 0.5 + 0.25 * k;
         double d3 = rand.nextFloat() * j;
         double d4 = (rand.nextFloat() - 0.5) * 0.125;
         double d5 = rand.nextFloat() * k;
         levelIn.addParticle(ParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5);
      }
   }

   @Override
   public void generateCustomBlockState(BlockStateGenerator generator) {
      generator.simpleBlock(
         this,
         generator.models()
            .cubeBottomTop(
               this.getBaseName(),
               generator.modLoc("block/" + this.getBaseName()),
               generator.modLoc("block/" + this.getBaseName() + "_bottom"),
               generator.modLoc("block/" + this.getBaseName() + "_top")
            )
      );
   }

   @Override
   public void registerTESR() {
      BlockEntityRenderers.register(ModBlockEntities.ENDER_CRATE, RenderEnderCrate::new);
   }

   public record Data(String enderName) {
      public static final Codec<BlockEnderCrate.Data> CODEC = RecordCodecBuilder.create(
         i -> i.group(Codec.STRING.fieldOf("name").forGetter(d -> d.enderName)).apply(i, BlockEnderCrate.Data::new)
      );
      public static final DataComponentType<BlockEnderCrate.Data> TYPE = DataComponentType.builder().persistent(CODEC).cacheEncoding().build();
   }
}
