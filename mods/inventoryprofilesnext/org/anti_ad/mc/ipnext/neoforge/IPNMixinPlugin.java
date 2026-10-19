package org.anti_ad.mc.ipnext.neoforge;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.moddiscovery.ModFileInfo;
import net.neoforged.neoforgespi.language.IModInfo;
import net.neoforged.neoforgespi.language.IModInfo.DependencyType;
import net.neoforged.neoforgespi.language.IModInfo.ModVersion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

@SourceDebugExtension(
   "SMAP\nIPNMixinPlugin.kt\nKotlin\n*S Kotlin\n*F\n+ 1 IPNMixinPlugin.kt\norg/anti_ad/mc/ipnext/neoforge/IPNMixinPlugin\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,102:1\n1869#2,2:103\n*S KotlinDebug\n*F\n+ 1 IPNMixinPlugin.kt\norg/anti_ad/mc/ipnext/neoforge/IPNMixinPlugin\n*L\n54#1:103,2\n*E\n"
)
public final class IPNMixinPlugin implements IMixinConfigPlugin {
   @NotNull
   private final Logger logger;
   private boolean shouldLoad;
   private boolean resolved;

   public IPNMixinPlugin() {
      Logger var10001 = LogManager.getLogger("IMixinConfigPlugin");
      Intrinsics.checkNotNullExpressionValue(var10001, "");
      this.logger = var10001;
      this.shouldLoad = true;
   }

   public final void onLoad(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");
   }

   @Nullable
   public final String getRefMapperConfig() {
      return null;
   }

   private final void resolveMyDependencies() {
      if (this.shouldLoad) {
         this.logger
            .warn(
               "Inventory Profiles Next: Doing our own dependency resolving! Because Forge is being Forge....: https://github.com/MinecraftForge/MinecraftForge/issues/9088"
            );
         ModFileInfo var1;
         if ((var1 = FMLLoader.getLoadingModList().getModFileById("inventoryprofilesnext")) == null) {
            this.logger.info("Inventory Profiles Next dependency resolution failed! Probably missing libIPN.");
            this.shouldLoad = false;
         } else {
            List var10000 = var1.getMods();
            Intrinsics.checkNotNullExpressionValue(var10000, "");
            Iterator var3 = var10000.iterator();

            while (true) {
               if (var3.hasNext()) {
                  Object var2;
                  if (!Intrinsics.areEqual(((IModInfo)(var2 = var3.next())).getModId(), "inventoryprofilesnext")) {
                     continue;
                  }

                  var9 = var2;
                  break;
               }

               var9 = null;
               break;
            }

            IModInfo var4;
            List var5;
            if ((var4 = (IModInfo)var9) != null && (var5 = var4.getDependencies()) != null) {
               Iterator var8 = var5.iterator();

               while (var8.hasNext()) {
                  ModVersion var6;
                  if ((var6 = (ModVersion)var8.next()).getType() == DependencyType.REQUIRED) {
                     var1 = FMLLoader.getLoadingModList().getModFileById(var6.getModId());
                     this.shouldLoad = var1 != null;
                     if (!this.shouldLoad) {
                        this.logger.info("Inventory Profiles Next dependency resolution failed! Probably missing libIPN.");
                        this.resolved = true;
                        return;
                     }
                  }
               }
            }
         }

         this.resolved = true;
      }
   }

   public final boolean shouldApplyMixin(@NotNull String var1, @NotNull String var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      if (!this.resolved) {
         this.resolveMyDependencies();
      }

      return this.shouldLoad;
   }

   public final void acceptTargets(@NotNull Set var1, @NotNull Set var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
   }

   @Nullable
   public final List getMixins() {
      return null;
   }

   public final void preApply(@NotNull String var1, @NotNull ClassNode var2, @NotNull String var3, @NotNull IMixinInfo var4) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var3, "");
      Intrinsics.checkNotNullParameter(var4, "");
   }

   public final void postApply(@NotNull String var1, @NotNull ClassNode var2, @NotNull String var3, @NotNull IMixinInfo var4) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var3, "");
      Intrinsics.checkNotNullParameter(var4, "");
   }
}
