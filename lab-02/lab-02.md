1. Monitor jest wąskim gardłem ==> jezeli mozna, nie stosowac.


## Zadanie do zaimplementowania

1. 1 producent 2 konsumentów
2. Wiele producentów, wielu konsumentów

Do wykorzystania synchronized, wait, notify (tylko w monitorze, nie wolno ich wykorzystywac w watkach)

Jak doprowadzi do zakleszczenia 
k1, k2, p, p, k2, k1, k2

P1, P2, K, K, 
1

1. Wchodzi producent, uzupełnia bufor. Obydwaj klienci czekają. 
2. Producent wychodzi, powiadomiony zostaje konsument.
3. 