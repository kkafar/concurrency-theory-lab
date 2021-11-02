# Lab

Rozwiązanie na 3 lockach i n condition (będzie tłumaczone)

jeden lock dla producentow, drugi dla konsumentów

trzeci lock, dostępny dla obydwu (wolno im wziąć tylko wtedy, gdy już mają pierwszy)
Finalnie w monitorze jest tylko jeden wątek.

Jest potencjalne głodzenie na locku 3

Jeden condition ma wystarczyć

ELA

# Homework

1. Do przeczytania arytkuł zalinkowany na moodlu.
2. Pomiar zadania, który dzisiaj wykonamy. 
   1. Pomiary czasu


Jak mierzyć czas? Dwóch programów wielowątkowych, losowe porcje, losowe generatory

* Jednakowe ziarno dla generatorów
* Każdy wątek powinien mieć swój generator i odpalać go z ustalonego ziarna
* Eksperyment powtórzyć i uśrednić