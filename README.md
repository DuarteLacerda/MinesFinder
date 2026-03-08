# MinesFinder 💣🔍

MinesFinder is a grid-based puzzle game inspired by the classic **Minesweeper**.  
The objective is to reveal all safe cells while avoiding hidden mines by using logical deduction and numerical hints.

Instead of traditional flags, players can mark cells using different indicators to help track possible mine locations.

---

## Features ✨

### 🧩 Core Gameplay
- Grid-based board
- Random mine generation
- Numerical hints indicating nearby mines
- Safe cell revealing
- Automatic expansion of empty areas

---

### ⚠️ Cell Marking System
Players can mark suspicious cells using two types of indicators:

- **Red `!`** – High probability that the cell contains a mine  
- **Yellow `?`** – Uncertain cell that may contain a mine  

These markings help players track potential dangers and avoid accidental reveals.

---

### 💣 Mine Detection
Each revealed cell displays a number representing the number of mines in the **8 surrounding cells**:
- Horizontal
- Vertical
- Diagonal

This information allows the player to deduce where mines are located.

---

### 🏆 High Scores Storage

The game automatically saves player records in a file named:
minesfinder.recordes
This file is stored in the user's home directory, ensuring that records persist between game sessions and remain independent of the application installation location.

#### 📂 Default Locations

Depending on the operating system, the file will typically be created in:

Windows
C:\Users\<username>\minesfinder.recordes

Linux
/home/<username>/minesfinder.recordes

macOS
/Users/<username>/minesfinder.recordes

#### ⚙️ How It Works

When the game starts, it checks whether the records file already exists:
If the file exists, the saved records are loaded.
If the file does not exist, a new one is automatically created.
This mechanism allows the game to maintain a persistent high score table across multiple sessions.

---

### 🎮 Game States
The game progresses through different states:

- **Start** – A new board is generated  
- **Playing** – The player reveals and marks cells  
- **Win** – All safe cells have been revealed  
- **Lose** – A mine is revealed  

---

## How the Game Works ⚙️

1. A board is generated with a predefined number of mines.
2. The player selects a cell to reveal.
3. If the cell contains a mine → the game ends.
4. If the cell is safe → a number appears showing nearby mines.
5. If the cell has **0 adjacent mines**, neighboring cells are automatically revealed.
6. The player wins when all non-mine cells are revealed.

---

## Controls 🕹️

Typical player actions:

- **Reveal Cell** – Opens the selected cell (**Mouse 1**)  
- **Mark Cell** – Cycles through markers (**Mouse 2**):
  - `!` → suspected mine  
  - `?` → uncertain  

---

## Project Structure 📂

```
MinesFinder/
│
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ └── pt.ipleiria.estg.dei.ei.esoft/
│ │ │ ├── BotaoCampoMinado.java
│ │ │ ├── CampoMinado.java
│ │ │ ├── TabelaRecordes.java
│ │ │ ├── TabelaRecordesListener.java
│ │ │
│ │ │ ├── JanelaDeJogo/
│ │ │ │ ├── JanelaDeJogo.java
│ │ │ │ └── JanelaDeJogo.form
│ │ │
│ │ │ └── MinesFinder/
│ │ │ ├── MinesFinder.java
│ │ │ └── MinesFinder.form
│ │ │
│ │ └── resources/
│
├── pom.xml
└── README.md
```

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
You can run the project directly from IntelliJ IDEA or execute the generated .jar file located in the target directory.

---

## Educational Purpose 🎓

This project was developed as a programming exercise to practice:
Object-oriented programming
Algorithm design
Grid-based game logic
Event-driven programming with Swing
GUI development in Java

---

## Possible Future Improvements 🚀

Difficulty levels (easy | medium | hard)
Timer and scoring system
Leaderboard
Custom board sizes
Improved UI/UX

---

## License 📄

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

**Version:** 1.0  
**Developed by:** Duarte Lacerda
