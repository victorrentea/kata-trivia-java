# Yahir Hernández
# Ficha de Análisis — Game.py

## 1. Olores de código detectados

**Olor 1 — Arrays paralelos**
`positions`, `coins`, `in_penalty_box` son tres listas indexadas por el mismo
entero `current_player`. 
Indica una clase ausente: `Player`.

**Olor 2 — Números mágicos sin nombre**
- `12` → tamaño del tablero
- `50` → preguntas por categoría
- `6`  → monedas para ganar

**Olor 3 — Método demasiado largo: `roll()`**
Aproximadamente 30 líneas. Hace demasiado: verifica penalti, mueve jugador,
calcula categoría y hace la pregunta.

**Olor 4 — Duplicación de código**
El bloque de mover al jugador y preguntar aparece dos veces dentro de `roll()`.

**Olor 5 — Mezcla de responsabilidades**
`Game` gestiona: estado de jugadores, mazo de preguntas, lógica de turno
y condición de victoria. Cuatro responsabilidades en una sola clase.

**Olor 6 — Cuatro listas de preguntas paralelas**
`pop_questions`, `science_questions`, `sports_questions`, `rock_questions`
representan el mismo concepto y deberían vivir en una clase `QuestionDeck`.

---

## 2. Responsabilidades que deberían separarse

| Responsabilidad | Clase propuesta |
| Estado del jugador (posición, monedas, penalti) | `Player` |
| Mazo de preguntas por categoría | `QuestionDeck` |
| Lógica de turno (mover, penalti, preguntar) | métodos privados en `Game` |
| Control del juego (jugadores, condición de victoria) | `Game` (reducida) |

---

## 3. Abstracciones ausentes

| Player                  QuestionDeck
-------------------       ----------------------
name: str                 _questions: dict
position: int             -----------------------
coins: int                next_question(cat) → str
in_penalty_box: bool
_--------------------
advance(roll)
add_coin()
send_to_penalty_box()
has_won() → bool

---

## 4. El typo y el bug

**Typo:**
`"Answer was corrent!!!!"` en `handle_correct_answer()` — ambos archivos.
El Golden Master no lo detecta porque ambas versiones producen el mismo output.

**Bug:**
Las posiciones se inicializan en `1` y en `_current_category()` se hace
`pos - 1` como parche. El tablero debería usar posiciones `0-11` con
`% BOARD_SIZE` para el wrap, no `> 12 → -12`.

---

## 5. Itinerario de refactorizaciones (de menos a más riesgoso)

1. Renombrar variables y métodos engañosos
2. Extraer números mágicos como constantes
3. Extraer métodos pequeños con nombre de intención
4. Crear clase `Player`, mover campos uno por uno
5. Mover métodos de comportamiento a `Player`
6. Crear clase `QuestionDeck`
7. Simplificar `roll()` hasta que sea legible como prosa
8. Corregir typo (primero `game.py`, luego `game_old.py`)
9. Corregir bug (primero `game.py`, luego `game_old.py`)

--- 
Yahir Hdz