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

# LAB 5

Następne zajęcia są zdalnie. Trzeba zaimplementować zadanie. 
Trzeba zapisać się na kosultacje. (terminy będą do rezerwacji 
na moodlu). Jeżeli żaden termin nie będzie pasował, to 
pisać maila.


Będzie należało porównać Producent-konsument na monitorze i na Active-object.

Trzeba wprowadzić dodatkowe zadania (żeby asynchroniczne rozwiązanie miała co liczyć podczas
oczekiwania na wynik z `Servant`).

(Rozwiązanie na monitorze to też musi liczyć)

Trzeba będzie zrobić z tego sprawozdanie. 
Będzie tylko 5 minut na omówienie wyników. (trzeba powiedzieć o najciekawszym wyniku)
Będzie oceniania prezentacja sprawozdania i samo sprawozdanie.


Na za tydzień - kod. Za dwa tygodnie prezentacja.

W Future nie musi być synchronizacji! (Pooling) (chyba, że zasobów jest dużo, to wtedy CHYBA trzeba)

Mamy mieć nieograniczoną kolejkę zadań ().(?)

Scheduler nie może przeszukiwać liniowo, nie może oczekiwać aktywnie, nie może zagładzać.

Najważniejsze jest, aby Scheduler nie zagładzał.
