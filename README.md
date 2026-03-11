# MinesFinder 💣🔍

**March 2026**  
*University Degree Group Project – 2nd Year, 2nd Semester*

MinesFinder is a grid-based puzzle game inspired by the classic **Minesweeper**.  
The goal is to reveal all safe cells while avoiding hidden mines using logical deduction and numerical hints.

Instead of traditional flags, players can mark cells using different indicators to track potential mine locations.

---

## Features ✨

### 🧩 Core Gameplay
- Grid-based board
- Random mine generation
- Numerical hints indicating nearby mines
- Safe cell revealing
- Automatic expansion of empty areas

### ⚠️ Cell Marking System
- **Red `!`** – High probability the cell contains a mine
- **Yellow `?`** – Uncertain cell that may contain a mine

These markers help players track potential dangers and avoid accidental reveals.

### 💣 Mine Detection
Each revealed cell displays the number of mines in the **8 surrounding cells** (horizontal, vertical, and diagonal), allowing players to deduce where mines are located.

### 🏆 High Scores Storage
Player records are automatically saved in a file:
```
minesfinder.recordes
```


Located in the user’s home directory, ensuring persistence across game sessions.

**Default locations:**

| OS       | Path |
|----------|------|
| Windows  | `C:\Users\<username>\minesfinder.recordes` |
| Linux    | `/home/<username>/minesfinder.recordes` |
| macOS    | `/Users/<username>/minesfinder.recordes` |

**How it works:**
- On startup, the game checks if the file exists.
- If it exists, saved records are loaded.
- If it does not exist, a new file is automatically created.

---

### 🎮 Game States
- **Start** – New board generated
- **Playing** – Player reveals and marks cells
- **Win** – All safe cells revealed
- **Lose** – A mine was revealed

---

## How the Game Works ⚙️

1. A board is generated with a predefined number of mines.
2. The player selects a cell to reveal.
3. If the cell contains a mine → game ends.
4. If the cell is safe → a number shows nearby mines.
5. If the cell has **0 adjacent mines**, neighboring cells are automatically revealed.
6. The player wins when all non-mine cells are revealed.

---

## Controls 🕹️

- **Reveal Cell:** Left mouse click
- **Mark Cell:** Right mouse click (cycle through markers):
  - `!` → suspected mine
  - `?` → uncertain

- **Solver (Logical Step):** Press `S` to automatically make safe moves

---

## Project Structure 📂
```
MinesFinder/
│
├── src/main/java/pt.ipleiria.estg.dei.ei.esoft/
│ ├── BotaoCampoMinado.java
│ ├── CampoMinado.java
│ ├── TabelaRecordes.java
│ ├── TabelaRecordesListener.java
│ ├── JanelaDeJogo/
│ │ ├── JanelaDeJogo.java
│ │ └── JanelaDeJogo.form
│ └── MinesFinder/
│ ├── MinesFinder.java
│ └── MinesFinder.form
│
├── src/main/resources/
├── pom.xml
└── README.md
```
---

## Module Interaction Diagram 🗂️
```
+-----------------+
|   MinesFinder   |  <-- main menu / difficulty selection
+-----------------+
|        |        \
|        |         \
v        v          v
|        |        /
|        |       /
|        |      /
+----------------+ +----------------+ +----------------+
| JanelaDeJogo | | TabelaRecordes | | SolverCampoMinado |
| (game window) |<->| (high scores) |<->| (logic helper) |
+----------------+ +----------------+ +----------------+
|
v
+----------------+
| CampoMinado | <-- game engine / board state
+----------------+
```
**Legend:**
- `MinesFinder` → launches `JanelaDeJogo` with selected difficulty  
- `JanelaDeJogo` → GUI + player interaction  
- `CampoMinado` → board logic, mine placement, game rules  
- `SolverCampoMinado` → optional auto-play logic for safe moves  
- `TabelaRecordes` → stores and updates high scores

---

## Technologies Used 🛠️

- **Java**
- **Java Swing** for the graphical interface
- **Maven** for project management and build
- **IntelliJ IDEA GUI Designer**
- **Object-Oriented Programming (OOP)**

---

## Build & Run 🚀

This project uses **Maven**.

Compile the project:
You can run the project directly from IntelliJ IDEA.

---

## Educational Purpose 🎓

This project was developed as a programming exercise to practice:
Object-oriented programming
Algorithm design
Grid-based game logic
Event-driven programming with Swing
GUI development in Java

---

## 👥 Authors

* Duarte Lacerda

---

## License 📄

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
