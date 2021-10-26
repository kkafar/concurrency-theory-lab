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
rzadka sytuacja, ale jest -- trzeba to wymyśleć)

Generalnie Jeżeli jest pierwsyz producent, jest on wyrzucowny przez
monitor przez konsumenta i powiedzmy do akcji wchodiz inny 
producent który dostaje locka, hasWaiters w tym momencie kłamie (
co prawda nikt na nim nie czeka, ale wątek pierwszgo producenta jest przed
monitorem -- w efekcie mamy małe zagładzanie). 
Powiedzmy, że stanie się tak z wszystkimi wątkami
producentów.
W końcu pierwszy rusza i mamy już kilka wątków stojących
pod conditionalem tylko dla pierwszego producenta.

Tak samo może się stać z konsumentami -- wtedy jakoś dochodzi do 
zakleszczenia -- wykazać to.

Należy przesłać kod poprawny -- bez hasWaiters (po prostu 
postawić flagę informującą o tym, czy jest pierwszy czekający).

