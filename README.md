# SkSWM  ![Java CI with Maven](https://github.com/RealGatt/SkSWM/workflows/Java%20CI%20with%20Maven/badge.svg)
Skript SlimeWorldManager addon

# Requirements

[SlimeWorldManager](https://github.com/Grinderwolf/Slime-World-Manager) 2.2.0 or above

Potentially  [EnhancedSlimeWorldManager](https://github.com/endrealm/Enhanced-Slime-World-Manager/) might work, but no guarantees.

# IMPORTANT

Please read over **all of the SlimeWorldManager** Wiki information before installing SlimeWorldManager, and SkSWM and decide if this is a product for you. This is **required reading material**.

https://github.com/Grinderwolf/Slime-World-Manager#wiki-overview

## Test Script

```
command /test <text>:
	trigger:
		set {_props} to new slime world property map
		set {_emptyWorld} to new empty slime world named "%arg%" from file using properties {_props}
		load slime world {_emptyWorld}
		wait 10 ticks
		set {_world} to "%arg-1%" parsed as a world
		teleport player to spawn of {_world}
```

Once a **SlimeWorld** has been loaded into memory, it can be used like any other World in Skript. To remove it, simply `unload` the world.

Saving a world will only work if a World is loaded without the `read-only` tag.

Example use-case:

```

Mini Game server;

> Server Decides Map from pool of Maps
> Server Loads Map from Database as a "read-only" world
> Game is played
> World is unloaded
> Repeat from step 1.

```

## Upcoming Features

> Cloning Worlds from a Template World

> Migrating Worlds

> Moving worlds between storage types

