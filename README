# Projet Ricochet Robot

## Présentation

Le projet **Ricochet Robot** est une implémentation du jeu de société Ricochet Robot en Java, avec un focus particulier sur l'utilisation de l'algorithme A* pour résoudre les puzzles du jeu. Ce projet inclut une librairie pour la gestion des éléments du jeu ainsi que des scripts pour la compilation, les tests, et la génération de documentation.

Les sources Java de la librairie se trouvent dans le répertoire `src/`, tandis que les sources des tests sont dans le répertoire `tests/`.

## Architecture Logicielle

### Modèle-Vue-Contrôleur (MVC)

Le projet utilise le modèle MVC pour structurer le code de manière claire et maintenable :

- **Modèle :** Représente les données du jeu et la logique métier, y compris les éléments du jeu et les règles du Ricochet Robot.
- **Vue :** Gère l'affichage des éléments du jeu à l'utilisateur, comme les robots et les obstacles.
- **Contrôleur :** Gère les interactions entre le modèle et la vue, en mettant à jour l'état du jeu en fonction des actions du joueur.

### Algorithme A*

L'algorithme A* est utilisé pour résoudre les puzzles en trouvant le chemin optimal pour le robot. Le projet comprend plusieurs versions de cet algorithme :

- **Version AStar :** La première implémentation de l'algorithme A*.
- **Version AStarMax :** Une version améliorée de l'algorithme A*.
- **Version AStarFinalForm :** La version finale optimisée de l'algorithme A*.

### Modèles de Conception Utilisés

1. **Pattern Singleton**
   - Utilisé pour assurer qu'une seule instance du gestionnaire de jeu est utilisée tout au long de l'exécution du programme.

2. **Pattern Observer**
   - Utilisé pour mettre à jour la vue chaque fois que l'état du modèle du jeu change.

3. **Pattern Strategy**
   - Utilisé pour permettre l'utilisation de différentes stratégies de résolution, comme différentes versions de l'algorithme A*.

## Commandes Principales

- **Lancer le programme :**

  ```sh
  sh scripts/run.sh [nbElements]
