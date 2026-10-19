package team.creative.creativecore.common.gui.creator;

import java.util.function.BiFunction;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.creative.creativecore.CreativeCore;
import team.creative.creativecore.common.gui.GuiLayer;
import team.creative.creativecore.common.gui.integration.GuiScreenIntegration;
import team.creative.creativecore.common.gui.packet.OpenGuiPacket;
import team.creative.creativecore.common.util.registry.NamedHandlerRegistry;

public abstract class GuiCreator {
   public static final NamedHandlerRegistry<GuiCreator> REGISTRY = new NamedHandlerRegistry<>(null);
   public static final GuiCreator.GuiCreatorItem ITEM_OPENER = register("item", new GuiCreator.GuiCreatorItem());
   public static final GuiCreator.GuiCreatorBlock BLOCK_OPENER = register("block", new GuiCreator.GuiCreatorBlock());
   String name;
   public final BiFunction<CompoundTag, Player, GuiLayer> function;

   public static <T extends GuiCreator> T register(String name, T creator) {
      creator.name = name;
      REGISTRY.register(name, creator);
      return creator;
   }

   public GuiCreator(BiFunction<CompoundTag, Player, GuiLayer> function) {
      this.function = function;
   }

   public String getName() {
      return this.name;
   }

   protected void openGui(CompoundTag nbt, Player player) {
      if (player.level().isClientSide) {
         CreativeCore.NETWORK.sendToServer(new OpenGuiPacket(this.name, nbt));
      } else {
         OpenGuiPacket.openGuiOnServer(this, nbt, (ServerPlayer)player);
      }
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   public static void openClientSide(GuiLayer layer) {
      Minecraft.getInstance().forceSetScreen(new GuiScreenIntegration(layer));
   }

   public static class GuiCreatorBasic extends GuiCreator {
      public GuiCreatorBasic(BiFunction<CompoundTag, Player, GuiLayer> function) {
         super(function);
      }

      public void open(Player player) {
         this.openGui(new CompoundTag(), player);
      }

      public void open(CompoundTag nbt, Player player) {
         this.openGui(nbt, player);
      }
   }

   public static class GuiCreatorBlock extends GuiCreator {
      public GuiCreatorBlock() {
         super((nbt, player) -> {
            BlockPos pos = new BlockPos(nbt.getInt("x"), nbt.getInt("y"), nbt.getInt("z"));
            Level level = player.level();
            BlockState state = level.getBlockState(pos);
            return state.getBlock() instanceof BlockGuiCreator block ? block.create(nbt, level, pos, state, player) : null;
         });
      }

      public void open(Player player, BlockPos pos) {
         this.open(new CompoundTag(), player, pos);
      }

      public void open(CompoundTag nbt, Player player, BlockPos pos) {
         nbt.putInt("x", pos.getX());
         nbt.putInt("y", pos.getY());
         nbt.putInt("z", pos.getZ());
         this.openGui(nbt, player);
      }
   }

   public static class GuiCreatorItem extends GuiCreator {
      public GuiCreatorItem() {
         super((nbt, player) -> {
            InteractionHand hand = nbt.getBoolean("main_hand") ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
            ItemStack stack = player.getItemInHand(hand);
            return stack.getItem() instanceof ItemGuiCreator item ? item.create(nbt, player) : null;
         });
      }

      public void open(Player player, InteractionHand hand) {
         this.open(new CompoundTag(), player, hand);
      }

      public void open(CompoundTag nbt, Player player, InteractionHand hand) {
         nbt.putBoolean("main_hand", hand == InteractionHand.MAIN_HAND);
         this.openGui(nbt, player);
      }
   }
}
