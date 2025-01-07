
# MMLeaderboard

MMLeaderboard is a Minecraft plugin that tracks player damage dealt to MythicMobs and stores it on minecraft scoreboard. It helps monitor and record player contributions in battles against MythicMobs using a dynamic scoreboard system.

## Features
- When an entity is being tracked, a scoreboard is created using the UUID as the scoreboard name. All damage dealt by players to the entity is recorded, except for fire damage (e.g., from Fire Aspect and Flame enchantments).
- Automatically cleans up the list and existing scoreboards when the server is stopping.

## Dependencies

- **MythicMobs** [Link here](https://mythiccraft.io/index.php?resources/mythicmobs.1/)

## Installation

1. Download the MMLeaderboard plugin `.jar` file.
2. Place the `.jar` file into your server's `plugins` folder.
3. Restart the server to load the plugin.

## Commands Usage

- **/mml add <uuid>**: Adds a MythicMob entity to be tracked by the plugin. This will create a new scoreboard with the UUID of the mob.
  
  Example:
  ```
  /mml add 788307ef-9dbc-4ae3-a2bf-0f2e3ad3d2ea
  ```
  Or in MythicMobs scripts:
  ```
  - command{c="mml add <caster.uuid>"}
  ```

- **/mml remove <uuid>**: Removes the tracked MythicMob entity and deletes it's scoreboard.

  Example:
  ```
  /mml remove 788307ef-9dbc-4ae3-a2bf-0f2e3ad3d2ea
  ```
  Or in MythicMobs scripts:
  ```
  - command{c="mml remove <caster.uuid>"}
  ```

- **/mml list**: Displays all the currently tracked UUID.

## Limitations

- Due to Minecraft's scoreboard limitations, damage numbers will be rounded up and multiplied by 100 to maintain only 2 decimal places of accuracy.

## Future Plans
- ~~Allow linking an entity to the leaderboard of another entity (For damage taken by minions to be counted towards their parent's leaderboard)~~ ✅ 
- Implement custom scoreboard mechanics to bypass the limitation of Minecraft scoreboard only able to take in integer value
