//Civil

!andar.

+!andar
	<- .wait(100) 
		caminharCivil;
	!andar.
+!andar.

+civilIncendiario : true
	<- !prenderIncendiario(civil).
	
+!prenderIncendiario(civil)
	<- .send(policial, achieve, prenderIncendiario(civil)).

+civilFogo : true	
	<- !apagarIncendio(civil).
	
+!apagarIncendio(civil)
	<- .send(bombeiro, achieve, apagarIncendio(civil)).
	
	




