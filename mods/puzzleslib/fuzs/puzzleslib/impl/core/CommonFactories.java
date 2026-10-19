package fuzs.puzzleslib.impl.core;

import fuzs.puzzleslib.api.core.v1.ContentRegistrationFlags;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.api.core.v1.ServiceProviderHelper;
import fuzs.puzzleslib.api.data.v2.tags.AbstractTagAppender;
import fuzs.puzzleslib.api.init.v3.GameRulesFactory;
import fuzs.puzzleslib.api.init.v3.registry.RegistryFactory;
import fuzs.puzzleslib.api.item.v2.ToolTypeHelper;
import fuzs.puzzleslib.api.item.v2.crafting.CombinedIngredients;
import fuzs.puzzleslib.impl.attachment.DataAttachmentRegistryImpl;
import java.util.Set;
import java.util.function.Function;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagBuilder;
import org.jetbrains.annotations.Nullable;

public interface CommonFactories {
   CommonFactories INSTANCE = ServiceProviderHelper.load(CommonFactories.class);

   void constructMod(String var1, ModConstructor var2, Set<ContentRegistrationFlags> var3, Set<ContentRegistrationFlags> var4);

   ModContext getModContext(String var1);

   ProxyImpl getClientProxy();

   ProxyImpl getServerProxy();

   RegistryFactory getRegistryFactory();

   GameRulesFactory getGameRulesFactory();

   void registerLoadingHandlers();

   void registerEventHandlers();

   ToolTypeHelper getToolTypeHelper();

   CombinedIngredients getCombinedIngredients();

   <T> AbstractTagAppender<T> getTagAppender(TagBuilder var1, @Nullable Function<T, ResourceKey<T>> var2);

   DataAttachmentRegistryImpl getDataAttachmentRegistry();
}
