//Bombeiro

!andar.
!apagarIncendio.
!chamarPolicial.

+!andar <-
	.wait(50)
	caminharBombeiro;
	!andar.
+!andar.

+!apagarIncendio <-
	apagarFogo;
	!apagarIncendio.
+!apagarIncendio.

+!chamarPolicial <-
	chamarPolicial;
	!chamarPolicial.
+!chamarPolicial.

