# uqac-958-bowling-testing

## Règles du bowling

- [x] La partie d'un joueur est divisée en dix carreaux (frames), numérotés de 1 à 10.
- [ ] Pour chacun des carreaux 1 à 9, le joueur effectue un ou deux lancers (rolls) pour tenter d'abattre les dix quilles (pins) se trouvant au bout de l'allée.
- [ ] Lorsqu'un joueur abat dix quilles au premier lancer, il réussit ce que l'on appelle un abat (strike). Dans ce cas, il n'effectue pas de deuxième lancer et le carreau prend fin.
- [ ] S'il reste des quilles à abattre après le premier lancer, le joueur lance une deuxième fois. S'il abat toutes les quilles restantes, il réussit ce que l'on appelle une réserve (spare). (Cela signifie qu'abattre dix quilles après le premier lancer ne s'appelle pas un abat.)
- [ ] Le dixième et dernier carreau est spécial, car le joueur peut parfois lancer trois fois:
  - [ ] si le joueur réussit un abat, il lance deux autres fois et le carreau prend fin;
  - [ ] si le joueur réussit une réserve après le deuxième lancer, il lance une troisième et dernière fois et le carreau prend fin;
  - [ ] autrement, le joueur lance exactement deux fois.

Un carreau où le joueur réussit à abattre les dix quilles (que ce soit en un ou deux lancers) s'appelle un carreau fermé (mark); autrement, on dit que le carreau est ouvert (open frame). Un lancer qui n'atteint aucune quille est appelé un dalot.

## Règles de pointage

- [ ] Le score d'un carreau ouvert est égal au nombre de quilles abattues.
- [ ] Le score d'un carreau fermé est égal au nombre de quilles abattues, auquel on ajoute des points bonus:
  - [ ] Dans le cas d'une réserve: les points bonus sont le nombre de quilles abattues dans le prochain lancer.
  - [ ] Dans le cas d'un abat: les points bonus sont le nombre de quilles abattues dans les deux lancers suivants. (Attention: si le lancer suivant est lui aussi un abat, les deux prochains lancers se trouvent donc chacun dans un carreau.)

### Exemple de grille de pointage

```
|#1  |#2  |#3  |#4  |#5  |#6  |#7  |#8  |#9  |#10 |
+----+----+----+----+----+----+----+----+----+----|
|  36|  X |  5-|  1/|  X |  --|  -6|  X |  2/| 1/3|
|9   |24  |29  |49  |59  |59  |65  |85  |96  |109 |
```
- [ ] On y retrouve sur la première ligne le numéro du carreau.
- [ ] Ensuite, dans chaque carreau, on voit le résultat des lancers: un dalot est noté par le symbole -, une réserve par /, et un abat par X; tous les autres lancers sont représentés par le chiffre correspondant au nombre de quilles abattues.
- [ ] Finalement, la dernière ligne donne le pointage cumulatif à ce point de la partie.

Cet exemple de partie met en évidence les différents cas de figure du pointage traditionnel:

- Le carreau #1 est ouvert: son score est donc égal à la somme des quilles abattues dans les deux lancers (9=3+6).
- Le joueur a réussi un abat au carreau #2. Le score de ce carreau est donc égal à 10 + le nombre de quilles abattues lors des des deux lancers suivants (5 + 0), soit 15. Le score cumulatif est donc de 9+15=24.
- Le joueur a réussi une réserve au carreau #4: le score de ce carreau est donc de 10 + le nombre de quilles abattues au lancer suivant (10), soit 20. Le score cumulatif est donc de 29+20=49.
- Le joueur a réussi une réserve au carreau #10, il relance donc une troisième fois.

## Librairie de pointage

### Classe Frame (Normal & Last)

La classe Frame représente un carreau. On s'attend à ce qu'elle fournisse les fonctionnalités suivantes:

- [x] La méthode setPinsDown(x,y) permet d'enregistrer que y quilles ont été abattues au lancer x du carreau (le premier lancer porte le numéro 1). Cette méthode doit obliger l'utilisateur à entrer les lancers dans l'ordre: le lancer 1 avant le lancer 2, etc. Une exception de type BowlingException doit être lancée dans tous les autres cas. Cette méthode doit également lancer une exception pour des appels correspondant à des lancers impossibles à obtenir aux quilles ---soit directement, soit en rapport avec les lancers précédents du carreau. Lorsqu'une exception est lancée, l'objet ne change pas d'état.
- [x] La méthode countRolls() retourne le nombre de lancers qui ont eu lieu dans le carreau (en fonction des lancers qui ont été enregistrés jusqu'à ce point). (**Antoine en cours**)
- [x] La méthode countPinsDown() retourne le nombre total de quilles abattues dans le carreau (en fonction des lancers qui ont été enregistrés jusqu'à ce point). (**Antoine en cours**)
- [x] La méthode countPinsDown(x) retourne le nombre de quilles abattues au lancer x (le premier lancer est numéroté 1). Si ce lancer n'a pas eu lieu, la méthode retourne -1. (**Antoine en cours**)
- [x] Selon que l'on soit dans les carreaux 1 à 9 ou au dixième, on doit instancier les classes NormalFrame ou LastFrame, respectivement.
- [ ] La méthode toString() retourne sous forme de chaîne de caractères les symboles correspondant aux lancers du carreau; le bon symbole doit être utilsé dans le cas d'un dalleau, d'une réserve et d'un abat. La méthode doit toujours retourner deux caractères pour les carreaux 1-9, et trois caractères pour le carreau 10; si un lancer n'a pas été effectué, il est remplacé par un espace. Par exemple, dans le cas du carreau #1 ci-dessus, cette méthode doit retourner la chaîne "X " (le caractère "X" suivi d'un espace).
- [ ] La méthode reset() doit effacer toute l'information du carreau, et permet de ré-enregistrer les lancers à partir du premier. Cette méthode peut être appelée en tout moment.

### Classe Game

La classe Game représente la partie d'un joueur.

- [x] On lui ajoute des carreaux (donc des objets de type Frame) en utilisant sa méthode addFrame(f).
- [ ] La méthode getFrame(x) permet d'obtenir l'objet Frame correspondant au carreau x. **(CETTE MÉTHODE N'EXISTE PAS)**
- [ ] La méthode getCumulativeScore(x) permet d'obtenir le score cumulatif jusqu'au carreau x.

## Consignes et Notation

Au moyen d'une suite de tests jUnit, vérifiez que les classes NormalFrame, LastFrame et Game implémentent correctement la notation et le calcul de pointage du jeu de quilles, et suivent le comportement spécifié ci-dessus. On y a délibérément laissé des des erreurs, que votre suite de tests vise à découvrir. (Il y a plus d'une quinzaine d'erreurs distinctes.)

Les points seront accordés au devoir selon le barème suivant:
| Élément |	Points |
|---------|--------|
| Présence de commentaires appropriés	| 1 |
|Présence d'un test utilisant une assertion	| 1 |
|Présence d'un test contrôlant une exception	| 1 |
|Présence d'un test paramétrisé	| 2 |
|Nombre d'éléments valides contrôlés	| 15 |
|Nombre d'erreurs distinctes détectées	| 15 |
| Total	| 35 |
