package chiww6.noterepeater.registry;

import chiww6.noterepeater.block.NoteRepeater;
import chiww6.noterepeater.NoteRepeaterMod;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {

    // 音符中继器方块实例
    public static final Block NOTE_REPEATER = new NoteRepeater(
            Block.Settings.create()
                    .mapColor(MapColor.STONE_GRAY)
                    .strength(0.5f)
                    .nonOpaque()
    );

    // 注册方块与物品，并加入创造模式红石标签
    public static void register() {
        Registry.register(Registries.BLOCK,
                new Identifier(NoteRepeaterMod.MOD_ID, "note_repeater"),
                NOTE_REPEATER);

        Registry.register(Registries.ITEM,
                new Identifier(NoteRepeaterMod.MOD_ID, "note_repeater"),
                new BlockItem(NOTE_REPEATER, new Item.Settings()));

        // 加入创造模式红石标签
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(entries -> {
            entries.addAfter(Items.REPEATER, NOTE_REPEATER);
        });
    }
}
