package io.github.thebusybiscuit.chestterminal;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu.MenuClickHandler;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunBlockHandler;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.UnregisterReason;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;

public class ImportBus extends SlimefunItem {
	
	private static final int[] border = {0, 1, 3, 4, 5, 6, 9, 10, 11, 12, 13, 14, 15, 18, 22, 24, 27, 31, 33, 34, 35, 36, 40, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53};

	public ImportBus(Category category, ItemStack item, String id, RecipeType recipeType, ItemStack[] recipe) {
		super(category, item, id, recipeType, recipe);
		
		new BlockMenuPreset(getID(), ChatColor.translateAlternateColorCodes('&', "&3CT \u8f38\u5165\u7aef\u53e3")) { /* "&3CT 輸入端口" */
			
			@Override
			public void init() {
				constructMenu(this);
			}
			
			@Override
			public void newInstance(final BlockMenu menu, final Block b) {
				try {
					if (!BlockStorage.hasBlockInfo(b) || BlockStorage.getLocationInfo(b.getLocation(), "filter-type") == null || BlockStorage.getLocationInfo(b.getLocation(), "filter-type").equals("whitelist")) {
						menu.replaceExistingItem(23, new CustomItem(Material.WHITE_WOOL, "&7\u985e\u578b: &r\u767d\u540d\u55ae", "", "&e> \u9ede\u64ca\u66f4\u6539\u70ba\u9ed1\u540d\u55ae"));
						menu.addMenuClickHandler(23, (p, slot, item, action) -> {
							BlockStorage.addBlockInfo(b, "filter-type", "blacklist");
							newInstance(menu, b);
							return false;
						}); /* "&7類型: &r白名單", "", "&e> 點擊更改為黑名單" */
					}
					else {
						menu.replaceExistingItem(23, new CustomItem(Material.BLACK_WOOL, "&7\u985e\u578b: &r\u9ed1\u540d\u55ae", "", "&e> \u9ede\u64ca\u66f4\u6539\u70ba\u767d\u540d\u55ae"));
						menu.addMenuClickHandler(23, (p, slot, item, action) -> {
							BlockStorage.addBlockInfo(b, "filter-type", "whitelist");
							newInstance(menu, b);
							return false;
						}); /* "&7類型: &r黑名單", "", "&e> 點擊更改為白名單" */
					}
					
					if (!BlockStorage.hasBlockInfo(b) || BlockStorage.getLocationInfo(b.getLocation(), "filter-durability") == null || BlockStorage.getLocationInfo(b.getLocation(), "filter-durability").equals("false")) {
						menu.replaceExistingItem(41, new CustomItem(Material.STONE_SWORD, "&7\u5305\u62ecSub-IDs/\u8010\u4e45\u5ea6: &4\u2718", "", "&e> \u9ede\u64ca\u689d\u4ef6\u662f\u5426\u5305\u62ec\u7269\u54c1 \u8010\u4e45\u5ea6 \u9700\u8981\u76f8\u540c"));
						menu.addMenuClickHandler(41, (p, slot, item, action) -> {
							BlockStorage.addBlockInfo(b, "filter-durability", "true");
							newInstance(menu, b);
							return false;
						}); /* "&7包括Sub-IDs/耐久度: &4\u2718", "", "&e> 點擊條件是否包括物品 耐久度 需要相同" */
					}
					else {
						menu.replaceExistingItem(41, new CustomItem(Material.GOLDEN_SWORD, "&7\u5305\u62ecSub-IDs/\u8010\u4e45\u5ea6: &2\u2714", "", "&e> \u9ede\u64ca\u689d\u4ef6\u662f\u5426\u5305\u62ec\u7269\u54c1 \u8010\u4e45\u5ea6 \u9700\u8981\u76f8\u540c"));
						menu.addMenuClickHandler(41, (p, slot, item, action) -> {
							BlockStorage.addBlockInfo(b, "filter-durability", "false");
							newInstance(menu, b);
							return false;
						}); /* "&7包括Sub-IDs/耐久度: &2\u2714", "", "&e> 點擊條件是否包括物品 耐久度 需要相同" */
					}
					
					if (!BlockStorage.hasBlockInfo(b) || BlockStorage.getLocationInfo(b.getLocation(), "filter-lore") == null || BlockStorage.getLocationInfo(b.getLocation(), "filter-lore").equals("true")) {
						menu.replaceExistingItem(32, new CustomItem(Material.MAP, "&7\u5305\u62ec\u9644\u8a3bLore: &2\u2714", "", "&e> \u9ede\u64ca\u689d\u4ef6\u662f\u5426\u5305\u62ec\u7269\u54c1 \u9644\u8a3bLore \u9700\u8981\u76f8\u540c"));
						menu.addMenuClickHandler(32, (p, slot, item, action) -> {
							BlockStorage.addBlockInfo(b, "filter-lore", "false");
							newInstance(menu, b);
							return false;
						}); /* "&7包括附註Lore: &2\u2714", "", "&e> 點擊條件是否包括物品 附註Lore 需要相同 */
					}
					else {
						menu.replaceExistingItem(32, new CustomItem(Material.MAP, "&7\u5305\u62ec\u9644\u8a3bLore: &4\u2718", "", "&e> \u9ede\u64ca\u689d\u4ef6\u662f\u5426\u5305\u62ec\u7269\u54c1 \u9644\u8a3bLore \u9700\u8981\u76f8\u540c"));
						menu.addMenuClickHandler(32, (p, slot, item, action) -> {
							BlockStorage.addBlockInfo(b, "filter-lore", "true");
							newInstance(menu, b);
							return false;
						}); /* "&7包括附註Lore: &4\u2718", "", "&e> 點擊條件是否包括物品 附註Lore 需要相同" */
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public boolean canOpen(Block b, Player p) {
				return BlockStorage.getLocationInfo(b.getLocation(), "owner").equals(p.getUniqueId().toString()) || p.hasPermission("slimefun.cargo.bypass");
			}

			@Override
			public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
				return new int[0];
			}
		};
		
		registerBlockHandler(getID(), new SlimefunBlockHandler() {
			
			@Override
			public void onPlace(Player p, Block b, SlimefunItem item) {
				BlockStorage.addBlockInfo(b, "owner", p.getUniqueId().toString());
				BlockStorage.addBlockInfo(b, "index", "0");
				BlockStorage.addBlockInfo(b, "filter-type", "whitelist");
				BlockStorage.addBlockInfo(b, "filter-lore", "true");
				BlockStorage.addBlockInfo(b, "filter-durability", "false");
			}
			
			@Override
			public boolean onBreak(Player p, Block b, SlimefunItem item, UnregisterReason reason) {
				for (int slot: getInputSlots()) {
					if (BlockStorage.getInventory(b).getItemInSlot(slot) != null) b.getWorld().dropItemNaturally(b.getLocation(), BlockStorage.getInventory(b).getItemInSlot(slot));
				}
				return true;
			}
		});
	}
	
	protected void constructMenu(BlockMenuPreset preset) {
		MenuClickHandler click = (p, slot, item, action) -> false;
		
		for (int i: border) {
			preset.addItem(i, new CustomItem(Material.CYAN_STAINED_GLASS_PANE, " "), click);
		}

		preset.addItem(7, new CustomItem(Material.ORANGE_STAINED_GLASS_PANE, " "), click);
		preset.addItem(8, new CustomItem(Material.ORANGE_STAINED_GLASS_PANE, " "), click);
		preset.addItem(16, new CustomItem(Material.ORANGE_STAINED_GLASS_PANE, " "), click);
		preset.addItem(25, new CustomItem(Material.ORANGE_STAINED_GLASS_PANE, " "), click);
		preset.addItem(26, new CustomItem(Material.ORANGE_STAINED_GLASS_PANE, " "), click);

		preset.addItem(2, new CustomItem(Material.PAPER, "&3\u7269\u54c1", "", "&b\u653e\u5728\u9019\u88e1\u52a0\u5165", "&b\u9ed1\u540d\u55ae/\u767d\u540d\u55ae"), click);
		/* "&3物品", "", "&b放在這裡加入", "&b黑名單/白名單" */
	}
	
	public int[] getInputSlots() {
		return new int[] {19, 20, 21, 28, 29, 30, 37, 38, 39};
	}
}
