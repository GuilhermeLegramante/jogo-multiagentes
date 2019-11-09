// Policial 

+!prenderIncendiario(civil)
	<- ?pos(incendiario,X,Y);
	perseguirIncendiario(X,Y);
	!prenderIncendiario(civil).
+!prenderIncendiario(civil).

+!prenderIncendiario(bombeiro)
	<- ?pos(incendiario,X,Y);
	perseguirIncendiario(X,Y);
	!prenderIncendiario(bombeiro).
+!prenderIncendiario(bombeiro).

+policialFogo : true	
	<- !apagarIncendio(policial).
	
+!apagarIncendio(policial)
	<- .send(bombeiro, achieve, apagarIncendio(policial)).

+policialIncendiario : true
	<- prenderIncendiario.


