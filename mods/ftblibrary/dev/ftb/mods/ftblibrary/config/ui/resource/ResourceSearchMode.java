package dev.ftb.mods.ftblibrary.config.ui.resource;

import dev.architectury.fluid.FluidStack;
import dev.architectury.hooks.fluid.FluidStackHooks;
import dev.ftb.mods.ftblibrary.FTBLibrary;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.icon.Icons;
import dev.ftb.mods.ftblibrary.icon.ItemIcon;
import dev.ftb.mods.ftblibrary.util.StringUtils;
import dev.ftb.mods.ftblibrary.util.client.ClientUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public interface ResourceSearchMode<T> {
   ResourceSearchMode<ItemStack> ALL_ITEMS = new ResourceSearchMode.SearchMode<ItemStack>(
      Component.translatable("ftblibrary.select_item.list_mode.all"), Icons.COMPASS
   ) {
      private List<SelectableResource<ItemStack>> allItemsCache = null;

      @Override
      public Collection<? extends SelectableResource<ItemStack>> getAllResources() {
         if (this.allItemsCache == null) {
            CreativeModeTabs.tryRebuildTabContents(FeatureFlags.DEFAULT_FLAGS, false, ClientUtils.registryAccess());
            this.allItemsCache = CreativeModeTabs.searchTab().getDisplayItems().stream().map(SelectableResource::item).toList();
         }

         return this.allItemsCache;
      }
   };
   ResourceSearchMode<ItemStack> INVENTORY = new ResourceSearchMode.SearchMode<ItemStack>(
      Component.translatable("ftblibrary.select_item.list_mode.inv"), ItemIcon.getItemIcon(Items.CHEST)
   ) {
      @Override
      public Collection<? extends SelectableResource<ItemStack>> getAllResources() {
         Player player = Minecraft.getInstance().player;
         if (player == null) {
            return Collections.emptySet();
         }

         int invSize = player.getInventory().getContainerSize();
         List<SelectableResource<ItemStack>> items = new ArrayList<>(invSize);

         for (int i = 0; i < invSize; i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (!stack.isEmpty()) {
               items.add(SelectableResource.item(stack));
            }
         }

         return items;
      }
   };
   ResourceSearchMode<FluidStack> ALL_FLUIDS = new ResourceSearchMode.SearchMode<FluidStack>(
      Component.translatable("ftblibrary.select_fluid.list_mode.all"), ItemIcon.getItemIcon(Items.COMPASS)
   ) {
      private List<SelectableResource<FluidStack>> allFluidsCache = null;

      @Override
      public Collection<? extends SelectableResource<FluidStack>> getAllResources() {
         if (this.allFluidsCache == null) {
            List<SelectableResource<FluidStack>> fluidstacks = new ArrayList<>();
            BuiltInRegistries.FLUID.forEach(f -> {
               if (f.isSource(f.defaultFluidState())) {
                  fluidstacks.add(SelectableResource.fluid(FluidStack.create(f, FluidStackHooks.bucketAmount())));
               }
            });
            this.allFluidsCache = List.copyOf(fluidstacks);
         }

         return this.allFluidsCache;
      }
   };
   ResourceSearchMode<EntityType<?>> ENTITY_FACES = new ResourceSearchMode.SearchMode<EntityType<?>>(
      Component.translatable("ftblibrary.select_entity.all_entities"), Icons.PLAYER
   ) {
      private List<SelectableResource<EntityType<?>>> allTypesCache = null;

      @Override
      public Collection<? extends SelectableResource<EntityType<?>>> getAllResources() {
         if (this.allTypesCache == null) {
            List<SelectableResource<EntityType<?>>> types = new ArrayList<>();
            BuiltInRegistries.ENTITY_TYPE.forEach(entityType -> {
               if (entityType.create(Minecraft.getInstance().level) instanceof LivingEntity) {
                  types.add(new EntityFaceResource(entityType));
               }
            });
            this.allTypesCache = types.stream().sorted().toList();
         }

         return this.allTypesCache;
      }
   };
   ResourceSearchMode<ResourceLocation> IMAGES = new ResourceSearchMode.SearchMode<ResourceLocation>(
      Component.translatable("ftblibrary.select_image.all_images"), Icons.ART
   ) {
      private List<ImageResource> cachedImages = null;

      @Override
      public void clearCache() {
         this.cachedImages = null;
      }

      @Override
      public Collection<? extends SelectableResource<ResourceLocation>> getAllResources() {
         if (this.cachedImages == null) {
            List<ResourceLocation> images = new ArrayList<>();
            StringUtils.ignoreResourceLocationErrors = true;
            Map<ResourceLocation, Resource> textures = Collections.emptyMap();

            try {
               textures = Minecraft.getInstance().getResourceManager().listResources("textures", t -> t.getPath().endsWith(".png"));
            } catch (Exception ex) {
               FTBLibrary.LOGGER.error("A mod has a broken resource preventing this list from loading: {}", String.valueOf(ex));
            }

            StringUtils.ignoreResourceLocationErrors = false;
            textures.keySet()
               .forEach(
                  rl -> ResourceLocation.read(rl.toString())
                     .result()
                     .ifPresentOrElse(
                        images::add, () -> FTBLibrary.LOGGER.warn("Image {} has invalid path! Report this to author of '{}'!", rl, rl.getNamespace())
                     )
               );
            this.cachedImages = images.stream().sorted().map(res -> {
               ResourceLocation res1 = ResourceLocation.fromNamespaceAndPath(res.getNamespace(), res.getPath().substring(9, res.getPath().length() - 4));
               TextureAtlasSprite sprite = Minecraft.getInstance().getModelManager().getAtlas(InventoryMenu.BLOCK_ATLAS).getSprite(res1);
               SpriteContents contents = sprite.contents();
               if (contents.name().equals(MissingTextureAtlasSprite.getLocation())) {
                  res1 = res;
               }

               return new ImageResource(res1);
            }).toList();
         }

         return this.cachedImages;
      }
   };

   Icon getIcon();

   Component getDisplayName();

   Collection<? extends SelectableResource<T>> getAllResources();

   default void clearCache() {
   }

   abstract class SearchMode<T> implements ResourceSearchMode<T> {
      private final Component name;
      private final Icon icon;

      protected SearchMode(Component name, Icon icon) {
         this.name = name;
         this.icon = icon;
      }

      @Override
      public Component getDisplayName() {
         return this.name;
      }

      @Override
      public Icon getIcon() {
         return this.icon;
      }
   }
}
