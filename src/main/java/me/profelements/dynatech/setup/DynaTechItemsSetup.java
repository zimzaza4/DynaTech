package me.profelements.dynatech.setup;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.profelements.dynatech.DynaTech;
import me.profelements.dynatech.DynaTechItems;
import me.profelements.dynatech.items.backpacks.PicnicBasket;
import me.profelements.dynatech.items.machines.AntigravityBubble;
import me.profelements.dynatech.items.machines.AutoKitchen;
import me.profelements.dynatech.items.machines.DragonEggGenerator;
import me.profelements.dynatech.items.machines.GrowthChamber;
import me.profelements.dynatech.items.machines.HydroGenerator;
import me.profelements.dynatech.items.machines.WeatherController;
import me.profelements.dynatech.items.tools.ElectricalStimulator;
import me.profelements.dynatech.items.tools.InventoryFilter;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class DynaTechItemsSetup {

    private DynaTechItemsSetup() {}

    public static final void setup(@Nonnull DynaTech plugin) {

        //Materials
        new SlimefunItem(DynaTechItems.DynaTechGeneral, DynaTechItems.STAINLESS_STEEL, RecipeType.SMELTERY,
                new ItemStack[] {
                    new ItemStack(Material.IRON_INGOT), SlimefunItems.IRON_DUST, SlimefunItems.ZINC_DUST,
                        null, null, null,
                        null, null, null,
                }).register(plugin);

        new SlimefunItem(DynaTechItems.DynaTechGeneral, DynaTechItems.STAINLESS_STEEL_ROTOR, RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {
                        null, DynaTechItems.STAINLESS_STEEL, null,
                        DynaTechItems.STAINLESS_STEEL, new ItemStack(Material.IRON_BLOCK), DynaTechItems.STAINLESS_STEEL,
                        null, DynaTechItems.STAINLESS_STEEL, null
                }).register(plugin);
        
        new SlimefunItem(DynaTechItems.DynaTechGeneral, DynaTechItems.ANCIENT_MACHINE_CORE, RecipeType.MAGIC_WORKBENCH,
                new ItemStack[] {
                        SlimefunItems.LEAD_INGOT, SlimefunItems.MAGIC_LUMP_1, SlimefunItems.LEAD_INGOT,
                        SlimefunItems.MAGIC_LUMP_1, new ItemStack(Material.REDSTONE_BLOCK), SlimefunItems.MAGIC_LUMP_1,
                        SlimefunItems.LEAD_INGOT, SlimefunItems.MAGIC_LUMP_1, SlimefunItems.LEAD_INGOT
                }).register(plugin);

        //Backpacks
        new PicnicBasket(27, DynaTechItems.DynaTechGeneral, DynaTechItems.PICNIC_BASKET, RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {
                        SlimefunItems.CLOTH, SlimefunItems.CLOTH, SlimefunItems.CLOTH,
                        new ItemStack(Material.BAMBOO), SlimefunItems.COOLER, new ItemStack(Material.BAMBOO),
                        SlimefunItems.HEATING_COIL, new ItemStack(Material.BAMBOO), SlimefunItems.COOLING_UNIT
                }).register(plugin);
        
        new InventoryFilter(DynaTechItems.DynaTechGeneral, DynaTechItems.INVENTORY_FILTER, RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {
                        SlimefunItems.REINFORCED_CLOTH, new ItemStack(Material.IRON_BARS), SlimefunItems.REINFORCED_CLOTH,
                        new ItemStack(Material.IRON_BARS), null, new ItemStack(Material.IRON_BARS),
                        SlimefunItems.REINFORCED_CLOTH, new ItemStack(Material.IRON_BARS), SlimefunItems.REINFORCED_CLOTH
                }).register(plugin);

        new ElectricalStimulator(DynaTechItems.DynaTechGeneral, DynaTechItems.ELECTRICAL_STIMULATOR, RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {
                        DynaTechItems.STAINLESS_STEEL, null, DynaTechItems.STAINLESS_STEEL,
                        DynaTechItems.STAINLESS_STEEL, SlimefunItems.FOOD_FABRICATOR, DynaTechItems.STAINLESS_STEEL,
                        SlimefunItems.PLASTIC_SHEET, SlimefunItems.PLASTIC_SHEET, SlimefunItems.PLASTIC_SHEET
                }).register(plugin);

        //Machines
        new AutoKitchen(DynaTechItems.DynaTechGeneral, DynaTechItems.AUTO_KITCHEN, RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {
                        new ItemStack(Material.BRICK), SlimefunItems.ELECTRIC_FURNACE, new ItemStack(Material.BRICK),
                        DynaTechItems.STAINLESS_STEEL, SlimefunItems.GOLD_24K_BLOCK, DynaTechItems.STAINLESS_STEEL,
                        new ItemStack(Material.TERRACOTTA), new ItemStack(Material.TERRACOTTA), new ItemStack(Material.TERRACOTTA)

                })
                .setEnergyCapacity(512)
                .setEnergyConsumption(16)
                .setProcessingSpeed(1)
                .register(plugin);

        new GrowthChamber(DynaTechItems.DynaTechGeneral, DynaTechItems.GROWTH_CHAMBER, RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {
                        SlimefunItems.HARDENED_GLASS,SlimefunItems.HARDENED_GLASS,SlimefunItems.HARDENED_GLASS,
                        new ItemStack(Material.BONE_BLOCK),new ItemStack(Material.GRASS_BLOCK),new ItemStack(Material.BONE_BLOCK),
                        DynaTechItems.STAINLESS_STEEL,new ItemStack(Material.BONE_BLOCK),DynaTechItems.STAINLESS_STEEL

                })
                .setEnergyCapacity(512)
                .setEnergyConsumption(32)
                .setProcessingSpeed(1)
                .register(plugin);

        new AntigravityBubble(DynaTechItems.DynaTechGeneral, DynaTechItems.ANTIGRAVITY_BUBBLE, RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {
                        SlimefunItems.BLISTERING_INGOT_3, new ItemStack(Material.DIAMOND_BLOCK), SlimefunItems.BLISTERING_INGOT_3,
                        SlimefunItems.REINFORCED_ALLOY_INGOT, SlimefunItems.BIG_CAPACITOR , SlimefunItems.REINFORCED_ALLOY_INGOT,
                        SlimefunItems.REINFORCED_ALLOY_INGOT, DynaTechItems.STAINLESS_STEEL, SlimefunItems.REINFORCED_ALLOY_INGOT, 
                })
                .setEnergyCapacity(1024)
                .setEnergyConsumption(128)
                .setProcessingSpeed(1)
                .register(plugin);
        
        new WeatherController(DynaTechItems.DynaTechGeneral, DynaTechItems.WEATHER_CONTROLLER, RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {
                        null,SlimefunItems.HARDENED_METAL_INGOT,null,
                        new ItemStack(Material.WATER_BUCKET), DynaTechItems.ANCIENT_MACHINE_CORE, new ItemStack(Material.LAVA_BUCKET),
                        DynaTechItems.STAINLESS_STEEL, new ItemStack(Material.CRYING_OBSIDIAN), DynaTechItems.STAINLESS_STEEL
                })
                .setEnergyCapacity(512)
                .setEnergyConsumption(32)
                .setProcessingSpeed(1)
                .register(plugin);


        new HydroGenerator(DynaTechItems.DynaTechGeneral, DynaTechItems.WATER_MILL, RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {
                        SlimefunItems.ALUMINUM_INGOT, SlimefunItems.SULFATE, SlimefunItems.ALUMINUM_INGOT,
                        SlimefunItems.ALUMINUM_INGOT, SlimefunItems.ENERGY_CONNECTOR, SlimefunItems.ALUMINUM_INGOT,
                        new ItemStack(Material.WHITE_CONCRETE), null, new ItemStack(Material.WHITE_CONCRETE)
                }).register(plugin);

        new DragonEggGenerator(DynaTechItems.DynaTechGeneral, DynaTechItems.DRAGON_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE,
                new ItemStack[] {
                        new ItemStack(Material.CRYING_OBSIDIAN), new ItemStack(Material.END_STONE), new ItemStack(Material.CRYING_OBSIDIAN),
                        new ItemStack(Material.CRYING_OBSIDIAN), SlimefunItems.LEAD_INGOT, new ItemStack(Material.CRYING_OBSIDIAN),
                        SlimefunItems.LEAD_INGOT, SlimefunItems.MEDIUM_CAPACITOR, SlimefunItems.LEAD_INGOT


                }).register(plugin);

    }




}
