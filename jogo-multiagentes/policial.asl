// Policial 

!andar.
!chamarBombeiro.
!prenderIncendiario.

+!andar <-
	.wait(50)
	caminharPolicial;
	!andar.
+!andar.

+!chamarBombeiro <-
	chamarBombeiro;
	!chamarBombeiro.
+!chamarBombeiro.

+!prenderIncendiario <-
	prenderIncendiario;
	!prenderIncendiario.
+!prenderIncendiario.
