// Policial 

!prenderIncendiario.

+!prenderIncendiario <-
	.wait(10)
	caminharPolicial;
	!prenderIncendiario.
+!prenderIncendiario.

+policialFogo : true <- chamarBombeiro.

+policialIncendiario : true <- prenderIncendiario.


