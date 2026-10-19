package com.blamejared.crafttweaker.api.plugin;

public interface ICraftTweakerPlugin {
   default void initialize() {
   }

   default void registerLoaders(ILoaderRegistrationHandler handler) {
   }

   default void registerLoadSource(IScriptLoadSourceRegistrationHandler handler) {
   }

   default void registerModuleConfigurators(IScriptRunModuleConfiguratorRegistrationHandler handler) {
   }

   default void registerBracketParsers(IBracketParserRegistrationHandler handler) {
   }

   default void registerRecipeComponents(IRecipeComponentRegistrationHandler handler) {
   }

   default void registerRecipeHandlers(IRecipeHandlerRegistrationHandler handler) {
   }

   default void manageJavaNativeIntegration(IJavaNativeIntegrationRegistrationHandler handler) {
   }

   default void registerListeners(IListenerRegistrationHandler handler) {
   }

   default void registerVillagerTradeConverters(IVillagerTradeRegistrationHandler handler) {
   }

   default void registerCommands(ICommandRegistrationHandler handler) {
   }

   default void registerTaggableElements(ITaggableElementRegistrationHandler handler) {
   }

   default void registerReplacerComponents(IReplacerComponentRegistrationHandler handler) {
   }

   default void registerEvents(IEventRegistrationHandler handler) {
   }
}
