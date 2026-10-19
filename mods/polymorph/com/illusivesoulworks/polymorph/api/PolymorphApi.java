package com.illusivesoulworks.polymorph.api;

import com.illusivesoulworks.polymorph.api.common.base.IPolymorphNetwork;
import com.illusivesoulworks.polymorph.api.common.base.IPolymorphRecipeManager;
import com.illusivesoulworks.polymorph.api.common.capability.IBlockEntityRecipeData;
import com.illusivesoulworks.polymorph.api.common.capability.IPlayerRecipeData;
import java.util.Map;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class PolymorphApi {
   public static final String MOD_ID = "polymorph";
   private static PolymorphApi instance;

   public static PolymorphApi getInstance() {
      if (instance == null) {
         throw new RuntimeException("Missing Polymorph API implementation!");
      } else {
         return instance;
      }
   }

   public abstract IPolymorphNetwork getNetwork();

   public abstract IPolymorphRecipeManager getRecipeManager();

   public abstract IBlockEntityRecipeData createBlockEntityRecipeData(BlockEntity var1);

   public abstract IBlockEntityRecipeData getBlockEntityRecipeData(BlockEntity var1);

   public abstract IBlockEntityRecipeData getBlockEntityRecipeData(AbstractContainerMenu var1);

   public abstract IPlayerRecipeData getPlayerRecipeData(Player var1);

   public abstract Map<Class<? extends BlockEntity>, PolymorphApi.IRecipeDataFactory> getBlockEntities();

   @Deprecated
   public abstract void registerBlockEntity(PolymorphApi.IRecipeDataFactory var1);

   public abstract void registerBlockEntity(Class<? extends BlockEntity> var1, PolymorphApi.IRecipeDataFactory var2);

   public abstract void registerMenu(PolymorphApi.IBlockEntityFactory var1);

   public interface IBlockEntityFactory {
      BlockEntity getBlockEntity(AbstractContainerMenu var1);
   }

   public interface IRecipeDataFactory {
      IBlockEntityRecipeData createRecipeData(BlockEntity var1);
   }
}
