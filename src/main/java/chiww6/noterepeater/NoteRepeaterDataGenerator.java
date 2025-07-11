package chiww6.noterepeater;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import chiww6.noterepeater.registry.ModBlocks;

public class NoteRepeaterDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        var pack = fabricDataGenerator.createPack();
        
        pack.addProvider((output, registriesFuture) -> new FabricTagProvider.ItemTagProvider(output, registriesFuture) {
            @Override
            protected void configure(RegistryWrapper.WrapperLookup arg) {
                // 将音符中继器添加到红石类别
                getOrCreateTagBuilder(TagKey.of(RegistryKeys.ITEM, new Identifier("minecraft", "redstone")))
                    .add(ModBlocks.NOTE_REPEATER.asItem());
            }
        });
    }
}
