# HaruhiBot
A simplistic Minecraft Spigot to Discord API binding

# Feature Road Map
## Configuration
- [x] Provide Discord app token and user
- [x] Select which channels receive status updates
- [x] Configurable status message formats

## Status Updates
- [x] Notify Discord channels when server is online or offline and on player join/quit
- [x] Forward chat messages from players

## Discord Commands
- [ ] `/msg`, `/whisper`:
    - [ ] PM/whisper to a user on the Minecraft server
    - [ ] Tab-complete name from list of online users
- [ ] `/console`:
    - [ ] Pass any console command to the server and retrieve its output
    - [ ] Enable passing tab-completion data from Minecraft to Discord

## Minecraft Commands: wrapped under `/haruhi` to avoid namespace collisions
- [ ] `[list]`:
    - [x] Get all text channels that the bot currently has access to
    - [ ] Enumerate them to the calling player
- [ ] `[channel] <id>`:
    - [ ] Use short ID's (provided by `list`) to identify channels
    - [ ] Enable tab completion
- [ ] `[reply]`, `[r]`:
    - [ ] Cache last PM received per-player
    - [ ] Reply to same channel cached
- [ ] `[user] <Discord username>`:
    - [ ] Tab-complete Discord tag from guilds Haruhi is in

## Backend Details
- [ ] Periodic reconnect attempts (without blocking main thread) when network errors occur


