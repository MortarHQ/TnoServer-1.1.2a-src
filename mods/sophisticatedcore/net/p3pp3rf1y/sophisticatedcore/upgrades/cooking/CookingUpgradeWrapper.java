package net.p3pp3rf1y.sophisticatedcore.upgrades.cooking;

import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.crafting.SmokingRecipe;
import net.minecraft.world.level.Level;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.renderdata.RenderInfo;
import net.p3pp3rf1y.sophisticatedcore.upgrades.ITickableUpgrade;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeItemBase;
import net.p3pp3rf1y.sophisticatedcore.upgrades.UpgradeWrapperBase;

public abstract class CookingUpgradeWrapper<W extends CookingUpgradeWrapper<W, U, R>, U extends UpgradeItemBase<W> & ICookingUpgradeItem, R extends AbstractCookingRecipe>
   extends UpgradeWrapperBase<W, U>
   implements ITickableUpgrade,
   ICookingUpgrade<R> {
   private static final int NOTHING_TO_DO_COOLDOWN = 10;
   protected final CookingLogic<R> cookingLogic;

   protected CookingUpgradeWrapper(
      IStorageWrapper storageWrapper, ItemStack upgrade, Consumer<ItemStack> upgradeSaveHandler, RecipeType<R> recipeType, float burnTimeModifier
   ) {
      super(storageWrapper, upgrade, upgradeSaveHandler);
      this.cookingLogic = new CookingLogic<>(upgrade, upgradeSaveHandler, this.upgradeItem.getCookingUpgradeConfig(), recipeType, burnTimeModifier);
   }

   @Override
   public void tick(@Nullable Entity entity, Level level, BlockPos pos) {
      if (!this.isInCooldown(level)) {
         if (!this.cookingLogic.tick(level)) {
            this.setCooldown(level, 10);
         }

         boolean isBurning = this.cookingLogic.isBurning(level);
         RenderInfo renderInfo = this.storageWrapper.getRenderInfo();
         if (renderInfo.getUpgradeRenderData(CookingUpgradeRenderData.TYPE).map(CookingUpgradeRenderData::isBurning).orElse(false) != isBurning) {
            if (isBurning) {
               renderInfo.setUpgradeRenderData(CookingUpgradeRenderData.TYPE, new CookingUpgradeRenderData(true));
            } else {
               renderInfo.removeUpgradeRenderData(CookingUpgradeRenderData.TYPE);
            }
         }
      }
   }

   @Override
   public void setEnabled(boolean enabled) {
      if (!enabled) {
         this.pauseAndRemoveRenderInfo();
      }

      super.setEnabled(enabled);
   }

   @Override
   public void onBeforeRemoved() {
      this.pauseAndRemoveRenderInfo();
   }

   private void pauseAndRemoveRenderInfo() {
      this.cookingLogic.pause();
      RenderInfo renderInfo = this.storageWrapper.getRenderInfo();
      renderInfo.removeUpgradeRenderData(CookingUpgradeRenderData.TYPE);
   }

   @Override
   public CookingLogic<R> getCookingLogic() {
      return this.cookingLogic;
   }

   public static class BlastingUpgradeWrapper extends CookingUpgradeWrapper<CookingUpgradeWrapper.BlastingUpgradeWrapper, BlastingUpgradeItem, BlastingRecipe> {
      public BlastingUpgradeWrapper(IStorageWrapper storageWrapper, ItemStack upgrade, Consumer<ItemStack> upgradeSaveHandler) {
         super(storageWrapper, upgrade, upgradeSaveHandler, RecipeType.BLASTING, 0.5F);
      }
   }

   public static class SmeltingUpgradeWrapper extends CookingUpgradeWrapper<CookingUpgradeWrapper.SmeltingUpgradeWrapper, SmeltingUpgradeItem, SmeltingRecipe> {
      public SmeltingUpgradeWrapper(IStorageWrapper storageWrapper, ItemStack upgrade, Consumer<ItemStack> upgradeSaveHandler) {
         super(storageWrapper, upgrade, upgradeSaveHandler, RecipeType.SMELTING, 1.0F);
      }
   }

   public static class SmokingUpgradeWrapper extends CookingUpgradeWrapper<CookingUpgradeWrapper.SmokingUpgradeWrapper, SmokingUpgradeItem, SmokingRecipe> {
      public SmokingUpgradeWrapper(IStorageWrapper storageWrapper, ItemStack upgrade, Consumer<ItemStack> upgradeSaveHandler) {
         super(storageWrapper, upgrade, upgradeSaveHandler, RecipeType.SMOKING, 0.5F);
      }
   }
}
