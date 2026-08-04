package de.merlinmomo12.createpowergridinstrumentation;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import net.createmod.catnip.lang.FontHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.concurrent.CompletableFuture;

@Mod(CreatePowergridInstrumentation.MODID)
public class CreatePowergridInstrumentation {
    public static final String MODID = "createpowergridinstrumentation";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MODID)
            .setTooltipModifierFactory(item ->
                    new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)
                            .andThen(TooltipModifier.mapNull(KineticStats.create(item)))
            );

    public CreatePowergridInstrumentation(IEventBus modBus) {
        REGISTRATE.registerEventListeners(modBus);


        AllCreativeModeTabs.register();

        REGISTRATE.setCreativeTab(AllCreativeModeTabs.MAIN_TAB);
        /*
        registerLangPartials();
        registerPonderLang();

         */
        /*
        AllItems.register();
        AllDisplaySources.register();
        AllBlocks.register();
        AllBlockEntityTypes.register();

         */

        modBus.addListener(this::onCommonSetup);
        modBus.addListener(this::onClientSetup);
        modBus.addListener(this::onGatherData);
    }

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    private void onCommonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("Common setup...");
    }

    private void onClientSetup(FMLClientSetupEvent event) {
        LOGGER.info("Client setup...");
        event.enqueueWork(() -> {
            // Ponder is client-only

        });
    }

    /**
     * Feeds the hand-authored language partials (assets/examplemod/lang/default/*.json)
     * into Registrate's lang provider so runData merges them with the generated block and
     * item names into a single en_us.json. Keeps English copy out of Java, mirroring how
     * Create authors its own translations.
     */
    /*
    private void registerLangPartials() {
        REGISTRATE.addDataGenerator(ProviderType.LANG, provider ->
                ExampleLangMerger.mergeInto(provider::add));
    }

     */

    /**
     * Feeds the Ponder scenes' text (titles and captions) into Registrate's lang provider
     * so runData writes it into the same en_us.json as the block and item names. The
     * registered callback only runs during data generation, so it is safe to touch the
     * client-only PonderIndex from here.
     */
    /*
    private void registerPonderLang() {
        REGISTRATE.addDataGenerator(ProviderType.LANG, provider -> {
            PonderIndex.addPlugin(new ExamplePonderPlugin());
            PonderIndex.getLangAccess().provideLang(ID, provider::add);
        });
    }

     */

    /**
     * Registers the data generators. Running gradlew runData writes their output into
     * src/generated/resources.
     */
    private void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> registries = event.getLookupProvider();


    }
}