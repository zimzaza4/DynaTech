package me.profelements.dynatech.items.machines;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemState;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.CSCoreLibPlugin.cscorelib2.item.CustomItem;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectableAction;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AMachine extends SlimefunItem implements EnergyNetComponent {

    public static Map<Block, MachineRecipe> processing = new HashMap<>();
    public static Map<Block, Integer> progress = new HashMap<>();
    
    protected final List<MachineRecipe> recipes = new ArrayList<>();

    private int energyConsumedPerTick = -1;
    private int energyCapacity = -1;
    private int processingSpeed = -1;

    private static final int[] BORDER = new int[] {0,1,2,3,4,5,6,7,8,13,31,36,37,38,39,40,41,42,43,44};
    private static final int[] BORDER_IN = new int[] {9,10,11,12,18,21,27,28,29,30};
    private static final int[] BORDER_OUT = new int[] {14,15,16,17,23,26,32,33,34,35};
    private static final int PROGRESS_BAR_SLOT = 22;

    private static final int[] INPUT_SLOTS = new int[] {19,20};
    private static final int[] OUTPUT_SLOTS = new int[] {24,25};

    @ParametersAreNonnullByDefault
    public AMachine(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
        if (isGraphical()) {
            new BlockMenuPreset(getMachineIdentifier(), getInventoryTitle()) {

                @Override
                public void init() {
                    constructMenu(this);
                }
    
                @Override
                public void newInstance(BlockMenu menu, Block b) {
                        newMachineInstance(menu, b);
    
                }
    
                @Override
                public boolean canOpen(Block b, Player p) {
                    return p.hasPermission("slimefun.inventory.bypass") ||
                            SlimefunPlugin.getProtectionManager().hasPermission(p, b.getLocation(), ProtectableAction.INTERACT_BLOCK);
                }
    
                @Override
                public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
                    return new int[0];
                }
    
                @Override
                public int[] getSlotsAccessedByItemTransport(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item) {
                    if (flow == ItemTransportFlow.INSERT) {
                        return getInputSlots();
                    } else {
                        return getOutputSlots();
                    }
                }
    
            };
        }
        
        registerBlockHandler(item.getItemId(), (p, b, tool, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);

            if (inv != null) {
                inv.dropItems(b.getLocation(), getInputSlots());
                inv.dropItems(b.getLocation(), getOutputSlots());

            }

            processing.remove(b);
            progress.remove(b);
            return true;

        });

        registerDefaultRecipes();
    }

    public void newMachineInstance(BlockMenu menu, Block b) {
    };

    @ParametersAreNonnullByDefault
    public AMachine(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, ItemStack recipeOutput) {
        super(category, item, recipeType, recipe);
        this.recipeOutput = recipeOutput;
    }

    protected void constructMenu(BlockMenuPreset preset) {
        for (int i : getBorders().get(0)) {
            preset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }

        for (int i : getBorders().get(1)) {
            preset.addItem(i, ChestMenuUtils.getInputSlotTexture(), ChestMenuUtils.getEmptyClickHandler());
        }

        for (int i : getBorders().get(2)) {
            preset.addItem(i, ChestMenuUtils.getOutputSlotTexture(), ChestMenuUtils.getEmptyClickHandler());
        }

        preset.addItem(getProgressBarSlot(), new CustomItem(Material.BLACK_STAINED_GLASS_PANE, " "), ChestMenuUtils.getEmptyClickHandler());

        for (int i : getOutputSlots()) {
            preset.addMenuClickHandler(i, new ChestMenu.AdvancedMenuClickHandler() {

                @Override
                public boolean onClick(Player p, int slot, ItemStack cursor, ClickAction action) {
                    return false;
                }

                @Override
                public boolean onClick(InventoryClickEvent e, Player p, int slot, ItemStack cursor, ClickAction action) {
                    return cursor == null || cursor.getType() == null || cursor.getType() == Material.AIR;
                }

            });

        }
    }

    public boolean isGraphical() {
        return true;
    }

    public List<int[]> getBorders() {
        List<int[]> borders = new ArrayList<>();
        borders.add(BORDER); //BORDER
        borders.add(BORDER_IN); //BORDER_IN
        borders.add(BORDER_OUT); //BORDER_OUT

        return borders;
    }

    public int getProgressBarSlot() {
        return PROGRESS_BAR_SLOT;
    }


    @Override
    public void preRegister() {
        addItemHandler(new BlockTicker() {

            @Override
            public void tick(Block b, SlimefunItem  sfItem, Config data) {
                AMachine.this.tick(b);
            }

            @Override
            public boolean isSynchronized() {
                return false;
            }
        });
    }

    //Processing Stuff
    public MachineRecipe getProcessing(Block b) {
        return processing.get(b);
    }

    public boolean isProcessing(Block b) {
        return processing.get(b) != null;
    }

    protected void tick(Block b) {
        BlockMenu inv = BlockStorage.getInventory(b);

        if (isProcessing(b)) {
            int timeLeft = progress.get(b);

            if (timeLeft > 0) {
                ChestMenuUtils.updateProgressbar(inv, getProgressBarSlot(), timeLeft, processing.get(b).getTicks(), getProgressBar());

                if (isChargeable()) {
                    if (getCharge(b.getLocation()) < getEnergyConsumption()) {
                        return;
                    }

                    removeCharge(b.getLocation(), getEnergyConsumption());
                }

                progress.put(b, timeLeft - 1);

            } else {
                inv.replaceExistingItem(getProgressBarSlot(),new CustomItem(Material.BLACK_STAINED_GLASS_PANE, " "));

                for (ItemStack output : processing.get(b).getOutput()) {
                    inv.pushItem(output.clone(), getOutputSlots());
                }

                processing.remove(b);
                progress.remove(b);

            }
        } else {
            MachineRecipe next = findNextRecipe(inv);

            if (next != null) {
                processing.put(b, next);
                progress.put(b, next.getTicks());
            }
        }
    }

    public MachineRecipe findNextRecipe(BlockMenu inv) {
        Map<Integer, ItemStack> inventory = new HashMap<>();

        for (int slot : getInputSlots()) {
            ItemStack item = inv.getItemInSlot(slot);

            if (item != null) {
                inventory.put(slot, new ItemStackWrapper(item));
            }
        }

        Map<Integer, Integer> found = new HashMap<>();

        for (MachineRecipe recipe : recipes) {
            for (ItemStack input : recipe.getInput()) {
                for (int slot : getInputSlots()) {
                    if (SlimefunUtils.isItemSimilar(inventory.get(slot), input, true)) {
                        found.put(slot, input.getAmount());
                        break;
                    }
                }
            }

            if (found.size() == recipe.getInput().length) {
                for (int slot : getOutputSlots() ) {
                    if (inv.getItemInSlot(slot) != null) {
                        return null;
                    }
                }

                for (Map.Entry<Integer, Integer> entry : found.entrySet()) {
                    inv.consumeItem(entry.getKey(), entry.getValue());
                }

                return recipe;
            } else {
                found.clear();
            }
        }
        return null;
    }


    //Recipe Related Shenanigans
    protected void registerDefaultRecipes() {}

    public List<MachineRecipe> getMachineRecipes() {
        return recipes;
    }

    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> displayRecipes = new ArrayList<>();

        for (MachineRecipe recipe : recipes) {
            if (recipe.getInput().length != 1 ) {
                continue;
            }

            displayRecipes.add(recipe.getInput()[0]);
            displayRecipes.add(recipe.getOutput()[0]);

        }

        return displayRecipes;
    }

    public void registerRecipe(MachineRecipe recipe) {
        recipe.setTicks(recipe.getTicks() / getSpeed());
        recipes.add(recipe);
    }

    public void registerRecipe(int seconds, ItemStack[] input, ItemStack[] output) {
        registerRecipe(new MachineRecipe(seconds, input, output));
    }

    public void registerRecipe(int seconds, ItemStack input, ItemStack output) {
        registerRecipe(new MachineRecipe(seconds, new ItemStack[] {input}, new ItemStack[] {output}));
    }

    //Generic Getters
    public String getInventoryTitle() {
        return getItemName();
    }

    public abstract String getMachineIdentifier();

    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    public abstract ItemStack getProgressBar();

    public int[] getInputSlots() {
        return INPUT_SLOTS;
    }

    public int[] getOutputSlots() {
        return OUTPUT_SLOTS;
    }


    //Registry Stuff.
    public int getCapacity() {
        return energyCapacity;
    }

    public int getEnergyConsumption() {
        return energyConsumedPerTick;
    }

    public int getSpeed() {
        return processingSpeed;
    }

    public final AMachine setEnergyCapacity(int capacity) {
        Validate.isTrue(capacity > 0, "能量容量必须大于0");

        if(getState() == ItemState.UNREGISTERED) {
            this.energyCapacity = capacity;
            return this;
        } else {
            throw new IllegalStateException("您无法修改已注册的项目");
        }

    }

    public final AMachine setEnergyConsumption(int energyConsumption) {
        Validate.isTrue(energyConsumption > 0, "能耗必须大于0");
        Validate.isTrue(energyCapacity > 0, "能耗之前必须指定能量容量");
        Validate.isTrue(energyConsumption <= energyCapacity, "能源消耗不能大于能源容量");

        this.energyConsumedPerTick = energyConsumption;
        return this;

    }

    public final AMachine setProcessingSpeed(int processingSpeed) {
        Validate.isTrue(processingSpeed > 0, "处理速度必须大于0");

        this.processingSpeed = processingSpeed;
        return this;

    }

    @Override
    public void register(@Nonnull SlimefunAddon slimefunAddon) {
        this.addon = slimefunAddon;

        if (getCapacity() <= 0) {
            warn("容量配置不正确。 该项目已被禁用");
            warn("确保 '" + getClass().getSimpleName() + "#setEnergyCapacity(...)' 在注册之前!");
        }

        if (getEnergyConsumption() <= 0) {
            warn("能耗配置不正确。 该项目已被禁用");
            warn("确保 '" + getClass().getSimpleName() + "#setEnergyConsumption(...)' 在注册之前!");
        }

        if (getSpeed() <= 0) {
            warn("处理速度未正确配置。 该项目已被禁用");
            warn("确保 '" + getClass().getSimpleName() + "#setProcessingSpeed(...)' 在注册之前!");
        }

        if (getCapacity() > 0 && getEnergyConsumption() > 0 && getSpeed() > 0) {
            super.register(addon);
        }

    }
}
