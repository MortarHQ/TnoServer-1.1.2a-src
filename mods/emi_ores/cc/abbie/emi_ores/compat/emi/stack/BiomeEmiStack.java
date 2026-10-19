package cc.abbie.emi_ores.compat.emi.stack;

import cc.abbie.emi_ores.EmiOres;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.emi.emi.api.render.EmiTooltipComponents;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.stack.serializer.EmiStackSerializer;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.biome.Biome;

public class BiomeEmiStack extends EmiStack {
   private static final ResourceLocation missingSpriteId = EmiOres.id("emi_ores/biome_icon/missing");
   private final Biome biome;
   private final TextureAtlasSprite sprite;

   private BiomeEmiStack(Biome biome) {
      this.biome = biome;
      TextureAtlas atlas = Minecraft.getInstance().getModelManager().getAtlas(InventoryMenu.BLOCK_ATLAS);
      ResourceLocation id = this.getId();
      TextureAtlasSprite sprite;
      if (id == null) {
         sprite = atlas.getSprite(missingSpriteId);
      } else {
         sprite = atlas.getSprite(this.getId().withPrefix("emi_ores/biome_icon/"));
         if (MissingTextureAtlasSprite.getLocation().equals(sprite.contents().name())) {
            sprite = atlas.getSprite(missingSpriteId);
         }
      }

      this.sprite = sprite;
   }

   public static EmiStack of(Biome biome, DataComponentPatch componentChanges, long amount) {
      return new BiomeEmiStack(biome);
   }

   public static EmiStack of(Biome biome) {
      return of(biome, null, 0L);
   }

   public EmiStack copy() {
      return new BiomeEmiStack(this.biome);
   }

   public void render(GuiGraphics gui, int x, int y, float delta, int flags) {
      if ((flags & 1) != 0) {
         PoseStack pose = gui.pose();
         pose.pushPose();
         pose.translate(0.0F, 0.0F, 150.0F);
         gui.blit(x, y, 0, 16, 16, this.sprite);
         pose.popPose();
      }
   }

   public boolean isEmpty() {
      return false;
   }

   public DataComponentPatch getComponentChanges() {
      return DataComponentPatch.EMPTY;
   }

   public Object getKey() {
      return this.biome;
   }

   public ResourceLocation getId() {
      return Minecraft.getInstance().level.registryAccess().registryOrThrow(Registries.BIOME).getKey(this.biome);
   }

   public List<Component> getTooltipText() {
      return null;
   }

   public List<ClientTooltipComponent> getTooltip() {
      List<ClientTooltipComponent> list = new ArrayList<>();
      list.add(ClientTooltipComponent.create(this.getName().getVisualOrderText()));
      if (Minecraft.getInstance().options.advancedItemTooltips) {
         list.add(ClientTooltipComponent.create(Component.literal(this.getId().toString()).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText()));
      }

      EmiTooltipComponents.appendModName(list, this.getId().getNamespace());
      list.addAll(super.getTooltip());
      return list;
   }

   public Component getName() {
      return Component.translatable(this.getId().toLanguageKey("biome"));
   }

   public static class Serializer implements EmiStackSerializer<BiomeEmiStack> {
      public String getType() {
         return "biome";
      }

      public EmiStack create(ResourceLocation id, DataComponentPatch componentChanges, long amount) {
         Registry<Biome> biomeRegistry = Minecraft.getInstance().level.registryAccess().registryOrThrow(Registries.BIOME);
         return BiomeEmiStack.of((Biome)biomeRegistry.getOrThrow(ResourceKey.create(Registries.BIOME, id)), componentChanges, amount);
      }
   }
}
