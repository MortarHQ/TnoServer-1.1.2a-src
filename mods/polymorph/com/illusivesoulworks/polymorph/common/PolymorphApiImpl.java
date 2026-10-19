package com.illusivesoulworks.polymorph.common;

import com.google.common.collect.ImmutableMap;
import com.illusivesoulworks.polymorph.PolymorphConstants;
import com.illusivesoulworks.polymorph.api.PolymorphApi;
import com.illusivesoulworks.polymorph.api.common.base.IPolymorphNetwork;
import com.illusivesoulworks.polymorph.api.common.base.IPolymorphRecipeManager;
import com.illusivesoulworks.polymorph.api.common.capability.IBlockEntityRecipeData;
import com.illusivesoulworks.polymorph.api.common.capability.IPlayerRecipeData;
import com.illusivesoulworks.polymorph.common.capability.PolymorphCapabilities;
import com.illusivesoulworks.polymorph.platform.Services;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;

public class PolymorphApiImpl extends PolymorphApi {
   public static final PolymorphApi INSTANCE = new PolymorphApiImpl();
   private final List<PolymorphApi.IBlockEntityFactory> blockEntityFactories = new CopyOnWriteArrayList<>();
   private final List<PolymorphApi.IRecipeDataFactory> recipeDataFactories = new CopyOnWriteArrayList<>();
   private final Map<Class<? extends BlockEntity>, PolymorphApi.IRecipeDataFactory> blockEntity2RecipeDataFactory = new ConcurrentHashMap<>();
   private final IPolymorphNetwork distributor = Services.PLATFORM.getPacketDistributor();
   private final IPolymorphRecipeManager recipeManager = new PolymorphRecipeManager();

   @Override
   public IPolymorphNetwork getNetwork() {
      return this.distributor;
   }

   @Override
   public IPolymorphRecipeManager getRecipeManager() {
      return this.recipeManager;
   }

   @Override
   public IBlockEntityRecipeData createBlockEntityRecipeData(BlockEntity blockEntity) {
      for (PolymorphApi.IRecipeDataFactory function : this.recipeDataFactories) {
         IBlockEntityRecipeData recipeData = function.createRecipeData(blockEntity);
         if (recipeData != null) {
            return recipeData;
         }
      }

      return null;
   }

   @Override
   public IBlockEntityRecipeData getBlockEntityRecipeData(BlockEntity blockEntity) {
      return PolymorphCapabilities.getRecipeData(blockEntity);
   }

   @Override
   public IBlockEntityRecipeData getBlockEntityRecipeData(AbstractContainerMenu container) {
      for (PolymorphApi.IBlockEntityFactory function : this.blockEntityFactories) {
         BlockEntity blockEntity = function.getBlockEntity(container);
         if (blockEntity != null) {
            return this.getBlockEntityRecipeData(blockEntity);
         }
      }

      return null;
   }

   @Override
   public IPlayerRecipeData getPlayerRecipeData(Player player) {
      return PolymorphCapabilities.getRecipeData(player);
   }

   @Override
   public Map<Class<? extends BlockEntity>, PolymorphApi.IRecipeDataFactory> getBlockEntities() {
      return ImmutableMap.copyOf(this.blockEntity2RecipeDataFactory);
   }

   @Override
   public void registerMenu(PolymorphApi.IBlockEntityFactory blockEntityFactory) {
      if (blockEntityFactory == null) {
         PolymorphConstants.LOG.error("Attempted to register a null IBlockEntityFactory");
      } else {
         this.blockEntityFactories.add(blockEntityFactory);
      }
   }

   @Override
   public void registerBlockEntity(PolymorphApi.IRecipeDataFactory recipeDataFactory) {
      if (recipeDataFactory == null) {
         PolymorphConstants.LOG.error("Attempted to register a null IRecipeDataFactory");
      } else {
         this.recipeDataFactories.add(recipeDataFactory);
      }
   }

   @Override
   public void registerBlockEntity(Class<? extends BlockEntity> blockEntity, PolymorphApi.IRecipeDataFactory recipeDataFactory) {
      if (recipeDataFactory == null) {
         PolymorphConstants.LOG.error("Attempted to register a null IRecipeDataFactory");
      } else {
         this.blockEntity2RecipeDataFactory.put(blockEntity, recipeDataFactory);
         this.recipeDataFactories.add(recipeDataFactory);
      }
   }
}
