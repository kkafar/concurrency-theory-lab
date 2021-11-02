Do zadań trzeba dodać (na następne zajęcia) badanie statystyczne zagłodzenia wątków.

Wykazać, że przy 2 condition będzie zagłodzenie, a przy 4
condition nie ma. (przy dobrym rozmiarze bufora?)

Wykorzystujemy metodę z wykorzystaniem metody hasWaiters
(nie powinno działać)

Z przygotowania należy wiedzieć dlaczego bufer ma mieć 
taką ale inną długość

P:5:0:5 [+++++_____]
P:4:0:9 [+++++++++_]
T:2:2:9 [__+++++++_]

Korzystanie z hasWaiters ma powodować zakleszczenie (bardzo
rzadka sytuacja, ale jest -- trzeba to wymyślić).

Generalnie, jeżeli jest pierwszy producent, jest on wyrzucony przez
monitor przez konsumenta i powiedzmy, do akcji wchodzi inny 
producent, który dostaje locka, hasWaiters w tym momencie kłamie (
co prawda nikt na nim nie czeka, ale wątek pierwszego producenta jest przed
monitorem -- w efekcie mamy małe zagładzanie). 
Powiedzmy, że stanie się tak z wszystkimi wątkami
producentów.
W końcu pierwszy rusza i mamy już kilka wątków stojących
pod conditionalem tylko dla pierwszego producenta.

Tak samo może się stać z konsumentami -- wtedy jakoś dochodzi do 
zakleszczenia -- wykazać to.

Należy przesłać kod poprawny -- bez hasWaiters (po prostu 
postawić flagę informującą o tym, czy jest pierwszy czekający).

Rozwiązanie:

Sytuacja początkowa: 
P1, P2, K1, K2, monitor z wartością 10 (i max = 10)

1. Wchodzi P1, chce wyprodukować 4, wiesza się jako pierwszy producent -> (10/10)
2. Wchodzi P2, chce wyprodukować 4, wiesza się jako producent (bo nie jest pierwszy) -> (10/10)
3. Wchodzi K1, zabiera 1, zwalnia P1 i obaj trafiają do zbioru przed monitorem -> (9/10)
 W tym momencie `hasWaiters` już zwraca nieprawdziwą (z punktu widzenia informacji, jaką chcemy uzyskać, czyli
 tego, czy pierwszy czekający został obsłużony) informację -- false (pierwszy producent nie czeka)
4. Wchodzi K1, zabiera 1, zwalnia
5. Wchodzi P1, chce wyprodukować 4, wiesza się jako **pierwszy** producent -> (9/10)
6. Wchodzi K2, zabiera 1, zwalnia P2 i obaj trafiają do zbioru przed monitorem. Sytuacja jak wcześniej -> (8/10)
7. 