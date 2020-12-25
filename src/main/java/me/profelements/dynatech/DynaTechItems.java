package me.profelements.dynatech;

import io.github.thebusybiscuit.slimefun4.core.attributes.MachineTier;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineType;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.mrCookieSlime.Slimefun.cscorelib2.skull.SkullItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

public class DynaTechItems {

    private DynaTechItems() {}

    public static final Category DynaTechGeneral = new Category(new NamespacedKey(DynaTech.getInstance(),
            "dynatech"),
            new CustomItem(Material.CONDUIT, "&b动力科技")
    );

    //Materials
    public static final SlimefunItemStack STAINLESS_STEEL = new SlimefunItemStack("STAINLESS_STEEL", Material.IRON_INGOT, "&6Stainless Steel Ingot");
    public static final SlimefunItemStack STAINLESS_STEEL_ROTOR = new SlimefunItemStack("STAINLESS_STEEL_ROTOR", Material.IRON_BLOCK, "&6Stainless Steel Rotor");
    public static final SlimefunItemStack ANCIENT_MACHINE_CORE = new SlimefunItemStack("ANCIENT_MACHINE_CORE", Material.LAPIS_BLOCK, "&6Ancient Machine Core");

    //Backpacks
    public static final SlimefunItemStack PICNIC_BASKET = new SlimefunItemStack("PICNIC_BASKET",
            new CustomItem(SkullItem.fromHash("7a6bf916e28ccb80b4ebfacf98686ad6af7c4fb257e57a8cb78c71d19dccb2")),
            "&6野餐篮",
            "",
            "&f可以储存食物",
            "&f饥饿时自动消耗它们",
            "&f必须在背包里",
            "",
            "&f大小: &e27",
            "",
            "&7ID: <ID>",
            "",
            "&e右键单击&7打开"
    );

    public static final SlimefunItemStack INVENTORY_FILTER = new SlimefunItemStack("INVENTORY_FILTER",
            Material.IRON_BARS,
            "&6库存过滤器",
            "",
            "&f过滤掉地上的物品",
            "",
            "&7ID: <ID>",
            "",
            "&e右键单击&7打开"
    );

    public static final SlimefunItemStack ELECTRICAL_STIMULATOR = new SlimefunItemStack("ELECTRICAL_STIMULATOR",
            new CustomItem(SkullItem.fromHash("82a319cf66a4de12e3330e8bc4c82c985ccc3cb2230868c336a88fc4a22082a")),
            "&6电刺激器",
            "",
            "&f自动为您提供能量",
            "",
            "&f&o激发您的感官",
            "",
            LoreBuilder.powerCharged(0, 1024)
    );

    //Machines
    public static final SlimefunItemStack AUTO_KITCHEN = new SlimefunItemStack("AUTO_KITCHEN",
            Material.SMOKER,
            "&6自动厨具",
            "",
            "&f自动制作厨房食谱",
            "",
            "&f&o闻起来像饼干",
            "",
            LoreBuilder.machine(MachineTier.MEDIUM, MachineType.MACHINE),
            LoreBuilder.speed(1),
            LoreBuilder.powerPerSecond(16)
    );

    public static final SlimefunItemStack GROWTH_CHAMBER = new SlimefunItemStack("GROWTH_CHAMBER",
            Material.GREEN_STAINED_GLASS,
            "&6生长室",
            "",
            "&f自动种植一些植物.",
            "",
            "&f&o就像一个小温室!",
            "",
            LoreBuilder.machine(MachineTier.MEDIUM, MachineType.MACHINE),
            LoreBuilder.speed(1),
            LoreBuilder.powerPerSecond(32)
            );

    public static final SlimefunItemStack ANTIGRAVITY_BUBBLE = new SlimefunItemStack("ANTIGRAVITY_BUBBLE",
            Material.OBSIDIAN,
            "&6反重力气泡",
            "",
            "&f在45个方块内进行飞行"
            "",
            "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
            LoreBuilder.powerPerSecond(128)
            );

    public static final SlimefunItemStack WEATHER_CONTROLLER = new SlimefunItemStack("WEATHER_CONTROLLER",
            Material.BLUE_STAINED_GLASS,
            "&6天气控制器",
            "",
            "&f控制天气",
            "",
            LoreBuilder.machine(MachineTier.MEDIUM, MachineType.MACHINE),
            LoreBuilder.powerPerSecond(32)
            );
  
    public static final SlimefunItemStack WATER_MILL = new SlimefunItemStack("WATER_MILL",
            Material.COBBLESTONE_WALL,
            "&6水力发电机",
            "",
            "&f通过流动的水创造能量",
            "",
            LoreBuilder.machine(MachineTier.MEDIUM, MachineType.GENERATOR),
            LoreBuilder.powerBuffer(128),
            LoreBuilder.powerPerSecond(32)
    );

    public static final SlimefunItemStack DRAGON_GENERATOR = new SlimefunItemStack("DRAGON_GENERATOR",
            Material.GRAY_CONCRETE
            ,"&6龙蛋发电机",
            "",
            "&f从龙蛋的温暖中创造能量",
            "",
            LoreBuilder.machine(MachineTier.MEDIUM, MachineType.GENERATOR),
            LoreBuilder.powerBuffer(512),
            LoreBuilder.powerPerSecond(32)
    );



}
