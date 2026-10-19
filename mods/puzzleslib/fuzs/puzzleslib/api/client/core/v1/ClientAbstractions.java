package fuzs.puzzleslib.api.client.core.v1;

import fuzs.puzzleslib.api.core.v1.ServiceProviderHelper;
import java.util.List;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public interface ClientAbstractions {
   ClientAbstractions INSTANCE = ServiceProviderHelper.load(ClientAbstractions.class);

   boolean hasChannel(ClientPacketListener var1, Type<?> var2);

   boolean isKeyActiveAndMatches(KeyMapping var1, int var2, int var3);

   ClientTooltipComponent createImageComponent(TooltipComponent var1);

   @Deprecated(forRemoval = true)
   default BakedModel getBakedModel(ResourceLocation resourceLocation) {
      return this.getBakedModel(Minecraft.getInstance().getModelManager(), resourceLocation);
   }

   BakedModel getBakedModel(ModelManager var1, ResourceLocation var2);

   RenderType getRenderType(Block var1);

   default RenderType getRenderType(Fluid fluid) {
      return ItemBlockRenderTypes.getRenderLayer(fluid.defaultFluidState());
   }

   void registerRenderType(Block var1, RenderType var2);

   void registerRenderType(Fluid var1, RenderType var2);

   default float getPartialTick() {
      return Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(false);
   }

   boolean onRenderTooltip(GuiGraphics var1, Font var2, int var3, int var4, List<ClientTooltipComponent> var5, ClientTooltipPositioner var6);

   int getGuiLeftHeight(Gui var1);

   int getGuiRightHeight(Gui var1);

   void addGuiLeftHeight(Gui var1, int var2);

   void addGuiRightHeight(Gui var1, int var2);
}
