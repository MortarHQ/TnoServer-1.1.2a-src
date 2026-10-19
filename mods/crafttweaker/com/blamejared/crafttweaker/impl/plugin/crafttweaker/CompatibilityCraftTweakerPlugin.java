package com.blamejared.crafttweaker.impl.plugin.crafttweaker;

import com.blamejared.crafttweaker.api.bracket.custom.EnumConstantBracketHandler;
import com.blamejared.crafttweaker.api.plugin.CraftTweakerPlugin;
import com.blamejared.crafttweaker.api.plugin.IBracketParserRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.ICraftTweakerPlugin;
import com.blamejared.crafttweaker.api.plugin.IEventRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.IJavaNativeIntegrationRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.IRecipeHandlerRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.ITaggableElementRegistrationHandler;

@CraftTweakerPlugin("crafttweaker:compatibility")
public final class CompatibilityCraftTweakerPlugin implements ICraftTweakerPlugin {
   private final BracketParserRegistrationManager bracketParserRegistrationManager = new BracketParserRegistrationManager();
   private final EnumBracketParserRegistrationManager enumBracketParserRegistrationManager = new EnumBracketParserRegistrationManager();
   private final EventRegistrationManager eventRegistrationManager = new EventRegistrationManager();
   private final RecipeHandlerGatherer handlerGatherer = new RecipeHandlerGatherer();
   private final TaggableElementsRegistrationManager taggableElementsRegistrationManager = new TaggableElementsRegistrationManager();
   private final ZenClassGatherer zenGatherer = new ZenClassGatherer();
   private final ZenClassRegistrationManager zenClassRegistrationManager = new ZenClassRegistrationManager();

   @Override
   public void initialize() {
      this.zenGatherer.gatherCandidates();
   }

   @Override
   public void manageJavaNativeIntegration(IJavaNativeIntegrationRegistrationHandler handler) {
      this.zenGatherer.listProviders();
      this.zenGatherer.onCandidates(candidate -> this.zenClassRegistrationManager.attemptRegistration(candidate.loader(), candidate.clazz(), handler));
      this.zenClassRegistrationManager.attemptDeferredRegistration(handler);
   }

   @Override
   public void registerBracketParsers(IBracketParserRegistrationHandler handler) {
      handler.registerParserFor(
         "*",
         "constant",
         new EnumConstantBracketHandler(),
         new IBracketParserRegistrationHandler.DumperData("constant", EnumConstantBracketHandler.getDumperData())
      );
      this.zenGatherer.onCandidates(candidate -> {
         this.bracketParserRegistrationManager.addRegistrationCandidate(candidate.clazz(), candidate.loader());
         this.enumBracketParserRegistrationManager.attemptRegistration(candidate.clazz(), candidate.loader(), handler);
      });
      this.bracketParserRegistrationManager.attemptRegistration(handler);
   }

   @Override
   public void registerRecipeHandlers(IRecipeHandlerRegistrationHandler handler) {
      this.handlerGatherer.gatherAndRegisterHandlers(handler);
   }

   @Override
   public void registerTaggableElements(ITaggableElementRegistrationHandler handler) {
      this.zenGatherer.onCandidates(candidate -> this.taggableElementsRegistrationManager.attemptRegistration(candidate.clazz(), handler));
   }

   @Override
   public void registerEvents(IEventRegistrationHandler handler) {
      this.zenGatherer.onCandidates(candidate -> this.eventRegistrationManager.attemptRegistration(candidate.clazz(), handler));
   }
}
